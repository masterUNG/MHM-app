package com.su.lapponampai_w.mhm_app_beta1;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by apple on 6/12/16.
 */
public class MyTimePickerFragment extends DialogFragment {

    private Activity mActivity;  //คือ Activity
    private TimePickerDialog.OnTimeSetListener mListener;  //ที่เอาไป Implement ที่ Activity อื่น
    private int hour, minute;

    @Override // Overwrite มาจาก App.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;

        try {
            mListener = (TimePickerDialog.OnTimeSetListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement OnTimeSetListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);




        TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, mListener, hour, minute, android.text.
                format.DateFormat.is24HourFormat(mActivity));
        timePickerDialog.setTitle("โปรดระบุเวลารับประทาน");

        return timePickerDialog;
    }



}  //Main Class
