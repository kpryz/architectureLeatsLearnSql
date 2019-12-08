package edu.learnsql.controller;

import edu.learnsql.entities.main.SQLTaskProgress;
import edu.learnsql.entities.main.User;
import edu.learnsql.service.SQLTaskProgressService;
import edu.learnsql.service.SQLTaskService;
import edu.learnsql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/user-task")
public class SQLTaskProgressController {


    @Autowired
    private UserService userService;

    @Autowired
    private SQLTaskProgressService SQLTaskProgressService;

    @Autowired
    private SQLTaskService SQLTaskService;


    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newUserTask() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user_task", new SQLTaskProgress());
        modelAndView.addObject("users", userService.findAll());
        modelAndView.addObject("tasks", SQLTaskService.findAll());
        modelAndView.addObject("auth", getUser());
        modelAndView.addObject("control", getUser().getRole().getRole());
        modelAndView.addObject("mode", "MODE_NEW");
        modelAndView.setViewName("user_task");
        return modelAndView;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView allUserTasks() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("rule", new SQLTaskProgress());
        modelAndView.addObject("user_tasks", SQLTaskProgressService.findAll());
        modelAndView.addObject("users", userService.findAll());
        modelAndView.addObject("tasks", SQLTaskService.findAll());
        modelAndView.addObject("auth", getUser());
        modelAndView.addObject("control", getUser().getRole().getRole());
        modelAndView.addObject("mode", "MODE_ALL");
        modelAndView.setViewName("user_task");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveUserTask(@Valid SQLTaskProgress userSQLTask, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/user-task/all");
        modelAndView.addObject("auth", getUser());
        modelAndView.addObject("control", getUser().getRole().getRole());
        SQLTaskProgressService.save(userSQLTask);
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateUserTask(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("rule", new SQLTaskProgress());
        modelAndView.addObject("user_task", SQLTaskProgressService.findUserTask(id));
        modelAndView.addObject("users", userService.findAll());
        modelAndView.addObject("tasks", SQLTaskService.findAll());
        modelAndView.addObject("auth", getUser());
        modelAndView.addObject("control", getUser().getRole().getRole());
        modelAndView.addObject("mode", "MODE_UPDATE");
        modelAndView.setViewName("user_task");
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteUserTask(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/user-task/all");
        modelAndView.addObject("auth", getUser());
        modelAndView.addObject("control", getUser().getRole().getRole());
        SQLTaskProgressService.delete(id);
        return modelAndView;
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return user;
    }

}
