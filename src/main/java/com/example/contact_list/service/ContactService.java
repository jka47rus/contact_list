package com.example.contact_list.service;

import com.example.contact_list.model.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> findAll();

    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(Integer id);

    Contact findById(Integer id);
}
