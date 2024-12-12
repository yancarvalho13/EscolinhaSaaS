package com.escolinha.repository;

import com.escolinha.model.student.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository

public interface StudentRepository extends JpaRepository<Student, Long> {

    public void deleteStudentByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.name = :name, s.cpf = :cpf, s.bornDate = :bornDate, " +
            "s.father = :father, s.mother = :mother WHERE s.idStudent = :id")
    public void updateAllStudentData(@Param("id") Long id,
                              @Param("name") String name,
                              @Param("cpf") Long cpf,
                              @Param("bornDate") LocalDate bornDate,
                              @Param("father") String father,
                              @Param("mother") String mother);
}


