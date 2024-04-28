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
import javax.ws.rs.DELETE;

import javax.ws.rs.GET;
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
                LOGGER.warn("No patients found!");
                throw new ResourceNotFoundException("No patients found!");
            } else {
                return new ResultData<>(patients, "Patients fetched successfully!", "success");
            }
        } catch (InternalServerErrorException e) {
            LOGGER.error("Internal Server Error occured", e.getMessage());
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
                LOGGER.info("Patient with ID {} removed successfully!", patientId);
                patientDAO.deletePatient(patientId);
                return new ResultWithNoData("Patient with ID " + patientId + " removed successfully!", "success");
            } else {
                LOGGER.warn("Patient with ID " + patientId + " not found!");
                throw new ResourceNotFoundException("Patient with ID " + patientId + " not found!");
            }

        } catch (InternalServerErrorException e) {
            LOGGER.error("Internal Server Error occured", e.getMessage());
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

}
