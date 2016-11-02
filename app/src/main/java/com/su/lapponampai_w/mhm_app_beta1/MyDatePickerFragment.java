package com.su.lapponampai_w.mhm_app_beta1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.DatePicker;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by apple on 6/12/16.
 */
public class MyDatePickerFragment extends DialogFragment{

    private Activity mActivity;  //คือ Activity
    private DatePickerDialog.OnDateSetListener dateSetListener;




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

        try {
            dateSetListener = (DatePickerDialog.OnDateSetListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDateSetListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();


        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, dateSetListener, year, month, day);
        return datePickerDialog;

    }


}
