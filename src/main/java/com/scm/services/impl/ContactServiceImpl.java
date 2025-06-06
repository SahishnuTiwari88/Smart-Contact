package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.exceptions.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact saveContact(Contact contact) {
        contact.setId(UUID.randomUUID().toString());
        return contactRepo.save(contact);

    }

    @Override
    public Contact updateContact(Contact contact) {
        Optional<Contact> contact2 = contactRepo.findById(contact.getId());
        if (contact2 != null) {
            return "";
        } else {
            throw new ResourceNotFoundException("Contact not present");
        }
    }

    @Override
    public Contact getById(String id) {
        Contact contactById = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not present with id " + id));
        return contactById;
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public void deleteContact(String id) {
        contactRepo.deleteById(id);
        // or we can first find by id then pass entity to delete
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        // we can search data based on name or email or phoneNumber
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

}
