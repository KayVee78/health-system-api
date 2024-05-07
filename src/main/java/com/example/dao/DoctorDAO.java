/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Doctor;
import com.example.model.Person;
import com.example.resource.DoctorResource;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
public class DoctorDAO extends PersonDAO {

    public static List<Doctor> doctors = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorDAO.class);

    static {
        doctors.add(new Doctor(3, "Neranjan Perera", "0772572800", "Kalutara", 1, "Diabetologist", 3000));
        doctors.add(new Doctor(4, "Tiran Gamlath", "0712359807", "Colombo-03", 2, "Cardiologist", 3500));
    }
    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    public void deleteDoctor(int id) {
        Doctor findDoctor = findDoctorById(id);
        deletePerson(findDoctor.getId());
        doctors.removeIf(doctor -> doctor.getDoctorId() == id);
    }

    public void addDoctor(Doctor doctor) {
        if ((doctor.getName() != null && doctor.getName() instanceof String && !doctor.getName().isEmpty()) && (doctor.getAddress() != null && doctor.getAddress() instanceof String && !doctor.getAddress().isEmpty()) && (doctor.getContactInfo() != null && doctor.getContactInfo() instanceof String && !doctor.getContactInfo().isEmpty()) && (doctor.getSpecialization() != null && doctor.getSpecialization() instanceof String && !doctor.getSpecialization().isEmpty()) && doctor.getDoctorFee() > 0) {
            int newPersonId = getNextUserId();
            int newDoctorId = getNextDoctorId();
            doctor.setId(newPersonId);
            doctor.setDoctorId(newDoctorId);
            addPerson(new Person(doctor.getId(), doctor.getName(), doctor.getContactInfo(), doctor.getAddress()));
            doctors.add(doctor);
        } else {
            LOGGER.error("Missing mandatory field(s) in doctor data. Failed to add a new Doctor!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in doctor data. Failed to add a new Doctor!", Response.Status.BAD_REQUEST);
        }

    }

    public void updateDoctor(Doctor updateDoctor) {
        if ((updateDoctor.getName() != null && updateDoctor.getName() instanceof String && !updateDoctor.getName().isEmpty()) && (updateDoctor.getAddress() != null && updateDoctor.getAddress() instanceof String && !updateDoctor.getAddress().isEmpty()) && (updateDoctor.getContactInfo() != null && updateDoctor.getContactInfo() instanceof String && !updateDoctor.getContactInfo().isEmpty()) && (updateDoctor.getSpecialization() != null && updateDoctor.getSpecialization() instanceof String && !updateDoctor.getSpecialization().isEmpty()) && updateDoctor.getDoctorFee() > 0) {
            for (int i = 0; i < doctors.size(); i++) {
                Doctor doctor = doctors.get(i);
                if (doctor.getDoctorId() == updateDoctor.getDoctorId()) {
                    Doctor updatingDoctor = findDoctorById(updateDoctor.getDoctorId());
                    updatePerson(new Person(updatingDoctor.getId(), updateDoctor.getName(), updateDoctor.getContactInfo(), updateDoctor.getAddress()));
                    updateDoctor.setId(updatingDoctor.getId());
                    doctors.set(i, updateDoctor);
                    return;
                }
            }
        } else {
            LOGGER.error("Missing mandatory field(s) in doctor data. Failed to update the Doctor!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in doctor data. Failed to update the Doctor!", Response.Status.BAD_REQUEST);
        }
    }

    public void updateDoctorFromPerson(Doctor updateDoctor) {
        if ((updateDoctor.getName() != null && updateDoctor.getName() instanceof String && !updateDoctor.getName().isEmpty()) && (updateDoctor.getAddress() != null && updateDoctor.getAddress() instanceof String && !updateDoctor.getAddress().isEmpty()) && (updateDoctor.getContactInfo() != null && updateDoctor.getContactInfo() instanceof String && !updateDoctor.getContactInfo().isEmpty()) && (updateDoctor.getSpecialization() != null && updateDoctor.getSpecialization() instanceof String && !updateDoctor.getSpecialization().isEmpty()) && updateDoctor.getDoctorFee() > 0) {
            Doctor updatingDoctor = findDoctorByPersonId(updateDoctor.getId());
            updateDoctor.setDoctorId(updatingDoctor.getDoctorId());
            for (int i = 0; i < doctors.size(); i++) {
                Doctor doctor = doctors.get(i);
                if (doctor.getDoctorId() == updateDoctor.getDoctorId()) {
                    doctors.set(i, updateDoctor);
                    return;
                }
            }
        } else {
            LOGGER.error("Missing mandatory field(s) in patient data. Failed to update Patient!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in patient data. Failed to update Patient!", Response.Status.BAD_REQUEST);
        }
    }

    //HELPER METHODS
    public Doctor findDoctorById(int id) {
        Doctor doctorFound = null;
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId() == id) {
                doctorFound = doctor;
                break;
            }
        }
        return doctorFound;
    }

    public Doctor findDoctorByPersonId(int id) {
        Doctor doctorFound = null;
        for (Doctor doctor : doctors) {
            if (doctor.getId() == id) {
                doctorFound = doctor;
                break;
            }
        }
        return doctorFound;
    }

    public int getNextDoctorId() {
        int maxUserId = 0;
        if (doctors.size() > 0) {
            maxUserId = Integer.MIN_VALUE;

            for (Doctor doctor : doctors) {
                int doctorId = doctor.getDoctorId();
                if (doctorId > maxUserId) {
                    maxUserId = doctorId;
                }
            }
            return maxUserId + 1;
        } else {
            return maxUserId = 1;
        }
    }
}
