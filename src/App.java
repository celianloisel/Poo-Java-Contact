import model.Contact;
import model.Date;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        getMenu();

        Scanner menuScanner = new Scanner(System.in);
        String choice = menuScanner.nextLine();

        while (true) {
            switch (choice) {
                case "1" -> addContact();
                case "2" -> dumpContact();
                case "3" -> deleteContact();
                case "4" -> updateContact();
                case "5" -> orderContact();
                case "6" -> orderContactDate();
                case "7" -> findContact();
                case "q" -> {
                    System.out.println("Application fermée");
                    return;
                }
                default -> System.out.println("La valeur n'est pas définit.");
            }
            getMenu();
            choice = menuScanner.nextLine();
        }
    }

    private static void addContact() {
        Contact contact = new Contact();

        System.out.println("Veuillez saisir le nom :");
        Scanner setLastnameScanner = new Scanner(System.in);
        contact.setLastname(setLastnameScanner.nextLine());

        System.out.println("Veuillez saisir le prénom :");
        Scanner setFirstnameScanner = new Scanner(System.in);
        contact.setFirstname(setFirstnameScanner.nextLine());

        do {
            try {
                System.out.println("Veuillez saisir le téléphone:");
                Scanner setNumberphoneScanner = new Scanner(System.in);
                contact.setNumber(setNumberphoneScanner.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.println("Veuillez saisir le mail:");
                Scanner setEmailScanner = new Scanner(System.in);
                contact.setEmail(setEmailScanner.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.println("Veuillez saisir la date de naissance:");
                Scanner setBirthdayScanner = new Scanner(System.in);
                contact.setBirthday(setBirthdayScanner.nextLine());
                break;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } while (true);

        try {
            contact.save();
            System.out.println("Contact enregistré.");
        } catch (IOException e) {
            System.out.println("Erreur à l'enregistrement");
        }
    }

    private static void dumpContact() {
        try {
            ArrayList<Contact> list = Contact.lister();

            for (Contact contact : list) {
                System.out.println(contact.getFirstname() + " " + contact.getLastname());
            }
        } catch (IOException e) {
            System.out.println("Une erreur avec le fichier est survenue.");
        }
    }

    private static void deleteContact() {
        try {
            dumpContact();

            ArrayList<Contact> list = Contact.lister();

            System.out.println("Entrez le email du contact à supprimer :");
            Scanner choseContactDelete = new Scanner(System.in);
            String emailContact = choseContactDelete.nextLine();

            Contact contactDelete = null;
            for (Contact contact : list) {
                if (contact.getEmail().equals(emailContact)) {
                    contactDelete = contact;
                }
            }

            if (contactDelete != null) {
                list.remove(contactDelete);
                Contact.save2(list);
                System.out.println("Contact supprimé");
            } else {
                System.out.println("Pas de contact à supprimer");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateContact() {
        try {
            dumpContact();
            ArrayList<Contact> list = Contact.lister();

            System.out.println("Entrez le email du contact à modifier :");
            Scanner choseContactUpdate = new Scanner(System.in);
            String emailContact = choseContactUpdate.nextLine();

            Contact contactUpdate = null;
            for (Contact contact : list) {
                if (contact.getEmail().equals(emailContact)) {
                    contactUpdate = contact;
                }
            }

            if (contactUpdate != null) {
                System.out.println("Veuillez saisir le nom :");
                contactUpdate.setLastname(choseContactUpdate.nextLine());
                System.out.println("Veuillez saisir le prénom :");
                contactUpdate.setFirstname(choseContactUpdate.nextLine());

                do {
                    try {
                        System.out.println("Veuillez saisir l'adresse email :");
                        contactUpdate.setEmail(choseContactUpdate.nextLine());
                        break;
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                } while (true);

                do {
                    try {
                        System.out.println("Veuillez saisir le numéro du téléphone :");
                        contactUpdate.setNumber(choseContactUpdate.nextLine());
                        break;
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                } while (true);

                do {
                    try {
                        System.out.println("Veuillez saisir la date de naissance :");
                        contactUpdate.setBirthday(choseContactUpdate.nextLine());
                        break;
                    } catch (ParseException e) {
                        System.out.println("Erreur dans la date de naissance");
                    }
                } while (true);
                Contact.save2(list);
                System.out.println("Contact modifié");
            } else {
                System.out.println("Pas de contact a modifier");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void orderContact() {
        try {
            ArrayList<Contact> list = Contact.lister();
            Collections.sort(list, null);

            for (Contact contact : list) {
                System.out.println(contact.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void orderContactDate() {
        try {
            ArrayList<Contact> list;
            list = Contact.lister();
            Collections.sort(list, new Date());

            for (Contact c : list) {
                System.out.println(c.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void findContact() {
        try {
            /* NOTE - Stream permet de traiter des collections d'objets. */
            Stream<Contact> liste = Contact.lister().stream();
            Scanner getNameScanner = new Scanner(System.in);
            System.out.println("Veuillez saisir le nom :");
            String name = getNameScanner.nextLine();

            Stream<Contact> result = liste.filter(contact -> contact.getLastname().contains(name.toUpperCase()));
            result.forEach(contactResult -> System.out.println(contactResult.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //    --- MENU ---
    public static void getMenu() {
        ArrayList<String> menu = new ArrayList<>();
        menu.add("-- MENU --");
        menu.add("1 - Ajouter un contact");
        menu.add("2 - Lister les contacts");
        menu.add("3 - Supprimer un contact");
        menu.add("4 - Modifier un contact");
        menu.add("5 - Trier les contacts");
        menu.add("6 - Trier les contacts par date");
        menu.add("7 - Rechercher les contacts sur nom");
        menu.add("q - Quitter");
        for (String line : menu) {
            System.out.println(line);
        }
    }
}