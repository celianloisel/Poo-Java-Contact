package model;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact implements Comparable<Contact> {
    public static final String separator = ";";

    private String lastname;
    private String firstname;
    private String number;
    private String email;
    private Date birthday;

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname.toUpperCase();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) throws ParseException {
        Pattern pat = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$");
        Matcher test = pat.matcher(number);
        if (test.matches()) {
            this.number = number;
        } else {
            throw new ParseException("Format du numéro incorrect.", 0);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws ParseException {
        Pattern pat = Pattern.compile("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9_.-]{2,}\\.[a-zA-Z.]{2,10}$");
        Matcher test = pat.matcher(email);
        if (test.matches()) {
            this.email = email;
        } else {
            throw new ParseException("Format du mail incorrect.", 0);
        }
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) throws ParseException {
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        this.birthday = dtf.parse(birthday);
    }

    public void save() throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contact.csv", true)));
        try {
            pw.println(this.toString());
        } finally {
            pw.close();
        }
    }

    public static void save2(List<Contact> contacts) throws IOException {

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contact.csv", false)));
        try {
            for (Contact contact : contacts) {
                pw.println(contact.toString());
            }
        } finally {
            pw.close();
        }
    }

    public static ArrayList<Contact> lister() throws IOException {
        ArrayList<Contact> list = new ArrayList<>();
        BufferedReader buf = new BufferedReader(new FileReader("contact.csv"));
        try {
            String line = buf.readLine();
            while (line != null) {
                String[] tab = line.split(";");
                Contact contact = new Contact();
                contact.setLastname(tab[0]);
                contact.setFirstname(tab[1]);
                contact.setEmail(tab[2]);
                contact.setNumber(tab[3]);
                contact.setBirthday(tab[4]);
                list.add(contact);
                line = buf.readLine();
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Erreur de lecture sur le fichier");
        } finally {
            buf.close();
        }
        return list;
    }

    @Override
    public String toString() {
        // return this.getNom() + ";" + this.getPrenom();*
        StringBuilder build = new StringBuilder();
        build.append(getLastname());
        build.append(separator);
        build.append(getFirstname());
        build.append(separator);
        build.append(getEmail());
        build.append(separator);
        build.append(getNumber());
        build.append(separator);
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        build.append(dtf.format(getBirthday()));
        return build.toString();
    }

    @Override
    public int compareTo(Contact o) {
        if (this.getLastname().equals(o.getLastname())) {
            return this.getFirstname().compareTo(o.getFirstname());
        }
        return this.getLastname().compareTo(o.getLastname());
    }
}