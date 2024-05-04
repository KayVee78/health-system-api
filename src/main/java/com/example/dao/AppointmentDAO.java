/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Appointment;
import com.example.model.Doctor;
import com.example.model.Patient;
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
        appointments.add(new Appointment(1, "2024-05-02", "18:00:00", new Patient(2), new Doctor(2), List.of("Gayani Fernando (Wife)", "Samadhi Fernando (Daughter)")));
        appointments.add(new Appointment(2, "2024-05-03", "17:00:00", new Patient(1), new Doctor(2), List.of("Sanjeewani Dias (Wife)")));
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> formattedAppontmentList = new ArrayList<>();
        if (!appointments.isEmpty()) {
            for (Appointment appointment : appointments) {
                int appointmentId = appointment.getAppointmentId();
                int doctorId = appointment.getDoctor().getId();
                Doctor doctor = doctorDAO.findDoctorById(doctorId);
                int patientId = appointment.getPatient().getId();
                Patient patient = patientDAO.findPatientById(patientId);

                if (doctor != null && patient != null) {
                    Doctor doctorObj = doctor;
                    String date = appointment.getDate();
                    String time = appointment.getTime();
                    List<String> associatedParticipants = appointment.getAssociatedParticipants();

                    //Created a new Appointment Object with formatted data
                    Appointment formattedAppointmentObj = new Appointment(appointmentId, date, time, patient, doctor, associatedParticipants);
                    formattedAppontmentList.add(formattedAppointmentObj);

                } else {
                    throw new ResourceNotFoundException("Error occurred while finding a doctor or patient with IDs");
                }
            }
        }
        return formattedAppontmentList;
    }

    public List<Appointment> getNonModifiedAppointmentList() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        if (appointment.getPatient().getId() > 0 && appointment.getDoctor().getId() > 0 && (appointment.getDate() instanceof String && appointment.getDate() != null) && (appointment.getTime() instanceof String && appointment.getTime() != null)) {
            boolean isValidPatientId = false;
            boolean isValidDoctorId = false;
            for (Patient patient : PatientDAO.patients) {
                if (appointment.getPatient().getId() == patient.getId()) {
                    isValidPatientId = true;
                    break;
                }
            }

            for (Doctor doctor : DoctorDAO.doctors) {
                if (appointment.getDoctor().getId() == doctor.getId()) {
                    isValidDoctorId = true;
                    break;
                }
            }

            if (!isValidDoctorId && !isValidPatientId) {
                throw new ResourceNotFoundException("Error occured while adding a new appointment. No patient found with the ID " + appointment.getPatient().getId() + " and no doctor found with the ID " + appointment.getDoctor().getId());
            }
            if (!isValidPatientId) {
                throw new ResourceNotFoundException("Error occured while adding a new appointment. No patient found with ID: " + appointment.getPatient().getId());
            }
            if (!isValidDoctorId) {
                throw new ResourceNotFoundException("Error occured while adding a new appointment. No doctor found with ID: " + appointment.getDoctor().getId());
            }

            int newAppointmentId = getNextAppointmentId();
            appointment.setAppointmentId(newAppointmentId);
            appointments.add(appointment);

            return;
        } else {
            LOGGER.error("Missing mandatory field(s) in appointment data. Failed to add a new Appointment!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in appointment data. Failed to add a new Appointment!");
        }

    }

    public void updateAppointmentData(Appointment updateAppointment) {
        if (updateAppointment.getPatient().getId() > 0 && updateAppointment.getDoctor().getId() > 0 && updateAppointment.getDate() instanceof String && updateAppointment.getTime() instanceof String) {
            boolean isValidPatientId = false;
            boolean isValidDoctorId = false;
            for (Patient patient : PatientDAO.patients) {
                if (updateAppointment.getPatient().getId() == patient.getId()) {
                    isValidPatientId = true;
                    break;
                }
            }

            for (Doctor doctor : DoctorDAO.doctors) {
                if (updateAppointment.getDoctor().getId() == doctor.getId()) {
                    isValidDoctorId = true;
                    break;
                }
            }

            if (!isValidDoctorId && !isValidPatientId) {
                throw new ResourceNotFoundException("Error occured while updating an appointment. No patient found with the ID " + updateAppointment.getPatient().getId() + " and no doctor found with the ID " + updateAppointment.getDoctor().getId());
            }
            if (!isValidPatientId) {
                throw new ResourceNotFoundException("Error occured while updating an appointment. No patient found with ID: " + updateAppointment.getPatient().getId());
            }
            if (!isValidDoctorId) {
                throw new ResourceNotFoundException("Error occured while updating an appointment. No doctor found with ID: " + updateAppointment.getDoctor().getId());
            }

            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                if (appointment.getAppointmentId() == updateAppointment.getAppointmentId()) {
                    appointments.set(i, updateAppointment);
                    return;
                }
            }
        }
    }

    public void deleteAppointment(int id) {
        appointments.removeIf(appointment -> appointment.getAppointmentId() == id);
    }

    //HELPER METHODS
    public Appointment findAppointmentById(int id) {
        Appointment appointmentDataFound = null;
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId() == id) {
                int doctorId = appointment.getDoctor().getId();
                Doctor doctor = doctorDAO.findDoctorById(doctorId);
                int patientId = appointment.getPatient().getId();
                Patient patient = patientDAO.findPatientById(patientId);
                if (doctor != null && patient != null) {
                    String date = appointment.getDate();
                    String time = appointment.getTime();
                    List<String> associatedParticipants = appointment.getAssociatedParticipants();

                    //Created a new Appointment Object with formatted data
                    Appointment formattedAppointmentObj = new Appointment(id, date, time, patient, doctor, associatedParticipants);
                    appointmentDataFound = formattedAppointmentObj;
                    break;
                } else {
                    throw new ResourceNotFoundException("Error occurred while finding a doctor or patient with IDs");
                }
            }
        }
        return appointmentDataFound;
    }

    public int getNextAppointmentId() {
        int maxappointmentId = Integer.MIN_VALUE;

        for (Appointment appointment : appointments) {
            int appointmentId = appointment.getAppointmentId();
            if (appointmentId > maxappointmentId) {
                maxappointmentId = appointmentId;
            }
        }

        return maxappointmentId + 1;
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
}
