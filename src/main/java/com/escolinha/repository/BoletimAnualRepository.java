package com.escolinha.repository;

import com.escolinha.dto.BoletimFinalDto;
import com.escolinha.model.classroom.BoletimAnual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoletimAnualRepository extends JpaRepository<BoletimAnual, Long> {

    @Query("""
           SELECT ba
           FROM BoletimAnual ba
           LEFT JOIN FETCH ba.notasFinais
           WHERE ba.student.idStudent = :studentId 
           AND ba.ano = :ano
""")
    Optional<BoletimAnual> findBoletimAnualByStudentAndAno(@Param("studentId") Long studentId, @Param("ano") Integer ano);
}
