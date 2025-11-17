package com.project.ptittoanthu.documents.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.helper.SortHelper;
import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.documents.dto.DocumentSearchRequest;
import com.project.ptittoanthu.documents.dto.request.CreateDocumentRequest;
import com.project.ptittoanthu.documents.dto.request.UpdateDocumentRequest;
import com.project.ptittoanthu.documents.dto.response.DocumentResponse;
import com.project.ptittoanthu.documents.dto.response.DocumentResponseDetail;
import com.project.ptittoanthu.documents.exception.DocumentNotFoundExp;
import com.project.ptittoanthu.documents.mapper.DocumentMapper;
import com.project.ptittoanthu.documents.model.Document;
import com.project.ptittoanthu.documents.repository.DocumentRepository;
import com.project.ptittoanthu.documents.service.DocumentService;
import com.project.ptittoanthu.infra.files.FileService;
import com.project.ptittoanthu.notify.dto.CreateNotificationRequest;
import com.project.ptittoanthu.notify.model.NotificationType;
import com.project.ptittoanthu.notify.service.NotificationService;
import com.project.ptittoanthu.subjects.exception.SubjectNotFoundException;
import com.project.ptittoanthu.subjects.model.Subject;
import com.project.ptittoanthu.subjects.repository.SubjectRepository;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.model.Role;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final DocumentMapper documentMapper;
    private final FileService fileService;
    private final NotificationService notificationService;

    public DocumentServiceImpl(@Qualifier("file-local") FileService fileService, DocumentRepository documentRepository,
                               UserRepository userRepository, SubjectRepository subjectRepository,
                               DocumentMapper documentMapper, NotificationService notificationService) {
        this.fileService = fileService;
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.documentMapper = documentMapper;
        this.notificationService = notificationService;
    }

    @Transactional
    @Override
    public DocumentResponseDetail insertDocument(CreateDocumentRequest request) throws IOException {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(""));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new SubjectNotFoundException(""));

        Document document = documentMapper.toDocument(request);

        document.setOwner(user);
        document.setSubject(subject);
        document.setEnable(!user.getRole().equals(Role.STUDENT));
        document.setFilePath(fileService.upload(request.getFile(), request.getType()));

        documentRepository.save(document);
        subject.getUsers().forEach(u -> sendNotification(u,"Tài liệu mới được đăng tải", document.getTitle(),
                NotificationType.UPLOAD_DOCUMENT, document.getId()));
        return documentMapper.toDocumentResponseDetail(document);
    }

    private void sendNotification(User user, String title, String msg, NotificationType type, Integer targetId) {
        notificationService.createNotification(
                CreateNotificationRequest.builder()
                        .title(title)
                        .type(type)
                        .message(msg)
                        .targetId(targetId)
                        .user(user)
                        .build());
    }

    @Transactional
    @Override
    public DocumentResponseDetail updateDocument(UpdateDocumentRequest request) throws IOException {
        Document document = documentRepository.findById(request.getId())
                .orElseThrow(() -> new DocumentNotFoundExp(""));

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            String fileName = fileService.upload(request.getFile(), request.getType());
            fileService.delete(document.getFilePath());
            document.setFilePath(fileName);
        }

        documentMapper.updateDoc(request, document);
        documentRepository.save(document);
        document.getSubject().getUsers().forEach(u -> sendNotification(u,"Cập nhật tài liệu", document.getTitle(),
                NotificationType.DOCUMENT, document.getId()));
        return documentMapper.toDocumentResponseDetail(document);
    }

    @Override
    public DocumentResponseDetail getDocument(Integer id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundExp(""));
        return documentMapper.toDocumentResponseDetail(document);
    }

    @Transactional
    @Override
    public DocumentResponseDetail approveDocument(Integer id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundExp(""));

        document.setEnable(true);
        documentRepository.save(document);
        sendNotification(document.getOwner(),"Tài liệu được phát hành", document.getTitle(), NotificationType.DOCUMENT, document.getId());
        return documentMapper.toDocumentResponseDetail(document);
    }

    @Transactional
    @Override
    public DocumentResponseDetail disableDocument(Integer id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundExp(""));

        document.setEnable(false);
        documentRepository.save(document);
        sendNotification(document.getOwner(),"Tài liệu bị từ chối", document.getTitle(), NotificationType.DOCUMENT, document.getId());
        return documentMapper.toDocumentResponseDetail(document);
    }

    @Transactional
    @Override
    public void deleteDocument(Integer id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundExp(""));
        document.setDeletedAt(OffsetDateTime.now());
        documentRepository.save(document);
    }

    @Override
    public PageResult<List<DocumentResponse>> getDocs(DocumentSearchRequest request) {
        Sort sort = SortHelper.buildSort(request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize(), sort);

        Page<Document> documents = documentRepository.findAllBySearchRequest(request.getKeyword() ,request.getSubjectId(), pageable);

        List<DocumentResponse> responses = documentMapper.toDocumentResponse(documents.getContent());
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(documents, request);
        return PageResult.<List<DocumentResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(responses)
                .build();
    }
}
