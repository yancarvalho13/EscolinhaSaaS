package com.escolinha.service;


import com.escolinha.model.professor.Professor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.escolinha.repository.ProfessorRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }


    public Professor createProfessor(String name, String cpf, LocalDate bornDate, BigDecimal salary){
        Professor professor = new Professor();
        professor.setName(name);
        professor.setCpf(Long.valueOf(cpf));
        professor.setBornDate(bornDate);
        professor.setSalary((salary));

        return professor;
    }

    public List<Professor> listAllProfessors(){
        List<Professor>  orderedProfessors = professorRepository.findAll();
        orderedProfessors.sort(Comparator.comparing(Professor::getName));
        return orderedProfessors;
    }

    public void deleteByName(String name){
        professorRepository.deleteProfessorByName(name);
    }

    public void updateProfessor(Long id, String name, Long cpf, LocalDate bornDate, BigDecimal salary ){
        professorRepository.updateAllProfessorData(id, name, cpf, bornDate, salary);
    }


    public void saveProfessor(Professor professor){
        professorRepository.save(professor);
    }


}
