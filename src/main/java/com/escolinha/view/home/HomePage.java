package com.escolinha.view.home;

import com.escolinha.model.student.Student;
import com.escolinha.model.student.StudentTableModel;
import com.escolinha.service.BoletimFinalService;
import com.escolinha.service.BoletimService;
import com.escolinha.service.ProfessorService;
import com.escolinha.service.StudentService;
import com.escolinha.view.student.TelaAlunoNotas;
import com.escolinha.view.student.TelaAlunos;
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
    private JLabel logoSchool;
    private JLabel nomeEscolinha;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final BoletimService boletimService;
    private final BoletimFinalService boletimFinalService;


    public HomePage(StudentService studentService, ProfessorService professorService, BoletimService boletimService, BoletimFinalService boletimFinalService)throws Exception {
        this.studentService = studentService;
        this.professorService = professorService;
        this.boletimService = boletimService;
        this.boletimFinalService = boletimFinalService;
        setContentPane(panel1);
        setTheme();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1024,768));
        setLocationRelativeTo(null);
        updateTable(studentService);




        //Abrir tela de notas do Aluno
        table1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table1.getSelectedRow();
                int selectedColumn = table1.getSelectedColumn();
                if(selectedRow != -1 && selectedColumn != -1) {
                    Long id = Long.valueOf(table1.getValueAt(selectedRow, 0).toString());
                    String name = table1.getValueAt(selectedRow, 1).toString();

                    deletarButton.addActionListener(f -> {
                        deleteStudent(name);
                        updateTable(studentService);
                    });

                    if (e.getClickCount() == 2) {
                        TelaAlunoNotas telaAlunoNotas = new TelaAlunoNotas(studentService, id, boletimService, boletimFinalService);
                    }
                }
            }
        });


        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaAlunos telaAlunos = new TelaAlunos(
                        studentService,
                        boletimService,
                        HomePage.this::updateTablehere  // Passa o método de atualização diretamente
                );
                telaAlunos.setVisible(true);
            }
        });

        sairButton.addActionListener(e -> {
            dispose();
            System.exit(0);
        });


    }

    private void updateTablehere() {
        List<Student> studentList = studentService.listAllStudents();
        StudentTableModel tableAlunos = new StudentTableModel(studentList);
        table1.setModel(tableAlunos);
        table1.setVisible(true);
        JTableHeader header = table1.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        System.out.println(studentList);
    }

    private void deleteStudent(String name) {
        studentService.deleteByName(name);
    }

    public void updateTable(StudentService studentService) {
        List<Student> studentList = studentService.listAllStudents();
        StudentTableModel tableAlunos = new StudentTableModel(studentList);
        table1.setModel(tableAlunos);
        table1.setVisible(true);
        JTableHeader header = table1.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        System.out.println(studentList);
    }


    private void setTheme(){
        try {
            for(UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private class TelaAlunosCloseListener implements TelaAlunos.TelaAlunosCloseListener {
        @Override
        public void onTelaAlunosClosed() {
            updateTable(studentService);
        }
    }

}

