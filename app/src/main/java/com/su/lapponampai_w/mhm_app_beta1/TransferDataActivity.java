package com.su.lapponampai_w.mhm_app_beta1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class TransferDataActivity extends AppCompatActivity {

    Button button;
    private EditText editText;
    private String vnString, jsonString;
    private String str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,
            str11,str12,str13,str14,str15,str16,str17,str18,str19,str20,str21,str22;
    private String s1,s2,s3,s4,s5,s6;
    private String m1,m2, m3;
    private boolean aBoolean = false;

    int iIndex,iIndex2,iIndex3;
    JSONArray jsonArray,jsonArray2,jsonArray3;

    private static final String urlMainSTRING = "http://www.swiftcodingthai.com/mhm/get_main_where_email_edited.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_data);

        bindWidget();

        clickToTransfer();
        
    }

    private void clickToTransfer() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vnString = editText.getText().toString().trim();

                if (vnString.equals("")) {
                    Toast.makeText(TransferDataActivity.this,"Have Space",Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("6July", "pass1");
                    checkVN();
                }
                
                //28July16
                if (aBoolean) {

                    updateNewsumTABLE();

                    updateNewtotalAmountTABLE();
                    
                }
                
            }
        });


        
    }

    private void updateNewtotalAmountTABLE() {

        String urlTotalAmountTABLE = "http://www.swiftcodingthai.com/mhm/get_totalAmount_where_VN.php";

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("VN", vnString)
                .build();
        Request.Builder builder = new Request.Builder();
        final Request request = builder.url(urlTotalAmountTABLE).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String jsonString2 = response.body().string();
                Log.d("28July16", "jsonString2==>" + jsonString2);
                response.body().close();

                MyManage myManage = new MyManage(TransferDataActivity.this);
                try {
                    jsonArray3 = new JSONArray(jsonString2);
                    iIndex3 = jsonArray3.length();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for(int x = 0;x<iIndex3;x++) {
                    try {
                        JSONObject jsonObject = jsonArray3.getJSONObject(x);

                        m1 = jsonObject.getString(MyManage.tcolumn_Main_id);
                        m2 = jsonObject.getString(MyManage.tcolumn_TotalAmount);
                        m3 = jsonObject.getString(MyManage.tcolumn_DateUpdated);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.d("28July16", "m1 :" + m1);
                    Log.d("28July16", "m2 :" + m2);
                    Log.d("28July16", "m3 :" + m3);
                    double d = Double.parseDouble(m2);

                    long l = myManage.addValueTo_totalAmountTABLE(m1, d, m3);


                } //for

                Intent intent = new Intent(TransferDataActivity.this,SplashScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }

    private void updateNewsumTABLE() {

        String urlsumTABLE = "http://www.swiftcodingthai.com/mhm/get_sum_where_VN.php";

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("VN", vnString)
                .build();
        Request.Builder builder = new Request.Builder();
        final Request request = builder.url(urlsumTABLE).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String jsonString2 = response.body().string();
                response.body().close();

                MyManage myManage = new MyManage(TransferDataActivity.this);
                try {
                    jsonArray2 = new JSONArray(jsonString2);
                    iIndex2 = jsonArray2.length();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for(int x = 0;x<iIndex2;x++) {

                    try {
                        JSONObject jsonObject = jsonArray2.getJSONObject(x);

                        s1 = jsonObject.getString(MyManage.column_Main_id);
                        s2 = jsonObject.getString(MyManage.column_DateRef);
                        s3 = jsonObject.getString(MyManage.column_TimeRef);
                        s4 = jsonObject.getString(MyManage.column_DateCheck);
                        s5 = jsonObject.getString(MyManage.column_TimeCheck);
                        s6 = jsonObject.getString(MyManage.column_SkipHold);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.d("28July16", "s1 :" + s1 );
                    Log.d("28July16", "s2 :" + s2 );
                    Log.d("28July16", "s3 :" + s3 );
                    Log.d("28July16", "s4 :" + s4 );
                    Log.d("28July16", "s5 :" + s5 );
                    Log.d("28July16", "s6 :" + s6 );


                    long l = myManage.addValueToSumTable(s1, s2, s3, s4, s5, s6);

                }//for




            }
        });


    }

    private void checkVN() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("VN", vnString)
                .build();

        Request.Builder builder = new Request.Builder();
        final Request request = builder.url(urlMainSTRING).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue((new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {


            }

            @Override
            public void onResponse(Response response) throws IOException {
                jsonString = response.body().string();
                response.body().close();

                Log.d("6JulyV2", "JSON ==>" + jsonString);

                updateNewMainTABLE(jsonString);

            } //onResponse
        }));

    }

    private void updateNewMainTABLE(String jsonString) {

        String strJSON = jsonString;
        Log.d("6JulyV3", "JSON at Update ==>" + strJSON);

        if (!strJSON.equals("null")) {
            //email True
            Log.d("6JulyV3", "Email True");

            deleteAllTABLE();

            aBoolean = true;


            updateValueFromServerToMain(strJSON);


        } else {

            Log.d("6JulyV3", "Email False");

        }
    }

    private void deleteAllTABLE() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyHelper.DATABASE_NAME, MODE_PRIVATE, null);
        //28July16
        sqLiteDatabase.delete(MyManage.mainTABLE, null, null);
        sqLiteDatabase.delete(MyManage.sum_table, null, null);
        sqLiteDatabase.delete(MyManage.totalAmountTABLE, null, null);

    }

    private void updateValueFromServerToMain(String strJSON) {

        MyManage myManage = new MyManage(this);

        try {
            jsonArray = new JSONArray(strJSON);
            iIndex = jsonArray.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("6July", "iIndex :" + iIndex);


        for(int x = 0;x<iIndex;x++) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(x);

                str1 = jsonObject.getString("VN");
                str2 = jsonObject.getString("Main_id");
                str3 = jsonObject.getString(MyManage.mcolumn_Med_id);
                str4 = jsonObject.getString(MyManage.mcolumn_trade_name);
                str5 = jsonObject.getString(MyManage.mcolumn_generic_line);
                str6 = jsonObject.getString("Amount_table");
                str7 = jsonObject.getString("Which_Date_D");
                str8 = jsonObject.getString(MyManage.mcolumn_appearance);
                str9 = jsonObject.getString(MyManage.mcolumn_ea);
                str10 = jsonObject.getString(MyManage.mcolumn_Main_pharmaco);
                str11 = jsonObject.getString(MyManage.mcolumn_startdate);
                str12 = jsonObject.getString(MyManage.mcolumn_finishdate);
                str13 = jsonObject.getString(MyManage.mcolumn_prn);
                str14 = jsonObject.getString(MyManage.mcolumn_t1);
                str15 = jsonObject.getString(MyManage.mcolumn_t2);
                str16 = jsonObject.getString(MyManage.mcolumn_t3);
                str17 = jsonObject.getString(MyManage.mcolumn_t4);
                str18 = jsonObject.getString(MyManage.mcolumn_t5);
                str19 = jsonObject.getString(MyManage.mcolumn_t6);
                str20 = jsonObject.getString(MyManage.mcolumn_t7);
                str21 = jsonObject.getString(MyManage.mcolumn_t8);
                str22 = jsonObject.getString(MyManage.mcolumn_datetimecanceled);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d("6July", "str1 : " + str1);
            Log.d("6July", "str2 : " + str2);
            Log.d("6July", "str3 : " + str3);
            Log.d("6July", "str4 : " + str4);
            Log.d("6July", "str5 : " + str5);
            Log.d("6July", "str6 : " + str6);
            Log.d("6July", "str7 : " + str7);
            Log.d("6July", "str8 : " + str8);
            Log.d("6July", "str9 : " + str9);
            Log.d("6July", "str10 : " + str10);
            Log.d("6July", "str11 : " + str11);
            Log.d("6July", "str12 : " + str12);
            Log.d("6July", "str13 : " + str13);
            Log.d("6July", "str14 : " + str14);
            Log.d("6July", "str15 : " + str15);
            Log.d("6July", "str16 : " + str16);
            Log.d("6July", "str17 : " + str17);
            Log.d("6July", "str18 : " + str18);
            Log.d("6July", "str19 : " + str19);
            Log.d("6July", "str20 : " + str20);
            Log.d("6July", "str21 : " + str21);
            Log.d("6July", "str22 : " + str22);

            int i_id = Integer.parseInt(str2);

            Log.d("6July", "i_id : " + i_id);
            long l = myManage.addValueTomainTABLE(i_id, str3, str4, str5, str6, str7, str8, str9, str10,
                    str11, str12, str13, str14, str15, str16, str17, str18, str19, str20, str21, str22);
            

        } //for
        


    }

    private void bindWidget() {

        button = (Button) findViewById(R.id.btnTransferDataActivity);
        editText = (EditText) findViewById(R.id.editText13);
    }
}
