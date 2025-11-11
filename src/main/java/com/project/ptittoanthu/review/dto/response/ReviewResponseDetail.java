package com.project.ptittoanthu.review.dto.response;

import com.project.ptittoanthu.documents.dto.response.DocumentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ReviewResponseDetail extends ReviewResponse{
    private DocumentResponse document;
}
