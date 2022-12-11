package model;

import java.util.Comparator;

public class Date implements Comparator<Contact> {
    @Override
    public int compare(Contact contact1, Contact contact2) {
        return contact1.getBirthday().compareTo(contact2.getBirthday());
    }
}
