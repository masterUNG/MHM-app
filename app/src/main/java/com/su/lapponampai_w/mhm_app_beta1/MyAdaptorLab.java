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
public class MyAdaptorLab extends BaseAdapter{

    Context context;
    String[] strings1;
    String[] strings2;
    String[] strings3;
    String[] strings4;
    String[] strings5;
    String[] strings6;
    String[] strings7;
    String[] stringsLabDate;

    public MyAdaptorLab(Context context, String[] strings1, String[] strings2, String[] strings3,
                        String[] strings4, String[] strings5, String[] strings6, String[] strings7,
                        String[] stringsLabDate) {
        this.context = context;
        this.strings1 = strings1;
        this.strings2 = strings2;
        this.strings3 = strings3;
        this.strings4 = strings4;
        this.strings5 = strings5;
        this.strings6 = strings6;
        this.strings7 = strings7;
        this.stringsLabDate = stringsLabDate;
    }

    @Override
    public int getCount() {
        return stringsLabDate.length;
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
        View view = layoutInflater.inflate(R.layout.my_lab_listview, parent, false);

        TextView textView1 = (TextView) view.findViewById(R.id.textView145);
        TextView textView2 = (TextView) view.findViewById(R.id.textView146);
        TextView textView3 = (TextView) view.findViewById(R.id.textView147);
        TextView textView4 = (TextView) view.findViewById(R.id.textView148);
        TextView textView5 = (TextView) view.findViewById(R.id.textView149);
        TextView textView6 = (TextView) view.findViewById(R.id.textView150);
        TextView textView7 = (TextView) view.findViewById(R.id.textView151);
        TextView textViewLabDate = (TextView) view.findViewById(R.id.textView142);

        textView1.setText(strings1[position]);
        textView2.setText(strings2[position]);
        textView3.setText(strings3[position]);
        textView4.setText(strings4[position]);
        textView5.setText(strings5[position]);
        textView6.setText(strings6[position]);
        textView7.setText(strings7[position]);
        textViewLabDate.setText(stringsLabDate[position]);


        return view;
    }
}
