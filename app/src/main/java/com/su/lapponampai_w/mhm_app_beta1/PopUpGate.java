package com.su.lapponampai_w.mhm_app_beta1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;

public class PopUpGate extends AppCompatActivity {

    //Explicit
    TextView textViewName,textViewOK,textViewCancel;
    EditText editTextPassword;
    MyManage myManage;
    String string_btn_pop1,string_btn_pop2,string_btn_pop3,string_btn_pop4,string_MedicatinList,string_News,
            string_setting,string_NotificationGate,string_NotificationGate_Sum_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_gate);

        myManage = new MyManage(this);

        receiveIntent();

        bindWidget();

        displayMetrics();

        showView();

        clickOKCancel();


    }

    private void receiveIntent() {

        string_btn_pop1 = getIntent().getStringExtra("btn_pop1");
        string_btn_pop2 = getIntent().getStringExtra("btn_pop2");
        string_btn_pop3 = getIntent().getStringExtra("btn_pop3");
        string_btn_pop4 = getIntent().getStringExtra("btn_pop4");
        string_MedicatinList = getIntent().getStringExtra("MedicationList");
        string_News = getIntent().getStringExtra("News");
        string_setting = getIntent().getStringExtra("Setting");
        string_NotificationGate = getIntent().getStringExtra("NotificationGate");
        string_NotificationGate_Sum_id = getIntent().getStringExtra("NotificationGate_SumId");


    }

    private void clickOKCancel() {

        //Click OK
        textViewOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] stringsPassword = myManage.readSQLite_userTABLE(2); //Password
                String strTestPassword = editTextPassword.getText().toString().trim();

                if (strTestPassword.equals("")) {
                    Toast.makeText(getBaseContext(), "กรุณากรอกรหัสผ่าน", Toast.LENGTH_SHORT).show();
                } else if (!stringsPassword[0].equals(strTestPassword)) {
                    Toast.makeText(getBaseContext(), "รหัสผ่านผิดพลาด", Toast.LENGTH_SHORT).show();
                    if (string_NotificationGate != null) {
                        //int iCount = 0;

                    } else {
                        finish();
                    }

                } else {
                    String[] stringsSendIntent = {string_btn_pop1,string_btn_pop2,string_btn_pop3,
                            string_btn_pop4,string_MedicatinList,string_News,string_setting,string_NotificationGate};
                    for(int i = 0; i < stringsSendIntent.length;i++) {
                        if (stringsSendIntent[i] != null) {
                            if (stringsSendIntent[i].equals(string_btn_pop1) ||
                                    stringsSendIntent[i].equals(string_btn_pop2) ||
                                    stringsSendIntent[i].equals(string_btn_pop3) ||
                                    stringsSendIntent[i].equals(string_btn_pop4)) {
                                Intent intent = new Intent(PopUpGate.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("PopUpMaster", stringsSendIntent[i]);
                                Toast.makeText(getBaseContext(),stringsSendIntent[i], Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            } else if (stringsSendIntent[i].equals(string_MedicatinList)) {
                                Intent intent = new Intent(PopUpGate.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("PopUpMaster", stringsSendIntent[i]);
                                Toast.makeText(getBaseContext(),stringsSendIntent[i], Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            } else if (stringsSendIntent[i].equals(string_News)) {
                                Intent intent = new Intent(PopUpGate.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("PopUpMaster", stringsSendIntent[i]);
                                Toast.makeText(getBaseContext(), stringsSendIntent[i], Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            } else if (stringsSendIntent[i].equals(string_setting)) {
                                Intent intent = new Intent(PopUpGate.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("PopUpMaster", stringsSendIntent[i]);
                                Toast.makeText(getBaseContext(), stringsSendIntent[i], Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            } else if (stringsSendIntent[i].equals(string_NotificationGate)) {
                                Intent intent = new Intent(PopUpGate.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("PopUpMaster", stringsSendIntent[i]);
                                intent.putExtra("SumId_AlarmReceiver", string_NotificationGate_Sum_id);
                                Toast.makeText(getBaseContext(), stringsSendIntent[i], Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                }
            }
        });

        //Click Cancel
        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (string_NotificationGate != null) {
                    finish();
                    //TakeSkipMedicineActivity.activityTSMActivity.finish();
                    MainActivity.activityMainActivity.finish();


                } else {
                    finish();
                }


            }
        });



    }

    private void showView() {
        String[] stringsUserName = myManage.readSQLite_userTABLE(1); //userName

        textViewName.setText(stringsUserName[0].concat(" :"));

    }

    private void displayMetrics() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width*.7),(int) (height*.3));
    }



    private void bindWidget() {

        textViewName = (TextView) findViewById(R.id.textView191);
        editTextPassword = (EditText) findViewById(R.id.editText19);
        textViewOK = (TextView) findViewById(R.id.textView194);
        textViewCancel = (TextView) findViewById(R.id.textView192);

    }


}
