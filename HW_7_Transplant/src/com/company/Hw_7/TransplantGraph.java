package com.company.Hw_7;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class to set up graph functions related to recipients and donors
 *
 * @author Jacky Chen
 *  	e-mail:jacky.chen.6@stonybrook.edu
 * 		Stony Brook ID: 112704638
 *      CSE214 HW 7 R03
 *
 *
 */
public class TransplantGraph implements Serializable{
    private ArrayList<Patient> donors = new ArrayList<>();//arraylist to keep track of donors
    private ArrayList<Patient> recipients = new ArrayList<>();// arraylist to keep track of recipients
    public static final int MAX_PATIENTS = 100; //constant for max number of patients

    private boolean[][] connections = new boolean[MAX_PATIENTS][MAX_PATIENTS]; //matrix to keep track of blood type compatibility

    /**
     * empty constructor
     */
    public TransplantGraph(){}

    /**
     * custom contructor
     * @param new_donors new donors arraylist
     * @param new_recipients new recipients arraylist
     * @param connections new matrix
     */
    public TransplantGraph(ArrayList<Patient> new_donors, ArrayList<Patient> new_recipients,boolean[][] connections){
        this.donors = new_donors;
        this.recipients = new_recipients;
        this.connections = connections;
    }


    /**
     * method to transport info from donor file to recipient file
     * @param donorFile file for donor info
     * @param recipientFile file for recipient info
     * @return new transplantGraph object containing everything
     * @throws IOException thrown if IO has issues
     */
    public static TransplantGraph buildFromFiles(String donorFile, String recipientFile) throws IOException,invalidAgeException {
        Scanner scanner = new Scanner(new File(donorFile));
        String allDonors = "";
        String allRecipients = "";

        ArrayList<Patient> donors = new ArrayList<>();
        ArrayList<Patient> recipients  = new ArrayList<>();


        //parsing the donors text
       while(scanner.hasNextLine()){
            String data = scanner.nextLine();

            int id_part = data.indexOf(",");
            int id = Integer.parseInt(data.substring(0,id_part));

            int name_part = data.indexOf(",",id_part+1);
            String name = data.substring(id_part+2,name_part);

            int age_part = data.indexOf(",",name_part+1);
            int age = Integer.parseInt(data.substring(name_part+2,age_part));

            int organ_part = data.indexOf(",",age_part+1);
            String organ = data.substring(age_part+2,organ_part);

            String bloodType = data.substring(organ_part+2);
            BloodType testBlood = new BloodType(bloodType);
            Patient patient = new Patient(id,name,age,organ,testBlood);
            patient.setDonor(true);
            donors.add(patient);

//            allDonors+= patient.toString()+'\n';
        }

       //Parsing the recipients
       Scanner scanner2 = new Scanner(new File(recipientFile));
        while(scanner2.hasNextLine()){
            String data = scanner2.nextLine();

            int id_part = data.indexOf(",");
            int id = Integer.parseInt(data.substring(0,id_part));

            int name_part = data.indexOf(",",id_part+1);
            String name = data.substring(id_part+2,name_part);

            int age_part = data.indexOf(",",name_part+1);
            int age = Integer.parseInt(data.substring(name_part+2,age_part));

            int organ_part = data.indexOf(",",age_part+1);
            String organ = data.substring(age_part+2,organ_part);

            String bloodType = data.substring(organ_part+2);
            BloodType testBlood = new BloodType(bloodType);

            Patient patient = new Patient(id,name,age,organ,testBlood);
            patient.setDonor(false);
            recipients.add(patient);

//            allRecipients+= patient.toString()+'\n';
        }

        boolean[][] connections = update(recipients,donors);
        TransplantGraph another = new TransplantGraph(donors,recipients,connections);
        return another;
    }

    /**
     * method to update recipient list and donors list
     * @param recipients recipient arraylist
     * @param donors donors arraylist
     * @return new connections matrix
     */
    private static boolean[][] update(ArrayList<Patient> recipients, ArrayList<Patient> donors){

        boolean[][] connections = new boolean[MAX_PATIENTS][MAX_PATIENTS];

        for(Patient patient : recipients) {
            patient.setIDs("");
            patient.setNumOfConnections(0);
        }
        for(Patient patient : donors){
            patient.setIDs("");
            patient.setNumOfConnections(0);
        }
        for( int i = 0; i < donors.size();i++){
            for(int x = 0; x< recipients.size();x++){
                boolean first = true;
                if(recipients.get(x).getOrgan().toLowerCase().
                        equals(donors.get(i).getOrgan().toLowerCase())) {
                    if(BloodType.isCompatible(recipients.get(x).getBloodType(),
                            donors.get(i).getBloodType())) {
                        connections[i][x] = BloodType.isCompatible(recipients.get(x).getBloodType(),
                                donors.get(x).getBloodType());
                        if(recipients.get(x).getIDs().equals("")){
                            recipients.get(x).setIDs(""+donors.get(i).getID());
                            recipients.get(x).setNumOfConnections(
                                    recipients.get(x).getNumOfConnections()+1);
                        }else{
                            recipients.get(x).setIDs(recipients.
                                    get(x).getIDs() + "," + donors.get(i).getID());
                            recipients.get(x).setNumOfConnections(
                                    recipients.get(x).getNumOfConnections()+1);
                        }

                        if(donors.get(i).getIDs().equals("")){
                            donors.get(i).setIDs(""+recipients.get(x).getID());
                            donors.get(i).setNumOfConnections(
                                    donors.get(i).getNumOfConnections()+1);
                        }else {
                            donors.get(i).setIDs(donors.get(i).
                                    getIDs() + ", " + recipients.get(x).getID());
                            donors.get(i).setNumOfConnections(
                                    donors.get(i).getNumOfConnections()+1);
                        }
                    }else{
                        connections[i][x] = BloodType.isCompatible(
                                recipients.get(x).getBloodType(), donors.get(i).getBloodType());
                    }
                }else{
                    connections[i][x] = false;
                }
            }
        }
        return connections;
    }

    /**
     * method to update ids of patients
     * @param list list of patients
     */
    private static void updateID(ArrayList<Patient> list){
        int count = 0;
        for(Patient patient : list){
            patient.setID(count++);
        }
    }


    /**
     * method to add recipients
     * @param patient patient you want to add
     * @throws maxCapacityException thrown if list is full
     */
    public void addRecipient(Patient patient) throws maxCapacityException{
        if(recipients.size() == MAX_PATIENTS){
            throw new maxCapacityException();
        }
        patient.setDonor(false);
        this.recipients.add(patient);
        updateID(this.recipients);
        this.connections = update(this.recipients,this.donors);
    }

    /**
     * method to add donor
     * @param patient donor to add
     * @throws maxCapacityException thrown if current list is full
     */
    public void addDonor(Patient patient)throws maxCapacityException{
        if(donors.size() == MAX_PATIENTS)
            throw new maxCapacityException();
        patient.setDonor(true);
        this.donors.add(patient);
        updateID(this.donors);
        this.connections = update(this.recipients,this.donors);
    }

    /**
     * method to remove recipients from the list
     * @param name name of recipient you want to remove
     * @throws noPatientsInSystemException thrown if list is empty
     */
    public void removeRecipient(String name) throws noPatientsInSystemException{
        if(recipients.size() == 0)
            throw new noPatientsInSystemException();
        int index = 0;
        boolean removed = false;
        for(Patient patient : this.recipients){
            if(patient.getName().toLowerCase().equals(name.toLowerCase())){
                this.recipients.remove(index);
                updateID(this.recipients);
                this.connections = update(this.recipients,this.donors);
                System.out.println();
                System.out.print(name+" was removed from the organ transplant waitlist.");
                System.out.println();
                removed = true;
                break;
            }
            index++;
        }
        if(!removed){
            System.out.println();
            System.out.println(name+" is not on the organ transplant waitlist");
            System.out.println();
        }
    }

    /**
     * removes donor from the list
     * @param name name of donor to remove
     * @throws noPatientsInSystemException thrown if no patients are currently in the list
     */
    public void removeDonors(String name) throws noPatientsInSystemException{
        if(donors.size() == 0)
            throw new noPatientsInSystemException();
        int index = 0;
        boolean removed = false;
        for(Patient patient : this.donors){
            if(patient.getName().toLowerCase().equals(name.toLowerCase())){
                this.donors.remove(index);
                updateID(this.donors);
                this.connections = update(this.recipients,this.donors);
                System.out.println();
                System.out.print(name+" was removed from the organ donor list.");
                System.out.println();
                removed = true;
                break;
            }
            index++;
        }
        if(!removed){
            System.out.println();
            System.out.println(name+" is not on the organ donor list");
            System.out.println();
        }
    }

    /**
     * method to prints all recipients
     */
    public void printAllRecipients(){
        String top = String.format("%-8s| %-18s| %-4s| %-13s| %-12s| %s", "Index"
                ,"Recipient Name","Age", "Organ Needed", "Blood Type", "Donor IDs");
        top+="\n"+"=====================================" +
                "======================================";
        System.out.print(top);
        System.out.print(toStringhelper(this.recipients));
    }

    /**
     * method to print all donors
     */
    public void printAllDonors(){
        String top = String.format("%-8s| %-18s| %-4s| %-13s | %-12s| %s", "Index"
                ,"Donor Name","Age", "Organ Donated", "Blood Type", "Recipient IDs");
        top+="\n"+"=====================================" +
                "===========================================";
        System.out.print(top);
        System.out.print(toStringhelper(this.donors));
    }

    /**
     * method to help tostring
     * @param list lit of patients
     * @return string
     */
    public String toStringhelper(ArrayList<Patient> list){
        String top = "";
        for(Patient patient: list){
            top+= "\n"+patient.toString();
        }
        return top;

    }

    /**
     * getter method for donors
     * @return list of donors
     */
    public ArrayList<Patient> getDonors() {
        return donors;
    }

    /**
     * setter method for donors
     * @param donors new donors list
     */
    public void setDonors(ArrayList<Patient> donors) {
        this.donors = donors;
    }

    /**
     * getter method for recipients
     * @return list of recipients
     */
    public ArrayList<Patient> getRecipients() {
        return recipients;
    }

    /**
     * setter method for recipients
     * @param recipients new recipient list
     */
    public void setRecipients(ArrayList<Patient> recipients) {
        this.recipients = recipients;
    }

    /**
     * getter method for connections
     * @return connections list
     */
    public boolean[][] getConnections() {
        return connections;
    }

    /**
     * setter method for connections
     * @param connections new connections matrix
     */
    public void setConnections(boolean[][] connections) {
        this.connections = connections;
    }
}

/**
 * exception class when max capacity is reached
 */
class maxCapacityException extends Exception{
    maxCapacityException(){
        super("Our system has reached its max capacity.");
    }
}

/**
 * exception class when no patients are in list
 */
class noPatientsInSystemException extends Exception{
    noPatientsInSystemException(){
        super("There are currently no patients in the System.");
    }
}
