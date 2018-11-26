package com.mongo.mongo.models;

public class SuccessResponseModel {
    private int statusCode;
    private String statusMessage;

    public SuccessResponseModel() {
    }

    public SuccessResponseModel(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
