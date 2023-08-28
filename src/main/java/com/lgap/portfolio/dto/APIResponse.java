package com.lgap.portfolio.dto;

public class APIResponse<T> {
    private boolean error;
    private String msg;
    private T payload;

    private int statusCode;



    public APIResponse(boolean error, String msg, T payload, int statusCode) {
        this.error = error;
        this.msg = msg;
        this.payload = payload;
        this.statusCode = statusCode;
    }


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
