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

        public Unidade(Student student, Materia materia, int posicaoUnidade, double nota1, double nota2) {
            this.student = student;
            this.materia = materia;
            this.posicaoUnidade = posicaoUnidade;
            this.nota1 = nota1;
            this.nota2 = nota2;
        }

        public Unidade() {

        }



        @Transient
        public double getNotaFinal() {
            return (nota1 + nota2) / 2;
        }

    }

