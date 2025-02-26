package com.escolinha.view.student;

import com.escolinha.dto.BoletimFinalDto;
import com.escolinha.dto.NotaFinalMateriaDto;
import com.escolinha.dto.NotasDTO;
import com.escolinha.infra.ServiceProvider;
import com.escolinha.model.student.Student;
import com.escolinha.model.student.StudentNotaFinalTableModel;
import com.escolinha.service.BoletimFinalService;
import com.escolinha.service.BoletimService;
import com.escolinha.service.StudentService;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaAlunoNotas extends JFrame{
    private ServiceProvider serviceProvider;
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
    private JButton boletimFinalButton;
    private JButton gerarBoletimFinalButton;


    public TelaAlunoNotas(ServiceProvider serviceProvider, Long id) {
        this.serviceProvider = serviceProvider;
        this.id = id;
        initializeScreen();

        handleEventListeners(serviceProvider);

    }

    private void handleEventListeners(ServiceProvider serviceProvider) {
        boletimFinalButton.addActionListener(e ->{
            updateTableFinal();
        });

        comboBox1.addActionListener(e -> {
            int unidade = comboBox1.getSelectedIndex();
            updateTable(serviceProvider.getBoletimService(), unidade+1);
        });

        gerarBoletimFinalButton.addActionListener(e -> {
           generateFinalBoletim();
        });
    }

    private void initializeScreen() {
        setContentPane(panelAlunoNotas);
        setTitle("Aluno");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1024, 768);
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
       Student student =  serviceProvider.getStudentService().findStudent(this.id);
       studentName.setText(student.getName());
       studentCpf.setText(String.valueOf(student.getCpf()));
       studentBornDate.setText(String.valueOf(student.getBornDate()));
       studentFather.setText(student.getFather());
       studentMother.setText(student.getMother());
    }

    private void updateTable(BoletimService boletimService,int unidade) {
        List<NotasDTO> notasDTOS = boletimService.findNotasDtoById(this.id);
        TelaBoletim.setTableModel(unidade, notasDTOS, table1);

    }
    private void updateTableFinal() {
        BoletimFinalDto boletim = serviceProvider.getBoletimFinalService().buscarBoletimFinal(id, 2025);

        if(boletim != null){
            List<NotaFinalMateriaDto> notas = boletim.notas();
            setTableModel(notas);
        }else{
            JOptionPane.showMessageDialog(this, "Nenhum boletim encontrado para este aluno.", "Aviso", JOptionPane.WARNING_MESSAGE);

        }

    }

    private void setTableModel(List<NotaFinalMateriaDto> notas) {
        StudentNotaFinalTableModel studentNotaFinalTableModel = new StudentNotaFinalTableModel(notas);
        table1.setModel(studentNotaFinalTableModel);
        table1.setVisible(true);

        JTableHeader header = table1.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(new Font("Arial", Font.BOLD, 20));
    }

    private void generateFinalBoletim(){
        try {
            BoletimFinalDto boletim = serviceProvider.getBoletimFinalService().buscarBoletimFinal(id, 2025);

            if(boletim == null) {
                serviceProvider.getBoletimFinalService().gerarBoletim(id, 2025);
                JOptionPane.showMessageDialog(this, "Boletim gerado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Boletim já existe para este aluno!", "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            updateTableFinal();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
