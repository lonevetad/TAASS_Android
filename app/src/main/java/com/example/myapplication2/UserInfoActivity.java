package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class UserInfoActivity extends AppCompatActivity {

    TextView username;
    TextView email;
    TextView description;


    Button nav_esplora;
    Button nav_profilo;
    ImageButton button_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        setUpNav();
        setUpAllViewStuff();
        loadUserGroups();

        username = (TextView) findViewById(R.id.info_username);
        email = (TextView) findViewById(R.id.info_email);
        description = (TextView) findViewById(R.id.info_description);

        UserLogged utente = UserLogged.getInstance();

        if (utente.isLogged()) {
            username.setText(utente.getUserName());
            email.setText(utente.getEmail());
            description.setText("Ludovica ha 18 anni, è bionda, ha gli occhi verdi, " +
                    "il colore della pelle molto chiaro ed è alta 1, 65 cm. E' una studentessa e frequenta da quattro anni" +
                    " il liceo classico di Siena. E' una ragazza molto allegra, espansiva e ha molti amici. Nel suo tempo libero " +
                    "ha due hobby in particolare: collezionare monete di tutti i Paesi del mondo e praticare lo sport dell'equitazione. ");
        } else {
            //not logged? -> logga !
            goToLogInActivity();
        }
    }


    @Override
    protected void onResume() {
        //ricontrolliamo se lutente è loggato
        System.out.println("Nella resume");
        super.onResume();

        if (!UserLogged.getInstance().isLogged()) {
            //not logged? -> logga !
            goToLogInActivity();
        }
    }

    public void setUpNav() {

        nav_esplora = (Button) findViewById(R.id.nav_esplora);
        nav_profilo = (Button) findViewById(R.id.nav_profilo);


        nav_esplora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, EsploraActivity.class);
                startActivity(intent);
            }
        });

        nav_profilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setUpAllViewStuff() {
        button_logout = (ImageButton) findViewById(R.id.button_logout);

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogged.getInstance().logout();
                System.out.println(UserLogged.getInstance().isLogged);
                Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    protected void goToLogInActivity() {
        Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void loadUserGroups(){

        String server_url = MainActivity.serverGroup + "userGroups/" + UserLogged.getInstance().getUserId();

        final RequestQueue requestQueue = Volley.newRequestQueue(UserInfoActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Somethin went wrong in loadUserGroups");
                //textView.setText("Something went wrong");
                error.printStackTrace();
                requestQueue.stop();
            }
        });
        requestQueue.add(stringRequest);
    }

}
