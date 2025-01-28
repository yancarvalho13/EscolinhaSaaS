package com.escolinha.model.student;

import com.escolinha.dto.NotaFinalMateriaDto;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StudentNotaFinalTableModel extends AbstractTableModel {

    private final String[] columnNames = { "Matéria", "Nota Final", "Situação" };
    private final List<NotaFinalMateriaDto> notas;

    public StudentNotaFinalTableModel(List<NotaFinalMateriaDto> notas) {
        this.notas = notas;
    }

    @Override
    public int getRowCount() {
        return notas.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        NotaFinalMateriaDto nota = notas.get(rowIndex);
        return switch (columnIndex) {
           case 0 -> nota.nomeMateria();
           case 1 -> nota.notaFinal();
            case 2 -> {
                if(nota.notaFinal() >= 6){
                   yield "Aprovado";
                }else{
                    yield  "Reprovado";
                }
            }

            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
