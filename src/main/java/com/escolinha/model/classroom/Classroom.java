package com.escolinha.model.classroom;

import com.escolinha.model.professor.Professor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClassroom;

    @NotBlank(message = "Nome obrigatório")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer academicYear;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;
}
