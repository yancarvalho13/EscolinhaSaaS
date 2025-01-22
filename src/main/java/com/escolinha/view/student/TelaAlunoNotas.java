package com.escolinha.view.student;

import com.escolinha.dto.NotasDTO;
import com.escolinha.model.student.Student;
import com.escolinha.model.student.StudentNotaTableModel;
import com.escolinha.model.student.StudentTableModel;
import com.escolinha.service.BoletimService;
import com.escolinha.service.StudentService;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaAlunoNotas extends JFrame{
    private Long id;
    private JTextField studentCpf;
    private JTextField studentBornDate;
    private JTextField studentFather;
    private JTextField studentMother;
    private JComboBox comboBox1;
    private JTable table1;
    private JPanel panelAlunoNotas;
    private JLabel Unidade;
    private JButton voltarButton;
    private JTextField studentName;
    private final StudentService studentService;
    private final BoletimService boletimService;



    public TelaAlunoNotas(StudentService studentService, Long id, BoletimService boletimService) {
        this.id = id;
        this.studentService = studentService;
        this.boletimService = boletimService;
        setContentPane(panelAlunoNotas);
        setTitle("Aluno");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
        getStudentInfo();
        exitButton();

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int unidade = comboBox1.getSelectedIndex();
                updateTable(boletimService, unidade+1);
            }
        });

    }


    private void exitButton(){
        voltarButton.setText("Voltar");
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void getStudentInfo(){
       Student student =  studentService.findStudent(this.id);
       studentName.setText(student.getName());
       studentCpf.setText(String.valueOf(student.getCpf()));
       studentBornDate.setText(String.valueOf(student.getBornDate()));
       studentFather.setText(student.getFather());
       studentMother.setText(student.getMother());
    }

    private void updateTable(BoletimService boletimService,int unidade) {
        List<NotasDTO> notasDTOS = boletimService.findNotasDtoById(this.id);
        StudentNotaTableModel studentNotaTableModel = new StudentNotaTableModel(unidade,notasDTOS);
        table1.setModel(studentNotaTableModel);
        table1.setVisible(true);
        JTableHeader header = table1.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        
    }
}
