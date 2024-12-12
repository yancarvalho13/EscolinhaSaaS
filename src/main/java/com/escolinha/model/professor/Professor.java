package com.escolinha.model.professor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Professors",
        uniqueConstraints = {@UniqueConstraint(columnNames = "cpf", name = "uk_professor_cpf"
        )})
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfessor;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Cpf obrigatório")
    @Column(nullable = false, unique = true, length = 11)
    private Long cpf;

    @Past(message = "Data de nascimento deve ser no passado")
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate bornDate;

    @NotNull(message = "Salário é obrigatório")
    @Positive(message = "Salário deve ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

}
