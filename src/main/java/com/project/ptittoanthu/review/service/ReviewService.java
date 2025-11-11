package com.project.ptittoanthu.review.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.review.dto.ReviewSearchRequest;
import com.project.ptittoanthu.review.dto.request.CreateReviewRequest;
import com.project.ptittoanthu.review.dto.request.UpdateReviewRequest;
import com.project.ptittoanthu.review.dto.response.ReviewResponse;
import com.project.ptittoanthu.review.dto.response.ReviewResponseDetail;

import java.util.List;

public interface ReviewService {
    ReviewResponseDetail createReview(CreateReviewRequest request);
    ReviewResponseDetail updateReview(UpdateReviewRequest request);
    PageResult<List<ReviewResponse>> getReviews(ReviewSearchRequest request);
    void delete(Integer id);
}
