package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger log = LoggerFactory.getLogger(UserController.class);

    // if we want logged in user info to be visible to all page we make a method
    // inside controller and annotate it with @ModelAttribute
    // it gets loaded before any method call automaticaaly and added to model for
    // any given key and then redirect to mention page(below is method)

    // @ModelAttribute
    // public void loggedInUser(Model model,Authentication authentication){
    // String emailOfLoggedInUser = Helper.getEmailOfLoggedInUser(authentication);
    // log.info("User logged in: {} ", emailOfLoggedInUser);
    // User user = userService.getUserByEmail(emailOfLoggedInUser);
    // log.info(user.getAbout());
    // log.info(user.getUserId());
    // log.info(user.getEmail());
    // log.info(user.getAuthorities().toString());
    // model.addAttribute("userDetail", user);
    // }
    // we make separate class named RootControoler so that above code can be made
    // available to all end points using @ControllerAdvice

    // user dashboard page
    // @PostMapping("/dashboard")
    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        return "user/dashboard"; // user folder and dashboard page
    }

    // user profile page
    // the user information only visible when we hit /profile point but we want it
    // to be add on all page for request then we can make a method and annotate it
    // with @ModelAttribute
    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {
        // String emailOfLoggedInUser = Helper.getEmailOfLoggedInUser(authentication);
        // log.info("User logged in: {} ", emailOfLoggedInUser);

        // once we get user email above then we can fetch user detail from DB using user
        // service
        // and this is used to shows login user profile on the web so we use Model and
        // redirect it to profile page

        // User user = userService.getUserByEmail(emailOfLoggedInUser);
        // log.info(user.getAbout());
        // log.info(user.getUserId());
        // log.info(user.getEmail());
        // log.info(user.getAuthorities().toString());
        // model.addAttribute("userDetail", user);
        // we can use this logged in user detail and show it's profile

        return "user/profile"; // user folder and dashboard page
    }

    // user add contacts page

    // user view page

    // user edit page

    // user delete page

    // user search page
}

// This controller mainly used to handle/protect all the url's which are
// starting with user i.e.
// 'user/dashborad' or (user/**)