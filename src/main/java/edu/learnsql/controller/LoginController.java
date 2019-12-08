package edu.learnsql.controller;

import edu.learnsql.entities.main.Role;
import edu.learnsql.entities.main.SQLTask;
import edu.learnsql.entities.main.SQLTaskProgress;
import edu.learnsql.entities.main.User;
import edu.learnsql.service.RoleService;
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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SQLTaskService SQLTaskService;

    @Autowired
    private SQLTaskProgressService SQLTaskProgressService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                                 "Email has already been taken"
                                 + " Check your details!");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Registration Successful.");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }


    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("403");
        return modelAndView;
    }


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Role role = new Role();
        Role role2 = new Role();
        role = roleService.findRole("ADMIN");
        role2 = roleService.findRole("USER");
        List<User> users = new ArrayList<>();
        List<User> users2 = new ArrayList<>();
        users = userService.findUserbyRole(role);
        users2 = userService.findUserbyRole(role2);
        List<SQLTask> tasks = new ArrayList<>();
        tasks = SQLTaskService.findAll();
        int taskCount = tasks.size();
        int adminCount = users.size();
        int userCount = users2.size();
        modelAndView.addObject("adminCount", adminCount); //Authentication for NavBar
        modelAndView.addObject("userCount", userCount); //Authentication for NavBar
        modelAndView.addObject("taskCount", taskCount); //Authentication for NavBar
        //-----------------------------------------
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("control", loginUser.getRole().getRole());//Authentication for NavBar
        modelAndView.addObject("auth", loginUser);
        List<SQLTaskProgress> userTasks = new ArrayList<>();
        userTasks = SQLTaskProgressService.findByUser(loginUser);
        modelAndView.addObject("userTaskSize", userTasks.size());
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
