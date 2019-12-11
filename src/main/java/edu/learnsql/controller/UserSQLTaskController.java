package edu.learnsql.controller;

import edu.learnsql.dao.learning.CommonDao;
import edu.learnsql.entities.main.SQLTask;
import edu.learnsql.entities.main.User;
import edu.learnsql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/task")
public class UserSQLTaskController {
    @Autowired
    private edu.learnsql.service.SQLTaskService SQLTaskService;

    @Autowired
    private edu.learnsql.service.SQLTaskProgressService SQLTaskProgressService;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/execute", method = RequestMethod.GET)
    public ModelAndView newUserTask(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("rule", new SQLTask());
        modelAndView.addObject("task", SQLTaskService.findTask(id));
        modelAndView.addObject("usertasks", SQLTaskProgressService.findByTask(SQLTaskService.findTask(id)));
        modelAndView.addObject("auth", getUser());
        modelAndView.addObject("control", getUser().getRole().getRole());
        modelAndView.addObject("mode", "MODE_EXEC");
        modelAndView.setViewName("sql_execution");
        return modelAndView;
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ModelAndView checkUserTask(@Valid SQLTask rule, @RequestParam int id) {
        SQLTask task = SQLTaskService.findTask(id);
        boolean isEqual = SQLTaskService.checkQueries(task.getQuery(), rule.getQuery());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("rule", rule);
        modelAndView.addObject("task", task);
        modelAndView.addObject("usertasks", SQLTaskProgressService.findByTask(SQLTaskService.findTask(id)));
        modelAndView.addObject("result", String.valueOf(isEqual));
        modelAndView.addObject("auth", getUser());
        modelAndView.addObject("control", getUser().getRole().getRole());
        modelAndView.addObject("mode", "MODE_EXEC");
        modelAndView.addObject("mode2", "MODE_RES");
        modelAndView.setViewName("sql_execution");
        return modelAndView;
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return user;
    }
}
