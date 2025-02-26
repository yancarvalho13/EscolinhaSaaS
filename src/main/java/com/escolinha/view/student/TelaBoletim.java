package com.escolinha.view.student;

import com.escolinha.dto.NotasDTO;
import com.escolinha.infra.ServiceProvider;
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
    private JComboBox selecionarUnidadeComboBox;
    private JButton voltarButton;
    private Long selectedId;
    private ServiceProvider serviceProvider;


    public TelaBoletim(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;

        initializeScreen(serviceProvider);
        handleEventListeners(serviceProvider);

    }

    private void handleEventListeners(ServiceProvider serviceProvider) {
        voltarButton.addActionListener(e -> {
            dispose();
        });


        selecionarUnidadeComboBox.addActionListener(e -> {

            updateTable(serviceProvider.getBoletimService());
        });
    }

    private void initializeScreen(ServiceProvider serviceProvider) {
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(1024, 768));
        setVisible(true);
        setLocationRelativeTo(null);
        updateTable(serviceProvider.getBoletimService());

        updateTable(serviceProvider.getBoletimService());
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
            Student student = serviceProvider.getStudentService().findStudentByName(studentName);
            Materia materia = serviceProvider.getMateriaService().findBynome(materiaName);
            System.out.println(materia.getIdMateria());
            Unidade unidade = new Unidade(student, materia, posicaoUnidade, prova1, prova2);
            serviceProvider.getUnidadeService().save(unidade);
            updateTable(serviceProvider.getBoletimService());

        });


    }

    private void getMateriasComboBox() {
        List<Materia> materiaList = serviceProvider.getMateriaService().findAll();
        for (Materia materia : materiaList) {
            materiaComboBox.addItem(makeObj(materia.getNome()));
        }
    }

    private void getAlunosComboBox() {
        List<Student> studentList = serviceProvider.getStudentService().listAllStudents();
        for(Student student : studentList) {
            alunosComboBox.addItem(makeObj(student.getName()));
        }
        alunosComboBox.addActionListener(e -> {
           String name = alunosComboBox.getSelectedItem().toString();
           var student  = serviceProvider.getStudentService().findStudentByName(name);
            updateTable(serviceProvider.getBoletimService());

        });
    }
    private Object makeObj(final String item)  {
        return new Object() { public String toString() { return item; } };
    }

    private void updateTable(BoletimService boletimService) {
        int unidadeSelecionada = selecionarUnidadeComboBox.getSelectedIndex()+1;
        List<NotasDTO> notasDTOS = boletimService.findNotasDtoById(selectedId);
        setTableModel(unidadeSelecionada, notasDTOS, table1);


    }

    static void setTableModel(int unidadeSelecionada, List<NotasDTO> notasDTOS, JTable table1) {
        StudentNotaTableModel studentNotaTableModel = new StudentNotaTableModel(unidadeSelecionada,notasDTOS);
        table1.setModel(studentNotaTableModel);
        table1.setVisible(true);
        JTableHeader header = table1.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(new Font("Arial", Font.BOLD, 20));
    }
}
