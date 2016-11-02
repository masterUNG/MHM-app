package com.su.lapponampai_w.mhm_app_beta1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    MyManage myManage;

    //Explicit_BindWidget
    private EditText editText_Username, editText_Password, editText_RePassword, editText_Email;
    private Button button_SignUp, button_ClearText;
    private TextView textView;

    //Explicit String ที่รับค่าจาก EditText
    String string_rev_Username;
    String string_rev_Password;
    String string_rev_RePassword;
    String string_rev_Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        myManage = new MyManage(this);

        bindWidget();
        textView.setText("Hospital Number :\n(optional)");



        click_button_SignUp_ClearText();

        click_button_SignUp_SignUp();


    } //Main Method

    private void click_button_SignUp_SignUp() {


        button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string_rev_Username = editText_Username.getText().toString().trim();
                string_rev_Password = editText_Password.getText().toString().trim();
                string_rev_RePassword = editText_RePassword.getText().toString().trim();
                string_rev_Email = editText_Email.getText().toString().trim();

                if (string_rev_Username.equals("") || string_rev_Password.equals("") || string_rev_RePassword.equals("")) {
                    Toast t = Toast.makeText(SignUpActivity.this, "มีค่าว่าง", Toast.LENGTH_SHORT);
                    t.show();
                } else {
                    if (!string_rev_Password.equals(string_rev_RePassword)) {
                        Toast t = Toast.makeText(SignUpActivity.this, "มีค่า Password ไม่เท่ากัน", Toast.LENGTH_SHORT);
                        t.show();
                    } else {
                        // ทำการบันทึกค่าได้
                        Toast t = Toast.makeText(SignUpActivity.this, "addValue _id,User,Password,Stay,Email", Toast.LENGTH_SHORT);
                        t.show();
                        myManage.addValueSignUp(string_rev_Username,string_rev_RePassword,"2",string_rev_Email);

                        // (03/10/2559);; เพิ่ม Update_Last_updated
                        myManage.update_Last_updated(string_rev_Username,"");
                        myManage.update_notification(string_rev_Username,"Default");
                        myManage.update_Allowed_notification(string_rev_Username,"Y");


                        startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                        finish();

                    }
                }
            }
        });

    }




    private void click_button_SignUp_ClearText() {
        button_ClearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_Username.setText(null);
                editText_Password.setText(null);
                editText_RePassword.setText(null);
                editText_Email.setText(null);

                Toast t = Toast.makeText(SignUpActivity.this, "ลบข้อมูลสำเร็จ", Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }

    private void bindWidget() {
        //EditText
        editText_Username = (EditText) findViewById(R.id.editText_SignUp_Username);
        editText_Password = (EditText) findViewById(R.id.editText_SignUp_Password);
        editText_RePassword = (EditText) findViewById(R.id.editText_SignUp_RePassword);
        editText_Email = (EditText) findViewById(R.id.editText_SignUp_Email);

        //Button
        button_SignUp = (Button) findViewById(R.id.button_SignUp_SignUp);
        button_ClearText = (Button) findViewById(R.id.button_SignUp_ClearText);

        //TextView
        textView = (TextView) findViewById(R.id.textView6);

    }


} //Main Class
