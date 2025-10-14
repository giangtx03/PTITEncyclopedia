package com.project.ptittoanthu.faculties.model;

import com.project.ptittoanthu.common.base.entity.BaseEntity;
import com.project.ptittoanthu.majors.model.Major;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "faculties")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Faculty extends BaseEntity {

    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "code", length = 64, nullable = false, unique = true)
    String code;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "faculty", fetch = FetchType.LAZY)
    List<Major> majors;
}
