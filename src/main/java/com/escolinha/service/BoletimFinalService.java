package com.escolinha.service;

import com.escolinha.dto.BoletimFinalDto;
import com.escolinha.dto.NotaFinalMateriaDto;
import com.escolinha.model.classroom.BoletimAnual;
import com.escolinha.repository.BoletimAnualRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BoletimFinalService {

    private BoletimAnualRepository boletimAnualRepository;

    public BoletimFinalService(BoletimAnualRepository boletimAnualRepository) {
        this.boletimAnualRepository = boletimAnualRepository;
    }

    public BoletimFinalDto buscarBoletimFinal(Long studentId, Integer ano){
       Optional<BoletimAnual> boletimAnualOptional = boletimAnualRepository.findBoletimAnualByStudentAndAno(studentId, ano);
       if(boletimAnualOptional.isPresent()){
           BoletimAnual boletimAnual = boletimAnualOptional.get();
           List<NotaFinalMateriaDto> notas = boletimAnual.getNotasFinais().entrySet().stream().map(entry -> new NotaFinalMateriaDto(entry.getKey().getNome(),
                   entry.getValue())).collect(Collectors.toList());

           return new BoletimFinalDto(boletimAnual.getStudent().getIdStudent(), boletimAnual.getAno(), notas);
       } else {
       return null;}
    }


}
