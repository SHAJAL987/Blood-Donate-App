package com.example.bdl;

import java.io.Serializable;

public class UserResponse implements Serializable {
    private int ID;
    private String USER_NAME;
    private String BLOOD_GROUP;
    private String LAST_BLOOD_DONATE;
    private String PREFIX;
    private String MOBILE_NUMBER;
    private String SEX;

    public String getMOBILE_NUMBER() {
        return MOBILE_NUMBER;
    }

    public void setMOBILE_NUMBER(String MOBILE_NUMBER) {
        this.MOBILE_NUMBER = MOBILE_NUMBER;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getPREFIX() {
        return PREFIX;
    }

    public void setPREFIX(String PREFIX) {
        this.PREFIX = PREFIX;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getBLOOD_GROUP() {
        return BLOOD_GROUP;
    }

    public void setBLOOD_GROUP(String BLOOD_GROUP) {
        this.BLOOD_GROUP = BLOOD_GROUP;
    }

    public String getLAST_BLOOD_DONATE() {
        return LAST_BLOOD_DONATE;
    }

    public void setLAST_BLOOD_DONATE(String LAST_BLOOD_DONATE) {
        this.LAST_BLOOD_DONATE = LAST_BLOOD_DONATE;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "ID=" + ID +
                ", USER_NAME='" + USER_NAME + '\'' +
                ", BLOOD_GROUP='" + BLOOD_GROUP + '\'' +
                ", LAST_BLOOD_DONATE='" + LAST_BLOOD_DONATE + '\'' +
                ", LAST_BLOOD_DONATE='" + PREFIX + '\'' +
                ", MOBILE_NUMBER='" + MOBILE_NUMBER + '\'' +
                ", SEX='" + SEX + '\'' +
                '}';
    }

}
