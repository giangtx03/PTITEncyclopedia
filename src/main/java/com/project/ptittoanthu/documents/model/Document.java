package com.project.ptittoanthu.documents.model;

import com.project.ptittoanthu.common.base.entity.BaseEntity;
import com.project.ptittoanthu.subjects.model.Subject;
import com.project.ptittoanthu.users.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "documents")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Document extends BaseEntity {

    @Column(name = "title", length = 255, nullable = false)
    String title;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    DocumentType type;

    @Column(name = "file_path")
    String filePath;

    @OneToOne(mappedBy = "document", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    MetaData metaData;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    User owner;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    Subject subject;
}
