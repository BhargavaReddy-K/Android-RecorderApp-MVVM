package com.company.recorder.model.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseModelArray<T> {


    @SerializedName("music")
    @Expose
    private List<T> data = null;
    @SerializedName("message")
    @Expose
    private String message;


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

