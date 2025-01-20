package com.escolinha.model.classroom;

import com.escolinha.model.student.Student;
import jakarta.persistence.*;

import java.util.Map;

@Entity
public class BoletimAnual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBoletimAnual;

    @OneToOne
    @JoinColumn(name = "idStudent", nullable = false)
    private Student student;

    private int ano;

    @ElementCollection
    @CollectionTable(name = "nota_final", joinColumns = @JoinColumn(name = "boletim_anual_id"))
    @MapKeyJoinColumn(name = "materia_id")
    @Column(name = "nota_final")
    private Map<Materia, Double> notasFinais;
}