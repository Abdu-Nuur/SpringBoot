package com.peaksoft.service.serviceimpl;

import com.peaksoft.entity.Course;
import com.peaksoft.entity.Group;
import com.peaksoft.entity.Instructor;
import com.peaksoft.entity.Student;
import com.peaksoft.repository.GroupRepository;
import com.peaksoft.repository.StudentRepository;
import com.peaksoft.service.StudentService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public StudentServiceImpl(GroupRepository groupRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> getAllStudent(Long groupId) {
        return studentRepository.findAllStudentByGroupId(groupId);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public void saveStudent(Long groupId, Student student) throws IOException {
        Group group = groupRepository.findById(groupId).get();
        for (Course c : group.getCourses()) {
            c.getCompany().plus();
        }
        for (Course c : group.getCourses()) {
            for (Instructor i : c.getInstructors()) {
                i.plus();
            }
        }
        validator(student.getPhoneNumber().replace(" ", ""), student.getLastName()
                .replace(" ", ""), student.getFirstName()
                .replace(" ", ""));
        group.addStudent(student);
        student.setGroup(group);
        studentRepository.save(student);
    }

    @Override
    public void updateStudent(Long id, Student student) throws IOException {
        Student student1 = studentRepository.findById(id).get();
        student1.setFirstName(student.getFirstName());
        student1.setLastName(student.getLastName());
        student1.setPhoneNumber(student.getPhoneNumber());
        student1.setEmail(student.getEmail());
        student1.setStudyFormat(student.getStudyFormat());
        validator(student.getPhoneNumber().replace(" ", ""), student.getLastName()
                .replace(" ", ""), student.getFirstName()
                .replace(" ", ""));
        studentRepository.save(student1);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).get();
        for (Course c : student.getGroup().getCourses()) {
            c.getCompany().minus();
        }
        for (Course c : student.getGroup().getCourses()) {
            for (Instructor i : c.getInstructors()) {
                i.minus();
            }
        }
        studentRepository.delete(student);
    }

    @Override
    public void assignStudent(Long id, Long groupId) throws IOException {
        Student student = studentRepository.findById(id).get();
        Group group = groupRepository.findById(groupId).get();
        if (group.getStudents() != null) {
            for (Student s : group.getStudents()) {
                if (s.getId() == id) {
                    throw new IOException("This student already exists!");
                }
            }
        }
        student.setGroup(group);
        group.addStudent(student);
        studentRepository.save(student);
        groupRepository.save(group);
    }

    private void validator(String phoneNumber, String firstName, String lastName) throws IOException {
        if (firstName.length() > 2 && lastName.length() > 2) {
            for (Character i : firstName.toCharArray()) {
                if (!Character.isAlphabetic(i)) {
                    throw new IOException("В имени инструктора нельзя вставлять цифры");
                }
            }

            for (Character i : lastName.toCharArray()) {
                if (!Character.isAlphabetic(i)) {
                    throw new IOException("В фамилию инструктора нельзя вставлять цифры");
                }
            }
        } else {
            throw new IOException("В имени или фамилии инструктора должно быть как минимум 3 буквы");
        }

        if (phoneNumber.length() == 13
                && phoneNumber.charAt(0) == '+'
                && phoneNumber.charAt(1) == '9'
                && phoneNumber.charAt(2) == '9'
                && phoneNumber.charAt(3) == '6') {
            int counter = 0;

            for (Character i : phoneNumber.toCharArray()) {
                if (counter != 0) {
                    if (!Character.isDigit(i)) {
                        throw new IOException("Формат номера не правильный");
                    }
                }
                counter++;
            }
        } else {
            throw new IOException("Формат номера не правильный");
        }
    }
}
