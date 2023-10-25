package org.example.service;

import org.example.entity.ContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Component
@Profile("init")
public class InitContacts implements InitContactsInterface {

    private final ContactService contactService;

    @Autowired
    @Value("${contacts.initPath}")
    private String contactsInitPath;

    public InitContacts(ContactService contactService) {
        this.contactService = contactService;
    }

    @Bean
    @Override
    public void getInitContactsInterface() {
        List<ContactEntity> contacts = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(contactsInitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            int counter = line.indexOf(";");
            int counterTwo = line.indexOf(";", counter + 1);
            ContactEntity contactEntity = new ContactEntity();
            contactEntity.setFullName(line.substring(0, counter));
            contactEntity.setPhoneNumber(line.substring(counter + 1, counterTwo));
            contactEntity.setEmail(line.substring(counterTwo + 1));
            contacts.add(contactEntity);
        }
        contactService.contactMenu(contacts);
    }
}
