/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Doctor;
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
public class DoctorDAO {

    public static List<Doctor> doctors = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorDAO.class);

    static {
        doctors.add(new Doctor(1, "Neranjan Perera", "0772572800", "Kalutara", "Diabetologist", 3000));
        doctors.add(new Doctor(2, "Tiran Gamlath", "0712359807", "Colombo-03", "Cardiologist", 3500));
    }

    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    public void deleteDoctor(int id) {
        doctors.removeIf(doctor -> doctor.getId() == id);
    }

    public void addDoctor(Doctor doctor) {
        if ((doctor.getName() != null && doctor.getName() instanceof String && !doctor.getName().isEmpty()) && (doctor.getAddress() != null && doctor.getAddress() instanceof String && !doctor.getAddress().isEmpty()) && (doctor.getContactInfo() != null && doctor.getContactInfo() instanceof String && !doctor.getContactInfo().isEmpty()) && (doctor.getSpecialization() != null && doctor.getSpecialization() instanceof String && !doctor.getSpecialization().isEmpty()) && doctor.getDoctorFee() > 0) {
            int newUserId = getNextUserId();
            doctor.setId(newUserId);
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
                if (doctor.getId() == updateDoctor.getId()) {
                    doctors.set(i, updateDoctor);
                    return;
                }
            }
        } else {
            LOGGER.error("Missing mandatory field(s) in doctor data. Failed to update the Doctor!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in doctor data. Failed to update the Doctor!", Response.Status.BAD_REQUEST);
        }
    }

    //HELPER METHODS
    public Doctor findDoctorById(int id) {
        Doctor doctorFound = null;
        for (Doctor doctor : doctors) {
            if (doctor.getId() == id) {
                doctorFound = doctor;
                break;
            }
        }
        return doctorFound;
    }

    public int getNextUserId() {
        int maxUserId = 0;
        if (doctors.size() > 0) {
            maxUserId = Integer.MIN_VALUE;

            for (Doctor doctor : doctors) {
                int userId = doctor.getId();
                if (userId > maxUserId) {
                    maxUserId = userId;
                }
            }
            return maxUserId + 1;
        } else {
            maxUserId = 1;
        }
        return maxUserId;
    }
}
