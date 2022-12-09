package com.peaksoft.entity;

import com.peaksoft.entity.enums.Specialization;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@Table(name = "instructors")
@NoArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "instructor_seq")
    @SequenceGenerator(name = "instructor_seq", sequenceName = "instructor_seq", allocationSize = 1)
    private Long id;

    @Column(length = 500)
    private String firstname;

    @Column(length = 500)
    private String lastname;

    @Column(length = 500)
    private String phoneNumber;

    @Column(length = 500)
    private String email;

    @Column
    private Specialization specialization;

    @Column
    private int student = 0;

    public Instructor(String firstname, String lastname, String phoneNumber, String email, Specialization specialization) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.specialization = specialization;
    }

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = EAGER)
    private Course course;

    public void plus() {
        student++;
    }
    public void minus() {
        student--;
    }

}
