package com.escolinha.dto;

import com.escolinha.model.classroom.Unidade;

import java.util.List;

public record NotasDTO(String materia, double nota1, double nota2, double media, int unidade) {

    public NotasDTO(String materia, double nota1, double nota2, int unidade) {
        this(materia, nota1, nota2, (nota1 + nota2) / 2, unidade);
    }
}