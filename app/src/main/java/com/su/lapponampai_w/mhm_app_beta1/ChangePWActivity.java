package com.su.lapponampai_w.mhm_app_beta1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePWActivity extends AppCompatActivity {

    //Explicit
    TextView textViewid, textViewPW, textViewNewPW, textViewNewPW2;
    EditText editTextPW, editTextNewPW, editTextNewPW2;
    MyManage myManage;
    Button buttonCancel, buttonSave;
    String str_PW, str_NewPW, str_NewPW2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        myManage = new MyManage(this);

        bindWidget();

        showView();

        clickSaveCancelButtom();



    }

    private void clickSaveCancelButtom() {

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_PW = editTextPW.getText().toString().trim();
                str_NewPW = editTextNewPW.getText().toString().trim();
                str_NewPW2 = editTextNewPW2.getText().toString().trim();


                String[] str_PWFromDataBase = myManage.filter_userTABLE(2);
                if (!str_PWFromDataBase[0].equals(str_PW)) {
                    Toast.makeText(getBaseContext(), "รหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                    editTextPW.setText("");
                    editTextNewPW.setText("");
                    editTextNewPW2.setText("");
                } else {
                    if (!str_NewPW2.equals(str_NewPW)) {
                        Toast.makeText(getBaseContext(), "รหัสผ่านใหม่ 2 ครั้งไม่เหมือนกัน", Toast.LENGTH_SHORT).show();
                        editTextPW.setText("");
                        editTextNewPW.setText("");
                        editTextNewPW2.setText("");
                    } else {
                        Toast.makeText(getBaseContext(), "เปลี่ยนรหัสผ่านสำเร็จ", Toast.LENGTH_SHORT).show();
                        String[] strUser = myManage.filter_userTABLE(1); //ค่า id
                        myManage.update_New_PW(strUser[0], str_NewPW);
                        finish();
                        SettingActivity.settingActivity.finish();
                    }
                }




            }
        });


    }

    private void showView() {

        String[] strUser = myManage.filter_userTABLE(1); //ค่า id
        textViewid.setText("ไอดีผู้ใช้ : ".concat(strUser[0]));

        textViewPW.setText("รหัสผ่าน :");
        textViewNewPW.setText("รหัสผ่านใหม่ :");
        textViewNewPW2.setText("รหัสผ่านใหม่(อีกครั้ง) :");



    }

    private void bindWidget() {

        textViewid = (TextView) findViewById(R.id.textView178);
        textViewPW = (TextView) findViewById(R.id.textView175);
        textViewNewPW = (TextView) findViewById(R.id.textView176);
        textViewNewPW2 = (TextView) findViewById(R.id.textView177);
        editTextPW = (EditText) findViewById(R.id.editText15);
        editTextNewPW = (EditText) findViewById(R.id.editText16);
        editTextNewPW2 = (EditText) findViewById(R.id.editText17);
        buttonSave = (Button) findViewById(R.id.buttonPWSave);
        buttonCancel = (Button) findViewById(R.id.buttonPWCancel);

    }
}
