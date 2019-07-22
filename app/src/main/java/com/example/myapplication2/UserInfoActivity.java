package com.example.myapplication2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {

    TextView username;
    TextView email;
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        username = (TextView) findViewById(R.id.info_username);
        email = (TextView) findViewById(R.id.info_email);
        description = (TextView) findViewById(R.id.info_description);

        UserLogged utente = UserLogged.getInstance();

        if(utente.isLogged){
            username.setText(utente.getUserName());
            email.setText(utente.getUserName()+ "@gmail.com");
            description.setText("Ludovica ha 18 anni, è bionda, ha gli occhi verdi, " +
                    "il colore della pelle molto chiaro ed è alta 1, 65 cm. E' una studentessa e frequenta da quattro anni" +
                    " il liceo classico di Siena. E' una ragazza molto allegra, espansiva e ha molti amici. Nel suo tempo libero " +
                    "ha due hobby in particolare: collezionare monete di tutti i Paesi del mondo e praticare lo sport dell'equitazione. ");
        }
    }

}
