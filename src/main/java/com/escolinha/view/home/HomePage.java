package com.escolinha.view.home;

import com.escolinha.service.ProfessorService;
import com.escolinha.service.StudentService;
import com.escolinha.view.professor.TelaProfessores;
import com.escolinha.view.student.TelaAlunos;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class HomePage extends JFrame {
    private JButton alunosButton;
    private JPanel panel1;
    private JButton professoresButton;
    private JButton sairButton;
    private JLabel nomeEscolinha;
    private final StudentService studentService;
    private final ProfessorService professorService;

    public HomePage(StudentService studentService, ProfessorService professorService) {
        this.studentService = studentService;
        this.professorService = professorService;
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });

        alunosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaAlunos telaAlunos = new TelaAlunos(studentService);
                telaAlunos.setVisible(true);
            }
        });

        professoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaProfessores telaProfessores = new TelaProfessores(professorService);
                telaProfessores.setVisible(true);
            }
        });
    }

}

