package com.project.ptittoanthu.review.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.helper.SortHelper;
import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.documents.exception.DocumentNotFoundExp;
import com.project.ptittoanthu.documents.model.Document;
import com.project.ptittoanthu.documents.repository.DocumentRepository;
import com.project.ptittoanthu.notify.dto.CreateNotificationRequest;
import com.project.ptittoanthu.notify.model.NotificationType;
import com.project.ptittoanthu.notify.service.NotificationService;
import com.project.ptittoanthu.review.dto.ReviewSearchRequest;
import com.project.ptittoanthu.review.dto.request.CreateReviewRequest;
import com.project.ptittoanthu.review.dto.request.UpdateReviewRequest;
import com.project.ptittoanthu.review.dto.response.ReviewResponse;
import com.project.ptittoanthu.review.dto.response.ReviewResponseDetail;
import com.project.ptittoanthu.review.exception.ReviewNotFoundExp;
import com.project.ptittoanthu.review.mapper.ReviewMapper;
import com.project.ptittoanthu.review.model.Review;
import com.project.ptittoanthu.review.repository.ReviewRepository;
import com.project.ptittoanthu.review.service.ReviewService;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final ReviewMapper reviewMapper;
    private final NotificationService notificationService;

    @Override
    public ReviewResponseDetail createReview(CreateReviewRequest request) {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(""));

        Document document = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new DocumentNotFoundExp(""));

        Review review = reviewMapper.toReview(request);
        review.setUser(user);
        review.setDocument(document);
        reviewRepository.save(review);
        sendNotification(document.getOwner(),"Đánh giá tài liệu", review.getContent(), NotificationType.REVIEW_DOCUMENT, document.getId());
        return reviewMapper.toReviewResponseDetail(review);
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

    @Override
    public ReviewResponseDetail updateReview(UpdateReviewRequest request) {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        Review review = reviewRepository.findByIdWithUserEmail(request.getId(), userEmail)
                        .orElseThrow(() -> new ReviewNotFoundExp(""));
        reviewMapper.updateReview(request, review);
        reviewRepository.save(review);
        return reviewMapper.toReviewResponseDetail(review);
    }

    @Override
    public PageResult<List<ReviewResponse>> getReviews(ReviewSearchRequest request) {
        Sort sort = SortHelper.buildSort(request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize(), sort);

        Page<Review> page = reviewRepository.findAllBySearch(
                request.getKeyword(),
                request.getStar(),
                request.getDocumentId(), pageable);
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(page, request);
        List<ReviewResponse> responses = reviewMapper.toReviewResponse(page.getContent());
        return PageResult.<List<ReviewResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(responses)
                .build();
    }

    @Override
    public void delete(Integer id) {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        Review review = reviewRepository.findByIdWithUserEmail(id, userEmail)
                .orElseThrow(() -> new ReviewNotFoundExp(""));
        review.setDeletedAt(LocalDateTime.now());
        reviewRepository.save(review);
    }
}
