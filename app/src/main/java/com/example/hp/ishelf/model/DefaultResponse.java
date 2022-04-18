package com.example.hp.ishelf.model;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {
    @SerializedName("error")
    private boolean err;
    @SerializedName("message")
    private String msg;

    //constructor
    public DefaultResponse(boolean err, String msg) {
        this.err = err;
        this.msg = msg;
    }

    //getter method
    public boolean isErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }

}
