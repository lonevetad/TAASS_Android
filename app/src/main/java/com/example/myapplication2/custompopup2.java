package com.example.myapplication2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class custompopup2 extends AppCompatActivity {


    private Gson gsonB;
    private static final String TAG = "cuspompopup2";

    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<String> tags_image_url= new ArrayList<>();

    private List<AppTag> alltags;

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateStart;
    private TextView dateEnd;
    private int year, month, day;
    private boolean isShowingStartDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custompopup2);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        setUpAllStuff();
        dateStart = (TextView) findViewById(R.id.startDate);
        dateEnd = (TextView) findViewById(R.id.endDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDateStartValue(year, month + 1, day);
        showDateEndValue(year, month + 1, day);
        isShowingStartDate = true;

        dateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateStart(v);
            }
        });

        dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
            }
        });
    }

    public void setUpAllStuff(){

        getAllTags();

        GsonBuilder gsonBuilder;
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy/MM/dd");
        this.gsonB = gsonBuilder.create();
    }


    //ricevo tutti i tag;


    @SuppressWarnings("deprecation")
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
        if (dateStart != null)
            dateStart.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        else System.out.println("DateStart is null, values cannot be shown");
    }

    private void showDateEndValue(int year, int month, int day) {
        if (dateEnd != null)
            dateEnd.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        else System.out.println("DateEnd is null, values cannot be shown");
    }

    public void getAllTags(){
        Log.d(TAG,"getAllTags: ricevo tutti i tag");

        String server_url = MainActivity.serverGroup + "listTagRest";

        final RequestQueue requestQueue = Volley.newRequestQueue(custompopup2.this);

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
