package com.su.lapponampai_w.mhm_app_beta1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PopUpChangeTradeGenericName extends AppCompatActivity {

    //Explicit
    TextView textViewTradeName, textViewGenericName,textViewOK,textViewCancel;
    EditText editTextTradeName, editTextGenericName;
    String stringTradeName, stringGenericName;
    String string1,string2,string3,string4,string5,string6,string7,string8,string9,
            string10,string11,string12,string13,string14,string15,string16, string17;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_change_trade_generic_name);

        bindWidget();

        displayMetrics();

        recieveIntent();

        showView();

        clickOKCancel();




    }

    private void clickOKCancel() {

        //Click OK
        textViewOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string2 = editTextTradeName.getText().toString().trim();
                string3 = editTextGenericName.getText().toString().trim();

                Intent intent = new Intent(PopUpChangeTradeGenericName.this, AddMedicine2Activity.class);
                intent.putExtra("Med_id",string1);
                intent.putExtra("Trade_name", string2);
                intent.putExtra("Generic_line", string3);
                intent.putExtra("Amount_tablet", string15);
                intent.putExtra("EA", string16);
                intent.putExtra("Which_Date_D", string4);
                intent.putExtra("Appearance", string5);
                intent.putExtra("Pharmaco", string6);
                intent.putExtra("T1",string7);
                intent.putExtra("T2",string8);
                intent.putExtra("T3",string9);
                intent.putExtra("T4",string10);
                intent.putExtra("T5",string11);
                intent.putExtra("T6",string12);
                intent.putExtra("T7",string13);
                intent.putExtra("T8",string14);
                intent.putExtra("TimesPerDay", string17);
                AddMedicine2Activity.activityAddMedicine2Activity.finish();
                startActivity(intent);
                finish();

            }
        });

        //Click Cancel
        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });



    }

    private void recieveIntent() {

        //stringTradeName = getIntent().getStringExtra("sendTradeName");
        //stringGenericName = getIntent().getStringExtra("sendGenericName");
        string1 = getIntent().getStringExtra("Med_id");
        string2 = getIntent().getStringExtra("Trade_name");
        string3 = getIntent().getStringExtra("Generic_line");
        string4 = getIntent().getStringExtra("Which_Date_D");
        string5 = getIntent().getStringExtra("Appearance");
        string6 = getIntent().getStringExtra("Pharmaco");
        string7 = getIntent().getStringExtra("T1");
        string8 = getIntent().getStringExtra("T2");
        string9 = getIntent().getStringExtra("T3");
        string10 = getIntent().getStringExtra("T4");
        string11 = getIntent().getStringExtra("T5");
        string12 = getIntent().getStringExtra("T6");
        string13 = getIntent().getStringExtra("T7");
        string14 = getIntent().getStringExtra("T8");
        string15 = getIntent().getStringExtra("Amount_tablet");
        string16 = getIntent().getStringExtra("EA");
        string17 = getIntent().getStringExtra("TimesPerDay");





    }

    private void showView() {

        textViewTradeName.setText("ชื่อการค้า :");
        textViewGenericName.setText("ชื่อสามัญทางยา :");
        editTextTradeName.setText(string2);
        editTextGenericName.setText(string3);

    }

    private void displayMetrics() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width*.85),(int) (height*.4));
    }

    private void bindWidget() {
        textViewTradeName = (TextView) findViewById(R.id.textView179);
        textViewGenericName = (TextView) findViewById(R.id.textView181);
        editTextTradeName = (EditText) findViewById(R.id.editText18);
        editTextGenericName = (EditText) findViewById(R.id.editText19);
        textViewCancel = (TextView) findViewById(R.id.textView182);
        textViewOK = (TextView) findViewById(R.id.textView184);

    }
}
