/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.hospitalsystems;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HospitalSystemsTest {

    HospitalSystems hospital;

    @BeforeEach
    public void start() {
        hospital = new HospitalSystems();
    }

    @Test
    public void registerPatientTest() {

        Patient patient = new Patient(
                "P001",
                "John",
                "Smith",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        assertTrue(hospital.registerPatient(patient));
        assertEquals(1, hospital.getPatientCount());
    }

    @Test
    public void searchPatientTest() {

        Patient patient = new Patient(
                "P002",
                "Sarah",
                "Jones",
                30,
                "Female",
                "Cold",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        assertNotNull(hospital.searchPatient("P002"));
        assertNull(hospital.searchPatient("P999"));
    }

    @Test
    public void updatePatientTest() {

        Patient patient = new Patient(
                "P003",
                "Mike",
                "Brown",
                20,
                "Male",
                "Fever",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        boolean result = hospital.updatePatient(
                "P003",
                "Michael",
                "Brown",
                21,
                "Male",
                "Flu"
        );

        assertTrue(result);
        assertEquals(
                "Michael",
                hospital.searchPatient("P003").getFirstName()
        );
    }

    @Test
    public void deletePatientTest() {

        Patient patient = new Patient(
                "P004",
                "Tom",
                "White",
                40,
                "Male",
                "Injury",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        assertTrue(hospital.deletePatient("P004"));
        assertNull(hospital.searchPatient("P004"));
    }

    @Test
    public void allocateBedTest() {

        Inpatient patient = new Inpatient(
                "P005",
                "David",
                "Green",
                50,
                "Male",
                "Injury",
                1,
                "None"
        );

        hospital.registerPatient(patient);

        assertTrue(hospital.allocateBed("P005", "B01"));
        assertEquals(19, hospital.getAvailableBeds());
    }

    @Test
    public void releaseBedTest() {

        Inpatient patient = new Inpatient(
                "P006",
                "Lisa",
                "Black",
                45,
                "Female",
                "Flu",
                1,
                "None"
        );

        hospital.registerPatient(patient);

        hospital.allocateBed("P006", "B02");

        assertTrue(hospital.releaseBed("P006"));
        assertEquals(20, hospital.getAvailableBeds());
    }

    @Test
    public void duplicateIDTest() {

        Patient patient1 = new Patient(
                "P007",
                "John",
                "Smith",
                20,
                "Male",
                "Cold",
                PatientCategory.OUTPATIENT
        );

        Patient patient2 = new Patient(
                "P007",
                "James",
                "Jones",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        assertTrue(hospital.registerPatient(patient1));
        assertFalse(hospital.registerPatient(patient2));
    }

    @Test
    public void occupiedBedTest() {

        Inpatient patient1 = new Inpatient(
                "P008",
                "Ann",
                "Smith",
                30,
                "Female",
                "Flu",
                1,
                "None"
        );

        Inpatient patient2 = new Inpatient(
                "P009",
                "Ben",
                "Jones",
                32,
                "Male",
                "Cold",
                1,
                "None"
        );

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);

        assertTrue(hospital.allocateBed("P008", "B01"));
        assertFalse(hospital.allocateBed("P009", "B01"));
    }

    @Test
    public void fullWardTest() {

        for (int i = 1; i <= 20; i++) {

            String id = String.format("P%03d", i);
            String bed = String.format("B%02d", i);

            Inpatient patient = new Inpatient(
                    id,
                    "Patient",
                    "Number" + i,
                    20,
                    "Male",
                    "Condition",
                    1,
                    "None"
            );

            hospital.registerPatient(patient);

            assertTrue(
                    hospital.allocateBed(id, bed)
            );
        }

        assertEquals(0, hospital.getAvailableBeds());

        Inpatient extra = new Inpatient(
                "P021",
                "Extra",
                "Patient",
                20,
                "Male",
                "Condition",
                1,
                "None"
        );

        hospital.registerPatient(extra);

        assertFalse(
                hospital.allocateBed("P021", "B01")
        );
    }
}