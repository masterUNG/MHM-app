package com.su.lapponampai_w.mhm_app_beta1;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AddMedicine2Activity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{

    //Explicit
    private TextView textView1, textView2, textView3, textView4,
            textView5, textView6, textView7, textView8, textView9,
            textView10, textView11, textView12, textView13,
            textView14, textView15, textView16, textViewStartDate,
            textViewFinishDate,textViewEA,textViewChangeTG;

    private String string1, string2, string3, string4, string5, string6,
            string7, string8, string9, string10, string11, string12,
            string13, string14, string15, string16, string17;

    public String stringModTradeName, stringModGenericName;
    private String string18,string19,string20; //StartDate,FinishDate,PRN

    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4,
            checkBox5,checkBox6,checkBox7,checkBox8;

    private EditText editText1;

    private ImageView imageView;

    private String string16_Translate;

    private String stringInteraction2,stringTimeMedicine1_2,stringTimeMedicine2_1;
    private String stringTime;

    private String[] strings0, strings2, strings3, strings4, strings5, strings6, strings1,
            strings7, stringGenericName2, stringsduplicate;
    private String[] stringsAppearance;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4,
            linearLayout5, linearLayout6, linearLayout7, linearLayout8, startDatelin,
            finishDatelin,linearLayoutTimePerDay,intervalLin,head4Layout,linBox4,
            linBox2Left,linBox2Right,linLine3;
    private EditText editTextCalculationAmount;
    private int integerAmountMedicine;
    private double doubleAmountMedicine;
    private Button buttonAdd1, buttonAdd10, buttonMinus1, buttonMinus10;

    private ArrayList<String> stringArrayListResultType2 = new ArrayList<String>();
    private ArrayList<String> stringArrayListResultType2Count = new ArrayList<String>();
    private ArrayList<String> stringArrayListResultType3 = new ArrayList<String>();
    private ArrayList<String> stringArrayListResultType3Count = new ArrayList<String>();

    private int pickerHour;
    private int pickerMin;
    public static Activity activityAddMedicine2Activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine2);

        activityAddMedicine2Activity = this;



        //BindWidget
        bindWidget();

        //Receive From Intent
        receiveIntent();

        //Show View
        showView();

        //click CheckBox
        clickCheckBox();

        //Click Amount_Tablet
        clickAmount_Tablet();

        //Click เพื่อใส่เวลา
        clickTimesAndDateTextView();

        clickChangeTG();



    } //Main Method




    @Override //จาก DatePickerFragment
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        MyData myData = new MyData();
        int pickYear = year;
        int pickMonthOfYear = monthOfYear;
        int pickDayOfMonth = dayOfMonth;

        Log.d("testTime", "DatePickerFragment = " + pickDayOfMonth + " " + pickMonthOfYear + " " + pickYear);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date_Specific = new Date();
        Date date = new Date();
        Date date_Start = new Date();
        Date date_Finish = new Date();

        String sSpecificDate = myData.createStringDay(pickDayOfMonth, pickMonthOfYear + 1, pickYear);
        String sDate = myData.currentDay();
        Log.d("testTime", "sSpecific = " + sSpecificDate);

        try {
            date_Specific = dateFormat.parse(sSpecificDate);
            date = dateFormat.parse(sDate);
            date_Start = dateFormat.parse(string18);
            if (!string19.equals("")) {
                date_Finish = dateFormat.parse(string19);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date_Specific.compareTo(date) >= 0) {
            //เงื่อนไขวันที่มากกว่าเท่ากับปัจจุบัน
            if (stringTime.equals("StartDate")) {
                if (!string19.equals("")) {
                    if (date_Specific.compareTo(date_Finish) > 0) {
                        Toast.makeText(AddMedicine2Activity.this,"ไม่สามารถตั้งวันเริ่มต้นให้มากกว่าวันที่สิ้นสุดรับประทานได้",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    textViewStartDate.setText(sSpecificDate);
                    string18 = sSpecificDate;
                }
            }

            if (stringTime.equals("FinishDate")) {
                if (date_Specific.compareTo(date_Start) >= 0) {
                    textViewFinishDate.setText(sSpecificDate);
                    string19 = sSpecificDate;
                } else {
                    Toast.makeText(AddMedicine2Activity.this,"ไม่สามารถตั้งวันสิ้นสุดให้น้อยกว่าวันที่เริ่มต้นรับประทานได้",Toast.LENGTH_SHORT).show();
                }
            }
        } else { //ถ้าตั้งวันน้อยกว่าวันที่ปัจจุบัน
            Toast.makeText(AddMedicine2Activity.this, "ไม่สามารถตั้งเวลารับประทานเป็นอดีตได้", Toast.LENGTH_SHORT).show();
        }


    }

    @Override //การนำค่าเวลามาอยู่ในค่า Int //จาก TimePickerFragment
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        MyData myData = new MyData();
        pickerHour = hourOfDay;
        pickerMin = minute;
        Log.d("testTime", "hourOfDay = " + Integer.toString(pickerHour) + "minute = " + Integer.toString(pickerMin));
        if (stringTime.equals("T1")) {
            string7 = myData.createStringTime(pickerHour, pickerMin);
            textView7.setText(string7);
        } else if (stringTime.equals("T2")) {
            string8 = myData.createStringTime(pickerHour, pickerMin);
            textView8.setText(string8);
        } else if (stringTime.equals("T3")) {
            string9 = myData.createStringTime(pickerHour, pickerMin);
            textView9.setText(string9);
        } else if (stringTime.equals("T4")) {
            string10 = myData.createStringTime(pickerHour, pickerMin);
            textView10.setText(string10);
        } else if (stringTime.equals("T5")) {
            string11 = myData.createStringTime(pickerHour, pickerMin);
            textView11.setText(string11);
        } else if (stringTime.equals("T6")) {
            string12 = myData.createStringTime(pickerHour, pickerMin);
            textView12.setText(string12);
        } else if (stringTime.equals("T7")) {
            string13 = myData.createStringTime(pickerHour, pickerMin);
            textView13.setText(string13);
        } else if (stringTime.equals("T8")) {
            string14 = myData.createStringTime(pickerHour, pickerMin);
            textView14.setText(string14);
        }
    }


    private void clickTimesAndDateTextView() {

        //T1
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTime = "T1";
                showTimePickerDialog(v);
            }
        });
        //T2
        textView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTime = "T2";
                showTimePickerDialog(v);
            }
        });
        //T3
        textView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTime = "T3";
                showTimePickerDialog(v);
            }
        });
        //T4
        textView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTime = "T4";
                showTimePickerDialog(v);
            }
        });
        //T5
        textView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTime = "T5";
                showTimePickerDialog(v);
            }
        });
        //T6
        textView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTime = "T6";
                showTimePickerDialog(v);
            }
        });
        //T7
        textView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTime = "T7";
                showTimePickerDialog(v);
            }
        });
        //T8
        textView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTime = "T8";
                showTimePickerDialog(v);
            }
        });


        //StartDate
        textViewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTime = "StartDate";
                showDatePickerDialog(v);
            }
        });

        //FinishDate
        textViewFinishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTime = "FinishDate";
                showDatePickerDialog(v);
            }
        });

    }

    public void showDatePickerDialog(View v) {
        MyDatePickerFragment myDatePickerFragment = new MyDatePickerFragment();
        myDatePickerFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {

        MyTimePickerFragment myTimePickerFragment = new MyTimePickerFragment();
        myTimePickerFragment.show(getFragmentManager(), "timePicker");

    }

    private void clickAmount_Tablet() {

        //จำนวนเม็ด ที่กิน... ทำไปก่อนแก้ทีหลังนะ
        textView15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
                final String[] strings = {"0.25(เศษหนึ่งส่วนสี่) เม็ด","0.5(ครึ่ง) เม็ด",
                        "0.75(เศษสามส่วนสี่) เม็ด","1(หนึ่ง) เม็ด","1.25(หนึ่งเศษหนึ่งส่วนสี่) เม็ด",
                        "1.5(หนึ่งครึ่ง) เม็ด","1.75(หนึ่งเศษสามส่วนสี่) เม็ด","2(สอง) เม็ด"};
                builder.setTitle("โปรดระบุ!!! \nจำนวนครั้งที่ต้องทานยา(ใน 1 วัน)");
                builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Double aDouble = (which + 1) * 0.25;
                        string15 = Double.toString(aDouble);
                        textView15.setText(string15);
                        dialog.dismiss();
                    }
                });
                builder.show();
            }

        });


        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
                final String[] strings = {"1","2","3","4","5","6","7","8"};
                builder.setTitle("โปรดระบุ!!! \nจำนวนครั้งที่ต้องทานยา(ใน 1 วัน)");
                builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int i = which + 1;
                        textView4.setText(Integer.toString(i));
                        //Toast.makeText(getApplicationContext(), Integer.toString(i), Toast.LENGTH_LONG).show();
                        if (i >= 8) {
                            linearLayout8.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout8.setVisibility(View.GONE);
                            textView14.setText("");
                            string14 = "";
                        }
                        if (i >= 7) {
                            linearLayout7.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout7.setVisibility(View.GONE);
                            textView13.setText("");
                            string13 = "";
                        }
                        if (i >= 6) {
                            linearLayout6.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout6.setVisibility(View.GONE);
                            textView12.setText("");
                            string12 = "";
                        }
                        if (i >= 5) {
                            linearLayout5.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout5.setVisibility(View.GONE);
                            textView11.setText("");
                            string11 = "";
                        }
                        if (i >= 4) {
                            linearLayout4.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout4.setVisibility(View.GONE);
                            textView10.setText("");
                            string10 = "";
                        }
                        if (i >= 3) {
                            linearLayout3.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout3.setVisibility(View.GONE);
                            textView9.setText("");
                            string9 = "";
                        }
                        if (i >= 2) {
                            linearLayout2.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout2.setVisibility(View.GONE);
                            textView8.setText("");
                            string8 = "";
                        }
                        if (i >= 1) {
                            linearLayout1.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout1.setVisibility(View.GONE);
                            textView7.setText("");
                            string7 = "";
                        }




                        dialog.dismiss();
                    }
                });

                builder.show();

            }
        });

    }

    private void clickCheckBox() {

        //Click CheckBox1
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox1.isChecked()) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    string4 = "ED:0";  //เก็บค่าไว้ใน string4 เหมือนเดิม
                    textView1.setVisibility(View.INVISIBLE);
                } else {
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    string4 = "ED:0";  //เก็บค่าไว้ใน string4 เหมือนเดิม
                }

            }
        });


        //Click CheckBox2 ... Day of Week (DOW:)
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox2.isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);


                    final ArrayList<Integer> arrayList = new ArrayList<Integer>();
                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
                    final String[] strings = {"วันอาทิตย์", "วันจันทร์", "วันอังคาร", "วันพุธ", "วันพฤหัสบดี", "วันศุกร์", "วันเสาร์"};
                    builder.setTitle("โปรดเลือกวันที่รับประทาน");
                    builder.setMultiChoiceItems(strings, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                arrayList.add(which);
                            } else if (arrayList.contains(which)) {
                                arrayList.remove(Integer.valueOf(which));
                            }
                        }
                    });
                    builder.setPositiveButton("เลือกรายการ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int intWhich) {

                            Integer[] integers = new Integer[arrayList.size()];
                            integers = arrayList.toArray(integers);
                            Arrays.sort(integers);

                            if (arrayList.size() == 0) {
                                string4 = "";
                                checkBox2.setChecked(false);
                                Toast.makeText(getApplicationContext(), "โปรดเลือกวันที่ต้องการทานยา", Toast.LENGTH_SHORT).show();
                                textView1.setVisibility(View.INVISIBLE);
                            } else {
                                StringBuffer stringBuffer = new StringBuffer("วันที่ทานยา : ");
                                StringBuffer stringBufferCode = new StringBuffer("DOW:");
                                for (int i = 0; i < arrayList.size(); i++) {
                                    Log.d("Which", Integer.toString(integers[i]));
                                    for (int w = 0; w < strings.length; w++) {
                                        if (integers[i] == w) {
                                            stringBuffer.append(strings[w]);
                                            stringBuffer.append(" ");

                                            stringBufferCode.append(Integer.toString(integers[i] + 1));
                                            stringBufferCode.append(",");
                                        }
                                    }
                                }
                                textView1.setText(stringBuffer);
                                textView1.setVisibility(View.VISIBLE);

                                String sCode = stringBufferCode.toString();
                                Log.d("Which", "sCode = " + sCode);
                                sCode = sCode.substring(0, sCode.length() - 1);
                                Log.d("Which", "sCode = " + sCode);
                                string4 = sCode;
                            }

                        }
                    });
                    builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            string4 = "";
                            checkBox2.setChecked(false);
                        }
                    });

                    builder.show();
                } else {
                    string4 = "";
                    textView1.setVisibility(View.INVISIBLE);
                    Log.d("Which", "string 4 :" + string4);
                }
            }
        });

        //Click CheckBox3 ... Day of Month (DOM:)
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox3.isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);


                    final ArrayList<Integer> arrayList = new ArrayList<Integer>();
                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
                    final String[] strings = new String[28];
                    for (int i = 0; i < 28; i++) {
                        strings[i] = Integer.toString(i + 1);
                    }
                    builder.setTitle("โปรดเลือกวันที่รับประทานในเดือนนั้น");
                    builder.setMultiChoiceItems(strings, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                arrayList.add(which);
                            } else if (arrayList.contains(which)) {
                                arrayList.remove(Integer.valueOf(which));
                            }

                        }
                    });
                    builder.setPositiveButton("เลือกรายการ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Integer[] integers = new Integer[arrayList.size()];
                            integers = arrayList.toArray(integers);
                            Arrays.sort(integers);

                            if (arrayList.size() == 0) {
                                string4 = "";
                                checkBox3.setChecked(false);
                                Toast.makeText(getApplicationContext(), "โปรดเลือกวันที่ต้องการทานยา", Toast.LENGTH_SHORT).show();
                                textView1.setVisibility(View.INVISIBLE);
                            } else {
                                StringBuffer stringBuffer = new StringBuffer("วันที่ทานยา : ");
                                StringBuffer stringBufferCode = new StringBuffer("DOM:");

                                for (int i = 0; i < arrayList.size(); i++) {
                                    Log.d("Which", Integer.toString(integers[i]));
                                    for (int w = 0; w < strings.length; w++) {
                                        if (integers[i] == w) {
                                            stringBuffer.append(strings[w]);
                                            stringBuffer.append(", ");

                                            stringBufferCode.append(Integer.toString(integers[i] + 1));  //DOM: ตัวเลขเป็นเลขวันไปเลยนะ
                                            stringBufferCode.append(",");
                                        }
                                    }
                                }
                                String s = stringBuffer.toString();
                                s = s.substring(0, s.length() - 2);
                                s = s.concat(" ของทุกเดือน");

                                textView1.setText(s);
                                textView1.setVisibility(View.VISIBLE);

                                String sCode = stringBufferCode.toString();
                                Log.d("Which", "sCode = " + sCode);
                                sCode = sCode.substring(0, sCode.length() - 1);
                                Log.d("Which", "sCode = " + sCode);
                                string4 = sCode;

                            }
                        }
                    });
                    builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            string4 = "";
                            checkBox3.setChecked(false);
                        }
                    });
                    builder.show();
                } else {
                    string4 = "";
                    textView1.setVisibility(View.INVISIBLE);
                    Log.d("Which", "string 4 :" + string4);
                }
            }
        });


        //Click CheckBox4 ... Every...day (ED:1-5)
        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox4.isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);


                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
                    final String[] strings = {"วันเว้นวัน", "1 วันเว้น 2 วัน(ทุก 3 วัน)", "1 วันเว้น 3 วัน(ทุก 4 วัน)", "1 วันเว้น 4 วัน(ทุก 5 วัน)", "1 วันเว้น 5 วัน(ทุก 6 วัน)"};
                    builder.setTitle("โปรดเลือกวันที่รับประทาน");
                    builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String s = strings[which];
                            if (which == 0) {
                                string4 = "ED:1";
                            } else if (which == 1) {
                                string4 = "ED:2";
                            } else if (which == 2) {
                                string4 = "ED:3";
                            } else if (which == 3) {
                                string4 = "ED:4";
                            } else if (which == 4) {
                                string4 = "ED:5";
                            }
                            textView1.setVisibility(View.VISIBLE);
                            textView1.setText(s);

                            Log.d("Which", "string 4 :" + string4);
                            dialog.dismiss();
                        }
                    });

                    builder.show();

                } else {
                    string4 = "";
                    textView1.setVisibility(View.INVISIBLE);
                    Log.d("Which", "string 4 :" + string4);
                }
            }
        });


        //Click CheckBox5
        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox5.isChecked()) {
                    checkBox6.setChecked(false);
                    finishDatelin.setVisibility(View.VISIBLE);
                    textViewFinishDate.setText("");
                } else {
                    finishDatelin.setVisibility(View.GONE);
                    textViewFinishDate.setText("");
                    string19 = "";
                }
            }
        });

        //Click CheckBox6
        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox6.isChecked()) {
                    checkBox5.setChecked(false);
                    finishDatelin.setVisibility(View.GONE);
                    textViewFinishDate.setText("");
                    string19 = "";
                }
            }
        });

        //Click CheckBox7
        checkBox7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox7.isChecked()) {
                    checkBox8.setChecked(false);
                    string20 = "N";
                    linearLayoutTimePerDay.setVisibility(View.VISIBLE);
                    if (!string14.equals("")) {
                        linearLayout8.setVisibility(View.VISIBLE);
                    }
                    if (!string13.equals("")) {
                        linearLayout7.setVisibility(View.VISIBLE);
                    }
                    if (!string12.equals("")) {
                        linearLayout6.setVisibility(View.VISIBLE);
                    }
                    if (!string11.equals("")) {
                        linearLayout5.setVisibility(View.VISIBLE);
                    }
                    if (!string10.equals("")) {
                        linearLayout4.setVisibility(View.VISIBLE);
                    }
                    if (!string9.equals("")) {
                        linearLayout3.setVisibility(View.VISIBLE);
                    }
                    if (!string8.equals("")) {
                        linearLayout2.setVisibility(View.VISIBLE);
                    }
                    if (!string7.equals("")) {
                        linearLayout1.setVisibility(View.VISIBLE);
                    }
                    head4Layout.setVisibility(View.VISIBLE);
                    linBox4.setVisibility(View.VISIBLE);
                    linBox2Left.setVisibility(View.VISIBLE);
                    linBox2Right.setVisibility(View.VISIBLE);
                    linLine3.setVisibility(View.VISIBLE);

                } else {
                    checkBox7.setChecked(true);

                }
            }
        });


        //Click CheckBox 8 PRN
        checkBox8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox8.isChecked()) {
                    checkBox7.setChecked(false);
                    string20 = "Y";
                    linearLayoutTimePerDay.setVisibility(View.GONE);
                    linearLayout1.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.GONE);
                    linearLayout3.setVisibility(View.GONE);
                    linearLayout4.setVisibility(View.GONE);
                    linearLayout5.setVisibility(View.GONE);
                    linearLayout6.setVisibility(View.GONE);
                    linearLayout7.setVisibility(View.GONE);
                    linearLayout8.setVisibility(View.GONE);
                    head4Layout.setVisibility(View.GONE);
                    linBox4.setVisibility(View.GONE);
                    linBox2Left.setVisibility(View.GONE);
                    linBox2Right.setVisibility(View.GONE);
                    linLine3.setVisibility(View.GONE);
                } else {
                    checkBox8.setChecked(true);
                }
            }
        });

    }

    private void showView() {
        MyData myData = new MyData();

        textView1.setVisibility(View.INVISIBLE);
        //ลบ textView1 ที่เป็น Med_id ออกแต่ยังคงเก็บค่าไว้ที่ String 1
        textView2.setText(string2);
        textView3.setText(string3);
        //19/10/2559

        /*
        if (stringModTradeName == null) {
            textView2.setText(string2);
        } else {
            textView2.setText(stringModTradeName);
            Log.d("19102559", stringModTradeName);
        }
        if (stringModGenericName == null) {
            textView3.setText(string3);
        } else {
            textView3.setText(string3);
        }
        */




        //textView4.setText(string4);
        //textView5.setText(string5);
        stringsAppearance = new String[1];
        stringsAppearance[0] = string5;
        int[] intsIndex = myData.translate_Appearance(stringsAppearance);
        imageView.setBackgroundResource(intsIndex[0]);
        //textView6.setText(string6);

        String stringsWhich_Date_D = myData.translate_Which_Date_D(string4);
        if (stringsWhich_Date_D.equals("รับประทานยาทุกวัน")) {
            checkBox1.setChecked(true);
        }
        textView4.setText(string17);

        textView7.setText(string7); //T1
        textView8.setText(string8); //T2
        textView9.setText(string9); //T3
        textView10.setText(string10); //T4
        textView11.setText(string11); //T5
        textView12.setText(string12); //T6
        textView13.setText(string13); //T7
        textView14.setText(string14); //T8

        //ทำให้ปรากฎตามจำนวนครั้งที่ต้องรับประทาน
        if (string14.equals("")) {
            linearLayout8.setVisibility(View.GONE);
        }
        if (string13.equals("")) {
            linearLayout7.setVisibility(View.GONE);
        }
        if (string12.equals("")) {
            linearLayout6.setVisibility(View.GONE);
        }
        if (string11.equals("")) {
            linearLayout5.setVisibility(View.GONE);
        }
        if (string10.equals("")) {
            linearLayout4.setVisibility(View.GONE);
        }
        if (string9.equals("")) {
            linearLayout3.setVisibility(View.GONE);
        }
        if (string8.equals("")) {
            linearLayout2.setVisibility(View.GONE);
        }
        if (string7.equals("")) {
            linearLayout1.setVisibility(View.GONE);
        }
        //linearLayout1.setVisibility(View.GONE);

        //textView15.setText(string15);
        textView15.setText(string15);
        string16_Translate = myData.translate_EA(string16);
        textView16.setText(string16_Translate);

        //checkBox 5,6 (ทานยาเป็นช่วง หรือต่อเนื่อง Default)
        checkBox5.setChecked(false);
        checkBox6.setChecked(true);
        finishDatelin.setVisibility(View.GONE);
        textViewStartDate.setText(myData.currentDay());
        Log.d("testTime",myData.currentDay());

        //checkBox 7,8 ทานยาประจำ เป็นครั้งคราว
        checkBox7.setChecked(true);
        checkBox8.setChecked(false);

        //set linBox5
        String string16_Translate2 = "(หน่วย : ".concat(string16_Translate).concat(" )");
        textViewEA.setText(string16_Translate2);
        clickCalculateAmountMedicine();

        //19/10/2559 ลองคลิกปุ่มเพื่อเปลี่ยน



    }

    //19/10/2559
    private void clickChangeTG() {
        textViewChangeTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddMedicine2Activity.this, PopUpChangeTradeGenericName.class);
                //intent.putExtra("sendTradeName", string2);
                //intent.putExtra("sendGenericName", string3);
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
                startActivity(intent);
                /*
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
                string18 = myData.currentDay(); //StartDate
                string19 = ""; //FinishDate
                string20 = "N"; //PRN
                */


            }
        });
    }

    private void clickCalculateAmountMedicine() {
        integerAmountMedicine = Integer.parseInt(editTextCalculationAmount.getText().toString());
        buttonAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integerAmountMedicine = integerAmountMedicine + 1;
                editTextCalculationAmount.setText(Integer.toString(integerAmountMedicine));
            }
        });

        buttonAdd10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integerAmountMedicine = integerAmountMedicine + 10;
                editTextCalculationAmount.setText(Integer.toString(integerAmountMedicine));
            }
        });

        buttonMinus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integerAmountMedicine = integerAmountMedicine - 10;
                if (integerAmountMedicine <= 0) {
                    integerAmountMedicine = 0;
                }
                editTextCalculationAmount.setText(Integer.toString(integerAmountMedicine));
            }
        });

        buttonMinus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integerAmountMedicine = integerAmountMedicine - 1;
                if (integerAmountMedicine <= 0) {
                    integerAmountMedicine = 0;
                }
                editTextCalculationAmount.setText(Integer.toString(integerAmountMedicine));
            }
        });



    }  //clickCalculateAmountMedicine


    private void receiveIntent() {
        MyData myData = new MyData();

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
        string18 = myData.currentDay(); //StartDate
        string19 = ""; //FinishDate
        string20 = "N"; //PRN


        stringModTradeName = "";
        stringModGenericName = "";

        /*
        stringModTradeName = getIntent().getStringExtra("ModTradeName");
        stringModGenericName = getIntent().getStringExtra("ModGenericName");
        Toast.makeText(getBaseContext(),stringModTradeName,Toast.LENGTH_SHORT).show();
        */


    }

    private void bindWidget() {

        textView1 = (TextView) findViewById(R.id.textViewWhich_Date_D2);
        textView2 = (TextView) findViewById(R.id.textView12);
        textView3 = (TextView) findViewById(R.id.textView14);
        //textView4 = (TextView) findViewById(R.id.textView16); เอาวันที่รับประทาน ออก Which_Date
        textView4 = (TextView) findViewById(R.id.textView54);
        imageView = (ImageView) findViewById(R.id.imageViewAppearance);
        textView15 = (TextView) findViewById(R.id.textView13);  //กลายเป็น Amount_Tablet
        //textView6 = (TextView) findViewById(R.id.textView20); จะเปลี่ยนให้เป็น FinishDate
        textView7 = (TextView) findViewById(R.id.textView22);
        textView8 = (TextView) findViewById(R.id.textView24);
        textView9 = (TextView) findViewById(R.id.textView26);
        textView10 = (TextView) findViewById(R.id.textView28);
        textView11 = (TextView) findViewById(R.id.textView30);
        textView12 = (TextView) findViewById(R.id.textView32);
        textView13 = (TextView) findViewById(R.id.textView34);
        textView14 = (TextView) findViewById(R.id.textView36);
        //textView15 = (TextView) findViewById(R.id.textView38);  เปลี่ยน Amount_Tablet ไปเป็น Edittext
        textViewStartDate = (TextView) findViewById(R.id.textViewStartDate);
        textViewFinishDate = (TextView) findViewById(R.id.textViewFinishDate);
        //editText1 = (EditText) findViewById(R.id.editText1);
        textView16 = (TextView) findViewById(R.id.textView46);
        textViewEA = (TextView) findViewById(R.id.textView160);
        editTextCalculationAmount = (EditText) findViewById(R.id.editTextAmountMedicine2);
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox) findViewById(R.id.checkBox6);
        checkBox7 = (CheckBox) findViewById(R.id.checkBox7);
        checkBox8 = (CheckBox) findViewById(R.id.checkBox8);
        buttonAdd1 = (Button) findViewById(R.id.button8);
        buttonAdd10 = (Button) findViewById(R.id.button9);
        buttonMinus1 = (Button) findViewById(R.id.button7);
        buttonMinus10 = (Button) findViewById(R.id.button6);
        linearLayout1 = (LinearLayout) findViewById(R.id.t1Layout);
        linearLayout2 = (LinearLayout) findViewById(R.id.t2Layout);
        linearLayout3 = (LinearLayout) findViewById(R.id.t3Layout);
        linearLayout4 = (LinearLayout) findViewById(R.id.t4Layout);
        linearLayout5 = (LinearLayout) findViewById(R.id.t5Layout);
        linearLayout6 = (LinearLayout) findViewById(R.id.t6Layout);
        linearLayout7 = (LinearLayout) findViewById(R.id.t7Layout);
        linearLayout8 = (LinearLayout) findViewById(R.id.t8Layout);
        startDatelin = (LinearLayout) findViewById(R.id.startDateLin);
        finishDatelin = (LinearLayout) findViewById(R.id.finishDateLin);
        linearLayoutTimePerDay = (LinearLayout) findViewById(R.id.timePerDayLayout);
        intervalLin = (LinearLayout) findViewById(R.id.intervalLin);
        head4Layout = (LinearLayout) findViewById(R.id.head4Layout);
        linBox4 = (LinearLayout) findViewById(R.id.linBox4);
        linBox2Left = (LinearLayout) findViewById(R.id.linBox2Left);
        linBox2Right = (LinearLayout) findViewById(R.id.linBox2Right);
        linLine3 = (LinearLayout) findViewById(R.id.line3);
        textViewChangeTG = (TextView) findViewById(R.id.textView180);


    }

    public void clickCancelAddMedicine(View view) {
        finish();
    }

    // Click Save
    public void clickSaveAddMedicine(View view) {

        if (string20.equals("N")) {
            if (string4.equals("")) {
                Toast.makeText(AddMedicine2Activity.this,
                        "กรุณากรอกข้อมูลในช่องให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                return;
            }
            String[] stringsTime = {string7, string8, string9, string10,
                    string11, string12, string13, string14};
            int iTimesPerDay = Integer.parseInt(textView4.getText().toString());

            for (int i = 0; i < iTimesPerDay; i++) {
                if (stringsTime.equals("")) {
                    Toast.makeText(AddMedicine2Activity.this,
                            "กรุณากรอกเวลาในช่องให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            //เวลาซ้ำกัน
            for (int w = 0; w < iTimesPerDay; w++) {
                for (int x = 0; x < iTimesPerDay; x++) {
                    if (!stringsTime[w].equals("") && !stringsTime[x].equals("") && w < x) {
                        Log.d("AddMedicine", "stringsTime[w] : " + w + " " + stringsTime[w]);
                        Log.d("AddMedicine", "stringsTime[x] : " + x + " " + stringsTime[x]);
                        if (stringsTime[w].equals(stringsTime[x])) {
                            Toast.makeText(AddMedicine2Activity.this,
                                    "ใส่เวลาในการรับประทานยาเดียวกัน ไม่สามารถดำเนินการต่อได้",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            }
        }

        Log.d("25July16", "ผ่านClickSaveAddMedication");
        checkDuplicate();

    } //clickSave

    private void alertDialogDuplicate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon_question);
        builder.setTitle("Duplicate!!!");
        builder.setMessage("ไม่สามารถดำเนินการต่อได้ \nเนื่องจากมียาตัวนี้ที่วิธีกินคล้ายกัน \n" +
                "หรือมียาตัวเดียวกันที่เวลาทานยาซ้ำกันในระบบแล้ว");
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }


    private void checkDuplicate() {
        MyManage myManage = new MyManage(this);

        stringsduplicate = myManage.readAllMainTABLE_string(string1, 0);
        //check Duplicate ว่ามียาตัวเดียวกันอยู่หรือไม่ถ้ามีใน mainTABLE แล้วจะไม่ยอมให้ save
        //ให้ไป Delete แล้วเพิ่มข้อมูลเข้าไปใหม่แทน

        if (stringsduplicate.length > 0) {
            String[] sAmount_tablet = myManage.readAllMainTABLE_string(string1, 15);
            Log.d("12345", "sAmount_tablet : " + sAmount_tablet[0]);
            Log.d("12345", "string15 : " + string15);
            String[] sT1 = myManage.readAllMainTABLE_string(string1, 7);  //T1
            String[] sT2 = myManage.readAllMainTABLE_string(string1, 8);  //T2
            String[] sT3 = myManage.readAllMainTABLE_string(string1, 9);  //T3
            String[] sT4 = myManage.readAllMainTABLE_string(string1, 10);  //T4
            String[] sT5 = myManage.readAllMainTABLE_string(string1, 11);  //T5
            String[] sT6 = myManage.readAllMainTABLE_string(string1, 12);  //T6
            String[] sT7 = myManage.readAllMainTABLE_string(string1, 13);  //T7
            String[] sT8 = myManage.readAllMainTABLE_string(string1, 14);  //T8
            String[] sTTime = {string7,string8,string9,string10,string11,string12,string13,string14};
            for (int i = 0; i < stringsduplicate.length; i++) {
                if (sAmount_tablet[i].equals(string15)) {
                    alertDialogDuplicate();
                    return;  //แปลว่าหยุดการทำงาน เหมือน End sub ใน VB
                }
            }

            for(int x = 0; x<sTTime.length;x++) {
                for (int y = 0;y<stringsduplicate.length;y++) {
                    if (sTTime[x].equals(sT1[y]) && !sTTime[x].equals("") && !sT1[y].equals("")) {
                        alertDialogDuplicate();
                        return;
                    } else if (sTTime[x].equals(sT2[y]) && !sTTime[x].equals("") && !sT2[y].equals("")) {
                        alertDialogDuplicate();
                        return;
                    } else if (sTTime[x].equals(sT3[y]) && !sTTime[x].equals("") && !sT3[y].equals("")) {
                        alertDialogDuplicate();
                        return;
                    } else if (sTTime[x].equals(sT4[y]) && !sTTime[x].equals("") && !sT4[y].equals("")) {
                        alertDialogDuplicate();
                        return;
                    } else if (sTTime[x].equals(sT5[y]) && !sTTime[x].equals("") && !sT5[y].equals("")) {
                        alertDialogDuplicate();
                        return;
                    } else if (sTTime[x].equals(sT6[y]) && !sTTime[x].equals("") && !sT6[y].equals("")) {
                        alertDialogDuplicate();
                        return;
                    } else if (sTTime[x].equals(sT7[y]) && !sTTime[x].equals("") && !sT7[y].equals("")) {
                        alertDialogDuplicate();
                        return;
                    } else if (sTTime[x].equals(sT8[y]) && !sTTime[x].equals("") && !sT8[y].equals("")) {
                        alertDialogDuplicate();
                        return;
                    }
                }
            }


        }
        // ไม่มีค่าอะไร add ข้อมูลเข้าไปได้เลย
        Log.d("25July16", "ผ่านCheckDuplicate");
        checkDrugInteraction();
    }

    private void checkDrugInteraction() {
        MyManage myManage = new MyManage(this);
        MyData myData = new MyData();

        if (string20.equals("Y")) {
            string7 = "";
            string8 = "";
            string9 = "";
            string10 = "";
            string11 = "";
            string12 = "";
            string13 = "";
            string14 = "";
            string18 = myData.currentDay();
            string19 = "";
        }

        //เริ่มจากตรงนี้นะ

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyHelper.DATABASE_NAME, MODE_PRIVATE, null);
        sqLiteDatabase.delete("drugInteractionTABLE_For_Query", null, null);

        myManage.checkDrugInteraction(string1);  //Check DrugInteraction จาก Med_id
        //ทำการ add ข้อมูลที่มี DrugInteraction ทั้งหมดเข้าไปในตาราง drugInteractionTABLE_For_Query
        //ถ้ามีค่าเป็น 1 ขึ้นไปแปลว่ามี DrugInteraction แต่ยังไม่รู้ว่า Type ไหน

        int countRowTABLE_Query = myManage.filter_drugInteractionTABLE_For_Query(0).length;
        Log.d("filter_drugInteraction", Integer.toString(countRowTABLE_Query));

        if (countRowTABLE_Query != 0) {
            strings0 = myManage.filter_drugInteractionTABLE_For_Query(0);
            strings1 = myManage.filter_drugInteractionTABLE_For_Query(1);
            strings2 = myManage.filter_drugInteractionTABLE_For_Query(2);
            strings3 = myManage.filter_drugInteractionTABLE_For_Query(3);
            strings4 = myManage.filter_drugInteractionTABLE_For_Query(4);
            strings5 = myManage.filter_drugInteractionTABLE_For_Query(5);
            strings6 = myManage.filter_drugInteractionTABLE_For_Query(6);
            strings7 = myManage.filter_drugInteractionTABLE_For_Query(7);

            Log.d("filter_drugInteraction", strings3[0]);

            //เอาค่าที่ได้มาดู ตำแหน่งที่ strings4 คือ DrugInteraction type ไหน 1,2,3 ถ้ามี 1 แม้แต่อันเดียวก็ไม่ยอม
            //ให้ save โดยทำการ filter ไว้แล้วว่าให้ 1 ขึ้นก่อนเสมอ
            if (strings4[0].equals("1")) {
                alertDialogFaltal();
                Log.d("filter_drugInteraction", "type 1:");
                return;
            } else {




                // เริ่มจากตรงนี้

                stringArrayListResultType2.clear(); //ตัวตั้งต้นในการนับ type 2
                stringArrayListResultType2Count.clear(); //ตัวนับใน type 2
                stringArrayListResultType3.clear(); //ตัวตั้นต้นในการนับ type 3
                stringArrayListResultType3Count.clear(); //ตัวนับใน type 3

                for (int i = 0; i < strings0.length; i++) {

                    if (strings4[i].equals("2")) {

                        stringArrayListResultType2.add("N"); //เพิ่มข้อมูลในตัวตั้งต้น

                        Log.d("filter_drugInteraction", "type2 :" +"_id :" + strings0[i] + "Initial_medicine :" + strings1[i] +
                                "Medicine1 :" + strings2[i] + "Medicine2 :" + strings3[i] + "Type_interaction :" + strings4[i] +
                                "Message :" + strings5[i] + "TimeMedicine1_2 :" + strings6[i] + "TimeMedicine2_1 :" + strings7[i]);

                        if (strings1[i].equals(strings2[i])) {
                            stringInteraction2 = strings3[i];
                        } else {
                            stringInteraction2 = strings2[i];
                        }  //เซ็ต stringInteraction2 เป็นตัวหนังสือที่ต่างจาก ตัวแรก

                        alertDialogInteraction(string2, strings1[i], stringInteraction2, strings5[i]);
                        Log.d("filter_drugInteraction", "Access via type2");


                    } else if (strings4[i].equals("3")) {
                        Log.d("filter_drugInteraction", "type 3 :" + strings0[i] + " :" + strings1[i] +
                                " :" + strings2[i] + " :" + strings3[i] + " :" + strings4[i] +
                                " :" + strings5[i] + " :" + strings6[i] + " :" + strings7[i]);
                        Toast.makeText(getBaseContext(), "ได้ค่า 3", Toast.LENGTH_LONG).show();
                        //ทำแสดง PopUp ค่า drugInteraction ที่ 3
                        //แต่ตอนนี้มีการทานที่บางวันไม่จำเป็นต้องทานด้วย
                        //เอาเป็นว่าเขียน message ในลักษณะ เกิด DrugInteraction ประเภทจำเป็นต้องทานห่างกัน
                        //ถ้าจำเป็นต้องทานร่วมกัน
                        if (strings1[i].equals(strings2[i])) {  //ชื่อยาเหมือนกัน ยาตัวแรกคือ strings1[i]
                            stringInteraction2 = strings3[i];  //ชื่อยาอีกตัวหนึ่ง
                            stringTimeMedicine1_2 = strings6[i]; //1 ไป 2 ไปข้างหน้า
                            stringTimeMedicine2_1 = strings7[i]; // 2 ไป 1 ไปด้านหลัง
                        } else {
                            stringInteraction2 = strings2[i]; // strings1[i] คือยาตัวแรก stringInteraction2 คือยาอีกตัว
                            stringTimeMedicine1_2 = strings7[i];  //1 ไป 2 ข้างหน้า
                            stringTimeMedicine2_1 = strings6[i]; // 2 ไป 1 ไปข้างหลัง
                        }
                        //ต่อไปจะคำนวณช่วยเวลาที่เกิด Drug Interaction ในกรณีที่จำทานวันเดียวกัน ไม่ได้ดูถึงขนาดว่าคนละวัน ทำไม่ได้ครับ

                        String[][] stringsInteraction2 = {myManage.readAllMainTABLE_string(stringInteraction2,7),
                                myManage.readAllMainTABLE_string(stringInteraction2,8),
                                myManage.readAllMainTABLE_string(stringInteraction2,9),
                                myManage.readAllMainTABLE_string(stringInteraction2,10),
                                myManage.readAllMainTABLE_string(stringInteraction2,11),
                                myManage.readAllMainTABLE_string(stringInteraction2,12),
                                myManage.readAllMainTABLE_string(stringInteraction2,13),
                                myManage.readAllMainTABLE_string(stringInteraction2,14)};
                        //ค่าของเวลาที่อยู่ใน T1-8 ของตัวที่เกิด durg Interaction Type 3 เก็บเป็น 2 Dimention Array
                        // stringsInteraction2[1-8][] คือ ตัวที่ 1 ของแต่ละอัน
                        // stringsInteraction2[][ตำแหน่งใดๆ] คือตัวต่อๆ ไป

                        //Log.d("filter_drugInteraction", "stringsInteraction2[0][0]" + stringsInteraction2[0][0]);
                        //Log.d("filter_drugInteraction", "stringsInteraction2[1][0]" + stringsInteraction2[1][0]);
                        //Log.d("filter_drugInteraction", "stringsInteraction2[2][0]" + stringsInteraction2[2][0]);

                        String[] stringsAllTimes = {string7, string8, string9, string10, string11, string12, string13, string14};
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Date date = new Date();
                        Calendar calendar1 = Calendar.getInstance();
                        Calendar calendar2 = Calendar.getInstance();

                        String today = myData.currentDay();

                        //Query ยาตัวที่ 2 แล้วก็เวลาด้วย

                        for (int x = 0; x < stringsAllTimes.length; x++) {

                            if (!stringsAllTimes[x].equals("")) {
                                try {
                                    date = simpleDateFormat.parse(today + " " + stringsAllTimes[x]);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                calendar1.setTime(date); // set calendar
                                calendar1.add(Calendar.MINUTE, Integer.parseInt(stringTimeMedicine1_2));
                                calendar2.setTime(date); // set Date เป็น calendar
                                calendar2.add(Calendar.MINUTE, -1 * Integer.parseInt(stringTimeMedicine2_1));

                                Date date1 = calendar1.getTime(); // เวลาที่ไปข้างหน้า
                                Date date2 = calendar2.getTime(); // เวลาด้านหลัง
                                String s = simpleDateFormat.format(date);  //เดี่ยวลบทิ้ง
                                String s1 = simpleDateFormat.format(date1); //เดี่ยวลบทิ้ง
                                String s2 = simpleDateFormat.format(date2); //เดี่ยวลบทิ้ง

                                Log.d("filter_drugInteraction", "date : " + s); //เดี่ยวลบทิ้ง
                                Log.d("filter_drugInteraction", "date1 : " + s1); //เดี่ยวลบทิ้ง
                                Log.d("filter_drugInteraction", "date2 : " + s2); //เดี่ยวลบทิ้ง

                                //เริ่มทำการเปรียบเทียบเวลาของยาตัวที่จะ add เข้า mainTABLE กับเวลาของยาที่มีอยู่ใน MainTABLE อยู่แล้ว


                                Log.d("filter_drugInteraction", "stringsInteraction2[0].length : " +Integer.toString(stringsInteraction2[0].length));
                                for (int y = 0; y < stringsInteraction2[0].length; y++) {
                                    Log.d("filter_drugInteraction", "Loop Y(1) :" + stringsInteraction2[0].length);

                                    for(int z =0;z < stringsInteraction2.length;z++) {
                                        Log.d("filter_drugInteraction", "Loop Z(2) :" + stringsInteraction2.length);
                                        if (!stringsInteraction2[z][y].equals("")) {
                                            Log.d("filter_drugInteraction", "stringsInteraction2[z][y] ไม่ใช่ค่าว่าง");
                                            Date dateCheck = new Date();
                                            try {
                                                dateCheck = simpleDateFormat.parse(today + " " + stringsInteraction2[z][y]);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            Calendar calendarCheckMinus1day = Calendar.getInstance();
                                            Calendar calendarCheckPlus1day = Calendar.getInstance();
                                            calendarCheckPlus1day.setTime(dateCheck);
                                            calendarCheckMinus1day.setTime(dateCheck);
                                            calendarCheckPlus1day.add(Calendar.DAY_OF_MONTH,1);
                                            calendarCheckMinus1day.add(Calendar.DAY_OF_MONTH, -1);

                                            Date dateCheckPlus1day = calendarCheckPlus1day.getTime();
                                            Date dateCheckMinus1day = calendarCheckMinus1day.getTime();


                                            String sPlus1day = simpleDateFormat.format(dateCheckPlus1day); //ลบได้
                                            String sDate = simpleDateFormat.format(dateCheck); //ลบได้
                                            String sMinus1day = simpleDateFormat.format(dateCheckMinus1day); //ลบได้
                                            int i1 = date1.compareTo(dateCheck); //ลบได้
                                            int i2 = date2.compareTo(dateCheck); //ลบได้
                                            int i3 = date1.compareTo(dateCheckPlus1day); //ลบได้
                                            int i4 = date2.compareTo(dateCheckPlus1day); //ลบได้
                                            int i5 = date1.compareTo(dateCheckMinus1day); //ลบได้
                                            int i6 = date2.compareTo(dateCheckMinus1day); //ลบได้


                                            //Log.d("filter_drugInteraction","ก่อนเข้า if : dateCheckPlus1day : " + sPlus1day);
                                            //Log.d("filter_drugInteraction", "ก่อนเข้า if : dateCheck : " + sDate);
                                            //Log.d("filter_drugInteraction","ก่อนเข้า if : dateCheckMinus1day : " + sMinus1day);
                                            //Log.d("filter_drugInteraction", "i ต่างๆ " + i1 + i2 + i3 + i4 + i5 + i6);

                                            //ทำการนับ type 3
                                            stringArrayListResultType3.add("N"); //นับตัว Reference
                                            //Log.d("filter_drugInteraction", "นับType 3 " );

                                            if ((date1.compareTo(dateCheck) > 0 && date2.compareTo(dateCheck) < 0) ||
                                                    (date1.compareTo(dateCheckPlus1day) > 0 && date2.compareTo(dateCheckPlus1day) < 0)
                                                    || (date1.compareTo(dateCheckMinus1day) > 0 && date2.compareTo(dateCheckMinus1day) < 0)) {

                                                Log.d("filter_drugInteraction", "เข้า if!!!!!!!!!!! ");
                                                alertDialogInteractionType3(string2, strings1[i], stringInteraction2, strings5[i], strings6[i], strings7[i]);
                                            } else {
                                                stringArrayListResultType3Count.add("N"); //นับตัว ที่ต้องนับ
                                                //Log.d("filter_drugInteraction", "นับType 3 ใช้ " );
                                            }
                                        }
                                    }
                                }
                                // ได้ค่าเวลามา 2 อันแล้ว ทั้ง upper และ lower
                            }
                        }

                    }  //Type 3
                } //ออกจาก loop Type 2 และ Type 3

            }
            checkaddValueTomainTABLEandIntent();
            return;
        }

        //addValueTomainTABLEandIntent();
        Log.d("25July16", "ผ่านClickDrugInteraction");
        checkZeroAmountTablet();
    }

    private void checkZeroAmountTablet() {

        doubleAmountMedicine = Double.parseDouble(editTextCalculationAmount.getText().toString());

        if (doubleAmountMedicine == 0) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(AddMedicine2Activity.this);
            builder.setIcon(R.drawable.icon_question);
            builder.setTitle("จำนวนยาเท่ากับ 0!!!");
            builder.setMessage("ท่านต้องการดำเนินการต่อหรือไม่ (สามารถเพิ่มจำนวนยาภายหลังได้)");
            builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addValueTomainTABLEandIntent();
                    dialog.dismiss();

                }
            });
            builder.show();

        } else {
            Log.d("25July16", "ผ่านcheckZeroAmountTablet");
            addValueTomainTABLEandIntent();
        }   //if

    }  //checkAmountTablet


    private void addValueTomainTABLEandIntent() {


        MainActivity.activityMainActivity.finish();
        AddMedicineActivity.activityAddMedicineActivity.finish();
        MyManage myManage = new MyManage(this);
        MyData myData = new MyData();


        //เรียงค่าเผื่อมีคนไม่เรียง (เวลา)
        String[] stringsTime = {string7, string8, string9, string10,
                string11, string12, string13, string14};
        Date date00 = new Date();
        Date date01 = new Date();
        Date date02 = new Date();
        Date date03 = new Date();
        Date date04 = new Date();
        Date date05 = new Date();
        Date date06 = new Date();
        Date date07 = new Date();
        Date date_w;
        Date date_x;
        Date[] dates = {date00,date01,date02,date03,date04,date05,date06,date07};
        int iTimesPerDay = Integer.parseInt(textView4.getText().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        for (int z = 0; z < iTimesPerDay; z++) {
            try {
                dates[z] = dateFormat.parse(stringsTime[z]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        for (int w = 0; w < iTimesPerDay; w++) {
            for (int x = 0; x < iTimesPerDay; x++) {
                if (w < x) {
                    Log.d("testTimes","1 : " + Integer.toString(w) + Integer.toString(x));
                    if (dates[w].compareTo(dates[x]) > 0) {
                        Log.d("testTimes","2 : " + Integer.toString(w) + Integer.toString(x));
                        date_w = dates[w];
                        Log.d("testTimes","date_w : " +  dateFormat.format(date_w));
                        date_x = dates[x];
                        Log.d("testTimes","date_x : " +  dateFormat.format(date_x));
                        dates[w] = date_x;
                        Log.d("testTimes","date[w] : " +  dateFormat.format(dates[w]));
                        dates[x] = date_w;
                        Log.d("testTimes","date[x] : " +  dateFormat.format(dates[x]));
                    }
                }
            }
        }

        if (!string7.equals("")) {
            string7 = dateFormat.format(dates[0]);
        }
        if (!string8.equals("")) {
            string8 = dateFormat.format(dates[1]);
        }
        if (!string9.equals("")) {
            string9 = dateFormat.format(dates[2]);
        }
        if (!string10.equals("")) {
            string10 = dateFormat.format(dates[3]);
        }
        if (!string11.equals("")) {
            string11 = dateFormat.format(dates[4]);
        }
        if (!string12.equals("")) {
            string12 = dateFormat.format(dates[5]);
        }
        if (!string13.equals("")) {
            string13 = dateFormat.format(dates[6]);
        }
        if (!string14.equals("")) {
            string14 = dateFormat.format(dates[7]);
        }

        //ต่อไปทำ PRN ถ้าเป็น Y ให้
        /*
        if (string20.equals("Y")) {
            string7 = "";
            string8 = "";
            string9 = "";
            string10 = "";
            string11 = "";
            string12 = "";
            string13 = "";
            string14 = "";
            string18 = myData.currentDay();
            string19 = "";
        }
        */

        //ทำการสร้าง primarykey ใหม่
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyMMdd");
        String strNewMain_id = dateFormat1.format(System.currentTimeMillis());
        String[] strMain_id_Array = myManage.filterMainTABLE_Mainid6Digit(strNewMain_id);
        if (strMain_id_Array[0].equals("")) {
            strNewMain_id = strNewMain_id.concat("01");
        } else {
            //หาถ้าสูงสุด 1 อัน
            Log.d("25July16", "strMain_id : " + strNewMain_id);
            for(int i=0;i<strMain_id_Array.length;i++) {
                Log.d("25July16", "strMain_id_Array : " + strMain_id_Array[i]);
            }
            int i = strMain_id_Array.length;
            i = i + 1;
            String s = Integer.toString(i);
            if (s.length() == 1) {
                s = "0".concat(s);
            }
            strNewMain_id = strNewMain_id.concat(s);
            Log.d("25July16", "strMain_id_Array : " + strNewMain_id);

        }
        Log.d("25July16", "strMain_id : " + strNewMain_id);
        int iMain_id = Integer.parseInt(strNewMain_id);
        Log.d("25July16", "iMain_id : " + iMain_id);


        //เริ่มจากตรงนี้.... ถ้าไม่ได้เริ่มยาในวันนี้ต้องยังไม่แสดงหนะ
        myManage.addValueTomainTABLE(iMain_id,string1, string2, string3, string15, string4, string5, string16, string6, string18, string19, string20, string7, string8, string9, string10, string11, string12, string13, string14, "");


        //เอาค่า Med_id เป็นตัว query ในตาราง mainTABLE โดยเรียงจาก _id แบบ DESC
        String[] strings1 = myManage.readAllMainTABLE_string(string1, 0); //เอาค่าMain_id
        String[] stringsT1 = myManage.readAllMainTABLE_string(string1, 7); //T1
        String[] stringsT2 = myManage.readAllMainTABLE_string(string1, 8); //T2
        String[] stringsT3 = myManage.readAllMainTABLE_string(string1, 9); //T3
        String[] stringsT4 = myManage.readAllMainTABLE_string(string1, 10); //T4
        String[] stringsT5 = myManage.readAllMainTABLE_string(string1, 11); //T5
        String[] stringsT6 = myManage.readAllMainTABLE_string(string1, 12); //T6
        String[] stringsT7 = myManage.readAllMainTABLE_string(string1, 13); //T7
        String[] stringsT8 = myManage.readAllMainTABLE_string(string1, 14); //T8


        //ต้องทำอย่างไรก็ได้ check ให้ได้ก่อนว่าวันนี้ต้องกินยาหรือไม่

        // 290916 ==>> เริ่มทำการวนลูป 7 วันเพื่อเพิ่มค่าเข้าไปนะ


        for(int iAddDate = 0;iAddDate <=9;iAddDate++) {
            String stringDateBegin = myData.currentDay(); //ค่าของวันที่เริ่มต้นก็คือวันนี้
            String addSumTABLE_Today = "N";
            String stringIntervalDate = "N"; // Date อยู่ระหว่าง StartDate & FinishDate


            Date dateBegin = myData.stringChangetoDateWithOutTime(stringDateBegin);
            Date dateStartDate = myData.stringChangetoDateWithOutTime(string18);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateBegin);
            calendar.add(Calendar.DAY_OF_MONTH,iAddDate);

            Date dateAfterProcess = calendar.getTime();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String stringDateAfterProcess = simpleDateFormat.format(dateAfterProcess);


            if (dateAfterProcess.compareTo(dateStartDate) >= 0) {
                stringIntervalDate = "Y";
            }

            if (!string19.equals("")) {
                Date dateFinishDate = myData.stringChangetoDateWithOutTime(string19);
                if (dateAfterProcess.compareTo(dateFinishDate) > 0) {
                    stringIntervalDate = "N";
                }
            }


            //ต่อไปจะทำ AddsumTABLEToday
            String current_DayOfWeek = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)); //เอาค่าตัวเลขของวันประจำสัปดาห์จาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว
            String current_DayOfMonth = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)); //เอาค่าตัวเลขของวันประจำเดือนจาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว

            Log.d("queryDay", "current_DayofWeek : " + current_DayOfWeek);
            Log.d("queryDay", "current_DayofMonth : " + current_DayOfMonth);

            String[] queryDay = string4.split(":");
            String[] querySelectedDay = null;

            Log.d("queryDay", "queryDay0 : " + queryDay[0]);
            Log.d("queryDay", "queryDay1 : " + queryDay[1]);

            if (!queryDay[0].equals("ED")) {
                querySelectedDay = queryDay[1].split(",");
                for (int i = 0; i < querySelectedDay.length; i++) {
                    Log.d("queryDay", "querySelectedDay[] : " + querySelectedDay[i]);
                    if (queryDay[0].equals("DOW")) {
                        if (querySelectedDay[i].equals(current_DayOfWeek)) {
                            addSumTABLE_Today = "Y";
                        }
                    }
                    if (queryDay[0].equals("DOM")) {
                        if (querySelectedDay[i].equals(current_DayOfMonth)) {
                            addSumTABLE_Today = "Y";
                        }
                    }
                }

            } else {  //ถ้าเป็น ED จะมี 0 1 2 3 4 5

                if (stringDateBegin.equals(stringDateAfterProcess)) {
                    addSumTABLE_Today = "Y";
                } else {  //ถ้าไม่ใช่วันแรกแล้วเป็นวันอื่นๆ
                    //อยากให้ทำการตรวจสอบจากครั้งหลังสุดใน sumTABLE ที่ได้ยาตัวนี้

                    String stringDate_ED_Ref = Integer.toString(iMain_id);
                    String[] stringsDate_ED_Ref = myManage.filter_sumTABLE_finding_DateRef_by_MainId_idDESC(stringDate_ED_Ref);
                    //Date dateRef = myData.stringChangetoDateWithOutTime(stringsDate_ED_Ref[0]); //dateRef ก่อนนำไป add ค่า
                    Date dateRef = myData.stringChangetoDateWithOutTime(stringsDate_ED_Ref[0]); //dateRef ก่อนนำไป add ค่า
                    Calendar calendarRef = Calendar.getInstance();
                    calendarRef.setTime(dateRef);  //calendarRef ก่อนนำไป add ค่า

                    //เทียบ date ที่ add ค่าแล้ว กับ dateAfterProcess จากข้างบน

                    if (queryDay[1].equals("0")) {
                        addSumTABLE_Today = "Y";
                    } else if (queryDay[1].equals("1")) {
                        //วันเว้นวัน
                        calendarRef.add(Calendar.DAY_OF_MONTH,2);
                        Date date = calendarRef.getTime();
                        if (date.compareTo(dateAfterProcess) == 0) {
                            addSumTABLE_Today = "Y";
                        }
                    } else if (queryDay[1].equals("2")) {
                        //วันเว้นวัน
                        calendarRef.add(Calendar.DAY_OF_MONTH,3);
                        Date date = calendarRef.getTime();
                        if (date.compareTo(dateAfterProcess) == 0) {
                            addSumTABLE_Today = "Y";
                        }
                    } else if (queryDay[1].equals("3")) {
                        //วันเว้นวัน
                        calendarRef.add(Calendar.DAY_OF_MONTH,4);
                        Date date = calendarRef.getTime();
                        if (date.compareTo(dateAfterProcess) == 0) {
                            addSumTABLE_Today = "Y";
                        }
                    } else if (queryDay[1].equals("4")) {
                        //วันเว้นวัน
                        calendarRef.add(Calendar.DAY_OF_MONTH,5);
                        Date date = calendarRef.getTime();
                        if (date.compareTo(dateAfterProcess) == 0) {
                            addSumTABLE_Today = "Y";
                        }
                    } else if (queryDay[1].equals("5")) {
                        //วันเว้นวัน
                        calendarRef.add(Calendar.DAY_OF_MONTH,6);
                        Date date = calendarRef.getTime();
                        if (date.compareTo(dateAfterProcess) == 0) {
                            addSumTABLE_Today = "Y";
                        }
                    }





                }

            }

            //addValueToSumTable
            //*** ดูว่าเป็นวันนั้นๆ ต้องใส่ข้อมูลลงไปใน sumTABLE หรือไม่
            Log.d("addValueToSumTable", strings1[0] + " " + stringDateAfterProcess);

            if (addSumTABLE_Today.equals("Y") && stringIntervalDate.equals("Y")) {

                if (!stringsT1[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT1[0], "", "", "");
                }

                if (!stringsT2[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT2[0], "", "", "");
                }
                if (!stringsT3[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT3[0], "", "", "");
                }
                if (!stringsT4[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT4[0], "", "", "");
                }
                if (!stringsT5[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT5[0], "", "", "");
                }
                if (!stringsT6[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT6[0], "", "", "");
                }
                if (!stringsT7[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT7[0], "", "", "");
                }
                if (!stringsT8[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT8[0], "", "", "");
                }

            }

        }  //for

        //Add จำนวนยาเข้า 2 TABLE
        doubleAmountMedicine = Double.parseDouble(editTextCalculationAmount.getText().toString());
        if (doubleAmountMedicine > 0) {

            String[][] stringstotalAmountTABLE = {myManage.readAlltotalAmountTABLE(0),
                    myManage.readAlltotalAmountTABLE(1),myManage.readAlltotalAmountTABLE(2),
                    myManage.readAlltotalAmountTABLE(3)};

            String sDate = myData.currentDateTime();
            String string_Main_id = Integer.toString(iMain_id);
            myManage.addValueTo_addUseTABLE(string_Main_id,"Add",doubleAmountMedicine,sDate);

            String s = "N";
            double doubleAmountInitialMedicine = 0;
            String s_id = null;
            for(int i = 0;i<stringstotalAmountTABLE[0].length;i++) {
                if (stringstotalAmountTABLE[1][i].equals(string_Main_id)) {
                    s = "Y";
                    doubleAmountInitialMedicine = Double.parseDouble(stringstotalAmountTABLE[2][i]);
                    s_id = stringstotalAmountTABLE[0][i];
                }
            }
            if (s.equals("N")) {
                myManage.addValueTo_totalAmountTABLE(string_Main_id, doubleAmountMedicine, sDate);
            } else if (s.equals("Y")) {
                //ทำการ UPdate
                doubleAmountMedicine = doubleAmountInitialMedicine + doubleAmountMedicine;
                String s_AmountMedicine = Double.toString(doubleAmountMedicine);
                myManage.updateTotalAmountTABLE(s_id, s_AmountMedicine, sDate);
                Log.d("MedicationAdd", "จำนวนเม็ดสะสม "+ s_AmountMedicine);

            }

        } //if(doubleAmountMedicine > 0)

        String[] strUser = myManage.filter_userTABLE(1);
        String strDate = myData.currentDay();
        myManage.update_Last_updated(strUser[0],strDate);


        //ทำการBoardCast
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.updateDailyBroadcast(getBaseContext());



        Intent intent = new Intent(AddMedicine2Activity.this, MainActivity.class);
        startActivity(intent);
        //ปิด Activity สุดท้าย
        finish();


    }


    private void addValueTomainTABLEandIntent1() {

        //ปิด 2 Activity ด้านหน้า
        MainActivity.activityMainActivity.finish();
        AddMedicineActivity.activityAddMedicineActivity.finish();
        MyManage myManage = new MyManage(this);
        MyData myData = new MyData();

        String[] stringsREAD_mainTABLE = myManage.read_mainTABLE_InCluded_DateTimeCanceled(0); //เอาค่ามาซักค่านึกไว้ check ว่า mainTABLE มียาหรือไม่
        String[] stringsREAD_sumTABLE = myManage.readAllsumTABLE_Full(0);
        if (stringsREAD_mainTABLE[0].equals("") && stringsREAD_sumTABLE[0].equals("")) {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkDailyUpdateReceiver1(); //เริ่มต้นการทำ boardcast ที่ยา Add เข้าไปครั้งแรก
                }
            }, 1000); // 1 วินาที
        } else {
            //เริ่มต้น 8/10/59
            DailyUpdateReceiver dailyUpdateReceiver = new DailyUpdateReceiver();
            dailyUpdateReceiver.stringsREAD0 = myManage.readAllMainTABLE_Full(0);
            dailyUpdateReceiver.stringsREAD1 = myManage.readAllMainTABLE_Full(1);  //Main_id
            dailyUpdateReceiver.stringsREAD2 = myManage.readAllMainTABLE_Full(2);
            dailyUpdateReceiver.stringsREAD3 = myManage.readAllMainTABLE_Full(3);
            dailyUpdateReceiver.stringsREAD4 = myManage.readAllMainTABLE_Full(4);
            dailyUpdateReceiver.stringsREAD5 = myManage.readAllMainTABLE_Full(5);
            dailyUpdateReceiver.stringsREAD6 = myManage.readAllMainTABLE_Full(6);
            dailyUpdateReceiver.stringsREAD7 = myManage.readAllMainTABLE_Full(7);
            dailyUpdateReceiver.stringsREAD8 = myManage.readAllMainTABLE_Full(8);
            dailyUpdateReceiver.stringsREAD9 = myManage.readAllMainTABLE_Full(9); //StartDate
            dailyUpdateReceiver.stringsREAD10 = myManage.readAllMainTABLE_Full(10);
            dailyUpdateReceiver.stringsREAD11 = myManage.readAllMainTABLE_Full(11); //prn
            dailyUpdateReceiver.stringsREAD12 = myManage.readAllMainTABLE_Full(12); //t1
            dailyUpdateReceiver.stringsREAD13 = myManage.readAllMainTABLE_Full(13);
            dailyUpdateReceiver.stringsREAD14 = myManage.readAllMainTABLE_Full(14);
            dailyUpdateReceiver.stringsREAD15 = myManage.readAllMainTABLE_Full(15);
            dailyUpdateReceiver.stringsREAD16 = myManage.readAllMainTABLE_Full(16);
            dailyUpdateReceiver.stringsREAD17 = myManage.readAllMainTABLE_Full(17);
            dailyUpdateReceiver.stringsREAD18 = myManage.readAllMainTABLE_Full(18);
            dailyUpdateReceiver.stringsREAD19 = myManage.readAllMainTABLE_Full(19); //T8
            dailyUpdateReceiver.stringsREAD20 = myManage.readAllMainTABLE_Full(20);  //TimeDateCanceled

            String[] strLast_updated = myManage.filter_userTABLE(5); //วันที่ในระบบล่าสุด
            Date dateLast_updated = myData.stringChangetoDateWithOutTime(strLast_updated[0]);
            String currentDay = myData.currentDay();
            Date dateInitial = myData.stringChangetoDateWithOutTime(currentDay);
            Calendar calendarLast_updated = Calendar.getInstance();
            calendarLast_updated.setTime(dateLast_updated);
            calendarLast_updated.add(Calendar.DAY_OF_MONTH, 9);
            Date dateRef = calendarLast_updated.getTime();


            Calendar calendarCurrent = Calendar.getInstance();
            calendarCurrent.setTime(dateInitial);
            calendarCurrent.add(Calendar.DAY_OF_MONTH, 9);
            Date dateFinalProcess = calendarCurrent.getTime();


            String strCheckPRN = "Y";
            for (int i = 0; i < dailyUpdateReceiver.stringsREAD11.length; i++) {
                if (dailyUpdateReceiver.stringsREAD11[i].equals("N")) {
                    strCheckPRN = "N";
                }
            }
            //มีถึงอีก 9 วันข้างหน้าแล้วหรือยัง
            String strDateRef = "N";
            if (dateRef.compareTo(dateFinalProcess) >= 0) {
                strDateRef = "Y";
            }


            if (dailyUpdateReceiver.stringsREAD0[0].equals("")) {
                Log.d("UpdatesumTABLE", "ไม่มียาใน mainTABLE : ค่าว่าง ยุติการ UpdateReceiver");
                Toast.makeText(getBaseContext(), "ไม่มียาใน mainTABLE : ค่าว่าง ยุติการ UpdateReceiver", Toast.LENGTH_LONG).show();
            } else if (strCheckPRN.equals("Y")) {
                Log.d("UpdatesumTABLE", "ยาใน mainTABLE มีแต่ยา PRN : ยุติการ UpdateReceiver");
                Toast.makeText(getBaseContext(), "ยาใน mainTABLE มีแต่ยา PRN :ยุติการ UpdateReceiver", Toast.LENGTH_LONG).show();
            }
            //ถ้าจะ Test การเอาเข้าให้เอา else if อันนี้ออกไป
            else if (strDateRef.equals("Y")) {
                Log.d("UpdatesumTABLE", "มีค่าวันนี้ใน sumTABLE ของวันนี้แล้ว : ยุติการ UpdateReceiver");
                Toast.makeText(getBaseContext(), "มีค่าวันนี้ใน sumTABLE ของวันนี้แล้ว : ยุติการ UpdateReceiver", Toast.LENGTH_LONG).show();
            } else {

                String[][] stringsReadAll_MainTABLE = {dailyUpdateReceiver.stringsREAD0,
                        dailyUpdateReceiver.stringsREAD1, dailyUpdateReceiver.stringsREAD2,
                        dailyUpdateReceiver.stringsREAD3, dailyUpdateReceiver.stringsREAD4,
                        dailyUpdateReceiver.stringsREAD5, dailyUpdateReceiver.stringsREAD6,
                        dailyUpdateReceiver.stringsREAD7, dailyUpdateReceiver.stringsREAD8,
                        dailyUpdateReceiver.stringsREAD9, dailyUpdateReceiver.stringsREAD10,
                        dailyUpdateReceiver.stringsREAD11, dailyUpdateReceiver.stringsREAD12,
                        dailyUpdateReceiver.stringsREAD13, dailyUpdateReceiver.stringsREAD14,
                        dailyUpdateReceiver.stringsREAD15, dailyUpdateReceiver.stringsREAD16,
                        dailyUpdateReceiver.stringsREAD17, dailyUpdateReceiver.stringsREAD18,
                        dailyUpdateReceiver.stringsREAD19, dailyUpdateReceiver.stringsREAD20};


                //(1/10/16)
                do {
                    //String stringDateRef = myData.string_ddMMyyyy_ConvertedFromSpecificDate(dateRef);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateRef);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    dateRef = calendar.getTime(); //เปลี่ยนค่าของ dateRef ให้มีค่าวันที่มากขึ้น 1 วัน

                    String stringDateRef = myData.string_ddMMyyyy_ConvertedFromSpecificDate(dateRef); // ค่า Text ของวันที่ที่ต้องการเพิ่มเข้าใน sumTABLE


                    //เริ่มยากจากตรงนี้!!!!
                    for (int i = 0; i < stringsReadAll_MainTABLE[i].length; i++) {  // Loop เท่ากับจำนวนแถว
                        dailyUpdateReceiver.checkFinishDay = "N";
                        dailyUpdateReceiver.checkStartDay = "N";
                        dailyUpdateReceiver.checkWhich_Date_D = "N";
                        if (stringsReadAll_MainTABLE[20][i].equals("")) { //ยาถูก Cancel ไปหรือยัง!!!
                            Log.d("UpdatesumTABLE", "ค่าตำแหน่งที่ 20 ว่าง : " + i);
                            //ถ้ายายัง Active อยู่!!!


                            //currentDay = myData.currentDay();  //ค่าของวันนี้
                            //Date dateToday = myData.stringChangetoDateWithOutTime(currentDay);
                            Date dateStartDate = myData.stringChangetoDateWithOutTime(stringsReadAll_MainTABLE[9][i]); //ค่า Date ของ StartDate

                            // เช็ค FinishDate ว่ายังต้องทานต่อหรือไม่
                            if (!stringsReadAll_MainTABLE[10][i].equals("")) { //ถ้า FinishDate ไม่เท่ากับค่าว่าง แปลว่ามีวันที่ต้องหยุดทาน
                                Date dateFinishDate = myData.stringChangetoDateWithOutTime(stringsReadAll_MainTABLE[10][i]); //ค่า Date ของ FinishDate
                                Log.d("UpdatesumTABLE", "dateFinishDate  : " + dateFinishDate);
                                if (dateRef.compareTo(dateFinishDate) <= 0) {
                                    dailyUpdateReceiver.checkFinishDay = "Y";
                                } else {
                                    dailyUpdateReceiver.checkFinishDay = "N"; //แปลว่าเลยวันที่ต้องใช้มาแล้ว
                                }

                            } else {
                                dailyUpdateReceiver.checkFinishDay = "Y";
                            }

                            //เช็ค StartDate ว่าเริ่มหรือยัง
                            if (dateRef.compareTo(dateStartDate) >= 0) {
                                dailyUpdateReceiver.checkStartDay = "Y";
                            } else {
                                dailyUpdateReceiver.checkStartDay = "N";
                            }


                            //String current_DayOfWeek = myData.current_DayOfWeek();  //ค่าเป็นเลข ของ DayofWeek
                            //String current_DayOfMonth = myData.current_DayOfMonth(); //ค่าเป็นเลข ของ DayofMonth
                            String current_DayOfWeek = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)); //เอาค่าตัวเลขของวันประจำสัปดาห์จาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว
                            String current_DayOfMonth = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)); //เอาค่าตัวเลขของวันประจำเดือนจาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว
                            //เช็ค ว่าตาม Which_Date_D วันนี้ต้องทานหรือไม่!!!
                            String[] queryDay = stringsReadAll_MainTABLE[5][i].split(":");
                            String[] querySelectedDay = null;


                            if (!queryDay[0].equals("ED")) {
                                querySelectedDay = queryDay[1].split(",");
                                for (int w = 0; w < querySelectedDay.length; w++) {
                                    Log.d("queryDay", "querySelectedDay[] : " + querySelectedDay[w]);
                                    if (queryDay[0].equals("DOW")) {
                                        if (querySelectedDay[w].equals(current_DayOfWeek)) {
                                            dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                        }
                                    }
                                    if (queryDay[0].equals("DOM")) {
                                        if (querySelectedDay[w].equals(current_DayOfMonth)) {
                                            dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                        }
                                    }
                                }

                            } else {  //ถ้าเป็น ED จะมี 0 1 2 3 4 5


                                String[] stringsDate_ED_Ref = myManage.filter_sumTABLE_finding_DateRef_by_MainId_idDESC(stringsReadAll_MainTABLE[1][i]); //เอาค่า Main_id มา
                                Date date_ED_Ref = myData.stringChangetoDateWithOutTime(stringsDate_ED_Ref[0]); //dateRef ก่อนนำไป add ค่Calendar calendarRef = Calendar.getInstance();

                                Calendar calendarRef = Calendar.getInstance();
                                calendarRef.setTime(date_ED_Ref);  //calendarRef ก่อนนำไป add ค่า

                                //เทียบ date ที่ add ค่าแล้ว กับ dateAfterProcess จากข้างบน

                                if (queryDay[1].equals("0")) {
                                    dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                } else if (queryDay[1].equals("1")) {
                                    //วันเว้นวัน
                                    calendarRef.add(Calendar.DAY_OF_MONTH, 2);
                                    Date date = calendarRef.getTime();
                                    if (date.compareTo(dateRef) == 0) {
                                        dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                    }
                                } else if (queryDay[1].equals("2")) {
                                    //วันเว้นวัน
                                    calendarRef.add(Calendar.DAY_OF_MONTH, 3);
                                    Date date = calendarRef.getTime();
                                    if (date.compareTo(dateRef) == 0) {
                                        dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                    }
                                } else if (queryDay[1].equals("3")) {
                                    //วันเว้นวัน
                                    calendarRef.add(Calendar.DAY_OF_MONTH, 4);
                                    Date date = calendarRef.getTime();
                                    if (date.compareTo(dateRef) == 0) {
                                        dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                    }
                                } else if (queryDay[1].equals("4")) {
                                    //วันเว้นวัน
                                    calendarRef.add(Calendar.DAY_OF_MONTH, 5);
                                    Date date = calendarRef.getTime();
                                    if (date.compareTo(dateRef) == 0) {
                                        dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                    }
                                } else if (queryDay[1].equals("5")) {
                                    //วันเว้นวัน
                                    calendarRef.add(Calendar.DAY_OF_MONTH, 6);
                                    Date date = calendarRef.getTime();
                                    if (date.compareTo(dateRef) == 0) {
                                        dailyUpdateReceiver.checkWhich_Date_D = "Y";
                                    }
                                }


                            }


                        } // stringsReadAll_MainTABLE[10][i].equals("")

                        //Check ค่าทั้งหมดแล้วว่าผ่าน ให้ทำการ add ค่าเข้ามาได้

                        if (dailyUpdateReceiver.checkWhich_Date_D.equals("Y")
                                && dailyUpdateReceiver.checkStartDay.equals("Y")
                                && dailyUpdateReceiver.checkFinishDay.equals("Y")) {
                            Log.d("UpdatesumTABLE", "ตำแหน่งที่ i addค่าเข้า SumTABLE ได้ : " + i);
                            Toast.makeText(getBaseContext(), "Addข้อมูลลง sumTABLE : (Y,Y,Y)", Toast.LENGTH_LONG).show();

                            //เริ่ม addข้อมูลลง sumTABLE
                            //1 row ของ mainTABLE add ได้หลาย row ของ sumTABLE ตาม T1-T8 (column 12 - 19)
                            for (int x = 12; x <= 19; x++) {
                                if (!stringsReadAll_MainTABLE[x][i].equals("")) {
                                    String stringMain_id = stringsReadAll_MainTABLE[0][i];  //Main_id
                                    String stringTimeRef = stringsReadAll_MainTABLE[x][i];  //TimeRef ตำแหน่งต่างๆ
                                    myManage.addValueToSumTable(stringMain_id, stringDateRef, stringTimeRef, "", "", "");
                                    Log.d("UpdatesumTABLE", "addValueToSumTable : " + stringMain_id + " " + stringDateRef + " " + stringTimeRef);
                                }


                            }

                        } else {
                            Log.d("UpdatesumTABLE", "ตำแหน่งที่ i addค่าเข้า SumTABLE ไม่ได้ : " + i);
                        }


                    } //first "For"

                } while (dateRef.compareTo(dateFinalProcess) < 0);

            }


        }

        //สิ้นสุด 8/10/59

        //เรียงเวลาในการกินยาเผื่อมีคนใส่เวลาแบบไม่เรียง > <'''
        String[] stringsTime = {string7, string8, string9, string10,
                string11, string12, string13, string14};
        Date date00 = new Date();
        Date date01 = new Date();
        Date date02 = new Date();
        Date date03 = new Date();
        Date date04 = new Date();
        Date date05 = new Date();
        Date date06 = new Date();
        Date date07 = new Date();
        Date date_w;
        Date date_x;
        Date[] dates = {date00,date01,date02,date03,date04,date05,date06,date07};
        int iTimesPerDay = Integer.parseInt(textView4.getText().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        for (int z = 0; z < iTimesPerDay; z++) {
            try {
                dates[z] = dateFormat.parse(stringsTime[z]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        for (int w = 0; w < iTimesPerDay; w++) {
            for (int x = 0; x < iTimesPerDay; x++) {
                if (w < x) {
                    Log.d("testTimes","1 : " + Integer.toString(w) + Integer.toString(x));
                    if (dates[w].compareTo(dates[x]) > 0) {
                        Log.d("testTimes","2 : " + Integer.toString(w) + Integer.toString(x));
                        date_w = dates[w];
                        Log.d("testTimes","date_w : " +  dateFormat.format(date_w));
                        date_x = dates[x];
                        Log.d("testTimes","date_x : " +  dateFormat.format(date_x));
                        dates[w] = date_x;
                        Log.d("testTimes","date[w] : " +  dateFormat.format(dates[w]));
                        dates[x] = date_w;
                        Log.d("testTimes","date[x] : " +  dateFormat.format(dates[x]));
                    }
                }
            }
        }

        String stringsTime0 = dateFormat.format(dates[0]);
        String stringsTime1 = dateFormat.format(dates[1]);
        String stringsTime2 = dateFormat.format(dates[2]);
        String stringsTime3 = dateFormat.format(dates[3]);
        String stringsTime4 = dateFormat.format(dates[4]);
        String stringsTime5 = dateFormat.format(dates[5]);
        String stringsTime6 = dateFormat.format(dates[6]);
        String stringsTime7 = dateFormat.format(dates[7]);
        Log.d("testTimes","D0 : " + stringsTime0);
        Log.d("testTimes","D1 : " + stringsTime1);
        Log.d("testTimes","D2 : " + stringsTime2);
        Log.d("testTimes","D3 : " + stringsTime3);
        Log.d("testTimes","D4 : " + stringsTime4);
        Log.d("testTimes","D5 : " + stringsTime5);
        Log.d("testTimes","D6 : " + stringsTime6);
        Log.d("testTimes","D7 : " + stringsTime7);

        if (!string7.equals("")) {
            string7 = dateFormat.format(dates[0]);
        }
        if (!string8.equals("")) {
            string8 = dateFormat.format(dates[1]);
        }
        if (!string9.equals("")) {
            string9 = dateFormat.format(dates[2]);
        }
        if (!string10.equals("")) {
            string10 = dateFormat.format(dates[3]);
        }
        if (!string11.equals("")) {
            string11 = dateFormat.format(dates[4]);
        }
        if (!string12.equals("")) {
            string12 = dateFormat.format(dates[5]);
        }
        if (!string13.equals("")) {
            string13 = dateFormat.format(dates[6]);
        }
        if (!string14.equals("")) {
            string14 = dateFormat.format(dates[7]);
        }

        //ต่อไปทำ PRN ถ้าเป็น Y ให้
        /*
        if (string20.equals("Y")) {
            string7 = "";
            string8 = "";
            string9 = "";
            string10 = "";
            string11 = "";
            string12 = "";
            string13 = "";
            string14 = "";
            string18 = myData.currentDay();
            string19 = "";
        }
        */

        //ทำการสร้าง primarykey ใหม่
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyMMdd");
        String strNewMain_id = dateFormat1.format(System.currentTimeMillis());
        String[] strMain_id_Array = myManage.filterMainTABLE_Mainid6Digit(strNewMain_id);
        if (strMain_id_Array[0].equals("")) {
            strNewMain_id = strNewMain_id.concat("01");
        } else {
            //หาถ้าสูงสุด 1 อัน
            Log.d("25July16", "strMain_id : " + strNewMain_id);
            for(int i=0;i<strMain_id_Array.length;i++) {
                Log.d("25July16", "strMain_id_Array : " + strMain_id_Array[i]);
            }
            int i = strMain_id_Array.length;
            i = i + 1;
            String s = Integer.toString(i);
            if (s.length() == 1) {
                s = "0".concat(s);
            }
            strNewMain_id = strNewMain_id.concat(s);
            Log.d("25July16", "strMain_id_Array : " + strNewMain_id);

        }
        Log.d("25July16", "strMain_id : " + strNewMain_id);
        int iMain_id = Integer.parseInt(strNewMain_id);
        Log.d("25July16", "iMain_id : " + iMain_id);


        //เริ่มจากตรงนี้.... ถ้าไม่ได้เริ่มยาในวันนี้ต้องยังไม่แสดงหนะ
        myManage.addValueTomainTABLE(iMain_id,string1, string2, string3, string15, string4, string5, string16, string6, string18, string19, string20, string7, string8, string9, string10, string11, string12, string13, string14, "");


        //เอาค่า Med_id เป็นตัว query ในตาราง mainTABLE โดยเรียงจาก _id แบบ DESC
        String[] strings1 = myManage.readAllMainTABLE_string(string1, 0); //เอาค่าMain_id
        String[] stringsT1 = myManage.readAllMainTABLE_string(string1, 7); //T1
        String[] stringsT2 = myManage.readAllMainTABLE_string(string1, 8); //T2
        String[] stringsT3 = myManage.readAllMainTABLE_string(string1, 9); //T3
        String[] stringsT4 = myManage.readAllMainTABLE_string(string1, 10); //T4
        String[] stringsT5 = myManage.readAllMainTABLE_string(string1, 11); //T5
        String[] stringsT6 = myManage.readAllMainTABLE_string(string1, 12); //T6
        String[] stringsT7 = myManage.readAllMainTABLE_string(string1, 13); //T7
        String[] stringsT8 = myManage.readAllMainTABLE_string(string1, 14); //T8


        //ต้องทำอย่างไรก็ได้ check ให้ได้ก่อนว่าวันนี้ต้องกินยาหรือไม่

        // 290916 ==>> เริ่มทำการวนลูป 7 วันเพื่อเพิ่มค่าเข้าไปนะ


        for(int iAddDate = 0;iAddDate <=9;iAddDate++) {
            String stringDateBegin = myData.currentDay(); //ค่าของวันที่เริ่มต้นก็คือวันนี้
            String addSumTABLE_Today = "N";
            String stringIntervalDate = "N"; // Date อยู่ระหว่าง StartDate & FinishDate


            Date dateBegin = myData.stringChangetoDateWithOutTime(stringDateBegin);
            Date dateStartDate = myData.stringChangetoDateWithOutTime(string18);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateBegin);
            calendar.add(Calendar.DAY_OF_MONTH,iAddDate);

            Date dateAfterProcess = calendar.getTime();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String stringDateAfterProcess = simpleDateFormat.format(dateAfterProcess);


            if (dateAfterProcess.compareTo(dateStartDate) >= 0) {
                stringIntervalDate = "Y";
            }

            if (!string19.equals("")) {
                Date dateFinishDate = myData.stringChangetoDateWithOutTime(string19);
                if (dateAfterProcess.compareTo(dateFinishDate) > 0) {
                    stringIntervalDate = "N";
                }
            }


            //ต่อไปจะทำ AddsumTABLEToday
            String current_DayOfWeek = Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)); //เอาค่าตัวเลขของวันประจำสัปดาห์จาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว
            String current_DayOfMonth = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)); //เอาค่าตัวเลขของวันประจำเดือนจาก calendar ที่ทำการเพิ่มวันตั้งแต่ 0 - 6 เรียบร้อยแล้ว

            Log.d("queryDay", "current_DayofWeek : " + current_DayOfWeek);
            Log.d("queryDay", "current_DayofMonth : " + current_DayOfMonth);

            String[] queryDay = string4.split(":");
            String[] querySelectedDay = null;

            Log.d("queryDay", "queryDay0 : " + queryDay[0]);
            Log.d("queryDay", "queryDay1 : " + queryDay[1]);

            if (!queryDay[0].equals("ED")) {
                querySelectedDay = queryDay[1].split(",");
                for (int i = 0; i < querySelectedDay.length; i++) {
                    Log.d("queryDay", "querySelectedDay[] : " + querySelectedDay[i]);
                    if (queryDay[0].equals("DOW")) {
                        if (querySelectedDay[i].equals(current_DayOfWeek)) {
                            addSumTABLE_Today = "Y";
                        }
                    }
                    if (queryDay[0].equals("DOM")) {
                        if (querySelectedDay[i].equals(current_DayOfMonth)) {
                            addSumTABLE_Today = "Y";
                        }
                    }
                }

            } else {  //ถ้าเป็น ED จะมี 0 1 2 3 4 5

                if (stringDateBegin.equals(stringDateAfterProcess)) {
                    addSumTABLE_Today = "Y";
                } else {  //ถ้าไม่ใช่วันแรกแล้วเป็นวันอื่นๆ
                    //อยากให้ทำการตรวจสอบจากครั้งหลังสุดใน sumTABLE ที่ได้ยาตัวนี้

                    String stringDate_ED_Ref = Integer.toString(iMain_id);
                    String[] stringsDate_ED_Ref = myManage.filter_sumTABLE_finding_DateRef_by_MainId_idDESC(stringDate_ED_Ref);
                    //Date dateRef = myData.stringChangetoDateWithOutTime(stringsDate_ED_Ref[0]); //dateRef ก่อนนำไป add ค่า
                    Date dateRef = myData.stringChangetoDateWithOutTime(stringsDate_ED_Ref[0]); //dateRef ก่อนนำไป add ค่า
                    Calendar calendarRef = Calendar.getInstance();
                    calendarRef.setTime(dateRef);  //calendarRef ก่อนนำไป add ค่า

                    //เทียบ date ที่ add ค่าแล้ว กับ dateAfterProcess จากข้างบน

                    if (queryDay[1].equals("0")) {
                        addSumTABLE_Today = "Y";
                    } else if (queryDay[1].equals("1")) {
                        //วันเว้นวัน
                        calendarRef.add(Calendar.DAY_OF_MONTH,2);
                        Date date = calendarRef.getTime();
                        if (date.compareTo(dateAfterProcess) == 0) {
                            addSumTABLE_Today = "Y";
                        }
                    } else if (queryDay[1].equals("2")) {
                        //วันเว้นวัน
                        calendarRef.add(Calendar.DAY_OF_MONTH,3);
                        Date date = calendarRef.getTime();
                        if (date.compareTo(dateAfterProcess) == 0) {
                            addSumTABLE_Today = "Y";
                        }
                    } else if (queryDay[1].equals("3")) {
                        //วันเว้นวัน
                        calendarRef.add(Calendar.DAY_OF_MONTH,4);
                        Date date = calendarRef.getTime();
                        if (date.compareTo(dateAfterProcess) == 0) {
                            addSumTABLE_Today = "Y";
                        }
                    } else if (queryDay[1].equals("4")) {
                        //วันเว้นวัน
                        calendarRef.add(Calendar.DAY_OF_MONTH,5);
                        Date date = calendarRef.getTime();
                        if (date.compareTo(dateAfterProcess) == 0) {
                            addSumTABLE_Today = "Y";
                        }
                    } else if (queryDay[1].equals("5")) {
                        //วันเว้นวัน
                        calendarRef.add(Calendar.DAY_OF_MONTH,6);
                        Date date = calendarRef.getTime();
                        if (date.compareTo(dateAfterProcess) == 0) {
                            addSumTABLE_Today = "Y";
                        }
                    }





                }

            }

            //addValueToSumTable
            //*** ดูว่าเป็นวันนั้นๆ ต้องใส่ข้อมูลลงไปใน sumTABLE หรือไม่
            Log.d("addValueToSumTable", strings1[0] + " " + stringDateAfterProcess);

            if (addSumTABLE_Today.equals("Y") && stringIntervalDate.equals("Y")) {

                if (!stringsT1[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT1[0], "", "", "");
                }

                if (!stringsT2[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT2[0], "", "", "");
                }
                if (!stringsT3[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT3[0], "", "", "");
                }
                if (!stringsT4[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT4[0], "", "", "");
                }
                if (!stringsT5[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT5[0], "", "", "");
                }
                if (!stringsT6[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT6[0], "", "", "");
                }
                if (!stringsT7[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT7[0], "", "", "");
                }
                if (!stringsT8[0].equals("")) {
                    myManage.addValueToSumTable(strings1[0], stringDateAfterProcess, stringsT8[0], "", "", "");
                }

            }

        }  //for

        //Add จำนวนยาเข้า 2 TABLE
        doubleAmountMedicine = Double.parseDouble(editTextCalculationAmount.getText().toString());
        if (doubleAmountMedicine > 0) {

            String[][] stringstotalAmountTABLE = {myManage.readAlltotalAmountTABLE(0),
                    myManage.readAlltotalAmountTABLE(1),myManage.readAlltotalAmountTABLE(2),
                    myManage.readAlltotalAmountTABLE(3)};

            String sDate = myData.currentDateTime();
            String string_Main_id = Integer.toString(iMain_id);
            myManage.addValueTo_addUseTABLE(string_Main_id,"Add",doubleAmountMedicine,sDate);

            String s = "N";
            double doubleAmountInitialMedicine = 0;
            String s_id = null;
            for(int i = 0;i<stringstotalAmountTABLE[0].length;i++) {
                if (stringstotalAmountTABLE[1][i].equals(string_Main_id)) {
                    s = "Y";
                    doubleAmountInitialMedicine = Double.parseDouble(stringstotalAmountTABLE[2][i]);
                    s_id = stringstotalAmountTABLE[0][i];
                }
            }
            if (s.equals("N")) {
                myManage.addValueTo_totalAmountTABLE(string_Main_id, doubleAmountMedicine, sDate);
            } else if (s.equals("Y")) {
                //ทำการ UPdate
                doubleAmountMedicine = doubleAmountInitialMedicine + doubleAmountMedicine;
                String s_AmountMedicine = Double.toString(doubleAmountMedicine);
                myManage.updateTotalAmountTABLE(s_id, s_AmountMedicine, sDate);
                Log.d("MedicationAdd", "จำนวนเม็ดสะสม "+ s_AmountMedicine);

            }

        } //if(doubleAmountMedicine > 0)

        String[] strUser = myManage.filter_userTABLE(1);
        String strDate = myData.currentDay();
        myManage.update_Last_updated(strUser[0],strDate);


        Intent intent = new Intent(AddMedicine2Activity.this, MainActivity.class);
        startActivity(intent);
        //ปิด Activity สุดท้าย
        finish();





        /*

        String currentDay = myData.currentDay();  //ค่าของวันนี้ & ค่า String ของวันเริ่มต้น คือ string18
        String addSumTABLE_Today = "N";
        String startDateToday = "N";
        // เปลี่ยน String ให้เป็น Date เพื่อเปรียบเทียบ
        Date dateToday = myData.stringChangetoDateWithOutTime(currentDay);
        Date dateStartDate = myData.stringChangetoDateWithOutTime(string18);

        if (dateToday.compareTo(dateStartDate) == 0) {
            startDateToday = "Y";
        }


        String current_DayOfWeek = myData.current_DayOfWeek();  //ค่าเป็นเลข ของ DayofWeek
        String current_DayOfMonth = myData.current_DayOfMonth(); //ค่าเป็นเลข ของ DayofMonth

        Log.d("queryDay", "current_DayofWeek : " + current_DayOfWeek);
        Log.d("queryDay", "current_DayofMonth : " + current_DayOfMonth);


        String[] queryDay = string4.split(":");
        String[] querySelectedDay = null;

        Log.d("queryDay", "queryDay0 : " + queryDay[0]);
        Log.d("queryDay", "queryDay1 : " + queryDay[1]);

        if (!queryDay[0].equals("ED")) {
            querySelectedDay = queryDay[1].split(",");
            for (int i = 0; i < querySelectedDay.length; i++) {
                Log.d("queryDay", "querySelectedDay[] : " + querySelectedDay[i]);
                if (queryDay[0].equals("DOW")) {
                    if (querySelectedDay[i].equals(current_DayOfWeek)) {
                        addSumTABLE_Today = "Y";
                    }
                }
                if (queryDay[0].equals("DOM")) {
                    if (querySelectedDay[i].equals(current_DayOfMonth)) {
                        addSumTABLE_Today = "Y";
                    }
                }
            }

        } else {  //ถ้าเป็น ED จะมี 0 1 2 3 4 5



            addSumTABLE_Today = "Y";
        }

        Log.d("queryDay", "addSumTABLE_Today : " + addSumTABLE_Today);
        Log.d("queryDay", "startDateToday : " + startDateToday);

        //addValueToSumTable
        //*** ดูว่าเป็นวันนี้หรือป่าว ในวันที่เริ่มต้น
        Log.d("addValueToSumTable", strings1[0] + " " + currentDay);

        if (addSumTABLE_Today.equals("Y") && startDateToday.equals("Y")) {

            if (!stringsT1[0].equals("")) {
                myManage.addValueToSumTable(strings1[0], currentDay, stringsT1[0], "", "", "");
            }

            if (!stringsT2[0].equals("")) {
                myManage.addValueToSumTable(strings1[0], currentDay, stringsT2[0], "", "", "");
            }
            if (!stringsT3[0].equals("")) {
                myManage.addValueToSumTable(strings1[0], currentDay, stringsT3[0], "", "", "");
            }
            if (!stringsT4[0].equals("")) {
                myManage.addValueToSumTable(strings1[0], currentDay, stringsT4[0], "", "", "");
            }
            if (!stringsT5[0].equals("")) {
                myManage.addValueToSumTable(strings1[0], currentDay, stringsT5[0], "", "", "");
            }
            if (!stringsT6[0].equals("")) {
                myManage.addValueToSumTable(strings1[0], currentDay, stringsT6[0], "", "", "");
            }
            if (!stringsT7[0].equals("")) {
                myManage.addValueToSumTable(strings1[0], currentDay, stringsT7[0], "", "", "");
            }
            if (!stringsT8[0].equals("")) {
                myManage.addValueToSumTable(strings1[0], currentDay, stringsT8[0], "", "", "");
            }

        }


        //Add จำนวนยาเข้า 2 TABLE
        doubleAmountMedicine = Double.parseDouble(editTextCalculationAmount.getText().toString());
        if (doubleAmountMedicine > 0) {

            String[][] stringstotalAmountTABLE = {myManage.readAlltotalAmountTABLE(0),
                    myManage.readAlltotalAmountTABLE(1),myManage.readAlltotalAmountTABLE(2),
                    myManage.readAlltotalAmountTABLE(3)};

            String sDate = myData.currentDateTime();
            String string_Main_id = Integer.toString(iMain_id);
            myManage.addValueTo_addUseTABLE(string_Main_id,"Add",doubleAmountMedicine,sDate);

            String s = "N";
            double doubleAmountInitialMedicine = 0;
            String s_id = null;
            for(int i = 0;i<stringstotalAmountTABLE[0].length;i++) {
                if (stringstotalAmountTABLE[1][i].equals(string_Main_id)) {
                    s = "Y";
                    doubleAmountInitialMedicine = Double.parseDouble(stringstotalAmountTABLE[2][i]);
                    s_id = stringstotalAmountTABLE[0][i];
                }
            }
            if (s.equals("N")) {
                myManage.addValueTo_totalAmountTABLE(string_Main_id, doubleAmountMedicine, sDate);
            } else if (s.equals("Y")) {
                //ทำการ UPdate
                doubleAmountMedicine = doubleAmountInitialMedicine + doubleAmountMedicine;
                String s_AmountMedicine = Double.toString(doubleAmountMedicine);
                myManage.updateTotalAmountTABLE(s_id, s_AmountMedicine, sDate);
                Log.d("MedicationAdd", "จำนวนเม็ดสะสม "+ s_AmountMedicine);

            }

        } //if(doubleAmountMedicine > 0)



        Intent intent = new Intent(AddMedicine2Activity.this, MainActivity.class);
        startActivity(intent);
        //ปิด Activity สุดท้าย
        finish();

        */

    }

    //111059 ไม่ได้ใช้แล้ว ไม่อยากใช้การ setRepeating
    private void checkDailyUpdateReceiver1() {

        Calendar calendar = Calendar.getInstance();
        Calendar myCalendar1 = (Calendar) calendar.clone();

        myCalendar1.set(Calendar.HOUR_OF_DAY, 0);
        myCalendar1.set(Calendar.MINUTE, 0);
        myCalendar1.set(Calendar.SECOND, 0);
        myCalendar1.set(Calendar.MILLISECOND, 10);


        Random random = new Random();
        int myRandom = random.nextInt(1000);

        Intent intent = new Intent(getBaseContext(), DailyUpdateReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),
                myRandom, intent, 0);


        AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(1, myCalendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Log.d("UpdatesumTABLE", "ทำ Alarm ขึ้นเองได้แล้ว" + myCalendar1.getTime().toString());
        Toast.makeText(getBaseContext(),"เริ่มทำการ BroadCAst",Toast.LENGTH_LONG).show();

    } //ทำการ BroadCast (checkDailyUpdateReceiver1)




    private void alertDialogInteraction(String s1, String s2, String s3, String s4) {


        MyManage myManage = new MyManage(this);
        s2 = myManage.findGenerinName_nameGenericTABLE_by_id(s2);


        stringGenericName2 = myManage.find_id_medTABLE_by_Generic_name(s3);
        //ต้องทำการ นับจำนวน stringGenericName2 แล้วทำการ bufferString ให้ได้ค่าของ String ออกมา
        //แล้วเอาไปใส่แทน ใน setMessage
        //สรุปคือ ทำ StringBuilder ในการหา tradename ทั้งหมดที่อยู่ใน mainTABLE เอามาแสดงดูว่าเกิดการตีกันของยา
        StringBuilder stringBuilder = new StringBuilder("ยา :");
        for (int i = 0; i < stringGenericName2.length; i++) {
            stringBuilder.append(stringGenericName2[i]);
            stringBuilder.append(" ");
        }

        myManage.filter_drugInteractionTABLE_Dialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon_question);
        builder.setTitle("เกิดปฏิกิริยาระหว่างยา (ยาตีกัน)");
        builder.setMessage("ยา " + s1 + " (" + s2 + ") \nเกิดปฏิกิริยาระหว่างยากับ\n" + stringBuilder + "\nเหตุผล : " + s4);
        builder.setPositiveButton("ยืนยันการรับประทาน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //addValueTomainTABLEandIntent();
                stringArrayListResultType2Count.add("N"); //เพิ่มข้อมูลเข้าไปใน stringArray แบบที่ 2
                checkaddValueTomainTABLEandIntent();


            }
        });
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();

    }

    private void checkaddValueTomainTABLEandIntent() {

        String[] stringstype2 = new String[stringArrayListResultType2.size()];
        String[] stringstype2Count = new String[stringArrayListResultType2Count.size()];
        String[] stringstype3 = new String[stringArrayListResultType3.size()];
        String[] stringstype3Count = new String[stringArrayListResultType3Count.size()];

        stringstype2 = stringArrayListResultType2.toArray(stringstype2);
        stringstype2Count = stringArrayListResultType2Count.toArray(stringstype2Count);
        stringstype3 = stringArrayListResultType3.toArray(stringstype3);
        stringstype3Count = stringArrayListResultType3Count.toArray(stringstype3Count);



        Log.d("Flow_Test", "stringstype2.length : " + stringstype2.length);
        Log.d("Flow_Test", "stringstype2Count : " + stringstype2Count.length);
        Log.d("Flow_Test", "stringstype3.length : " + stringstype3.length);
        Log.d("Flow_Test", "stringstype3Count : " + stringstype3Count.length);

        if (stringstype2.length == stringstype2Count.length &&
                stringstype3.length == stringstype3Count.length) {
            Log.d("Flow_Test", "เข้า if .... เท่ากันแล้ว");
            //addValueTomainTABLEandIntent();
            checkZeroAmountTablet();

        }

    }

    private void alertDialogInteractionType3(String s1,String s2,String s3,
                                             String s4,String s5, String s6) {
        //tradename,Generic name(id),Generic name(id ยาคู่), drugInteraction12,drugInteraction21
        MyManage myManage = new MyManage(this);

        s2 = myManage.findGenerinName_nameGenericTABLE_by_id(s2); //เปลี่ยน Generic name ตัวเลขเป็น ตัวอักษร
        stringGenericName2 = myManage.find_id_medTABLE_by_Generic_name(s3);
        StringBuilder stringBuilder = new StringBuilder("ยา :");
        for (int i = 0; i < stringGenericName2.length; i++) {
            stringBuilder.append(stringGenericName2[i]);
            stringBuilder.append(" ");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon_question);
        builder.setTitle("ยาที่ควรทานห่างกัน");
        builder.setMessage("ยา " + s1 + " (" + s2 + ") \nควรทานห่างจาก" + stringBuilder +
                "\nเหตุผล : " + s4 + "\nหมายเหตุ!! \nควรทานยา " + s1 + " ก่อน"+stringBuilder +" อย่างน้อย " + s5 +
                " นาที\nหรือควรทานยา" + s1 + " หลัง"+stringBuilder+ " อย่างน้อย " + s6 + " นาที");
        builder.setPositiveButton("ยืนยันการรับประทาน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stringArrayListResultType3Count.add("N"); //นับตัว type 3
                checkaddValueTomainTABLEandIntent();

            }
        });
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();

    }

    private void alertDialogFaltal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.faltal_drug);
        builder.setTitle("Fatal Drug Interaction");
        builder.setMessage("ยานี้ไม่สามารถทานร่วมกับ\n ยาบางตัวที่ท่านรับประทานอยู่ \n โปรดปรึกษาแพทย์หรือเภสัชกรก่อน \n (ไม่แนะนำให้รับประทานอย่างเด็ดขาด!!!)");
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }




}  //Main Class
