package com.su.lapponampai_w.mhm_app_beta1;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by apple on 7/20/16.
 */
public class MyHeadingDetail extends ContextWrapper {

    //Explicit
    String[] strTextSpinner;
    Context context;

    public MyHeadingDetail(Context base) {
        super(base);

    }

    public void spinnersetup(final Context context, ImageButton imageButtonAdherence,Spinner spinner) {

        this.context = context.getApplicationContext();

        final MyManage myManage = new MyManage(context);
        String[] sName = myManage.readAlluserTABLE(1);

        strTextSpinner = new String[8];
        //strTextSpinner = new String[9];
        strTextSpinner[0] = "ไอดีผู้ใช้ : \n\n                 " + sName[0] + "\n=+=+=+=+=+=+=+=+";
        strTextSpinner[1] = "เพิ่มรายการยา";
        strTextSpinner[2] = "เพิ่มวันนัด";
        strTextSpinner[3] = "เพิ่มค่าแล็ป";
        strTextSpinner[4] = "เพิ่มบันทึกประจำวัน";
        strTextSpinner[5] = "ปฏิทิน";
        strTextSpinner[6] = "ตั้งค่าการใช้งาน";
        //strTextSpinner[7] = "เกี่ยวกับ\nMHM Application";
        strTextSpinner[7] = "ออกจากระบบ";

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context, R.layout.my_spinner_item, strTextSpinner);
        spinner.setAdapter(stringArrayAdapter);


        imageButtonAdherence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context, AdherenceActivity.class);
                Intent intent = new Intent(context, PatientAdherenceActivity.class);
                startActivity(intent);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {

                if (strTextSpinner[position].equals("เพิ่มรายการยา")) {

                    String[] stringsStay = myManage.readSQLite_userTABLE(3);
                    if (stringsStay[0].equals("0") || stringsStay[0].equals("1")) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        startActivity(new Intent(context, AddMedicineActivity.class));
                    } else {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Intent intent2 = new Intent(context, PopUpGate.class);
                        intent2.putExtra("btn_pop1", "btn_pop1");
                        startActivity(intent2);
                    }


                    /*
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    startActivity(new Intent(context, AddMedicineActivity.class));
                    */
                }

                if (strTextSpinner[position].equals("เพิ่มวันนัด")) {

                    String[] stringsStay = myManage.readSQLite_userTABLE(3);
                    if (stringsStay[0].equals("0") || stringsStay[0].equals("1")) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        startActivity(new Intent(context, AppointmentActivity.class));
                    } else {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Intent intent2 = new Intent(context, PopUpGate.class);
                        intent2.putExtra("btn_pop2", "btn_pop2");
                        startActivity(intent2);
                    }



                    /*
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    startActivity(new Intent(context, AppointmentActivity.class));
                    */
                }

                if (strTextSpinner[position].equals("เพิ่มค่าแล็ป")) {


                    String[] stringsStay = myManage.readSQLite_userTABLE(3);
                    if (stringsStay[0].equals("0") || stringsStay[0].equals("1")) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        startActivity(new Intent(context, LabActivity.class));
                    } else {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Intent intent2 = new Intent(context, PopUpGate.class);
                        intent2.putExtra("btn_pop3", "btn_pop3");
                        startActivity(intent2);
                    }

                    /*
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    startActivity(new Intent(context, LabActivity.class));
                    */
                }

                if (strTextSpinner[position].equals("เพิ่มบันทึกประจำวัน")) {

                    String[] stringsStay = myManage.readSQLite_userTABLE(3);
                    if (stringsStay[0].equals("0") || stringsStay[0].equals("1")) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        startActivity(new Intent(context, NoteActivity.class));
                    } else {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Intent intent2 = new Intent(context, PopUpGate.class);
                        intent2.putExtra("btn_pop4", "btn_pop4");
                        startActivity(intent2);
                    }

                    /*
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    startActivity(new Intent(context, NoteActivity.class));
                    */
                }

                if (strTextSpinner[position].equals("ปฏิทิน")) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    //startActivity(new Intent(context, AdherenceActivity.class));
                    startActivity(new Intent(context, PatientAdherenceActivity.class));
                }

                if (strTextSpinner[position].equals("ตั้งค่าการใช้งาน")) {

                    String[] stringsStay = myManage.readSQLite_userTABLE(3);
                    if (stringsStay[0].equals("0") || stringsStay[0].equals("1")) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        startActivity(new Intent(context, SettingActivity.class));
                    } else {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Intent intent2 = new Intent(context, PopUpGate.class);
                        intent2.putExtra("Setting", "Setting");
                        startActivity(intent2);
                    }


                    /*
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    startActivity(new Intent(context, SettingActivity.class));
                    */
                }
                /*
                if (strTextSpinner[position].equals("เกี่ยวกับ\nMHM Application")) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    startActivity(new Intent(context, AboutMHMActivity.class));
                }
                */


                if (strTextSpinner[position].equals("ออกจากระบบ")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setCancelable(false);
                    builder.setIcon(R.drawable.logo_carabao48);
                    builder.setTitle("Log Out");
                    builder.setMessage("ยืนยันการ Log Out");
                    builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            myManage.canceledStayLogin();
                            Intent intent = new Intent(context,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } //OnClick
                    });
                    builder.show();

                }



                ((TextView)view).setText(null); //สำคัญมากได้แล้ว
                view.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //strDesk = strDeskSpinner[0];

            }
        });
    }



} //Main Class
