package com.peaksoft.controller;

import com.peaksoft.entity.Group;
import com.peaksoft.entity.Student;
import com.peaksoft.service.GroupService;
import com.peaksoft.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;


@Controller
public class GroupController {

    private final GroupService groupService;
    private final StudentService studentService;

    @Autowired
    public GroupController(GroupService groupService, StudentService studentService) {
        this.groupService = groupService;
        this.studentService = studentService;
    }

    @GetMapping("/getAllGroup/{courseId}")
    public String getAllGroup(@PathVariable Long courseId, Model model) {
        model.addAttribute("getAllGroup", groupService.getAllGroup());
        model.addAttribute("courseId", courseId);
        return "/group/mainGroupPage";
    }

    @GetMapping("/getAllGroupByCourseId/{courseId}")
    public String getAllGroupByCourseId(@PathVariable Long courseId,
                                        @ModelAttribute("student") Student student,
                                        Model model) {
        model.addAttribute("getAllGroupByCourseId", groupService.getAllGroup(courseId));
        model.addAttribute("courseId", courseId);
        model.addAttribute("students", studentService.getAllStudent());
        return "/group/mainGroupPage";
    }

    @GetMapping("/getGroupById/{id}")
    public String getGroupById(@PathVariable Long id, Model model) {
        model.addAttribute("group", groupService.getGroupById(id));
        return "/courses/mainCoursePage";
    }

    @GetMapping("/getAllGroupByCourseId/{courseId}/new")
    public String newGroup(@PathVariable Long courseId, Model model) {
        model.addAttribute("newGroup", new Group());
        model.addAttribute("courseId", courseId);
        return "/group/newGroupPage";
    }

    @PostMapping("/{courseId}/saveGroup")
    public String saveGroup(@PathVariable Long courseId, @ModelAttribute("newGroup") Group group) {
        groupService.saveGroup(courseId, group);
        return "redirect:/getAllGroupByCourseId/" + courseId;
    }

    @GetMapping("/updateGroupByCourseId/{courseId}/{id}")
    public String updateGroupByCourseId(@PathVariable Long courseId,
                                        @PathVariable Long id, Model model) {
        Group group = groupService.getGroupById(id);
        model.addAttribute("group", group);
        model.addAttribute("courseId", courseId);
        return "/group/updateGroup";
    }

    @PostMapping("/{courseId}/{id}/saveUpdateGroupByCourseId")
    public String saveUpdateGroupByCourseId(@PathVariable Long courseId,
                                            @PathVariable Long id,
                                            @ModelAttribute("group") Group group) {
        groupService.updateGroup(id, group);
        return "redirect:/getAllGroupByCourseId/" + courseId;
    }

    @GetMapping("/{courseId}/{id}/deleteGroupById")
    public String deleteGroupById(@PathVariable Long courseId, @PathVariable Long id) {
        groupService.deleteGroup(courseId, id);
        return "redirect:/getAllGroupByCourseId/" + courseId;
    }

    @PostMapping("/{groupId}/assignStudent")
    private String assignStudent(@PathVariable("groupId") Long groupId,
                                            @ModelAttribute("student") Student student) throws IOException {
        studentService.assignStudent(student.getId(), groupId);
        return "redirect:/getAllStudentByGroupId/ " + groupId;
    }

}
