package com.escolinha.model.student;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StudentTableModel extends AbstractTableModel {
    private final List<Student> studentList;
    private final String[] columnNames  = {"Id","Nome", "Cpf", "Nascimento", "Pai", "Mae"};

    public StudentTableModel(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public int getRowCount() {
        return studentList.size();
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
       Student student = studentList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> student.getIdStudent();
            case 1 -> student.getName();
            case 2 -> student.getCpf();
            case 3 -> student.getBornDate();
            case 4 -> student.getFather();
            case 5 -> student.getMother();
            default -> null;
        };
    }
}
