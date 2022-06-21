package ru.alishev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.models.Assignment;
import ru.alishev.springcourse.models.User;
import ru.alishev.springcourse.services.AssignmentsService;
import ru.alishev.springcourse.services.UsersService;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final AssignmentsService assignmentsService;


    @Autowired
    public UsersController(UsersService usersService, AssignmentsService assignmentsService) {
        this.usersService = usersService;
        this.assignmentsService = assignmentsService;

    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "users_per_page", required = false) Integer usersPerPage,
                        @RequestParam(value = "sort_by_name", required = false) boolean sortByName) {
        if (page == null || usersPerPage == null)
            model.addAttribute("users", usersService.findAll(sortByName));
        else
            model.addAttribute("users", usersService.findWithPagination(page, usersPerPage, sortByName));

        return "users/index";
    }

//    @GetMapping()
//    public String index(Model model){
//        model.addAttribute("users", userDAO.index());
//        return "users/index";
//    }
//
//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") Integer id, Model model) {
//        model.addAttribute("executor", userDAO.show(id));
//        return "users/show";
//    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model, @ModelAttribute("assignment") Assignment assignment) {
        model.addAttribute("user", usersService.findOne(id));

        Assignment userAssignment = usersService.getUserAssignment(id);

        if (userAssignment != null)
            model.addAttribute("executor",userAssignment);
        else
            model.addAttribute("assignments", assignmentsService.findAll());

        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user){
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "users/new";

        usersService.save(user);

        return "redirect:/users";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id){
        model.addAttribute("user", usersService.findOne(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors())
            return "users/edit";

        usersService.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id){
        usersService.delete(id);
        return "redirect:/users";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") Integer id){
        usersService.release(id);
        return "redirect:/users/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") Integer id, @ModelAttribute("assignment") Assignment selectedAssignment) {
        usersService.assign(id, selectedAssignment);
        return "redirect:/users/" + id;
    }

    @GetMapping("/search")
    public String searchPage(){
        return "users/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("users", usersService.search(query));
        return "users/search";
    }
}

