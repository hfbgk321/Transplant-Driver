package com.company.Hw_7;

import java.io.Serializable;
/**
 * Class to define information about a patient
 *
 * @author Jacky Chen
 *  	e-mail:jacky.chen.6@stonybrook.edu
 * 		Stony Brook ID: 112704638
 *      CSE214 HW 7 R03
 *
 *
 */
public class Patient implements Comparable<Object>, Serializable {
    private String name; // name of patient
    private String organ; // name of organ
    private int age; //age of patient
    private BloodType bloodType; // blood type of patient
    private int ID; //id of patient
    private boolean isDonor; //represents whether patient is a donor
    private String IDs =""; //represents ids of patient's connections
    private int numOfConnections = 0;// stores the number of connections

    /**
     * empty constructor
     */
    public Patient(){}

    /**
     * custom constructor for patient id
     * @param ID new ID
     */
    public Patient(int ID){
        this.ID = ID;
    }

    /**
     * custom constructor
     * @param ID id of patient
     * @param name name of patient
     * @param age age of patient
     * @param organ organ name of patient
     * @param new_blood blood type of patient
     */
    public Patient(int ID, String name, int age, String organ, BloodType new_blood) throws invalidAgeException{
        if (age > 0) {
            this.age = age;
            this.ID = ID;
            this.name = name;
            this.organ = organ;
            this.bloodType = new_blood;
        }else{
            throw new invalidAgeException();
        }
    }

    /**
     * constructor for when patient is a donor
     * @param name name of patient
     * @param organ organ name of patient
     * @param bloodType blood type of patient
     * @param age age of patient
     */
    public Patient(String name, String organ, BloodType bloodType, int age) throws invalidAgeException{ //for donors
        if(age >0) {
            this.name = name;
            this.organ = organ;
            this.bloodType = bloodType;
            this.age = age;
        }else{
            throw new invalidAgeException();
        }
    }

    /**
     * custom constructor of patient
     * @param name name of patient
     * @param bloodType blood type of patient
     * @param age age of patient
     * @param organ organ name of patient
     */
    public Patient(String name, BloodType bloodType, int age, String organ) throws invalidAgeException{
        if(age >0) {
            this.name = name;
            this.bloodType = bloodType;
            this.age = age;
            this.organ = organ;
        }else{
            throw new invalidAgeException();
        }
    }

    /**
     * method to compare patient id
     * @param o object
     * @return integer based on patient id
     */
    public int compareTo(Object o){
        if(o instanceof Patient){
            if(this.ID >((Patient) o).ID){
                return 1;
            }else if (this.ID < ((Patient) o).ID){
                return -1;
            }else{
                return 0;
            }
        }
        return -1;
    }

    /**
     * method to display string representation of patient
     * @return string representation of patient
     */
    public String toString(){ //come back to this.
        if(!isDonor) {
            return String.format("%2s%-6d%-3s%-17s%-2s%-4s%-5s%-10s%-7s%-7s%-5s%s"
                    , "",this.ID, "|", this.name, "|", this.age, "|", this.organ, "|", this.bloodType.getBloodType(), "|", this.IDs);
        }
        return String.format("%2s%-6d%-3s%-17s%-2s%-4s%-5s%-11s%-6s%-8s%-5s%s"
                ,"",this.ID,"|",this.name,"|",this.age,"|",this.organ,"|",this.bloodType.getBloodType(),"|",this.IDs);
    }


    /**
     * getter method for name of patient
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter method for name
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter method for organ
     * @return organ
     */
    public String getOrgan() {
        return organ;
    }

    /**
     * setter method for organ
     * @param organ new organ
     */
    public void setOrgan(String organ) {
        this.organ = organ;
    }

    /**
     * getter method for age
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * setter method for age
     * @param age new age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * getter method for blood type
     * @return blood type
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * setter method for bloodtype
     * @param bloodType new bloodtype
     */
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * getter method for id
     * @return id
     */
    public int getID() {
        return ID;
    }

    /**
     * setter method for id
     * @param ID new id
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * getter method for isDonor
     * @return isDonor
     */
    public boolean isDonor() {
        return isDonor;
    }

    /**
     * setter method for isDonor
     * @param donor new isDonor
     */
    public void setDonor(boolean donor) {
        isDonor = donor;
    }

    /**
     * setter method for ids of patient connections
     * @param IDs new patient connections
     */
    public void setIDs(String IDs) {
        this.IDs = IDs;
    }

    /**
     * getter method for ids of patient connections
     * @return patient connection ids
     */
    public String getIDs(){
        return this.IDs;
    }

    /**
     * getter method for num of connections
     * @return number of connections
     */
    public int getNumOfConnections() {
        return numOfConnections;
    }

    /**
     * setter method for number of connections
     * @param numOfConnections new number of connections
     */
    public void setNumOfConnections(int numOfConnections) {
        this.numOfConnections = numOfConnections;
    }
}

/**
 * class to represent invalid date exception
 */
class invalidAgeException extends Exception{
    invalidAgeException(){
        super("\nPlease enter an age greater than zero");
    }
}

