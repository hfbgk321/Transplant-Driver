package com.company.Hw_7;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

/**
 * Class to run simulation of organ transplant waitlists
 *
 * @author Jacky Chen
 * 		e-mail:jacky.chen.6@stonybrook.edu
 * 		Stony Brook ID: 112704638
 * 		CSE214 HW 7 R03
 *
 */
public class TransplantDriver {
    public static final String DONOR_FILE = "donors.txt"; // constant to represent donor file
    public static final String RECIPIENT_FILE = "recipients.txt";// constant to represent recipient file

    /**
     * main class
     * @param args args
     */
    public static void main(String[] args){
        TransplantGraph intro = new TransplantGraph();

        try{
            FileInputStream file = new FileInputStream("transplant.obj");
            ObjectInputStream fin  = new ObjectInputStream(file);
            intro = (TransplantGraph) fin.readObject();
            fin.close();
            System.out.println("Loading data from transplant.obj...");
        }catch(Exception x){
            System.out.println("transport.obj not found. Creating new TransplantGraph Object...");
            System.out.println("Loading data from 'donors.txt'");
            System.out.println("Loading data from recipients.txt");
            try {
                intro = TransplantGraph.buildFromFiles("donors.txt", "recipients.txt");
            }catch(Exception x2){
                System.out.println(x2.getMessage());
            }
        }

//        intro.printAllRecipients();

        menu();
        System.out.println();
        boolean notDone = true;
        Scanner input = new Scanner(System.in);

        do{
            System.out.println();
            System.out.print("Please select an option: ");
            String choice = input.nextLine();
            System.out.println();
            switch(choice.toLowerCase()){
                case "lr":
                    intro.printAllRecipients();
                    System.out.println();
                    menu();
                    System.out.println();
                    break;
                case "lo":
                    intro.printAllDonors();
                    System.out.println();
                    menu();
                    System.out.println();
                    break;
                case "ao":
                    try {
                        System.out.print("Please enter the organ donor name: ");
                        String donor_name = input.nextLine();
                        System.out.print("Please enter the organs " + donor_name + " is donating: ");
                        String organ = input.nextLine();
                        System.out.print("Please enter the blood type of " + donor_name + ": ");
                        String blood_type = input.nextLine();
                        System.out.print("Please enter the age of " + donor_name + ": ");
                        int age = input.nextInt();
                        input.nextLine();
                        BloodType donor_blood = new BloodType(blood_type);
                        Patient donor = new Patient(donor_name, organ, donor_blood, age);
                        intro.addDonor(donor);
                        System.out.println();
                        System.out.println("The organ donor with ID " + donor.getID() + " " +
                                "was successfully added to the donor list!");
                        System.out.println();
                        menu();
                        System.out.println();
                    }catch(Exception x){
                        System.out.println(x.getMessage());
                    }
                    break;
                case "ar":
                    try {
                        System.out.print("Please enter new recipient's name: ");
                        String recipient_name = input.nextLine();
                        System.out.print("Please enter the recipient's blood type: ");
                        String recipient_blood = input.nextLine();
                        System.out.print("Please enter the recipient's age: ");
                        int recipient_age = input.nextInt();
                        input.nextLine();
                        System.out.print("Please enter the organ needed: ");
                        String recipient_organ = input.nextLine();
                        BloodType bloodType = new BloodType(recipient_blood);
                        Patient recipient = new Patient(recipient_name, bloodType, recipient_age, recipient_organ);
                        intro.addRecipient(recipient);
                        System.out.println();
                        System.out.print(recipient_name + " is now on the organ transplant waitlist!");
                        System.out.println();
                        menu();
                        System.out.println();
                    }catch (Exception x){
                        System.out.println(x.getMessage());
                    }
                    break;
                case "ro":
                    try {
                        System.out.print("Please enter the name of the organ donor to remove: ");
                        String remove_donor_name = input.nextLine();
                        intro.removeDonors(remove_donor_name);
                        System.out.println();
                        menu();
                        System.out.println();
                    }catch(Exception o){
                        System.out.println(o.getMessage());
                    }
                    break;
                case "rr":
                    try {
                        System.out.print("Please enter the name of the recipient to remove: ");
                        String remove_recipient_name = input.nextLine();
                        intro.removeRecipient(remove_recipient_name);
                        System.out.println();
                        menu();
                        System.out.println();
                    }catch (Exception r){
                        System.out.println(r.getMessage());
                    }
                    break;
                case "sr":
                    boolean finish_sorting = false;
                    ArrayList<Patient> temp = intro.getRecipients();
                    do{
                        sortRecipientMenu();
                        System.out.println();
                        System.out.print("Please select an option: ");
                        String option = input.nextLine();
                        switch(option.toLowerCase()){
                            case "i":
                                Collections.sort(intro.getRecipients());
                                intro.printAllRecipients();
                                System.out.println();
                                System.out.println();
                                break;
                            case "n":
                                Collections.sort(intro.getRecipients(),new NumConnectionsComparator());
                                intro.printAllRecipients();
                                System.out.println();
                                System.out.println();
                                break;
                            case "b":
                                Collections.sort(intro.getRecipients(),new BloodTypeComparator());
                                intro.printAllRecipients();
                                System.out.println();
                                System.out.println();
                                break;
                            case "o":
                                Collections.sort(intro.getRecipients(),new OrganComparator());
                                intro.printAllRecipients();
                                System.out.println();
                                System.out.println();
                                break;
                            case "q":
                                Collections.sort(intro.getRecipients());
                                System.out.println("Returning to main menu.");
                                finish_sorting = true;
                                break;
                            default:
                                System.out.println("Please choose a choice from the menu.");
                                break;
                        }
                    }while(!finish_sorting);
                    break;
                case"so":
                    boolean finish_sorting_donors = false;
                    do{
                        sortDonorMenu();
                        System.out.println();
                        System.out.print("Please select an option: ");
                        String option = input.nextLine();
                        switch(option.toLowerCase()){
                            case "i":
                                Collections.sort(intro.getDonors());
                                intro.printAllDonors();
                                System.out.println();
                                System.out.println();
                                break;
                            case "n":
                                Collections.sort(intro.getDonors(),new NumConnectionsComparator());
                                intro.printAllDonors();
                                System.out.println();
                                System.out.println();
                                break;
                            case "b":
                                Collections.sort(intro.getDonors(),new BloodTypeComparator());
                                intro.printAllDonors();
                                System.out.println();
                                System.out.println();
                                break;
                            case "o":
                                Collections.sort(intro.getDonors(),new OrganComparator());
                                intro.printAllDonors();
                                System.out.println();
                                System.out.println();
                                break;
                            case "q":
                                Collections.sort(intro.getDonors());
                                System.out.println("Returning to main menu.");
                                finish_sorting_donors = true;
                                break;
                            default:
                                System.out.println("Please choose a choice from the menu.");
                                break;
                        }
                    }while(!finish_sorting_donors);
                    break;
                case "q":
                    try {
                        System.out.println("Writing data to transplant.obj...");
                        System.out.println();
                        System.out.println("Program terminating normally...");
                        FileOutputStream file = new FileOutputStream("transplant.obj");
                        ObjectOutputStream fout = new ObjectOutputStream(file);
                        fout.writeObject(intro);
                        fout.close();
                        notDone = false;
                    }catch (Exception x){
                        System.out.println(x.getMessage());
                    }
                    break;
                default:
                    System.out.println("Please choose an option from the given menu.");
                    menu();
                    break;
            }
        }while(notDone);
    }

    /**
     * method to print out menu
     */
    public static void menu(){
        System.out.println();
        System.out.println("Menu:\n" +
                "    (LR) - List all recipients\n" +
                "    (LO) - List all donors\n" +
                "    (AO) - Add new donor\n" +
                "    (AR) - Add new recipient\n" +
                "    (RO) - Remove donor\n" +
                "    (RR) - Remove recipient\n" +
                "    (SR) - Sort recipients\n" +
                "    (SO) - Sort donors\n" +
                "    (Q) - Quit");
    }

    /**
     * method to print out the sortRecipientMenu options
     */
    public static  void sortRecipientMenu(){
        System.out.println("    (I) Sort by ID\n" +
                "    (N) Sort by Number of Donors\n" +
                "    (B) Sort by Blood Type\n" +
                "    (O) Sort by Organ Needed\n" +
                "    (Q) Back to Main Menu\n");
    }

    /**
     * method to print out the sortdonormenu
     */
    public static void sortDonorMenu(){
        System.out.println("   (I) Sort by ID\n" +
                "   (N) Sort by Number of Recipients\n" +
                "   (B) Sort by Blood Type\n" +
                "   (O) Sort by Organ Donated\n" +
                "   (Q) Back to Main Menu");
    }
}
