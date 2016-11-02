package com.su.lapponampai_w.mhm_app_beta1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ForDoctorActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passworeEditText;
    private String userString, passwordString;

    private String[] userStrings = new String[]{"1","user2","user3","user4","user5"};
    private String[] passwordStrings = new String[]{"1","12345","12345","12345","12345"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_doctor);

        userEditText = (EditText) findViewById(R.id.editText);
        passworeEditText = (EditText) findViewById(R.id.editText2);



    } //Main Method

    public void  clickLoginDoctor(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passworeEditText.getText().toString().trim();

        //Check Space
        if (userString.equals("") || passwordString.equals("")) {
            Toast.makeText(this,"Have Space",Toast.LENGTH_SHORT).show();
        } else {

            checkUserPassword();

        }

    } // clicklogin

    private void checkUserPassword() {

        boolean bolStatus = true;
        String truePassword = null;

        for(int i = 0;i<userStrings.length;i++) {

            if (userString.equals(userStrings[i])) {
                bolStatus = !bolStatus;
                truePassword = passwordStrings[i];
            }
            if (bolStatus) {
                Toast.makeText(this, "User False", Toast.LENGTH_SHORT).show();
            } else if (passwordString.equals(truePassword)) {
                startActivity(new Intent(ForDoctorActivity.this, TransferDataActivity.class));
            } else {
                Toast.makeText(this, "Password False", Toast.LENGTH_SHORT).show();
            }

        } // for

    } //checkUserPassword


} //Main Class
