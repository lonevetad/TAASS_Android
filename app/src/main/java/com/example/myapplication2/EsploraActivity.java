package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication2.payloadsResponses.GroupFullDetail;
import com.example.myapplication2.payloadsResponses.GroupSearchAdvPayload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EsploraActivity extends AppCompatActivity {

    private Gson gson;
    private List<GroupFullDetail> allGroups;
    private GroupSearchAdvPayload filters;

    Button nav_esplora;
    Button nav_profilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esplora);

        setUpNav();
        setUpAllStuff();
        getAllGroup();
    }

    //Come mounted or create di vue
    public void setUpAllStuff() {
        GsonBuilder gsonBuilder;
        filters = new GroupSearchAdvPayload();
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy/MM/dd");
        gson = gsonBuilder.create();

    }

    public void getAllGroup() {

        String server_url = MainActivity.serverGroup + "listGroupRest";

        final RequestQueue requestQueue = Volley.newRequestQueue(EsploraActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Type typeOfList = new TypeToken<ArrayList<GroupFullDetail>>() {
                }.getType();
                allGroups = (List<GroupFullDetail>) gson.fromJson(response, typeOfList);
                System.out.println(response);
                System.out.println("AllGroup: " + allGroups);
                //System.out.println("AppGroupName:" + allGroups.get(0).getGroupName());
                requestQueue.stop();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listGroupEsplora);
                ListGroupFullDetail adapter = new ListGroupFullDetail(allGroups.toArray(new GroupFullDetail[allGroups.size()]));
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(EsploraActivity.this));

                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("Errore nella get all user");
                error.printStackTrace();
                requestQueue.stop();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void setUpNav(){

        nav_esplora = (Button) findViewById(R.id.esploraNav_esplora);
        nav_profilo = (Button) findViewById(R.id.esploraNav_profilo);


        nav_esplora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EsploraActivity.this, EsploraActivity.class);
                startActivity(intent);
            }
        });

        nav_profilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EsploraActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });
    }

}
