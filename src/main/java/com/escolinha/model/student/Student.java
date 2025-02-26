package com.escolinha.model.student;

import com.escolinha.model.classroom.BoletimAnual;
import com.escolinha.model.classroom.Turma;
import com.escolinha.model.classroom.Unidade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStudent;

    private String name;
    private Long cpf;
    private LocalDate bornDate;
    private String father;
    private String mother;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unidade> unidades;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private BoletimAnual boletimAnual;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;


}
