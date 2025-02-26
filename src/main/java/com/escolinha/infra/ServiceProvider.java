package com.escolinha.infra;

import com.escolinha.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ServiceProvider {

    private final StudentService studentService;
    private final ProfessorService professorService;
    private final BoletimService boletimService;
    private final BoletimFinalService boletimFinalService;
    private final MateriaService materiaService;
    private final UnidadeService unidadeService;

    public ServiceProvider(StudentService studentService, ProfessorService professorService, BoletimService boletimService, BoletimFinalService boletimFinalService, MateriaService materiaService, UnidadeService unidadeService) {
        this.studentService = studentService;
        this.professorService = professorService;
        this.boletimService = boletimService;
        this.boletimFinalService = boletimFinalService;
        this.materiaService = materiaService;
        this.unidadeService = unidadeService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public ProfessorService getProfessorService() {
        return professorService;
    }

    public BoletimService getBoletimService() {
        return boletimService;
    }

    public BoletimFinalService getBoletimFinalService() {
        return boletimFinalService;
    }

    public MateriaService getMateriaService() {
        return materiaService;
    }

    public UnidadeService getUnidadeService() {
        return unidadeService;
    }
}
