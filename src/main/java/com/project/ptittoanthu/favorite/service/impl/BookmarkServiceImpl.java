package com.project.ptittoanthu.favorite.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.helper.SortHelper;
import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.documents.model.Document;
import com.project.ptittoanthu.documents.repository.DocumentRepository;
import com.project.ptittoanthu.favorite.model.Bookmark;
import com.project.ptittoanthu.favorite.repository.BookmarkRepository;
import com.project.ptittoanthu.favorite.service.BookmarkService;
import com.project.ptittoanthu.subjects.exception.SubjectNotFoundException;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepo;
    private final UserRepository userRepo;
    private final DocumentRepository documentRepo;

    @Override
    public void toggleBookmark(Integer documentId) {
        String email = SecurityUtils.getUserEmailFromSecurity();

        if (bookmarkRepo.existsByUserEmailAndDocumentId(email, documentId)) {
            Bookmark bookmark = bookmarkRepo
                    .findByUserEmailAndDocumentId(email, documentId)
                    .orElseThrow();
            bookmarkRepo.delete(bookmark);
            return;
        }

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));
        Document document = documentRepo.findById(documentId)
                .orElseThrow(() -> new SubjectNotFoundException(""));

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .document(document)
                .build();

        bookmarkRepo.save(bookmark);
    }

    @Override
    public boolean isBookmark(Integer documentId) {
        String email = SecurityUtils.getUserEmailFromSecurity();
        return bookmarkRepo.existsByUserEmailAndDocumentId(email, documentId);
    }

    @Override
    public PageResult<List<Bookmark>> getMyBookmarks(SearchRequest request) {
        String email = SecurityUtils.getUserEmailFromSecurity();
        Sort sort = SortHelper.buildSort(request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getPageSize(), sort);

        Page<Bookmark> page = bookmarkRepo.findAllBySearchAndUserEmail(request.getKeyword(), email, pageable);
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(page, request);
        return PageResult.<List<Bookmark>>builder()
                .metaDataResponse(metaDataResponse)
                .data(page.getContent())
                .build();
    }
}
