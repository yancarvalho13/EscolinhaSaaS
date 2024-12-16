package com.escolinha.view.home;

import com.escolinha.service.ProfessorService;
import com.escolinha.service.StudentService;
import com.escolinha.view.professor.TelaProfessores;
import com.escolinha.view.student.TelaAlunos;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class HomePage extends JFrame {
    private JButton alunosButton;
    private JPanel panel1;
    private JButton professoresButton;
    private JButton sairButton;
    private JPanel logo;
    private JPanel buttons;
    private JLabel logoSchool;
    private JLabel nomeEscolinha;
    private final StudentService studentService;
    private final ProfessorService professorService;

    public HomePage(StudentService studentService, ProfessorService professorService)throws Exception {
        this.studentService = studentService;
        this.professorService = professorService;
        setContentPane(panel1);
        setTheme();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1024,768));
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

}

