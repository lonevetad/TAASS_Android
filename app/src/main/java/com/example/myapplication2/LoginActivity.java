package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    TextView tv_userName;
    TextView tv_password;
    TextView tv_errorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        btn_login = (Button) findViewById(R.id.login_button);
        tv_userName =(TextView)findViewById(R.id.username);
        tv_password = (TextView) findViewById(R.id.password);
        tv_errorLogin = (TextView) findViewById(R.id.tv_errorLogin);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_errorLogin.setVisibility(View.INVISIBLE);
                String userName = tv_userName.getText().toString().trim();
                String password = tv_password.getText().toString().trim();
                try {
                    if(userName.length() > 0 && password.length() > 0) {
                        checkUserPassword(userName, password);
                    }else{
                        tv_errorLogin.setText("Email o password errati");
                        tv_errorLogin.setVisibility(View.VISIBLE);

                    }
                } catch (JSONException e) {
                    System.out.println("Errore nella checkUserPassword");
                    e.printStackTrace();
                }
            }
        });

    }

    public void checkUserPassword(String username, String password) throws JSONException {

        String server_url = MainActivity.url + "User/login2";

        final Boolean userExist = false;
        final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", username);
        jsonBody.put("password", password);
        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("RESPONSE LOGIN:" + response);
                if(response.length() > 0 ){
                    try {
                        login(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    tv_errorLogin.setText("Email o password errati");
                    tv_errorLogin.setVisibility(View.VISIBLE);
                }
                //textView.setText(response);
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_errorLogin.setText("Errore Server");
                tv_errorLogin.setVisibility(View.VISIBLE);
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

    public void login(String response) throws JSONException {
        System.out.println("-------------------------------------------" + response);
        JSONObject jsonResponse = new JSONObject(response);
        System.out.println(jsonResponse);
        UserLogged.setIstanceData(Long.parseLong(jsonResponse.getString("userId")),jsonResponse.getString("userName"),jsonResponse.getString("userEmail"));
        Intent intent = new Intent(LoginActivity.this, EsploraActivity.class);
        startActivity(intent);

    }

}
