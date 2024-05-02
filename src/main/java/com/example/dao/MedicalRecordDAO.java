/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import static com.example.dao.MedicalRecordDAO.medicalRecords;
import com.example.exception.ResourceNotFoundException;
import com.example.model.MedicalRecord;
import com.example.model.Patient;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
public class MedicalRecordDAO {

    public static List<MedicalRecord> medicalRecords = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordDAO.class);
    private PatientDAO patientDAO = new PatientDAO();

    static {
        medicalRecords.add(new MedicalRecord(1, 1, "Management of Type 2 Diabetes", "Type 2 Diabetes", "Monitor blood sugar levels regularly, Continue current medications", "None"));
        medicalRecords.add(new MedicalRecord(2, 2, "Acute upper respiratory infection (URI)", "Acute URI, viral", "Rest, fluids, over-the-counter pain relievers, recommend follow-up if symptoms worsen or persist", "None"));
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecords;
    }

    public void deleteMedicalRecord(int id) {
        medicalRecords.removeIf(medicalRecord -> medicalRecord.getRecordId() == id);
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        boolean isDuplicate = false;
        boolean isValidPatient = false;
        if (medicalRecord.getPatientId() != 0 && medicalRecord.getReasonToVisit() != null && medicalRecord.getDiagnosis() != null && medicalRecord.getMedications() != null && medicalRecord.getAllergies() != null) {
//            for (MedicalRecord record : medicalRecords) {
//                if (record.getPatientId() == medicalRecord.getPatientId()) {
//                    isDuplicate = true;
//                    break;
//                }
//            }
//            if (!isDuplicate) {
            for (Patient patient : PatientDAO.patients) {
                if (patient.getId() == medicalRecord.getPatientId()) {
                    isValidPatient = true;
                    break;
                }
            }
            if (isValidPatient) {
                int newRecordId = getNextRecordId();
                medicalRecord.setRecordId(newRecordId);
                medicalRecords.add(medicalRecord);
            } else {
                throw new ResourceNotFoundException("Invalid patient ID. Failed to add a new medical record!");
            }
//            }
        } else {
            LOGGER.error("Missing mandatory field(s) in medical record data. Failed to add a new medical record!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in medical record data. Failed to add a new medical record!");
        }

    }

    public void updateMedicalRecord(MedicalRecord updateMedicalRecord) {
        boolean isValidPatient = false;
        for (Patient patient : PatientDAO.patients) {
            if (patient.getId() == updateMedicalRecord.getPatientId()) {
                isValidPatient = true;
                break;
            }
        }
        if (isValidPatient) {
            for (int i = 0; i < medicalRecords.size(); i++) {
                MedicalRecord medicalRecord = medicalRecords.get(i);
                if (medicalRecord.getRecordId() == updateMedicalRecord.getRecordId()) {
                    medicalRecords.set(i, updateMedicalRecord);
                    return;
                }
            }
        } else {
            throw new ResourceNotFoundException("Error occured while updating a medical record. No patient found with ID: " + updateMedicalRecord.getPatientId());
        }

    }

    public MedicalRecord getMedicalRecordByPatientId(int id) {
        MedicalRecord medicalRecordFound = null;
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getPatientId() == id) {
                medicalRecordFound = medicalRecord;
                break;
            }
        }
        return medicalRecordFound;
    }

    //HELPER METHODS
    public MedicalRecord findMedicalRecordById(int id) {
        MedicalRecord medicalRecordFound = null;
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getRecordId() == id) {
                medicalRecordFound = medicalRecord;
                break;
            }
        }
        return medicalRecordFound;
    }

    public int getNextRecordId() {
        int maxUserId = Integer.MIN_VALUE;

        for (MedicalRecord medicalRecord : medicalRecords) {
            int userId = medicalRecord.getRecordId();
            if (userId > maxUserId) {
                maxUserId = userId;
            }
        }

        return maxUserId + 1;

    }
}
