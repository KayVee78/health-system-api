/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.MedicalRecordDAO;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.InternalServerErrorException;
import com.example.model.MedicalRecord;
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
@Path("/medicalRecords")
public class MedicalRecordResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordResource.class);

    private MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<List<MedicalRecord>> getAllMedicalRecords() {

        try {
            LOGGER.info("Fetching all the medicalRecords");

            List<MedicalRecord> medicalRecords = medicalRecordDAO.getAllMedicalRecords();
            LOGGER.info("{} medical records fetched", medicalRecords.size());

            if (medicalRecords.isEmpty()) {
                throw new ResourceNotFoundException("No medical records found!", Response.Status.NOT_FOUND);
            } else {
                return new ResultData<>(medicalRecords, "Medical records fetched successfully!", Response.Status.OK);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }

    }

    @GET
    @Path("/{medicalRecordId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<MedicalRecord> getMedicalRecordById(@PathParam("medicalRecordId") int medicalRecordId) {
        try {
            LOGGER.info("Getting medical record by id: {}", medicalRecordId);
            MedicalRecord medicalRecord = medicalRecordDAO.getMedicalRecordById(medicalRecordId);
            if (medicalRecord != null) {
                LOGGER.info("Medical record with ID {} fetched successfully!", medicalRecordId);
                return new ResultData<>(medicalRecord, "Medical record with " + medicalRecordId + " fetched successfully!", Response.Status.OK);
            } else {
                throw new ResourceNotFoundException("No medical record found with ID: " + medicalRecordId, Response.Status.NOT_FOUND);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }

    }

    @GET
    @Path("/patient/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<MedicalRecord> getMedicalRecordByPatientId(@PathParam("patientId") int patientId) {
        try {
            if (patientId > 0) {
                LOGGER.info("Getting medical record by id: {}", patientId);
                MedicalRecord medicalRecord = medicalRecordDAO.getMedicalRecordByPatientId(patientId);
                if (medicalRecord != null || medicalRecord.getRecordId() != 0) {
                    LOGGER.info("Medical record with ID {} fetched successfully!", patientId);
                    return new ResultData<>(medicalRecord, "Medical record with " + patientId + " fetched successfully!", Response.Status.OK);
                } else {
                    throw new ResourceNotFoundException("No medical record found with patient ID: " + patientId, Response.Status.NOT_FOUND);
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
    public ResultWithNoData addMedicalRecord(MedicalRecord medicalRecord) {
        try {
            List<MedicalRecord> medicalRecords = medicalRecordDAO.getAllMedicalRecords();
            int prevMedicalRecordListSize = medicalRecords.size();
            medicalRecordDAO.addMedicalRecord(medicalRecord);
            medicalRecords = medicalRecordDAO.getAllMedicalRecords();
            if (medicalRecords.size() > prevMedicalRecordListSize) {
                LOGGER.info("New medical record added successfully!");
                return new ResultWithNoData("New medical record added successfully!", Response.Status.OK);
            } else {
                throw new ResourceNotFoundException("Failed to add a new medical record!", Response.Status.BAD_REQUEST);
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @PUT
    @Path("/{medicalRecordId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWithNoData updateMedicalRecord(@PathParam("medicalRecordId") int medicalRecordId, MedicalRecord updateMedicalRecord) {
        try {
            MedicalRecord existingMedicalRecord = medicalRecordDAO.findMedicalRecordById(medicalRecordId);

            if (existingMedicalRecord != null) {
                updateMedicalRecord.setRecordId(medicalRecordId);
                medicalRecordDAO.updateMedicalRecord(updateMedicalRecord);
                LOGGER.info("Medical record with ID {} updated successfully!", medicalRecordId);
                return new ResultWithNoData("Medical record with ID " + medicalRecordId + " updated successfully!", Response.Status.OK);
            } else {
                throw new ResourceNotFoundException("No medical record found with ID: " + medicalRecordId, Response.Status.NOT_FOUND);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{medicalRecordId}")
    public ResultWithNoData deleteMedicalRecord(@PathParam("medicalRecordId") int medicalRecordId) {
        try {
            MedicalRecord medicalRecord = medicalRecordDAO.findMedicalRecordById(medicalRecordId);
            if (medicalRecord != null) {
                medicalRecordDAO.deleteMedicalRecord(medicalRecordId);
                LOGGER.info("Medical record with ID {} removed successfully!", medicalRecordId);

                return new ResultWithNoData("Medical record with ID " + medicalRecordId + " removed successfully!", Response.Status.OK);
            } else {
                throw new ResourceNotFoundException("No medical record found with ID: " + medicalRecordId, Response.Status.NOT_FOUND);
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

}
