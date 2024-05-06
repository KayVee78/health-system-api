/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Prescription;
import com.example.model.Patient;
import com.example.model.Prescription;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
public class PrescriptionDAO {

    public static List<Prescription> prescriptions = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionDAO.class);
    private PatientDAO patientDAO = new PatientDAO();

    static {
        prescriptions.add(new Prescription(1, "1", List.of("Medication Name : Metformin, Dosage : 500mg, Duration : 1 tablet daily for 1 month", "Medication Name : Glipizide, Dosage : 5mg, Duration : 2 tablets twice a day for 2 months", "Medication Name : Sitagliptin, Dosage : 100mg, Duration : 1 tablet once a day for 2 months")));
        prescriptions.add(new Prescription(2, "2", List.of("Medication Name : Aspirin, Dosage : 81mg, Duration : 1 tablet daily indefinitely", "Medication Name : Atorvastatin, Dosage : 20mg, Duration : 1 tablet daily indefinitely", "Medication Name : Metoprolol, Dosage : 25mg, Duration : 1 tablet twice daily indefinitely")));
    }

    public List<Prescription> getAllPrescriptions() {
        List<Prescription> formattedPrescriptionList = new ArrayList<>();
        if (!prescriptions.isEmpty()) {
            for (Prescription prescription : prescriptions) {
                int patientId = Integer.parseInt(prescription.getPatientId());
                Patient patient = patientDAO.findPatientById(patientId);
                if (patient != null) {
                    String patientDetails = String.format("%d (%s)",
                            patientId,
                            patient.getName());

                    //Created a new Prescription List Object with formatted data
                    Prescription formattedPrescriptionObj = new Prescription(prescription.getPrescriptionId(), patientDetails, prescription.getMedication());
                    formattedPrescriptionList.add(formattedPrescriptionObj);
                } else {
                    throw new ResourceNotFoundException("Error occurred while finding a prescription for patient with ID: " + patientId + "", Response.Status.NOT_FOUND);
                }
            }
        }
        return formattedPrescriptionList;
    }

    public Prescription getPrescriptionById(int id) {
        Prescription formattedPrescription = null;
        Prescription prescription = findPrescriptionById(id);
        int patientId = Integer.parseInt(prescription.getPatientId());
        Patient patient = patientDAO.findPatientById(patientId);
        if (patient != null) {
            String patientDetails = String.format("%d (%s)",
                    patientId,
                    patient.getName());
            formattedPrescription = new Prescription(id, patientDetails, prescription.getMedication());
        } else {
            throw new ResourceNotFoundException("Error occurred while finding a prescription for patient with ID: " + patientId, Response.Status.NOT_FOUND);
        }
        return formattedPrescription;
    }

    public void deletePrescription(int id) {
        prescriptions.removeIf(prescription -> prescription.getPrescriptionId() == id);
    }

    public void addPrescription(Prescription prescription) {
        if ((prescription.getPatientId() != null && isNumeric(prescription.getPatientId())) && (prescription.getMedication() != null && !prescription.getMedication().isEmpty() && prescription.getMedication() instanceof List)) {
            boolean isValidPatient = false;
            for (Patient patient : PatientDAO.patients) {
                if (patient.getId() == Integer.parseInt(prescription.getPatientId())) {
                    isValidPatient = true;
                    break;
                }
            }
            if (!isValidPatient) {
                throw new ResourceNotFoundException("Invalid patient ID. Failed to add a new prescription!", Response.Status.NOT_FOUND);
            }

            int newPrescriptionId = getNextPrescriptionId();
            prescription.setPrescriptionId(newPrescriptionId);
            prescriptions.add(prescription);
        } else {
            LOGGER.error("Missing mandatory field(s) in prescription data. Failed to add a prescription!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in prescription data. Failed to add a new prescription!", Response.Status.BAD_REQUEST);
        }

    }

    public void updatePrescription(Prescription updatePrescription) {
        if ((updatePrescription.getPatientId() != null && isNumeric(updatePrescription.getPatientId())) && (updatePrescription.getMedication() != null && !updatePrescription.getMedication().isEmpty() && updatePrescription.getMedication() instanceof List)) {

            boolean isValidPatient = false;
            for (Patient patient : PatientDAO.patients) {
                if (patient.getId() == Integer.parseInt(updatePrescription.getPatientId())) {
                    isValidPatient = true;
                    break;
                }
            }
            if (isValidPatient) {
                for (int i = 0; i < prescriptions.size(); i++) {
                    Prescription prescription = prescriptions.get(i);
                    if (prescription.getPrescriptionId() == updatePrescription.getPrescriptionId()) {
                        prescriptions.set(i, updatePrescription);
                        return;
                    }
                }
            } else {
                throw new ResourceNotFoundException("Error occured while updating a prescription. No patient found with ID: " + updatePrescription.getPatientId(), Response.Status.NOT_FOUND);
            }
        } else {
            LOGGER.error("Missing mandatory field(s) in prescription data. Failed to update prescription!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in prescription data. Failed to update prescription!", Response.Status.BAD_REQUEST);
        }
    }

    public Prescription getPrescriptionByPatientId(int id) {
        Prescription prescriptionFound = null;
        for (Prescription prescription : prescriptions) {
            if (Integer.parseInt(prescription.getPatientId()) == id) {
                Patient patient = patientDAO.findPatientById(id);
                String patientDetails = String.format("%d (%s)",
                        id,
                        patient.getName());
                prescription.setPatientId(patientDetails);
                prescriptionFound = prescription;
                break;
            }
        }
        return prescriptionFound;
    }

    //HELPER METHODS
    public Prescription findPrescriptionById(int id) {
        Prescription prescriptionFound = null;
        for (Prescription prescription : prescriptions) {
            if (prescription.getPrescriptionId() == id) {
                prescriptionFound = prescription;
                break;
            }
        }
        return prescriptionFound;
    }

    public int getNextPrescriptionId() {
        int maxPrescriptionId = 0;
        if (prescriptions.size() > 0) {
            maxPrescriptionId = Integer.MIN_VALUE;

            for (Prescription prescription : prescriptions) {
                int prescriptionId = prescription.getPrescriptionId();
                if (prescriptionId > maxPrescriptionId) {
                    maxPrescriptionId = prescriptionId;
                }
            }
            return maxPrescriptionId + 1;
        } else {
            maxPrescriptionId = 1;
        }
        return maxPrescriptionId;
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
}
