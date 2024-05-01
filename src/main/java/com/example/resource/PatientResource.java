/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.PatientDAO;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.InternalServerErrorException;
import com.example.model.Patient;
import com.example.result.ResultData;
import com.example.result.ResultWithNoData;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
@Path("/patients")
public class PatientResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientResource.class);

    private PatientDAO patientDAO = new PatientDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<List<Patient>> getAllPatients() {

        try {
            LOGGER.info("Fetching all the patients");

            List<Patient> patients = patientDAO.getAllPatients();
            LOGGER.info("{} patients fetched", patients.size());

            if (patients.isEmpty()) {
                throw new ResourceNotFoundException("No patients found!");
            } else {
                return new ResultData<>(patients, "Patients fetched successfully!", "success");
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }

    }

    @GET
    @Path("/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<Patient> getPatientById(@PathParam("patientId") int patientId) {
        try {
            LOGGER.info("Getting patient by id: {}", patientId);
            Patient patient = patientDAO.findPatientById(patientId);
            if (patient != null) {
                LOGGER.info("Patient with ID {} fetched successfully!", patientId);
                return new ResultData<>(patient, "Patients with " + patientId + " fetched successfully!", "success");
            } else {
                throw new ResourceNotFoundException("No patient found with ID: " + patientId);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWithNoData addPatient(Patient patient) {
        try {
            List<Patient> patients = patientDAO.getAllPatients();
            int prevPatientListSize = patients.size();
            patientDAO.addPatient(patient);
            if (patients.size() > prevPatientListSize) {
                LOGGER.info("New patient added successfully!");
                return new ResultWithNoData("New patient added successfully!", "success");
            } else {
                throw new ResourceNotFoundException("Failed to add a new patient!");
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @PUT
    @Path("/{patientId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWithNoData updatePatient(@PathParam("patientId") int patientId, Patient updatePatient) {
        try {
            Patient existingPatient = patientDAO.findPatientById(patientId);

            if (existingPatient != null) {
                updatePatient.setId(patientId);
                patientDAO.updatePatient(updatePatient);
                LOGGER.info("Patient with ID {} updated successfully!", patientId);
                return new ResultWithNoData("Patient with ID " + patientId + " updated successfully!", "success");
            } else {
                throw new ResourceNotFoundException("No patient found with ID: " + patientId);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{patientId}")
    public ResultWithNoData deletePatient(@PathParam("patientId") int patientId) {
        try {
            Patient patient = patientDAO.findPatientById(patientId);
            if (patient != null) {
                patientDAO.deletePatient(patientId);
                LOGGER.info("Patient with ID {} removed successfully!", patientId);

                return new ResultWithNoData("Patient with ID " + patientId + " removed successfully!", "success");
            } else {
                throw new ResourceNotFoundException("No patient found with ID: " + patientId);
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

}
