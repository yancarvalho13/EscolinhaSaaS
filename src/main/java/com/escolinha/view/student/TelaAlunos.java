package com.escolinha.view.student;

import com.escolinha.model.student.Student;
import com.escolinha.model.student.StudentTableModel;
import com.escolinha.service.StudentService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class TelaAlunos extends JFrame {
    private JFrame frame;
    private JButton cadastrarButton;
    private JButton atualizarButton;
    private JButton deletarButton;
    private JButton voltarButton;
    private JTextField nomeTextField;
    private JTextField cpfTextField;
    private JTextField dataDeNascimentoTextField;
    private JTextField nomeDaMaeTextField;
    private JTextField nomeDoPaiTextField;
    private JTextField turmaTextField;
    private JPanel panelAluno;
    private JTable table1;
    private JScrollPane pane1;
    private JComboBox comboBox1;
    private final StudentService studentService;


    public TelaAlunos(StudentService studentService) {
        this.studentService = studentService;
        setContentPane(panelAluno);
        setTitle("Gerenciamento de Alunos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        updateTable(studentService);


        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long id = Long.valueOf(table1.getValueAt(table1.getSelectedRow(), 0).toString());
                String name = nomeTextField.getText();
                Long cpf = Long.valueOf(cpfTextField.getText());
                LocalDate bornDate = dateTextFieldFormatter();
                String father = nomeDoPaiTextField.getText();
                String mother = nomeDaMaeTextField.getText();

                studentService.updateStudent(id,
                        name,
                        cpf,
                        bornDate,
                        father,
                        mother);

                updateTable(studentService);
            }
        });

        //Seleção de usuário na tabela
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table1.getSelectedRow();
                int selectedColumn = table1.getSelectedColumn();
                if (selectedRow != -1) {
                    Long id = Long.valueOf(table1.getValueAt(selectedRow, 0).toString());
                    String name = table1.getValueAt(selectedRow, 1).toString();
                    String cpf = table1.getValueAt(selectedRow, 2).toString();
                    String bornDate = table1.getValueAt(selectedRow, 3).toString();
                    String fatherName = table1.getValueAt(selectedRow, 4).toString();
                    String motherName = table1.getValueAt(selectedRow, 5).toString();

                    nomeTextField.setText(name);
                    cpfTextField.setText(cpf);
                    dataDeNascimentoTextField.setText(bornDate);
                    nomeDaMaeTextField.setText(motherName);
                    nomeDoPaiTextField.setText(fatherName);

                    deletarButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            deleteButton(name);
                            updateTable(studentService);
                        }
                    });

                    if(e.getClickCount() == 2){
                        TelaAlunoNotas telaAlunoNotas = new TelaAlunoNotas(studentService, id);
                    }

                }
            }
        });



        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveButton();
                updateTable(studentService);
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void updateButton(Long id) {
        String name = nomeTextField.getText();
        Long cpf = Long.valueOf(cpfTextField.getText());
        LocalDate bornDate = dateTextFieldFormatter();
        String father = nomeDoPaiTextField.getText();
        String mother = nomeDaMaeTextField.getText();

        studentService.updateStudent(id, name, cpf, bornDate, father, mother);
    }

    private void deleteButton(String name) {
        studentService.deleteByName(name);
    }

    private void updateTable(StudentService studentService) {
        List<Student> studentList = studentService.listAllStudents();
        StudentTableModel tableAlunos = new StudentTableModel(studentList);
        table1.setModel(tableAlunos);
        table1.setVisible(true);
        JTableHeader header = table1.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        System.out.println(studentList);
    }

    private void saveButton() {
        try {
            LocalDate bornDate = dateTextFieldFormatter();

            Student student = studentService.createStudent(nomeTextField.getText(),
                    cpfTextField.getText(), bornDate,
                    nomeDoPaiTextField.getText(), nomeDaMaeTextField.getText());

            studentService.saveStudent(student);
            JOptionPane.showMessageDialog(frame, "User saved !!");
        } catch (Exception e) {
            throw new RuntimeException(e + ": User not Saved !!");
        }


    }

    private LocalDate dateTextFieldFormatter() {
        String bornDateString = dataDeNascimentoTextField.getText();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate bornDate = LocalDate.parse(bornDateString, dateTimeFormatter);
        return bornDate;
    }

}
