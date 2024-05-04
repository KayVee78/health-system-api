/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

import java.util.List;

/**
 *
 * @author Kithmi
 */
public class Appointment {

    private int appointmentId;
    private String date; //year-month-date format
    private String time; //24 hour format
    private Patient patient;
    private Doctor doctor;
    private List<String> associatedParticipants;

    public Appointment() {
    }

    public Appointment(int appointmentId, String date, String time, Patient patient, Doctor doctor, List<String> associatedParticipants) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.patient = patient;
        this.associatedParticipants = associatedParticipants;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
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

    public List<String> getAssociatedParticipants() {
        return associatedParticipants;
    }

    public void setAssociatedParticipants(List<String> associatedParticipants) {
        this.associatedParticipants = associatedParticipants;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
