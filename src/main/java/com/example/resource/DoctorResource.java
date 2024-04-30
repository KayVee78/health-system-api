/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.DoctorDAO;
import com.example.exception.InternalServerErrorException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Doctor;
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
@Path("/doctors")
public class DoctorResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorResource.class);

    private DoctorDAO doctorDAO = new DoctorDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<List<Doctor>> getAllDoctors() {

        try {
            LOGGER.info("Fetching all the doctors");

            List<Doctor> doctors = doctorDAO.getAllDoctors();
            LOGGER.info("{} doctors fetched", doctors.size());

            if (doctors.isEmpty()) {
                throw new ResourceNotFoundException("No doctors found!");
            } else {
                return new ResultData<>(doctors, "Doctors fetched successfully!", "success");
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }

    }

    @GET
    @Path("/{doctorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<Doctor> getDoctorById(@PathParam("doctorId") int doctorId) {
        try {
            LOGGER.info("Getting doctor by id: {}", doctorId);
            Doctor doctor = doctorDAO.findDoctorById(doctorId);
            if (doctor != null) {
                LOGGER.info("Doctor with ID {} fetched successfully!", doctorId);
                return new ResultData<>(doctor, "Doctors with " + doctorId + " fetched successfully!", "success");
            } else {
                throw new ResourceNotFoundException("No doctor found for ID: " + doctorId);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWithNoData addDoctor(Doctor doctor) {
        try {
            List<Doctor> doctors = doctorDAO.getAllDoctors();
            int prevDoctorListSize = doctors.size();
            doctorDAO.addDoctor(doctor);
            if (doctors.size() > prevDoctorListSize) {
                LOGGER.info("New doctor added successfully!");
                return new ResultWithNoData("New doctor added successfully!", "success");
            } else {
                throw new ResourceNotFoundException("Failed to add a new doctor!");
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @PUT
    @Path("/{doctorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWithNoData updateDoctor(@PathParam("doctorId") int doctorId, Doctor updateDoctor) {
        try {
            Doctor existingDoctor = doctorDAO.findDoctorById(doctorId);

            if (existingDoctor != null) {
                updateDoctor.setId(doctorId);
                doctorDAO.updateDoctor(updateDoctor);
                LOGGER.info("Doctor with ID {} updated successfully!", doctorId);
                return new ResultWithNoData("Doctor with ID " + doctorId + " updated successfully!", "success");
            } else {
                throw new ResourceNotFoundException("Doctor with ID " + doctorId + " not found!");
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{doctorId}")
    public ResultWithNoData deleteDoctor(@PathParam("doctorId") int doctorId) {
        try {
            Doctor doctor = doctorDAO.findDoctorById(doctorId);
            if (doctor != null) {
                doctorDAO.deleteDoctor(doctorId);
                LOGGER.info("Doctor with ID {} removed successfully!", doctorId);

                return new ResultWithNoData("Doctor with ID " + doctorId + " removed successfully!", "success");
            } else {
                throw new ResourceNotFoundException("Doctor with ID " + doctorId + " not found!");
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

}
