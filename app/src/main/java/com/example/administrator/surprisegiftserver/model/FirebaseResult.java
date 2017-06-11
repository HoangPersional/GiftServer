package com.example.administrator.surprisegiftserver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 31/5/2017.
 */

public class FirebaseResult  {
    @SerializedName("success")
    public int success;
    public FirebaseResult(){}

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
    public String toString()
    {
        return String.valueOf(success);
    }
}
