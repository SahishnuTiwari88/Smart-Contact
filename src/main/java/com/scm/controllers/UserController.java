package com.scm.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.helper.Helper;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    // user dashboard page
   // @PostMapping("/dashboard")
   @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        return "user/dashboard"; // user folder and dashboard page
    }

    // user profile page
    @GetMapping("/profile")
    public String userProfile(Authentication authentication) {
         String emailOfLoggedInUser = Helper.getEmailOfLoggedInUser(authentication);
         log.info("User logged in: {} ",emailOfLoggedInUser);
         //once we get user email above then we can fetch user detail frim DB
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