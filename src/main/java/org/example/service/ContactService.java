package org.example.service;


import org.example.entity.ContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class ContactService {

    @Autowired
    @Value("${contacts.save.path}")
    private String contactsSavePath;

    public void contactMenu(List<ContactEntity> contactsNew){
        List<ContactEntity> contacts = new ArrayList<>(contactsNew);
        for (;;){
            for (String s : Arrays.asList("Приложение «Контакты»", "L - Вывести все имеющиеся контакты пользователя",
                    "A - Добавить новый контакт в список контактов (Формат ввода для обработки данных: Ф. И. О.;" +
                            " номер телефона; адрес электронной почты)", "D - Удалить контакт по email",
                    "S - Сохранить контакты в файл", "Выход - любой символ кроме вышеуказанных")) {
                System.out.println(s);
            }
            Scanner command = new Scanner(System.in);
            String commandIn = command.next();
            if (commandIn != null) {
                switch(commandIn.toUpperCase()) {
                    case "L":
                        printContacts(contacts);
                        break;
                    case "A":
                        addContact(contacts);
                        break;
                    case "D":
                        deleteContact(contacts);
                        break;
                    case "S":
                        saveContacts(contacts);
                        break;
                    default :
                        System.exit(0);
                }
            }
        }
    }

    public void saveContacts(List<ContactEntity> contacts) {

        if (contactsSavePath == null) System.out.println("В application.properties нет пути и/или имени файла: " + contactsSavePath);
        File file = new File(contactsSavePath);
        StringBuilder text = new StringBuilder();

        for (ContactEntity contact : contacts){
            text.append(contact.getFullName());
            text.append(";");
            text.append(contact.getPhoneNumber());
            text.append(";");
            text.append(contact.getEmail());
            text.append("\n");
        }

        try (FileOutputStream fos = new FileOutputStream(file);
             PrintStream out = new PrintStream(fos))
        {
            out.print(text);
            System.out.println("Список контактов сохранен в файл: " + file);
        }
        catch (IOException e) {
            System.out.println("Ошибка сохранения в файл: " + file);
            e.printStackTrace();
        }
    }

    private void deleteContact(List<ContactEntity> contacts) {
        System.out.println("Для удаления контакта введите адрес электронной почты");
        Scanner contactDelete = new Scanner(System.in);
        String contactString = contactDelete.next().strip();
        int counter = -1;
        for (int a = 0; a < contacts.size(); a++){
            ContactEntity contactEntity = contacts.get(a);
            if (contactEntity.getEmail().equals(contactString)){
                counter = a;
            }
        }
        if (counter != -1) {
            contacts.remove(counter);
            System.out.println("Адрес электронной почты " + contactString + " удален");
        } else {
            System.out.println("Адрес электронной почты " + contactString + " не найден");
        }
    }

    private void addContact(List<ContactEntity> contacts) {
        System.out.println("Формат ввода для обработки данных: Ф. И. О.; номер телефона; адрес электронной почты");
        Scanner contact = new Scanner(System.in);
        String contactString = contact.nextLine();
        ContactEntity contactEntity = new ContactEntity();
        int counter = contactString.indexOf(";");
        if (counter > 0) {
            contactEntity.setFullName(contactString.substring(0, counter).strip());
            int counterTwo = contactString.indexOf(";", counter + 1);
            if (counterTwo > 0) {
                contactEntity.setPhoneNumber(contactString.substring(counter + 1, counterTwo).strip());
                contactEntity.setEmail(contactString.substring(counterTwo + 1).strip());
                contacts.add(contactEntity);
            } else {
                System.out.println("Неверный формат");
            }
        } else {
            System.out.println("Неверный формат");
        }
    }

    private void printContacts(List<ContactEntity> contacts) {
        if (contacts.isEmpty()){
            System.out.println("Список контактов пуст");
        } else {
            contacts.forEach(contactEntity -> {
                System.out.println(contactEntity.getFullName() + " | " + contactEntity.getPhoneNumber()
                        + " | " + contactEntity.getEmail());
            });
        }
    }
}
