package ru.miinegaliev.springapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.miinegaliev.springapp.models.Assignment;
import ru.miinegaliev.springapp.services.AssignmentsService;
import ru.miinegaliev.springapp.util.AssignmentValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/assignments")
public class AssignmentsController {

    private final AssignmentsService assignmentsService;
    private final AssignmentValidator assignmentValidator;

    @Autowired
    public AssignmentsController(AssignmentsService assignmentsService, AssignmentValidator assignmentValidator) {
        this.assignmentsService = assignmentsService;
        this.assignmentValidator = assignmentValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("assignments", assignmentsService.findAll());
        return "assignments/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("assignment", assignmentsService.findOne(id));
        model.addAttribute("users", assignmentsService.getUsersByAssignmentId(id));

        return "assignments/show";
    }
    @GetMapping("/new")
    public String newAssignment(@ModelAttribute("assignment") Assignment assignment) {
        return "assignments/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("assignment") @Valid Assignment assignment,
                         BindingResult bindingResult) {
        assignmentValidator.validate(assignment, bindingResult);

        if (bindingResult.hasErrors())
            return "assignments/new";

        assignmentsService.save(assignment);
        return "redirect:/assignments";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("assignment", assignmentsService.findOne(id));
        return "assignments/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("assignment") @Valid Assignment assignment, BindingResult bindingResult,
                         @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors())
            return "assignments/edit";

        assignmentsService.update(id, assignment);
        return "redirect:/assignments";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        assignmentsService.delete(id);
        return "redirect:/assignments";
    }
}
