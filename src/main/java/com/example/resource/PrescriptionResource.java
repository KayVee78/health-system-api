/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.PrescriptionDAO;
import com.example.exception.InternalServerErrorException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Prescription;
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
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
@Path("/prescriptions")
public class PrescriptionResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionResource.class);

    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<List<Prescription>> getAllPrescriptions() {

        try {
            LOGGER.info("Fetching all the prescriptions");

            List<Prescription> prescriptions = prescriptionDAO.getAllPrescriptions();
            LOGGER.info("{} prescriptions fetched", prescriptions.size());

            if (prescriptions.isEmpty()) {
                throw new ResourceNotFoundException("No prescriptions found!", Response.Status.NOT_FOUND);
            } else {
                return new ResultData<>(prescriptions, "Prescriptions fetched successfully!", Response.Status.OK);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }

    }

    @GET
    @Path("/{prescriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<Prescription> getPrescriptionById(@PathParam("prescriptionId") int prescriptionId) {
        try {
            LOGGER.info("Getting prescription by id: {}", prescriptionId);
            Prescription prescription = prescriptionDAO.getPrescriptionById(prescriptionId);
            if (prescription != null) {
                LOGGER.info("Prescription with ID {} fetched successfully!", prescriptionId);
                return new ResultData<>(prescription, "Prescription with " + prescriptionId + " fetched successfully!", Response.Status.OK);
            } else {
                throw new ResourceNotFoundException("No prescription found with ID: " + prescriptionId, Response.Status.NOT_FOUND);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }

    }

    @GET
    @Path("/patient/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<Prescription> getPrescriptionByPatientId(@PathParam("patientId") int patientId) {
        try {
            if (patientId > 0) {
                LOGGER.info("Getting prescription by id: {}", patientId);
                Prescription prescription = prescriptionDAO.getPrescriptionByPatientId(patientId);
                if (prescription != null || prescription.getPrescriptionId() != 0) {
                    LOGGER.info("Prescription with ID {} fetched successfully!", patientId);
                    return new ResultData<>(prescription, "Prescriptions with " + patientId + " fetched successfully!", Response.Status.OK);
                } else {
                    throw new ResourceNotFoundException("No prescription found for patient ID: " + patientId, Response.Status.NOT_FOUND);
                }
            } else {
                throw new ResourceNotFoundException("Invalid patient ID: " + patientId, Response.Status.NOT_FOUND);

            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWithNoData addPrescription(Prescription prescription) {
        try {
            List<Prescription> prescriptions = prescriptionDAO.getAllPrescriptions();
            int prevPrescriptionListSize = prescriptions.size();
            prescriptionDAO.addPrescription(prescription);
            prescriptions = prescriptionDAO.getAllPrescriptions();
            if (prescriptions.size() > prevPrescriptionListSize) {
                LOGGER.info("New prescription added successfully!");
                return new ResultWithNoData("New prescription added successfully!", Response.Status.OK);
            } else {
                throw new ResourceNotFoundException("Failed to add a new prescription!", Response.Status.BAD_REQUEST);
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @PUT
    @Path("/{prescriptionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWithNoData updatePrescription(@PathParam("prescriptionId") int prescriptionId, Prescription updatePrescription) {
        try {
            Prescription existingPrescription = prescriptionDAO.findPrescriptionById(prescriptionId);

            if (existingPrescription != null) {
                updatePrescription.setPrescriptionId(prescriptionId);
                prescriptionDAO.updatePrescription(updatePrescription);
                LOGGER.info("Prescription with ID {} updated successfully!", prescriptionId);
                return new ResultWithNoData("Prescription with ID " + prescriptionId + " updated successfully!", Response.Status.OK);
            } else {
                throw new ResourceNotFoundException("No prescription found with ID: " + prescriptionId, Response.Status.NOT_FOUND);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{prescriptionId}")
    public ResultWithNoData deletePrescription(@PathParam("prescriptionId") int prescriptionId) {
        try {
            Prescription prescription = prescriptionDAO.findPrescriptionById(prescriptionId);
            if (prescription != null) {
                prescriptionDAO.deletePrescription(prescriptionId);
                LOGGER.info("Prescription with ID {} removed successfully!", prescriptionId);

                return new ResultWithNoData("Prescription with ID " + prescriptionId + " removed successfully!", Response.Status.OK);
            } else {
                throw new ResourceNotFoundException("No prescription found with ID: " + prescriptionId, Response.Status.NOT_FOUND);
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

}
