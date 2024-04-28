/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
@Provider
public class InternalServerErrorExceptionMapper implements
        ExceptionMapper<InternalServerErrorException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalServerErrorExceptionMapper.class);

    @Override
    public Response toResponse(InternalServerErrorException exception) {
        LOGGER.error("InternalServerErrorException caught: {}",
                exception.getMessage(), exception);
        String responseJson = "{"
                + "\"status\": fail,"
                + "\"message\": \"" + exception.getMessage() + "\","
                + "}";

        // Return the response
        return Response.status(Response.Status.NOT_FOUND)
                .entity(responseJson)
                .type(MediaType.APPLICATION_JSON)
                .build();

    }
}
