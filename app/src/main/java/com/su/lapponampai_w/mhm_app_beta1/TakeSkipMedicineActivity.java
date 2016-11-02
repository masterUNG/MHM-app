package com.su.lapponampai_w.mhm_app_beta1;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TakeSkipMedicineActivity extends AppCompatActivity {

    //Explicit
    public static Activity activityTSMActivity;
    String string1,string2,string3, string4,string5,string6,string7,stringId,stringDateRef,stringMain_id;
    TextView textView1,textView2, textView3;
    TextView textViewB1,textViewB2, textViewB3;
    ImageView imageView;
    int[] intsIndex;
    String[] stringsIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_skip_medicine);

        activityTSMActivity = this;

        displayMetrics();

        bindWidget();

        receiveIntent();

        showView();

        setTextButton();

        clickButton();
    }



    private void clickButton() {
        final MyManage myManage = new MyManage(this);
        textViewB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (string5.equals("") && string6.equals("")) {
                    myManage.updatesumTABLE_ADD_SkipHold_Now(stringId);
                    Toast.makeText(getBaseContext(), stringId, Toast.LENGTH_LONG).show();
                    finish();
                } else if (!string5.equals("") && string6.equals("")) {
                    myManage.updatesumTABLE_ADD_SkipHold_Now(stringId);
                    finish();
                } else if (string5.equals("") && !string6.equals("")) {
                    myManage.updatesumTABLE_Canceled_SkipHold(stringId);
                    finish();
                }
            }
        });

        textViewB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (string5.equals("") && string6.equals("")) {
                    Boolean aBoolean = checkAmountTablet(stringMain_id,string3,activityTSMActivity);
                    Log.d("22July16", "aBoolean :" + aBoolean);
                    if (aBoolean) {
                        myManage.updatesumTABLE_ADD_DateCheckTimeCheck_Now(stringId);
                        myManage.updateTotalAmountTABLE_minusTabBy_MainId_AmountTablet(stringMain_id, string3);
                        finish();
                    }

                } else if (!string5.equals("") && string6.equals("")) {
                    myManage.updatesumTABLE_Canceled_ADD_DateCheckTimeCheck(stringId);
                    myManage.updateTotalAmountTABLE_AddTabBy_MainId_AmountTablet(stringMain_id, string3);
                    finish();
                } else if (string5.equals("") && !string6.equals("")) {
                    Boolean aBoolean = checkAmountTablet(stringMain_id,string3,activityTSMActivity);
                    Log.d("22July16", "aBoolean :" + aBoolean);
                    if (aBoolean) {
                        myManage.updatesumTABLE_ADD_DateCheckTimeCheck_Now(stringId);
                        myManage.updateTotalAmountTABLE_minusTabBy_MainId_AmountTablet(stringMain_id, string3);
                        finish();
                    }
                }
            }
        });
        textViewB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public Boolean checkAmountTablet(String MainId, String strTabletUse,Activity activity) {

        MyManage myManage = new MyManage(activity);
        String[] strMain_id = myManage.readAlltotalAmountTABLE(1);
        String[] stringsAmountTABLE = myManage.readAlltotalAmountTABLE(2);
        String strAmountTABLE = null;
        for(int i = 0;i<strMain_id.length;i++) {
            if (strMain_id[i].equals(MainId)) {
                strAmountTABLE = stringsAmountTABLE[i];
            }
        }

        if (strAmountTABLE == null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setIcon(R.drawable.icon_question);
            builder.setTitle("กรุณาเพิ่มเม็ดยา!!!");
            builder.setMessage("ไม่สามารถดำเนินการได้เนื่องจาก\nจำนวนเม็ดยาไม่เพียงพอ\n\nท่านสามารถเพิ่มจำนวนยาที่" +
                    " : \nรายการยา ==> เพิ่มจำนวนยา");
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.show();
            return false;
        } else {

            Double doubleAmountTABLE = Double.parseDouble(strAmountTABLE);
            Double doubleTabletUse = Double.parseDouble(strTabletUse);
            Double doubleResult = doubleAmountTABLE - doubleTabletUse;

            if (doubleResult < 0) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setIcon(R.drawable.icon_question);
                builder.setTitle("จำนวนเม็ดยาของท่านไม่เพียงพอ!!!");
                builder.setMessage("ไม่สามารถดำเนินการได้เนื่องจาก\n" +
                        "จำนวนเม็ดยาไม่เพียงพอ\n\n" +
                        "ท่านสามารถเพิ่มจำนวนยาที่" +
                        " : \nรายการยา ==> เพิ่มจำนวนยา");
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.show();
                return false;

            } else {
                return true;
            }

        }

    }

    private void setTextButton() {
        if (string5.equals("") && string6.equals("")) {
            textViewB1.setText("ข้ามการกิน");
            textViewB2.setText("กินยาตอนนี้");
            textViewB3.setText("ยกเลิก");
        } else if (!string5.equals("") && string6.equals("")) {
            textViewB1.setText("ข้ามการกิน");
            textViewB2.setText("ยกเลิกการกินยา");
            textViewB3.setText("ยกเลิก");
        } else if (string5.equals("") && !string6.equals("")) {
            textViewB1.setText("ยกเลิกการข้าม");
            textViewB2.setText("กินยาตอนนี้");
            textViewB3.setText("ยกเลิก");
        }
    } //setButton

    private void showView() {

        MyData myData = new MyData();
        stringsIndex = new String[1];
        stringsIndex[0] = string2;

        Log.d("abc","ค่าใน StringsIndex[0] :" + stringsIndex[0]);
        intsIndex = myData.translate_Small_Appearance(stringsIndex);

        String stextView1 = "ชื่อยา : " + string1;
        String stextView2 = "จำนวนที่รับประทาน : " + string3 + " " + myData.translate_EA(string7);
        String stextView3 = "เวลาที่รับประทาน : " + string4;

        textView1.setText(stextView1);
        textView2.setText(stextView2);
        textView3.setText(stextView3);
        imageView.setImageResource(intsIndex[0]);
    }

    private void bindWidget() {
        textView1 = (TextView) findViewById(R.id.textViewTSMHeadline);
        textView2 = (TextView) findViewById(R.id.textViewTSML1);
        textView3 = (TextView) findViewById(R.id.textViewTSML2);
        imageView = (ImageView) findViewById(R.id.imageViewTSM);
        textViewB1 = (TextView) findViewById(R.id.textView47);
        textViewB2 = (TextView) findViewById(R.id.textView48);
        textViewB3 = (TextView) findViewById(R.id.textView49);
    }

    private void receiveIntent() {
        string1 = getIntent().getStringExtra("MainActivity_Tradename");
        string2 = getIntent().getStringExtra("MainActivity_Appearance");
        string3 = getIntent().getStringExtra("MainActivity_AmountTablet");
        string4 = getIntent().getStringExtra("MainActivity_TimeRef");
        string5 = getIntent().getStringExtra("MainActivity_DateTimeCheck");
        string6 = getIntent().getStringExtra("MainActivity_SkipHold");
        string7 = getIntent().getStringExtra("MainActivity_EA");
        stringId = getIntent().getStringExtra("MainActivity_Sum_id");
        stringMain_id = getIntent().getStringExtra("MainActivity_Main_id");
        Log.d("22July16", "MainActivity_Main_id : " + stringMain_id);
        stringDateRef = getIntent().getStringExtra("MainActivity_DateRef");
        Log.d("TakeSkip", "Tradename : " + string1);
        Log.d("TakeSkip", "Appearance : " + string2);
        Log.d("TakeSkip", "AmountTablet : " + string3);
        Log.d("TakeSkip", "TimeRef : " + string4);
        Log.d("TakeSkip", "TimeCheck : " + string5);
        Log.d("TakeSkip", "SkipHold : " + string6);
        Log.d("TakeSkip", "Ea : " + string7);
        Log.d("TakeSkip", "Sum_id : " + stringId);


    }

    private void displayMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width*.8),(int) (height*.4));
    }
}
