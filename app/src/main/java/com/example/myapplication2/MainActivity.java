package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


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

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String url = "http:/192.168.43.252:8080/", serverGroup = url + "Group/", serverUser = url + "User/";

    Button button;
    Button button_getAllUser;
    Button button_changePage;
    Button button_login;
    Button button_getGroupByGroupId;
    Button button_userPage;
    TextView textView;
    private Gson gson;

    private List<AppGroup> appGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        button = (Button) findViewById(R.id.bn);
        button_getAllUser = (Button) findViewById(R.id.getAllUser);
        button_getGroupByGroupId = (Button) findViewById(R.id.btnGroupById);
        button_login = (Button) findViewById(R.id.button_login);
        button_changePage = (Button) findViewById(R.id.button3);
        button_userPage= (Button) findViewById(R.id.button_userPage);
        textView = (TextView) findViewById(R.id.txt);




        //------------------------------------------------------------------------------GET ALL USER
        button_getAllUser.setOnClickListener(new View.OnClickListener() {
            String server_url = serverUser + "listAllUsers";
            @Override
            public void onClick(View v) {
                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText("");
                        System.out.println(response);
                        textView.setText(response);
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Something went wrong");
                        error.printStackTrace();
                        requestQueue.stop();
                    }
                });
                requestQueue.add(stringRequest);
            }
        });

        //----------------------------------------------------------------------------- GET ALL GROUP
        button.setOnClickListener(new View.OnClickListener() {
            String server_url = serverGroup + "listGroupRest";
            @Override
            public void onClick(View v) {

                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;

                            //Creo gsonBuilder
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            gsonBuilder.setDateFormat("yyyy/MM/dd");
                            gson = gsonBuilder.create();
                            Type typeOfList = new TypeToken<ArrayList<AppGroup>>(){}.getType();
                            appGroups = gson.fromJson(response,typeOfList);

                            System.out.println(response);
                            System.out.println("AppGroupName:" + appGroups.get(0).getGroupName());
                            textView.setText(response);
                            requestQueue.stop();



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        textView.setText("Something went wrong");
                        error.printStackTrace();
                        requestQueue.stop();
                    }
                });
                requestQueue.add(stringRequest);

            }
        });

        //----------------------------------------------------------------------------- GET GROUP BY GROUP ID --------------------------------------

        button_getGroupByGroupId.setOnClickListener(new View.OnClickListener() {
            List<AppGroup> myList;
            int groupId = 4;
            String server_url = serverGroup + "info/" + groupId;
            @Override
            public void onClick(View v) {

                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //myList = response;
                        System.out.println(response);
                        textView.setText(response);
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        textView.setText("Something went wrong");
                        error.printStackTrace();
                        requestQueue.stop();
                    }
                });
                requestQueue.add(stringRequest);

            }
        });

        //------------------------------------------------------------------------- CHANGE PAGE ------------------------------------------------------

        button_changePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EsploraActivity.class);
                startActivity(intent);
            }
        });

        //------------------------------------------------------------------------- CHANGE PAGE LOGIN-----------------------------------------------

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogInActivity();
            }
        });

        //-----------------------------------------------------------------------------CHANGE PAGE USER--------------------------------------

        button_userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });

        /*
         ora eseguiamo tutte le cose secondarie, dopo il caricamento
        * */

        if(! UserLogged.getInstance().isLogged()){
            //not logged? -> logga !
            goToLogInActivity();
        }

        // end on create
    }

    public void provaRichiesta(){


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //

    // altro

    protected void goToLogInActivity(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
