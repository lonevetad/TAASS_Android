package com.example.myapplication2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication2.entities.AppTag;
import com.example.myapplication2.payloadsResponses.GroupFullDetail;
import com.example.myapplication2.payloadsResponses.GroupSearchAdvPayload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class EsploraActivity extends AppCompatActivity {

    private Gson gsonB;
    private List<GroupFullDetail> allGroups;
    private GroupSearchAdvPayload filters;

    Dialog myDialog;
    EditText search_creator;
    EditText search_groupTitle;
    ImageButton adv_search;
    ImageButton find_group;
    Button nav_esplora;
    Button nav_profilo;

    private static final String TAG = "cuspompopup2";

    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<String> tags_image_url= new ArrayList<>();

    private List<AppTag> alltags;
    public static List<String> selectedTags = new ArrayList<>();

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateStart;
    private TextView dateEnd;
    private int year, month, day;
    private boolean isShowingStartDate;
    private String dateSearchStart = "";
    String dateSearchEnd = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esplora);





        myDialog = new Dialog(this);
        setUpViewComponent();
        setUpAllStuff();
        getAllGroup();
        dateStart = (TextView) findViewById(R.id.startDate);
        dateEnd = (TextView) findViewById(R.id.endDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDateStartValue(year, month + 1, day);
        showDateEndValue(year, month + 1, day);
        isShowingStartDate = true;
    }

    //Come mounted or create di vue
    public void setUpAllStuff() {

        getAllTags();
        GsonBuilder gsonBuilder;
        filters = new GroupSearchAdvPayload();
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
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


    public void showPopup(View v){
        System.out.println("Entra in show popup");
        TextView txtclose;
        myDialog.setContentView(R.layout.activity_custompopup2);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.show();


    }

    public void setUpViewComponent(){

        search_creator = (EditText) findViewById(R.id.search_creator);
        search_groupTitle = (EditText) findViewById(R.id.search_groupName);

        find_group = (ImageButton) findViewById(R.id.button_searchgroup);
        find_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    searchGroup();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

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

    public void searchGroup() throws ParseException {


        GroupSearchAdvPayload gs = new GroupSearchAdvPayload();
        System.out.println("DateEnd:  " +dateEnd.getText().toString());
        System.out.println("DateStart:  " +dateStart.getText().toString());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date endDate = null;
        java.sql.Date sqlDataEnd = null;

        java.util.Date startDate = null;
        java.sql.Date sqlDataStart = null;

        // Ricerca per Data
        if(dateEnd.getText().length() > 0 && !dateEnd.getText().toString().equals("24/7/2019")){
            endDate = sdf.parse(dateEnd.getText().toString());
            sqlDataEnd = new java.sql.Date(endDate.getTime());
            gs.setDateEndRange(sqlDataEnd);
        }

        if(dateStart.getText().length() > 0 && !dateStart.getText().toString().equals("24/7/2019")){
            startDate = sdf.parse(dateStart.getText().toString());
            sqlDataStart = new java.sql.Date(startDate.getTime());
            gs.setDateStartRange(sqlDataStart);
        }

        //Ricerca per creatore o titolo gruppo
        if(search_creator.getText().toString().length()> 0 ){
            String creator = search_creator.getText().toString().trim();
            gs.setCreatorMember(creator);
        }
        if( search_groupTitle.getText().toString().length()>0){
            String groupTitle = search_groupTitle.getText().toString().trim();
            gs.setGroupName(groupTitle);
        }
        if(selectedTags.size()>0){
            gs.setTags(selectedTags);
        }

        System.out.println("Prima search: "+ gs.toString());



        final String requestBody = gsonB.toJson(gs);
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

    public void setDateStart(View view) {
        isShowingStartDate = true;
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }


    @SuppressWarnings("deprecation")
    public void setDateEnd(View view) {
        isShowingStartDate = false;
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int year, int month, int day) {
                    if (isShowingStartDate)
                        showDateStartValue(year, month + 1, day);
                    else
                        showDateEndValue(year, month + 1, day);
                }
            };

    private void showDateStartValue(int year, int month, int day) {
        dateSearchStart = (new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year)).toString();
        if (dateStart != null)
            dateStart.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        else System.out.println("DateStart is null, values cannot be shown");
    }

    private void showDateEndValue(int year, int month, int day) {
        dateSearchEnd = (new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year)).toString();
        if (dateEnd != null)
            dateEnd.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        else System.out.println("DateEnd is null, values cannot be shown");
    }

    public void getAllTags(){
        Log.d(TAG,"getAllTags: ricevo tutti i tag");

        String server_url = MainActivity.serverGroup + "listTagRest";

        final RequestQueue requestQueue = Volley.newRequestQueue(EsploraActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type typeOfList = new TypeToken<ArrayList<AppTag>>(){}.getType();
                alltags = (List<AppTag>) gsonB.fromJson(response, typeOfList);
                System.out.println(response);
                System.out.println("------- Esplora Activity AllTag: " + alltags);
                //System.out.println("AppGroupName:" + allGroups.get(0).getGroupName());
                setRecyclerView();
                requestQueue.stop();

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

    public void setRecyclerView(){
        for(AppTag tag: alltags ){
            mName.add(tag.getName());
            tags_image_url.add("https://source.unsplash.com/random/200x200");
        }

        initRecyclerView();
    }

    public void initRecyclerView(){
        Log.d(TAG,"initRecyclerView: init recycler view");

        LinearLayoutManager layoutManager =  new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);

        RecyclerView recyclerView = findViewById(R.id.tagsRecyclerView);
        recyclerView.setLayoutManager(layoutManager);

        tagsRecyclerViewAdapter adapter = new tagsRecyclerViewAdapter(this,mName,tags_image_url);
        recyclerView.setAdapter(adapter);
    }

}
