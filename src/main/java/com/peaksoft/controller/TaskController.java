package com.peaksoft.controller;

import com.peaksoft.entity.Task;
import com.peaksoft.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/getAllTask/{lessonId}")
    public String getAllTask(@PathVariable Long lessonId, Model model) {
        model.addAttribute("getAllTask", taskService.getAllTask());
        model.addAttribute("lessonId", lessonId);
        return "/tasks/mainTaskPage";
    }

    @GetMapping("/getAllTaskByLessonId/{lessonId}")
    public String getAllTaskByLessonId(@PathVariable Long lessonId, Model model) {
        model.addAttribute("getAllTaskByLessonId", taskService.getAllTask(lessonId));
        model.addAttribute("lessonId", lessonId);
        return "/tasks/mainTaskPage";
    }

    @GetMapping("/getTaskById/{id}")
    public String getTaskById(@PathVariable Long id, Model model) {
        model.addAttribute("student", taskService.getTaskById(id));
        return "redirect:/getAllTaskByLessonId";
    }

    @GetMapping("/getAllTaskByLessonId/{lessonId}/new")
    public String newTask(@PathVariable Long lessonId, Model model) {
        model.addAttribute("newTask", new Task());
        model.addAttribute("lessonId", lessonId);
        return "/tasks/newTask";
    }

    @PostMapping("/{lessonId}/saveTask")
    public String saveTask(@PathVariable Long lessonId, @ModelAttribute("newTask") Task task) {
        taskService.saveTask(lessonId, task);
        return "redirect:/getAllTaskByLessonId/" + lessonId;
    }

    @GetMapping("/updateTask/{id}")
    public String updateTask(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("updateTask", task);
        model.addAttribute("lessonId", task.getLesson().getId());
        return "/tasks/updateTask";
    }

    @PostMapping("/{lessonId}/{id}/saveUpdateTask")
    public String saveUpdateTask(@PathVariable Long lessonId,
                                   @PathVariable Long id,
                                   @ModelAttribute("updateTask") Task task) {
        taskService.updateTask(id, task);
        return "redirect:/getAllTaskByLessonId/" + lessonId;
    }

    @GetMapping("/{lessonId}/{id}/deleteTaskById")
    public String deleteTaskById(@PathVariable Long lessonId, @PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/getAllTaskByLessonId/" + lessonId;
    }

}
