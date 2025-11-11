package com.project.ptittoanthu.review.mapper;

import com.project.ptittoanthu.documents.mapper.DocumentMapper;
import com.project.ptittoanthu.review.dto.request.CreateReviewRequest;
import com.project.ptittoanthu.review.dto.request.UpdateReviewRequest;
import com.project.ptittoanthu.review.dto.response.ReviewResponse;
import com.project.ptittoanthu.review.dto.response.ReviewResponseDetail;
import com.project.ptittoanthu.review.model.Review;
import com.project.ptittoanthu.users.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, DocumentMapper.class})
public interface ReviewMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "document", ignore = true)
    Review toReview(CreateReviewRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "document", ignore = true)
    void updateReview(UpdateReviewRequest request, @MappingTarget Review review);

    ReviewResponse toReviewResponse(Review review);

    ReviewResponseDetail toReviewResponseDetail(Review review);

    List<ReviewResponse> toReviewResponse(List<Review> content);
}
