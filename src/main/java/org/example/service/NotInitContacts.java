package org.example.service;

import org.example.entity.ContactEntity;
import org.example.service.ContactService;
import org.example.service.InitContactsInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Profile("default")
public class NotInitContacts implements InitContactsInterface {

    private final ContactService contactService;

    public static List<ContactEntity> contacts;

    public NotInitContacts(ContactService contactService) {
        this.contactService = contactService;
    }

    @Bean
    @Override
    public void getInitContactsInterface() {
        List<ContactEntity> contacts = new ArrayList<>();
        contactService.contactMenu(contacts);
    }
}
