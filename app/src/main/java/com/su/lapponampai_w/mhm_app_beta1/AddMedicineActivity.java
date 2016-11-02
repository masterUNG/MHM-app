package com.su.lapponampai_w.mhm_app_beta1;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AddMedicineActivity extends AppCompatActivity {

    //Explicit
    public static Activity activityAddMedicineActivity;
    String stringeditTextAddTG;
    MyManage myManage;

    //นำค่าจาก filterAddMed
    String[] stringsTradename, stringsappearance, stringsGeneric1, stringsGeneric3, stringsGeneric4,
            stringsDosage1, stringsDosage2, stringsDosage3, stringsDosage4, stringsUOM1,
            stringsUOM2, stringsUOM3, stringsUOM4, stringsGeneric2, stringsId;

    //รับค่าจาก stringUOM2-4
    String[] stringsUOM2t, stringsUOM3t, stringsUOM4t;

    //รับค่านำไปใช้สร้าง จาก id
    String stringGenericLine;
    String[] strings_receiver;

    //widget
    EditText editTextAddTG;
    ListView listViewAddTG;
    Button buttonFilterListView;

    //Heading
    Spinner spinner;
    ImageButton imageAdherence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        activityAddMedicineActivity = this;

        myManage = new MyManage(this);



        setHeading();

        bindWidget();


        //pressbuttonfilterListView1();
        pressbuttonfilterListView();


    }




    @Override
    public void onResume() {
        super.onResume();

        setHeading();

        bindWidget();


        //pressbuttonfilterListView1();
        pressbuttonfilterListView();

    }

    private void setHeading() {

        imageAdherence = (ImageButton) findViewById(R.id.imageButton6);
        spinner = (Spinner) findViewById(R.id.spinner2);

        MyHeadingDetail myHeadingDetail = new MyHeadingDetail(AddMedicineActivity.this);
        myHeadingDetail.spinnersetup(AddMedicineActivity.this,imageAdherence,spinner);


    } //setHeading

    private void pressbuttonfilterListView() {
        buttonFilterListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringeditTextAddTG = editTextAddTG.getText().toString().trim();
                //รับค่า
                stringsId = myManage.filterAddMed(0, stringeditTextAddTG); // id
                stringsTradename = myManage.filterAddMed(1, stringeditTextAddTG); //Tradename
                stringsGeneric1 = myManage.filterAddMed(3, stringeditTextAddTG); //Genericname
                stringsDosage1 = myManage.filterAddMed(4, stringeditTextAddTG); //Dosage
                stringsUOM1 = myManage.filterAddMed(5, stringeditTextAddTG); //UOM
                stringsGeneric2 = myManage.filterAddMed(6, stringeditTextAddTG);
                stringsDosage2 = myManage.filterAddMed(7, stringeditTextAddTG);
                stringsUOM2 = myManage.filterAddMed(8, stringeditTextAddTG);
                stringsGeneric3 = myManage.filterAddMed(9, stringeditTextAddTG);
                stringsDosage3 = myManage.filterAddMed(10, stringeditTextAddTG);
                stringsUOM3 = myManage.filterAddMed(11, stringeditTextAddTG);
                stringsGeneric4 = myManage.filterAddMed(12, stringeditTextAddTG);
                stringsDosage4 = myManage.filterAddMed(13, stringeditTextAddTG);
                stringsUOM4 = myManage.filterAddMed(14, stringeditTextAddTG);
                stringsappearance = myManage.filterAddMed(16, stringeditTextAddTG);

                //แปลค่า
                MyData myData = new MyData();

                stringsGeneric1 = myManage.translate_GenericName(stringsGeneric1);
                stringsGeneric2 = myManage.translate_GenericName(stringsGeneric2);
                stringsGeneric3 = myManage.translate_GenericName(stringsGeneric3);
                stringsGeneric4 = myManage.translate_GenericName(stringsGeneric4);


                stringsUOM1 = myData.translate_uomArray(stringsUOM1);
                stringsUOM2t = new String[stringsUOM2.length];
                stringsUOM3t = new String[stringsUOM3.length];
                stringsUOM4t = new String[stringsUOM4.length];

                for(int x = 0; x <stringsUOM2.length;x++) {
                    if (stringsUOM2[x] == null) {
                        stringsUOM2t[x] = "N/A";
                    } else {
                        stringsUOM2t[x] = myData.translate_uom(stringsUOM2[x]);
                    }

                }
                for(int x = 0; x <stringsUOM3.length;x++) {
                    if (stringsUOM3[x] == null) {
                        stringsUOM3t[x] = "N/A";
                    } else {
                        stringsUOM3t[x] = myData.translate_uom(stringsUOM3[x]);
                    }

                }
                for(int x = 0; x <stringsUOM4.length;x++) {
                    if (stringsUOM4[x] == null) {
                        stringsUOM4t[x] = "N/A";
                    } else {
                        stringsUOM4t[x] = myData.translate_uom(stringsUOM4[x]);
                    }

                }



                final String[] stringsGenericLine1 = new String[stringsGeneric1.length];
                for(int i = 0;i < stringsGeneric1.length;i++) {
                    if (stringsGeneric2[i].equals("N/A")) {
                        stringsGenericLine1[i] = stringsGeneric1[i] + " " + stringsDosage1[i] + " " + stringsUOM1[i];
                    } else if (stringsGeneric3[i].equals("N/A")) {
                        stringsGenericLine1[i] = stringsGeneric1[i] + " " + stringsDosage1[i] +
                                " " + stringsUOM1[i] + " / " + stringsGeneric2[i] + " " +
                                stringsDosage2[i] + " " + stringsUOM2t[i];
                    } else if (stringsGeneric4[i].equals("N/A")) {

                        stringsGenericLine1[i] = stringsGeneric1[i] + " " + stringsDosage1[i] +
                                " " + stringsUOM1[i] + " / " + stringsGeneric2[i] + " " +
                                stringsDosage2[i] + " " + stringsUOM2t[i] + " / " +
                                stringsGeneric3[i] + " " + stringsDosage3[i] + " " +
                                stringsUOM3t[i];
                    } else {
                        stringsGenericLine1[i] = stringsGeneric1[i] + " " + stringsDosage1[i] +
                                " " + stringsUOM1[i] + " / " + stringsGeneric2[i] + " " +
                                stringsDosage2[i] + " " + stringsUOM2t[i] + " / " +
                                stringsGeneric3[i] + " " + stringsDosage3[i] + " " +
                                stringsUOM3t[i] + " / " + stringsGeneric4[i] + " " +
                                stringsDosage4[i] + " " + stringsUOM4t[i];
                    }

                } //for

                //ใส่ภาพเข้าไป
                int[] intsIndex = myData.translate_Appearance(stringsappearance);

                MyAdaptor myAdaptor = new MyAdaptor(AddMedicineActivity.this, stringsTradename, stringsGenericLine1, intsIndex);
                listViewAddTG.setAdapter(myAdaptor);


                //ทำการ Click listViewAddTG
                listViewAddTG.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String stringSearchId = stringsId[position];
                        stringGenericLine = stringsGenericLine1[position];
                        strings_receiver = myManage.searchById(stringSearchId); // search ที่จำเป็นต้องใช้มา


                        //นับว่า Default ให้ทานยาวันละกี่ครั้ง
                        ArrayList<String> arrayListstrings = new ArrayList<String>();
                        for(int i = 18;i<=25;i++) {
                            if (!strings_receiver[i].equals("")) {
                                arrayListstrings.add(strings_receiver[i]);
                            }
                        }
                        String sCountTimesPerDay = Integer.toString(arrayListstrings.size());


                        //เปิดหน้าใหม่ พร้อมจำค่าใน putExtra
                        Intent intent = new Intent(AddMedicineActivity.this,AddMedicine2Activity.class);

                        intent.putExtra("Med_id",strings_receiver[0]);
                        intent.putExtra("Trade_name", strings_receiver[1]);
                        intent.putExtra("Generic_line", stringGenericLine);
                        intent.putExtra("Amount_tablet", strings_receiver[26]);
                        intent.putExtra("EA", strings_receiver[27]);
                        intent.putExtra("Which_Date_D", strings_receiver[15]);
                        intent.putExtra("Appearance", strings_receiver[16]);
                        intent.putExtra("Pharmaco", strings_receiver[17]);
                        intent.putExtra("T1",strings_receiver[18]);
                        intent.putExtra("T2",strings_receiver[19]);
                        intent.putExtra("T3",strings_receiver[20]);
                        intent.putExtra("T4",strings_receiver[21]);
                        intent.putExtra("T5",strings_receiver[22]);
                        intent.putExtra("T6",strings_receiver[23]);
                        intent.putExtra("T7",strings_receiver[24]);
                        intent.putExtra("T8",strings_receiver[25]);
                        intent.putExtra("TimesPerDay", sCountTimesPerDay);

                        startActivity(intent);

                    }
                });


            }
        });
    }

    private void pressbuttonfilterListView1() {

        buttonFilterListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringeditTextAddTG = editTextAddTG.getText().toString().trim();
                //รับค่า
                stringsTradename = myManage.filterAddMed(1, stringeditTextAddTG);
                stringsGeneric1 = myManage.filterAddMed(3, stringeditTextAddTG);
                stringsDosage1 = myManage.filterAddMed(4, stringeditTextAddTG);
                stringsUOM1 = myManage.filterAddMed(5, stringeditTextAddTG);
                stringsGeneric2 = myManage.filterAddMed(6, stringeditTextAddTG);
                stringsDosage2 = myManage.filterAddMed(7, stringeditTextAddTG);
                stringsUOM2 = myManage.filterAddMed(8, stringeditTextAddTG);
                stringsGeneric3 = myManage.filterAddMed(9, stringeditTextAddTG);
                stringsDosage3 = myManage.filterAddMed(10, stringeditTextAddTG);
                stringsUOM3 = myManage.filterAddMed(11, stringeditTextAddTG);
                stringsGeneric4 = myManage.filterAddMed(12, stringeditTextAddTG);
                stringsDosage4 = myManage.filterAddMed(13, stringeditTextAddTG);
                stringsUOM4 = myManage.filterAddMed(14, stringeditTextAddTG);
                stringsappearance = myManage.filterAddMed(16, stringeditTextAddTG);


                //แปลค่า
                MyData myData = new MyData();


                stringsGeneric1 = myManage.translate_GenericName(stringsGeneric1);
                stringsGeneric2 = myManage.translate_GenericName(stringsGeneric2);
                stringsGeneric3 = myManage.translate_GenericName(stringsGeneric3);
                stringsGeneric4 = myManage.translate_GenericName(stringsGeneric4);

                stringsUOM1 = myData.translate_uomArray(stringsUOM1);
                stringsUOM2 = myData.translate_uomArray(stringsUOM2);
                stringsUOM3 = myData.translate_uomArray(stringsUOM3);
                stringsUOM4 = myData.translate_uomArray(stringsUOM4);

                //ทำ String ยาวๆ ของ 2 บรรทัด
                String[] stringsGenericLine1 = new String[stringsGeneric1.length];
                for(int i = 0;i < stringsGeneric1.length;i++) {
                    if (!stringsGeneric4.equals("")) {
                        stringsGenericLine1[i] = stringsGeneric1[i] + " " + stringsDosage1[i] +
                                " " + stringsUOM1[i] + " / " + stringsGeneric2[i] + " " +
                                stringsDosage2[i] + " " + stringsUOM2[i] + " / " +
                                stringsGeneric3[i] + " " + stringsDosage3[i] + " " +
                                stringsUOM3[i] + " / " + stringsGeneric4[i] + " " +
                                stringsDosage4[i] + " " + stringsUOM4[i];
                    } else if (!stringsGeneric3.equals("")){
                        stringsGenericLine1[i] = stringsGeneric1[i] + " " + stringsDosage1[i] +
                                " " + stringsUOM1[i] + " / " + stringsGeneric2[i] + " " +
                                stringsDosage2[i] + " " + stringsUOM2[i] + " / " +
                                stringsGeneric3[i] + " " + stringsDosage3[i] + " " +
                                stringsUOM3[i];
                    } else if (!stringsGeneric2.equals("")) {
                        stringsGenericLine1[i] = stringsGeneric1[i] + " " + stringsDosage1[i] +
                                " " + stringsUOM1[i] + " / " + stringsGeneric2[i] + " " +
                                stringsDosage2[i] + " " + stringsUOM2[i];
                    } else {
                        stringsGenericLine1[i] = stringsGeneric1[i] + " " + stringsDosage1[i] + " " + stringsUOM1[i];
                    }
                }


                int[] intsIndex = myData.translate_Appearance(stringsappearance);


                MyAdaptor myAdaptor = new MyAdaptor(AddMedicineActivity.this, stringsTradename, stringsGenericLine1, intsIndex);
                listViewAddTG.setAdapter(myAdaptor);

            }
        });
    }



    private void bindWidget() {
        editTextAddTG = (EditText) findViewById(R.id.editText_Add_TG);
        listViewAddTG = (ListView) findViewById(R.id.listView_Add_TG);
        buttonFilterListView = (Button) findViewById(R.id.buttonFilterListView);

    }
}
