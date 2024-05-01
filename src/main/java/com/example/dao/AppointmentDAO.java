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
        appointments.add(new Appointment(1, "2", "1", "2024-05-02", "18:00:00", List.of("Gayani Fernando (Wife)", "Samadhi Fernando (Daughter)")));
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
                    if (patient != null) {
                        String patientDetails = String.format("%s (%s) - %d years - %s Patient",
                                patient.getName(),
                                patient.getContactInfo(),
                                patient.getMedicalHistory().getAge(),
                                patient.getMedicalHistory().getDiagnosis());

                        String date = appointment.getDate();
                        String time = appointment.getTime();
                        List<String> associatedParticipants = appointment.getassociatedParticipants();

                        //Created a new Appointment Object with formatted data
                        Appointment formattedAppointmentObj = new Appointment(appointmentId, patientDetails, doctorDetails, date, time, associatedParticipants);
                        formattedAppontmentList.add(formattedAppointmentObj);
                    } else {
                        throw new ResourceNotFoundException("Error occurred while finding a patient with ID: " + patientId);
                    }
                } else {
                    throw new ResourceNotFoundException("Error occurred while finding a doctor with ID: " + doctorId);
                }
            }
        }
        return formattedAppontmentList;
    }

    public List<Appointment> getNonModifiedAppointmentList() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        if (appointment.getPatientId() != null && appointment.getDoctorId() != null && appointment.getDate() != null && appointment.getTime() != null) {
            boolean isValidPatientId = false;
            boolean isValidDoctorId = false;
            for (Patient patient : PatientDAO.patients) {
                if (Integer.parseInt(appointment.getPatientId()) == patient.getId()) {
                    isValidPatientId = true;
                    break;
                }
            }

            for (Doctor doctor : DoctorDAO.doctors) {
                if (Integer.parseInt(appointment.getDoctorId()) == doctor.getId()) {
                    isValidDoctorId = true;
                    break;
                }
            }

            if (!isValidDoctorId && !isValidPatientId) {
                throw new ResourceNotFoundException("Error occured while adding a new appointment. No patient found with ID the " + appointment.getPatientId() + " and no doctor found with ID the " + appointment.getPatientId());
            }
            if (!isValidPatientId) {
                throw new ResourceNotFoundException("Error occured while adding a new appointment. No patient found with ID: " + appointment.getPatientId());
            }
            if (!isValidDoctorId) {
                throw new ResourceNotFoundException("Error occured while adding a new appointment. No doctor found with ID: " + appointment.getDoctorId());
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
        boolean isValidPatientId = false;
        boolean isValidDoctorId = false;
        for (Patient patient : PatientDAO.patients) {
            if (Integer.parseInt(updateAppointment.getPatientId()) == patient.getId()) {
                isValidPatientId = true;
                break;
            }
        }

        for (Doctor doctor : DoctorDAO.doctors) {
            if (Integer.parseInt(updateAppointment.getDoctorId()) == doctor.getId()) {
                isValidDoctorId = true;
                break;
            }
        }

        if (!isValidDoctorId && !isValidPatientId) {
            throw new ResourceNotFoundException("Error occured while updating an appointment. No patient found with ID the " + updateAppointment.getPatientId() + " and no doctor found with ID the " + updateAppointment.getPatientId());
        }
        if (!isValidPatientId) {
            throw new ResourceNotFoundException("Error occured while updating an appointment. No patient found with ID: " + updateAppointment.getPatientId());
        }
        if (!isValidDoctorId) {
            throw new ResourceNotFoundException("Error occured while updating an appointment. No doctor found with ID: " + updateAppointment.getDoctorId());
        }

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getAppointmentId() == updateAppointment.getAppointmentId()) {
                appointments.set(i, updateAppointment);
                return;
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
                int doctorId = Integer.parseInt(appointment.getDoctorId());
                Doctor doctor = doctorDAO.findDoctorById(doctorId);
                if (doctor != null) {
                    String doctorDetails = doctor.getName() + " (" + doctor.getSpecialization() + ")";

                    int patientId = Integer.parseInt(appointment.getPatientId());
                    Patient patient = patientDAO.findPatientById(patientId);
                    if (patient != null) {
                        String patientDetails = String.format("%s (%s) - %d years - %s Patient",
                                patient.getName(),
                                patient.getContactInfo(),
                                patient.getMedicalHistory().getAge(),
                                patient.getMedicalHistory().getDiagnosis());

                        String date = appointment.getDate();
                        String time = appointment.getTime();
                        List<String> associatedParticipants = appointment.getassociatedParticipants();

                        //Created a new Appointment Object with formatted data
                        Appointment formattedAppointmentObj = new Appointment(id, patientDetails, doctorDetails, date, time, associatedParticipants);
                        appointmentDataFound = formattedAppointmentObj;
                        break;
                    } else {
                        throw new ResourceNotFoundException("Error occurred while finding a patient with ID: " + patientId);
                    }
                } else {
                    throw new ResourceNotFoundException("Error occurred while finding a doctor with ID: " + doctorId);
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
}
