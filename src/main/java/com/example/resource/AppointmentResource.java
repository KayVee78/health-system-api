/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.AppointmentDAO;
import com.example.exception.InternalServerErrorException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Appointment;
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
@Path("/appointments")
public class AppointmentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentResource.class);

    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<List<Appointment>> getAllAppointments() {

        try {
            LOGGER.info("Fetching all the appointments");

            List<Appointment> appointments = appointmentDAO.getAllAppointments();
            LOGGER.info("{} appointments fetched", appointments.size());

            if (appointments.isEmpty()) {
                throw new ResourceNotFoundException("No appointments found!");
            } else {
                return new ResultData<>(appointments, "Appointments fetched successfully!", "success");
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }

    }

    @GET
    @Path("/{appointmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<Appointment> getAppointmentDetailsById(@PathParam("appointmentId") int appointmentId) {
        try {
            LOGGER.info("Getting appointment details by id: {}", appointmentId);
            Appointment appointment = appointmentDAO.findAppointmentById(appointmentId);
            if (appointment != null) {
                LOGGER.info("Appointment with ID {} fetched successfully!", appointmentId);
                return new ResultData<>(appointment, "Appointment with " + appointmentId + " fetched successfully!", "success");
            } else {
                throw new ResourceNotFoundException("No appointment found with ID: " + appointmentId);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal server error occured");
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWithNoData addAppointment(Appointment appointment) {
        try {
            List<Appointment> appointments = appointmentDAO.getNonModifiedAppointmentList();
            int prevAppointmentListSize = appointments.size();
            appointmentDAO.addAppointment(appointment);
            if (appointments.size() > prevAppointmentListSize) {
                LOGGER.info("New appointment added successfully!");
                return new ResultWithNoData("New appointment added successfully!", "success");
            } else {
                throw new ResourceNotFoundException("Failed to add a new appointment!");
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @PUT
    @Path("/{appointmentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWithNoData updatePatient(@PathParam("appointmentId") int appointmentId, Appointment updateAppointment) {
        try {
            Appointment existingAppointment = appointmentDAO.findAppointmentById(appointmentId);

            if (existingAppointment != null) {
                updateAppointment.setAppointmentId(appointmentId);
                appointmentDAO.updateAppointmentData(updateAppointment);
                LOGGER.info("Appointment with ID {} updated successfully!", appointmentId);
                return new ResultWithNoData("Appointment with ID " + appointmentId + " updated successfully!", "success");
            } else {
                throw new ResourceNotFoundException("No appointment found with ID: " + appointmentId);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{appointmentId}")
    public ResultWithNoData deleteAppointment(@PathParam("appointmentId") int appointmentId) {
        try {
            Appointment appointment = appointmentDAO.findAppointmentById(appointmentId);
            if (appointment != null) {
                appointmentDAO.deleteAppointment(appointmentId);
                LOGGER.info("Appointment with ID {} removed successfully!", appointmentId);

                return new ResultWithNoData("Appointment with ID " + appointmentId + " removed successfully!", "success");
            } else {
                throw new ResourceNotFoundException("No appointment found with ID: " + appointmentId);
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }
}
