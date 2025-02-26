package com.escolinha.view.student;

import com.escolinha.dto.BoletimFinalDto;
import com.escolinha.dto.NotaFinalMateriaDto;
import com.escolinha.dto.NotasDTO;
import com.escolinha.infra.ServiceProvider;
import com.escolinha.model.student.Student;
import com.escolinha.model.student.StudentNotaFinalTableModel;
import com.escolinha.service.BoletimService;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
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
    private JPanel photoLayer;
    private JLabel photo;
    private JButton changePhotoButton;


    public TelaAlunoNotas(ServiceProvider serviceProvider, Long id) {
        this.serviceProvider = serviceProvider;
        this.id = id;
        initializeScreen();
        loadPhoto();

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

        changePhotoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                // Caminho fixo para salvar a imagem
                String savePath = "/home/yanonjava/IdeaProjects/EscolinhaSaaS/src/main/resources/images/" + id + ".jpg";
                File destination = new File(savePath);

                try {
                    // Copia a imagem para o diretório fixo
                    BufferedImage image = ImageIO.read(file);
                    ImageIO.write(image, "jpg", destination);

                    // Atualiza o JLabel com a nova imagem
                    photo.setIcon(resizeImage(destination));

                    // Salva o caminho da foto em um arquivo local
                    savePhotoPath(savePath);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar a imagem!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private static ImageIcon resizeImage(File file) {
        try {
            BufferedImage image = ImageIO.read(new File(file.getAbsolutePath()));
            Image resizedImage = image.getScaledInstance(260, 240, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
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
       studentName.setText(student.getName().toUpperCase());
       studentCpf.setText(String.valueOf(student.getCpf()));
       studentBornDate.setText(String.valueOf(student.getBornDate()));
       studentFather.setText(student.getFather().toUpperCase());
       studentMother.setText(student.getMother().toUpperCase());
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

    private void savePhotoPath(String path) {
        try {
            File file = new File("/home/yanonjava/IdeaProjects/EscolinhaSaaS/src/main/resources/photo_path.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(path);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPhoto() {
        try {
            File file = new File("/home/yanonjava/IdeaProjects/EscolinhaSaaS/src/main/resources/photo_path.txt");

            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String path = reader.readLine();
                reader.close();

                // Verifica se o arquivo da foto existe
                File imageFile = new File(path);
                if (imageFile.exists()) {
                    photo.setIcon(resizeImage(imageFile));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
