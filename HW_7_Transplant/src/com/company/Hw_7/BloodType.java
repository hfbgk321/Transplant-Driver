package com.company.Hw_7;

import java.io.Serializable;

/**
 * Serializable class to represent the Blood type of a person
 *
 * @author Jacky Chen
 * 		e-mail:jacky.chen.6@stonybrook.edu
 * 		Stony Brook ID: 112704638
 * 		CSE214 HW 7 R03
 *
 */
public class BloodType implements Serializable {
    private String bloodType; //String variable for bloodtype

    /**
     * Empty constructor
     */
    public BloodType(){}

    /**
     * Constructor to set bloodtype
     * @param bloodType new Blood type
     */
    public BloodType(String bloodType){
        this.bloodType = bloodType;
    }

    /**
     * method to determine if blood type of recipient is compatible with donor's blood type
     * @param recipient bloodtype of recipient
     * @param donor bloodtype of donor
     * @return true or false depending on compatibility
     */
    public static boolean isCompatible(BloodType recipient, BloodType donor){ //come back to this
        switch (recipient.bloodType){
            case "O":
                switch(donor.bloodType){
                    case "O":
                        return true;
                    case "A":
                    case "B":
                    case "AB":
                        return false;
                }
                break;
            case "A":
                switch(donor.bloodType){
                    case "O":
                    case "A":
                        return true;
                    case "B":
                    case "AB":
                        return false;
                }
                break;
            case "B":
                switch(donor.bloodType){
                    case "O":
                    case "B":
                        return true;
                    case "A":
                    case "AB":
                        return false;
                }
                break;
            case "AB":
                return true;
        }
        return false;
    }

    /**
     * getter method to blood type
     * @return blood type string
     */
    public String getBloodType() {
        return bloodType;
    }
}
