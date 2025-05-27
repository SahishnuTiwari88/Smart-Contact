package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @RequestMapping("/base")
    public String base() {
        return "base";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/services")
    public String services() {
        return "services";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        // when hit /register then blank/empty userform object created and it will be
        // available to the signup page, but in order to use userForm blank object on
        // signup page we need to define an action in form tag as
        // data-th-object="@{'userForm'}"
        UserForm userForm = new UserForm();

        // passing default value to form, if not passes then the blank userform will be
        // passed and used to store data from signup page
        // userForm.setName("Sarvesh");
        // userForm.setAbout("**********");

        model.addAttribute("userForm", userForm);

        // if we want we can send default data also to the signup page
        return "signup";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "contact";
    }

    // we can use @RequestMapping(value="/register", method=RequestMethod.POST)
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult ,RedirectAttributes redirectAttributes) {
        System.out.println("Process Register....." + userForm);

        //@Valid added for validation, then to check if it has error then use 'BindingResult' & if there is 
        //error check it in if condition and return to signup page itself
        if(rBindingResult.hasErrors()){
            return "signup";
        }

        //steps involved
        // 1. fetch form data
        // 2. validate the form
        // 3. save data to database(we need to save data to database but saveUser takes
        // User but we have data in
        // UserForm so first we create user builder and take data from UserForm then set
        // it into User)

        // builder pattern
        User user = User.builder().name(userForm.getName()).email(userForm.getEmail()).password(userForm.getPassword())
                .about(userForm.getAbout()).phoneNumber(userForm.getPhoneNumber()).build();
        userService.saveUser(user);
        System.out.println("User Saved " + user);

        //4. message="Registration successfull" use httpsession for message
        //session.setAttribute("message", "Registration Successfull..");
        redirectAttributes.addFlashAttribute("message","Registration Successfull..");

        //5. redirect to register page
        return "redirect:/signup";
    }

}
