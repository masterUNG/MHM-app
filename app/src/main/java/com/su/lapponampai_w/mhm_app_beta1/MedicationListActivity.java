package com.su.lapponampai_w.mhm_app_beta1;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MedicationListActivity extends AppCompatActivity {

    //Explicit
    public static Activity activityMedicationListActivity;
    private ListView listView;
    private String[] strings_id, strings_tradname,strings_med_id, strings_generic_line, strings_appearance;
    ImageButton imageButtonCalendar;
    MyManage myManage;

    //Heading
    Spinner spinner;
    ImageButton imageAdherence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_list);
        activityMedicationListActivity = this;

        myManage = new MyManage(this);
        setHeading();

        bindWidget();


        recieveValue_mainTABLE();




    } // Main Method



    @Override
    public void onResume(){
        super.onResume();
        setHeading();
        recieveValue_mainTABLE();

    }




    private void recieveValue_mainTABLE() {
        final MyManage myManage = new MyManage(this);
        /*
        strings_id = myManage.readAllMainTABLE(0);
        strings_tradname = myManage.readAllMainTABLE(3);
        strings_med_id = myManage.readAllMainTABLE(2);
        strings_generic_line = myManage.readAllMainTABLE(4);
        strings_appearance = myManage.readAllMainTABLE(5);
        */

        strings_id = myManage.readAllMainTABLE_Full(0);
        strings_tradname = myManage.readAllMainTABLE_Full(2);
        strings_med_id = myManage.readAllMainTABLE_Full(1);
        strings_generic_line = myManage.readAllMainTABLE_Full(3);
        strings_appearance = myManage.readAllMainTABLE_Full(6);

        if (strings_id[0].equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.icon_question);
            builder.setTitle("ไม่มียาในรายการ!!!");
            builder.setMessage("กรุณาเพิ่มรายการยาก่อน\nโดยคลิ๊กที่ +เพิ่มเติม+ ==> เพิ่มรายการยา");
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                }
            });
            builder.show();
        }


        MyData myData = new MyData();
        final int[] intsIndex = myData.translate_Appearance(strings_appearance);

        //MyAdaptor myAdaptor = new MyAdaptor(AddMedicineActivity.this, stringsTradename, stringsGenericLine1, intsIndex);
        //listViewAddTG.setAdapter(myAdaptor);

        MyAdaptor myAdaptor = new MyAdaptor(MedicationListActivity.this,strings_tradname,strings_generic_line,intsIndex);
        listView.setAdapter(myAdaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strIndex = strings_id[position];
                String[][] strREAD = {myManage.readAllMainTABLE_Full(0),
                        myManage.readAllMainTABLE_Full(1), myManage.readAllMainTABLE_Full(2),
                        myManage.readAllMainTABLE_Full(3), myManage.readAllMainTABLE_Full(4),
                        myManage.readAllMainTABLE_Full(5), myManage.readAllMainTABLE_Full(6),
                        myManage.readAllMainTABLE_Full(7), myManage.readAllMainTABLE_Full(8),
                        myManage.readAllMainTABLE_Full(9), myManage.readAllMainTABLE_Full(10),
                        myManage.readAllMainTABLE_Full(11), myManage.readAllMainTABLE_Full(12),
                        myManage.readAllMainTABLE_Full(13), myManage.readAllMainTABLE_Full(14),
                        myManage.readAllMainTABLE_Full(15), myManage.readAllMainTABLE_Full(16),
                        myManage.readAllMainTABLE_Full(17), myManage.readAllMainTABLE_Full(18),
                        myManage.readAllMainTABLE_Full(19), myManage.readAllMainTABLE_Full(20)};

                for (int i = 0;i<strREAD[0].length;i++) {
                    if (strIndex.equals(strREAD[0][i])) {
                        Log.d("MedicationListActivity", strREAD[0][i]);

                        Intent intent = new Intent(MedicationListActivity.this, MedicationDetailActivity.class);

                        intent.putExtra("MedicationListActivity_id",strREAD[0][i]);
                        intent.putExtra("MedicationListActivity_med_id",strREAD[1][i]);
                        intent.putExtra("MedicationListActivity_trade_name",strREAD[2][i]);
                        intent.putExtra("MedicationListActivity_generic_line",strREAD[3][i]);
                        intent.putExtra("MedicationListActivity_amount_tablet",strREAD[4][i]);
                        intent.putExtra("MedicationListActivity_which_date_d",strREAD[5][i]);
                        intent.putExtra("MedicationListActivity_appearance",strREAD[6][i]);
                        intent.putExtra("MedicationListActivity_ea",strREAD[7][i]);
                        intent.putExtra("MedicationListActivity_pharmaco",strREAD[8][i]);
                        intent.putExtra("MedicationListActivity_startdate",strREAD[9][i]);
                        intent.putExtra("MedicationListActivity_finishdate",strREAD[10][i]);
                        intent.putExtra("MedicationListActivity_prn",strREAD[11][i]);
                        intent.putExtra("MedicationListActivity_t1",strREAD[12][i]);
                        intent.putExtra("MedicationListActivity_t2",strREAD[13][i]);
                        intent.putExtra("MedicationListActivity_t3",strREAD[14][i]);
                        intent.putExtra("MedicationListActivity_t4",strREAD[15][i]);
                        intent.putExtra("MedicationListActivity_t5",strREAD[16][i]);
                        intent.putExtra("MedicationListActivity_t6",strREAD[17][i]);
                        intent.putExtra("MedicationListActivity_t7",strREAD[18][i]);
                        intent.putExtra("MedicationListActivity_t8",strREAD[19][i]);
                        intent.putExtra("MedicationListActivity_datetimecanceled",strREAD[20][i]);

                        startActivity(intent);

                    }
                }

            }
        });


    }

    private void bindWidget() {
        listView = (ListView) findViewById(R.id.listView_MedicationList);
    }

    private void setHeading() {

        imageAdherence = (ImageButton) findViewById(R.id.imageButtonCalendar2);
        spinner = (Spinner) findViewById(R.id.spinner4);

        MyHeadingDetail myHeadingDetail = new MyHeadingDetail(MedicationListActivity.this);
        myHeadingDetail.spinnersetup(MedicationListActivity.this,imageAdherence,spinner);


    } //setHeading


}  // Main Class
