package com.escolinha.model.professor;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ProfessorTableModel extends AbstractTableModel {
    private final List<Professor> professorList;
    private final String[] columnNames = {"Id","Nome", "Cpf", "Nascimento", "Salario"};

    public ProfessorTableModel(List<Professor> professorList){
        this.professorList = professorList;
    }


    @Override
    public int getRowCount() {
        return professorList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       Professor professor = professorList.get(rowIndex);
       return switch (columnIndex){
           case 0 -> professor.getIdProfessor();
           case 1 -> professor.getName();
           case 2 -> professor.getCpf();
           case 3 -> professor.getBornDate();
           case 4 -> professor.getSalary();
           default -> null;
       };
    }
}
