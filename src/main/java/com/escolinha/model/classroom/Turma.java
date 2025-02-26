package com.escolinha.model.classroom;

import com.escolinha.model.professor.Professor;
import com.escolinha.model.student.Student;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date date;

    @OneToMany(mappedBy = "turma")
    private List<Student> students = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

}
