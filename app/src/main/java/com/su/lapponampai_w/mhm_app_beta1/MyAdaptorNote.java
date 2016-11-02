package com.su.lapponampai_w.mhm_app_beta1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by apple on 7/15/16.
 */
public class MyAdaptorNote extends BaseAdapter {
    Context context;
    String[] stringsDate, stringsNote;

    public MyAdaptorNote(Context context, String[] stringsDate, String[] stringsNote) {
        this.context = context;
        this.stringsDate = stringsDate;
        this.stringsNote = stringsNote;
    }

    @Override
    public int getCount() {
        return stringsDate.length;
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
        View view = layoutInflater.inflate(R.layout.my_note_listview, parent, false);

        TextView textViewDate = (TextView) view.findViewById(R.id.textView123);
        textViewDate.setText(stringsDate[position]);


        TextView textViewNote = (TextView) view.findViewById(R.id.textView122);
        textViewNote.setText(stringsNote[position]);

        return view;
    }
}
