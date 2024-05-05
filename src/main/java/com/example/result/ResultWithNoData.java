/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.result;

import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Kithmi
 */
public class ResultWithNoData<T> {

    private String message;
    private Status status;

    public ResultWithNoData() {
    }

    public ResultWithNoData(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public String getMsg() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        StringBuilder responseJson = new StringBuilder("{");
        responseJson.append("\"status\": \"").append(status.getStatusCode()).append(" - ").append(status).append("\",");
        responseJson.append("\"message\": \"").append(message).append("\"}");
        return responseJson.toString();
    }

}
