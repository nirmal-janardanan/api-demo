package org.nirmal.learning.apidemo.controller;

import org.nirmal.learning.apidemo.model.Student;
import org.nirmal.learning.apidemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "/students")
public class StudentController {

    private final StudentRepository studentRepository;


    public StudentController(
            @Autowired StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/all")
    public Collection<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    @GetMapping("/{rollNumber}")
    public Student getById(@PathVariable("rollNumber") int rollNumber) {
        Optional<Student> student = studentRepository.getStudent(rollNumber);
        return student.orElseThrow(() -> new StudentNotFoundException(rollNumber));
    }

    @PostMapping("/")
    public Student addStudent(@RequestBody Student student) {
        Student onboardedStudent = studentRepository.addStudent(student);
        return onboardedStudent;
    }

    @PutMapping("/")
    public Student updateStudent(@RequestBody Student student) {
        try {
            Student onboardedStudent = studentRepository.updateStudent(student);
            return onboardedStudent;
        } catch(RuntimeException e) {
            throw new StudentNotFoundException(student.getRollNumber());
        }
    }

    @DeleteMapping("/{rollNumber}")
    public String deleteStudent(@PathVariable("rollNumber") int rollNumber) {
        boolean deleted = studentRepository.deleteStudent(rollNumber);
        return "Student deleted: " + deleted;
    }
}

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No Student with provided rollNumber")  // 404
class StudentNotFoundException extends RuntimeException {
    int rollNumber;

    public StudentNotFoundException(int rollNumber) {
        super("No Student with provided rollNumber = " + rollNumber);
        this.rollNumber = rollNumber;
    }
}
