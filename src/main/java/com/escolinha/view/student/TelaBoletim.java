package com.escolinha.view.student;

import com.escolinha.dto.NotasDTO;
import com.escolinha.model.student.Student;
import com.escolinha.model.student.StudentNotaTableModel;
import com.escolinha.repository.StudentRepository;
import com.escolinha.service.BoletimService;
import com.escolinha.service.StudentService;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class TelaBoletim extends JFrame {
    private JPanel panel1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox AlunosComboBox;
    private JButton salvarNotaButton;
    private JTable table1;
    private StudentService studentService;
    private StudentRepository studentRepository;

    public TelaBoletim() {
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(1024, 768));
        setVisible(true);
        setLocationRelativeTo(null);

        getAlunos();


    }

    private void getAlunos() {

        studentService = new StudentService(studentRepository);
        List<Student> studentList = studentService.listAllStudents();
        for(Student student : studentList) {
            AlunosComboBox.addItem(student.getName());
        }
    }

    private void updateTable(BoletimService boletimService, int unidade,Long id) {
        List<NotasDTO> notasDTOS = boletimService.findNotasDtoById(id);
        StudentNotaTableModel studentNotaTableModel = new StudentNotaTableModel(unidade,notasDTOS);
        table1.setModel(studentNotaTableModel);
        table1.setVisible(true);
        JTableHeader header = table1.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(new Font("Arial", Font.BOLD, 20));

    }
}
