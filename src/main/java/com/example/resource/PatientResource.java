/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.PatientDAO;
import com.example.model.Patient;

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
@Path("/patients")
public class PatientResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientResource.class);
    
    private PatientDAO patientDAO = new PatientDAO();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> getAllPatients(){
        return patientDAO.getAllPatients();
    }
}
