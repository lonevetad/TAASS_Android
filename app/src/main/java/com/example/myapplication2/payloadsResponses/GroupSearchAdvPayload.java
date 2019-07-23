package com.example.myapplication2.payloadsResponses;


import com.example.myapplication2.entities.GoogleLocation;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class GroupSearchAdvPayload implements Serializable {

    @SerializedName("isSearchingByCreator")
    private boolean isSearchingByCreator = true;

    @SerializedName("groupName")
    private String groupName;

    @SerializedName("creatorMember")
    private String creatorMember;

    @SerializedName("dateStartRange")
    private java.sql.Date dateStartRange;

    @SerializedName("dateEndRange")
    private java.sql.Date dateEndRange;

    @SerializedName("location")
    private GoogleLocation location; // use GOOGLE MAPS's documentation

    @SerializedName("maxDistance")
    private Double maxDistance;

    @SerializedName("tags")
    private List<String> tags;

    //

    public GroupSearchAdvPayload() {
        this(true, "", "", null, null, null, Double.valueOf(0.0), new LinkedList<String>());
    }

    public GroupSearchAdvPayload(boolean isSearchingByCreator,
                                 String creatorMember,
                                 String groupName,
                                 GoogleLocation location,
                                 Date dateStartRange,
                                 Date dateEndRange,
                                 Double maxDistance,
                                 List<String> tags) {
        this.isSearchingByCreator = isSearchingByCreator;
        this.creatorMember = creatorMember;
        this.groupName = groupName;
        this.location = location;
        //this.genre = genre;
        this.dateStartRange = dateStartRange;
        this.dateEndRange = dateEndRange;
        this.maxDistance = maxDistance;
        this.tags = tags;
    }

    //


    public boolean isSearchingByCreator() {
        return isSearchingByCreator;
    }

    public void setSearchingByCreator(boolean searchingByCreator) {
        isSearchingByCreator = searchingByCreator;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreatorMember() {
        return creatorMember;
    }

    public void setCreatorMember(String creatorMember) {
        this.creatorMember = creatorMember;
    }

    public Date getDateStartRange() {
        return dateStartRange;
    }

    public void setDateStartRange(Date dateStartRange) {
        this.dateStartRange = dateStartRange;
    }

    public Date getDateEndRange() {
        return dateEndRange;
    }

    public void setDateEndRange(Date dateEndRange) {
        this.dateEndRange = dateEndRange;
    }

    public Double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public GoogleLocation getLocation() {
        return location;
    }

    public void setLocation(GoogleLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "GroupSearchAdvPayload{" +
                "isSearchingByCreator=" + isSearchingByCreator +
                ", creatorMember='" + creatorMember + '\'' +
                ", groupName='" + groupName + '\'' +
                ", location='" + location + '\'' +
                //", genre='" + genre + '\'' +
                ", dateStartRange=" + dateStartRange +
                ", dateEndRange=" + dateEndRange +
                ", maxDistance=" + maxDistance +
                ", tags=" + tags +
                '}';
    }
}
