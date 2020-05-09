package com.company.Hw_7;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        BloodType no = new BloodType();
//        Patient greater = new Patient(10);
//        Patient lower = new Patient(10);
//        System.out.println(greater.compareTo(no));
//
//        BloodType recipient = new BloodType("AB");
//        BloodType donor = new BloodType("AB");
//        System.out.println(BloodType.isCompatible(recipient,donor));

        try {
            TransplantGraph test = TransplantGraph.buildFromFiles("donors.txt", "recipients.txt");
//            ArrayList<Patient> donors = test.getDonors();
//            ArrayList<Patient> recipients = test.getRecipients();
//
//            System.out.println("Donors: ");
//            for(Patient donor : donors){
//                System.out.println(donor.toString());
//            }
//            System.out.println();
//
//            System.out.println("Recipients: ");
//            for(Patient recipient: recipients){
//                System.out.println(recipient);
//            }
//            test.printAllRecipients();
//            System.out.println();
//            System.out.println();
//            test.printAllDonors();
            for(int i = 0; i < test.getConnections().length;i++){
                for(int x = 0; x < test.getConnections()[i].length;x++){
                    System.out.print(test.getConnections()[i][x] +" ");
                }
                System.out.println();
            }



        }catch(Exception x){
            System.out.println(x.getMessage());
        }

    }
}
