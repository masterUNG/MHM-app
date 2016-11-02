package com.su.lapponampai_w.mhm_app_beta1;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment {

    private Context globalContext = null;


    public DailyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily, container, false);
        globalContext = this.getActivity();

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //bindWidget
        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        final ListView listView = (ListView) view.findViewById(R.id.listViewDaily);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView6);

        calendarView.setShowWeekNumber(false);

        //Show!! listView
        MyManage myManage = new MyManage(globalContext);
        MyData myData = new MyData();
        String strDateRef = myData.currentDay();
        Log.d("DailyFragment", strDateRef);

        //เอาค่าของ sumTABLE มาทุกค่าโดย filter วันที่คลิกในปฏิทิน
        String[] strings_id = myManage.filtersumTABLE_by_DateRef(strDateRef, 0); //id
        String[] strings_Main_id = myManage.filtersumTABLE_by_DateRef(strDateRef, 1); //Main_id
        String[] strings_DateRef = myManage.filtersumTABLE_by_DateRef(strDateRef, 2); //Date_Ref
        String[] strings_TimeRef = myManage.filtersumTABLE_by_DateRef(strDateRef, 3); //Time_Ref
        String[] strings_DateCheck = myManage.filtersumTABLE_by_DateRef(strDateRef, 4); //Date_Check
        String[] strings_TimeCheck = myManage.filtersumTABLE_by_DateRef(strDateRef, 5); //Time_Check
        String[] strings_SkipHold = myManage.filtersumTABLE_by_DateRef(strDateRef, 6); //SkipHold
        String[] stringsReadmainTABLE_id = myManage.read_mainTABLE_InCluded_DateTimeCanceled(0); //Main_id
        String[] stringsReadmainTABLE_Tradename = myManage.read_mainTABLE_InCluded_DateTimeCanceled(2);  //Tradename
        String[] stringsReadmainTABLE_Pharmaco = myManage.read_mainTABLE_InCluded_DateTimeCanceled(8); //Pharmaco
        String[] stringsReadmainTABLE_Appearance = myManage.read_mainTABLE_InCluded_DateTimeCanceled(6); //Appearance

        Log.d("DailyFragment", strings_Main_id[0]);
        Log.d("DailyFragment", strings_id[0]);
        Toast.makeText(getActivity(), strings_Main_id[0], Toast.LENGTH_SHORT).show();

        if (!strings_id[0].equals("")) {

            //รวม DateRef,TimeRef เป็น DateTimeRef , รวม DateCheck,TimeCheck เป็น DateTimeCheck
            String[] strings_DateTimeRef = new String[strings_id.length];
            String[] strings_DateTimeCheck = new String[strings_id.length];
            String[] strings_Tradename = new String[strings_id.length];
            String[] strings_AdherenceIndicator = new String[strings_id.length];
            String[] strings_Appearance = new String[strings_id.length];

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

            String[] strAdaptorTradename = new String[stringsIndex.length];
            String[] strAdaptorDisplay = new String[stringsIndex.length];
            double[] dAdaptorAdherence = new double[stringsIndex.length];
            String[] strAdaptorAdherence = new String[stringsIndex.length];
            int[] iAdaptorKPI = new int[stringsIndex.length];
            //เอาค่ามาทำ Adaptor
            int iadherence = 0;
            int itotal = 0;
            for (int i = 0; i < stringsIndex.length; i++) {
                iadherence = 0;
                itotal = 0;
                for (int w = 0; w < strings_Main_id.length; w++) {
                    if (stringsIndex[i].equals(strings_Main_id[w])) {
                        strAdaptorTradename[i] = strings_Tradename[w];
                        strAdaptorDisplay[i] = strings_Appearance[w];
                        iadherence = iadherence + iInitial[w];
                        itotal = itotal + iResult[w];
                        Log.d("DailyFragment", "iadherence : " + iadherence);
                        Log.d("DailyFragment", "itotal : " + itotal);
                    }
                }
                dAdaptorAdherence[i] = iadherence * 100 / itotal;
                strAdaptorAdherence[i] = Double.toString(dAdaptorAdherence[i]);
                if (dAdaptorAdherence[i] == 100) {
                    iAdaptorKPI[i] = R.drawable.good_adherence;
                } else {
                    iAdaptorKPI[i] = R.drawable.bad_adherence;
                }
            }

            int[] iAdaptorDisplay = myData.translate_Small_Appearance(strAdaptorDisplay);

            MyAdaptorAdherence myAdaptorAdherence = new
                    MyAdaptorAdherence(getActivity(), strAdaptorTradename,
                    strAdaptorAdherence, iAdaptorKPI, iAdaptorDisplay);

            listView.setAdapter(myAdaptorAdherence);
            listView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
        } else {
            listView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }

        //จบ Show



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                MyManage myManage = new MyManage(globalContext);
                MyData myData = new MyData();

                //เราเอาค่าจาก sum table querybyday
                String strDateRef = myData.createStringDay(dayOfMonth, month + 1, year);

                Log.d("DailyFragment", strDateRef);

                //เอาค่าของ sumTABLE มาทุกค่าโดย filter วันที่คลิกในปฏิทิน
                String[] strings_id = myManage.filtersumTABLE_by_DateRef(strDateRef, 0);
                String[] strings_Main_id = myManage.filtersumTABLE_by_DateRef(strDateRef, 1);
                String[] strings_DateRef = myManage.filtersumTABLE_by_DateRef(strDateRef, 2);
                String[] strings_TimeRef = myManage.filtersumTABLE_by_DateRef(strDateRef, 3);
                String[] strings_DateCheck = myManage.filtersumTABLE_by_DateRef(strDateRef, 4);
                String[] strings_TimeCheck = myManage.filtersumTABLE_by_DateRef(strDateRef, 5);
                String[] strings_SkipHold = myManage.filtersumTABLE_by_DateRef(strDateRef, 6);
                //รับค่า mainTABLE ที่รวม DateTimeCanceled
                String[] stringsReadmainTABLE_id = myManage.read_mainTABLE_InCluded_DateTimeCanceled(0);
                String[] stringsReadmainTABLE_Tradename = myManage.read_mainTABLE_InCluded_DateTimeCanceled(2);
                String[] stringsReadmainTABLE_Pharmaco = myManage.read_mainTABLE_InCluded_DateTimeCanceled(8);
                String[] stringsReadmainTABLE_Appearance = myManage.read_mainTABLE_InCluded_DateTimeCanceled(6);

                Log.d("DailyFragment", strings_Main_id[0]);
                Log.d("DailyFragment", strings_id[0]);
                Toast.makeText(getActivity(), strings_Main_id[0], Toast.LENGTH_SHORT).show();

                if (!strings_id[0].equals("")) {

                    //รวม DateRef,TimeRef เป็น DateTimeRef , รวม DateCheck,TimeCheck เป็น DateTimeCheck
                    String[] strings_DateTimeRef = new String[strings_id.length];
                    String[] strings_DateTimeCheck = new String[strings_id.length];
                    String[] strings_Tradename = new String[strings_id.length];
                    String[] strings_AdherenceIndicator = new String[strings_id.length];
                    String[] strings_Appearance = new String[strings_id.length];

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
                    for(int i = 0;i<stringsReadmainTABLE_id.length;i++) {
                        //String strIntermediate = ""; //ตัวกลางใช้ยกตัวอย่าง
                        for(int w = 0;w<strings_Main_id.length;w++) { //Main_id ในตารางที่จะใช้
                            if (stringsReadmainTABLE_id[i].equals(strings_Main_id[w])) {
                                String str = "N";
                                for (int z = 0;z<stringsIndex.length;z++) {
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

                    String[] strAdaptorTradename = new String[stringsIndex.length];
                    String[] strAdaptorDisplay = new String[stringsIndex.length];
                    double[] dAdaptorAdherence = new double[stringsIndex.length];
                    String[] strAdaptorAdherence = new String[stringsIndex.length];
                    int[] iAdaptorKPI = new int[stringsIndex.length];
                    //เอาค่ามาทำ Adaptor
                    int iadherence = 0;
                    int itotal = 0;
                    for(int i = 0;i<stringsIndex.length;i++) {
                        iadherence = 0;
                        itotal = 0;
                        for(int w = 0;w<strings_Main_id.length;w++) {
                            if (stringsIndex[i].equals(strings_Main_id[w])) {
                                strAdaptorTradename[i] = strings_Tradename[w];
                                strAdaptorDisplay[i] = strings_Appearance[w];
                                iadherence = iadherence + iInitial[w];
                                itotal = itotal + iResult[w];
                                Log.d("DailyFragment", "iadherence : " + iadherence);
                                Log.d("DailyFragment", "itotal : " + itotal);
                            }
                        }
                        dAdaptorAdherence[i] = iadherence * 100 / itotal;
                        strAdaptorAdherence[i] = Double.toString(dAdaptorAdherence[i]);
                        if (dAdaptorAdherence[i] == 100) {
                            iAdaptorKPI[i] = R.drawable.good_adherence;
                        } else {
                            iAdaptorKPI[i] = R.drawable.bad_adherence;
                        }
                    }
                    int[] iAdaptorDisplay = myData.translate_Small_Appearance(strAdaptorDisplay);

                    MyAdaptorAdherence myAdaptorAdherence = new
                            MyAdaptorAdherence(getActivity(), strAdaptorTradename,
                            strAdaptorAdherence, iAdaptorKPI, iAdaptorDisplay);

                    listView.setAdapter(myAdaptorAdherence);
                    listView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                } else {
                    listView.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
