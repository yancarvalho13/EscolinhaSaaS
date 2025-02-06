package com.escolinha.repository;

import com.escolinha.dto.NotasDTO;
import com.escolinha.model.classroom.Materia;
import com.escolinha.model.classroom.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoletimRepository extends JpaRepository<Unidade, Long> {

    @Query("""
    SELECT new com.escolinha.dto.NotasDTO(
        u.materia.nome,
        u.nota1,
        u.nota2,
        u.posicaoUnidade
    )
    FROM Unidade u
    WHERE u.student.idStudent = :studentId
    """)
    List<NotasDTO> findNotasByStudentId(@Param("studentId") Long studentId);

}
