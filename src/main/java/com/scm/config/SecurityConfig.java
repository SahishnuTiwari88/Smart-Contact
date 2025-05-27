package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scm.services.impl.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /* 
   1. // user create and login using java code with in-memory service i.e. hard coding
    @Bean
    public UserDetailsService userDetailsService() {

        // create user(hardcoding not from DB)
        UserDetails user = User.withDefaultPasswordEncoder()
        .username("admin123")
        .password("admin123")
        .roles("ADMIN","USER")
        .build();

        //other user
        UserDetails user1 = User
        .withUsername("user123")
        .password("user123")
        .roles("USER")
        .build();

        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user,user1);
        return inMemoryUserDetailsManager;
    }
        */

        // 2. configure/use user from database for login purpose
        @Bean
        public AuthenticationProvider authenticationProvider(){
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            //user detail service object
            daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
            //password encoder object
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
            return daoAuthenticationProvider;
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

}

// here we do configuration related to security like login page and it will
// take username and password from database

// Spring security uses userdetailservice for it's working, while logging it
// fetches user (for feching user it uses userdetailservice) this service has a
// method 'loadUserByUsername'
// call 'loadUserByUsername' to load the user from dB and then compare loaded
// user password with our user
// password if match allow(login)
// where userdetailservice is an interface provided by spring security it has a
// method named loadUserByUsername