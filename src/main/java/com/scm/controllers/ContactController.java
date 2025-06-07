package com.scm.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.contactForm;
import com.scm.helper.Helper;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService; // needed to find user

    @Autowired
    private ImageService imageService;

    @RequestMapping("/add")
    public String addContactView(Model model) {
        // here we pass Model as parameter above and create a empty object of
        // contactForm.java
        // and add it to model so that this empty object can be pass & used in the
        // add_contact.html form
        // to pass data from form and also to display default value from here on form.
        // here main task is to send blank/empty onject to the form so that user can add
        // info in the form data field
        // and sent it to other endpoint where it gets saved to db
        contactForm contactForm = new contactForm();
        // contactForm.setName("Sarvesh");
        // contactForm.setEmail("iamtiwari@gmail.com");
        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    // Above form(add_contact.html) is submitted to the action
    // url(data-th-action="@{/user/contacts/addContact}") that we handle
    // below

    @PostMapping("/addContact")
    public String saveContact(@Valid @ModelAttribute contactForm contactForm, BindingResult result,
            Authentication authentication, RedirectAttributes redirect) {
        System.out.println(contactForm);

        // NOTE: We need to save data to DB so first we create contactRepo and service

        // we are getting data in contactForm but need to save contact, so we can create
        // our new object for contact and add contactform info there then save to db, we
        // can also do it using builder

        // to get user we need authentication and then use Helper class to get current
        // user mail

        // 1. Need to do validation
        if (result.hasErrors()) {
            return "user/add_contact";
        }

        // find user
        String emailOfLoggedInUser = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(emailOfLoggedInUser);

        // 2. process contact picture
        // to check we are getting contact image file or not
        log.info("Contact image info : {} " + contactForm.getContactImage().getOriginalFilename());
        //generate randome file name & it is public id
        String filename = UUID.randomUUID().toString();
        // image processing code i.e. file upload karne ka code
        String imageUrl = imageService.uploadImage(contactForm.getContactImage(),filename);

        // convert contactForm-->contact
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        contact.setWebsite(contactForm.getWebsite());
        contact.setLinkedeIn(contactForm.getLinkedIn());
         // set contact picture
        contact.setPicture(imageUrl);
        contact.setCloudinaryImagePublicId(filename);
        contact.setUser(user);
       

        // save contact to db
        contactService.saveContact(contact);
        redirect.addFlashAttribute("message", "Contact Added Successfully");

        return "redirect:/user/contacts/add";
    }

}

// In case to redirect we mention the endpoint not the page name