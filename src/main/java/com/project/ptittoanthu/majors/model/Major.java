package com.project.ptittoanthu.majors.model;

import com.project.ptittoanthu.common.base.entity.BaseEntity;
import com.project.ptittoanthu.faculties.model.Faculty;
import com.project.ptittoanthu.subjects.model.Subject;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "majors")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Major extends BaseEntity {

    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "code", length = 64, nullable = false, unique = true)
    String code;

    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    Faculty faculty;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "major", fetch = FetchType.LAZY)
    List<Subject> subjects;
}
