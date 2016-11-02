package com.su.lapponampai_w.mhm_app_beta1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PatientAdherenceActivity extends AppCompatActivity {

    //Explicit
    CalendarView calendarView;
    ListView listView;
    MyManage myManage;
    MyData myData;
    String strDateRef;
    String[] strings_id,strings_Main_id,strings_DateRef,strings_TimeRef,
            strings_DateCheck,strings_TimeCheck, strings_SkipHold;
    String[] stringsReadmainTABLE_id,stringsReadmainTABLE_Tradename,
            stringsReadmainTABLE_Pharmaco,stringsReadmainTABLE_Appearance;

    String[] strings_DateTimeRef,strings_DateTimeCheck,strings_Tradename,
            strings_AdherenceIndicator, strings_Appearance;

    String[] strAdaptorTradename;

    String[] strAdaptorMedicine, strAdaptorStar1,
            strAdaptorStar2, strAdaptorStar3, strAdaptorStar4,
            strAdaptorStar5, strAdaptorStar6, strAdaptorStar7, strAdaptorStar8;

    int[] intsAdaptorAdherence,intsAdaptorStar1,intsAdaptorStar2,intsAdaptorStar3,intsAdaptorStar4
            ,intsAdaptorStar5,intsAdaptorStar6,intsAdaptorStar7,intsAdaptorStar8,intsAdaptorMedicine;

    double[] dAdaptorAdherence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_adherence);
        myManage = new MyManage(this);
        myData = new MyData();

        bindWidget();

        strDateRef = myData.currentDay();
        showListView(strDateRef);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String strDateRef = myData.createStringDay(dayOfMonth, month + 1, year);
                showListView(strDateRef);
            }
        });

    }



    private void showListView(String strDateRef) {

        //เอาค่าของ sumTABLE มาทุกค่าโดย filter วันที่คลิกในปฏิทิน
        strings_id = myManage.filtersumTABLE_by_DateRef(strDateRef, 0); //id
        strings_Main_id = myManage.filtersumTABLE_by_DateRef(strDateRef, 1); //Main_id
        strings_DateRef = myManage.filtersumTABLE_by_DateRef(strDateRef, 2); //Date_Ref
        strings_TimeRef = myManage.filtersumTABLE_by_DateRef(strDateRef, 3); //Time_Ref
        strings_DateCheck = myManage.filtersumTABLE_by_DateRef(strDateRef, 4); //Date_Check
        strings_TimeCheck = myManage.filtersumTABLE_by_DateRef(strDateRef, 5); //Time_Check
        strings_SkipHold = myManage.filtersumTABLE_by_DateRef(strDateRef, 6); //SkipHold
        stringsReadmainTABLE_id = myManage.read_mainTABLE_InCluded_DateTimeCanceled(0); //Main_id
        stringsReadmainTABLE_Tradename = myManage.read_mainTABLE_InCluded_DateTimeCanceled(2);  //Tradename
        stringsReadmainTABLE_Pharmaco = myManage.read_mainTABLE_InCluded_DateTimeCanceled(8); //Pharmaco
        stringsReadmainTABLE_Appearance = myManage.read_mainTABLE_InCluded_DateTimeCanceled(6); //Appearance

        if (!strings_id[0].equals("")) {
            //รวม DateRef,TimeRef เป็น DateTimeRef , รวม DateCheck,TimeCheck เป็น DateTimeCheck
            strings_DateTimeRef = new String[strings_id.length];
            strings_DateTimeCheck = new String[strings_id.length];
            strings_Tradename = new String[strings_id.length];
            strings_AdherenceIndicator = new String[strings_id.length];
            strings_Appearance = new String[strings_id.length];

            for (int i = 0; i < strings_Main_id.length; i++) {

                strings_DateTimeRef[i] = strings_DateRef[i] + " " + strings_TimeRef[i];
                if (!strings_DateCheck[i].equals("")) {
                    strings_DateTimeCheck[i] = strings_DateCheck[i] + " " + strings_TimeCheck[i];
                } else {
                    strings_DateTimeCheck[i] = "";
                }

                for (int w = 0; w < stringsReadmainTABLE_id.length; w++) {
                    if (strings_Main_id[i].equals(stringsReadmainTABLE_id[w])) {
                        strings_Tradename[i] = stringsReadmainTABLE_Tradename[w];
                        strings_AdherenceIndicator[i] = stringsReadmainTABLE_Pharmaco[w];
                        strings_Appearance[i] = stringsReadmainTABLE_Appearance[w];
                    }
                }
            } //ได้ค่าครบทุกค่าแล้ว

            Log.d("DailyFragment1", strings_DateTimeRef[0]);
            Log.d("DailyFragment1", strings_Tradename[0]);
            Log.d("DailyFragment1", strings_AdherenceIndicator[0]);


            //คำนวณกันเถอะโดยมี เงื่อนไงดังนี้
            //ถ้า Pharmaco มีค่าเป็น A ต้องมีระยะเวลาในการรับประทาน +- ไม่เกิน 30 นาที
            //ถ้า Pharmaco เป็ฯอย่างอื่น แค่ check ว่าทานก็ Ok แล้วครับ

            int[] iInitial = new int[strings_id.length];
            int[] iResult = new int[strings_id.length];

            for (int i = 0; i < strings_id.length; i++) {
                int i_initial = 0;
                int i_total = 0;
                if (!strings_SkipHold[i].equals("")) {
                    i_initial = i_initial + 1;
                    i_total = i_total + 1;
                } else {
                    if (strings_DateTimeCheck[i].equals("")) {
                        i_initial = i_initial + 0;
                        i_total = i_total + 1;
                    } else {
                        if (strings_AdherenceIndicator[i].equals("A")) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            java.util.Date date_Ref = new java.util.Date();
                            java.util.Date date_Check = new java.util.Date();

                            try {
                                date_Ref = dateFormat.parse(strings_DateTimeRef[i]);
                                date_Check = dateFormat.parse(strings_DateTimeCheck[i]);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            //ยกตัวอย่างให้อยู่ใน ไม่เกิน 30 นาที
                            Calendar calendar_Ref1 = Calendar.getInstance();
                            Calendar calendar_Ref2 = Calendar.getInstance();
                            calendar_Ref1.setTime(date_Ref);
                            calendar_Ref2.setTime(date_Ref);
                            calendar_Ref1.add(Calendar.MINUTE, -30);
                            calendar_Ref2.add(Calendar.MINUTE, 30);

                            java.util.Date date_Ref1 = calendar_Ref1.getTime();
                            java.util.Date date_Ref2 = calendar_Ref2.getTime();

                            String sssDateCheck = dateFormat.format(date_Check);
                            String sssDateRef1 = dateFormat.format(date_Ref1);
                            String sssDateRef2 = dateFormat.format(date_Ref2);
                            Log.d("DailyFragment", "date_check = " + sssDateCheck);
                            Log.d("DailyFragment", "dateRef1 = " + sssDateRef1);
                            Log.d("DailyFragment", "dateRef2 = " + sssDateRef2);

                            if (date_Ref1.compareTo(date_Check) < 0 &&
                                    date_Ref2.compareTo(date_Check) >= 0) {
                                i_initial = i_initial + 1;
                                i_total = i_total + 1;
                            } else {
                                i_initial = i_initial + 0;
                                i_total = i_total + 1;
                            }


                        } else {
                            i_initial = i_initial + 1;
                            i_total = i_total + 1;
                        }
                    }
                }
                iInitial[i] = i_initial;
                iResult[i] = i_total;
                Log.d("DailyFragment", "i_initial = " + Integer.toString(i_initial));
                Log.d("DailyFragment", "i_result = " + Integer.toString(i_total));
            }


            //น่าจะครบทุกอย่างละต่อไปก็แค่เอาทั้งหมดมารวมกันแล้วก็สรุปใน listView
            ArrayList<String> arrayList_main_id = new ArrayList<String>();
            int iIndex = 0;
            String[] stringsIndex = {""};
            for (int i = 0; i < stringsReadmainTABLE_id.length; i++) {
                String strIntermediate = ""; //ตัวกลางใช้ยกตัวอย่าง
                for (int w = 0; w < strings_Main_id.length; w++) { //Main_id ในตารางที่จะใช้
                    if (stringsReadmainTABLE_id[i].equals(strings_Main_id[w])) {
                        String str = "N";
                        for (int z = 0; z < stringsIndex.length; z++) {
                            if (stringsIndex[z].equals(stringsReadmainTABLE_id[i])) {
                                str = "Y";
                            }
                        }
                        if (str.equals("N")) {
                            arrayList_main_id.add(iIndex, strings_Main_id[w]);
                            iIndex = iIndex + 1;
                            stringsIndex = new String[arrayList_main_id.size()];
                            stringsIndex = arrayList_main_id.toArray(stringsIndex);
                        }
                    }
                }
            }

            strAdaptorTradename = new String[stringsIndex.length]; //
            strAdaptorMedicine = new String[stringsIndex.length]; //
            dAdaptorAdherence = new double[stringsIndex.length]; //
            intsAdaptorAdherence = new int[stringsIndex.length];  //หน้ายิ้ม
            strAdaptorStar1 = new String[stringsIndex.length];
            strAdaptorStar2 = new String[stringsIndex.length];
            strAdaptorStar3 = new String[stringsIndex.length];
            strAdaptorStar4 = new String[stringsIndex.length];
            strAdaptorStar5 = new String[stringsIndex.length];
            strAdaptorStar6 = new String[stringsIndex.length];
            strAdaptorStar7 = new String[stringsIndex.length];
            strAdaptorStar8 = new String[stringsIndex.length];
            String[] strAdaptorAdherence = new String[stringsIndex.length];

            //เอาค่ามาทำ Adaptor
            int iadherence = 0;
            int itotal = 0;
            for (int i = 0; i < stringsIndex.length; i++) {
                iadherence = 0;
                itotal = 0;
                for (int w = 0; w < strings_Main_id.length; w++) {
                    if (stringsIndex[i].equals(strings_Main_id[w])) {
                        strAdaptorTradename[i] = strings_Tradename[w];
                        strAdaptorMedicine[i] = strings_Appearance[w];

                        iadherence = iadherence + iInitial[w];
                        itotal = itotal + iResult[w];
                        Log.d("DailyFragment", "iadherence : " + iadherence);
                        Log.d("DailyFragment", "itotal : " + itotal);
                    }
                }
                dAdaptorAdherence[i] = iadherence * 100 / itotal;
                strAdaptorAdherence[i] = Double.toString(dAdaptorAdherence[i]);
                if (dAdaptorAdherence[i] == 100) {
                    intsAdaptorAdherence[i] = R.drawable.good_adherence;
                } else if (dAdaptorAdherence[i] == 0) {
                    intsAdaptorAdherence[i] = R.drawable.bad_adherence;
                } else {
                    intsAdaptorAdherence[i] = R.drawable.bad_adherence;
                }
            }

            //stringsIndex คือ main_id ที่เป็น Array เอามาหาค่า T1 - T8
            for (int i = 0; i < stringsIndex.length; i++) {
                strAdaptorStar1[i] = "N";
                strAdaptorStar2[i] = "N";
                strAdaptorStar3[i] = "N";
                strAdaptorStar4[i] = "N";
                strAdaptorStar5[i] = "N";
                strAdaptorStar6[i] = "N";
                strAdaptorStar7[i] = "N";
                strAdaptorStar8[i] = "N";
            }


            String[][] stringsStar = {strAdaptorStar1, strAdaptorStar2, strAdaptorStar3,
                    strAdaptorStar4, strAdaptorStar5, strAdaptorStar6, strAdaptorStar7, strAdaptorStar8};
            //กำหนดให้ stringsStar ทั้งหมดมีค่าเป็น W


            for (int i = 0; i < stringsIndex.length; i++) {
                String[] sT1T8 = myManage.filter_mainTABLE_T1T8_by_mainID(stringsIndex[i]);
                if (!sT1T8[0].equals("")) {
                    for (int a = 0; a < sT1T8.length; a++) {
                        for (int b = 0; b < strings_id.length; b++) {
                            if (stringsIndex[i].equals(strings_Main_id[b]) && sT1T8[a].equals(strings_TimeRef[b])) {
                                if (!strings_SkipHold[b].equals("")) {
                                    stringsStar[a][i] = "B";
                                    //stringsStar[i][a] = sT1T8[a];
                                    //Log.d("01/11/2559", stringsStar[i][a]);
                                } else {
                                    //ตรวจสอบ
                                    if (strings_DateTimeCheck[b].equals("")) {
                                        stringsStar[a][i] = "W";
                                    } else {
                                        if (strings_AdherenceIndicator[b].equals("A")) {
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                            java.util.Date date_Ref = new java.util.Date();
                                            java.util.Date date_Check = new java.util.Date();

                                            try {
                                                date_Ref = dateFormat.parse(strings_DateTimeRef[b]);
                                                date_Check = dateFormat.parse(strings_DateTimeCheck[b]);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                            //ยกตัวอย่างให้อยู่ใน ไม่เกิน 30 นาที
                                            Calendar calendar_Ref1 = Calendar.getInstance();
                                            Calendar calendar_Ref2 = Calendar.getInstance();
                                            calendar_Ref1.setTime(date_Ref);
                                            calendar_Ref2.setTime(date_Ref);
                                            calendar_Ref1.add(Calendar.MINUTE, -30);
                                            calendar_Ref2.add(Calendar.MINUTE, 30);

                                            java.util.Date date_Ref1 = calendar_Ref1.getTime();
                                            java.util.Date date_Ref2 = calendar_Ref2.getTime();

                                            if (date_Ref1.compareTo(date_Check) < 0 &&
                                                    date_Ref2.compareTo(date_Check) >= 0) {
                                                stringsStar[a][i] = "G";
                                            } else {
                                                stringsStar[a][i] = "S";
                                            }

                                        } else {
                                            stringsStar[a][i] = "G";
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

            } //first for

            for (int i = 0; i < stringsStar[0].length; i++) { //row
                for (int x = 0; x < stringsStar.length; x++) { //column
                    Log.d("1/11/59", "T" + Integer.toString(x + 1) + "row: " + Integer.toString(i) + " = " + stringsStar[x][i]);
                }
            }

            /*
            Log.d("1/11/59", "T1 = " + strAdaptorStar1[0]);
            Log.d("1/11/59", "T2 = " + strAdaptorStar2[0]);
            Log.d("1/11/59", "T3 = " + strAdaptorStar3[0]);
            Log.d("1/11/59", "T4 = " + strAdaptorStar4[0]);
            Log.d("1/11/59", "T5 = " + strAdaptorStar5[0]);
            Log.d("1/11/59", "T6 = " + strAdaptorStar6[0]);
            Log.d("1/11/59", "T7 = " + strAdaptorStar7[0]);
            Log.d("1/11/59", "T8 = " + strAdaptorStar8[0]);
            */

            intsAdaptorMedicine = myData.translate_Small_Appearance(strAdaptorMedicine);
            intsAdaptorStar1 = myData.translate_Star_Appearance(strAdaptorStar1);
            intsAdaptorStar2 = myData.translate_Star_Appearance(strAdaptorStar2);
            intsAdaptorStar3 = myData.translate_Star_Appearance(strAdaptorStar3);
            intsAdaptorStar4 = myData.translate_Star_Appearance(strAdaptorStar4);
            intsAdaptorStar5 = myData.translate_Star_Appearance(strAdaptorStar5);
            intsAdaptorStar6 = myData.translate_Star_Appearance(strAdaptorStar6);
            intsAdaptorStar7 = myData.translate_Star_Appearance(strAdaptorStar7);
            intsAdaptorStar8 = myData.translate_Star_Appearance(strAdaptorStar8);


            MyAdaptorPatientAdherence myAdaptorPatientAdherence = new
                    MyAdaptorPatientAdherence(getBaseContext(), strAdaptorTradename,
                    intsAdaptorAdherence, intsAdaptorMedicine, intsAdaptorStar1, intsAdaptorStar2,
                    intsAdaptorStar3, intsAdaptorStar4, intsAdaptorStar5, intsAdaptorStar6,
                    intsAdaptorStar7, intsAdaptorStar8);

            listView.setAdapter(myAdaptorPatientAdherence);
            listView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.INVISIBLE);
        }







    }

    private void bindWidget() {

        calendarView = (CalendarView) findViewById(R.id.calendarView5);
        listView = (ListView) findViewById(R.id.listViewPatientAdherence);



    }
}
