/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.AppointmentDAO;
import com.example.exception.InternalServerErrorException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Appointment;
import com.example.model.Doctor;
import com.example.result.ResultData;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
}
