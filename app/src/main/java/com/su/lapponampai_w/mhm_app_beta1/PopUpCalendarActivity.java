package com.su.lapponampai_w.mhm_app_beta1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

public class PopUpCalendarActivity extends AppCompatActivity {

    Button button;
    CalendarView calendarView;
    String stringDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_calendar);

        bindWidget();

        calendarView.setShowWeekNumber(false);

        displayMetrics();

        clickCalendarDay();

        clickButton();

    }

    private void clickButton() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopUpCalendarActivity.this, NoteActivity.class);
                intent.putExtra("PopupCalendarActivity", stringDate);
                setResult(Activity.RESULT_OK,intent);
                finish();

            }
        });
    }

    private void clickCalendarDay() {

        final MyData myData = new MyData();

        stringDate = myData.currentDay();
        Log.d("14July16V1", "stringDate : " + stringDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                stringDate = myData.createStringDay(dayOfMonth, month + 1, year);
                Log.d("14July16V1", "stringDate : " + stringDate);
            }
        });

    }

    private void bindWidget() {

        button = (Button) findViewById(R.id.buttonPopUpCalendar);
        calendarView = (CalendarView) findViewById(R.id.calendarView2);
    }

    private void displayMetrics() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width*.8),(int) (height*.5));
    }
} //Main Class
