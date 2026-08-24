/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalsystems;

/**
 *
 * @author Delicia
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);
    static HospitalSystems hospital = new HospitalSystems();

    public static void main(String[] args) {

        int choice = -1;

        while (choice != 0) {

            showMenu();

            try {

                choice = input.nextInt();
                input.nextLine();

                if (choice == 1) {
                    registerPatient();

                } else if (choice == 2) {
                    searchPatient();

                } else if (choice == 3) {
                    updatePatient();

                } else if (choice == 4) {
                    deletePatient();

                } else if (choice == 5) {
                    hospital.displayPatients();

                } else if (choice == 6) {
                    allocateBed();

                } else if (choice == 7) {
                    releaseBed();

                } else if (choice == 8) {
                    hospital.displayWard();

                } else if (choice == 9) {
                    hospital.displayAvailableBeds();

                } else if (choice == 10) {
                    hospital.displayOccupiedBeds();

                } else if (choice == 11) {
                    hospital.patientReport();

                } else if (choice == 12) {
                    hospital.bedReport();

                } else if (choice == 13) {
                    sortPatients();

                } else if (choice == 0) {
                    System.out.println("Thank you for using the system.");

                } else {
                    System.out.println("Invalid option.");
                }

            } catch (InputMismatchException e) {

                System.out.println("Please enter a number.");
                input.nextLine();
            }
        }
    }

    public static void showMenu() {

        System.out.println("\n==============================");
        System.out.println("       HOSPITAL SYSTEM");
        System.out.println("==============================");
        System.out.println("1. Register Patient");
        System.out.println("2. Search Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Delete Patient");
        System.out.println("5. Show All Patients");
        System.out.println("6. Allocate Bed");
        System.out.println("7. Release Bed");
        System.out.println("8. Show Ward");
        System.out.println("9. Show Available Beds");
        System.out.println("10. Show Occupied Beds");
        System.out.println("11. Patient Report");
        System.out.println("12. Bed Occupancy Report");
        System.out.println("13. Sort Patients");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void registerPatient() {

        System.out.print("Enter patient ID: ");
        String id = input.nextLine();

        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        int age = getAge();

        System.out.print("Enter gender: ");
        String gender = input.nextLine();

        System.out.print("Enter medical condition: ");
        String condition = input.nextLine();

        System.out.println("\n1. Inpatient");
        System.out.println("2. Outpatient");
        System.out.println("3. Emergency");
        System.out.print("Choose category: ");

        int option;

        try {
            option = input.nextInt();
            input.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a number.");
            input.nextLine();
            return;
        }

        Patient patient;

        if (option == 1) {

            patient = new Inpatient(
                    id,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    1,
                    "None"
            );

        } else if (option == 2) {

            patient = new Patient(
                    id,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    PatientCategory.OUTPATIENT
            );

        } else if (option == 3) {

            patient = new Patient(
                    id,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    PatientCategory.EMERGENCY
            );

        } else {

            System.out.println("Invalid category.");
            return;
        }

        if (hospital.registerPatient(patient)) {
            System.out.println("Patient registered.");
        } else {
            System.out.println("That patient ID already exists.");
        }
    }

    public static int getAge() {

        while (true) {

            try {

                System.out.print("Enter age: ");
                int age = input.nextInt();
                input.nextLine();

                if (age >= 0) {
                    return age;
                }

                System.out.println("Age cannot be negative.");

            } catch (InputMismatchException e) {

                System.out.println("Please enter a valid age.");
                input.nextLine();
            }
        }
    }

    public static void searchPatient() {

        System.out.print("Enter patient ID: ");
        String id = input.nextLine();

        Patient patient = hospital.searchPatient(id);

        if (patient != null) {
            System.out.println("\nPatient found:");
            patient.displayDetails();
        } else {
            System.out.println("Patient was not found.");
        }
    }

    public static void updatePatient() {

        System.out.print("Enter patient ID: ");
        String id = input.nextLine();

        Patient patient = hospital.searchPatient(id);

        if (patient == null) {
            System.out.println("Patient was not found.");
            return;
        }

        System.out.print("Enter new first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter new last name: ");
        String lastName = input.nextLine();

        int age = getAge();

        System.out.print("Enter new gender: ");
        String gender = input.nextLine();

        System.out.print("Enter new medical condition: ");
        String condition = input.nextLine();

        boolean updated = hospital.updatePatient(
                id,
                firstName,
                lastName,
                age,
                gender,
                condition
        );

        if (updated) {
            System.out.println("Patient updated.");
        } else {
            System.out.println("Patient could not be updated.");
        }
    }

    public static void deletePatient() {

        System.out.print("Enter patient ID: ");
        String id = input.nextLine();

        if (hospital.deletePatient(id)) {
            System.out.println("Patient deleted.");
        } else {
            System.out.println("Patient was not found.");
        }
    }

    public static void allocateBed() {

        System.out.print("Enter patient ID: ");
        String id = input.nextLine();

        Patient patient = hospital.searchPatient(id);

        if (patient == null) {
            System.out.println("Patient was not found.");
            return;
        }

        if (!(patient instanceof Inpatient)) {
            System.out.println("Only inpatients can get a bed.");
            return;
        }

        if (hospital.getAvailableBeds() == 0) {
            System.out.println("There are no beds available.");
            return;
        }

        System.out.print("Enter bed number (B01 - B20): ");
        String bed = input.nextLine();

        if (hospital.allocateBed(id, bed)) {
            System.out.println("Bed allocated.");
        } else {
            System.out.println("The bed is not available.");
        }
    }

    public static void releaseBed() {

        System.out.print("Enter patient ID: ");
        String id = input.nextLine();

        if (hospital.releaseBed(id)) {
            System.out.println("Bed released.");
        } else {
            System.out.println("This patient does not have a bed.");
        }
    }

    public static void sortPatients() {

        System.out.println("\n1. Sort by surname");
        System.out.println("2. Sort by patient ID");
        System.out.print("Choose: ");

        try {

            int option = input.nextInt();
            input.nextLine();

            if (option == 1) {

                hospital.sortBySurname();
                System.out.println("Patients sorted by surname.");

            } else if (option == 2) {

                hospital.sortByID();
                System.out.println("Patients sorted by ID.");

            } else {

                System.out.println("Invalid option.");
                return;
            }

            hospital.displayPatients();

        } catch (InputMismatchException e) {

            System.out.println("Please enter a number.");
            input.nextLine();
        }
    }
}