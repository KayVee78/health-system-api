/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Appointment;
import com.example.model.Doctor;
import com.example.model.Patient;
import com.example.model.PatientMedicalHistory;
import com.example.resource.DoctorResource;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
public class AppointmentDAO {

    private static List<Appointment> appointments = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentDAO.class);
    private DoctorDAO doctorDAO = new DoctorDAO();
    private PatientDAO patientDAO = new PatientDAO();
    public static List<Doctor> doctors = DoctorDAO.doctors;

    static {
        appointments.add(new Appointment(1, "2", "4", "2024-05-02", "18:00:00", List.of("Gayani Fernando (Wife)", "Samadhi Fernando (Daughter)")));
        appointments.add(new Appointment(2, "1", "2", "2024-05-03", "17:00:00", List.of("Sanjeewani Dias (Wife)")));
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> formattedAppontmentList = new ArrayList<>();
        if (!appointments.isEmpty()) {
            for (Appointment appointment : appointments) {
                int appointmentId = appointment.getAppointmentId();
                int doctorId = Integer.parseInt(appointment.getDoctorId());
                Doctor doctor = doctorDAO.findDoctorById(doctorId);
                if (doctor != null) {
                    String doctorDetails = doctor.getName() + " (" + doctor.getSpecialization() + ")";

                    int patientId = Integer.parseInt(appointment.getPatientId());
                    Patient patient = patientDAO.findPatientById(patientId);
                    String patientDetails = String.format("%s (%s) - %d years - %s Patient",
                            patient.getName(),
                            patient.getContactInfo(),
                            patient.getMedicalHistory().getAge(),
                            patient.getMedicalHistory().getDiagnosis());

                    String date = appointment.getDate();
                    String time = appointment.getTime();
                    List<String> associatedParticipants = appointment.getAsscoiatedPatients();

                    //Created a new Appointment Object with formatted data
                    Appointment formattedAppointmentObj = new Appointment(appointmentId, patientDetails, doctorDetails, date, time, associatedParticipants);
                    formattedAppontmentList.add(formattedAppointmentObj);
                } else {
                    throw new ResourceNotFoundException("No doctor found for ID: " + doctorId);
                }
            }
        }
        return formattedAppontmentList;
    }
}
