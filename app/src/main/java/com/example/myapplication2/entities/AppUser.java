package com.example.myapplication2.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

public class AppUser implements Serializable {

    @SerializedName("userId")
    private Long userId;

    @SerializedName("userName")
    private String userName;

    @SerializedName("encrytedPassword")
    private String encrytedPassword;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("enabled")
    private Integer enabled;

    @SerializedName("dateCreated")
    private Date dateCreated;

    public AppUser (){}

    public AppUser(String userName, String encrytedPassword, String userEmail, boolean enabled) {
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.userEmail  = userEmail;
        this.enabled = enabled?1:0;
        this.dateCreated = new java.sql.Date(Calendar.getInstance().getTime().getTime());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public Integer isEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getuserEmail() {
        return userEmail;
    }

    public void setuserEmail(String userEmail) {
        userEmail = userEmail;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public String toString(){
        return "{id:"+ userId + "userName:"+ this.userEmail+ ", password: "+ this.encrytedPassword  +"}";
    }
}

