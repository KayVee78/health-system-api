/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kithmi
 */
public class Appointment {

    private int appointmentId;
    private String patientId;
    private String doctorId;
    private String date; //year-month-date format
    private String time; //24 hour format
    private List<String> asscoiatedPatients;

    public Appointment() {
    }

    public Appointment(int appointmentId, String patientId, String doctorId, String date, String time, List<String> asscoiatedPatients) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.asscoiatedPatients = asscoiatedPatients;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getAsscoiatedPatients() {
        return asscoiatedPatients;
    }

    public void setAsscoiatedPatients(List<String> asscoiatedPatients) {
        this.asscoiatedPatients = asscoiatedPatients;
    }

}
