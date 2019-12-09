package edu.learnsql.controller;

import edu.learnsql.entities.main.SQLTask;
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
import java.sql.SQLException;

@Controller
@RequestMapping("/admin/tasks")
public class SQLTaskController {
    @Autowired
    private SQLTaskService SQLTaskService;

    @Autowired
    private SQLTaskProgressService SQLTaskProgressService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newTask() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("task", new SQLTask());
        modelAndView.addObject("tasks", SQLTaskService.findAll());
        modelAndView.addObject("auth", getUser());
        modelAndView.addObject("control", getUser().getRole().getRole());
        modelAndView.addObject("mode", "MODE_NEW");
        modelAndView.setViewName("sqltask");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveTask(@Valid SQLTask task, BindingResult bindingResult) throws SQLException {
        SQLTaskService.save(task);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/tasks/all");
        modelAndView.addObject("auth", getUser());
        modelAndView.addObject("control", getUser().getRole().getRole());
        return modelAndView;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView allTasks() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("rule", new SQLTask());
        //POINT=7 http://stackoverflow.com/questions/22364886/neither-bindingresult-nor-plain-target-object-for-bean-available
        // -as-request-attr
        modelAndView.addObject("tasks", SQLTaskService.findAll());
        modelAndView.addObject("auth", getUser());
        modelAndView.addObject("control", getUser().getRole().getRole());
        modelAndView.addObject("mode", "MODE_ALL");
        modelAndView.setViewName("task");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateTask(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("rule", new SQLTask());
        modelAndView.addObject("task", SQLTaskService.findTask(id));
        modelAndView.addObject("auth", getUser());
        modelAndView.addObject("control", getUser().getRole().getRole());
        modelAndView.addObject("mode", "MODE_UPDATE");
        modelAndView.setViewName("task");
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteTask(@RequestParam int id) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/tasks/all");
        modelAndView.addObject("rule", new SQLTask());
        modelAndView.addObject("auth", getUser());
        modelAndView.addObject("control", getUser().getRole().getRole());
        SQLTaskService.delete(id);
        return modelAndView;
    }

    @RequestMapping(value = "/per_inf", method = RequestMethod.GET)
    public ModelAndView infTask(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("rule", new SQLTask());
        modelAndView.addObject("task", SQLTaskService.findTask(id));
        modelAndView.addObject("usertasks", SQLTaskProgressService.findByTask(SQLTaskService.findTask(id)));
        modelAndView.addObject("auth", getUser());
        modelAndView.addObject("control", getUser().getRole().getRole());
        modelAndView.addObject("mode", "MODE_INF");
        modelAndView.setViewName("task");
        return modelAndView;
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return user;
    }
}
