package com.peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Table(name = "groups")
@NoArgsConstructor
public class Group {

    @Id
    @SequenceGenerator(name = "group_seq", sequenceName = "group_seq", allocationSize = 1)
    @GeneratedValue(generator = "group_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 500)
    private String groupName;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataOfStart;

    @Column(length = 500)
    private String image;

    @ManyToMany(cascade = {MERGE ,REFRESH, DETACH, PERSIST}, mappedBy = "groups")
    private List<Course> courses;

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "group")
    private List<Student> students;

    public Group(String groupName, LocalDate dataOfStart, String image) {
        this.groupName = groupName;
        this.dataOfStart = dataOfStart;
        this.image = image;
    }

    public void addCourse(Course course) {
        if (courses == null) courses = new ArrayList<>();
        courses.add(course);
    }

    public void addStudent(Student student) {
        if (students == null) students = new ArrayList<>();
        students.add(student);
    }

}
