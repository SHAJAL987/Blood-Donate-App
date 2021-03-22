package com.example.bdl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("bloodgroup")
    @Expose
    private String bloodgroup;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("lastdonatedate")
    @Expose
    private String lastdonatedate;
    @SerializedName("mobilenumber")
    @Expose
    private String mobilenumber;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("username")
    @Expose
    private String username;

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastdonatedate() {
        return lastdonatedate;
    }

    public void setLastdonatedate(String lastdonatedate) {
        this.lastdonatedate = lastdonatedate;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
