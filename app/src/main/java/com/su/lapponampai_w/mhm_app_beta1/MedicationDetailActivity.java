package com.su.lapponampai_w.mhm_app_beta1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MedicationDetailActivity extends AppCompatActivity {


    //Explicit
    private TextView textView1,textView2, textView3,textView4,textView5,textView6,textView7,
            textView8,textView9,textView10,textView11,textView12,textView13,textView14,textView15,textView16;
    private TextView textViewAddAmountMedicine, textViewtotalAmountTablet,
            textViewDeleteMedicine,textViewAddDoseNow,textViewOutOfMedicine,textViewOutOfMedicineLabel;
    private ImageView imageView1;
    //receiveIntent
    private String string0,string1,string2,string3,string4,string5,string6,string7,string7_Translate,
            string8,string9,string10,string11,string12,string13,string14,string15,string16,
            string17,string18,string19,string20,s_Amount;
    public Activity activityMedicationDetail;
    //Heading
    Spinner spinner;
    ImageButton imageAdherence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_detail);

        activityMedicationDetail = MedicationDetailActivity.this;
        setHeading();
        bindWidget();

        //Receive From Intent
        receiveIntent();

        //Show View
        showView();

        //ดูว่าเกินวันที่ต้องกินยาแล้ว.. ต้องการลบข้อมูลทิ้งหรือไม่
        checkdateFinishAndCancel();

        //Click AddAmountMedicine
        clickAddAmountMedicine();

        //Click Delete Medicine
        clickDeleteMedicine();

        //show จำนวนเม็ดยาคงเหลือ
        showtotalAmountTablet();

        clickAddDose();

        showAmountDateOut();

        //Log.d("MedicationDetail1","string0 : " + string0);

    }

    private void checkdateFinishAndCancel() {
        MyData myData = new MyData();

        //Checkวันที่ก่อนว่าถึงวันกินวันเริ่มต้น หรือว่าเลยวันสิ้นสุดไปแล้วหรือยัง
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strCurrentDay = myData.currentDay();
        Date dateFinish = new Date();
        Date dateCurrent = new Date();

        if (!string10.equals("")) {

            try {
                dateCurrent = simpleDateFormat.parse(strCurrentDay);
                dateFinish = simpleDateFormat.parse(string10);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (dateFinish.compareTo(dateCurrent) < 0) {
                //ทำ DialogBox
                AlertDialog.Builder builder = new AlertDialog.Builder(MedicationDetailActivity.this);
                builder.setIcon(R.drawable.icon_question);
                builder.setTitle("ครบกำหนดทานยาตัวดังกล่าวแล้ว");
                builder.setMessage("ท่านต้องการลบยาออกจากฐานข้อมูล หรือไม่");
                builder.setNegativeButton("ไม่ต้องการ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("ต้องการ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyManage myManage = new MyManage(MedicationDetailActivity.this);
                        myManage.updatemainTABLE_DateTimeCanceled(string0);
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.show();
            }

        }

    } //checkdateFinishAndCancel

    private void showAmountDateOut() {

        if (s_Amount != null) {
            if (string7.equals("1") || string7.equals("2")) { //เป็นยาเม็ดหรือ แคปซูล
                //เป็น prn หรือไม่
                if (string11.equals("Y")) {
                    textViewOutOfMedicineLabel.setVisibility(View.GONE);
                    textViewOutOfMedicine.setVisibility(View.GONE);
                } else {

                    Calendar calendarCurrentDay = Calendar.getInstance();
                    String[] stringsTimesPerDay = {string12, string13, string14, string15,
                            string16, string17, string18, string19};
                    int iCount = 0;
                    for (int i = 0; i < stringsTimesPerDay.length; i++) {
                        if (!stringsTimesPerDay[i].equals("")) {
                            iCount = iCount + 1;
                        }
                    }

                    Log.d("21July16", "s_Amount : " + s_Amount);
                    //int x = Integer.parseInt(s_Amount) / (iCount * Integer.parseInt(string4));

                    double y = Double.parseDouble(s_Amount) / (iCount * Double.parseDouble(string4));
                    int x = Integer.parseInt(s_Amount) / (iCount * Integer.parseInt(string4));
                    Log.d("21July16", "y : " + y);
                    Log.d("21July16", "string4 : " + string4);
                    String strGetDate;

                    //คำนวนวันในการจะแสดงผล
                    MyData myData = new MyData();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String strCurrentDay = myData.currentDay();
                    Date dateInitial = new Date();
                    Date dateFinish = new Date();
                    Date dateCurrent = new Date();

                    if (y == 0) {
                        strGetDate = "ไม่มียา!!!";
                        textViewOutOfMedicine.setText(strGetDate);
                    }

                        try {
                            dateInitial = simpleDateFormat.parse(string9);
                            dateCurrent = simpleDateFormat.parse(strCurrentDay);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (dateInitial.compareTo(dateCurrent) > 0) {
                            if (y < 1 && y > 0) {
                                //set วันตาม string 9

                                textViewOutOfMedicine.setText(string9);
                            } else {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dateInitial);
                                calendar.add(Calendar.DAY_OF_MONTH, x - 1);
                                Date date = calendar.getTime();
                                strGetDate = simpleDateFormat.format(date);
                                textViewOutOfMedicine.setText(strGetDate);

                            }
                        } else {
                            if (y < 1 && y > 0) {
                                strGetDate = "ยาเหลือไม่ถึง 1 วัน";
                                textViewOutOfMedicine.setText(strGetDate);
                            } else {

                                calendarCurrentDay.add(Calendar.DAY_OF_MONTH, x - 1);

                                Date date = calendarCurrentDay.getTime();

                                strGetDate = simpleDateFormat.format(date);
                                textViewOutOfMedicine.setText(strGetDate);
                            }
                        }  //จบแบบไม่มี FinishDate



                } // if (string11.equals("Y"))

            } else { //if (!stringsTimesPerDay[i].equals(""))
                textViewOutOfMedicineLabel.setVisibility(View.GONE);
                textViewOutOfMedicine.setVisibility(View.GONE);
            } //for
        } else {
            textViewOutOfMedicine.setText("ไม่มียา!!!");
        }  //if(s_amount !=null)


    }  //showAmountDateOut

    private void clickAddDose() {

        textViewAddDoseNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("25JulyV2", "clickAddDose");
                MyData myData = new MyData();
                MyManage myManage = new MyManage(MedicationDetailActivity.this);

                //Checkวันที่ก่อนว่าถึงวันกินวันเริ่มต้น หรือว่าเลยวันสิ้นสุดไปแล้วหรือยัง
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String strCurrentDay = myData.currentDay();
                Date dateInitial = new Date();
                Date dateFinish = new Date();
                Date dateCurrent = new Date();

                Log.d("25JulyV2", string10);

                if (string10.equals("")) {
                    try {
                        dateInitial = simpleDateFormat.parse(string9);
                        dateCurrent = simpleDateFormat.parse(strCurrentDay);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (dateInitial.compareTo(dateCurrent) > 0) {
                        Toast.makeText(MedicationDetailActivity.this, "ไม่สามารถทำรายการดังกล่าวได้ " +
                                "เนื่องจากยังไม่ถึงวันที่กำหนด", Toast.LENGTH_LONG).show();
                        return;
                    }

                } else {
                    try {
                        dateInitial = simpleDateFormat.parse(string9);
                        dateCurrent = simpleDateFormat.parse(strCurrentDay);
                        dateFinish = simpleDateFormat.parse(string10);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (dateInitial.compareTo(dateCurrent) > 0) {
                        Toast.makeText(MedicationDetailActivity.this, "ไม่สามารถทำรายการดังกล่าวได้ " +
                                "เนื่องจากยังไม่ถึงวันที่กำหนด", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (dateFinish.compareTo(dateCurrent) < 0) {
                        Toast.makeText(MedicationDetailActivity.this, "ไม่สามารถทำรายการดังกล่าวได้ " +
                                "เนื่องจากเกินวันที่กำหนดแล้ว", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                //Checkก่อนมีเม็ดยาให้กินป่าว
                TakeSkipMedicineActivity takeSkipMedicineActivity = new TakeSkipMedicineActivity();
                Boolean aBoolean = takeSkipMedicineActivity.checkAmountTablet(string0,string4,activityMedicationDetail);


                if (aBoolean) {
                    String strCurrentTime = myData.currentTime_Minus();

                    myManage.addValueToSumTable(string0, strCurrentDay, strCurrentTime, strCurrentDay, strCurrentTime, "");
                    myManage.updateTotalAmountTABLE_minusTabBy_MainId_AmountTablet(string0, string4);
                    Toast.makeText(MedicationDetailActivity.this,"เสร็จสิ้นการทำงาน",Toast.LENGTH_SHORT).show();
                    MedicationListActivity.activityMedicationListActivity.finish();
                    finish();
                }



            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        //show จำนวนเม็ดยาคงเหลือ
        showtotalAmountTablet();
        showAmountDateOut();

    }

    private void clickDeleteMedicine() {

        textViewDeleteMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MedicationDetailActivity.this);
                builder.setIcon(R.drawable.icon_question);
                builder.setTitle("ลบยาออกจากระบบ!!!");
                builder.setMessage("ท่านต้องการลบยาออกจากฐานข้อมูล (ทั้งชื่อยาและจำนวนยา)");
                builder.setPositiveButton("ลบข้อมูล", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MedicationDetailActivity.this,"ลบข้อมูลเสร็จสิ้น",Toast.LENGTH_SHORT).show();
                        MyManage myManage = new MyManage(MedicationDetailActivity.this);
                        myManage.updatemainTABLE_DateTimeCanceled(string0);
                        //11/10/59.... ทำการลบตาม main_id..คือ string0 คัด id ของ sumTABLE ออกในลักษณะ loop
                        for(int i = 0;i<=9;i++) {
                            MyData myData = new MyData();
                            String sDate = myData.currentDay();
                            Date dDate = myData.stringChangetoDateWithOutTime(sDate);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(dDate);
                            calendar.add(Calendar.DAY_OF_MONTH,i);
                            dDate = calendar.getTime();
                            String sSpecificDate = myData.string_ddMMyyyy_ConvertedFromSpecificDate(dDate);

                            String[] strsumTABLE_filterID = myManage.filter_sumTABLE_by_Main_id_AND_DateRef
                                    (string0, sSpecificDate, 0);
                            //String[] strsumTABLE_filterMain_id = myManage.filter_sumTABLE_by_Main_id_AND_DateRef
                            //        (string0, sSpecificDate, 1);
                            //String[] strsumTABLE_filterDateRef = myManage.filter_sumTABLE_by_Main_id_AND_DateRef
                            //        (string0, sSpecificDate, 2);
                            String[] strsumTABLE_filterDateCheck = myManage.filter_sumTABLE_by_Main_id_AND_DateRef
                                    (string0, sSpecificDate, 4);
                            String[] strsumTABLE_filterSkipHold = myManage.filter_sumTABLE_by_Main_id_AND_DateRef
                                    (string0, sSpecificDate, 6);

                            if (!strsumTABLE_filterID[0].equals("")) {
                                for(int x = 0;x < strsumTABLE_filterID.length;x++) {
                                    if (strsumTABLE_filterDateCheck[x].equals("") &&
                                            strsumTABLE_filterSkipHold[x].equals("")) {
                                        //ลบ ตาม id ใน sumTABLE
                                        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyHelper.DATABASE_NAME, MODE_PRIVATE, null);
                                        sqLiteDatabase.delete("sumTABLE", "_id = " + strsumTABLE_filterID[x], null);
                                    }
                                } //for
                            } // มีค่า ของ main_id ในวันนั้นๆ

                        } //for ;;;ทำการลบค่าใน sumTABLE ค่าที่ต้องการลบ

                        MedicationListActivity.activityMedicationListActivity.finish();
                        dialog.dismiss();
                        finish();
                        SplashScreen splashScreen = new SplashScreen();
                        //MyData myData = new MyData();
                        splashScreen.updateDailyBroadcast(getBaseContext());
                        //ลบข้อมูล
                    }
                });
                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });

    }

    private void showtotalAmountTablet() {
        MyManage myManage = new MyManage(this);
        MyData myData = new MyData();
        String[][] stringstotalAmount = {myManage.readAlltotalAmountTABLE(0),
                myManage.readAlltotalAmountTABLE(1),myManage.readAlltotalAmountTABLE(2),
                myManage.readAlltotalAmountTABLE(3)};
        String s_totalAmount = "N/A";
        String s_totalAmount_null = "Y";

        if (stringstotalAmount[0][0].equals("")) {
            //Toast.makeText(MedicationDetailActivity.this,"a;dsjf;sdfj",Toast.LENGTH_LONG).show();
            s_totalAmount = "0 ";

        } else {
            // ใส่จำนวนเม็ดใน s_totalAmount ... ค่า mainTABLE ค่า 1
            String[][] stringstotalAmountTABLE = {myManage.readAlltotalAmountTABLE(0),
                    myManage.readAlltotalAmountTABLE(1),myManage.readAlltotalAmountTABLE(2),
                    myManage.readAlltotalAmountTABLE(3)};

            for(int i = 0;i<stringstotalAmountTABLE[0].length;i++) {
                if (stringstotalAmountTABLE[1][i].equals(string0)) {
                    s_totalAmount = stringstotalAmountTABLE[2][i];
                    s_Amount = s_totalAmount;
                    Log.d("21July16", "s_Amount first :" + s_Amount);
                    s_totalAmount = s_totalAmount.concat(" ");
                    s_totalAmount_null = "N";
                }
            }

            if (s_totalAmount_null.equals("Y")) {
                s_totalAmount = "0";
                s_Amount = s_totalAmount;
                s_totalAmount = s_totalAmount.concat(" ");
            }
        }

        string7_Translate = myData.translate_EA(string7);
        s_totalAmount = s_totalAmount.concat(string7_Translate);
        textViewtotalAmountTablet.setText(s_totalAmount);

    }

    private void clickAddAmountMedicine() {
        textViewAddAmountMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MedicationDetailActivity.this, PopUpAddAmountMedicine.class);


                intent.putExtra("MedicationDetailActivity_id", string0);
                startActivity(intent);

            }
        });

    }

    private void showView() {

        MyData myData = new MyData();
        String[] stringAppearance = new String[1];
        stringAppearance[0] = string6;
        int[] intsIndex = myData.translate_Appearance(stringAppearance);
        imageView1.setBackgroundResource(intsIndex[0]);

        textView1.setText(string2);
        textView2.setText(string3);
        //แปลงหน่วย
        string7_Translate = myData.translate_EA(string7);

        textView3.setText("รับประทานครั้งละ " + string4 + " " + string7_Translate);

        String string5_Translate = myData.translate_Which_Date_D(string5);
        textView4.setText(string5_Translate);

        textView5.setText("วันที่เริ่มต้นรับประทานยา : " + string9);


        if (string10.equals("")) {
            textView6.setVisibility(View.GONE);
        } else {
            textView6.setText("วันสิ้นสุดรับประทานยา : " + string10);
        }

            textView7.setText("เวลาในการรับประทานยา :");

        if (string11.equals("Y")) {
            textView8.setText("รับประทานยาเป็นครั้งคราว!!!");
            //เริ่มตรงนี้
            textView9.setVisibility(View.GONE);
            textView10.setVisibility(View.GONE);
            textView11.setVisibility(View.GONE);
            textView12.setVisibility(View.GONE);
            textView13.setVisibility(View.GONE);
            textView14.setVisibility(View.GONE);
            textView15.setVisibility(View.GONE);
            textView4.setVisibility(View.GONE);








        } else if (string11.equals("N")) {
            TextView[] textViews = {textView8, textView9, textView10, textView11, textView12,
                    textView13, textView14, textView15};
            String[] strings = {string12,string13,string14,string15,string16,string17,
                    string18,string19};

            for(int i=0;i<strings.length;i++) {
                if (strings[i].equals("")) {
                    textViews[i].setVisibility(View.GONE);
                } else {
                    textViews[i].setText("เวลาที่ " + Integer.toString(i+1) + " :  " + strings[i] + " น.");
                }
            }
        }






    }  //showView

    private void receiveIntent() {


        string0 = getIntent().getStringExtra("MedicationListActivity_id");
        string1 = getIntent().getStringExtra("MedicationListActivity_med_id");
        string2 = getIntent().getStringExtra("MedicationListActivity_trade_name");
        string3 = getIntent().getStringExtra("MedicationListActivity_generic_line");
        string4 = getIntent().getStringExtra("MedicationListActivity_amount_tablet");
        string5 = getIntent().getStringExtra("MedicationListActivity_which_date_d");
        string6 = getIntent().getStringExtra("MedicationListActivity_appearance");
        string7 = getIntent().getStringExtra("MedicationListActivity_ea");
        string8 = getIntent().getStringExtra("MedicationListActivity_pharmaco");
        string9 = getIntent().getStringExtra("MedicationListActivity_startdate");
        string10 = getIntent().getStringExtra("MedicationListActivity_finishdate");
        string11 = getIntent().getStringExtra("MedicationListActivity_prn");
        string12 = getIntent().getStringExtra("MedicationListActivity_t1");
        string13 = getIntent().getStringExtra("MedicationListActivity_t2");
        string14 = getIntent().getStringExtra("MedicationListActivity_t3");
        string15 = getIntent().getStringExtra("MedicationListActivity_t4");
        string16 = getIntent().getStringExtra("MedicationListActivity_t5");
        string17 = getIntent().getStringExtra("MedicationListActivity_t6");
        string18 = getIntent().getStringExtra("MedicationListActivity_t7");
        string19 = getIntent().getStringExtra("MedicationListActivity_t8");
        string20 = getIntent().getStringExtra("MedicationListActivity_datetimecanceled");
    }

    private void bindWidget() {
        imageView1 = (ImageView) findViewById(R.id.imageView3); //รูปเม็ดยา
        textView1 = (TextView) findViewById(R.id.textView66); //ชื่อการค้า
        textView2 = (TextView) findViewById(R.id.textView20); //ชื่อสามัญทางยา
        textView3 = (TextView) findViewById(R.id.textView62); //รับประทานครั้งละ .... EA
        textView4 = (TextView) findViewById(R.id.textView64);
        textView5 = (TextView) findViewById(R.id.textView68); //StartDate
        textView6 = (TextView) findViewById(R.id.textView69); //FinishDate
        textView7 = (TextView) findViewById(R.id.textView70); //เวลาที่รับประทาน,เป็นครั้งคราว

        textView8 = (TextView) findViewById(R.id.textView71); //t1
        textView9 = (TextView) findViewById(R.id.textView72); //t2
        textView10 = (TextView) findViewById(R.id.textView73); //t3
        textView11 = (TextView) findViewById(R.id.textView74); //t4
        textView12 = (TextView) findViewById(R.id.textView75); //t5
        textView13 = (TextView) findViewById(R.id.textView76); //t6
        textView14 = (TextView) findViewById(R.id.textView77); //t7
        textView15 = (TextView) findViewById(R.id.textView78); //t8


        textViewAddAmountMedicine = (TextView) findViewById(R.id.textView79); //เพิ่มจำนวนยา
        textViewtotalAmountTablet = (TextView) findViewById(R.id.textView88); //จำนวนยาคงเหลือพร้อม UOM
        textViewDeleteMedicine = (TextView) findViewById(R.id.textView51); //ลบยาออกจากระบบ
        textViewAddDoseNow = (TextView) findViewById(R.id.textView82); //กินยาเพิ่มตอนนี้!!!
        textViewOutOfMedicine = (TextView) findViewById(R.id.textView157); //วันที่ยาหมด
        textViewOutOfMedicineLabel = (TextView) findViewById(R.id.textView156);


    }

    private void setHeading() {

        imageAdherence = (ImageButton) findViewById(R.id.imageButton8);
        spinner = (Spinner) findViewById(R.id.spinner5);

        MyHeadingDetail myHeadingDetail = new MyHeadingDetail(MedicationDetailActivity.this);
        myHeadingDetail.spinnersetup(MedicationDetailActivity.this,imageAdherence,spinner);


    } //setHeading
}
