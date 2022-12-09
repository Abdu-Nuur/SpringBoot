package com.peaksoft.service;


import com.peaksoft.entity.Course;

import java.io.IOException;
import java.util.List;

public interface CourseService {
    List<Course> getAllCourse();
    List<Course> getAllCourse(Long companyId);
    Course getCourseById(Long id);
    void saveCourse(Long companyId, Course course) throws IOException;
    void updateCourse(Long id, Course course);
    void deleteCourse(Long id);
}
