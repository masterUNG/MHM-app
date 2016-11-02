package com.su.lapponampai_w.mhm_app_beta1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.NestedScrollingChild;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    //Explicit
    Activity activityNewsActivity;
    String[]stringsNewsTABLE_Med_id,stringsAdaptorid,
            stringsAppearance,stringsAdaptorAppearance,stringsAdaptorMessage,
            stringsNewsTABLE_Appearance_edit,stringsAdaptorAppearance_edit,
            stringsAdaptorActivity;
    ListView listViewNewsActivity;
    String[] stringsMainTABLE_id,stringsMainTABLE_Med_id,stringsArray_AllGeneric_NotDuplicated;

    String[] stringsMedTABLE_id,stringsMedTABLE_Generic1,stringsMedTABLE_Generic2,
            stringsMedTABLE_Generic3,stringsMedTABLE_Generic4,stringsArray_AllGeneric;

    String[] stringsNewsTABLE_Generic_id,stringsNewsTABLE_Appearance_News,stringsNewsTABLE_Message,
            stringsNewsTABLE_Criteria,stringsNewsTABLE_Activity,stringsNewsTABLE_id;

    String[] messageStrings,picStrings,criteriaStrings, activityStrings;

    //Heading
    Spinner spinner;
    ImageButton imageAdherence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        activityNewsActivity = this;



        MyManage myManage = new MyManage(this);
        MyData myData = new MyData();


        stringsMainTABLE_id = myManage.readAllMainTABLE_Full(0);
        //ถ้าไม่มีข่าวสารให้ออกเลย
        if (stringsMainTABLE_id[0].equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.icon_question);
            builder.setTitle("ไม่มีข่าวสาร!!!");
            builder.setMessage("ข่าวสารจะปรากฎตามรายการยาที่กำหนด\nกรุณาเพิ่มยา โดยเข้าไปที่ +เพิ่มเติม+ ==> เพิ่มรายการยา");
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                }
            });
            builder.show();
        }

        //เอา Generic_id จาก MainTABLE จากยกทุกตัวมาก่อนเลย
        stringsMainTABLE_Med_id = myManage.readAllMainTABLE_Full(1); //Med_id ในตาราง mainTABLE ทั้งหมด
        stringsMedTABLE_id = myManage.readAllmedTABLE(0); //Med_id ในตาราง MedTABLE
        stringsMedTABLE_Generic1 = myManage.readAllmedTABLE(3); //Generic_name1 ในตาราง MedTABLE
        stringsMedTABLE_Generic2 = myManage.readAllmedTABLE(6); //Generic_name2 ในตาราง MedTABLE
        stringsMedTABLE_Generic3 = myManage.readAllmedTABLE(9); //Generic_name3 ในตาราง MedTABLE
        stringsMedTABLE_Generic4 = myManage.readAllmedTABLE(12); //Generic_name4 ในตาราง MedTABLE

        stringsNewsTABLE_Generic_id = myManage.readAllnewsTABLE(1); //Generic_id ในตาราง newsTABLE
        stringsNewsTABLE_Message = myManage.readAllnewsTABLE(2);
        stringsNewsTABLE_Appearance_News = myManage.readAllnewsTABLE(3);
        stringsNewsTABLE_Criteria = myManage.readAllnewsTABLE(4);
        stringsNewsTABLE_Activity = myManage.readAllnewsTABLE(5);




        ArrayList<String> genericStringArrayList = new ArrayList<String>();
        int iIndex = 0;
        for(int i = 0;i<stringsMainTABLE_Med_id.length;i++) {

            for(int x = 0;x<stringsMedTABLE_id.length;x++) {
                if (stringsMainTABLE_Med_id[i].equals(stringsMedTABLE_id[x])) {
                    genericStringArrayList.add(iIndex, stringsMedTABLE_Generic1[x]);
                    iIndex = iIndex + 1;
                    if (!stringsMedTABLE_Generic2[x].equals("1")) {
                        genericStringArrayList.add(iIndex, stringsMedTABLE_Generic2[x]);
                        iIndex = iIndex + 1;
                    }
                    if (!stringsMedTABLE_Generic3[x].equals("1")) {
                        genericStringArrayList.add(iIndex, stringsMedTABLE_Generic3[x]);
                        iIndex = iIndex + 1;
                    }
                    if (!stringsMedTABLE_Generic4[x].equals("1")) {
                        genericStringArrayList.add(iIndex, stringsMedTABLE_Generic4[x]);
                        iIndex = iIndex + 1;
                    }
                }
            }
        }

        stringsArray_AllGeneric = new String[genericStringArrayList.size()];
        stringsArray_AllGeneric = genericStringArrayList.toArray(stringsArray_AllGeneric);
        //Check can erase
        for(int b = 0;b<stringsArray_AllGeneric.length;b++) {
            Log.d("7July16V1",stringsArray_AllGeneric[b]);
        }



        genericStringArrayList.clear();
        iIndex = 0;
        String duplicateString;
        //เอาซ้ำออกก่อน
        stringsArray_AllGeneric_NotDuplicated = new String[1];
        stringsArray_AllGeneric_NotDuplicated[0] = "";


        for(int a = 0;a<stringsArray_AllGeneric.length;a++) {
            duplicateString = "N";
            if (stringsArray_AllGeneric_NotDuplicated[0].equals("")) {
                //correct
            } else {
                for(int b = 0;b<stringsArray_AllGeneric_NotDuplicated.length;b++) {
                    if (stringsArray_AllGeneric[a].equals(stringsArray_AllGeneric_NotDuplicated[b])) {
                        duplicateString = "Y";
                    } else {
                       //correct
                    }
                }
            }
            if (duplicateString.equals("N")) {
                genericStringArrayList.add(stringsArray_AllGeneric[a]);
                iIndex = iIndex + 1;
                stringsArray_AllGeneric_NotDuplicated = new String[genericStringArrayList.size()];
                stringsArray_AllGeneric_NotDuplicated =
                        genericStringArrayList.toArray(stringsArray_AllGeneric_NotDuplicated);
            }
        }

        //Check can erase
        for(int b = 0;b<stringsArray_AllGeneric_NotDuplicated.length;b++) {
            Log.d("7July16V1","NotDuplicated ==>" + stringsArray_AllGeneric_NotDuplicated[b]);
        }



        ArrayList<String> arrayListString = new ArrayList<String>(); //รายละเอียดข้อมูลยา
        ArrayList<String> arrayListStringPic = new ArrayList<String>();
        ArrayList<String> arrayListStringCriteria = new ArrayList<String>();
        ArrayList<String> arrayListStringActivity = new ArrayList<String>();
        iIndex = 0;
        for(int i = 0;i<stringsArray_AllGeneric_NotDuplicated.length;i++) {
            for(int x = 0;x<stringsNewsTABLE_Generic_id.length;x++) {
                if (stringsArray_AllGeneric_NotDuplicated[i].equals(stringsNewsTABLE_Generic_id[x])) {
                    if (stringsNewsTABLE_Criteria[x].equals("1")) {
                        String genericName = myManage.findGenerinName_nameGenericTABLE_by_id
                                (stringsNewsTABLE_Generic_id[x]);
                        genericName = "รายละเอียดข้อมูลยา : ".concat(genericName);
                        arrayListString.add(iIndex, genericName);
                        arrayListStringPic.add(iIndex,stringsNewsTABLE_Appearance_News[x]);
                        arrayListStringCriteria.add(iIndex, stringsNewsTABLE_Criteria[x]);
                        arrayListStringActivity.add(iIndex, stringsNewsTABLE_Activity[x]);
                        iIndex = iIndex + 1;
                    }
                }
            }
        }

        messageStrings = new String[arrayListString.size()];
        messageStrings = arrayListString.toArray(messageStrings);

        picStrings = new String[arrayListStringPic.size()];
        picStrings = arrayListStringPic.toArray(picStrings);

        criteriaStrings = new String[arrayListStringCriteria.size()];
        criteriaStrings = arrayListStringCriteria.toArray(criteriaStrings);

        activityStrings = new String[arrayListStringActivity.size()];
        activityStrings = arrayListStringActivity.toArray(activityStrings);

        int[] intsIndex = myData.translate_Appearance_News(picStrings);

        MyNewsAdaptor myNewsAdaptor = new MyNewsAdaptor(NewsActivity.this, messageStrings, intsIndex);
        listViewNewsActivity = (ListView) findViewById(R.id.listViewNewsActivity);
        listViewNewsActivity.setAdapter(myNewsAdaptor);


        listViewNewsActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (activityStrings[position].equals("DrugInformationActivity")) {

                    Intent intent = new Intent(NewsActivity.this, DrugInformationActivity.class);
                    intent.putExtra("NewsActivity_Med_id", stringsArray_AllGeneric_NotDuplicated[position]);
                    startActivity(intent);
                }

            }
        });

        setHeading();


    }  //Main Method

    private void setHeading() {

        imageAdherence = (ImageButton) findViewById(R.id.imageButton4);
        spinner = (Spinner) findViewById(R.id.spinner3);

        MyHeadingDetail myHeadingDetail = new MyHeadingDetail(NewsActivity.this);
        myHeadingDetail.spinnersetup(NewsActivity.this,imageAdherence,spinner);


    } //setHeading


}  //Main Class
