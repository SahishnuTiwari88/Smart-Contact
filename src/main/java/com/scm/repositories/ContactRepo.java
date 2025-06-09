package com.scm.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contact;
import com.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {
    // find the contact by userId
    // costom finder method
    List<Contact> findByUser(User user);

    // custom query method
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    Page<Contact> findByUserId(@Param("userId") String userId, Pageable pageable);

    List<Contact> findByNameIgnoreCase(String name);

    List<Contact> findByEmailIgnoreCase(String email);

    List<Contact> findByPhoneNumber(String phoneNumber);

    // in order to implement pagination we will replace List-->Page(coming from
    // domain.page) and pass one pageble object as argument

    // both are same

}
