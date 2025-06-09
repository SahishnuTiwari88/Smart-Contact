package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.entities.Contact;

public interface ContactService {
    // add contact to db
    Contact saveContact(Contact contact);

    // update contact
    Contact updateContact(Contact contact);

    // get contact
    Contact getById(String id);

    // get all Contact
    List<Contact> getAll();

    // delete contact
    void deleteContact(String id);

    // serach contact
    List<Contact> search(String field, String keyword);
    // where field might be name,email or phonenumber and keyword will hold actual
    // value

    // Get contacts by User id
    Page<Contact> getByUserId(String userId, int pageNo, int size, String sort, String direction);
}
