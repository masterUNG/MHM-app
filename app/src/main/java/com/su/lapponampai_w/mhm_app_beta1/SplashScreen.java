package com.su.lapponampai_w.mhm_app_beta1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class SplashScreen extends AppCompatActivity {

    MyManage myManage;
    MyData myData;
    int anInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        myManage = new MyManage(this);
        myData = new MyData();

        //Test Add Value to sumTABLE
        //myManage.addValueToSumTable("Main_id", "DateRef", "TimeRef", "DateCheck",
        //      "TimeCheck", "SkipHold");

        //updatesumTABLE00();

        //updatesumTABLE00_New();


        //updatesumTABLE00_New2();

        //updatesumTABLEAndBroadcast1();

        //updatesumTABLEAndBroadcast();

        updateDailyBroadcast(getBaseContext());


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                anInt = myManage.check_null_userTABLE();

                if (anInt == 1) {

                    // ต้องแยก Stay ว่า 0 หรือ 1
                    String[] strings = myManage.readSQLite_userTABLE(3);
                    if (strings[0].equals("1") || strings[0].equals("2")) {
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                        finish();
                    } else if (strings[0].equals("0")) {
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        finish();
                    } else {
                        Toast t = Toast.makeText(SplashScreen.this, "Stay != '0' or '1' What's happen", Toast.LENGTH_LONG); // ลบภายหลัง
                        t.show();
                    }




                } else {
                    startActivity(new Intent(SplashScreen.this, SignUpActivity.class)); // เดี่ยวต้องเปลี่ยน
                    Toast t = Toast.makeText(SplashScreen.this, Integer.toString(anInt) , Toast.LENGTH_LONG); //ลบภายหลัง
                    t.show();
                    finish();
                }


            }
        },3000); // 2 วินาที

        myManage.nameGenericTABLEData();
        myManage.medTABLEData();
        myManage.drugInteractionTABLEData();
        myManage.timeTABLEData();
        myManage.newsTABLEData();

    }  //Main Method

    public void updateDailyBroadcast(Context context) {

        Intent alertIntent = new Intent(context, DailyUpdateReceiver.class); //1

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE); //2

        for(int a = 950;a <953;a++ ) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT); //3
            pendingIntent.cancel();
        }


        Calendar calendarCurrent = Calendar.getInstance();
        Calendar myCalendarAlarm = (Calendar) calendarCurrent.clone(); //clone เวลาในเครื่องเข้ามาใช้

        myCalendarAlarm.set(Calendar.HOUR_OF_DAY,0);
        myCalendarAlarm.set(Calendar.MINUTE, 0);
        myCalendarAlarm.set(Calendar.SECOND,0);
        myCalendarAlarm.set(Calendar.MILLISECOND,10);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,951, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT); //3

        //alarmManager.setInexactRepeating(1, myCalendarAlarm.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent); //4
        alarmManager.setRepeating(1, myCalendarAlarm.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent); //

    }












    //ด้านล้างทั้งหมดลบทิ้งได้
    private void updatesumTABLEAndBroadcast() {

        DailyUpdateReceiver dailyUpdateReceiver = new DailyUpdateReceiver();
        dailyUpdateReceiver.stringsREAD0 = myManage.readAllMainTABLE_Full(0);
        dailyUpdateReceiver.stringsREAD1 = myManage.readAllMainTABLE_Full(1);  //Main_id
        dailyUpdateReceiver.stringsREAD2 = myManage.readAllMainTABLE_Full(2);
        dailyUpdateReceiver.stringsREAD3 = myManage.readAllMainTABLE_Full(3);
        dailyUpdateReceiver.stringsREAD4 = myManage.readAllMainTABLE_Full(4);
        dailyUpdateReceiver.stringsREAD5 = myManage.readAllMainTABLE_Full(5);
        dailyUpdateReceiver.stringsREAD6 = myManage.readAllMainTABLE_Full(6);
        dailyUpdateReceiver.stringsREAD7 = myManage.readAllMainTABLE_Full(7);
        dailyUpdateReceiver.stringsREAD8 = myManage.readAllMainTABLE_Full(8);
        dailyUpdateReceiver.stringsREAD9 = myManage.readAllMainTABLE_Full(9); //StartDate
        dailyUpdateReceiver.stringsREAD10 = myManage.readAllMainTABLE_Full(10);
        dailyUpdateReceiver.stringsREAD11 = myManage.readAllMainTABLE_Full(11); //prn
        dailyUpdateReceiver.stringsREAD12 = myManage.readAllMainTABLE_Full(12); //t1
        dailyUpdateReceiver.stringsREAD13 = myManage.readAllMainTABLE_Full(13);
        dailyUpdateReceiver.stringsREAD14 = myManage.readAllMainTABLE_Full(14);
        dailyUpdateReceiver.stringsREAD15 = myManage.readAllMainTABLE_Full(15);
        dailyUpdateReceiver.stringsREAD16 = myManage.readAllMainTABLE_Full(16);
        dailyUpdateReceiver.stringsREAD17 = myManage.readAllMainTABLE_Full(17);
        dailyUpdateReceiver.stringsREAD18 = myManage.readAllMainTABLE_Full(18);
        dailyUpdateReceiver.stringsREAD19 = myManage.readAllMainTABLE_Full(19); //T8
        dailyUpdateReceiver.stringsREAD20 = myManage.readAllMainTABLE_Full(20);  //TimeDateCanceled

        String[] strLast_updated = myManage.filter_userTABLE(5); //วันที่ในระบบล่าสุด
        Date dateLast_updated = myData.stringChangetoDateWithOutTime(strLast_updated[0]); //ในระบบล่าสุด type Date
        String currentDay = myData.currentDay(); //วันที่ของวันนี้
        Date dateInitial = myData.stringChangetoDateWithOutTime(currentDay); //วันที่ของวันนี้ Type Date


        Calendar calendarLast_updated = Calendar.getInstance();
        calendarLast_updated.setTime(dateLast_updated);
        calendarLast_updated.add(Calendar.DAY_OF_MONTH, 9); //วันที่ในระบบล่าสุด + 9 วัน
        Date dateRef = calendarLast_updated.getTime(); //วันที่ในระบบล่าสุด + 9 วัน type Date


        Calendar calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTime(dateInitial);  //วันนี้
        calendarCurrent.add(Calendar.DAY_OF_MONTH, 9);
        Date dateFinalProcess = calendarCurrent.getTime(); //วันที่วันนี้ในระบบล่าสุด + 9 วัน

        String[][] stringsReadAll_MainTABLE = {dailyUpdateReceiver.stringsREAD0,
                dailyUpdateReceiver.stringsREAD1, dailyUpdateReceiver.stringsREAD2,
                dailyUpdateReceiver.stringsREAD3, dailyUpdateReceiver.stringsREAD4,
                dailyUpdateReceiver.stringsREAD5, dailyUpdateReceiver.stringsREAD6,
                dailyUpdateReceiver.stringsREAD7, dailyUpdateReceiver.stringsREAD8,
                dailyUpdateReceiver.stringsREAD9, dailyUpdateReceiver.stringsREAD10,
                dailyUpdateReceiver.stringsREAD11, dailyUpdateReceiver.stringsREAD12,
                dailyUpdateReceiver.stringsREAD13, dailyUpdateReceiver.stringsREAD14,
                dailyUpdateReceiver.stringsREAD15, dailyUpdateReceiver.stringsREAD16,
                dailyUpdateReceiver.stringsREAD17, dailyUpdateReceiver.stringsREAD18,
                dailyUpdateReceiver.stringsREAD19, dailyUpdateReceiver.stringsREAD20};

        if (dateRef.compareTo(dateFinalProcess) == 0) {
            Toast.makeText(getBaseContext(), "ได้ค่าเท่ากันของ dateRef กับ dateFinalProcess", Toast.LENGTH_SHORT);
        }


        //(1/10/16)
        if (!stringsReadAll_MainTABLE[0][0].equals("")) {
            while (dateRef.compareTo(dateFinalProcess) < 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateRef);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                dateRef = calendar.getTime(); //เปลี่ยนค่าของ dateRef ให้มีค่าวันที่มากขึ้น 1 วัน

                String stringDateRef = myData.string_ddMMyyyy_ConvertedFromSpecificDate(dateRef); // ค่า Text ของวันที่ที่ต้องการเพิ่มเข้าใน sumTABLE


                //เริ่มยากจากตรงนี้!!!!

                for (int i = 0; i < stringsReadAll_MainTABLE[i].length; i++) {  // Loop เท่ากับจำนวนแถว
                    dailyUpdateReceiver.checkFinishDay = "N";
                    dailyUpdateReceiver.checkStartDay = "N";
                    dailyUpdateReceiver.checkWhich_Date_D = "N";
                    if (stringsReadAll_MainTABLE[20][i].equals("")) { //ยาถูก Cancel ไปหรือยัง!!!
                        Log.d("UpdatesumTABLE", "ค่าตำแหน่งที่ 20 ว่าง : " + i);
                        //ถ้ายายัง Active อยู่!!!


                        //currentDay = myData.currentDay();  //ค่าของวันนี้
                        //Date dateToday = myData.stringChangetoDateWithOutTime(currentDay);
                        Date dateStartDate = myData.stringChangetoDateWithOutTime(stringsReadAll_MainTABLE[9][i]); //ค่า Date ของ StartDate

                        // เช็ค FinishDate ว่ายังต้องทานต่อหรือไม่
                        if (!stringsReadAll_MainTABLE[10][i].equals("")) { //ถ้า FinishDate ไม่เท่ากับค่าว่าง แปลว่ามีวันที่ต้องหยุดทาน
                            Date dateFinishDate = myData.stringChangetoDateWithOutTime(stringsReadAll_MainTABLE[10][i]); //ค่า Date ของ FinishDate
                            Log.d("UpdatesumTABLE", "dateFinishDate  : " + dateFinishDate);
                            if (dateRef.compareTo(dateFinishDate) <= 0) {
                                dailyUpdateReceiver.checkFinishDay = "Y";
                            } else {
                                dailyUpdateReceiver.checkFinishDay = "N"; //แปลว่าเลยวันที่ต้องใช้มาแล้ว
                            }

                        } else {
                            dailyUpdateReceiver.checkFinishDay = "Y";
                        }

                        //เช็ค StartDate ว่าเริ่มหรือยัง
                        if (dateRef.compareTo(dateStartDate) >= 0) {
                            dailyUpdateReceiver.checkStartDay = "Y";
                        } else {
                            dailyUpdateReceiver.checkStartDay = "N";
                        }


                        //String current_DayOfWeek = myData.current_DayOfWeek();  //ค่าเป็นเลข ของ DayofWeek
                        //String current_DayOfMonth = myData.current_DayOfMonth(); //ค่าเป็นเลข ของ DayofMonth
                        String current_DayOfWeek = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)); //เอาค่าตัวเลขของวันประจำสัปดาห์จาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว
                        String current_DayOfMonth = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)); //เอาค่าตัวเลขของวันประจำเดือนจาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว
                        //เช็ค ว่าตาม Which_Date_D วันนี้ต้องทานหรือไม่!!!
                        String[] queryDay = stringsReadAll_MainTABLE[5][i].split(":");
                        String[] querySelectedDay = null;


                        if (!queryDay[0].equals("ED")) {
                            querySelectedDay = queryDay[1].split(",");
                            for (int w = 0; w < querySelectedDay.length; w++) {
                                Log.d("queryDay", "querySelectedDay[] : " + querySelectedDay[w]);
                                if (queryDay[0].equals("DOW")) {
                                    if (querySelectedDay[w].equals(current_DayOfWeek)) {
                                        dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                    }
                                }
                                if (queryDay[0].equals("DOM")) {
                                    if (querySelectedDay[w].equals(current_DayOfMonth)) {
                                        dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                    }
                                }
                            }

                        } else {  //ถ้าเป็น ED จะมี 0 1 2 3 4 5


                            String[] stringsDate_ED_Ref = myManage.filter_sumTABLE_finding_DateRef_by_MainId_idDESC(stringsReadAll_MainTABLE[1][i]); //เอาค่า Main_id มา
                            Date date_ED_Ref = myData.stringChangetoDateWithOutTime(stringsDate_ED_Ref[0]); //dateRef ก่อนนำไป add ค่Calendar calendarRef = Calendar.getInstance();

                            Calendar calendarRef = Calendar.getInstance();
                            calendarRef.setTime(date_ED_Ref);  //calendarRef ก่อนนำไป add ค่า

                            //เทียบ date ที่ add ค่าแล้ว กับ dateAfterProcess จากข้างบน

                            if (queryDay[1].equals("0")) {
                                dailyUpdateReceiver.checkWhich_Date_D = "Y";
                            } else if (queryDay[1].equals("1")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 2);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("2")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 3);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("3")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 4);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("4")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 5);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("5")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 6);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            }


                        }


                    } // stringsReadAll_MainTABLE[10][i].equals("")

                    //Check ค่าทั้งหมดแล้วว่าผ่าน ให้ทำการ add ค่าเข้ามาได้

                    if (dailyUpdateReceiver.checkWhich_Date_D.equals("Y")
                            && dailyUpdateReceiver.checkStartDay.equals("Y")
                            && dailyUpdateReceiver.checkFinishDay.equals("Y")) {
                        Log.d("UpdatesumTABLE", "ตำแหน่งที่ i addค่าเข้า SumTABLE ได้ : " + i);
                        Toast.makeText(getBaseContext(), "Addข้อมูลลง sumTABLE : (Y,Y,Y)", Toast.LENGTH_SHORT).show();

                        //เริ่ม addข้อมูลลง sumTABLE
                        //1 row ของ mainTABLE add ได้หลาย row ของ sumTABLE ตาม T1-T8 (column 12 - 19)
                        for (int x = 12; x <= 19; x++) {
                            if (!stringsReadAll_MainTABLE[x][i].equals("")) {
                                String stringMain_id = stringsReadAll_MainTABLE[0][i];  //Main_id
                                String stringTimeRef = stringsReadAll_MainTABLE[x][i];  //TimeRef ตำแหน่งต่างๆ
                                myManage.addValueToSumTable(stringMain_id, stringDateRef, stringTimeRef, "", "", "");
                                Log.d("UpdatesumTABLE", "addValueToSumTable : " + stringMain_id + " " + stringDateRef + " " + stringTimeRef);
                            }


                        }

                    } else {
                        Log.d("UpdatesumTABLE", "ตำแหน่งที่ i addค่าเข้า SumTABLE ไม่ได้ : " + i);
                    }


                } //first "For"


            } //while
            String[] strUser = myManage.filter_userTABLE(1);
            String strDate = myData.currentDay();
            myManage.update_Last_updated(strUser[0],strDate);
        }  //ถ้า ยังไม่มีค่าในนี้ให้ boardcast อย่างเดียว
        //เปลี่ยนตรงนี้...08/10/2559
        //เปลี่ยนตรงนี้...14/10/2559

        broadcastAndAddNotification(getBaseContext(),myData,myManage);

    }

    private void updatesumTABLEAndBroadcast2() {
        DailyUpdateReceiver dailyUpdateReceiver = new DailyUpdateReceiver();
        dailyUpdateReceiver.stringsREAD0 = myManage.readAllMainTABLE_Full(0);
        dailyUpdateReceiver.stringsREAD1 = myManage.readAllMainTABLE_Full(1);  //Main_id
        dailyUpdateReceiver.stringsREAD2 = myManage.readAllMainTABLE_Full(2);
        dailyUpdateReceiver.stringsREAD3 = myManage.readAllMainTABLE_Full(3);
        dailyUpdateReceiver.stringsREAD4 = myManage.readAllMainTABLE_Full(4);
        dailyUpdateReceiver.stringsREAD5 = myManage.readAllMainTABLE_Full(5);
        dailyUpdateReceiver.stringsREAD6 = myManage.readAllMainTABLE_Full(6);
        dailyUpdateReceiver.stringsREAD7 = myManage.readAllMainTABLE_Full(7);
        dailyUpdateReceiver.stringsREAD8 = myManage.readAllMainTABLE_Full(8);
        dailyUpdateReceiver.stringsREAD9 = myManage.readAllMainTABLE_Full(9); //StartDate
        dailyUpdateReceiver.stringsREAD10 = myManage.readAllMainTABLE_Full(10);
        dailyUpdateReceiver.stringsREAD11 = myManage.readAllMainTABLE_Full(11); //prn
        dailyUpdateReceiver.stringsREAD12 = myManage.readAllMainTABLE_Full(12); //t1
        dailyUpdateReceiver.stringsREAD13 = myManage.readAllMainTABLE_Full(13);
        dailyUpdateReceiver.stringsREAD14 = myManage.readAllMainTABLE_Full(14);
        dailyUpdateReceiver.stringsREAD15 = myManage.readAllMainTABLE_Full(15);
        dailyUpdateReceiver.stringsREAD16 = myManage.readAllMainTABLE_Full(16);
        dailyUpdateReceiver.stringsREAD17 = myManage.readAllMainTABLE_Full(17);
        dailyUpdateReceiver.stringsREAD18 = myManage.readAllMainTABLE_Full(18);
        dailyUpdateReceiver.stringsREAD19 = myManage.readAllMainTABLE_Full(19); //T8
        dailyUpdateReceiver.stringsREAD20 = myManage.readAllMainTABLE_Full(20);  //TimeDateCanceled

        String[] strLast_updated = myManage.filter_userTABLE(5); //วันที่ในระบบล่าสุด
        Date dateLast_updated = myData.stringChangetoDateWithOutTime(strLast_updated[0]); //ในระบบล่าสุด type Date
        String currentDay = myData.currentDay(); //วันที่ของวันนี้
        Date dateInitial = myData.stringChangetoDateWithOutTime(currentDay); //วันที่ของวันนี้ Type Date


        Calendar calendarLast_updated = Calendar.getInstance();
        calendarLast_updated.setTime(dateLast_updated);
        calendarLast_updated.add(Calendar.DAY_OF_MONTH, 9); //วันที่ในระบบล่าสุด + 9 วัน
        Date dateRef = calendarLast_updated.getTime(); //วันที่ในระบบล่าสุด + 9 วัน type Date


        Calendar calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTime(dateInitial);  //วันนี้
        calendarCurrent.add(Calendar.DAY_OF_MONTH, 9);
        Date dateFinalProcess = calendarCurrent.getTime(); //วันที่วันนี้ในระบบล่าสุด + 9 วัน

        String[][] stringsReadAll_MainTABLE = {dailyUpdateReceiver.stringsREAD0,
                dailyUpdateReceiver.stringsREAD1, dailyUpdateReceiver.stringsREAD2,
                dailyUpdateReceiver.stringsREAD3, dailyUpdateReceiver.stringsREAD4,
                dailyUpdateReceiver.stringsREAD5, dailyUpdateReceiver.stringsREAD6,
                dailyUpdateReceiver.stringsREAD7, dailyUpdateReceiver.stringsREAD8,
                dailyUpdateReceiver.stringsREAD9, dailyUpdateReceiver.stringsREAD10,
                dailyUpdateReceiver.stringsREAD11, dailyUpdateReceiver.stringsREAD12,
                dailyUpdateReceiver.stringsREAD13, dailyUpdateReceiver.stringsREAD14,
                dailyUpdateReceiver.stringsREAD15, dailyUpdateReceiver.stringsREAD16,
                dailyUpdateReceiver.stringsREAD17, dailyUpdateReceiver.stringsREAD18,
                dailyUpdateReceiver.stringsREAD19, dailyUpdateReceiver.stringsREAD20};

        if (dateRef.compareTo(dateFinalProcess) == 0) {
            Toast.makeText(getBaseContext(), "ได้ค่าเท่ากันของ dateRef กับ dateFinalProcess", Toast.LENGTH_SHORT);
        }


        //(1/10/16)
        if (!stringsReadAll_MainTABLE[0][0].equals("")) {
            while (dateRef.compareTo(dateFinalProcess) < 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateRef);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                dateRef = calendar.getTime(); //เปลี่ยนค่าของ dateRef ให้มีค่าวันที่มากขึ้น 1 วัน

                String stringDateRef = myData.string_ddMMyyyy_ConvertedFromSpecificDate(dateRef); // ค่า Text ของวันที่ที่ต้องการเพิ่มเข้าใน sumTABLE


                //เริ่มยากจากตรงนี้!!!!

                for (int i = 0; i < stringsReadAll_MainTABLE[i].length; i++) {  // Loop เท่ากับจำนวนแถว
                    dailyUpdateReceiver.checkFinishDay = "N";
                    dailyUpdateReceiver.checkStartDay = "N";
                    dailyUpdateReceiver.checkWhich_Date_D = "N";
                    if (stringsReadAll_MainTABLE[20][i].equals("")) { //ยาถูก Cancel ไปหรือยัง!!!
                        Log.d("UpdatesumTABLE", "ค่าตำแหน่งที่ 20 ว่าง : " + i);
                        //ถ้ายายัง Active อยู่!!!


                        //currentDay = myData.currentDay();  //ค่าของวันนี้
                        //Date dateToday = myData.stringChangetoDateWithOutTime(currentDay);
                        Date dateStartDate = myData.stringChangetoDateWithOutTime(stringsReadAll_MainTABLE[9][i]); //ค่า Date ของ StartDate

                        // เช็ค FinishDate ว่ายังต้องทานต่อหรือไม่
                        if (!stringsReadAll_MainTABLE[10][i].equals("")) { //ถ้า FinishDate ไม่เท่ากับค่าว่าง แปลว่ามีวันที่ต้องหยุดทาน
                            Date dateFinishDate = myData.stringChangetoDateWithOutTime(stringsReadAll_MainTABLE[10][i]); //ค่า Date ของ FinishDate
                            Log.d("UpdatesumTABLE", "dateFinishDate  : " + dateFinishDate);
                            if (dateRef.compareTo(dateFinishDate) <= 0) {
                                dailyUpdateReceiver.checkFinishDay = "Y";
                            } else {
                                dailyUpdateReceiver.checkFinishDay = "N"; //แปลว่าเลยวันที่ต้องใช้มาแล้ว
                            }

                        } else {
                            dailyUpdateReceiver.checkFinishDay = "Y";
                        }

                        //เช็ค StartDate ว่าเริ่มหรือยัง
                        if (dateRef.compareTo(dateStartDate) >= 0) {
                            dailyUpdateReceiver.checkStartDay = "Y";
                        } else {
                            dailyUpdateReceiver.checkStartDay = "N";
                        }


                        //String current_DayOfWeek = myData.current_DayOfWeek();  //ค่าเป็นเลข ของ DayofWeek
                        //String current_DayOfMonth = myData.current_DayOfMonth(); //ค่าเป็นเลข ของ DayofMonth
                        String current_DayOfWeek = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)); //เอาค่าตัวเลขของวันประจำสัปดาห์จาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว
                        String current_DayOfMonth = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)); //เอาค่าตัวเลขของวันประจำเดือนจาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว
                        //เช็ค ว่าตาม Which_Date_D วันนี้ต้องทานหรือไม่!!!
                        String[] queryDay = stringsReadAll_MainTABLE[5][i].split(":");
                        String[] querySelectedDay = null;


                        if (!queryDay[0].equals("ED")) {
                            querySelectedDay = queryDay[1].split(",");
                            for (int w = 0; w < querySelectedDay.length; w++) {
                                Log.d("queryDay", "querySelectedDay[] : " + querySelectedDay[w]);
                                if (queryDay[0].equals("DOW")) {
                                    if (querySelectedDay[w].equals(current_DayOfWeek)) {
                                        dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                    }
                                }
                                if (queryDay[0].equals("DOM")) {
                                    if (querySelectedDay[w].equals(current_DayOfMonth)) {
                                        dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                    }
                                }
                            }

                        } else {  //ถ้าเป็น ED จะมี 0 1 2 3 4 5


                            String[] stringsDate_ED_Ref = myManage.filter_sumTABLE_finding_DateRef_by_MainId_idDESC(stringsReadAll_MainTABLE[1][i]); //เอาค่า Main_id มา
                            Date date_ED_Ref = myData.stringChangetoDateWithOutTime(stringsDate_ED_Ref[0]); //dateRef ก่อนนำไป add ค่Calendar calendarRef = Calendar.getInstance();

                            Calendar calendarRef = Calendar.getInstance();
                            calendarRef.setTime(date_ED_Ref);  //calendarRef ก่อนนำไป add ค่า

                            //เทียบ date ที่ add ค่าแล้ว กับ dateAfterProcess จากข้างบน

                            if (queryDay[1].equals("0")) {
                                dailyUpdateReceiver.checkWhich_Date_D = "Y";
                            } else if (queryDay[1].equals("1")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 2);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("2")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 3);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("3")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 4);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("4")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 5);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("5")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 6);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            }


                        }


                    } // stringsReadAll_MainTABLE[10][i].equals("")

                    //Check ค่าทั้งหมดแล้วว่าผ่าน ให้ทำการ add ค่าเข้ามาได้

                    if (dailyUpdateReceiver.checkWhich_Date_D.equals("Y")
                            && dailyUpdateReceiver.checkStartDay.equals("Y")
                            && dailyUpdateReceiver.checkFinishDay.equals("Y")) {
                        Log.d("UpdatesumTABLE", "ตำแหน่งที่ i addค่าเข้า SumTABLE ได้ : " + i);
                        Toast.makeText(getBaseContext(), "Addข้อมูลลง sumTABLE : (Y,Y,Y)", Toast.LENGTH_SHORT).show();

                        //เริ่ม addข้อมูลลง sumTABLE
                        //1 row ของ mainTABLE add ได้หลาย row ของ sumTABLE ตาม T1-T8 (column 12 - 19)
                        for (int x = 12; x <= 19; x++) {
                            if (!stringsReadAll_MainTABLE[x][i].equals("")) {
                                String stringMain_id = stringsReadAll_MainTABLE[0][i];  //Main_id
                                String stringTimeRef = stringsReadAll_MainTABLE[x][i];  //TimeRef ตำแหน่งต่างๆ
                                myManage.addValueToSumTable(stringMain_id, stringDateRef, stringTimeRef, "", "", "");
                                Log.d("UpdatesumTABLE", "addValueToSumTable : " + stringMain_id + " " + stringDateRef + " " + stringTimeRef);
                            }


                        }

                    } else {
                        Log.d("UpdatesumTABLE", "ตำแหน่งที่ i addค่าเข้า SumTABLE ไม่ได้ : " + i);
                    }


                } //first "For"


            } //while
            String[] strUser = myManage.filter_userTABLE(1);
            String strDate = myData.currentDay();
            myManage.update_Last_updated(strUser[0],strDate);
        }  //ถ้า ยังไม่มีค่าในนี้ให้ boardcast อย่างเดียว
        //เปลี่ยนตรงนี้...08/10/2559
        //เปลี่ยนตรงนี้...14/10/2559









        //broadcastAndAddNotification(getBaseContext());
    }

    private void updatesumTABLEAndBroadcast1() {


        DailyUpdateReceiver dailyUpdateReceiver = new DailyUpdateReceiver();
        dailyUpdateReceiver.stringsREAD0 = myManage.readAllMainTABLE_Full(0);
        dailyUpdateReceiver.stringsREAD1 = myManage.readAllMainTABLE_Full(1);  //Main_id
        dailyUpdateReceiver.stringsREAD2 = myManage.readAllMainTABLE_Full(2);
        dailyUpdateReceiver.stringsREAD3 = myManage.readAllMainTABLE_Full(3);
        dailyUpdateReceiver.stringsREAD4 = myManage.readAllMainTABLE_Full(4);
        dailyUpdateReceiver.stringsREAD5 = myManage.readAllMainTABLE_Full(5);
        dailyUpdateReceiver.stringsREAD6 = myManage.readAllMainTABLE_Full(6);
        dailyUpdateReceiver.stringsREAD7 = myManage.readAllMainTABLE_Full(7);
        dailyUpdateReceiver.stringsREAD8 = myManage.readAllMainTABLE_Full(8);
        dailyUpdateReceiver.stringsREAD9 = myManage.readAllMainTABLE_Full(9); //StartDate
        dailyUpdateReceiver.stringsREAD10 = myManage.readAllMainTABLE_Full(10);
        dailyUpdateReceiver.stringsREAD11 = myManage.readAllMainTABLE_Full(11); //prn
        dailyUpdateReceiver.stringsREAD12 = myManage.readAllMainTABLE_Full(12); //t1
        dailyUpdateReceiver.stringsREAD13 = myManage.readAllMainTABLE_Full(13);
        dailyUpdateReceiver.stringsREAD14 = myManage.readAllMainTABLE_Full(14);
        dailyUpdateReceiver.stringsREAD15 = myManage.readAllMainTABLE_Full(15);
        dailyUpdateReceiver.stringsREAD16 = myManage.readAllMainTABLE_Full(16);
        dailyUpdateReceiver.stringsREAD17 = myManage.readAllMainTABLE_Full(17);
        dailyUpdateReceiver.stringsREAD18 = myManage.readAllMainTABLE_Full(18);
        dailyUpdateReceiver.stringsREAD19 = myManage.readAllMainTABLE_Full(19); //T8
        dailyUpdateReceiver.stringsREAD20 = myManage.readAllMainTABLE_Full(20);  //TimeDateCanceled

        String[] strLast_updated = myManage.filter_userTABLE(5); //วันที่ในระบบล่าสุด
        Date dateLast_updated = myData.stringChangetoDateWithOutTime(strLast_updated[0]); //ในระบบล่าสุด type Date
        String currentDay = myData.currentDay(); //วันที่ของวันนี้
        Date dateInitial = myData.stringChangetoDateWithOutTime(currentDay); //วันที่ของวันนี้ Type Date


        Calendar calendarLast_updated = Calendar.getInstance();
        calendarLast_updated.setTime(dateLast_updated);
        calendarLast_updated.add(Calendar.DAY_OF_MONTH, 9); //วันที่ในระบบล่าสุด + 9 วัน
        Date dateRef = calendarLast_updated.getTime(); //วันที่ในระบบล่าสุด + 9 วัน type Date


        Calendar calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTime(dateInitial);  //วันนี้
        calendarCurrent.add(Calendar.DAY_OF_MONTH, 9);
        Date dateFinalProcess = calendarCurrent.getTime(); //วันที่วันนี้ในระบบล่าสุด + 9 วัน


            String[][] stringsReadAll_MainTABLE = {dailyUpdateReceiver.stringsREAD0,
                    dailyUpdateReceiver.stringsREAD1, dailyUpdateReceiver.stringsREAD2,
                    dailyUpdateReceiver.stringsREAD3, dailyUpdateReceiver.stringsREAD4,
                    dailyUpdateReceiver.stringsREAD5, dailyUpdateReceiver.stringsREAD6,
                    dailyUpdateReceiver.stringsREAD7, dailyUpdateReceiver.stringsREAD8,
                    dailyUpdateReceiver.stringsREAD9, dailyUpdateReceiver.stringsREAD10,
                    dailyUpdateReceiver.stringsREAD11, dailyUpdateReceiver.stringsREAD12,
                    dailyUpdateReceiver.stringsREAD13, dailyUpdateReceiver.stringsREAD14,
                    dailyUpdateReceiver.stringsREAD15, dailyUpdateReceiver.stringsREAD16,
                    dailyUpdateReceiver.stringsREAD17, dailyUpdateReceiver.stringsREAD18,
                    dailyUpdateReceiver.stringsREAD19, dailyUpdateReceiver.stringsREAD20};


            //(1/10/16)
        if (!stringsReadAll_MainTABLE[0][0].equals("")) {
            do {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateRef);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                dateRef = calendar.getTime(); //เปลี่ยนค่าของ dateRef ให้มีค่าวันที่มากขึ้น 1 วัน

                String stringDateRef = myData.string_ddMMyyyy_ConvertedFromSpecificDate(dateRef); // ค่า Text ของวันที่ที่ต้องการเพิ่มเข้าใน sumTABLE


                //เริ่มยากจากตรงนี้!!!!

                for (int i = 0; i < stringsReadAll_MainTABLE[i].length; i++) {  // Loop เท่ากับจำนวนแถว
                    dailyUpdateReceiver.checkFinishDay = "N";
                    dailyUpdateReceiver.checkStartDay = "N";
                    dailyUpdateReceiver.checkWhich_Date_D = "N";
                    if (stringsReadAll_MainTABLE[20][i].equals("")) { //ยาถูก Cancel ไปหรือยัง!!!
                        Log.d("UpdatesumTABLE", "ค่าตำแหน่งที่ 20 ว่าง : " + i);
                        //ถ้ายายัง Active อยู่!!!


                        //currentDay = myData.currentDay();  //ค่าของวันนี้
                        //Date dateToday = myData.stringChangetoDateWithOutTime(currentDay);
                        Date dateStartDate = myData.stringChangetoDateWithOutTime(stringsReadAll_MainTABLE[9][i]); //ค่า Date ของ StartDate

                        // เช็ค FinishDate ว่ายังต้องทานต่อหรือไม่
                        if (!stringsReadAll_MainTABLE[10][i].equals("")) { //ถ้า FinishDate ไม่เท่ากับค่าว่าง แปลว่ามีวันที่ต้องหยุดทาน
                            Date dateFinishDate = myData.stringChangetoDateWithOutTime(stringsReadAll_MainTABLE[10][i]); //ค่า Date ของ FinishDate
                            Log.d("UpdatesumTABLE", "dateFinishDate  : " + dateFinishDate);
                            if (dateRef.compareTo(dateFinishDate) <= 0) {
                                dailyUpdateReceiver.checkFinishDay = "Y";
                            } else {
                                dailyUpdateReceiver.checkFinishDay = "N"; //แปลว่าเลยวันที่ต้องใช้มาแล้ว
                            }

                        } else {
                            dailyUpdateReceiver.checkFinishDay = "Y";
                        }

                        //เช็ค StartDate ว่าเริ่มหรือยัง
                        if (dateRef.compareTo(dateStartDate) >= 0) {
                            dailyUpdateReceiver.checkStartDay = "Y";
                        } else {
                            dailyUpdateReceiver.checkStartDay = "N";
                        }


                        //String current_DayOfWeek = myData.current_DayOfWeek();  //ค่าเป็นเลข ของ DayofWeek
                        //String current_DayOfMonth = myData.current_DayOfMonth(); //ค่าเป็นเลข ของ DayofMonth
                        String current_DayOfWeek = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)); //เอาค่าตัวเลขของวันประจำสัปดาห์จาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว
                        String current_DayOfMonth = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)); //เอาค่าตัวเลขของวันประจำเดือนจาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว
                        //เช็ค ว่าตาม Which_Date_D วันนี้ต้องทานหรือไม่!!!
                        String[] queryDay = stringsReadAll_MainTABLE[5][i].split(":");
                        String[] querySelectedDay = null;


                        if (!queryDay[0].equals("ED")) {
                            querySelectedDay = queryDay[1].split(",");
                            for (int w = 0; w < querySelectedDay.length; w++) {
                                Log.d("queryDay", "querySelectedDay[] : " + querySelectedDay[w]);
                                if (queryDay[0].equals("DOW")) {
                                    if (querySelectedDay[w].equals(current_DayOfWeek)) {
                                        dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                    }
                                }
                                if (queryDay[0].equals("DOM")) {
                                    if (querySelectedDay[w].equals(current_DayOfMonth)) {
                                        dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                    }
                                }
                            }

                        } else {  //ถ้าเป็น ED จะมี 0 1 2 3 4 5


                            String[] stringsDate_ED_Ref = myManage.filter_sumTABLE_finding_DateRef_by_MainId_idDESC(stringsReadAll_MainTABLE[1][i]); //เอาค่า Main_id มา
                            Date date_ED_Ref = myData.stringChangetoDateWithOutTime(stringsDate_ED_Ref[0]); //dateRef ก่อนนำไป add ค่Calendar calendarRef = Calendar.getInstance();

                            Calendar calendarRef = Calendar.getInstance();
                            calendarRef.setTime(date_ED_Ref);  //calendarRef ก่อนนำไป add ค่า

                            //เทียบ date ที่ add ค่าแล้ว กับ dateAfterProcess จากข้างบน

                            if (queryDay[1].equals("0")) {
                                dailyUpdateReceiver.checkWhich_Date_D = "Y";
                            } else if (queryDay[1].equals("1")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 2);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("2")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 3);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("3")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 4);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("4")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 5);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("5")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 6);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            }


                        }


                    } // stringsReadAll_MainTABLE[10][i].equals("")

                    //Check ค่าทั้งหมดแล้วว่าผ่าน ให้ทำการ add ค่าเข้ามาได้

                    if (dailyUpdateReceiver.checkWhich_Date_D.equals("Y")
                            && dailyUpdateReceiver.checkStartDay.equals("Y")
                            && dailyUpdateReceiver.checkFinishDay.equals("Y")) {
                        Log.d("UpdatesumTABLE", "ตำแหน่งที่ i addค่าเข้า SumTABLE ได้ : " + i);
                        Toast.makeText(getBaseContext(), "Addข้อมูลลง sumTABLE : (Y,Y,Y)", Toast.LENGTH_LONG).show();

                        //เริ่ม addข้อมูลลง sumTABLE
                        //1 row ของ mainTABLE add ได้หลาย row ของ sumTABLE ตาม T1-T8 (column 12 - 19)
                        for (int x = 12; x <= 19; x++) {
                            if (!stringsReadAll_MainTABLE[x][i].equals("")) {
                                String stringMain_id = stringsReadAll_MainTABLE[0][i];  //Main_id
                                String stringTimeRef = stringsReadAll_MainTABLE[x][i];  //TimeRef ตำแหน่งต่างๆ
                                myManage.addValueToSumTable(stringMain_id, stringDateRef, stringTimeRef, "", "", "");
                                Log.d("UpdatesumTABLE", "addValueToSumTable : " + stringMain_id + " " + stringDateRef + " " + stringTimeRef);
                            }


                        }

                    } else {
                        Log.d("UpdatesumTABLE", "ตำแหน่งที่ i addค่าเข้า SumTABLE ไม่ได้ : " + i);
                    }


                } //first "For"


            } while (dateRef.compareTo(dateFinalProcess) < 0);
        }  //ถ้า ยังไม่มีค่าในนี้ให้ boardcast อย่างเดียว




            //เปลี่ยนตรงนี้...08/10/2559


        broadcastAndAddNotification(getBaseContext(),myData,myManage);
        /*
        Calendar calendar = Calendar.getInstance();  //ค้นหาเวลาในเครื่อง
        Calendar myCalendar1 = (Calendar) calendar.clone(); //clone เวลาในเครื่องเข้ามาใช้
        myCalendar1.add(Calendar.SECOND,1);

        Intent alertIntent = new Intent(getBaseContext(), DailyUpdateReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1000, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(1,myCalendar1.getTimeInMillis(),pendingIntent);
        */

    }

    public void broadcastAndAddNotification(Context context,MyData myData,MyManage myManage) {

        Intent alertIntent = new Intent(context, AlarmReceiver.class); //1

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE); //2


        Random random = new Random();
        int iRandom = random.nextInt(10000);

        for(int a = 0; a <= 200 ; a++) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, a, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT); //3
            pendingIntent.cancel();
        }
        int a = 0;

        for(int i = 0; i < 3; i++) {
            String sDate = myData.currentDay();
            Date dDate = myData.stringChangetoDateWithOutTime(sDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dDate);
            calendar.add(Calendar.DAY_OF_MONTH,i);
            dDate = calendar.getTime();
            String sSpecificDate = myData.string_ddMMyyyy_ConvertedFromSpecificDate(dDate);

            String sCurrentDateTime = myData.currentDateTime_Withoutsecond();
            Date dCurrentDateTime = myData.stringChangetoDate(sCurrentDateTime);




            Log.d("14/10/2559", "1 : sSpecificDate : " + sSpecificDate);

            String[] strings_sumTABLE_id = myManage.filter_sumTABLE__by_Date(sSpecificDate, 0);
            String[] strings_sumTABLE_MainId = myManage.filter_sumTABLE__by_Date(sSpecificDate, 1);
            //String[] strings_sumTABLE_DateRef = myManage.filter_sumTABLE__by_Date(sSpecificDate, 2);
            String[] strings_sumTABLE_TimeRef = myManage.filter_sumTABLE__by_Date(sSpecificDate, 3);
            String[] strings_sumTABLE_DateCheck = myManage.filter_sumTABLE__by_Date(sSpecificDate, 4);
            String[] strings_sumTABLE_TimeCheck = myManage.filter_sumTABLE__by_Date(sSpecificDate, 5);
            String[] strings_sumTABLE_SkipHold = myManage.filter_sumTABLE__by_Date(sSpecificDate, 6);

            if (!strings_sumTABLE_id[0].equals("")) {
                Log.d("14/10/2559", "1 : เข้า if1 : ");
                //String sCurrentTime = myData.currentTime_Minus();
                //Date dCurrentTime = myData.stringChangetoTime_Minute(sCurrentTime);

                for (int x = 0; x < strings_sumTABLE_id.length; x++) {
                    if (strings_sumTABLE_DateCheck[x].equals("") && strings_sumTABLE_SkipHold[x].equals("")) {
                        Log.d("14/10/2559", "1 : เข้า if2 และ for");

                        //เพิ่ม
                        String stringDateTime = sSpecificDate + " " + strings_sumTABLE_TimeRef[x];
                        Date d_sumTABLE_DateTimeRef = myData.stringChangetoDate(stringDateTime);

                        //Date d_sumTABLE_TimeRef = myData.stringChangetoTime_Minute(strings_sumTABLE_TimeRef[x]);

                        if (dCurrentDateTime.compareTo(d_sumTABLE_DateTimeRef) <= 0) {
                            //เริ่ม boardcast

                            Calendar calendarCurrent = Calendar.getInstance();
                            Calendar myCalendarAlarm = (Calendar) calendarCurrent.clone(); //clone เวลาในเครื่องเข้ามาใช้

                            String stringAlarm = sSpecificDate + " " + strings_sumTABLE_TimeRef[x];

                            Log.d("14/10/2559", "2 : stringAlarm : " + stringAlarm);
                            Date dAlarm = myData.stringChangetoDate(stringAlarm);
                            myCalendarAlarm.setTime(dAlarm);


                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, a, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            a = a + 1;

                            alarmManager.set(1, myCalendarAlarm.getTimeInMillis(), pendingIntent); //4
                            Log.d("14/10/2559", "3 : เข้า alarmManager");


                        }

                    }
                }


            }

        }




    }

    public void broadcastAndAddNotification1(Context context) {

        Calendar calendar = Calendar.getInstance();  //ค้นหาเวลาในเครื่อง
        Calendar myCalendar1 = (Calendar) calendar.clone(); //clone เวลาในเครื่องเข้ามาใช้
        myCalendar1.add(Calendar.SECOND,1);

        Intent alertIntent = new Intent(context, DailyUpdateReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1000, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(1,myCalendar1.getTimeInMillis(),pendingIntent);
        Toast.makeText(context, "....broadcast สำเร็จ....", Toast.LENGTH_LONG).show();



    }


    private void updatesumTABLE00_New2() {



        Calendar calendar = Calendar.getInstance();  //ค้นหาเวลาในเครื่อง
        Calendar myCalendar1 = (Calendar) calendar.clone(); //clone เวลาในเครื่องเข้ามาใช้
        myCalendar1.add(Calendar.SECOND,1);

        Intent alertIntent = new Intent(getBaseContext(), DailyUpdateReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1000, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(1,myCalendar1.getTimeInMillis(),pendingIntent);


    }

    private void updatesumTABLE00_New() {

        DailyUpdateReceiver dailyUpdateReceiver = new DailyUpdateReceiver();
        dailyUpdateReceiver.stringsREAD0 = myManage.readAllMainTABLE_Full(0);
        dailyUpdateReceiver.stringsREAD1 = myManage.readAllMainTABLE_Full(1);  //Main_id
        dailyUpdateReceiver.stringsREAD2 = myManage.readAllMainTABLE_Full(2);
        dailyUpdateReceiver.stringsREAD3 = myManage.readAllMainTABLE_Full(3);
        dailyUpdateReceiver.stringsREAD4 = myManage.readAllMainTABLE_Full(4);
        dailyUpdateReceiver.stringsREAD5 = myManage.readAllMainTABLE_Full(5);
        dailyUpdateReceiver.stringsREAD6 = myManage.readAllMainTABLE_Full(6);
        dailyUpdateReceiver.stringsREAD7 = myManage.readAllMainTABLE_Full(7);
        dailyUpdateReceiver.stringsREAD8 = myManage.readAllMainTABLE_Full(8);
        dailyUpdateReceiver.stringsREAD9 = myManage.readAllMainTABLE_Full(9); //StartDate
        dailyUpdateReceiver.stringsREAD10 = myManage.readAllMainTABLE_Full(10);
        dailyUpdateReceiver.stringsREAD11 = myManage.readAllMainTABLE_Full(11); //prn
        dailyUpdateReceiver.stringsREAD12 = myManage.readAllMainTABLE_Full(12); //t1
        dailyUpdateReceiver.stringsREAD13 = myManage.readAllMainTABLE_Full(13);
        dailyUpdateReceiver.stringsREAD14 = myManage.readAllMainTABLE_Full(14);
        dailyUpdateReceiver.stringsREAD15 = myManage.readAllMainTABLE_Full(15);
        dailyUpdateReceiver.stringsREAD16 = myManage.readAllMainTABLE_Full(16);
        dailyUpdateReceiver.stringsREAD17 = myManage.readAllMainTABLE_Full(17);
        dailyUpdateReceiver.stringsREAD18 = myManage.readAllMainTABLE_Full(18);
        dailyUpdateReceiver.stringsREAD19 = myManage.readAllMainTABLE_Full(19); //T8
        dailyUpdateReceiver.stringsREAD20 = myManage.readAllMainTABLE_Full(20);  //TimeDateCanceled

        String[] strLast_updated = myManage.filter_userTABLE(5); //วันที่ในระบบล่าสุด
        Date dateLast_updated = myData.stringChangetoDateWithOutTime(strLast_updated[0]);
        String currentDay = myData.currentDay();
        Date dateInitial = myData.stringChangetoDateWithOutTime(currentDay);
        Calendar calendarLast_updated = Calendar.getInstance();
        calendarLast_updated.setTime(dateLast_updated);
        calendarLast_updated.add(Calendar.DAY_OF_MONTH, 9);
        Date dateRef = calendarLast_updated.getTime();


        Calendar calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTime(dateInitial);
        calendarCurrent.add(Calendar.DAY_OF_MONTH, 9);
        Date dateFinalProcess = calendarCurrent.getTime();


        String strCheckPRN = "Y";
        for (int i = 0; i < dailyUpdateReceiver.stringsREAD11.length; i++) {
            if (dailyUpdateReceiver.stringsREAD11[i].equals("N")) {
                strCheckPRN = "N";
            }
        }
        //มีถึงอีก 9 วันข้างหน้าแล้วหรือยัง
        String strDateRef = "N";
        if (dateRef.compareTo(dateFinalProcess) >= 0) {
            strDateRef = "Y";
        }


        if (dailyUpdateReceiver.stringsREAD0[0].equals("")) {
            Log.d("UpdatesumTABLE", "ไม่มียาใน mainTABLE : ค่าว่าง ยุติการ UpdateReceiver");
            Toast.makeText(getBaseContext(), "ไม่มียาใน mainTABLE : ค่าว่าง ยุติการ UpdateReceiver", Toast.LENGTH_LONG).show();
            return;
        } else if (strCheckPRN.equals("Y")) {
            Log.d("UpdatesumTABLE", "ยาใน mainTABLE มีแต่ยา PRN : ยุติการ UpdateReceiver");
            Toast.makeText(getBaseContext(), "ยาใน mainTABLE มีแต่ยา PRN :ยุติการ UpdateReceiver", Toast.LENGTH_LONG).show();
            return;
        }
        //ถ้าจะ Test การเอาเข้าให้เอา else if อันนี้ออกไป
        else if (strDateRef.equals("Y")) {
            Log.d("UpdatesumTABLE", "มีค่าวันนี้ใน sumTABLE ของวันนี้แล้ว : ยุติการ UpdateReceiver");
            Toast.makeText(getBaseContext(), "มีค่าวันนี้ใน sumTABLE ของวันนี้แล้ว : ยุติการ UpdateReceiver", Toast.LENGTH_LONG).show();
            return;
        } else {

            String[][] stringsReadAll_MainTABLE = {dailyUpdateReceiver.stringsREAD0,
                    dailyUpdateReceiver.stringsREAD1, dailyUpdateReceiver.stringsREAD2,
                    dailyUpdateReceiver.stringsREAD3, dailyUpdateReceiver.stringsREAD4,
                    dailyUpdateReceiver.stringsREAD5, dailyUpdateReceiver.stringsREAD6,
                    dailyUpdateReceiver.stringsREAD7, dailyUpdateReceiver.stringsREAD8,
                    dailyUpdateReceiver.stringsREAD9, dailyUpdateReceiver.stringsREAD10,
                    dailyUpdateReceiver.stringsREAD11, dailyUpdateReceiver.stringsREAD12,
                    dailyUpdateReceiver.stringsREAD13, dailyUpdateReceiver.stringsREAD14,
                    dailyUpdateReceiver.stringsREAD15, dailyUpdateReceiver.stringsREAD16,
                    dailyUpdateReceiver.stringsREAD17, dailyUpdateReceiver.stringsREAD18,
                    dailyUpdateReceiver.stringsREAD19, dailyUpdateReceiver.stringsREAD20};


            //(1/10/16)
            do {
                //String stringDateRef = myData.string_ddMMyyyy_ConvertedFromSpecificDate(dateRef);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateRef);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                dateRef = calendar.getTime(); //เปลี่ยนค่าของ dateRef ให้มีค่าวันที่มากขึ้น 1 วัน

                String stringDateRef = myData.string_ddMMyyyy_ConvertedFromSpecificDate(dateRef); // ค่า Text ของวันที่ที่ต้องการเพิ่มเข้าใน sumTABLE


                //เริ่มยากจากตรงนี้!!!!
                for (int i = 0; i < stringsReadAll_MainTABLE[i].length; i++) {  // Loop เท่ากับจำนวนแถว
                    dailyUpdateReceiver.checkFinishDay = "N";
                    dailyUpdateReceiver.checkStartDay = "N";
                    dailyUpdateReceiver.checkWhich_Date_D = "N";
                    if (stringsReadAll_MainTABLE[20][i].equals("")) { //ยาถูก Cancel ไปหรือยัง!!!
                        Log.d("UpdatesumTABLE", "ค่าตำแหน่งที่ 20 ว่าง : " + i);
                        //ถ้ายายัง Active อยู่!!!


                        //currentDay = myData.currentDay();  //ค่าของวันนี้
                        //Date dateToday = myData.stringChangetoDateWithOutTime(currentDay);
                        Date dateStartDate = myData.stringChangetoDateWithOutTime(stringsReadAll_MainTABLE[9][i]); //ค่า Date ของ StartDate

                        // เช็ค FinishDate ว่ายังต้องทานต่อหรือไม่
                        if (!stringsReadAll_MainTABLE[10][i].equals("")) { //ถ้า FinishDate ไม่เท่ากับค่าว่าง แปลว่ามีวันที่ต้องหยุดทาน
                            Date dateFinishDate = myData.stringChangetoDateWithOutTime(stringsReadAll_MainTABLE[10][i]); //ค่า Date ของ FinishDate
                            Log.d("UpdatesumTABLE", "dateFinishDate  : " + dateFinishDate);
                            if (dateRef.compareTo(dateFinishDate) <= 0) {
                                dailyUpdateReceiver.checkFinishDay = "Y";
                            } else {
                                dailyUpdateReceiver.checkFinishDay = "N"; //แปลว่าเลยวันที่ต้องใช้มาแล้ว
                            }

                        } else {
                            dailyUpdateReceiver.checkFinishDay = "Y";
                        }

                        //เช็ค StartDate ว่าเริ่มหรือยัง
                        if (dateRef.compareTo(dateStartDate) >= 0) {
                            dailyUpdateReceiver.checkStartDay = "Y";
                        } else {
                            dailyUpdateReceiver.checkStartDay = "N";
                        }


                        //String current_DayOfWeek = myData.current_DayOfWeek();  //ค่าเป็นเลข ของ DayofWeek
                        //String current_DayOfMonth = myData.current_DayOfMonth(); //ค่าเป็นเลข ของ DayofMonth
                        String current_DayOfWeek = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)); //เอาค่าตัวเลขของวันประจำสัปดาห์จาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว
                        String current_DayOfMonth = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)); //เอาค่าตัวเลขของวันประจำเดือนจาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว
                        //เช็ค ว่าตาม Which_Date_D วันนี้ต้องทานหรือไม่!!!
                        String[] queryDay = stringsReadAll_MainTABLE[5][i].split(":");
                        String[] querySelectedDay = null;


                        if (!queryDay[0].equals("ED")) {
                            querySelectedDay = queryDay[1].split(",");
                            for (int w = 0; w < querySelectedDay.length; w++) {
                                Log.d("queryDay", "querySelectedDay[] : " + querySelectedDay[w]);
                                if (queryDay[0].equals("DOW")) {
                                    if (querySelectedDay[w].equals(current_DayOfWeek)) {
                                        dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                    }
                                }
                                if (queryDay[0].equals("DOM")) {
                                    if (querySelectedDay[w].equals(current_DayOfMonth)) {
                                        dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                    }
                                }
                            }

                        } else {  //ถ้าเป็น ED จะมี 0 1 2 3 4 5


                            String[] stringsDate_ED_Ref = myManage.filter_sumTABLE_finding_DateRef_by_MainId_idDESC(stringsReadAll_MainTABLE[1][i]); //เอาค่า Main_id มา
                            Date date_ED_Ref = myData.stringChangetoDateWithOutTime(stringsDate_ED_Ref[0]); //dateRef ก่อนนำไป add ค่Calendar calendarRef = Calendar.getInstance();

                            Calendar calendarRef = Calendar.getInstance();
                            calendarRef.setTime(date_ED_Ref);  //calendarRef ก่อนนำไป add ค่า

                            //เทียบ date ที่ add ค่าแล้ว กับ dateAfterProcess จากข้างบน

                            if (queryDay[1].equals("0")) {
                                dailyUpdateReceiver.checkWhich_Date_D = "Y";
                            } else if (queryDay[1].equals("1")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 2);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("2")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 3);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("3")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 4);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("4")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 5);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            } else if (queryDay[1].equals("5")) {
                                //วันเว้นวัน
                                calendarRef.add(Calendar.DAY_OF_MONTH, 6);
                                Date date = calendarRef.getTime();
                                if (date.compareTo(dateRef) == 0) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                }
                            }


                        }


                    } // stringsReadAll_MainTABLE[10][i].equals("")

                    //Check ค่าทั้งหมดแล้วว่าผ่าน ให้ทำการ add ค่าเข้ามาได้

                    if (dailyUpdateReceiver.checkWhich_Date_D.equals("Y")
                            && dailyUpdateReceiver.checkStartDay.equals("Y")
                            && dailyUpdateReceiver.checkFinishDay.equals("Y")) {
                        Log.d("UpdatesumTABLE", "ตำแหน่งที่ i addค่าเข้า SumTABLE ได้ : " + i);
                        Toast.makeText(getBaseContext(), "Addข้อมูลลง sumTABLE : (Y,Y,Y)", Toast.LENGTH_LONG).show();

                        //เริ่ม addข้อมูลลง sumTABLE
                        //1 row ของ mainTABLE add ได้หลาย row ของ sumTABLE ตาม T1-T8 (column 12 - 19)
                        for (int x = 12; x <= 19; x++) {
                            if (!stringsReadAll_MainTABLE[x][i].equals("")) {
                                String stringMain_id = stringsReadAll_MainTABLE[0][i];  //Main_id
                                String stringTimeRef = stringsReadAll_MainTABLE[x][i];  //TimeRef ตำแหน่งต่างๆ
                                myManage.addValueToSumTable(stringMain_id, stringDateRef, stringTimeRef, "", "", "");
                                Log.d("UpdatesumTABLE", "addValueToSumTable : " + stringMain_id + " " + stringDateRef + " " + stringTimeRef);
                            }


                        }

                    } else {
                        Log.d("UpdatesumTABLE", "ตำแหน่งที่ i addค่าเข้า SumTABLE ไม่ได้ : " + i);
                    }


                } //first "For"

            } while (dateRef.compareTo(dateFinalProcess) < 0);


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Calendar calendar = Calendar.getInstance();
                    Calendar myCalendar1 = (Calendar) calendar.clone();

                    myCalendar1.set(Calendar.HOUR_OF_DAY, 0);
                    myCalendar1.set(Calendar.MINUTE, 0);
                    myCalendar1.set(Calendar.SECOND, 0);
                    myCalendar1.set(Calendar.MILLISECOND, 10);


                    Intent intent = new Intent(getBaseContext(), DailyUpdateReceiver.class);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),
                            1000, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                    AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
                    alarmManager.setRepeating(1, myCalendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                    Toast.makeText(getBaseContext(), "เริ่มทำการ BroadCAst", Toast.LENGTH_LONG).show();

                }
            },10000); // 7 วินาที







        } //else  สุดท้าย

    }


    public void updatesumTABLE00() {


        String[] stringsREAD_mainTABLE = myManage.readAllMainTABLE_Full(11); //เอามา check ว่า mainTABLE มียาป่าว หรือมีแต่ PRN (N หรือ Y)
        //String[] stringsDateRef = myManage.readAllsumTABLE_Full_Order_id_DESC(2); //check วันที่มีการ Add ยาลง sumTABLE ล่าสุด

        //2/10/2559.......ต้องแก้ 2 อัน....อันนี้กับ DailyupdateReceiver นะจ๊ะ

        String[] strLast_updated = myManage.filter_userTABLE(5); //วันที่ในระบบล่าสุด
        Date dateLast_updated = myData.stringChangetoDateWithOutTime(strLast_updated[0]);
        String currentDay = myData.currentDay();
        Date dateInitial = myData.stringChangetoDateWithOutTime(currentDay);


        //ดูว่ามีแต่ prn ก็ต้องยกเลิก
        String strCheckPRN = "Y";
        for(int i = 0;i<stringsREAD_mainTABLE.length;i++) {
            if (stringsREAD_mainTABLE[i].equals("N")) {
                strCheckPRN = "N";
            }
        }
        //มีถึงอีก 9 วันข้างหน้าแล้วหรือยัง
        String strDateRef = "N";
        if (dateLast_updated.compareTo(dateInitial) >= 0) {
            strDateRef = "Y"; //stopProcess
        }


        if (stringsREAD_mainTABLE[0].equals("")) {
            Log.d("UpdatesumTABLE", "ไม่มียาใน mainTABLE : ค่าว่าง ยุติการ UpdateReceiver");
            Toast.makeText(SplashScreen.this,"ไม่มียาใน mainTABLE : ค่าว่าง ยุติการ UpdateReceiver",Toast.LENGTH_LONG).show();
            return;
        }
        //ดูว่าถ้ามีถ่าแต่ prn ก็ต้องยกเลิก
        else if (strCheckPRN.equals("Y")) {
            Log.d("UpdatesumTABLE", "ยาใน mainTABLE มีแต่ยา PRN : ยุติการ UpdateReceiver");
            Toast.makeText(SplashScreen.this,"ยาใน mainTABLE มีแต่ยา PRN :ยุติการ UpdateReceiver",Toast.LENGTH_LONG).show();
            return;

        }
        //ถ้าจะ Test การเอาเข้าให้เอา else if อันนี้ออกไป
        else if(strDateRef.equals("Y")) {
            Log.d("UpdatesumTABLE", "มีค่าวันนี้ใน sumTABLE ของวันนี้แล้ว : ยุติการ UpdateReceiver");
            Toast.makeText(SplashScreen.this, "มีค่าวันนี้ใน sumTABLE ของวันนี้แล้ว : ยุติการ UpdateReceiver", Toast.LENGTH_LONG).show();
            return;
        } else {

            Calendar calendar = Calendar.getInstance();
            Calendar myCalendar1 = (Calendar) calendar.clone();

            myCalendar1.set(Calendar.HOUR_OF_DAY, 0);
            myCalendar1.set(Calendar.MINUTE, 0);
            myCalendar1.set(Calendar.SECOND, 0);
            myCalendar1.set(Calendar.MILLISECOND, 10);



            Intent intent = new Intent(getBaseContext(), DailyUpdateReceiver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),
                    1000, intent, PendingIntent.FLAG_UPDATE_CURRENT);


            AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(1, myCalendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            Toast.makeText(getBaseContext(), "เริ่มทำการ BroadCAst", Toast.LENGTH_LONG).show();

        }
    } //updatesumTABLE00



    /*
    private void updatesumTABLE00() {
        MyData myData = new MyData();
        String[] stringsREAD_mainTABLE = myManage.readAllMainTABLE_Full(11); //เอามา check ว่า mainTABLE มียาป่าว หรือมีแต่ PRN (N หรือ Y)
        //ต้องแก้ค่าใน sumTABLE ก่อนให้มีแต่ค่าที่แท้จริงเท่านั้นก่อนควรจะมีแค่บรรทัดเดียว
        String[] stringsDateRef = myManage.readAllsumTABLE_Full(2); //check วันที่มีการ Add ยาลง sumTABLE ล่าสุด
        String currentDay = myData.currentDay();  //ค่าของวันนี้
        // 29/09/16 ==>ดูว่าอีก 7 วันข้างหน้ามียาหรือยัง
        Calendar calendarCurrent = Calendar.getInstance();
        calendarCurrent.add(Calendar.DAY_OF_MONTH,7);
        Date dateCurrent = calendarCurrent.getTime(); //อีก 7 วันนับจากวันนี้ ของ smartPhone
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Calendar calendarRef = Calendar.getInstance();
        //calendarRef.setTime(date);
        //calendarRef.add(Calendar.DAY_OF_MONTH, 7);
        //Date dateRef = calendarRef.getTime(); //อีก 7 วันนับจากวันล่าสุดที่อยู่ใน sumTABLE
        //stringDateRefAdd7 = dateFormat.format(dateRef);
        stringCurrentAdd7 = dateFormat.format(dateCurrent); //ใช้อันนี้
        Log.d("29/09/16V1", stringCurrentAdd7);
        // 29/09/16V1 ลอง.....แค่นี้ก่อน
        //ดูว่ามีแต่ prn ก็ต้องยกเลิก
        String strCheckPRN = "Y";
        for(int i = 0;i<stringsREAD_mainTABLE.length;i++) {
            if (stringsREAD_mainTABLE[i].equals("N")) {
                strCheckPRN = "N";
            }
        }
        //มีวันนี้แล้วหรือยัง
        String strDateRef = "N";
        for(int x = 0;x<stringsDateRef.length;x++) {
            if (stringsDateRef[x].equals(currentDay)) {
                strDateRef = "Y";
            }
        }
        if (stringsREAD_mainTABLE[0].equals("")) {
            Log.d("UpdatesumTABLE", "ไม่มียาใน mainTABLE : ค่าว่าง ยุติการ UpdateReceiver");
            Toast.makeText(SplashScreen.this,"ไม่มียาใน mainTABLE : ค่าว่าง ยุติการ UpdateReceiver",Toast.LENGTH_LONG).show();
            return;
        }
        //ดูว่าถ้ามีถ่าแต่ prn ก็ต้องยกเลิก
        else if (strCheckPRN.equals("Y")) {
            Log.d("UpdatesumTABLE", "ยาใน mainTABLE มีแต่ยา PRN : ยุติการ UpdateReceiver");
            Toast.makeText(SplashScreen.this,"ยาใน mainTABLE มีแต่ยา PRN :ยุติการ UpdateReceiver",Toast.LENGTH_LONG).show();
            return;
        }
        //ถ้าจะ Test การเอาเข้าให้เอา else if อันนี้ออกไป
        else if (strDateRef.equals("Y")) {
            Log.d("UpdatesumTABLE", "มีค่าวันนี้ใน sumTABLE ของวันนี้แล้ว : ยุติการ UpdateReceiver");
            Toast.makeText(SplashScreen.this, "มีค่าวันนี้ใน sumTABLE ของวันนี้แล้ว : ยุติการ UpdateReceiver", Toast.LENGTH_LONG).show();
            return;
        } else {
            Calendar calendar = Calendar.getInstance();
            Calendar myCalendar1 = (Calendar) calendar.clone();
            myCalendar1.set(Calendar.HOUR_OF_DAY, 0);
            myCalendar1.set(Calendar.MINUTE, 0);
            myCalendar1.set(Calendar.SECOND, 0);
            myCalendar1.set(Calendar.MILLISECOND, 10);
            Random random = new Random();
            int myRandom = random.nextInt(1000);
            Intent intent = new Intent(getBaseContext(), DailyUpdateReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),
                    myRandom, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(1, myCalendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("UpdatesumTABLE", "ทำ Alarm ขึ้นเองได้แล้ว" + myCalendar1.getTime().toString());
            Toast.makeText(getBaseContext(), "เริ่มทำการ BroadCAst", Toast.LENGTH_LONG).show();
        }
    } //updatesumTABLE00    */






}  //Main Class