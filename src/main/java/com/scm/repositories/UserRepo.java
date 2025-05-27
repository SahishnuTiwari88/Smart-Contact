package com.scm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scm.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    // here we can write extra db related operation if needed
    Optional<User> findByEmail(String email); // we don't need to write query as it is given by data jpa
}
// repository is used to interact with the database