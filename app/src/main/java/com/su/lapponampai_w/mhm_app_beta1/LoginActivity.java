package com.su.lapponampai_w.mhm_app_beta1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    MyManage myManage;

    //ปุ่มต่างๆจาก activity_login.xml
    Button buttonLogin;
    EditText editTextUser, editTextPassword;
    CheckBox checkBoxlogin;

    //ค่า String ของ editText
    String stringUser, stringPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myManage = new MyManage(this);

        // เชื่อมปุ่มต่างๆ
        bindWidget();
        checkBoxlogin.setVisibility(View.GONE);

        // เมื่อทำการกดปุ่ม buttonLogin
        Click_buttonLogin();


    } //Main Method

    public void clickForgotPassword(View view) {

        Log.d("2novV1", "Click ForgotPassword");

        String strSubject = "Your Password";
        String strBody = "Your Password 123456";
        String strEmail = "easy4comworkshop@gmail.com";

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setData(Uri.parse("mailto:" + strEmail));
        intent.setType("text/plain");
        //intent.putExtra(Intent.EXTRA_EMAIL, strEmail);
        intent.putExtra(Intent.EXTRA_SUBJECT, strSubject);
        intent.putExtra(Intent.EXTRA_TEXT, strBody);

        try {

            startActivity(Intent.createChooser(intent, "โปรเลือกโปรแกรมส่งเมล"));

        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(this, "ไม่มีแอพส่งเมล นะ", Toast.LENGTH_SHORT).show();
        }



    }   // forgot

    private void Click_buttonLogin() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringUser = editTextUser.getText().toString().trim();
                stringPassword = editTextPassword.getText().toString().trim();

                if (stringUser.equals("") || stringPassword.equals("")) {
                    Toast t = Toast.makeText(LoginActivity.this, "มีค่าว่าง", Toast.LENGTH_SHORT);
                    t.show();
                } else {
                    checkPasswordLoginAndGoToMainActivity();
                }


            }
        });

    } //Click_buttonLogin Method

    private void checkPasswordLoginAndGoToMainActivity() {

        stringUser = editTextUser.getText().toString().trim();
        stringPassword = editTextPassword.getText().toString().trim();
        String[] strUsername = myManage.readSQLite_userTABLE(1);
        String[] strPassword = myManage.readSQLite_userTABLE(2);

        String arrayIndexStringUsername = myManage.getArrayStringIndex(strUsername, stringUser);
        String arrayIndexStringPassword = myManage.getArrayStringIndex(strPassword, stringPassword);




        if (arrayIndexStringUsername.equals("-555") || arrayIndexStringPassword.equals("-555")) {
            Toast t = Toast.makeText(LoginActivity.this, "ไม่มี Username หรือ Password (-555)", Toast.LENGTH_SHORT);
            t.show();
        } else if (!arrayIndexStringUsername.equals(arrayIndexStringPassword)) {
            Toast t = Toast.makeText(LoginActivity.this, "Username ไม่สัมพันธ์กับ password", Toast.LENGTH_SHORT);
            t.show();
        } else if (arrayIndexStringUsername.equals(arrayIndexStringPassword)) {

             if (checkBoxlogin.isChecked()) {

                //ทำการเพิ่มค่า Stay เป็น 1
                myManage.updateStayLogin(stringUser,"1");

             }

                //gotoMainActivity
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();


        } else {
            Toast t = Toast.makeText(LoginActivity.this, "Cannot Define What's Happen!!!!!", Toast.LENGTH_SHORT);
            t.show();
        }

    }

    private void bindWidget() {
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextUser = (EditText) findViewById(R.id.editTextLoginUser);
        editTextPassword = (EditText) findViewById(R.id.editTextLoginPassword);
        checkBoxlogin = (CheckBox) findViewById(R.id.checkBox_login);
    } // bindWidget Method


}  //Main Class
