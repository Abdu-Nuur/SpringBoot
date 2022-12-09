package com.peaksoft.service;

import com.peaksoft.entity.Lesson;

import java.util.List;

public interface LessonService {
    List<Lesson> getAllLesson();
    List<Lesson> getAllLesson(Long courseId);
    Lesson getLessonById(Long id);
    void saveLesson(Long courseId, Lesson lesson);
    void updateLesson(Long id, Lesson lesson);
    void deleteLesson(Long id);
}
