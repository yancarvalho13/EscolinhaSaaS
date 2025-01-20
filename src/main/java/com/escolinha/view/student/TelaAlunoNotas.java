package com.escolinha.view.student;

import com.escolinha.model.student.Student;
import com.escolinha.service.StudentService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public TelaAlunoNotas(StudentService studentService, Long id){
        this.id = id;
        this.studentService = studentService;
        setContentPane(panelAlunoNotas);
        setTitle("Aluno");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);

        getStudentInfo();

        exitButton();

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
}
