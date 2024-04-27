/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
@Provider
public class ResourceNotFoundExceptionMapper implements
        ExceptionMapper<ResourceNotFoundException> {

//    private static final Logger LOGGER
//            = Logger.getLogger(ResourceNotFoundExceptionMapper.class.getName());
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(ResourceNotFoundException exception) {
//        LOGGER.log(Level.ALL.SEVERE, "ResourceNotFoundException caught: {}"
//                + exception.getMessage(), exception);
        LOGGER.error("ResourceNotFoundException caught: {}",
                exception.getMessage(), exception);
        return Response.status(Response.Status.NOT_FOUND)
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
