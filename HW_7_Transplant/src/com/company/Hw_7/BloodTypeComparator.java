package com.company.Hw_7;

import java.util.Comparator;
/**
 * Class to for comparing blood types
 *
 * @author Jacky Chen
 *  	e-mail:jacky.chen.6@stonybrook.edu
 * 		Stony Brook ID: 112704638
 *      CSE214 HW 7 R03
 *
 *
 */
public class BloodTypeComparator implements Comparator<Patient> {
    /**
     * method to compare blood types
     * @param one patient one
     * @param two patient two
     * @return int depending on which bloodtype is greater
     */
    public int compare(Patient one, Patient two){
        if(one.getBloodType().getBloodType().toLowerCase().
                compareTo(two.getBloodType().getBloodType().toLowerCase()) >0){
            return 1;
        }else if(one.getBloodType().getBloodType().toLowerCase().
                compareTo(two.getBloodType().getBloodType().toLowerCase()) <0){
            return -1;
        }else{
            return one.compareTo(two);
        }
    }
}
