package com.company;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class ContactField {
    String name;
    String number;
    LocalDateTime timeCreated;
    LocalDateTime timeEdit;

    public abstract String toShortString();

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getTimeCreated() { return timeCreated; }

    public LocalDateTime getTimeEdit() { return timeEdit; }

    public boolean hasNumber() {
        return !number.equals("") && !number.equals("[no data]");
    }
}

class ContactPerson extends ContactField {
    private String surname;
    private String birthDate;
    private String gender;

    private ContactPerson(String name, String surname, String birthDate, String gender, String number, LocalDateTime timeCreated, LocalDateTime timeEdit) {
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.birthDate = birthDate;
        this.gender = gender;
        super.timeCreated = timeCreated;
        super.timeEdit = timeEdit;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthDate() { return birthDate; }

    public String getGender() { return gender; }

    @Override
    public String toString() {
        String str = "";
        if (name != null) {
            str += "Name: ";
            str += name;
            str += System.lineSeparator();
        }
        if (surname != null) {
            str += "Surname: ";
            str += surname;
            str += System.lineSeparator();
        }
        if (birthDate != null) {
            str += "Birth date: ";
            str += birthDate;
            str += System.lineSeparator();
        }
        if (gender != null) {
            str += "Gender: ";
            str += gender;
            str += System.lineSeparator();
        }
        if (number != null) {
            str += "Number: ";
            str += number;
            str += System.lineSeparator();
        }
        if (timeCreated != null) {
            str += "Time created: ";
            str += timeCreated.withNano(0);
            str += System.lineSeparator();
        }
        if (timeEdit != null) {
            str += "Time last edit: ";
            str += timeEdit.withNano(0);
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
        private String birthDate = "";
        private String gender = "";
        private LocalDateTime timeCreated;
        private LocalDateTime timeEdit;


        Builder() {
        }

        Builder(ContactPerson contactPerson) {
            this.name = contactPerson.getName();
            this.surname = contactPerson.getSurname();
            this.number = contactPerson.getNumber();
            this.birthDate = contactPerson.getBirthDate();
            this.gender = contactPerson.getGender();
            this.timeCreated = contactPerson.getTimeCreated();
            this.timeEdit = contactPerson.getTimeEdit();
        }

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        Builder setBirthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        Builder setTimeCreated(LocalDateTime timeCreated) {
            this.timeCreated = timeCreated;
            return this;
        }

        Builder setTimeEdit(LocalDateTime timeEdit) {
            this.timeEdit = timeEdit;
            return this;
        }

        ContactPerson build() {
            return new ContactPerson(name, surname, birthDate, gender, number, timeCreated, timeEdit);
        }

    }

}

class ContactCompany extends ContactField {
    private String address;

    private ContactCompany(String name, String address, String number, LocalDateTime timeCreated, LocalDateTime timeEdit) {
        this.name = name;
        this.address = address;
        this.number = number;
        super.timeCreated = timeCreated;
        super.timeEdit = timeEdit;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        String str = "";
        if (name != null) {
            str += "Organization name: ";
            str += name;
            str += System.lineSeparator();
        }
        if (address != null) {
            str += "Address: ";
            str += address;
            str += System.lineSeparator();
        }
        if (number != null) {
            str += "Number: ";
            str += number;
            str += System.lineSeparator();
        }
        if (timeCreated != null) {
            str += "Time created: ";
            str += timeCreated.withNano(0);
            str += System.lineSeparator();
        }
        if (timeEdit != null) {
            str += "Time last edit: ";
            str += timeEdit.withNano(0);
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
        private LocalDateTime timeCreated;
        private LocalDateTime timeEdit;

        Builder() {
        }

        Builder(ContactCompany contactCompany) {
            this.name = contactCompany.getName();
            this.address = contactCompany.getAddress();
            this.number = contactCompany.getNumber();
            this.timeCreated = contactCompany.getTimeCreated();
            this.timeEdit = contactCompany.getTimeEdit();
        }

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        Builder setTimeCreated(LocalDateTime timeCreated) {
            this.timeCreated = timeCreated;
            return this;
        }

        Builder setTimeEdit(LocalDateTime timeEdit) {
            this.timeEdit = timeEdit;
            return this;
        }

        ContactCompany build() {
            return new ContactCompany(name, address, number, timeCreated, timeEdit);
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

    private boolean validateBirthDate(String birthDate) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(birthDate);
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

    private String scanBirthDate() {
        System.out.println("Enter the birth date:");
        String birthDate = scanner.nextLine();
        if (!validateBirthDate(birthDate)) {
            birthDate = "[no data]";
            System.out.println("Bad birth date!");
        }
        return birthDate;
    }

    private String scanNumber() {
        System.out.println("Enter the number:");
        String number = scanner.nextLine();
        if (!validateNumber(number)) {
            number = "[no data]";
            System.out.println("Wrong number format!");
        }
        return number;
    }

    private String scanGender() {
        System.out.println("Enter the gender (M, F):");
        String gender = scanner.nextLine();
        if (!gender.equals("M") && !gender.equals("F")) {
            gender = "[no data]";
            System.out.println("Bad gender!");
        }
        return gender;
    }

    private String scanAddress() {
        System.out.println("Enter the address:");
        return scanner.nextLine();
    }

    private void addContact() {
        System.out.println("Enter the type (person, organization):");
        String type = scanner.nextLine();
        LocalDateTime time = LocalDateTime.now();

        switch (type) {
            case "person":
                ContactField contactPerson = new ContactPerson.Builder().setName(scanName())
                        .setSurname(scanSurname())
                        .setBirthDate(scanBirthDate())
                        .setGender(scanGender())
                        .setNumber(scanNumber())
                        .setTimeCreated(time)
                        .setTimeEdit(time)
                        .build();
                contactArrayList.add(contactPerson);
                System.out.println("The record added.");
                break;
            case "organization":
                ContactField contactCompany = new ContactCompany.Builder().setName(scanName())
                        .setAddress(scanAddress())
                        .setNumber(scanNumber())
                        .setTimeCreated(time)
                        .setTimeEdit(time)
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

    private void infoContact() {
        if (contactArrayList.isEmpty()) {
            System.out.println("No records to remove!");
        } else {
            ContactField contact = selectContact("info");
            System.out.println(contact.toString());
        }
    }

    private void removeContact() {
        if (contactArrayList.isEmpty()) {
            System.out.println("No records to remove!");
        } else {
            ContactField contact = selectContact("edit");
            contactArrayList.remove(contact);
            System.out.println("The record removed!");
        }
    }

    private void countContacts() {
        System.out.println("The Phone Book has " + contactArrayList.size() + " record" +
                (contactArrayList.size() == 1 ? "." : "s."));
    }


    private ContactField selectContact(String type) {
        listContacts();
        if (type.equals("edit")) {
            System.out.println("Select a record:");
        } else {
            System.out.println("Enter index to show info:");
        }
        return contactArrayList.get(Integer.parseInt(scanner.nextLine())-1);
    }

    private void editContact() {
        if (contactArrayList.isEmpty()) {
            System.out.println("No records to edit!");
        } else {
            ContactField contact = selectContact("edit");
            if (contact instanceof ContactPerson) {
                editContactPerson((ContactPerson) contact);
            } else {
                editContactCompany((ContactCompany) contact);
            }
        }
    }

    private void editContactPerson(ContactPerson contact) {
        System.out.println("Select a field (name, surname, birthdate, gender, number):");
        String field = scanner.nextLine();
        switch (field) {
            case "name":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactPerson.Builder(contact).setName(scanName()).setTimeEdit(LocalDateTime.now()).build());
                break;

            case "surname":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactPerson.Builder(contact).setSurname(scanSurname()).setTimeEdit(LocalDateTime.now()).build());
                break;

            case "birthdate":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactPerson.Builder(contact).setBirthDate(scanBirthDate()).setTimeEdit(LocalDateTime.now()).build());
                break;

            case "gender":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactPerson.Builder(contact).setGender(scanGender()).setTimeEdit(LocalDateTime.now()).build());
                break;

            case "number":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactPerson.Builder(contact).setNumber(scanNumber()).setTimeEdit(LocalDateTime.now()).build());
                break;
            }
    }


    private void editContactCompany(ContactCompany contact) {
        System.out.println("Select a field (name, address, number):");
        String field = scanner.nextLine();
        switch (field) {
            case "name":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactCompany.Builder(contact).setName(scanName()).setTimeEdit(LocalDateTime.now()).build());
                break;

            case "address":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactCompany.Builder(contact).setAddress(scanAddress()).setTimeEdit(LocalDateTime.now()).build());
                break;
            case "number":
                contactArrayList.set(contactArrayList.indexOf(contact),
                        new ContactCompany.Builder(contact).setNumber(scanNumber()).setTimeEdit(LocalDateTime.now()).build());
                break;
        }
    }




    public void loop() {
        loop: while (true) {
            System.out.println("Enter action (add, remove, edit, count, info, exit):");
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
                case "info":
                    infoContact();
                    break;
                case "exit":
                    break loop;
                default:
                    System.out.println("Unknown action");
            }
            System.out.println();
        }
    }

}

public class Main {

    public static void main(String[] args) {
	Contacts contacts = new Contacts();
    contacts.loop();
    }
}
