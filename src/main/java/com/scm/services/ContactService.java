package com.scm.services;

import java.util.List;

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
    List<Contact> search(String name, String email, String phoneNumber);

    //Get contacts by User id
    List<Contact> getByUserId(String userId);
}
