package com.amitu.ws.products.rest;

import java.util.Date;

public class ErrorMessage {
    private Date timeStamp;
    private String message;
    private String details;

    public ErrorMessage(Date timeStamp, String details, String message) {
        this.timeStamp = timeStamp;
        this.details = details;
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
