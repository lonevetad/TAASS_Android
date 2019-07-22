package com.example.myapplication2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class GroupInfoActivity extends AppCompatActivity {

    String groupId;
    String groupName;
    String groupDescription;
    String groupCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        getIncomingIntent();
        System.out.println(toStringGroup());
    }

    public void getIncomingIntent(){
        Log.d("GroupInfoActivity","getIncomingIntend: checking for incoming intents.");
        if(getIntent().hasExtra("groupId") &&  getIntent().hasExtra("groupName") && getIntent().hasExtra("groupDescription") && getIntent().hasExtra("groupCreator")){

            System.out.println("ASDASDASDASD: "+ getIntent().getStringExtra("groupId"));
            this.groupCreator = getIntent().getStringExtra("groupCreator");
            this.groupDescription = getIntent().getStringExtra("groupDescription");
            this.groupId = getIntent().getStringExtra("groupId");
            this.groupName = getIntent().getStringExtra("groupName");

        }
    }

    public String toStringGroup(){
        return "Id: " + this.groupId +
                " GroupCreator: " + this.groupCreator +
                " GroupDescription: " + this.groupDescription +
                " GroupName: " + this.groupName;
    }

}
