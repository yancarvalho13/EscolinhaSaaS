package com.escolinha.repository;

import com.escolinha.model.professor.Professor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    public void deleteProfessorByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Professor p SET p.name = :name, p.cpf = :cpf, p.bornDate = :bornDate," +
            "p.salary = :salary  WHERE p.idProfessor = :id")
    public void updateAllProfessorData(@Param("id") Long id,
                                       @Param("name") String name,
                                       @Param("cpf") Long cpf,
                                       @Param("bornDate") LocalDate bornDate,
                                       @Param("salary") BigDecimal salary);
}
