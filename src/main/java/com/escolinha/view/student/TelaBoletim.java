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
    private JTextField prova1TextField;
    private JTextField prova2TextField;
    private JComboBox alunosComboBox;
    private JButton salvarNotaButton;
    private JTable table1;
    private StudentService studentService;
    private BoletimService boletimService;
    private BoletimFinalService boletimFinalService;
    private MateriaRepository materiaRepository;
    private MateriaService materiaService;
    private UnidadeService unidadeService;
    private Long selectedId;


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
        salvarNota();


    }

    private void salvarNota() {
        salvarNotaButton.addActionListener(a ->{
            String materiaName = materiaComboBox.getSelectedItem().toString();
            String studentName = alunosComboBox.getSelectedItem().toString();
            int posicaoUnidade = unidadeComboBox.getSelectedIndex()+1;
            Double prova1 = Double.parseDouble(prova1TextField.getText());
            Double prova2 = Double.parseDouble(prova2TextField.getText());
            Student student = studentService.findStudentByName(studentName);
            Materia materia = materiaService.findBynome(materiaName);
            System.out.println(materia.getIdMateria());
            Unidade unidade = new Unidade(student, materia, posicaoUnidade, prova1, prova2);
            unidadeService.save(unidade);
        });


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
        alunosComboBox.addActionListener(e -> {
           String name = alunosComboBox.getSelectedItem().toString();
           var student  = studentService.findStudentByName(name);
           this.selectedId = student.getIdStudent(); ///Não rodar debug nessa linha, Trava o computador
            System.out.println(selectedId);
        });
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
