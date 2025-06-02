package com.scm.helper;

import java.security.Principal;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class Helper {
    public static String getEmailOfLoggedInUser(Authentication authentication) {

        // now here we have to write logic to get email
        if (authentication instanceof OAuth2AuthenticationToken) { // to check login with OAuth2
            var OAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var authorizedClientRegistrationId = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
            String userName = "";
            // if logged in using OAuth google
            if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
                System.out.println("Email for google");
                userName = user.getAttribute("email").toString();

            }
            // if logged in using OAuth github
            else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
                System.out.println("Email for github");
                userName = user.getAttribute("email") != null ? user.getAttribute("email").toString()
                        : user.getAttribute("login").toString() + "@gmail.com";

            }
            return userName;

        } else {
            // if user logged in using email & password
            System.out.println("Email for login page");
            return authentication.getName();
        }

    }
}

/*
 * In Spring Security, the principal represents the currently authenticated
 * user.
 * Think of the principal as the identity of the logged-in user — whether they
 * logged in using username/password, OAuth (like Google/GitHub), or any other
 * method.
 * You can use it to get the username, roles, email, etc.
 * 
 * Then we user above Helper class at place where we need to show user info like
 * email
 * in case of UserController.java where we need to show username i.e. email for
 * 'profile & dashboard endpoint'
 */

// in order to check login with OAuth2 we use if condition to check 'if
// principal is instanceOf Oauth2AuthenticatedPrincipal'
// if it is then we get it's email for google and github, if logged in using
// username and password then just return
// principal.getName()