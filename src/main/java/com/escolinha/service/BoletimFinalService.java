package com.escolinha.service;

import com.escolinha.dto.BoletimFinalDto;
import com.escolinha.dto.NotaFinalMateriaDto;
import com.escolinha.model.classroom.BoletimAnual;
import com.escolinha.model.classroom.Materia;
import com.escolinha.model.student.Student;
import com.escolinha.repository.BoletimAnualRepository;
import com.escolinha.repository.MateriaRepository;
import com.escolinha.repository.StudentRepository;
import com.escolinha.repository.UnidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BoletimFinalService {

    private MateriaRepository materiaRepository;
    private BoletimAnualRepository boletimAnualRepository;
    private StudentRepository studentRepository;
    private UnidadeRepository unidadeRepository;

    public BoletimFinalService(BoletimAnualRepository boletimAnualRepository, MateriaRepository materiaRepository, StudentRepository studentRepository, UnidadeRepository unidadeRepository) {
        this.boletimAnualRepository = boletimAnualRepository;
        this.materiaRepository = materiaRepository;
        this.studentRepository = studentRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public BoletimFinalDto buscarBoletimFinal(Long studentId, Integer ano) {
        Optional<BoletimAnual> boletimAnualOptional = boletimAnualRepository.findBoletimAnualByStudentAndAno(studentId, ano);

        if (boletimAnualOptional.isPresent()) {
            BoletimAnual boletimAnual = boletimAnualOptional.get();

            List<NotaFinalMateriaDto> notas = boletimAnual.getNotasFinais().entrySet().stream().map(entry -> {
                Integer materiaId = entry.getKey().intValue();
                Double notaFinal = entry.getValue();

                // Buscar o nome da matéria no banco
                String nomeMateria = materiaRepository.findById(materiaId)
                        .map(Materia::getNome)
                        .orElse("Matéria Desconhecida");

                return new NotaFinalMateriaDto(nomeMateria, notaFinal);
            }).collect(Collectors.toList());

            return new BoletimFinalDto(boletimAnual.getStudent().getIdStudent(), boletimAnual.getAno(), notas);
        }

        return null;
    }

    public BoletimAnual gerarBoletim(Long studentId, int ano) {
        // Verificar se já existe um boletim
        Optional<BoletimAnual> boletimExistente = boletimAnualRepository.findBoletimAnualByStudentAndAno(studentId, ano);
        if (boletimExistente.isPresent()) {
            throw new RuntimeException("Já existe um boletim gerado para este aluno no ano especificado");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        List<Object[]> medias = unidadeRepository.calcularMediaPorMateria(studentId);
        if (medias.isEmpty()) {
            throw new RuntimeException("Não há notas registradas para este aluno");
        }

        BoletimAnual boletim = new BoletimAnual();
        boletim.setStudent(student);
        boletim.setAno(ano);
        boletim.setNotasFinais(new HashMap<>());

        for (Object[] row : medias) {
            Long materiaId = (Long) row[0];
            Double media = (Double) row[1];
            boletim.getNotasFinais().put(materiaId, media);
        }

        return boletimAnualRepository.save(boletim);
    }

}
