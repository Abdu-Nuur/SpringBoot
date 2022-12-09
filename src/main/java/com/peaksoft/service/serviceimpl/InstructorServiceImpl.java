package com.peaksoft.service.serviceimpl;


import com.peaksoft.entity.Course;
import com.peaksoft.entity.Instructor;
import com.peaksoft.repository.CourseRepository;
import com.peaksoft.repository.InstructorRepository;
import com.peaksoft.service.InstructorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public InstructorServiceImpl(CourseRepository courseRepository, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public List<Instructor> getAllInstructor() {
        return instructorRepository.findAll();
    }

    @Override
    public List<Instructor> getAllInstructor(Long courseId) {
        return instructorRepository.findAllInstructorByCourseId(courseId);
    }

    @Override
    public Instructor getInstructorById(Long id) {
        return instructorRepository.findById(id).get();
    }

    @Override
    public void saveInstructor(Long courseId, Instructor instructor) throws IOException {
        Course course = courseRepository.findById(courseId).get();
        for (Instructor i : course.getInstructors()) {
            try {
                if (i.getEmail().equals(instructor.getEmail()))
                    throw new IOException();
            } catch (IOException e) {
                throw new IOException("This email already exists!");
            }
        }
        validator(instructor.getPhoneNumber().replace(" ", ""), instructor.getLastname()
                .replace(" ", ""), instructor.getFirstname()
                .replace(" ", ""));
        course.addInstructor(instructor);
        instructor.setCourse(course);
        instructorRepository.save(instructor);
    }

    @Override
    public void updateInstructor(Long id, Instructor instructor) throws IOException {
        Instructor instructor1 = instructorRepository.findById(id).get();
        instructor1.setFirstname(instructor.getFirstname());
        instructor1.setLastname(instructor.getLastname());
        instructor1.setPhoneNumber(instructor.getPhoneNumber());
        instructor1.setEmail(instructor.getEmail());
        instructor1.setSpecialization(instructor.getSpecialization());
        validator(instructor.getPhoneNumber().replace(" ", ""), instructor.getLastname()
                .replace(" ", ""), instructor.getFirstname()
                .replace(" ", ""));
        instructorRepository.save(instructor1);
    }

    @Override
    public void deleteInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id).get();
        instructorRepository.delete(instructor);
    }

    @Override
    public void assignInstructor(Long id, Long courseId) throws IOException {
        Instructor instructor = instructorRepository.findById(id).get();
        Course course = courseRepository.findById(courseId).get();
        if (course.getInstructors() != null) {
            for (Instructor i : course.getInstructors()) {
                if (i.getId() == id) {
                    throw new IOException("This instructor already exists!");
                }
            }
        }
        instructor.setCourse(course);
        course.addInstructor(instructor);
        courseRepository.save(course);
        instructorRepository.save(instructor);
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
