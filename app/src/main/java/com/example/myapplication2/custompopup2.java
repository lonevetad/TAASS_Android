package com.example.myapplication2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class custompopup2 extends AppCompatActivity {


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

}
