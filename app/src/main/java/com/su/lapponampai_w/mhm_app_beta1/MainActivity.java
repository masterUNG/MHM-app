package com.su.lapponampai_w.mhm_app_beta1;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //Explicit
    public static Activity activityMainActivity;

    //Widget ต่างๆ
    ImageButton imageButtonPop1, imageButtonPop2, imageButtonPop3, imageButtonPop4, imageButtonPop5, imageButtonPop6;
    ImageButton imageAdherence;
    ImageButton imageButtonM1, imageButtonM2, imageButtonM3, imageButtonM4,
            imageButtonM5, imageButtonM6, imageButtonM7, imageButtonM8, imageButtonM9,
            imageButtonA1, imageButtonA2, imageButtonA3, imageButtonA4, imageButtonA5,
            imageButtonA6, imageButtonA7, imageButtonA8, imageButtonA9, imageButtonE1,
            imageButtonE2, imageButtonE3, imageButtonE4, imageButtonE5, imageButtonE6,
            imageButtonE7, imageButtonE8, imageButtonE9, imageButtonB1, imageButtonB2,
            imageButtonB3, imageButtonB4, imageButtonB5, imageButtonB6, imageButtonB7,
            imageButtonB8, imageButtonB9;
    TextView textViewAdd, textViewMainDate, textViewMedication, textViewNews;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private RelativeLayout relativeLayout;
    private TextView tvMorning, tvAfternoon, tvEvening, tvBedtime;
    String today;
    String[] stringsInterval, stringsStartTime, stringsEndTime, stringstimeTABLE;


    String[] stringsClick_Position, stringsClick_Main_id, stringsClick_TimeRef,stringsClick_Sum_id,
            stringsClick_Appearance, stringsClick_SkipHold,stringsClick_DateTimeCheck,stringsClick_DateRef; //clickTakeMedicine
    String[] stringsMainTABLE_TradeName, stringsMainTABLE_AmountTablet,
            stringsMainTABLE_Main_id,stringsMainTABLE_EA; //clickTakeMedicine
    String strResult_Position, strResult_Main_id, strResult_TimeRef, strResult_Appearance,
            strResult_AmountTablet, strResult_Tradename,strResult_DateTimeCheck,strResult_Sum_id,
            strResult_EA,strResult_SkipHold,strResult_DateRef = ""; //clickTakeMedicine

    String popUpMaster;

    String[] strTextSpinner;

    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainActivity = this;

        hideUnuse();

        //เปิดโดยใช้ Password(Intent)
        receiveIntentAndGoToAnotherActivity();


        //Bind Widget
        bindWidget();
        setHeading();
        showView();
        //Notification from SQLite
        //notificationFormSQLite();
        setDateAndTimeToday();

        delete_UnnecessaryData_sumTABLE();

        displayMedicineByDay(today); //แสดงเม็ดยาบนหน้าจอ
        checkDisplay_ERR();

        //คลิก เพิ่มเติม
        clickAddbtn();

        //คลิก รายการยา
        clickMedicationList();



        click_News();

        clickImagepill();

        clickTextViewMainDate();

        //คลิก ImageButtonAdherence
        //click_ImageButtonAdherence();



    } //Main method

    private void receiveIntentAndGoToAnotherActivity() {

        popUpMaster = getIntent().getStringExtra("PopUpMaster");

        if (popUpMaster != null) {
            Log.d("24/10/2559", popUpMaster);
            if (popUpMaster.equals("btn_pop1")) {
                startActivity(new Intent(MainActivity.this, AddMedicineActivity.class));
            } else if (popUpMaster.equals("btn_pop2")) {
                startActivity(new Intent(MainActivity.this, AppointmentActivity.class));
            } else if (popUpMaster.equals("btn_pop3")) {
                startActivity(new Intent(MainActivity.this, LabActivity.class));
            } else if (popUpMaster.equals("btn_pop4")) {
                startActivity(new Intent(MainActivity.this, NoteActivity.class));
            } else if (popUpMaster.equals("MedicationList")) {
                startActivity(new Intent(MainActivity.this, MedicationListActivity.class));
            } else if (popUpMaster.equals("News")) {
                startActivity(new Intent(MainActivity.this, NewsActivity.class));
            } else if (popUpMaster.equals("Setting")) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            } else if (popUpMaster.equals("AlarmReceiver")) {

                strResult_Sum_id = getIntent().getStringExtra("SumId_AlarmReceiver");
                Log.d("25/10/2559", "strResult_Sum_id" + strResult_Sum_id);
                Toast.makeText(getBaseContext(), "strResult_Sum_id = " + strResult_Sum_id, Toast.LENGTH_SHORT).show();
                MyManage myManage = new MyManage(this);
                String[] stringsStay = myManage.readSQLite_userTABLE(3);
                if (stringsStay[0].equals("1") || stringsStay[0].equals("2")) {
                    Intent intent = new Intent(MainActivity.this, PopUpGate.class);
                    intent.putExtra("NotificationGate", "NotificationGate");
                    intent.putExtra("NotificationGate_SumId", strResult_Sum_id);
                    startActivity(intent);
                } else {
                    receiveValueToPopUpTakeMedicine();
                }
            } else if (popUpMaster.equals("NotificationGate")) {
                strResult_Sum_id = getIntent().getStringExtra("SumId_AlarmReceiver");
                receiveValueToPopUpTakeMedicine();
            }
        } else {
            Toast.makeText(getBaseContext(), "PopUpMaster == null",Toast.LENGTH_LONG);
        }

    }

    private void receiveValueToPopUpTakeMedicine() {

        MyManage myManage = new MyManage(this);
        MyData myData = new MyData();
        String sCurrentDay = myData.currentDay();
        stringsClick_Sum_id = myManage.filter_sumTABLE__by_Date(sCurrentDay, 0);
        stringsClick_DateRef = myManage.filter_sumTABLE__by_Date(sCurrentDay, 2); //DateRef
        stringsClick_Main_id = myManage.filter_sumTABLE__by_Date(sCurrentDay, 1); //Main_id
        stringsClick_TimeRef = myManage.filter_sumTABLE__by_Date(sCurrentDay, 3); //TimeRef
        stringsClick_SkipHold = myManage.filter_sumTABLE__by_Date(sCurrentDay, 6); //SkipHold

        String[] strings_DateCheck = myManage.filter_sumTABLE__by_Date(sCurrentDay, 4); //DateCheck
        String[] strings_TimeCheck = myManage.filter_sumTABLE__by_Date(sCurrentDay, 5); //TimeCheck

        stringsClick_DateTimeCheck = new String[stringsClick_Main_id.length];
        if (!stringsClick_Main_id[0].equals("")) {
            for (int x = 0; x < stringsClick_Main_id.length; x++) {
                if (!strings_DateCheck[x].equals("")) {
                    stringsClick_DateTimeCheck[x] = strings_DateCheck[x] + " " + strings_TimeCheck[x];
                } else {
                    stringsClick_DateTimeCheck[x] = "";
                }
            }

        }


        stringsMainTABLE_Main_id = myManage.readAllMainTABLE(0);
        stringsMainTABLE_TradeName = myManage.readAllMainTABLE(3);
        stringsMainTABLE_AmountTablet = myManage.readAllMainTABLE(6);
        stringsMainTABLE_EA = myManage.readAllMainTABLE(7);
        String[] stringsMainTABLE_Appearance = myManage.readAllMainTABLE(5);


        for (int i = 0; i < stringsClick_Sum_id.length; i++) {
            if (stringsClick_Sum_id[i].equals(strResult_Sum_id)) {
                //ค่าที่จะเอาไปใช้ใน Pop up จริงๆ
                strResult_Main_id = stringsClick_Main_id[i];  //ต้องเอา Main_id ไปทำต่อ
                strResult_DateRef = stringsClick_DateRef[i];
                strResult_TimeRef = stringsClick_TimeRef[i];
                //strResult_Appearance = stringsClick_Appearance[i];
                strResult_DateTimeCheck = stringsClick_DateTimeCheck[i];
                strResult_SkipHold = stringsClick_SkipHold[i];

            }
        }

        for (int i = 0; i < stringsMainTABLE_Main_id.length; i++) {
            if (stringsMainTABLE_Main_id[i].equals(strResult_Main_id)) {
                strResult_Tradename = stringsMainTABLE_TradeName[i];  //Tradename
                strResult_AmountTablet = stringsMainTABLE_AmountTablet[i]; //จำนวนเม็ดที่กิน
                strResult_EA = stringsMainTABLE_EA[i]; //EA
                strResult_Appearance = stringsMainTABLE_Appearance[i]; //Appearance
            }

        }


        checkAndIntentToTakeSkipMedicineActivity();

    }

    private void checkDisplay_ERR() {

        //03/10/2559 ทำ AlertDialog
        MyManage myManage = new MyManage(this);
        String str_Check_ERR = myManage.filterdisplayTABLE_Position_ERR();
        if (str_Check_ERR.equals("True")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.icon_question);
            builder.setTitle("กล่องยาเต็ม!!!");
            builder.setMessage("กล่องยาไม่สามารถแสดงจำนวนยาเกิด 9 รายการต่อ 1 มื้อได้ ท่านอาจเห็นยาที่ต้องรับประทานได้ไม่ครบทุกตัว");
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }

    } //checkDisplay_ERR

    private void hideUnuse() {

        Button button = (Button) findViewById(R.id.buttonForDoctor);
        button.setVisibility(View.GONE);

    } //hideUnuse ไว้ทำที่หลังหรือส่วนที่ไม่ใช้ ต้องลบทิ้งหลังเสร็จ

    private void setHeading() {


        MyHeadingDetail myHeadingDetail = new MyHeadingDetail(MainActivity.this);
        myHeadingDetail.spinnersetup(MainActivity.this,imageAdherence,spinner);

        /*

        final MyManage myManage = new MyManage(this);
        String[] sName = myManage.readAlluserTABLE(1);

        strTextSpinner = new String[9];
        strTextSpinner[0] = "ไอดีผู้ใช้ : \n\n                 " + sName[0] + "\n=+=+=+=+=+=+=+=+";
        strTextSpinner[1] = "เพิ่มรายการยา";
        strTextSpinner[2] = "เพิ่มวันนัด";
        strTextSpinner[3] = "เพิ่มค่าแล็ป";
        strTextSpinner[4] = "เพิ่มบันทึกประจำวัน";
        strTextSpinner[5] = "ปฏิทิน";
        strTextSpinner[6] = "ตั้งค่าการใช้งาน";
        strTextSpinner[7] = "เกี่ยวกับ\nMHM Application";
        strTextSpinner[8] = "ออกจากระบบ";


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner_item, strTextSpinner);
        spinner.setAdapter(stringArrayAdapter);

        imageAdherence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdherenceActivity.class);
                startActivity(intent);



            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (strTextSpinner[position].equals("ออกจากระบบ")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setCancelable(false);
                    builder.setIcon(R.drawable.logo_carabao48);
                    builder.setTitle("Log Out");
                    builder.setMessage("ยืนยันการ Log Out");
                    builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myManage.canceledStayLogin();
                            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } //OnClick
                    });
                    builder.show();


                }



                ((TextView)view).setText(null); //สำคัญมากได้แล้ว
                view.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //strDesk = strDeskSpinner[0];
            }
        });

        */

    } //setHeading


    //ตอนนี้ยังไม่ใช้ ให้ไปที่หน้าหมอ
    public void clickDoctor(View view) {
        startActivity(new Intent(MainActivity.this,ForDoctorActivity.class));
    }

    public void clickUpdateValuetoServer(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.logo_carabao48);
        builder.setTitle("Update All data to Server");
        builder.setMessage("คุณต้องการ Synchronize ขึ้น Server ของเราหรือไม่");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //updateUserTABLE();  // Update userTABLE SQLite to Server

                updatemainTABLE(updateUserTABLE());

            } //OnClick
        });
        builder.show();


    } //clickUpdate

    private void updatemainTABLE(String strEmail) {

        String strURL = "http://www.swiftcodingthai.com/mhm/add_mainTABLE.php";


        //OkHttpClient okHttpClient = new OkHttpClient();
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        final Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mainTABLE", null);
        int iCount = cursor.getCount();
        final int intTime = 0;
        if (iCount > 0) {
            cursor.moveToFirst();
            for(int i = 0;i<cursor.getCount();i++) {
                String str1 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_id));
                String str2 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_Med_id));
                String str3 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_trade_name));
                String str4 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_generic_line));
                String str21 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_amount_tablet));
                String str5 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_which_date_d));
                String str6 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_appearance));
                String str7 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_ea));
                String str8 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_Main_pharmaco));
                String str9 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_startdate));
                String str10 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_finishdate));
                String str11 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_prn));
                String str12 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_t1));
                String str13 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_t2));
                String str14 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_t3));
                String str15 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_t4));
                String str16 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_t5));
                String str17 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_t6));
                String str18 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_t7));
                String str19 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_t8));
                String str20 = cursor.getString(cursor.getColumnIndex(MyManage.mcolumn_datetimecanceled));

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("isAdd", "true")
                        .add("EmailUser", strEmail)
                        .add(MyManage.mcolumn_Med_id, str2)
                        .add(MyManage.mcolumn_trade_name, str3)
                        .add(MyManage.mcolumn_generic_line, str4)
                        .add(MyManage.mcolumn_amount_tablet,str21)
                        .add(MyManage.mcolumn_which_date_d, str5)
                        .add(MyManage.mcolumn_appearance, str6)
                        .add(MyManage.mcolumn_ea, str7)
                        .add(MyManage.mcolumn_Main_pharmaco, str8)
                        .add(MyManage.mcolumn_startdate, str9)
                        .add(MyManage.mcolumn_finishdate, str10)
                        .add(MyManage.mcolumn_prn, str11)
                        .add(MyManage.mcolumn_t1, str12)
                        .add(MyManage.mcolumn_t2, str13)
                        .add(MyManage.mcolumn_t3, str14)
                        .add(MyManage.mcolumn_t4, str15)
                        .add(MyManage.mcolumn_t5, str16)
                        .add(MyManage.mcolumn_t6, str17)
                        .add(MyManage.mcolumn_t7, str18)
                        .add(MyManage.mcolumn_t8, str19)
                        .add(MyManage.mcolumn_datetimecanceled, str20)
                        .build();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strURL).post(requestBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {

                    }
                });

                cursor.moveToNext();
            } //for

            cursor.close();
        }


    } //updateMain

    private String updateUserTABLE() {

        //String strURL = "http://www.swiftcodingthai.com/mhm/add_user.php";


        //OkHttpClient okHttpClient = new OkHttpClient();

        //Read SQLite userTABLE
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE", null);

        cursor.moveToFirst();
        String strUser = cursor.getString(cursor.getColumnIndex(MyManage.ucolumn_User));
        String strPassword = cursor.getString(cursor.getColumnIndex(MyManage.ucolumn_Password));
        String strStay = cursor.getString(cursor.getColumnIndex(MyManage.ucolumn_Stay));
        String strEmail = cursor.getString(cursor.getColumnIndex(MyManage.ucolumn_hn));

        /*
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("User", strUser)
                .add("Password", strPassword)
                .add("Stay", strStay)
                .add("Email", strEmail)
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(strURL).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() { //ถ้าไม่สามารถโยนขึ้นได้ จะทำงานที่ onFailure
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
        */

        return strEmail;

    } //UpdateUserTABLE

    private void clickTextViewMainDate() {
        textViewMainDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }

            private void showDatePickerDialog(View v) {
                MyDatePickerFragment myDatePickerFragment = new MyDatePickerFragment();
                myDatePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });

    }

    private void showView() {
        //Invisible
        imageButtonA1.setVisibility(View.INVISIBLE);
        imageButtonA2.setVisibility(View.INVISIBLE);
        imageButtonA3.setVisibility(View.INVISIBLE);
        imageButtonA4.setVisibility(View.INVISIBLE);
        imageButtonA5.setVisibility(View.INVISIBLE);
        imageButtonA6.setVisibility(View.INVISIBLE);
        imageButtonA7.setVisibility(View.INVISIBLE);
        imageButtonA8.setVisibility(View.INVISIBLE);
        imageButtonA9.setVisibility(View.INVISIBLE);
        imageButtonB1.setVisibility(View.INVISIBLE);
        imageButtonB2.setVisibility(View.INVISIBLE);
        imageButtonB3.setVisibility(View.INVISIBLE);
        imageButtonB4.setVisibility(View.INVISIBLE);
        imageButtonB5.setVisibility(View.INVISIBLE);
        imageButtonB6.setVisibility(View.INVISIBLE);
        imageButtonB7.setVisibility(View.INVISIBLE);
        imageButtonB8.setVisibility(View.INVISIBLE);
        imageButtonB9.setVisibility(View.INVISIBLE);
        imageButtonM1.setVisibility(View.INVISIBLE);
        imageButtonM2.setVisibility(View.INVISIBLE);
        imageButtonM3.setVisibility(View.INVISIBLE);
        imageButtonM4.setVisibility(View.INVISIBLE);
        imageButtonM5.setVisibility(View.INVISIBLE);
        imageButtonM6.setVisibility(View.INVISIBLE);
        imageButtonM7.setVisibility(View.INVISIBLE);
        imageButtonM8.setVisibility(View.INVISIBLE);
        imageButtonM9.setVisibility(View.INVISIBLE);
        imageButtonE1.setVisibility(View.INVISIBLE);
        imageButtonE2.setVisibility(View.INVISIBLE);
        imageButtonE3.setVisibility(View.INVISIBLE);
        imageButtonE4.setVisibility(View.INVISIBLE);
        imageButtonE5.setVisibility(View.INVISIBLE);
        imageButtonE6.setVisibility(View.INVISIBLE);
        imageButtonE7.setVisibility(View.INVISIBLE);
        imageButtonE8.setVisibility(View.INVISIBLE);
        imageButtonE9.setVisibility(View.INVISIBLE);
    } //showView

    @Override
    public void onResume(){
        super.onResume();

        if (!strResult_DateRef.equals("")) {
            showView();
            delete_UnnecessaryData_sumTABLE();
            textViewMainDate.setText("วันที่ : " + strResult_DateRef);
            displayMedicineByDay(strResult_DateRef);
            //checkDisplay_ERR();
            clickAddbtn();
            clickMedicationList();
            //click_ImageButtonAdherence();
            click_News();
            clickImagepill();
            clickTextViewMainDate();
        } else {
            showView();
            delete_UnnecessaryData_sumTABLE();
            setDateAndTimeToday();
            textViewMainDate.setText("วันที่ : " + today);
            displayMedicineByDay(today);
            //checkDisplay_ERR();
            clickAddbtn();
            clickMedicationList();
            //click_ImageButtonAdherence();
            click_News();
            clickImagepill();
            clickTextViewMainDate();
            setHeading();
        }


    } //Override

    private void delete_UnnecessaryData_sumTABLE() {

        MyManage myManage = new MyManage(this);

        //เปิดmainTABLE จะเอาเฉพาะค่าที่ถูก cancel แล้ว
        String[] strMain_id_in_mainTABLE = myManage.read_mainTABLE_InCluded_DateTimeCanceled(0);
        String[] strDateTimeCanceled = myManage.read_mainTABLE_InCluded_DateTimeCanceled(20);
        //11/10/59 เพิ่ม Prn
        String[] strPRN = myManage.read_mainTABLE_InCluded_DateTimeCanceled(11);
        String[] strMain_id_in_sumTABlE_Today = myManage.filter_sumTABLE__by_Date(today, 1);
        String[] str_id_in_sumTABLE_Today = myManage.filter_sumTABLE__by_Date(today, 0);
        String[] str_DateCheck_in_sumTABLE_Today = myManage.filter_sumTABLE__by_Date(today, 4);
        ArrayList<String> stringArrayList = new ArrayList<String>();
        int arrayIndex = 0;
        if (!strMain_id_in_mainTABLE.equals("") && !strMain_id_in_sumTABlE_Today.equals("")) {
            for(int i = 0;i<strMain_id_in_mainTABLE.length;i++) {
                //11/10/59 เพิ่ม Prn
                if (!strDateTimeCanceled[i].equals("") || strPRN[i].equals("Y")) {
                    stringArrayList.add(arrayIndex, strMain_id_in_mainTABLE[i]);
                    arrayIndex = arrayIndex + 1;
                }
            }
              //เช็คว่ามียาที่ถูก cancel หรือป่าว
            if (stringArrayList.size() > 0) {
                String[] strings = new String[stringArrayList.size()];
                strings = stringArrayList.toArray(strings);

                arrayIndex = 0;
                for (int w = 0;w<strMain_id_in_sumTABlE_Today.length;w++) {
                    for(int x = 0;x<strings.length;x++) {
                        if (strMain_id_in_sumTABlE_Today[w].equals(strings[x])) {
                            if (str_DateCheck_in_sumTABLE_Today[w].equals("")) {
                                SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyHelper.DATABASE_NAME, MODE_PRIVATE, null);
                                sqLiteDatabase.delete("sumTABLE", "_id = " + str_id_in_sumTABLE_Today[w], null);
                            }
                        }
                    }
                }
            }
        }





        /*
        // ถ้ามีให้ลบทิ้งไป
        MyManage myManage = new MyManage(this);
        //เปิดmainTABLE เฉพาะค่าที่ ACtive ขึ้นมา
        String[] strMain_id_in_mainTABLE = myManage.readAllMainTABLE_Full(0);
        //เปิด sumTABLE ของวันนี้ขึ้นมา (เอาเฉพาะวันนี้ก็ได้)  filter_sumTABLE__by_Date
        String[] strMain_id_in_sumTABlE_Today = myManage.filter_sumTABLE__by_Date(today, 1);
        String[] str_id_in_sumTABLE_Today = myManage.filter_sumTABLE__by_Date(today, 0);
        //เอาค่ามาเทียบกันว่า มีค่าใน sumTABLE (main_id) ที่ไม่มีใน mainTABLE หรือไม่

        if (!strMain_id_in_sumTABlE_Today.equals("")) {
            int[][] iCheckMain_id_in_sumTABLE_Today =
                    new int[strMain_id_in_sumTABlE_Today.length][strMain_id_in_mainTABLE.length];


            for(int i = 0;i<strMain_id_in_sumTABlE_Today.length;i++) {
                for (int x = 0;x<strMain_id_in_mainTABLE.length;x++) {
                    if (strMain_id_in_sumTABlE_Today[i].equals(strMain_id_in_mainTABLE[x])) {
                        iCheckMain_id_in_sumTABLE_Today[i][x] = 1;
                    } else {
                        iCheckMain_id_in_sumTABLE_Today[i][x] = 0;
                    }

                }
            }

            ArrayList<Integer> arrayList = new ArrayList<Integer>();

            for(int w = 0;w<iCheckMain_id_in_sumTABLE_Today[0].length;w++) {
                int arrayListIndex = 0;
                for(int z = 0;z<iCheckMain_id_in_sumTABLE_Today.length;z++) {
                    Log.d("Main_Delete", "iCheckMain_id_in_sumTABLE_Today[z][w] : " +Integer.toString(z)+Integer.toString(w) + " : " + Integer.toString(iCheckMain_id_in_sumTABLE_Today[z][w]));
                    arrayList.add(arrayListIndex,iCheckMain_id_in_sumTABLE_Today[z][w]); //ค่า main_id ในตำแหน่ง w มี 0 หรือ 1
                    arrayListIndex = arrayListIndex + 1;
                }
                Integer[] ii = new Integer[arrayList.size()];
                ii = arrayList.toArray(ii);

                Arrays.sort(ii);
                int i = Arrays.binarySearch(ii, 1);
                if (i < 0) {
                    //ทำการลบค่าที่ตำแหน่งดังกล่าว
                    //strMain_id_in_sumTABlE_Today[w] ลบค่านี้ ใช้ search ด้วย
                    // main_id ของ วันนี้(Date_Ref) และยังมี DateCheck เป็นค่าว่าง
                    Log.d("Main_Delete", "strMain_id_in_sumTABLE_Today[w] ที่จะลบ  : " + strMain_id_in_sumTABlE_Today[w]);
                    SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyHelper.DATABASE_NAME, MODE_PRIVATE, null);
                    sqLiteDatabase.delete("sumTABLE", "Main_id = " + str_id_in_sumTABLE_Today[w], null);
                }
                arrayList.clear();
            }


        }

        */


    }



    private void clickImagepill() {

        final MyManage myManage = new MyManage(this);
        //เอาค่าจากใน displayTABLE
        stringsClick_Position = myManage.readAlldisplayTABLE(1);  //ตำแหน่ง
        stringsClick_Sum_id = myManage.readAlldisplayTABLE(2); //Sum_id
        stringsClick_DateRef = myManage.readAlldisplayTABLE(4); //Day (DateRef)
        stringsClick_Main_id = myManage.readAlldisplayTABLE(3);  //Main_id
        stringsClick_TimeRef = myManage.readAlldisplayTABLE(5);  //TimeRef
        stringsClick_DateTimeCheck = myManage.readAlldisplayTABLE(6); //DateTimeCheck
        stringsClick_Appearance = myManage.readAlldisplayTABLE(7);  //Appearance
        stringsClick_SkipHold = myManage.readAlldisplayTABLE(8);  //SkipHold
        //เอาค่าบางค่าจาก mainTABLE มาใช้ด้วย
        stringsMainTABLE_Main_id = myManage.readAllMainTABLE(0);
        stringsMainTABLE_TradeName = myManage.readAllMainTABLE(3);
        stringsMainTABLE_AmountTablet = myManage.readAllMainTABLE(6);
        stringsMainTABLE_EA = myManage.readAllMainTABLE(7);

        //เริ่ม Click!!!
        imageButtonM1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("M1");}});
        imageButtonM2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("M2");}});
        imageButtonM3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("M3");}});
        imageButtonM4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("M4");}});
        imageButtonM5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("M5");}});
        imageButtonM6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("M6");}});
        imageButtonM7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("M7");}});
        imageButtonM8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("M8");}});
        imageButtonM9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("M9");}});

        imageButtonA1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("A1");}});
        imageButtonA2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("A2");}});
        imageButtonA3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("A3");}});
        imageButtonA4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("A4");}});
        imageButtonA5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("A5");}});
        imageButtonA6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("A6");}});
        imageButtonA7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("A7");}});
        imageButtonA8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("A8");}});
        imageButtonA9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("A9");}});

        imageButtonE1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("E1");}});
        imageButtonE2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("E2");}});
        imageButtonE3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("E3");}});
        imageButtonE4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("E4");}});
        imageButtonE5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("E5");}});
        imageButtonE6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("E6");}});
        imageButtonE7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("E7");}});
        imageButtonE8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("E8");}});
        imageButtonE9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("E9");}});

        imageButtonB1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("B1");}});
        imageButtonB2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("B2");}});
        imageButtonB3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("B3");}});
        imageButtonB4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("B4");}});
        imageButtonB5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("B5");}});
        imageButtonB6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("B6");}});
        imageButtonB7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("B7");}});
        imageButtonB8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("B8");}});
        imageButtonB9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {clickTakeMedicine("B9");}});

    } //clickImagepill

    //คลิก!!! จะทานยา
    private void clickTakeMedicine(String maeb) {
        //Toast.makeText(getBaseContext(), "เริ่ม " + maeb, Toast.LENGTH_SHORT).show();

        for (int i = 0; i < stringsClick_Position.length; i++) {
            if (stringsClick_Position[i].equals(maeb)) {
                //ค่าที่จะเอาไปใช้ใน Pop up จริงๆ
                strResult_Position = stringsClick_Position[i];
                strResult_Main_id = stringsClick_Main_id[i];  //ต้องเอา Main_id ไปทำต่อ
                strResult_Sum_id = stringsClick_Sum_id[i];
                strResult_DateRef = stringsClick_DateRef[i];
                strResult_TimeRef = stringsClick_TimeRef[i];
                strResult_Appearance = stringsClick_Appearance[i];
                strResult_DateTimeCheck = stringsClick_DateTimeCheck[i];
                strResult_SkipHold = stringsClick_SkipHold[i];
            }
        }

        for (int i = 0; i < stringsMainTABLE_Main_id.length; i++) {
            if (stringsMainTABLE_Main_id[i].equals(strResult_Main_id)) {
                strResult_Tradename = stringsMainTABLE_TradeName[i];  //Tradename
                strResult_AmountTablet = stringsMainTABLE_AmountTablet[i]; //จำนวนเม็ดที่กิน
                strResult_EA = stringsMainTABLE_EA[i]; //EA
            }

        }

        Log.d("clickTakeMedicine", strResult_Position + " " + strResult_Main_id +
                " " + strResult_TimeRef + " " + strResult_Appearance + " " +
                strResult_Tradename + " " + strResult_AmountTablet);

        //เริ่มทำการดูเวลา ถ้ามีการทานไปแล้ว หรือ skip ไปแล้วเกินเวลาที่กำหนดให้ Toast ขึ้นมาแล้วทำการ return

        checkAndIntentToTakeSkipMedicineActivity();

        /*
        if (!strResult_DateTimeCheck.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();
            try {
                date = dateFormat.parse(strResult_DateTimeCheck);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, -3);
            Date dateRef = calendar.getTime();

            if (date.compareTo(dateRef) < 0) {
                Toast.makeText(MainActivity.this, "ไม่สามารถแก้ไข้เกินเวลาที่กำหนดได้", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (!strResult_SkipHold.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            try {
                date = dateFormat.parse(strResult_SkipHold);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, -3);
            Date dateRef = calendar.getTime();

            if (date.compareTo(dateRef) < 0) {
                Toast.makeText(MainActivity.this, "ไม่สามารถแก้ไข้เกินเวลาที่กำหนดได้", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (strResult_DateTimeCheck.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();

            try {
                date = dateFormat.parse(strResult_DateRef + " " + strResult_TimeRef);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -2);
            Date dateRef = calendar.getTime();

            if (date.compareTo(dateRef) < 0) {
                Toast.makeText(MainActivity.this,"ไม่สามารถเปลี่ยนแปลงเกินเวลาที่กำหนดได้", Toast.LENGTH_SHORT).show();
                return;
            }
        }



        Intent intent = new Intent(MainActivity.this, TakeSkipMedicineActivity.class);

        //ทำการ copy ข้อมูลไป TakeSkipMedicineActivity
        //intent.putExtra("Med_id",strings_receiver[0]);
        intent.putExtra("MainActivity_Tradename", strResult_Tradename);
        intent.putExtra("MainActivity_Appearance", strResult_Appearance);
        intent.putExtra("MainActivity_AmountTablet", strResult_AmountTablet);
        intent.putExtra("MainActivity_DateRef", strResult_DateRef);
        intent.putExtra("MainActivity_TimeRef", strResult_TimeRef);
        intent.putExtra("MainActivity_DateTimeCheck", strResult_DateTimeCheck);
        intent.putExtra("MainActivity_SkipHold", strResult_SkipHold);
        intent.putExtra("MainActivity_EA", strResult_EA);
        intent.putExtra("MainActivity_Sum_id", strResult_Sum_id);
        intent.putExtra("MainActivity_Main_id", strResult_Main_id);
        startActivity(intent);
        */

    }

    private void checkAndIntentToTakeSkipMedicineActivity() {

        if (!strResult_DateTimeCheck.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();
            try {
                date = dateFormat.parse(strResult_DateTimeCheck);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, -3);
            Date dateRef = calendar.getTime();

            if (date.compareTo(dateRef) < 0) {
                Toast.makeText(MainActivity.this, "ไม่สามารถแก้ไข้เกินเวลาที่กำหนดได้", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (!strResult_SkipHold.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            try {
                date = dateFormat.parse(strResult_SkipHold);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, -3);
            Date dateRef = calendar.getTime();

            if (date.compareTo(dateRef) < 0) {
                Toast.makeText(MainActivity.this, "ไม่สามารถแก้ไข้เกินเวลาที่กำหนดได้", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (strResult_DateTimeCheck.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();

            try {
                date = dateFormat.parse(strResult_DateRef + " " + strResult_TimeRef);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -2);
            Date dateRef = calendar.getTime();

            if (date.compareTo(dateRef) < 0) {
                Toast.makeText(MainActivity.this,"ไม่สามารถเปลี่ยนแปลงเกินเวลาที่กำหนดได้", Toast.LENGTH_SHORT).show();
                return;
            }
        }



        Intent intent = new Intent(MainActivity.this, TakeSkipMedicineActivity.class);

        //ทำการ copy ข้อมูลไป TakeSkipMedicineActivity
        //intent.putExtra("Med_id",strings_receiver[0]);
        intent.putExtra("MainActivity_Tradename", strResult_Tradename);
        intent.putExtra("MainActivity_Appearance", strResult_Appearance);
        intent.putExtra("MainActivity_AmountTablet", strResult_AmountTablet);
        intent.putExtra("MainActivity_DateRef", strResult_DateRef);
        intent.putExtra("MainActivity_TimeRef", strResult_TimeRef);
        intent.putExtra("MainActivity_DateTimeCheck", strResult_DateTimeCheck);
        intent.putExtra("MainActivity_SkipHold", strResult_SkipHold);
        intent.putExtra("MainActivity_EA", strResult_EA);
        intent.putExtra("MainActivity_Sum_id", strResult_Sum_id);
        intent.putExtra("MainActivity_Main_id", strResult_Main_id);
        startActivity(intent);
    }

    private void click_News() {
        textViewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Context context = MainActivity.this;
                //startActivity(new Intent(context, NewsActivity.class));
                final MyManage myManage = new MyManage(getBaseContext());
                String[] stringsStay = myManage.readSQLite_userTABLE(3);
                if (stringsStay[0].equals("0") || stringsStay[0].equals("1")) {
                    startActivity(new Intent(MainActivity.this, NewsActivity.class));
                } else {
                    Intent intent = new Intent(MainActivity.this, PopUpGate.class);
                    intent.putExtra("News", "News");
                    startActivity(intent);
                }
            }
        });
    }

    private void clickMedicationList() {
        textViewMedication.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final MyManage myManage = new MyManage(getBaseContext());
                //startActivity(new Intent(MainActivity.this, MedicationListActivity.class));
                String[] stringsStay = myManage.readSQLite_userTABLE(3);
                if (stringsStay[0].equals("0") || stringsStay[0].equals("1")) {
                    startActivity(new Intent(MainActivity.this, MedicationListActivity.class));
                } else {
                    Intent intent = new Intent(MainActivity.this, PopUpGate.class);
                    intent.putExtra("MedicationList", "MedicationList");
                    startActivity(intent);
                }
            }
        });
    }

    private void setDateAndTimeToday() {

        Log.d("abc", "เข้ามาที่ setDateAndTimeToday");

        MyManage myManage = new MyManage(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        today = dateFormat.format(System.currentTimeMillis());
        textViewMainDate.setText("วันที่ : " + today);

        stringsInterval = myManage.readTimeTABLE(1);
        stringsStartTime = myManage.readTimeTABLE(2);
        stringsEndTime = myManage.readTimeTABLE(3);
        stringstimeTABLE = new String[4];

        for (int x = 0; x < 4; x++) {
            stringstimeTABLE[x] = stringsInterval[x] + "(" + stringsStartTime[x] + " - " + stringsEndTime[x] + ")";
        }
        //set เวลาเป็นเช้า กลางวัน เย็น ก่อนนอน
        tvMorning.setText(stringstimeTABLE[0]);
        tvAfternoon.setText(stringstimeTABLE[1]);
        tvEvening.setText(stringstimeTABLE[2]);
        tvBedtime.setText(stringstimeTABLE[3]);
    }

    private void displayMedicineByDay(String date_specific) {

        MyManage myManage = new MyManage(this);

        String[] strings_Main_id = myManage.filter_sumTABLE__by_Date(date_specific, 1);  //ได้ Main_id จาก sumTABLE
        String[] strings_TimeRef = myManage.filter_sumTABLE__by_Date(date_specific, 3);  //ได้ Time_Ref จาก sumTABLE
        String[] strings_Sum_id = myManage.filter_sumTABLE__by_Date(date_specific, 0); //ได้ sum_id จาก sumTABLE
        String[] strings_DateCheck = myManage.filter_sumTABLE__by_Date(date_specific, 4); //ได้ DateCheck จาก sumTABLE
        String[] strings_TimeCheck = myManage.filter_sumTABLE__by_Date(date_specific, 5); //ได้ TimeCheck จาก sumTABLE

        String[] strings_DateTimeCheck = new String[strings_Main_id.length];
        if (!strings_Main_id[0].equals("")) {
            for(int i = 0;i<strings_Main_id.length;i++) {
                if (!strings_DateCheck[i].equals("")) {
                    strings_DateTimeCheck[i] = strings_DateCheck[i] + " " + strings_TimeCheck[i];
                } else {
                    strings_DateTimeCheck[i] = "";
                }
            }

        }

        //เพิ่ม skiphold
        String[] strings_skiphold = myManage.filter_sumTABLE__by_Date(date_specific, 6); //ได้ skipHold จาก sumTABLE
        String[] strings_Appearance = new String[strings_Main_id.length];

        if (!strings_Main_id[0].equals("")) {
            Log.d("ContentMainActivity", strings_Main_id[0] + strings_TimeRef[0]);

            for (int i = 0; i < strings_Main_id.length; i++) {

                String[] strings_medTABLE = myManage.filter_mainTABLE_by_id_Full(strings_Main_id[i]);
                strings_Appearance[i] = strings_medTABLE[6];
            }

            MyData myData = new MyData();
            int[] intsNotTakeYet = myData.translate_Small_Appearance(strings_Appearance); //รับค่ารูปจาก mainTABLE แล้วเปลี่ยนเป็นขนาดเล็ก
            int[] intsTake = myData.translate_Smallate_Appearance(strings_Appearance);
            int[] intsSkipHold = myData.translate_SmallSkipHold_Appearance(strings_Appearance);

            //เริ่มทำการใส่ภาพของใน MainActivity

            String strDayM1 = date_specific;
            String strTimeM1 = stringsStartTime[0];
            String strTimeM2 = stringsEndTime[0];

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date convertedDateMorning1 = new Date();
            Date convertedDateMorning2 = new Date();
            Date convertedDateAfternoon1 = new Date();
            Date convertedDateAfternoon2 = new Date();
            Date convertedDateEvening1 = new Date();
            Date convertedDateEvening2 = new Date();
            Date convertedBedtime1 = new Date();
            Date convertedBedtime2 = new Date();
            Date t = new Date();
            try {
                convertedDateMorning1 = dateFormat.parse(date_specific + " " + stringsStartTime[0]);
                convertedDateMorning2 = dateFormat.parse(date_specific + " " + stringsEndTime[0]);
                convertedDateAfternoon1 = dateFormat.parse(date_specific + " " + stringsStartTime[1]);
                convertedDateAfternoon2 = dateFormat.parse(date_specific + " " + stringsEndTime[1]);
                convertedDateEvening1 = dateFormat.parse(date_specific + " " + stringsStartTime[2]);
                convertedDateEvening2 = dateFormat.parse(date_specific + " " + stringsEndTime[2]);
                convertedBedtime1 = dateFormat.parse(date_specific + " " + stringsStartTime[3]);
                convertedBedtime2 = dateFormat.parse(date_specific + " " + stringsEndTime[3]);
                t = dateFormat.parse(date_specific + " " + strings_TimeRef[0]);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            //แค่เช็ค Log.d ดูว่าถูกต้อง
            String s = dateFormat.format(convertedDateMorning1);
            String s1 = dateFormat.format(convertedDateMorning2);
            String s2 = dateFormat.format(convertedDateAfternoon1);
            String s3 = dateFormat.format(convertedDateAfternoon2);
            String s4 = dateFormat.format(convertedDateEvening1);
            String s5 = dateFormat.format(convertedDateEvening2);
            String s6 = dateFormat.format(convertedBedtime1);
            String s7 = dateFormat.format(convertedBedtime2);
            String st = dateFormat.format(t);
            Log.d("abc", "st :" + st);


            if (t.after(convertedDateMorning1)) {
                Log.d("abc", st + " ตามหลัง " + s);
            }

            Log.d("abc", s + " " + s1);
            Log.d("abc", s2 + " " + s3);
            Log.d("abc", s4 + " " + s5);
            Log.d("abc", s6 + " " + s7);  // ลบได้ต้องแต่ //แค่เช็ค ถึงบรรทัดนี้

            Date time = new Date();
            //ลบข้อมูลทั้งหมดใน displayTABLE
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyHelper.DATABASE_NAME, MODE_PRIVATE, null);
            sqLiteDatabase.delete("displayTABLE", null, null);

            //เริ่มใส่ข้อมูลเข้าไปใน displayTABLE
            for (int z = 0; z < strings_TimeRef.length; z++) {
                String strValue;
                try {
                    time = dateFormat.parse(date_specific + " " + strings_TimeRef[z]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (time.compareTo(convertedDateMorning1) >= 0 && time.compareTo(convertedDateMorning2) <= 0) {
                    Log.d("abc", "อยู่ระหว่าง 06:00 - 11:59");
                    strValue = myManage.filterdisplayTABLE_MAEB_By_Position("M");
                    Log.d("abc", "strREAD :" + strValue);
                    if (strValue.equals("Non value")) {
                        myManage.adddisplayTABLEValue("M1", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("abc", "strings_DateTimeCheck[0] มีค่าว่าง");
                            imageButtonM1.setImageResource(intsNotTakeYet[z]);
                            imageButtonM1.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonM1.setImageResource(intsTake[z]);
                            imageButtonM1.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonM1.setImageResource(intsSkipHold[z]);
                            imageButtonM1.setVisibility(View.VISIBLE);
                        }
                        // Non Value เติมค่า M1
                    } else if (strValue.equals("M1")) {
                        myManage.adddisplayTABLEValue("M2", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("abc", "strings_DateTimeCheck[1] มีค่าว่าง");
                            imageButtonM2.setImageResource(intsNotTakeYet[z]);
                            imageButtonM2.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonM2.setImageResource(intsTake[z]);
                            imageButtonM2.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonM2.setImageResource(intsSkipHold[z]);
                            imageButtonM2.setVisibility(View.VISIBLE);
                        }
                        // มี M1 แล้ว เติม M2
                    } else if (strValue.equals("M2")) {
                        myManage.adddisplayTABLEValue("M3", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("abc", "strings_DateTimeCheck[2] มีค่าว่าง");
                            imageButtonM3.setImageResource(intsNotTakeYet[z]);
                            imageButtonM3.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonM3.setImageResource(intsTake[z]);
                            imageButtonM3.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonM3.setImageResource(intsSkipHold[z]);
                            imageButtonM3.setVisibility(View.VISIBLE);
                        }
                        // มี M2 แล้ว เติม M3
                    } else if (strValue.equals("M3")) {
                        myManage.adddisplayTABLEValue("M4", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("abc", "strings_DateTimeCheck[3] มีค่าว่าง");
                            imageButtonM4.setImageResource(intsNotTakeYet[z]);
                            imageButtonM4.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonM4.setImageResource(intsTake[z]);
                            imageButtonM4.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonM4.setImageResource(intsSkipHold[z]);
                            imageButtonM4.setVisibility(View.VISIBLE);
                        }
                        // มี M3 แล้ว เติม M4
                    } else if (strValue.equals("M4")) {
                        myManage.adddisplayTABLEValue("M5", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("abc", "strings_DateTimeCheck[4] มีค่าว่าง");
                            imageButtonM5.setImageResource(intsNotTakeYet[z]);
                            imageButtonM5.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonM5.setImageResource(intsTake[z]);
                            imageButtonM5.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonM5.setImageResource(intsSkipHold[z]);
                            imageButtonM5.setVisibility(View.VISIBLE);
                        }
                        // มี M4 แล้ว เติม M5
                    } else if (strValue.equals("M5")) {
                        myManage.adddisplayTABLEValue("M6", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("abc", "strings_DateTimeCheck[5] มีค่าว่าง");
                            imageButtonM6.setImageResource(intsNotTakeYet[z]);
                            imageButtonM6.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonM6.setImageResource(intsTake[z]);
                            imageButtonM6.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonM6.setImageResource(intsSkipHold[z]);
                            imageButtonM6.setVisibility(View.VISIBLE);
                        }
                        // มี M5 แล้ว เติม M6
                    } else if (strValue.equals("M6")) {
                        myManage.adddisplayTABLEValue("M7", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("abc", "strings_DateTimeCheck[6] มีค่าว่าง");
                            imageButtonM7.setImageResource(intsNotTakeYet[z]);
                            imageButtonM7.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonM7.setImageResource(intsTake[z]);
                            imageButtonM7.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonM7.setImageResource(intsSkipHold[z]);
                            imageButtonM7.setVisibility(View.VISIBLE);
                        }
                        // มี M6 แล้ว เติม M7
                    } else if (strValue.equals("M7")) {
                        myManage.adddisplayTABLEValue("M8", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("abc", "strings_DateTimeCheck[7] มีค่าว่าง");
                            imageButtonM8.setImageResource(intsNotTakeYet[z]);
                            imageButtonM8.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonM8.setImageResource(intsTake[z]);
                            imageButtonM8.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonM8.setImageResource(intsSkipHold[z]);
                            imageButtonM8.setVisibility(View.VISIBLE);
                        }
                        // มี M7 แล้ว เติม M8
                    } else if (strValue.equals("M8")) {
                        myManage.adddisplayTABLEValue("M9", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("abc", "strings_DateTimeCheck[8] มีค่าว่าง");
                            imageButtonM9.setImageResource(intsNotTakeYet[z]);
                            imageButtonM9.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonM9.setImageResource(intsTake[z]);
                            imageButtonM9.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonM9.setImageResource(intsSkipHold[z]);
                            imageButtonM9.setVisibility(View.VISIBLE);
                        }
                        // มี M8 แล้ว เติม M9
                    } else if (strValue.equals("M9")) {
                        String strCheckERR =  myManage.filterdisplayTABLE_Position_ERR();
                        if (strCheckERR.equals("False")) {
                            myManage.adddisplayTABLEValue("#ERR#", "", "", "", "", "", "","");
                        }
                    } //มี M9 แล้ว เติม #ERR#

                } // if ของ Morning...

                if (time.compareTo(convertedDateAfternoon1) >= 0 && time.compareTo(convertedDateAfternoon2) <= 0) {
                    Log.d("afternoon", "อยู่ระหว่าง 12:00 - 17:59");
                    strValue = myManage.filterdisplayTABLE_MAEB_By_Position("A");
                    Log.d("afternoon", "strREAD :" + strValue);
                    if (strValue.equals("Non value")) {
                        myManage.adddisplayTABLEValue("A1", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("afternoon", "strings_DateTimeCheck[0] มีค่าว่าง");
                            imageButtonA1.setImageResource(intsNotTakeYet[z]);
                            imageButtonA1.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonA1.setImageResource(intsTake[z]);
                            imageButtonA1.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonA1.setImageResource(intsSkipHold[z]);
                            imageButtonA1.setVisibility(View.VISIBLE);
                        }
                        // Non Value เติมค่า A1
                    } else if (strValue.equals("A1")) {
                        myManage.adddisplayTABLEValue("A2", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("afternoon", "strings_DateTimeCheck[1] มีค่าว่าง");
                            imageButtonA2.setImageResource(intsNotTakeYet[z]);
                            imageButtonA2.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonA2.setImageResource(intsTake[z]);
                            imageButtonA2.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonA2.setImageResource(intsSkipHold[z]);
                            imageButtonA2.setVisibility(View.VISIBLE);
                        }
                        // มี A1 แล้ว เติม A2
                    } else if (strValue.equals("A2")) {
                        myManage.adddisplayTABLEValue("A3", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("afternoon", "strings_DateTimeCheck[2] มีค่าว่าง");
                            imageButtonA3.setImageResource(intsNotTakeYet[z]);
                            imageButtonA3.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonA3.setImageResource(intsTake[z]);
                            imageButtonA3.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonA3.setImageResource(intsSkipHold[z]);
                            imageButtonA3.setVisibility(View.VISIBLE);
                        }
                        // มี A2 แล้ว เติม A3
                    } else if (strValue.equals("A3")) {
                        myManage.adddisplayTABLEValue("A4", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("afternoon", "strings_DateTimeCheck[3] มีค่าว่าง");
                            imageButtonA4.setImageResource(intsNotTakeYet[z]);
                            imageButtonA4.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonA4.setImageResource(intsTake[z]);
                            imageButtonA4.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonA4.setImageResource(intsSkipHold[z]);
                            imageButtonA4.setVisibility(View.VISIBLE);
                        }
                        // มี A3 แล้ว เติม A4
                    } else if (strValue.equals("A4")) {
                        myManage.adddisplayTABLEValue("A5", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("afternoon", "strings_DateTimeCheck[4] มีค่าว่าง");
                            imageButtonA5.setImageResource(intsNotTakeYet[z]);
                            imageButtonA5.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonA5.setImageResource(intsTake[z]);
                            imageButtonA5.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonA5.setImageResource(intsSkipHold[z]);
                            imageButtonA5.setVisibility(View.VISIBLE);
                        }
                        // มี A4 แล้ว เติม A5
                    } else if (strValue.equals("A5")) {
                        myManage.adddisplayTABLEValue("A6", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("abc", "strings_DateTimeCheck[5] มีค่าว่าง");
                            imageButtonA6.setImageResource(intsNotTakeYet[z]);
                            imageButtonA6.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonA6.setImageResource(intsTake[z]);
                            imageButtonA6.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonA6.setImageResource(intsSkipHold[z]);
                            imageButtonA6.setVisibility(View.VISIBLE);
                        }
                        // มี A5 แล้ว เติม A6
                    } else if (strValue.equals("A6")) {
                        myManage.adddisplayTABLEValue("A7", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("afternoon", "strings_DateTimeCheck[6] มีค่าว่าง");
                            imageButtonA7.setImageResource(intsNotTakeYet[z]);
                            imageButtonA7.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonA7.setImageResource(intsTake[z]);
                            imageButtonA7.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonA7.setImageResource(intsSkipHold[z]);
                            imageButtonA7.setVisibility(View.VISIBLE);
                        }
                        // มี A6 แล้ว เติม A7
                    } else if (strValue.equals("A7")) {
                        myManage.adddisplayTABLEValue("A8", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("afternoon", "strings_DateTimeCheck[7] มีค่าว่าง");
                            imageButtonA8.setImageResource(intsNotTakeYet[z]);
                            imageButtonA8.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonA8.setImageResource(intsTake[z]);
                            imageButtonA8.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonA8.setImageResource(intsSkipHold[z]);
                            imageButtonA8.setVisibility(View.VISIBLE);
                        }
                        // มี A7 แล้ว เติม A8
                    } else if (strValue.equals("A8")) {
                        myManage.adddisplayTABLEValue("A9", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("afternoon", "strings_DateTimeCheck[8] มีค่าว่าง");
                            imageButtonA9.setImageResource(intsNotTakeYet[z]);
                            imageButtonA9.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonA9.setImageResource(intsTake[z]);
                            imageButtonA9.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonA9.setImageResource(intsSkipHold[z]);
                            imageButtonA9.setVisibility(View.VISIBLE);
                        }
                        // มี A8 แล้ว เติม A9
                    } else if (strValue.equals("A9")) {
                        String strCheckERR =  myManage.filterdisplayTABLE_Position_ERR();
                        if (strCheckERR.equals("False")) {
                            myManage.adddisplayTABLEValue("#ERR#", "", "", "", "", "", "","");
                        }
                    } //มี A9 แล้ว เติม #ERR#

                } // if ของ Afternoon


                //เริ่มทำ Evening
                if (time.compareTo(convertedDateEvening1) >= 0 && time.compareTo(convertedDateEvening2) <= 0) {
                    Log.d("Evening", "อยู่ระหว่าง 18:00 - 23:59");
                    strValue = myManage.filterdisplayTABLE_MAEB_By_Position("E");
                    Log.d("Evening", "strREAD :" + strValue);
                    if (strValue.equals("Non value")) {
                        myManage.adddisplayTABLEValue("E1", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Evening", "strings_DateTimeCheck[0] มีค่าว่าง");
                            imageButtonE1.setImageResource(intsNotTakeYet[z]);
                            imageButtonE1.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonE1.setImageResource(intsTake[z]);
                            imageButtonE1.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonE1.setImageResource(intsSkipHold[z]);
                            imageButtonE1.setVisibility(View.VISIBLE);
                        }
                        // Non Value เติมค่า E1
                    } else if (strValue.equals("E1")) {
                        myManage.adddisplayTABLEValue("E2", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Evening", "strings_DateTimeCheck[1] มีค่าว่าง");
                            imageButtonE2.setImageResource(intsNotTakeYet[z]);
                            imageButtonE2.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonE2.setImageResource(intsTake[z]);
                            imageButtonE2.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonE2.setImageResource(intsSkipHold[z]);
                            imageButtonE2.setVisibility(View.VISIBLE);
                        }
                        // มี E1 แล้ว เติม E2
                    } else if (strValue.equals("E2")) {
                        myManage.adddisplayTABLEValue("E3", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Evening", "strings_DateTimeCheck[2] มีค่าว่าง");
                            imageButtonE3.setImageResource(intsNotTakeYet[z]);
                            imageButtonE3.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonE3.setImageResource(intsTake[z]);
                            imageButtonE3.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonE3.setImageResource(intsSkipHold[z]);
                            imageButtonE3.setVisibility(View.VISIBLE);
                        }
                        // มี E2 แล้ว เติม E3
                    } else if (strValue.equals("E3")) {
                        myManage.adddisplayTABLEValue("E4", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Evening", "strings_DateTimeCheck[3] มีค่าว่าง");
                            imageButtonE4.setImageResource(intsNotTakeYet[z]);
                            imageButtonE4.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonE4.setImageResource(intsTake[z]);
                            imageButtonE4.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonE4.setImageResource(intsSkipHold[z]);
                            imageButtonE4.setVisibility(View.VISIBLE);
                        }
                        // มี E3 แล้ว เติม E4
                    } else if (strValue.equals("E4")) {
                        myManage.adddisplayTABLEValue("E5", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Evening", "strings_DateTimeCheck[4] มีค่าว่าง");
                            imageButtonE5.setImageResource(intsNotTakeYet[z]);
                            imageButtonE5.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonE5.setImageResource(intsTake[z]);
                            imageButtonE5.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonE5.setImageResource(intsSkipHold[z]);
                            imageButtonE5.setVisibility(View.VISIBLE);
                        }
                        // มี E4 แล้ว เติม E5
                    } else if (strValue.equals("E5")) {
                        myManage.adddisplayTABLEValue("E6", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Evening", "strings_DateTimeCheck[5] มีค่าว่าง");
                            imageButtonE6.setImageResource(intsNotTakeYet[z]);
                            imageButtonE6.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonE6.setImageResource(intsTake[z]);
                            imageButtonE6.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonE6.setImageResource(intsSkipHold[z]);
                            imageButtonE6.setVisibility(View.VISIBLE);
                        }
                        // มี E5 แล้ว เติม E6
                    } else if (strValue.equals("E6")) {
                        myManage.adddisplayTABLEValue("E7", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Evening", "strings_DateTimeCheck[6] มีค่าว่าง");
                            imageButtonE7.setImageResource(intsNotTakeYet[z]);
                            imageButtonE7.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonE7.setImageResource(intsTake[z]);
                            imageButtonE7.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonE7.setImageResource(intsSkipHold[z]);
                            imageButtonE7.setVisibility(View.VISIBLE);
                        }
                        // มี E6 แล้ว เติม E7
                    } else if (strValue.equals("E7")) {
                        myManage.adddisplayTABLEValue("E8", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Evening", "strings_DateTimeCheck[7] มีค่าว่าง");
                            imageButtonE8.setImageResource(intsNotTakeYet[z]);
                            imageButtonE8.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonE8.setImageResource(intsTake[z]);
                            imageButtonE8.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonE8.setImageResource(intsSkipHold[z]);
                            imageButtonE8.setVisibility(View.VISIBLE);
                        }
                        // มี E7 แล้ว เติม E8
                    } else if (strValue.equals("E8")) {
                        myManage.adddisplayTABLEValue("E9", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Evening", "strings_DateTimeCheck[8] มีค่าว่าง");
                            imageButtonE9.setImageResource(intsNotTakeYet[z]);
                            imageButtonE9.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonE9.setImageResource(intsTake[z]);
                            imageButtonE9.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonE9.setImageResource(intsSkipHold[z]);
                            imageButtonE9.setVisibility(View.VISIBLE);
                        }
                        // มี E8 แล้ว เติม E9
                    } else if (strValue.equals("E9")) {
                        String strCheckERR =  myManage.filterdisplayTABLE_Position_ERR();
                        if (strCheckERR.equals("False")) {
                            myManage.adddisplayTABLEValue("#ERR#", "", "", "", "", "", "","");
                        }
                    } //มี E9 แล้ว เติม #ERR#

                } // if ของ Evening


                if (time.compareTo(convertedBedtime1) >= 0 && time.compareTo(convertedBedtime2) <= 0) {
                    Log.d("Bedtime", "อยู่ระหว่าง 00:00 - 05:59");
                    strValue = myManage.filterdisplayTABLE_MAEB_By_Position("B");
                    Log.d("Bedtime", "strREAD :" + strValue);
                    if (strValue.equals("Non value")) {
                        myManage.adddisplayTABLEValue("B1", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Bedtime", "strings_DateTimeCheck[0] มีค่าว่าง");
                            imageButtonB1.setImageResource(intsNotTakeYet[z]);
                            imageButtonB1.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonB1.setImageResource(intsTake[z]);
                            imageButtonB1.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonB1.setImageResource(intsSkipHold[z]);
                            imageButtonB1.setVisibility(View.VISIBLE);
                        }
                        // Non Value เติมค่า B1
                    } else if (strValue.equals("B1")) {
                        myManage.adddisplayTABLEValue("B2", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Bedtime", "strings_DateTimeCheck[1] มีค่าว่าง");
                            imageButtonB2.setImageResource(intsNotTakeYet[z]);
                            imageButtonB2.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonB2.setImageResource(intsTake[z]);
                            imageButtonB2.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonB2.setImageResource(intsSkipHold[z]);
                            imageButtonB2.setVisibility(View.VISIBLE);
                        }
                        // มี B1 แล้ว เติม B2
                    } else if (strValue.equals("B2")) {
                        myManage.adddisplayTABLEValue("B3", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Bedtime", "strings_DateTimeCheck[2] มีค่าว่าง");
                            imageButtonB3.setImageResource(intsNotTakeYet[z]);
                            imageButtonB3.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonB3.setImageResource(intsTake[z]);
                            imageButtonB3.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonB3.setImageResource(intsSkipHold[z]);
                            imageButtonB3.setVisibility(View.VISIBLE);
                        }
                        // มี B2 แล้ว เติม B3
                    } else if (strValue.equals("B3")) {
                        myManage.adddisplayTABLEValue("B4", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Bedtime", "strings_DateTimeCheck[3] มีค่าว่าง");
                            imageButtonB4.setImageResource(intsNotTakeYet[z]);
                            imageButtonB4.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonB4.setImageResource(intsTake[z]);
                            imageButtonB4.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonB4.setImageResource(intsSkipHold[z]);
                            imageButtonB4.setVisibility(View.VISIBLE);
                        }
                        // มี B3 แล้ว เติม B4
                    } else if (strValue.equals("B4")) {
                        myManage.adddisplayTABLEValue("B5", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Bedtime", "strings_DateTimeCheck[4] มีค่าว่าง");
                            imageButtonB5.setImageResource(intsNotTakeYet[z]);
                            imageButtonB5.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonB5.setImageResource(intsTake[z]);
                            imageButtonB5.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonB5.setImageResource(intsSkipHold[z]);
                            imageButtonB5.setVisibility(View.VISIBLE);
                        }
                        // มี B4 แล้ว เติม B5
                    } else if (strValue.equals("B5")) {
                        myManage.adddisplayTABLEValue("B6", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Bedtime", "strings_DateTimeCheck[5] มีค่าว่าง");
                            imageButtonB6.setImageResource(intsNotTakeYet[z]);
                            imageButtonB6.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonB6.setImageResource(intsTake[z]);
                            imageButtonB6.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonB6.setImageResource(intsSkipHold[z]);
                            imageButtonB6.setVisibility(View.VISIBLE);
                        }
                        // มี B5 แล้ว เติม B6
                    } else if (strValue.equals("B6")) {
                        myManage.adddisplayTABLEValue("B7", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Bedtime", "strings_DateTimeCheck[6] มีค่าว่าง");
                            imageButtonB7.setImageResource(intsNotTakeYet[z]);
                            imageButtonB7.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonB7.setImageResource(intsTake[z]);
                            imageButtonB7.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonB7.setImageResource(intsSkipHold[z]);
                            imageButtonB7.setVisibility(View.VISIBLE);
                        }
                        // มี B6 แล้ว เติม B7
                    } else if (strValue.equals("B7")) {
                        myManage.adddisplayTABLEValue("B8", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Bedtime", "strings_DateTimeCheck[7] มีค่าว่าง");
                            imageButtonB8.setImageResource(intsNotTakeYet[z]);
                            imageButtonB8.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonB8.setImageResource(intsTake[z]);
                            imageButtonB8.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonB8.setImageResource(intsSkipHold[z]);
                            imageButtonB8.setVisibility(View.VISIBLE);
                        }
                        // มี B7 แล้ว เติม B8
                    } else if (strValue.equals("B8")) {
                        myManage.adddisplayTABLEValue("B9", strings_Sum_id[z], strings_Main_id[z], date_specific, strings_TimeRef[z], strings_DateTimeCheck[z], strings_Appearance[z],strings_skiphold[z]);
                        if (strings_DateTimeCheck[z].equals("")) {
                            Log.d("Bedtime", "strings_DateTimeCheck[8] มีค่าว่าง");
                            imageButtonB9.setImageResource(intsNotTakeYet[z]);
                            imageButtonB9.setVisibility(View.VISIBLE);
                        } else {
                            imageButtonB9.setImageResource(intsTake[z]);
                            imageButtonB9.setVisibility(View.VISIBLE);
                        }
                        if (!strings_skiphold[z].equals("")) {
                            imageButtonB9.setImageResource(intsSkipHold[z]);
                            imageButtonB9.setVisibility(View.VISIBLE);
                        }
                        // มี B8 แล้ว เติม B9
                    } else if (strValue.equals("B9")) {
                        String strCheckERR =  myManage.filterdisplayTABLE_Position_ERR();
                        if (strCheckERR.equals("False")) {
                            myManage.adddisplayTABLEValue("#ERR#", "", "", "", "", "", "","");
                        }
                    } //มี B9 แล้ว เติม #ERR#

                } // if ของ Bedtime

            } //for


        } //if แรก



    }


    private void click_ImageButtonAdherence() {
        imageAdherence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdherenceActivity.class);
                startActivity(intent);



            }
        });
    }


    private void clickAddbtn() {
        final MyManage myManage = new MyManage(this);

        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popup_main_add, null);

                popupWindow = new PopupWindow(container, ListPopupWindow.WRAP_CONTENT, ListPopupWindow.WRAP_CONTENT, true);
                popupWindow.showAtLocation(relativeLayout, Gravity.BOTTOM, 0, 0);

                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });


                imageButtonPop1 = (ImageButton) container.findViewById(R.id.btn_pop1);
                imageButtonPop1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] stringsStay = myManage.readSQLite_userTABLE(3);
                        if (stringsStay[0].equals("0") || stringsStay[0].equals("1")) {
                            startActivity(new Intent(MainActivity.this, AddMedicineActivity.class));
                        } else {
                            Intent intent = new Intent(MainActivity.this, PopUpGate.class);
                            intent.putExtra("btn_pop1", "btn_pop1");
                            startActivity(intent);
                        }
                        popupWindow.dismiss();
                    }
                });

                imageButtonPop2 = (ImageButton) container.findViewById(R.id.btn_pop2);
                imageButtonPop2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startActivity(new Intent(MainActivity.this, AppointmentActivity.class));
                        String[] stringsStay = myManage.readSQLite_userTABLE(3);
                        if (stringsStay[0].equals("0") || stringsStay[0].equals("1")) {
                            startActivity(new Intent(MainActivity.this, AppointmentActivity.class));
                        } else {
                            Intent intent = new Intent(MainActivity.this, PopUpGate.class);
                            intent.putExtra("btn_pop2", "btn_pop2");
                            startActivity(intent);
                        }
                        popupWindow.dismiss();
                    }
                });

                imageButtonPop3 = (ImageButton) container.findViewById(R.id.btn_pop3);
                imageButtonPop3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startActivity(new Intent(MainActivity.this,LabActivity.class));
                        String[] stringsStay = myManage.readSQLite_userTABLE(3);
                        if (stringsStay[0].equals("0") || stringsStay[0].equals("1")) {
                            startActivity(new Intent(MainActivity.this, LabActivity.class));
                        } else {
                            Intent intent = new Intent(MainActivity.this, PopUpGate.class);
                            intent.putExtra("btn_pop3", "btn_pop3");
                            startActivity(intent);
                        }
                        popupWindow.dismiss();
                    }
                });


                imageButtonPop4 = (ImageButton) container.findViewById(R.id.btn_pop4);
                imageButtonPop4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] stringsStay = myManage.readSQLite_userTABLE(3);
                        if (stringsStay[0].equals("0") || stringsStay[0].equals("1")) {
                            startActivity(new Intent(MainActivity.this, NoteActivity.class));
                        } else {
                            Intent intent = new Intent(MainActivity.this, PopUpGate.class);
                            intent.putExtra("btn_pop4", "btn_pop4");
                            startActivity(intent);
                        }
                        popupWindow.dismiss();

                        /*
                        final EditText editText = new EditText(MainActivity.this);
                        editText.setInputType(16);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(false);
                        builder.setIcon(R.drawable.logo_carabao48);
                        builder.setTitle("รหัสผ่าน");
                        builder.setMessage("โปรดใส่รหัสผ่าน (รหัสเดียวกับหน้า Login)");
                        builder.setView(editText);
                        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String strEditText = editText.getText().toString().trim();
                                MyManage myManage = new MyManage(MainActivity.this);
                                String[] strPassword = myManage.readAlluserTABLE(2);

                                if (strPassword[0].equals(strEditText)) {
                                    startActivity(new Intent(MainActivity.this, NoteActivity.class));
                                    Toast.makeText(getBaseContext(),"เข้า NoteActivity",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    popupWindow.dismiss();
                                } else {
                                    Toast.makeText(getBaseContext(),"รหัสผิดพลาด",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        });
                        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                popupWindow.dismiss();
                            }
                        });
                        builder.show();
                        */
                    }
                });


                imageButtonPop6 = (ImageButton) container.findViewById(R.id.btn_pop6);
                imageButtonPop6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });


            }
        });
    }

    private void notificationFormSQLite() {

        //Read sumTABLE
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyHelper.DATABASE_NAME,
                MODE_PRIVATE, null); //เชื่อมต่อกับ SQLiteDatabase
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM sumTABLE", null);
        cursor.moveToFirst();

        String[] dateStrings = new String[cursor.getCount()];
        String[] dayStrings = new String[cursor.getCount()];
        String[] monthStrings = new String[cursor.getCount()];
        String[] timeStrings = new String[cursor.getCount()];
        String[] hrStrings = new String[cursor.getCount()];
        String[] minStrings = new String[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++) {

            dateStrings[i] = cursor.getString(2);
            String[] strings = dateStrings[i].split("/");
            dayStrings[i] = strings[0];
            monthStrings[i] = strings[1];

            timeStrings[i] = cursor.getString(3);
            String[] strings1 = timeStrings[i].split(":");
            hrStrings[i] = strings1[0];
            minStrings[i] = strings1[1];

        } //ตอนนี้คาดว่าน่าจะยังไม่จำเป็นนะจ๊ะ

        cursor.close();

    }  //notication

    private void updatesumTABLE001() {
        Calendar calendar = Calendar.getInstance();  //ค้นหาเวลาในเครื่อง
        Calendar myCalendar1 = (Calendar) calendar.clone(); //clone เวลาในเครื่องเข้ามาใช้


        //myCalendar1.set(calendar.DAY_OF_MONTH,15);
        myCalendar1.set(Calendar.HOUR_OF_DAY, 23);
        myCalendar1.set(Calendar.MINUTE, 59);
        myCalendar1.set(Calendar.SECOND, 59);
        myCalendar1.set(Calendar.MILLISECOND, 59);

        Log.d("10MayV1", "myCaledar ==> " + myCalendar1.getTime().toString()); //กำหนดค่าค่าเวลาในการเตือน

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String currentDate = dateFormat.format(date);

        Intent intent = new Intent(getBaseContext(), DailyUpdateReceiver.class);
        //Intent intent = new Intent(getBaseContext(),AlarmReceiver.class);


        //Date
        //intent.putExtra("intDay",calendar.get(Calendar.DAY_OF_MONTH) );

        intent.putExtra("Date", currentDate);

        Random random = new Random();
        int myRandom = random.nextInt(1000);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),
                myRandom, intent, 0);


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, myCalendar1.getTimeInMillis(), pendingIntent); //Wakeuppppppp

    }

    private void bindWidget() {
        textViewAdd = (TextView) findViewById(R.id.textView_Main_Add);
        textViewNews = (TextView) findViewById(R.id.textView_News);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        imageAdherence = (ImageButton) findViewById(R.id.imageButtonAdherence);
        textViewMainDate = (TextView) findViewById(R.id.textViewMainDate);
        textViewMedication = (TextView) findViewById(R.id.textView_Medicine);
        imageButtonA1 = (ImageButton) findViewById(R.id.iB_A1);
        imageButtonA2 = (ImageButton) findViewById(R.id.iB_A2);
        imageButtonA3 = (ImageButton) findViewById(R.id.iB_A3);
        imageButtonA4 = (ImageButton) findViewById(R.id.iB_A4);
        imageButtonA5 = (ImageButton) findViewById(R.id.iB_A5);
        imageButtonA6 = (ImageButton) findViewById(R.id.iB_A6);
        imageButtonA7 = (ImageButton) findViewById(R.id.iB_A7);
        imageButtonA8 = (ImageButton) findViewById(R.id.iB_A8);
        imageButtonA9 = (ImageButton) findViewById(R.id.iB_A9);
        imageButtonB1 = (ImageButton) findViewById(R.id.iB_B1);
        imageButtonB2 = (ImageButton) findViewById(R.id.iB_B2);
        imageButtonB3 = (ImageButton) findViewById(R.id.iB_B3);
        imageButtonB4 = (ImageButton) findViewById(R.id.iB_B4);
        imageButtonB5 = (ImageButton) findViewById(R.id.iB_B5);
        imageButtonB6 = (ImageButton) findViewById(R.id.iB_B6);
        imageButtonB7 = (ImageButton) findViewById(R.id.iB_B7);
        imageButtonB8 = (ImageButton) findViewById(R.id.iB_B8);
        imageButtonB9 = (ImageButton) findViewById(R.id.iB_B9);
        imageButtonM1 = (ImageButton) findViewById(R.id.iB_M1);
        imageButtonM2 = (ImageButton) findViewById(R.id.iB_M2);
        imageButtonM3 = (ImageButton) findViewById(R.id.iB_M3);
        imageButtonM4 = (ImageButton) findViewById(R.id.iB_M4);
        imageButtonM5 = (ImageButton) findViewById(R.id.iB_M5);
        imageButtonM6 = (ImageButton) findViewById(R.id.iB_M6);
        imageButtonM7 = (ImageButton) findViewById(R.id.iB_M7);
        imageButtonM8 = (ImageButton) findViewById(R.id.iB_M8);
        imageButtonM9 = (ImageButton) findViewById(R.id.iB_M9);
        imageButtonE1 = (ImageButton) findViewById(R.id.iB_E1);
        imageButtonE2 = (ImageButton) findViewById(R.id.iB_E2);
        imageButtonE3 = (ImageButton) findViewById(R.id.iB_E3);
        imageButtonE4 = (ImageButton) findViewById(R.id.iB_E4);
        imageButtonE5 = (ImageButton) findViewById(R.id.iB_E5);
        imageButtonE6 = (ImageButton) findViewById(R.id.iB_E6);
        imageButtonE7 = (ImageButton) findViewById(R.id.iB_E7);
        imageButtonE8 = (ImageButton) findViewById(R.id.iB_E8);
        imageButtonE9 = (ImageButton) findViewById(R.id.iB_E9);
        tvMorning = (TextView) findViewById(R.id.tvMorning);
        tvAfternoon = (TextView) findViewById(R.id.tvAfternoon);
        tvEvening = (TextView) findViewById(R.id.tvEvening);
        tvBedtime = (TextView) findViewById(R.id.tvBedtime);
        spinner = (Spinner) findViewById(R.id.spinner);

    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        MyData myData = new MyData();

        String sSpecificDate = myData.createStringDay(dayOfMonth, monthOfYear + 1, year);
        textViewMainDate.setText("วันที่ : " + sSpecificDate);

        showView();

        displayMedicineByDay(sSpecificDate);
        clickAddbtn();

        //คลิก รายการยา
        clickMedicationList();

        //คลิก ImageButtonAdherence
        //click_ImageButtonAdherence();
        setHeading();

        click_News();

        clickImagepill();

        clickTextViewMainDate();

    }
} //Main Class
