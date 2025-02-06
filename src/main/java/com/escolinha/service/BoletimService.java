package com.escolinha.service;

import com.escolinha.dto.NotasDTO;
import com.escolinha.repository.BoletimRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BoletimService {
   private final BoletimRepository boletimRepository;


    public BoletimService(BoletimRepository boletimRepository) {

        this.boletimRepository = boletimRepository;
    }

    public List<NotasDTO> findNotasDtoById(Long id){
        return boletimRepository.findNotasByStudentId(id);
    }

}
