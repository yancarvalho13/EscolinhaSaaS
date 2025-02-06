package com.escolinha.view.student;

import com.escolinha.dto.NotasDTO;
import com.escolinha.model.classroom.Materia;
import com.escolinha.model.classroom.Unidade;
import com.escolinha.model.student.Student;
import com.escolinha.model.student.StudentNotaTableModel;
import com.escolinha.repository.MateriaRepository;
import com.escolinha.service.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class TelaBoletim extends JFrame {
    private JPanel panel1;
    private JTextField textField2;
    private JComboBox materiaComboBox;
    private JComboBox unidadeComboBox;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox alunosComboBox;
    private JButton salvarNotaButton;
    private JTable table1;
    private StudentService studentService;
    private BoletimService boletimService;
    private BoletimFinalService boletimFinalService;
    private MateriaRepository materiaRepository;
    private MateriaService materiaService;
    private UnidadeService unidadeService;


    public TelaBoletim(StudentService studentService, BoletimService boletimService, BoletimFinalService boletimFinalService, MateriaService materiaService, UnidadeService unidadeService) {
        this.materiaService = materiaService;
        this.studentService = studentService;
        this.boletimService = boletimService;
        this.boletimFinalService = boletimFinalService;
        this.unidadeService = unidadeService;

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(1024, 768));
        setVisible(true);
        setLocationRelativeTo(null);

        getAlunosComboBox();
        getMateriasComboBox();



    }


    private void getMateriasComboBox() {
        List<Materia> materiaList = materiaService.findAll();
        for (Materia materia : materiaList) {
            materiaComboBox.addItem(makeObj(materia.getNome()));
        }
    }

    private void getAlunosComboBox() {
        List<Student> studentList = studentService.listAllStudents();
        for(Student student : studentList) {
            alunosComboBox.addItem(makeObj(student.getName()));
        }
    }
    private Object makeObj(final String item)  {
        return new Object() { public String toString() { return item; } };
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
