package com.project.ptittoanthu.documents.dto.request;

import com.project.ptittoanthu.documents.model.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateDocumentRequest {
    @NotBlank
    String title;
    @NotNull
    DocumentType type;
    @NotNull
    MultipartFile file;
    @NotNull
    Integer subjectId;
    String author;
    LocalDate publishDate;
}
