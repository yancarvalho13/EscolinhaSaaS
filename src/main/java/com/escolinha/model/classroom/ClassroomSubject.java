package com.escolinha.model.classroom;

import jakarta.persistence.*;

@Entity
@Table(name = "classroom_subject")
public class ClassroomSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;
}
