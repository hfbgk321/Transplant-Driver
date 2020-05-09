package com.company.Hw_7;

import java.util.Comparator;

/**
 * comparator class to compare patient organs
 *
 * @author Jacky Chen
 * 		e-mail:jacky.chen.6@stonybrook.edu
 * 		Stony Brook ID: 112704638
 * 		CSE214 HW 7 R03
 *
 */
public class OrganComparator implements Comparator<Patient> {
    /**
     * compare method to compare letter of organ
     * @param one patient one
     * @param two patient two
     * @return integer depending on letters of organ
     */
    public int compare(Patient one, Patient two){
        if(one.getOrgan().toLowerCase().compareTo(two.getOrgan().toLowerCase())>0){
            return 1;
        }else if(one.getOrgan().toLowerCase().compareTo(two.getOrgan().toLowerCase())<0){
            return  -1;
        }else{
            return 0;
        }
    }
}
