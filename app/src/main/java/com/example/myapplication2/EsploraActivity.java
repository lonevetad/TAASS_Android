package com.example.myapplication2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication2.entities.AppGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EsploraActivity extends AppCompatActivity {

    private Gson gson;
    private List<AppGroup> allGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setUpAllStuff();
        getAllGroup();
    }

    public void setUpAllStuff(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy/MM/dd");
        gson = gsonBuilder.create();
    }

    public void getAllGroup(){

        String server_url = MainActivity.serverGroup + "listGroupRest";

        final RequestQueue requestQueue = Volley.newRequestQueue(EsploraActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Type typeOfList = new TypeToken<ArrayList<AppGroup>>(){}.getType();
                allGroups = (List<AppGroup>)gson.fromJson(response,typeOfList);
                System.out.println(response);
                System.out.println("AllGroup: "+allGroups);
                System.out.println("AppGroupName:" + allGroups.get(0).getGroupName());
                requestQueue.stop();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                ListAppGroup adapter = new ListAppGroup(allGroups.toArray(new AppGroup[allGroups.size()]));
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

}
