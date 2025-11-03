package com.project.ptittoanthu.documents.dto.response;

import com.project.ptittoanthu.subjects.dto.response.SubjectResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DocumentResponseDetail extends DocumentResponse {
    SubjectResponse subject;
    String author;
    LocalDate publishTime;
}
