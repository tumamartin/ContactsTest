package com.company;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Contact {
    private String name;
    private String surname;
    private String number;

    private Contact(String name, String surname, String number) {
        this.name = name;
        this.surname = surname;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNumber() {
        return number;
    }

    public boolean hasNumber() {
        return !number.equals("") && !number.equals("[no number]");
    }

    @Override
    public String toString() {
        String str = "";
        if (name != null) {
            str += name;
        }
        if (surname != null) {
            str += " " + surname;
        }
        if (number != null) {
            str +=", " + number;
        }
        return str;
    }

    static class Builder {
        private String name = "";
        private String surname = "";
        private String number = "";

        Builder() {
        }

        Builder(Contact contact) {
            this.name = contact.getName();
            this.surname = contact.getSurname();
            this.number = contact.getNumber();
        }

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        Contact build() {
            return new Contact(name, surname, number);
        }

    }

}

class Contacts {
    private ArrayList<Contact> contactArrayList;
    private Scanner scanner = new Scanner(System.in);


    public Contacts() {
        contactArrayList = new ArrayList<Contact>();
    }

    private boolean validateNumber(String number) {
        Pattern pattern = Pattern.compile("(" + "\\+?[\\d[a-z]]+([ -][\\d[a-z]]{2,})*" + "|" +
                "\\+?\\([\\d[a-z]]+\\)([ -][\\d[a-z]]{2,})*" + "|" +
                "\\+?[\\d[a-z]]+[ -]\\([\\d[a-z]]{2,}\\)([ -][\\d[a-z]]{2,})*" + ")", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    private String scanName() {
        System.out.println("Enter the name:");
        return scanner.nextLine();
    }

    private String scanSurname() {
        System.out.println("Enter the surname:");
        return scanner.nextLine();
    }

    private String scanNumber() {
        System.out.println("Enter the number:");
        String number = scanner.nextLine();
        if (!validateNumber(number)) {
            number = "[no number]";
            System.out.println("Wrong number format!");
        }
        return number;
    }

    private void addContact() {
        Contact contact = new Contact.Builder().setName(scanName())
                        .setSurname(scanSurname())
                        .setNumber(scanNumber())
                        .build();

        contactArrayList.add(contact);

        System.out.println("The record added.");
    }

    private void countContacts() {
        System.out.println("The Phone Book has " + contactArrayList.size() + " record" +
                (contactArrayList.size() == 1 ? "." : "s."));
    }

    private void listContacts() {
        for (Contact contact: contactArrayList)
        {
            System.out.println((contactArrayList.indexOf(contact) + 1) + ". " + contact.toString());
        }
    }

    private Contact selectContacts() {
        listContacts();
        System.out.println("Select a record:");
        return contactArrayList.get(Integer.parseInt(scanner.nextLine())-1);
    }

    private void editContacts() {
        if (contactArrayList.isEmpty()) {
            System.out.println("No records to edit!");
        } else {
            Contact contact = selectContacts();
            System.out.println("Select a field (name, surname, number):");
            String field = scanner.nextLine();
            switch (field) {
                case "name":
                    contactArrayList.set(contactArrayList.indexOf(contact),
                            new Contact.Builder(contact).setName(scanName()).build());
                    break;
                case "surname":
                    contactArrayList.set(contactArrayList.indexOf(contact),
                            new Contact.Builder(contact).setSurname(scanSurname()).build());
                    break;
                case "number":
                    contactArrayList.set(contactArrayList.indexOf(contact),
                            new Contact.Builder(contact).setNumber(scanNumber()).build());
                    break;
            }
        }
    }

    private void removeContacts() {
        if (contactArrayList.isEmpty()) {
            System.out.println("No records to remove!");
        } else {
            Contact contact = selectContacts();
            contactArrayList.remove(contact);
            System.out.println("The record removed!");
        }
    }

    public void loop() {
        loop: while (true) {
            System.out.println("Enter action (add, remove, edit, count, list, exit):");
            String action = scanner.nextLine();
            switch (action) {
                case "add":
                    addContact();
                    break;
                case "remove":
                    removeContacts();
                    break;
                case "edit":
                    editContacts();
                    break;
                case "count":
                    countContacts();
                    break;
                case "list":
                    listContacts();
                    break;
                case "exit":
                    break loop;
                default:
                    System.out.println("Unknown action");
            }
        }
    }

}

public class Main {

    public static void main(String[] args) {
	Contacts contacts = new Contacts();
    contacts.loop();
    }
}
