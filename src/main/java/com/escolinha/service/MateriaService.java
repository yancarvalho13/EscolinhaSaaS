package com.escolinha.service;

import com.escolinha.model.classroom.Materia;
import com.escolinha.repository.MateriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaService {
    MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;

    }

    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }
}
