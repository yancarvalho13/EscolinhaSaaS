package com.escolinha.model.classroom;

import com.escolinha.model.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.N;

import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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