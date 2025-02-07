package com.escolinha.repository;

import com.escolinha.model.classroom.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
    @Query("SELECT u.materia.id, AVG((u.nota1 + u.nota2) / 2) FROM Unidade u WHERE u.student.id = :studentId GROUP BY u.materia.id")
    List<Object[]> calcularMediaPorMateria(@Param("studentId") Long studentId);

}
