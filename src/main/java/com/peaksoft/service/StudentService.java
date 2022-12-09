package com.peaksoft.service;

import com.peaksoft.entity.Student;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudent();
    List<Student> getAllStudent(Long groupId);
    Student getStudentById(Long id);
    void saveStudent(Long groupId, Student student) throws IOException;
    void updateStudent(Long id, Student student) throws IOException;
    void deleteStudent(Long id);
    void assignStudent(Long id, Long groupId) throws IOException;
}
