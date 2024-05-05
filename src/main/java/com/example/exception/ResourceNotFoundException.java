/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.exception;

import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Kithmi
 */
public class ResourceNotFoundException extends RuntimeException {

    private final Status status;

    public ResourceNotFoundException(String message, Status status) {
        super(message);
        this.status = status;
    }
    
    public Status getStatus() {
        return status;
    }
}
