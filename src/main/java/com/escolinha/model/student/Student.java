package com.escolinha.model.student;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "Students",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf", name = "uk_student_cpf")
        })
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStudent;

    @NotBlank(message = "Nome do Aluno Obrigatório")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Cpf Obrigatório")
    @Column(nullable = false, unique = true, length = 11)
    private Long cpf;



    @Past(message = "Data de nascimento deve ser no passado")
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate bornDate;

    @NotBlank
    @Column
    private String father;

    @NotBlank
    @Column
    private String mother;





}
