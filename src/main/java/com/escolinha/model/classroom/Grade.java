package com.escolinha.model.classroom;

import com.escolinha.model.student.Student;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrade;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Unit unit;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal grade;

    public enum Unit {
        PRIMEIRA_UNIDADE,
        SEGUNDA_UNIDADE,
        TERCEIRA_UNIDADE,
        QUARTA_UNIDADE
    }
}
