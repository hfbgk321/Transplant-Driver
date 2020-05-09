package com.company.Hw_7;

import sun.security.krb5.internal.PAData;

import java.util.Comparator;

/**
 * comparator class to compare number of connections per patient
 *
 * @author Jacky Chen
 * 		e-mail:jacky.chen.6@stonybrook.edu
 * 		Stony Brook ID: 112704638
 * 		CSE214 HW 7 R03
 *
 */
public class NumConnectionsComparator implements Comparator<Patient> {
    /**
     * compare method to compare patient connections
     * @param one patient one
     * @param two patient two
     * @return integer depending on who has more connections
     */
    public int compare(Patient one, Patient two){
        if(one.getNumOfConnections()<two.getNumOfConnections()){
            return -1;
        }else if(one.getNumOfConnections() > two.getNumOfConnections()){
            return 1;
        }else{
            return 0;
        }
    }
}
