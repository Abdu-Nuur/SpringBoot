package com.peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@NoArgsConstructor
@Table(name = "tasks")
@Setter
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
    private Long id;

    @Column(length = 500)
    private String taskName;

    @Column(length = 500)
    private String taskText;

    @Column
    private LocalDate deadLine;

    public Task(String taskName, String taskText, LocalDate deadLine) {
        this.taskName = taskName;
        this.taskText = taskText;
        this.deadLine = deadLine;
    }

    @ManyToOne(cascade = {DETACH, REFRESH, MERGE, PERSIST}, fetch = EAGER)
    private Lesson lesson;
}
