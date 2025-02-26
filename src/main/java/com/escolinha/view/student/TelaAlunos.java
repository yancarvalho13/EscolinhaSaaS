package com.escolinha.view.student;

import com.escolinha.infra.ServiceProvider;
import com.escolinha.model.student.Student;
import com.escolinha.service.StudentService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.escolinha.view.home.HomePage.setTableModel;


public class TelaAlunos extends JFrame {

    private final ServiceProvider serviceProvider;
    private TelaAlunosCloseListener closeListener;
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


    public TelaAlunos(ServiceProvider serviceProvider, TelaAlunosCloseListener closeListener) {
        this.serviceProvider = serviceProvider;
        this.closeListener = closeListener;


        initializeScreen();

        windownCloseListener(closeListener);


        handleMouseListener(serviceProvider);
    }

    private void handleMouseListener(ServiceProvider serviceProvider) {
        atualizarButton.addActionListener(e -> handleUpdateButton(serviceProvider));


        cadastrarButton.addActionListener(e -> saveButton());

        voltarButton.addActionListener(e -> dispose());
    }

    private void handleUpdateButton(ServiceProvider serviceProvider) {
        Long id = Long.valueOf(table1.getValueAt(table1.getSelectedRow(), 0).toString());
        String name = nomeTextField.getText();
        Long cpf = Long.valueOf(cpfTextField.getText());
        LocalDate bornDate = formatDateToBR();
        String father = nomeDoPaiTextField.getText();
        String mother = nomeDaMaeTextField.getText();

        serviceProvider.getStudentService().updateStudent(id,
                name,
                cpf,
                bornDate,
                father,
                mother);

        updateTable(serviceProvider.getStudentService());
    }

    private void windownCloseListener(TelaAlunosCloseListener closeListener) {
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (closeListener != null) {
                    closeListener.onTelaAlunosClosed();
                }
            }
        });
    }

    private void initializeScreen() {
        setContentPane(panelAluno);
        setTitle("Gerenciamento de Alunos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(1024, 400));
        panelAluno.setFont(new Font("Tahoma", Font.PLAIN, 24));
        setLocationRelativeTo(null);
    }


    public void updateTable(StudentService studentService) {
        List<Student> studentList = studentService.listAllStudents();
        setTableModel(studentList, studentService, table1);
    }

    private void saveButton() {
        saveStudentForm();
    }

    private void saveStudentForm() {
        try {
            LocalDate bornDate = formatDateToBR();

            Student student = createNewStudent(bornDate);

            serviceProvider.getStudentService().saveStudent(student);
            JOptionPane.showMessageDialog(frame, "Usuário salvo com sucesso!");
            closeListener.onTelaAlunosClosed(); // Chama o listener para atualizar a tabela
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Erro ao salvar usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Student createNewStudent(LocalDate bornDate) {
        return serviceProvider.getStudentService().createStudent(
                nomeTextField.getText(),
                cpfTextField.getText(),
                bornDate,
                nomeDoPaiTextField.getText(),
                nomeDaMaeTextField.getText()
        );
    }

    private LocalDate formatDateToBR() {
        String bornDateString = dataDeNascimentoTextField.getText();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate bornDate = LocalDate.parse(bornDateString, dateTimeFormatter);
        return bornDate;
    }

    public interface TelaAlunosCloseListener {
        void onTelaAlunosClosed();
    }

}
