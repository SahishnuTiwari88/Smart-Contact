package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.services.UserService;

@ControllerAdvice
public class RootController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void loggedInUser(Model model, Authentication authentication) {
        //check if user not authenticated
        if(authentication == null){
            return;
        }
        String emailOfLoggedInUser = Helper.getEmailOfLoggedInUser(authentication);
        log.info("User logged in: {} ", emailOfLoggedInUser);
        User user = userService.getUserByEmail(emailOfLoggedInUser);
        if(user == null){
             model.addAttribute("userDetail", null);
        }else{
        log.info(user.getAbout());
        log.info(user.getUserId());
        log.info(user.getEmail());
        log.info(user.getAuthorities().toString());
        model.addAttribute("userDetail", user);
        }
    }

}
