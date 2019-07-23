package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import com.example.myapplication2.payloadsResponses.GroupFullDetail;

public class GroupInfoActivity extends AppCompatActivity {

    String groupId;
    String groupName;
    String groupDescription;
    String groupCreator;
    String requestMember;
    Boolean isMember;
    int numMember;


    TextView num_members;
    TextView info_nomeGruppo;
    TextView info_descrizione;
    TextView info_location;
    TextView info_tag1;
    TextView info_tag2;
    TextView info_tag3;
    TextView info_creator;

    Button btn_iscrizione;
    Button nav_esplora;
    Button nav_profilo;

      GroupFullDetail gFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);



        setUpNav();
        getIncomingIntent();
        try {
            isMember();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setUpViewComponent();
        System.out.println(toStringGroup());

    }

    public void getIncomingIntent(){
        Intent dataGIven;
        Log.d("GroupInfoActivity","getIncomingIntend: checking for incoming intents.");
        dataGIven = getIntent();
        if(dataGIven.hasExtra("groupId") &&  dataGIven.hasExtra("groupName") && dataGIven.hasExtra("groupDescription") && dataGIven.hasExtra("groupCreator")){

            System.out.println("ASDASDASDASD: "+ dataGIven.getStringExtra("groupDescription"));
            this.groupCreator = dataGIven.getStringExtra("groupCreator");
            this.groupDescription = dataGIven.getStringExtra("groupDescription");
            this.groupId = dataGIven.getStringExtra("groupId");
            this.groupName = dataGIven.getStringExtra("groupName");
            this.gFull = (GroupFullDetail) dataGIven.getSerializableExtra("groupFullDetail");
            System.out.println("COMEEEEEEEEEEEEEEEEEEEEEEEEEEEEE: " + gFull.getMembers().get(0).getUserName());
            numMember = gFull.getMembers().size();

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

    public void setUpViewComponent(){
        num_members = (TextView) findViewById(R.id.num_members);
        info_nomeGruppo = (TextView) findViewById(R.id.info_groupName);
        info_descrizione = (TextView) findViewById(R.id.info_description);
        info_location = (TextView) findViewById(R.id.info_location);
        info_tag1 = (TextView) findViewById(R.id.info_tag1);
        info_tag2 = (TextView) findViewById(R.id.info_tag2);
        info_tag3 = (TextView) findViewById(R.id.info_tag3);
        info_creator = (TextView) findViewById(R.id.info_creator);
        btn_iscrizione = (Button) findViewById(R.id.iscrizione);


        if(gFull.getTags().get(0) != null)
            info_tag1.setText(gFull.getTags().get(0).getName() + "  |");
        else
            info_tag1.setText("Nessun Tag");
        if(gFull.getTags().get(1) != null)
            info_tag2.setText(gFull.getTags().get(1).getName()+ "  |");
        else
            info_tag2.setText("");
        if(gFull.getTags().get(2) != null)
            info_tag3.setText(gFull.getTags().get(2).getName());
        else
            info_tag3.setText("");

        num_members.setText(Integer.toString(gFull.getMembers().size()));
        info_nomeGruppo.setText(this.groupName);
        info_descrizione.setText(this.groupDescription);
        info_location.setText(gFull.getLocation().getName());
        info_creator.setText(this.groupCreator);

        btn_iscrizione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMember){
                    // E' iscritto, ha premuto per disiscriversi
                    btn_iscrizione.setText("Iscriviti");
                    removeGroupMember();
                    num_members.setText(Integer.toString(--numMember));

                }else{
                    //Non è iscitto, ha premuto per iscriversi
                    btn_iscrizione.setText("Disiscriviti");
                    addGroupMember();
                    num_members.setText(Integer.toString(++numMember));
                }
                isMember = !isMember;
            }
        });

    }

    public void isMember() throws JSONException {

        /* ----MemberGroupPayload(userId,userName,groupId)
        private Long groupId;
        private Long userId;
        private String userName;
        */

        String server_url = MainActivity.serverGroup + "isMember2";

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("userId", UserLogged.getInstance().getUserId());
        jsonBody.put("userName", UserLogged.getInstance().getUserName());
        jsonBody.put("groupId", this.groupId);

        final String requestBody = jsonBody.toString();
        this.requestMember = jsonBody.toString();
        System.out.println("requestbody: " + requestBody);
        final RequestQueue requestQueue = Volley.newRequestQueue(GroupInfoActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("RESPONSE ISMEMBER:" + response);
                if(response.equals("false")) {
                    isMember = false;
                    btn_iscrizione.setText("Iscriviti");
                }else {
                    isMember = true;
                    btn_iscrizione.setText("Disiscriviti");
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


    public void addGroupMember(){

        String server_url = MainActivity.serverGroup + "addMember";

        final RequestQueue requestQueue = Volley.newRequestQueue(GroupInfoActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("RESPONSE ISMEMBER:" + response);
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
                    return requestMember == null ? null : requestMember.getBytes("utf-8");
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

    public void removeGroupMember(){


        String server_url = MainActivity.serverGroup + "removeMember";

        final RequestQueue requestQueue = Volley.newRequestQueue(GroupInfoActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("RESPONSE ISMEMBER:" + response);
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
                    return requestMember == null ? null : requestMember.getBytes("utf-8");
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
