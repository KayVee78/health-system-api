/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.BillingDAO;
import com.example.exception.InternalServerErrorException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Billing;
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
@Path("/bills")
public class BillingResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingResource.class);

    private BillingDAO billingDAO = new BillingDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<List<Billing>> getAllBills() {

        try {
            LOGGER.info("Fetching all the bills");

            List<Billing> bills = billingDAO.getAllBillingData();
            LOGGER.info("{} bills fetched", bills.size());

            if (bills.isEmpty()) {
                throw new ResourceNotFoundException("No bills found!");
            } else {
                return new ResultData<>(bills, "Bills fetched successfully!", "success");
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }

    }

    @GET
    @Path("/{billId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<Billing> getBillingDetailsById(@PathParam("billId") int billId) {
        try {
            LOGGER.info("Getting bill details by id: {}", billId);
            Billing bill = billingDAO.findBillingDataById(billId);
            if (bill != null) {
                LOGGER.info("Bill with ID {} fetched successfully!", billId);
                return new ResultData<>(bill, "Bill with " + billId + " fetched successfully!", "success");
            } else {
                throw new ResourceNotFoundException("No bill found with ID: " + billId);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal server error occured");
        }
    }
    
    @GET
    @Path("/appointment/{appointmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<Billing> getBillingDetailsByAppointmentId(@PathParam("appointmentId") int appointmentId) {
        try {
            LOGGER.info("Getting bill details by appointment id: {}", appointmentId);
            Billing bill = billingDAO.findBillingDataByAppointmentId(appointmentId);
            if (bill != null) {
                LOGGER.info("Bill with appointment ID {} fetched successfully!", appointmentId);
                return new ResultData<>(bill, "Bill with appointment ID" + appointmentId + " fetched successfully!", "success");
            } else {
                throw new ResourceNotFoundException("No bill found with appointment ID: " + appointmentId);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal server error occured");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWithNoData addBilling(Billing bill) {
        try {
            List<Billing> bills = billingDAO.getNonModifiedBillingList();
            int prevBillingListSize = bills.size();
            billingDAO.addBillingData(bill);
            if (bills.size() > prevBillingListSize) {
                LOGGER.info("New bill added successfully!");
                return new ResultWithNoData("New bill added successfully!", "success");
            } else {
                throw new ResourceNotFoundException("Failed to add a new bill!");
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @PUT
    @Path("/{billId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWithNoData updateBilling(@PathParam("billId") int billId, Billing updateBilling) {
        try {
            Billing existingBilling = billingDAO.findBillingDataById(billId);

            if (existingBilling != null) {
                updateBilling.setBillingId(billId);
                billingDAO.updateBillingData(updateBilling);
                LOGGER.info("Bill with ID {} updated successfully!", billId);
                return new ResultWithNoData("Bill with ID " + billId + " updated successfully!", "success");
            } else {
                throw new ResourceNotFoundException("No bill found with ID: " + billId);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{billId}")
    public ResultWithNoData deleteBilling(@PathParam("billId") int billId) {
        try {
            Billing bill = billingDAO.findBillingDataById(billId);
            if (bill != null) {
                billingDAO.deleteBillingData(billId);
                LOGGER.info("Bill with ID {} removed successfully!", billId);

                return new ResultWithNoData("Bill with ID " + billId + " removed successfully!", "success");
            } else {
                throw new ResourceNotFoundException("No bill found with ID: " + billId);
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }
}
