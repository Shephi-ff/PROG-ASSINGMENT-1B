/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalsystems;

/**
 *
 * @author Delicia
 */
import java.util.ArrayList;

public class HospitalSystems {

    private ArrayList<Patient> patients;
    private String[][] beds;

    public HospitalSystems() {

        patients = new ArrayList<>();
        beds = new String[4][5];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                beds[i][j] = null;
            }
        }
    }

    public boolean registerPatient(Patient patient) {

        if (searchPatient(patient.getPatientID()) != null) {
            return false;
        }

        patients.add(patient);
        return true;
    }

    public Patient searchPatient(String id) {

        for (int i = 0; i < patients.size(); i++) {

            if (patients.get(i).getPatientID().equalsIgnoreCase(id)) {
                return patients.get(i);
            }
        }

        return null;
    }

    public boolean updatePatient(String id, String firstName,
                                 String lastName, int age,
                                 String gender, String condition) {

        Patient patient = searchPatient(id);

        if (patient == null) {
            return false;
        }

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(condition);

        return true;
    }

    public boolean deletePatient(String id) {

        Patient patient = searchPatient(id);

        if (patient == null) {
            return false;
        }

        releaseBed(id);
        patients.remove(patient);

        return true;
    }

    public void displayPatients() {

        if (patients.size() == 0) {
            System.out.println("There are no patients.");
            return;
        }

        for (int i = 0; i < patients.size(); i++) {

            System.out.println("--------------------");
            patients.get(i).displayDetails();
        }
    }

    public boolean allocateBed(String patientID, String bedNumber) {

        Patient patient = searchPatient(patientID);

        if (patient == null) {
            return false;
        }

        if (!(patient instanceof Inpatient)) {
            return false;
        }

        if (alreadyHasBed(patientID)) {
            return false;
        }

        int[] place = findBed(bedNumber);

        if (place == null) {
            return false;
        }

        int row = place[0];
        int column = place[1];

        if (beds[row][column] != null) {
            return false;
        }

        beds[row][column] = patientID;

        Inpatient inpatient = (Inpatient) patient;
        inpatient.setBedNumber(bedNumber);

        return true;
    }

    public boolean releaseBed(String patientID) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {

                if (patientID.equals(beds[i][j])) {

                    beds[i][j] = null;

                    Patient patient = searchPatient(patientID);

                    if (patient instanceof Inpatient) {
                        Inpatient inpatient = (Inpatient) patient;
                        inpatient.setBedNumber("None");
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private boolean alreadyHasBed(String patientID) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {

                if (patientID.equals(beds[i][j])) {
                    return true;
                }
            }
        }

        return false;
    }

    private int[] findBed(String bedNumber) {

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                String bed = String.format(
                        "B%02d", (i * 5) + j + 1);

                if (bed.equalsIgnoreCase(bedNumber)) {
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }

    public void displayWard() {

        System.out.println("\nWARD LAYOUT");

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                String bed = String.format(
                        "B%02d", (i * 5) + j + 1);

                if (beds[i][j] == null) {
                    System.out.print(bed + "[Available] ");
                } else {
                    System.out.print(
                            bed + "[" + beds[i][j] + "] ");
                }
            }

            System.out.println();
        }
    }

    public void displayAvailableBeds() {

        System.out.println("\nAVAILABLE BEDS");

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                if (beds[i][j] == null) {

                    String bed = String.format(
                            "B%02d", (i * 5) + j + 1);

                    System.out.print(bed + " ");
                }
            }
        }

        System.out.println();
    }

    public void displayOccupiedBeds() {

        System.out.println("\nOCCUPIED BEDS");

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                if (beds[i][j] != null) {

                    String bed = String.format(
                            "B%02d", (i * 5) + j + 1);

                    System.out.println(
                            bed + " - Patient: " + beds[i][j]);
                }
            }
        }
    }

    public int getAvailableBeds() {

        int count = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {

                if (beds[i][j] == null) {
                    count++;
                }
            }
        }

        return count;
    }

    public int getOccupiedBeds() {
        return 20 - getAvailableBeds();
    }

    public int getPatientCount() {
        return patients.size();
    }

    public double getOccupancy() {

        return (getOccupiedBeds() / 20.0) * 100;
    }

    public void sortBySurname() {

        for (int i = 0; i < patients.size() - 1; i++) {

            for (int j = i + 1; j < patients.size(); j++) {

                String name1 = patients.get(i).getLastName();
                String name2 = patients.get(j).getLastName();

                if (name1.compareToIgnoreCase(name2) > 0) {

                    Patient temp = patients.get(i);
                    patients.set(i, patients.get(j));
                    patients.set(j, temp);
                }
            }
        }
    }

    public void sortByID() {

        for (int i = 0; i < patients.size() - 1; i++) {

            for (int j = i + 1; j < patients.size(); j++) {

                String id1 = patients.get(i).getPatientID();
                String id2 = patients.get(j).getPatientID();

                if (id1.compareToIgnoreCase(id2) > 0) {

                    Patient temp = patients.get(i);
                    patients.set(i, patients.get(j));
                    patients.set(j, temp);
                }
            }
        }
    }

    public void patientReport() {

        System.out.println("\nPATIENT REPORT");
        System.out.println("--------------------");

        displayPatients();

        System.out.println("--------------------");
        System.out.println("Total patients: " + patients.size());
    }

    public void bedReport() {

        System.out.println("\nBED OCCUPANCY REPORT");
        System.out.println("--------------------");
        System.out.println("Total beds: 20");
        System.out.println("Occupied beds: " + getOccupiedBeds());
        System.out.println("Available beds: " + getAvailableBeds());
        System.out.println("Occupancy: " + getOccupancy() + "%");
    }
}