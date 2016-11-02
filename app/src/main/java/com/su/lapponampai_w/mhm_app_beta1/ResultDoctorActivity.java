package com.su.lapponampai_w.mhm_app_beta1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.DropBoxManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class ResultDoctorActivity extends AppCompatActivity {

    private EditText editText;
    private String vnString, jsonString;
    private String str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,
            str11,str12,str13,str14,str15,str16,str17,str18,str19,str20,str21,str22;

    private static final String urlMainSTRING = "http://www.swiftcodingthai.com/mhm/get_main_where_email_edited.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_doctor);

        editText = (EditText) findViewById(R.id.editText3);



    } //Main Method

    /* 26July
    private class SynMainTABLE extends AsyncTask<Void, Void, String> {

        //Explicit
        private String jsonString;
        private Context context;
        private String myEmailString;
        private String strUrlJSON;

        public SynMainTABLE(Context context,
                            String myEmailString,
                            String strUrlJSON) {
            this.context = context;
            this.myEmailString = myEmailString;
            this.strUrlJSON = strUrlJSON;
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
           26July */
                /*
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("isAdd", "true")
                        .add("EmailUser", myEmailString)
                        .build();

                Request.Builder builder = new Request.Builder();
                final Request request = builder.url(strUrlJSON).post(requestBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue((new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        jsonString = response.body().string();
                    }
                }));
                */

                //26July16 ทำการปิดถึงข้างล่าง
                /*
                return jsonString;


            } catch (Exception e) {
                Log.d("6JulyV2", "e doIn ==>" + e.toString());
                return null;
            }

        } // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("6JulyV2", "JSON ==> " + s);

        } // onPost
    } // SynMain Class

    */


    public void clickLoadData(View view) {

        vnString = editText.getText().toString().trim();

        //Check Space
        if (vnString.equals("")) {
            Toast.makeText(this,"Have Space",Toast.LENGTH_SHORT).show();
        } else {
            checkEmail();
        }

    } //clickLoad

    private void checkEmail() {
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

                Log.d("6JulyV2", "JSON ==>" + jsonString);


                    updateNewMainTABLE(jsonString);


            } //onResponse
        }));






        //SynMainTABLE synMainTABLE = new SynMainTABLE(this, emailString, urlMainSTRING);
        //synMainTABLE.execute();



    } //checkEmail

    private void updateNewMainTABLE(String jsonString) {


        String strJSON = jsonString;
        Log.d("6JulyV3", "JSON at Update ==>" + strJSON);

        if (!strJSON.equals("null")) {
            //email True
            Log.d("6JulyV3", "Email True");

            //deleteMainTABLE();


            updateValueFromServerToMain(strJSON);


        } else {

            Log.d("6JulyV3", "Email False");

        }


    } //Update

    private void updateValueFromServerToMain(String strJSON) {

        Log.d("6JulyV3", "updateValueFromServerToMain");
        MyManage myManage = new MyManage(this);

        try {

            JSONArray jsonArray = new JSONArray(strJSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                //String strUnUse1 = jsonObject.getString("id");
                str1 = jsonObject.getString("VN");
                str2 = jsonObject.getString("Main_id");
                //String strUnUse2 = jsonObject.getString("HN");
                str3 = jsonObject.getString(MyManage.mcolumn_Med_id);
                str4 = jsonObject.getString(MyManage.mcolumn_trade_name);
                str5 = jsonObject.getString(MyManage.mcolumn_generic_line);
                str6 = jsonObject.getString(MyManage.mcolumn_amount_tablet);
                str7 = jsonObject.getString(MyManage.mcolumn_which_date_d);
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



            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Log.d("6JulyV3", str1);


    }


    private void deleteMainTABLE() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyHelper.DATABASE_NAME, MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.mainTABLE, null, null);
    }

} //Main Class
