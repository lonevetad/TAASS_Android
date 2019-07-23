package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.myapplication2.payloadsResponses.GroupFullDetail;

public class GroupInfoActivity extends AppCompatActivity {

    String groupId;
    String groupName;
    String groupDescription;
    String groupCreator;
    GroupFullDetail gFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        getIncomingIntent();
        System.out.println(toStringGroup());

    }

    public void getIncomingIntent(){
        Intent dataGIven;
        Log.d("GroupInfoActivity","getIncomingIntend: checking for incoming intents.");
        dataGIven = getIntent();
        if(dataGIven.hasExtra("groupId") &&  dataGIven.hasExtra("groupName") && dataGIven.hasExtra("groupDescription") && dataGIven.hasExtra("groupCreator")){

            System.out.println("ASDASDASDASD: "+ dataGIven.getStringExtra("groupId"));
            this.groupCreator = dataGIven.getStringExtra("groupCreator");
            this.groupDescription = dataGIven.getStringExtra("groupDescription");
            this.groupId = dataGIven.getStringExtra("groupId");
            this.groupName = dataGIven.getStringExtra("groupName");
            this.gFull = (GroupFullDetail) dataGIven.getSerializableExtra("groupFullDetail");
        }
    }

    public String toStringGroup(){
        return "Id: " + this.groupId +
                " GroupCreator: " + this.groupCreator +
                " GroupDescription: " + this.groupDescription +
                " GroupName: " + this.groupName
                + " GroupDate: " + this.gFull.getGroupDate()
                + " GroupLocation: " + this.gFull.getLocation()
                + " GroupTags: " + this.gFull.getTags()
                + " GroupMembers: " + this.gFull.getMembers();
    }

}
