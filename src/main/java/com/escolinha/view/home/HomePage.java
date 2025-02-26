package com.escolinha.view.home;

import com.escolinha.infra.ServiceProvider;
import com.escolinha.model.student.Student;
import com.escolinha.model.student.StudentTableModel;
import com.escolinha.service.*;
import com.escolinha.view.student.TelaAlunoNotas;
import com.escolinha.view.student.TelaAlunos;
import com.escolinha.view.student.TelaBoletim;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Component
public class HomePage extends JFrame {

    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JButton sairButton;
    private JPanel alunosPanel;
    private JPanel professoresPanel;
    private JScrollPane alunosScrollPane;
    private JTable table1;
    private JButton cadastrarButton;
    private JButton deletarButton;
    private JPanel header;
    private JButton cadastrarNotasButton;
    private JPanel secretariaPanel;
    private JTable table2;
    private JButton button1;
    private JButton cadastrarTurmaButton;
    private JLabel logoSchool;
    private JLabel nomeEscolinha;
    private final ServiceProvider serviceProvider;


    public HomePage(ServiceProvider serviceProvider) throws Exception {
        this.serviceProvider = serviceProvider;

        initializeScreen(serviceProvider);

        handleMouseInteractions(serviceProvider);

    }

    private void handleMouseInteractions(ServiceProvider serviceProvider) {

        TableMouseInteractions(serviceProvider);

        cadastrarNotasMouseInteraction(serviceProvider);


        cadastrarAlunoMouseInteraction(serviceProvider);

        closeApplicationMouseInteraction();
    }

    private void closeApplicationMouseInteraction() {
        sairButton.addActionListener(e -> {
            dispose();
            System.exit(0);
        });
    }

    private void cadastrarAlunoMouseInteraction(ServiceProvider serviceProvider) {
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAlunosScreen(serviceProvider);
            }
        });
    }

    private void cadastrarNotasMouseInteraction(ServiceProvider serviceProvider) {
        cadastrarNotasButton.addActionListener(f -> {
            openBoletimScreen(serviceProvider);
        });
    }

    private void TableMouseInteractions(ServiceProvider serviceProvider) {
        table1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                handleTableMouseInteraction(e, serviceProvider);
            }
        });
    }

    private void handleTableMouseInteraction(MouseEvent e, ServiceProvider serviceProvider) {
        int selectedRow = table1.getSelectedRow();
        int selectedColumn = table1.getSelectedColumn();
        if (selectedRow != -1 && selectedColumn != -1) {
            Long id = Long.valueOf(table1.getValueAt(selectedRow, 0).toString());
            String name = table1.getValueAt(selectedRow, 1).toString();


            handleDeleteStudentButton(name, serviceProvider);

            if (e.getClickCount() == 2) {
                openTelaAlunosNotasScreen(id, serviceProvider);
            }
        }
    }

    private static void openTelaAlunosNotasScreen(Long id, ServiceProvider serviceProvider) {
        TelaAlunoNotas telaAlunoNotas = new TelaAlunoNotas(serviceProvider, id);
    }

    private void handleDeleteStudentButton(String name, ServiceProvider serviceProvider) {
        deletarButton.addActionListener(f -> {
            deleteStudent(name);
            updateTable(serviceProvider.getStudentService());
        });
    }

    private void openAlunosScreen(ServiceProvider serviceProvider) {
        TelaAlunos telaAlunos = new TelaAlunos(
                serviceProvider,
                HomePage.this::updateTablehere  // Passa o método de atualização diretamente
        );
        telaAlunos.setVisible(true);
    }

    private static void openBoletimScreen(ServiceProvider serviceProvider) {
        TelaBoletim telaBoletim = new TelaBoletim(serviceProvider);
        telaBoletim.setVisible(true);
    }

    private void initializeScreen(ServiceProvider serviceProvider) {
        setContentPane(panel1);
        setTheme();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1024, 768));
        setLocationRelativeTo(null);
        updateTable(serviceProvider.getStudentService());
    }

    private void updateTablehere() {
        List<Student> studentList = serviceProvider.getStudentService().listAllStudents();
        StudentTableModel tableAlunos = new StudentTableModel(studentList);
        table1.setModel(tableAlunos);
        table1.setVisible(true);
        JTableHeader header = table1.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        System.out.println(studentList);
    }

    private void deleteStudent(String name) {
        serviceProvider.getStudentService().deleteByName(name);
    }

    public void updateTable(StudentService studentService) {
        List<Student> studentList = studentService.listAllStudents();
        setTableModel(studentList,studentService, table1);
    }

    public static void setTableModel(List<Student> studentList,StudentService studentService, JTable table1) {
        StudentTableModel tableAlunos = new StudentTableModel(studentList);
        table1.setModel(tableAlunos);
        table1.setVisible(true);
        JTableHeader header = table1.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        table1.setFont(new Font("Arial", Font.BOLD, 24));
        table1.setRowHeight(40);
        setColumnWidth(0,100,table1);
        System.out.println(studentList);
    }

    private static void setColumnWidth(int columnPosition,int width,JTable table1) {
        table1.getColumnModel().getColumn(columnPosition).setPreferredWidth(width);
        table1.getColumnModel().getColumn(columnPosition).setMaxWidth(width);
        table1.getColumnModel().getColumn(columnPosition).setMinWidth(width);
    }


    private void setTheme() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private class TelaAlunosCloseListener implements TelaAlunos.TelaAlunosCloseListener {
        @Override
        public void onTelaAlunosClosed() {
            updateTable(serviceProvider.getStudentService());
        }
    }

}

