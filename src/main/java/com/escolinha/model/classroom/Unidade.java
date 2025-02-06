package com.escolinha.model.classroom;

import com.escolinha.model.student.Student;
import jakarta.persistence.*;
import lombok.Getter;

    @Entity
    @Getter
    public class Unidade {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idUnidade;

        @ManyToOne
        @JoinColumn(name = "idStudent", nullable = false)
        private Student student;

        @ManyToOne
        @JoinColumn(name = "materia_id", nullable = false)
        private Materia materia;

        private int posicaoUnidade; // Ex: 1 para 1ª unidade
        private double nota1;
        private double nota2;

        @Transient
        public double getNotaFinal() {
            return (nota1 + nota2) / 2;
        }
    }

