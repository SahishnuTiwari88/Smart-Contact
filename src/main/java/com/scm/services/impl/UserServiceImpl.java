package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.exceptions.ResourceNotFoundException;
import com.scm.helper.AppConstants;
import com.scm.repositories.UserRepo;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    // To add/implement the methods press ctrl+.

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // to use logger use slf4j
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        //we need to generate userId dynamically before saving
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        //set password encoder
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set default role for user
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        log.info("User info saved to database");
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public User updateUser(User user) {
        // user found
        User user2 = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // updating old user(user2) info with new user(user)
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());
        // save
        User saveUser = userRepo.save(user2);
        // if saveUser is null then return empty else return data if user is optional
        // then use below line
        // return Optional.ofNullable(saveUser);
        return saveUser;
    }

    @Override
    public void deleteUser(String id) {
        User userInfo = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Id not found"));
        userRepo.delete(userInfo);
    }

    @Override
    public boolean isUserExist(String userId) {
        User userDe = userRepo.findById(userId).orElse(null);
        return userDe != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
       User userEmail = userRepo.findByEmail(email).orElse(null);
       return userEmail != null ? true : false;
    }

    @Override
    public Optional<List<User>> getAllUsers() {
        return Optional.of(userRepo.findAll());
    }

}
