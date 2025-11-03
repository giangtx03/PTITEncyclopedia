package com.project.ptittoanthu.documents.dto.request;

import com.project.ptittoanthu.documents.model.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateDocumentRequest {
    @NotNull
    private Integer id;
    @NotBlank
    private String title;
    @NotNull
    private DocumentType type;
    @NotNull
    private MultipartFile file;
    @NotBlank
    private String author;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate publishTime;
}
