package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EsploraActivity extends AppCompatActivity {

    private Gson gsonB;
    private List<GroupFullDetail> allGroups;
    private GroupSearchAdvPayload filters;


    EditText search_creator;
    EditText search_groupTitle;
    ImageButton find_group;
    Button nav_esplora;
    Button nav_profilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esplora);

        setUpViewComponent();
        setUpAllStuff();
        getAllGroup();
    }

    //Come mounted or create di vue
    public void setUpAllStuff() {

        GsonBuilder gsonBuilder;
        filters = new GroupSearchAdvPayload();
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy/MM/dd");
        this.gsonB = gsonBuilder.create();

    }

    public void getAllGroup() {

        String server_url = MainActivity.serverGroup + "listGroupRest";

        final RequestQueue requestQueue = Volley.newRequestQueue(EsploraActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Type typeOfList = new TypeToken<ArrayList<GroupFullDetail>>(){}.getType();
                allGroups = (List<GroupFullDetail>) gsonB.fromJson(response, typeOfList);
                System.out.println(response);
                System.out.println("------- Esplora Activity AllGroup: " + allGroups);
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

    public void setUpViewComponent(){
        search_creator = (EditText) findViewById(R.id.search_creator);
        search_groupTitle = (EditText) findViewById(R.id.search_groupName);
        find_group = (ImageButton) findViewById(R.id.button_searchgroup);
        find_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchGroup();

            }
        });
        //NavBar
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

    public void searchGroup(){


        GroupSearchAdvPayload gs = new GroupSearchAdvPayload();
        if(search_creator.getText().toString().length()> 0 ){
            String creator = search_creator.getText().toString().trim();
            gs.setCreatorMember(creator);
        }
        if( search_groupTitle.getText().toString().length()>0){
            String groupTitle = search_groupTitle.getText().toString().trim();
            gs.setGroupName(groupTitle);
        }

        Gson gson = new Gson();

        final String requestBody = gson.toJson(gs);
        System.out.println("In search: " + requestBody);
        String server_url = MainActivity.serverGroup + "advGroupSearch";
        final RequestQueue requestQueue = Volley.newRequestQueue(EsploraActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("RESPONSE LOGIN:" + response);
                Type typeOfList = new TypeToken<ArrayList<GroupFullDetail>>(){}.getType();
                allGroups = (List<GroupFullDetail>) gsonB.fromJson(response, typeOfList);
                System.out.println(response);
                System.out.println("------- Esplora Activity AllGroup: " + allGroups);
                //System.out.println("AppGroupName:" + allGroups.get(0).getGroupName());
                requestQueue.stop();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listGroupEsplora);
                ListGroupFullDetail adapter = new ListGroupFullDetail(allGroups.toArray(new GroupFullDetail[allGroups.size()]));
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(EsploraActivity.this));

                recyclerView.setAdapter(adapter);
                if(response.length() > 0 ){
                   System.out.println("RICERCA PER UTENTE" + response);
                }else{
                    System.out.println("FALLITO RICERCA PER UTENTE");
                }
                //textView.setText(response);
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                requestQueue.stop();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
            }
        };
        requestQueue.add(stringRequest);


    }

}
