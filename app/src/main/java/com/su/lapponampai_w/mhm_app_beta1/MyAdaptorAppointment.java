package com.su.lapponampai_w.mhm_app_beta1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by apple on 7/12/16.
 */
public class MyAdaptorAppointment extends BaseAdapter{
    Context context;
    String[] stringsDoctor, stringsDate, stringsTime, stringsNote;

    public MyAdaptorAppointment(Context context, String[] stringsDoctor, String[] stringsDate, String[] stringsTime, String[] stringsNote) {
        this.context = context;
        this.stringsDoctor = stringsDoctor;
        this.stringsDate = stringsDate;
        this.stringsTime = stringsTime;
        this.stringsNote = stringsNote;
    }

    @Override
    public int getCount() {
        return stringsDoctor.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.my_appointment_listview, parent, false);

        TextView textViewDoctor = (TextView) view.findViewById(R.id.textView112);
        textViewDoctor.setText(stringsDoctor[position]);

        TextView textViewDate = (TextView) view.findViewById(R.id.textView113);
        textViewDate.setText(stringsDate[position]);

        TextView textViewTime = (TextView) view.findViewById(R.id.textView114);
        textViewTime.setText(stringsTime[position]);

        TextView textViewNote = (TextView) view.findViewById(R.id.textView115);
        textViewNote.setText(stringsNote[position]);

        return view;
    }
}
