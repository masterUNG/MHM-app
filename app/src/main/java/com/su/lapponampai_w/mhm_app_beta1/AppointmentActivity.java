package com.su.lapponampai_w.mhm_app_beta1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.su.lapponampai_w.mhm_app_beta1.R.id.buttonAppointmentSave;

public class AppointmentActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{

    //Explicit
    EditText doctoreditText, noteEditText;
    TextView dateTextView, timeTextView;
    CheckBox checkBox;
    LinearLayout linearLayout;
    Button cancelButton, saveButton;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        bindWidget();

        clickAppointmentDateTime();

        showView();

        showListView();

        clickSaveCancel();

        clickDeleteInListView();

    }



    private void showListView() {

        MyManage myManage = new MyManage(this);
        MyData myData = new MyData();

        //ShowListView ขึ้นมา
        final String[][] stringsAppointment = {myManage.readAllappointmentTABLE(0),
                myManage.readAllappointmentTABLE(2),
                myManage.readAllappointmentTABLE(3),myManage.readAllappointmentTABLE(4)
                ,myManage.readAllappointmentTABLE(5)};
        //0 id,1 Date,2 Time,3 Doctor,4 Note

        if (!stringsAppointment[0][0].equals("")) {

            //ArrayList<String> stringArrayList = new ArrayList<String>();

            //int intIndex = 0;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date_Specific = new Date();
            Date date_current = new Date();
            String sDate = myData.currentDay();

            for(int i = 0;i<stringsAppointment[0].length;i++) {

                try {
                    date_Specific = dateFormat.parse(stringsAppointment[1][i]);
                    date_current = dateFormat.parse(sDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String test_Specific = dateFormat.format(date_Specific);
                String test_current = dateFormat.format(date_current);
                Log.d("12July16V1", "test_Specific : " + test_Specific);
                Log.d("12July16V1", "test_current : " + test_current);

                if (date_Specific.compareTo(date_current) < 0) {
                    stringsAppointment[4][i] = "หมายเหตุ :".concat(stringsAppointment[4][i]).concat(" (เลยวันนัดที่กำหนดแล้ว!!!)");
                } else {
                    if (!stringsAppointment[4][i].equals("")) {
                        stringsAppointment[4][i] = "หมายเหตุ :".concat(stringsAppointment[4][i]);
                    }
                }

                stringsAppointment[1][i] = "วันที่นัด : ".concat(stringsAppointment[1][i]);
                Log.d("12July16V1", "วันที่นัด : " + stringsAppointment[1][i]);

                if (!stringsAppointment[2][i].equals("")) {
                    stringsAppointment[2][i] = "เวลาที่นัด : ".concat(stringsAppointment[2][i]);
                } else {
                    stringsAppointment[2][i] = "เวลาที่นัด : ไม่ได้ระบุ";
                }
                stringsAppointment[3][i] = "ชื่อแพทย์ผู้นัด : ".concat(stringsAppointment[3][i]);
            }


            MyAdaptorAppointment myAdaptorAppointment = new MyAdaptorAppointment(AppointmentActivity.this,
                    stringsAppointment[3], stringsAppointment[1], stringsAppointment[2], stringsAppointment[4]);
            listView.setAdapter(myAdaptorAppointment);


        }
    }

    private void clickDeleteInListView() {

        MyManage myManage = new MyManage(this);
        final String[][] stringsAppointment = {myManage.readAllappointmentTABLE(0),
                myManage.readAllappointmentTABLE(2),
                myManage.readAllappointmentTABLE(3),myManage.readAllappointmentTABLE(4)
                ,myManage.readAllappointmentTABLE(5)};


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(false);
                builder.setIcon(R.drawable.logo_carabao48);
                builder.setTitle("ลบข้อมูลวันนัด");
                builder.setMessage("ยืนยันการลบข้อมูลวันนัด");
                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String id = stringsAppointment[0][position];
                        Log.d("13JulyV1", "id : " + id);

                        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyHelper.DATABASE_NAME,
                                MODE_PRIVATE, null);
                        sqLiteDatabase.delete("appointmentTABLE", "_id = " + id, null);

                        Toast.makeText(AppointmentActivity.this,"Delete in appointmentTABLE",Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(AppointmentActivity.this,AppointmentActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();

            }
        });


    }

    private void clickSaveCancel() {


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringDoctor = doctoreditText.getText().toString().trim();
                String stringNote = noteEditText.getText().toString();
                String stringDate = dateTextView.getText().toString();
                String stringTime = timeTextView.getText().toString();
                Log.d("AppointmentActivity", "stringTime : " + stringTime);
                String sSkip;
                stringNote = noteEditText.getText().toString().trim();
                Log.d("AppointmentActivity", stringDoctor);

                if (checkBox.isChecked()) {
                    sSkip = "Y";
                } else {
                    sSkip = "N";
                }

                if (sSkip.equals("N") && stringTime.equals("")) {
                    Toast.makeText(getBaseContext(), "โปรดกรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                } else {
                    if (stringDoctor.equals("") || stringDate.equals("")) {
                        Toast.makeText(getBaseContext(), "โปรดกรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "success!!", Toast.LENGTH_SHORT).show();
                        //ทำไปต่อจากตรงนี้
                        MyManage myManage = new MyManage(AppointmentActivity.this);
                        MyData myData = new MyData();
                        String strCurrentDateTime = myData.currentDateTime();
                        myManage.addValueToAppointmentTABLE(strCurrentDateTime, stringDate,
                                stringTime, stringDoctor, stringNote);

                        Intent intent = new Intent(AppointmentActivity.this,AppointmentActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });


    } //clickSaveCancel

    private void showView() {

        dateTextView.setText("");
        timeTextView.setText("");

       checkBox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (checkBox.isChecked()) {
                   linearLayout.setVisibility(View.INVISIBLE);
                   timeTextView.setText("");
               } else {
                   linearLayout.setVisibility(View.VISIBLE);
               }
           }
       });

        final MyManage myManage = new MyManage(this);
        MyData myData = new MyData();







    } //showView

    private void clickAppointmentDateTime() {

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });



    } //clickAppointmentDate

    private void bindWidget() {

        doctoreditText = (EditText) findViewById(R.id.editText4);
        noteEditText = (EditText) findViewById(R.id.editText5);
        dateTextView = (TextView) findViewById(R.id.textView108); //ปุ่ม Date
        timeTextView = (TextView) findViewById(R.id.textView110); //ปุ่ม Time
        checkBox = (CheckBox) findViewById(R.id.checkBox); //checkBox ว่าต้องมีเวลาหรือไม่
        linearLayout = (LinearLayout) findViewById(R.id.linAppointmentTime);
        saveButton = (Button) findViewById(R.id.buttonAppointmentSave);
        cancelButton = (Button) findViewById(R.id.buttonAppointmentCancel);
        listView = (ListView) findViewById(R.id.listViewAppointment);



    } //bindWidget

    public void showDatePickerDialog(View v) {
        MyDatePickerFragment myDatePickerFragment = new MyDatePickerFragment();
        myDatePickerFragment.show(getFragmentManager(), "datePicker");

    } //showDatePickerDialog

    public void showTimePickerDialog(View v) {

        MyTimePickerFragment myTimePickerFragment = new MyTimePickerFragment();
        myTimePickerFragment.show(getFragmentManager(), "timePicker");

    } //showTimePickerDialog

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        MyData myData = new MyData();
        String sSpecificDate = myData.createStringDay(dayOfMonth, monthOfYear + 1, year);
        String sDate = myData.currentDay();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date_Specific = new Date();
        Date date_current = new Date();

        try {
            date_Specific = dateFormat.parse(sSpecificDate);
            date_current = dateFormat.parse(sDate);

            } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date_Specific.compareTo(date_current) < 0) {
            Toast.makeText(getBaseContext(), "ไม่สามารถตั้งวันนัดน้อยกว่าวันที่ปัจจุบันได้", Toast.LENGTH_SHORT).show();
            dateTextView.setText("");
        } else {
            dateTextView.setText(sSpecificDate);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        MyData myData = new MyData();
        String sTime = myData.createStringTime(hourOfDay, minute);
        timeTextView.setText(sTime);

    }
} //Main Class
