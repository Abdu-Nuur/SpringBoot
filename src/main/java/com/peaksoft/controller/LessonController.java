package com.peaksoft.controller;

import com.peaksoft.entity.Lesson;
import com.peaksoft.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/getAllLesson/{courseId}")
    public String getAllLesson(@PathVariable Long courseId, Model model) {
        model.addAttribute("getAllLesson", lessonService.getAllLesson());
        model.addAttribute("courseId", courseId);
        return "/lessons/mainLessonPage";
    }

    @GetMapping("/getAllLessonByCourseId/{courseId}")
    public String getAllInstructorByCourseId(@PathVariable Long courseId, Model model) {
        model.addAttribute("getAllLessonByCourseId", lessonService.getAllLesson(courseId));
        model.addAttribute("courseId", courseId);
        return "/lessons/mainLessonPage";
    }

    @GetMapping("/getLessonById/{id}")
    public String getLessonById(@PathVariable Long id, Model model) {
        model.addAttribute("lesson", lessonService.getLessonById(id));
        return "redirect:/getAllLessonByCourseId";
    }

    @GetMapping("/getAllLessonByCourseId/{courseId}/new")
    public String newLesson(@PathVariable Long courseId, Model model) {
        model.addAttribute("newLesson", new Lesson());
        model.addAttribute("courseId", courseId);
        return "/lessons/newLesson";
    }

    @PostMapping("/{courseId}/saveLesson")
    public String saveLesson(@PathVariable Long courseId, @ModelAttribute("newLesson") Lesson lesson) {
        lessonService.saveLesson(courseId, lesson);
        return "redirect:/getAllLessonByCourseId/" + courseId;
    }

    @GetMapping("/updateLesson/{id}")
    public String updateLesson(@PathVariable Long id, Model model) {
        Lesson lesson = lessonService.getLessonById(id);
        model.addAttribute("updateLesson", lesson);
        model.addAttribute("courseId", lesson.getCourse().getId());
        return "/lessons/updateLesson";
    }

    @PostMapping("/{courseId}/{id}/saveUpdateLesson")
    public String saveUpdateLesson(@PathVariable Long courseId,
                                       @PathVariable Long id,
                                       @ModelAttribute("updateLesson") Lesson lesson) {
        lessonService.updateLesson(id, lesson);
        return "redirect:/getAllLessonByCourseId/" + courseId;
    }

    @GetMapping("/{courseId}/{id}/deleteLessonById")
    public String deleteLessonById(@PathVariable Long courseId, @PathVariable Long id) {
        lessonService.deleteLesson(id);
        return "redirect:/getAllLessonByCourseId/" + courseId;
    }
}
