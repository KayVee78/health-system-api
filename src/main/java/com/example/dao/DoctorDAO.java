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
        if (doctor.getName() != null && doctor.getAddress() != null && doctor.getContactInfo() != null && doctor.getSpecialization() != null && doctor.getDoctorFee() != 0.0) {
            int newUserId = getNextUserId();
            doctor.setId(newUserId);
            doctors.add(doctor);
        } else {
            LOGGER.error("Missing mandatory field(s) in doctor data. Failed to add a new Doctor!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in doctor data. Failed to add a new Doctor!");
        }

    }

    public void updateDoctor(Doctor updateDoctor) {
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            if (doctor.getId() == updateDoctor.getId()) {
                doctors.set(i, updateDoctor);
                return;
            }
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
        int maxUserId = Integer.MIN_VALUE;

        for (Doctor doctor : doctors) {
            int userId = doctor.getId();
            if (userId > maxUserId) {
                maxUserId = userId;
            }
        }
        return maxUserId + 1;
    }
}
