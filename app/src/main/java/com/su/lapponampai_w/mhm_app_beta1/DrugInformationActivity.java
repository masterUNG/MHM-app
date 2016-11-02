package com.su.lapponampai_w.mhm_app_beta1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DrugInformationActivity extends AppCompatActivity {

    //Explicit
    TextView textViewGenericName,textViewTradeName,textViewDetail,textViewOnline1,textViewOnline2;
    String string1;
    Button button;
    String[] strings,str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_information);

        bindWidget();

        receiveIntentAndMessage();

        showView();

        clickButtonFinish();

    }

    private void clickButtonFinish() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showView() {

        textViewGenericName.setText(strings[0]);
        textViewTradeName.setText(strings[1]);
        textViewDetail.setText(strings[2]);

        if (!strings[3].equals("")) {
            str = strings[3].split(";");
            textViewOnline1.setText(str[0]);
            textViewOnline1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(str[1]));
                    startActivity(intent);
                }
            });
        } else {
            textViewOnline1.setVisibility(View.GONE);
        }
        if (!strings[4].equals("")) {
            str = strings[4].split(";");
            textViewOnline2.setText(str[0]);
            textViewOnline2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(str[1]));
                    startActivity(intent);
                }
            });
        } else {
            textViewOnline2.setVisibility(View.GONE);
        }

    } //showView

    private void bindWidget() {
        textViewGenericName = (TextView) findViewById(R.id.textView_Infor_GenericName);
        textViewTradeName = (TextView) findViewById(R.id.textView_Infor_Tradename);
        textViewDetail = (TextView) findViewById(R.id.textView_Infor_Detail);
        textViewOnline1 = (TextView) findViewById(R.id.textView_Infor_Online1);
        textViewOnline2 = (TextView) findViewById(R.id.textView_Infor_Online2);
        button = (Button) findViewById(R.id.button_Infor_Finish);
    }  //bindWidget

    private void receiveIntentAndMessage() {
        string1 = getIntent().getStringExtra("NewsActivity_Med_id");

        Log.d("7July16", string1);



        MyDrugInformation myDrugInformation = new MyDrugInformation();
        strings = myDrugInformation.receiveInformation(string1);


    } //receiveIntent
}
