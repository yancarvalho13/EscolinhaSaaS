package com.escolinha.service;

import com.escolinha.model.student.Student;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.escolinha.repository.StudentRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(String name, String cpf, LocalDate bornDate, String father, String mother){
            Student student = new Student();

            student.setName(name);
            student.setCpf(Long.valueOf(cpf));
            student.setBornDate(bornDate);
            student.setFather(father);
            student.setMother(mother);

            return student;

    }

    public void saveStudent(Student student){
            studentRepository.save(student);
    }

    public void deleteByName(String name){
        studentRepository.deleteStudentByName(name);
    }

    public void updateStudent(Long id, String name, Long cpf, LocalDate bornDate, String father, String mother) {
        studentRepository.updateAllStudentData(id, name, cpf, bornDate, father, mother);
    }

    public List<Student> listAllStudents(){
        List<Student> orderedStudents = studentRepository.findAll();
        orderedStudents.sort(Comparator.comparing(Student::getName));
        return orderedStudents;
    }

    public Student findStudent(Long id){
        Optional<Student> student = studentRepository.findById(id);
        return student.orElse(null);
    }

    public Student findStudentByName(String name){
        return studentRepository.findStudentByName(name);
    }
}
