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
@Path("/bills")
public class BillingResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingResource.class);

    private BillingDAO billingDAO = new BillingDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<List<Billing>> getAllBills() {

        try {
            LOGGER.info("Fetching all the bills");

            List<Billing> bills = billingDAO.getAllBills();
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
}
