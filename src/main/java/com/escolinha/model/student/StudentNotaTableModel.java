package com.escolinha.model.student;

import com.escolinha.dto.NotasDTO;
import com.escolinha.model.classroom.Materia;
import com.escolinha.model.classroom.Unidade;
import com.escolinha.repository.BoletimRepository;
import com.escolinha.service.BoletimService;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.stream.Collectors;

public class StudentNotaTableModel extends AbstractTableModel {

    private final int unidade;
    private List<NotasDTO> notasDTOList;
    private final String[] columnNames = {"Unidade","Disciplina", "Prova 1", "Prova 2","Media Final"};

    public StudentNotaTableModel(int unidade, List<NotasDTO> notasDTOList) {
        this.unidade = unidade;

        this.notasDTOList = notasDTOList.stream().filter(nota -> nota.unidade() == unidade).collect(Collectors.toList());
    }

    @Override
    public int getRowCount() {
        return notasDTOList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        NotasDTO notasDTO = notasDTOList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> notasDTO.unidade();
            case 1 -> notasDTO.materia();
            case 2 -> notasDTO.nota1();
            case 3 -> notasDTO.nota2();
            case 4 -> notasDTO.media();
            default -> null;
        };

    }




}
