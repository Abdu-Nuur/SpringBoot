package com.peaksoft.service.serviceimpl;

import com.peaksoft.entity.Company;
import com.peaksoft.entity.Course;
import com.peaksoft.repository.CompanyRepository;
import com.peaksoft.repository.CourseRepository;
import com.peaksoft.service.CourseService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CompanyRepository companyRepository;
    private final CourseRepository courseRepository;


    public CourseServiceImpl(CompanyRepository companyRepository, CourseRepository courseRepository) {
        this.companyRepository = companyRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getAllCourse(Long companyId) {
        return courseRepository.findAllCourseByCompanyId(companyId);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).get();
    }

    @Override
    public void saveCourse(Long companyId, Course course) throws IOException {
        Company company = companyRepository.findById(companyId).get();

        try {
            if (course.getDuration() < 2 && course.getDuration() > 12)
                throw new IOException();
        } catch (IOException e) {
            throw new IOException("You entered the wrong number!");
        }
        company.addCourse(course);
        course.setCompany(company);
        courseRepository.save(course);
    }

    @Override
    public void updateCourse(Long id, Course course) {
        Course course1 = courseRepository.findById(id).get();
        course1.setCourseName(course.getCourseName());
        course1.setDuration(course.getDuration());
        course1.setDescription(course.getDescription());
        courseRepository.save(course1);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id).get();
        courseRepository.delete(course);
    }
}
