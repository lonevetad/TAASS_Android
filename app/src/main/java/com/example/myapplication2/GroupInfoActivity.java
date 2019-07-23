package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GroupInfoActivity extends AppCompatActivity {

    String groupId;
    String groupName;
    String groupDescription;
    String groupCreator;


    Button nav_esplora;
    Button nav_profilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);


        setUpNav();
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

    public void setUpNav(){

        nav_esplora = (Button) findViewById(R.id.nav_esplora);
        nav_profilo = (Button) findViewById(R.id.nav_profilo);


        nav_esplora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupInfoActivity.this, EsploraActivity.class);
                startActivity(intent);
            }
        });

        nav_profilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupInfoActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });
    }

}
