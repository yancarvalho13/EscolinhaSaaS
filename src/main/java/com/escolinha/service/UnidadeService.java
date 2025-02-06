package com.escolinha.service;

import com.escolinha.model.classroom.Unidade;
import com.escolinha.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeService {
    UnidadeRepository repository;

    public UnidadeService(UnidadeRepository repository) {
        this.repository = repository;
    }

    public Unidade save(Unidade unidade) {
        return repository.save(unidade);
    }

    public List<Unidade> findAll() {
        return repository.findAll();
    }
}
