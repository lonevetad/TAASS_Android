package com.example.myapplication2.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AppTag implements Serializable {

    @SerializedName("tagId")
    private Long tagId;

    @SerializedName("name")
    private String name;

    public AppTag(){}

    public AppTag(String name){
        this.name = name;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
