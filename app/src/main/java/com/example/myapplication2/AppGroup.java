package com.example.myapplication2;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

public class AppGroup implements Serializable {

    @SerializedName("groupId")
    private Long groupId;

    @SerializedName("creatorId")
    private Long creatorId;

    @SerializedName("creato")
    private String creator;

    @SerializedName("groupName")
    private String groupName;

    @SerializedName("groupDate")
    private java.sql.Date groupDate;

    @SerializedName("locationId")
    private Long locationId;

    @SerializedName("description")
    private String description;


    //

    public AppGroup() {}

    public AppGroup(Long creatorId, String creator, String groupName, String description, Date groupDate) {
        this.creatorId = creatorId;
        this.groupName = groupName;
        this.description = description;
        this.groupDate = groupDate;
        this.creator = creator;
        this.locationId = null;
    }


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(Date groupDate) {
        this.groupDate = groupDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String groupCreator) {
        this.creator = groupCreator;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getLocation() {
        return locationId;
    }

    public void setLocation(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "AppGroup{" +
                "groupId='" + groupId +
                ", creator='" + creator + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupDate='" + groupDate +
                ", locationId='" + locationId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
