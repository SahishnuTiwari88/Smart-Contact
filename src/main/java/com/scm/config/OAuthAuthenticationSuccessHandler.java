package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helper.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepo userRepo;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        log.info("OAuthAuthenticationSuccessHandler Logged Success");

        // before redirect we can save data to the database
        // we will use getPrincipal() from authentication and typecast it to
        // DefaultOauth2User
        // to get User details
        // DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        /*
         * log.info(user.getName());
         * user.getAttributes().forEach((key,value)->{
         * log.info("KEY "+key+ "VALUE "+value);
         * });
         * log.info(user.getAuthorities().toString());
         */

        // get user info
        /*
         * String email = user.getAttribute("email").toString();
         * String name = user.getAttribute("name").toString();
         * String picture = user.getAttribute("picture").toString();
         * 
         * // create new User and add above detail to it
         * User user1 = new User();
         * user1.setEmail(email);
         * user1.setName(name);
         * user1.setProfilePic(picture);
         * // add some dummy details to user
         * user1.setPassword("password");
         * user1.setUserId(UUID.randomUUID().toString());
         * user1.setProvider(Providers.GOOGLE); // because we are using OAuth2 for
         * google sign in
         * user1.setEnabled(true); // b/c we need to enable account
         * user1.setEmailVerified(true);
         * user1.setProviderUserId(user.getName());
         * user1.setRoleList(List.of(AppConstants.ROLE_USER));
         * user1.setAbout("This account is created using google OAuth2");
         * // we save above user data to database
         * User user2 = userRepo.findByEmail(email).orElse(null);
         * if(user2 == null){
         * userRepo.save(user1);
         * log.info("User saved...");
         * }
         */

        // above is only for when using login with google

        // now we fetch provider info(google,github etc) above code remains same i.e. to
        // save user to DB we just need to find provider first
        var OAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        log.info(authorizedClientRegistrationId);

        // it is used to fetch user info
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

        // set default property which is mandatory
        User user1 = new User();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setRoleList(List.of(AppConstants.ROLE_USER));
        user1.setEmailVerified(true);
        user1.setEnabled(true);
        user1.setPassword("password");

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            // write google code
            user1.setEmail(user.getAttribute("email").toString());
            user1.setName(user.getAttribute("name").toString());
            user1.setProfilePic(user.getAttribute("picture").toString());
            user1.setProviderUserId(user.getName());
            user1.setAbout(AppConstants.GOOGLE_LOGIN);
            user1.setProvider(Providers.GOOGLE);

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            // github code
            String email = user.getAttribute("email") != null ? user.getAttribute("email").toString()
                    : user.getAttribute("login").toString() +"@gmail.com";
            String picture = user.getAttribute("avatar_url").toString();
            String name = user.getAttribute("login").toString();
            String providerUserId = user.getName();

            user1.setEmail(email);
            user1.setProfilePic(picture);
            user1.setName(name);
            user1.setProviderUserId(providerUserId);
            user1.setAbout(AppConstants.GITHUB_LOGIN);
            user1.setProvider(Providers.GITHUB);

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("facebook")) {
            // facebook code
        } else {
            log.info("Unknown Provider " + authorizedClientRegistrationId);
        }


        //save the user
        User user2 = userRepo.findByEmail(user1.getEmail()).orElse(null);
          if(user2 == null){
          userRepo.save(user1);
          log.info("User saved...");
          }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");

    }

}

// when onAuthenticationSuccess method executes then only authentication success