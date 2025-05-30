package com.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scm.services.impl.CustomUserDetailsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /*
     * 1. // user create and login using java code with in-memory service i.e. hard
     * coding
     * 
     * @Bean
     * public UserDetailsService userDetailsService() {
     * 
     * // create user(hardcoding not from DB)
     * UserDetails user = User.withDefaultPasswordEncoder()
     * .username("admin123")
     * .password("admin123")
     * .roles("ADMIN","USER")
     * .build();
     * 
     * //other user
     * UserDetails user1 = User
     * .withUsername("user123")
     * .password("user123")
     * .roles("USER")
     * .build();
     * 
     * var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user,user1);
     * return inMemoryUserDetailsManager;
     * }
     */

    // 2. configure/use user from database for login purpose(AuthenticationProvider
    // configuration)
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user detail service object
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        // password encoder object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // configure security filter chain to make sure some pages are secured and some
    // are not
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // httpSecurity helps in deciding which page to configure, routes,roles
        // allowed(which user can access what page)
        // when httpSecurity.build() calls default security filter chain

        // 1. configuration(route configure)
        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/user/**").authenticated() // user need to authenticated
                    .anyRequest().permitAll();
        });

        // need to configure loginPage(support form based authentication)if we need
        // default we use withDefaults from Customizer as 
        //httpSecurity.formLogin(Customizer.withDefaults());

        httpSecurity.formLogin(form -> {
            // making our own login form
            form.loginPage("/login");
            form.loginProcessingUrl("/authenticate"); // form submit at this url
            form.successForwardUrl("/user/dashboard");
            form.failureForwardUrl("/login?error=true");
            form.usernameParameter("email"); // username as email from login form
            form.passwordParameter("password");
           
           /*  form.failureHandler(new AuthenticationFailureHandler() {

                @Override
                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException exception) throws IOException, ServletException {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
                }

            });
            form.successHandler(new AuthenticationSuccessHandler() {

                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) throws IOException, ServletException {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
                }

            });
            */

        });

        //disabling CSRF
        httpSecurity.csrf().disable();
         //customize logout(logout needs to configure separetly)
         httpSecurity.logout(formOut->{
            formOut.logoutUrl("/logout");
            //once logout return it to login success url
            formOut.logoutSuccessUrl("/login?logout=true");
         });

        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
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