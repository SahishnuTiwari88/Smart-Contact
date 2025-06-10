package com.scm.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
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

    // search contact based on field(name,email,phone) and keyword(actual data
    // passed)
    @Override
    public Page<Contact> search(User user, String field, String keyword, int page, int size, String sortBy,
            String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        if (field.equals("name")) {
            return contactRepo.findByUserAndNameIgnoreCase(user, keyword, pageable);
        } else if (field.equals("email")) {
            return contactRepo.findByUserAndEmailIgnoreCase(user, keyword, pageable);
        } else if (field.equals("phone")) {
            return contactRepo.findByUserAndPhoneNumber(user, keyword, pageable);
        } else {
            return null;
        }
    }

    @Override
    public Page<Contact> getByUserId(String userId, int pageNo, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, size, sort);
        return contactRepo.findByUserId(userId, pageable);
    }

}
// in sortBy we mention field on which do sorting
// in direction we mention ascending or decending order
