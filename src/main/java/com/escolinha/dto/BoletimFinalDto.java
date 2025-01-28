package com.escolinha.dto;

import java.util.List;

public record BoletimFinalDto(Long idStudent, Integer ano, List<NotaFinalMateriaDto> notas) {
    @Override
    public Long idStudent() {
        return idStudent;
    }

    @Override
    public Integer ano() {
        return ano;
    }

    @Override
    public List<NotaFinalMateriaDto> notas() {
        return notas;
    }
}
