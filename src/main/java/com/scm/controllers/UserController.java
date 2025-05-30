package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    // user dashboard page
   // @PostMapping("/dashboard")
   @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        return "user/dashboard"; // user folder and dashboard page
    }

    // user profile page
    @GetMapping("/profile")
    public String userProfile() {
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