package com.company;

import java.util.ArrayList;
import java.util.Scanner;

class Contact {
    private String name;
    private String surname;
    private String number;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

class Contacts {
    private ArrayList<Contact> contactArrayList;

    public Contacts() {
        contactArrayList = new ArrayList<Contact>();
    }

    public void getContactfromKeyboard() {
        Scanner scanner = new Scanner(System.in);
        Contact contact = new Contact();
        System.out.println("Enter the name of the person:");
        contact.setName(scanner.next());
        System.out.println("Enter the surname of the person:");
        contact.setSurname(scanner.next());
        System.out.println("Enter the number:");
        contact.setNumber(scanner.next());

        contactArrayList.add(contact);

        System.out.println();
        System.out.println("A record created!");
        System.out.println("A Phone Book with a single record created!");
    }
}

public class Main {

    public static void main(String[] args) {
	Contacts contacts = new Contacts();
    contacts.getContactfromKeyboard();
    }
}
