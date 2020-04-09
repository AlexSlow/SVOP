package com.svop.exeptions.response;

import com.svop.message.SvopDefaultMessage;

public class ExceptionResponse implements SvopMessage{

    private String message;
    private String requestedURI;
    private Integer kod;

    public ExceptionResponse() {
    }

    public void setKod(Integer kod) {
        this.kod = kod;
    }

    public ExceptionResponse(String message, Integer kod) {
        this.message = message;
        this.kod = kod;
    }

    @Override
    public Integer getKod() {
        return kod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String errorMessage) {
        this.message = errorMessage;
    }

    public String getRequestedURI() {
        return requestedURI;
    }

    public void callerURL(final String requestedURI) {
        this.requestedURI = requestedURI;
    }
}
