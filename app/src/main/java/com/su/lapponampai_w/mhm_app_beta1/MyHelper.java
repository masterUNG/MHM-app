package com.su.lapponampai_w.mhm_app_beta1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by apple on 4/20/16.
 */
public class MyHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MHM.db";
    private static final int DATABASE_VERSION = 1;

    //Create userTABLE
    private static final String CREATE_userTABLE = "create table userTABLE" +
            "(_id integer primary key, " +
            "User text, Password text, Stay text, " +
            "HN text, Last_updated text, Notification text, Allowed_notif text);";

    //Create table medTABLE
    private static final String CREATE_medTABLE = "create table medTABLE" +
            "(_id integer primary key, Trade_name text, Key_search text, " +
            "Generic_name1 integer, Dosage1 text, UOM1 text, " +
            "Generic_name2 integer, Dosage2 text, UOM2 text, " +
            "Generic_name3 integer, Dosage3 text, UOM3 text, " +
            "Generic_name4 integer, Dosage4 text, UOM4 text, " +
            "EA text, Amount_tablet double, " +
            "Which_Date_D text, Appearance text, Pharmaco text, T1 text, T2 text," +
            "T3 text, T4 text,T5 text, T6 text,T7 text, T8 text);";


    //Create table mainTABLE
    private static final String CREATE_mainTABLE = "create table mainTABLE" +
            "(_id integer primary key, Med_id integer, Trade_name text, " +
            "Generic_line text, Amount_tablet double, Which_Date_D text, Appearance text, " +
            "EA text, pharmaco text, StartDate text, FinishDate text, PRN text, " +
            "T1 text, T2 text, T3 text, T4 text,T5 text, T6 text, " +
            "T7 text, T8 text, DateTimeCanceled text);";


    //Create table nameGenericTABLE
    private static final String CREATE_nameGenericTABLE = "create table nameGenericTABLE" +
            "(_id integer primary key, Generic_name text);";


    //Create table sumTABLE
    private static final String CREATE_sumTABLE = "create table sumTABLE " +
            "(_id integer primary key, Main_id text, DateRef text, TimeRef text," +
            "DateCheck text, TimeCheck text, SkipHold text);";

    //Create table drugInteractionTABLE
    private static final String CREATE_drugInteractionTABLE = "create table drugInteractionTABLE " +
            "(_id integer primary key, Medicine1 integer, Medicine2 integer, " +
            "Type_interaction text, Message text, TimeMedicine1_2 integer, TimeMedicine2_1 integer);";

    //Create table drugInteractionTABLE_For_Query
    private static final String CREATE_drugInteractionTABLE_For_Query = "create table drugInteractionTABLE_For_Query " +
            "(_id integer primary key, Initial_medicine integer, Medicine1 integer, Medicine2 integer, " +
            "Type_interaction text, Message text, TimeMedicine1_2 integer, TimeMedicine2_1 integer);";

    //Create table timeTABLE
    private static final String CREATE_timeTABLE = "create table timeTABLE " +
            "(_id integer primary key, Time_interval text, Start_time text, End_time text);";

    //Create table displayTABLE
    private static final String CREATE_displayTABLE = "create table displayTABLE " +
            "(_id integer primary key, Position text, Sum_id text, Main_id text, Day text, " +
            "TimeRef text, DateTimeCheck text, Appearance text, SkipHold text);";

    //Create table newsTABLE
    private static final String CREATE_newsTABLE = "create table newsTABLE " +
            "(_id integer primary key, Generic_id text, Message text," +
            " Appearance_News text, Criteria text, Activity text);";

    //Create table addUseTABLE
    private static final String CREATE_addUseTABLE = "create table addUseTABLE " +
            "(_id integer primary key, Main_id text, Add_Use_Adjust_txt text, Amount double, " +
            "Date text);";

    //Create table totalAmountTABLE
    private static final String CREATE_totalAmountTABLE = "create table totalAmountTABLE " +
            "(_id integer primary key, Main_id text, TotalAmount double, DateUpdated text);";

    //Create table appointmentTABLE
    private static final String CREATE_appointmentTABLE = "create table appointmentTABLE " +
            "(_id integer primary key, DateTimeSave Text, AppointmentDate Text, " +
            "AppointmentTime Text, AppointmentDoctor Text, AppointmentNote Text);";

    //Create table noteTABLE
    private static final String CREATE_noteTABLE = "create table noteTABLE " +
            "(_id integer primary key, DateTimeSave Text, NoteDate Text, " +
            "NoteText Text, Allergy_SideEffect Text);";

    //Create table labTABLE
    private static final String CREATE_labTABLE = "create table labTABLE " +
            "(_id integer primary key, DateTimeSave Text, LabDate Text, BloodGlucose Text, BloodPressure Text, " +
            "Weight Text, Temperature Text, LDLCholesterol Text, CD4 Text, ViralLoad Text);";

    public MyHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_userTABLE);
        db.execSQL(CREATE_medTABLE);
        db.execSQL(CREATE_nameGenericTABLE);
        db.execSQL(CREATE_mainTABLE);
        db.execSQL(CREATE_sumTABLE);
        db.execSQL(CREATE_drugInteractionTABLE);
        db.execSQL(CREATE_drugInteractionTABLE_For_Query);
        db.execSQL(CREATE_timeTABLE);
        db.execSQL(CREATE_displayTABLE);
        db.execSQL(CREATE_newsTABLE);
        db.execSQL(CREATE_addUseTABLE);
        db.execSQL(CREATE_totalAmountTABLE);
        db.execSQL(CREATE_appointmentTABLE);
        db.execSQL(CREATE_noteTABLE);
        db.execSQL(CREATE_labTABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}  //Main Class
