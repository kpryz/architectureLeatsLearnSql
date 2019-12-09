package edu.learnsql.controller;

import edu.learnsql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/task")
public class UserSQLTaskController {
    @Autowired
    private edu.learnsql.service.SQLTaskService SQLTaskService;

    @Autowired
    private edu.learnsql.service.SQLTaskProgressService SQLTaskProgressService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/execute", method = RequestMethod.GET)
    public ModelAndView newUserTask() {
        return null;
    }
}
