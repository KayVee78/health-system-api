/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.PatientDAO;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Patient;

import java.util.List;
import javax.ws.rs.DELETE;

import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
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
    public List<Patient> getAllPatients() {
        try {
            LOGGER.info("Fetching all the patients");

            List<Patient> patients = patientDAO.getAllPatients();
            LOGGER.info("{} patients fetched", patients.size());

            if (patients.isEmpty()) {
                throw new ResourceNotFoundException("No patients found!");
            }
            return patients;
        } catch (ResourceNotFoundException e) {
            LOGGER.warn("No patients found!");
            throw e;
        } catch (Exception e) {
            LOGGER.error("Internal Server Error occured", e.getMessage());
            throw new InternalServerErrorException("Internal Server Error occurred");
        }
    }

    @DELETE
    @Path("/{patientId}")
    public String deletePatient(@PathParam("patientId") int patientId) {
        try {
            Patient patient = patientDAO.findPatientById(patientId);
            if (patient != null) {
                LOGGER.info("Patient with ID {} removed successfully!", patientId);
                patientDAO.deletePatient(patientId);
                return "Patient with ID " + patientId + " removed successfully!";
            } else {
                throw new ResourceNotFoundException("Patient with ID " + patientId + " not found!");
            }
        } catch (ResourceNotFoundException e) {
            LOGGER.warn("Patient with ID {} not found!", patientId);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Internal Server Error occured", e.getMessage());
            throw new InternalServerErrorException("Internal Server Error occurred");
        }
    }
}
