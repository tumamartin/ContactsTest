package com.company;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class ContactField {
    String name;
    String number;

    public abstract String toShortString();

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public boolean hasNumber() {
        return !number.equals("") && !number.equals("[no number]");
    }
}

class ContactPerson extends ContactField {
    private String surname;

    private ContactPerson(String name, String surname, String number) {
        this.name = name;
        this.surname = surname;
        this.number = number;
    }

    public String getSurname() {
        return surname;
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

    public String toShortString() {
        String str = "";
        if (name != null) {
            str += name;
        }
        if (surname != null) {
            str += " " + surname;
        }
        return str;
    }

    static class Builder {
        private String name = "";
        private String surname = "";
        private String number = "";

        Builder() {
        }

        Builder(ContactPerson contactPerson) {
            this.name = contactPerson.getName();
            this.surname = contactPerson.getSurname();
            this.number = contactPerson.getNumber();
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

        ContactPerson build() {
            return new ContactPerson(name, surname, number);
        }

    }

}

class ContactCompany extends ContactField {
    private String address;

    private ContactCompany(String name, String address, String number) {
        this.name = name;
        this.address = address;
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        String str = "";
        if (name != null) {
            str += name;
        }
        if (address != null) {
            str += " " + address;
        }
        if (number != null) {
            str +=", " + number;
        }
        return str;
    }

    public String toShortString() {
        String str = "";
        if (name != null) {
            str += name;
        }
        return str;
    }

    static class Builder {
        private String name = "";
        private String address = "";
        private String number = "";

        Builder() {
        }

        Builder(ContactCompany contactCompany) {
            this.name = contactCompany.getName();
            this.address = contactCompany.getAddress();
            this.number = contactCompany.getNumber();
        }

        ContactCompany.Builder setName(String name) {
            this.name = name;
            return this;
        }

        ContactCompany.Builder setAddress(String surname) {
            this.address = address;
            return this;
        }

        ContactCompany.Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        ContactCompany build() {
            return new ContactCompany(name, address, number);
        }

    }

}

class Contacts {
    private ArrayList<ContactField> contactArrayList;
    private Scanner scanner = new Scanner(System.in);


    public Contacts() {
        contactArrayList = new ArrayList<ContactField>();
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

    private String scanAddress() {
        System.out.println("Enter the address:");
        return scanner.nextLine();
    }

    private void addContact() {
        System.out.println("Enter the type (person, organization):");
        String type = scanner.nextLine();

        switch (type) {
            case "person":
                ContactField contactPerson = new ContactPerson.Builder().setName(scanName())
                        .setSurname(scanSurname())
                        .setNumber(scanNumber())
                        .build();
                contactArrayList.add(contactPerson);
                System.out.println("The record added.");
                break;
            case "organization":
                ContactField contactCompany = new ContactCompany.Builder().setName(scanName())
                        .setAddress(scanAddress())
                        .setNumber(scanNumber())
                        .build();
                contactArrayList.add(contactCompany);
                System.out.println("The record added.");
                break;
            default:
                System.out.println("Unknown action");
        }
    }

    private void listContacts() {
        for (ContactField contact: contactArrayList)
        {
            System.out.println((contactArrayList.indexOf(contact) + 1) + ". " + contact.toShortString());
        }
    }

    private void removeContact() {
        if (contactArrayList.isEmpty()) {
            System.out.println("No records to remove!");
        } else {
            ContactField contact = selectContact();
            contactArrayList.remove(contact);
            System.out.println("The record removed!");
        }
    }

    private void countContacts() {
        System.out.println("The Phone Book has " + contactArrayList.size() + " record" +
                (contactArrayList.size() == 1 ? "." : "s."));
    }


    private ContactField selectContact() {
        listContacts();
        System.out.println("Select a record:");
        return contactArrayList.get(Integer.parseInt(scanner.nextLine())-1);
    }

    private void editContact() {
        if (contactArrayList.isEmpty()) {
            System.out.println("No records to edit!");
        } else {
            ContactField contact = selectContact();
            if (contact instanceof ContactPerson) {
                editContactPerson((ContactPerson) contact);
            } else {
                editContactCompany((ContactCompany) contact);
            }
        }
    }

    private void editContactPerson(ContactPerson contact) {
        System.out.println("Select a field (name, surname, number):");
        String field = scanner.nextLine();
        switch (field) {
            case "name":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactPerson.Builder(contact).setName(scanName()).build());
                break;

            case "surname":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactPerson.Builder(contact).setSurname(scanSurname()).build());
                break;
            case "number":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactPerson.Builder(contact).setNumber(scanNumber()).build());
                break;
            }
    }


    private void editContactCompany(ContactCompany contact) {
        System.out.println("Select a field (name, address, number):");
        String field = scanner.nextLine();
        switch (field) {
            case "name":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactCompany.Builder(contact).setName(scanName()).build());
                break;

            case "address":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactCompany.Builder(contact).setAddress(scanAddress()).build());
                break;
            case "number":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactCompany.Builder(contact).setNumber(scanNumber()).build());
                break;
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
                    removeContact();
                    break;
                case "edit":
                    editContact();
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
