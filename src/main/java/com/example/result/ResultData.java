/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.result;

import java.util.Objects;

/**
 *
 * @author Kithmi
 */
public class ResultData<T> {

    private T data;
    private String message;
    private String status;

    public ResultData() {
    }

    public ResultData(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public ResultData(T data, String message, String status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return message;
    }

    public String getStatus() {
        return status;
    }

//    @Override
//    public String toString() {
//        String responseJson = "{"
//                + "\"status\": \"" + status + "\","
//                + "\"message\": \"" + message + "\","
//                + "\"data\": \"" + data
//                + "}";
//        return responseJson;
//    }
    @Override
    public String toString() {
        StringBuilder responseJson = new StringBuilder("{");
        responseJson.append("\"status\": \"").append(status).append("\",");
        responseJson.append("\"message\": \"").append(message).append("\"");

        if (data != "null") {
            responseJson.append(", \"data\": ").append(data);
        }

        responseJson.append("}");
        return responseJson.toString();
    }

}
