package org.nirmal.learning.apidemo.repository;

import org.nirmal.learning.apidemo.model.Student;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class StudentRepository {

    private final Map<Integer, Student> students = new TreeMap<>();
    private final AtomicInteger latestRollNumber = new AtomicInteger(0);

    @PostConstruct
    public void init() {
        Student aadhya = new Student(1, "Aadhya", "A", LocalDate.of(2016, 1, 1));
        addStudent(aadhya);
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }

    public Optional<Student> getStudent(int rollNumber) {
        Student student = students.get(rollNumber);
        if(student != null) {
            return Optional.of(student);
        }
        return Optional.empty();
    }

    public Student addStudent(Student student) {
        int latestRollNumberInt = latestRollNumber.incrementAndGet();
        Student onboardedStudent = student.withRollNumber(latestRollNumberInt);
        students.put(latestRollNumberInt, onboardedStudent);
        return onboardedStudent;
    }

    public Student updateStudent(Student student) {
        Student existingStudent = students.get(student.getRollNumber());
        if(existingStudent == null) {
            throw new IllegalArgumentException("Can not update student as the roll number does not exist");
        }
        Student updatedStudent = student
                .withFirstName(student.getFirstName())
                .withLastName(student.getLastName())
                .withDateOfBirth(student.getDateOfBirth());

        students.put(updatedStudent.getRollNumber(), updatedStudent);
        return updatedStudent;
    }

    public boolean deleteStudent(int rollNumber) {

        Student existingStudent = students.get(rollNumber);
        if(existingStudent == null) {
            return false;
        }

        students.remove(rollNumber);
        return true;
    }

}
