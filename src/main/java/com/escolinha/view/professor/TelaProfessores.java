package com.escolinha.view.professor;

import com.escolinha.model.professor.Professor;
import com.escolinha.model.professor.ProfessorTableModel;
import com.escolinha.service.ProfessorService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class TelaProfessores extends JFrame{
    private JFrame frame;
    private JButton cadastrarButton;
    private JButton atualizarButton;
    private JButton deletarButton;
    private JTextField salarioTextField;
    private JTextField turmaTextField;
    private JTextField bornDateTextField;
    private JTextField cpfTextField;
    private JButton voltarButton;
    private JPanel panelProfessor;
    private JTextField nameTextField;
    private JTable table1;
    private final ProfessorService professorService;

    public TelaProfessores(ProfessorService professorService){
        this.professorService = professorService;
        setContentPane(panelProfessor);
        setTitle("Gerenciamento de Professores");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        updateTable(professorService);

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long id = Long.valueOf(table1.getValueAt(table1.getSelectedRow(), 0).toString());
                updateButton(id);
            }
        });

        //Seleção de usuário na tabela
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table1.getSelectedRow();
                int selectedColumn = table1.getSelectedColumn();
                if(selectedRow != -1){
                    String name = table1.getValueAt(selectedRow, 1).toString();
                    String cpf =   table1.getValueAt(selectedRow, 2).toString();
                    String bornDate =   table1.getValueAt(selectedRow, 3).toString();
                    String salary =   table1.getValueAt(selectedRow, 4).toString();

                    nameTextField.setText(name);
                    cpfTextField.setText(cpf);
                    bornDateTextField.setText(bornDate);
                    salarioTextField.setText(salary);

                    deletarButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            deleteButton(name);
                            updateTable(professorService);
                        }
                    });
                }
            }
        });

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                saveButton();
                updateTable(professorService);
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void deleteButton(String name){
        professorService.deleteByName(name);
    }

    private void updateTable(ProfessorService professorService) {
        List<Professor> professorList = professorService.listAllProfessors();
        ProfessorTableModel tableProfessors = new ProfessorTableModel(professorList);
        table1.setModel(tableProfessors);
        table1.setVisible(true);
        JTableHeader header = table1.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        System.out.println(professorList);
    }

    private void updateButton(Long id){
        String name = nameTextField.getText();
        Long cpf = Long.valueOf(cpfTextField.getText());
        LocalDate bornDate = dateTextFieldFormatter();
        BigDecimal salary = new BigDecimal(salarioTextField.getText());

        professorService.updateProfessor(id, name, cpf, bornDate, salary);
        updateTable(professorService);
    }

    private void saveButton(){
        try {
            LocalDate bornDate = dateTextFieldFormatter();
            BigDecimal salary;

            Professor professor = professorService.createProfessor(
                    nameTextField.getText(),
                    cpfTextField.getText(),
                    bornDate,
                    salary = new BigDecimal(salarioTextField.getText())
            );
            professorService.saveProfessor(professor);
            JOptionPane.showMessageDialog(frame,"Professor Saved !!");
        } catch (Exception e) {
            throw new RuntimeException(e+ ": Professor not Saved !!");
        }

    }

    private LocalDate dateTextFieldFormatter() {
        String bornDateString = bornDateTextField.getText();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate bornDate = LocalDate.parse(bornDateString, dateTimeFormatter);
        return bornDate;
    }

}
