package com.su.lapponampai_w.mhm_app_beta1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by apple on 7/4/16.
 */
public class MyAdaptorAdherence extends BaseAdapter {

    Context context;
    String[] stringsTradeName, stringsPercentAdherence;
    int[] intsAdherence, intsDisplay;

    public MyAdaptorAdherence(Context context,
                              String[] stringsTradeName,
                              String[] stringsPercentAdherence,
                              int[] intsAdherence,
                              int[] intsDisplay) {

        this.context = context;
        this.stringsTradeName = stringsTradeName;
        this.stringsPercentAdherence = stringsPercentAdherence;
        this.intsAdherence = intsAdherence;
        this.intsDisplay = intsDisplay;
    }

    @Override
    public int getCount() {
        return stringsTradeName.length;
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
        View view = layoutInflater.inflate(R.layout.my_daily_listview, parent, false);

        TextView textViewTradename = (TextView) view.findViewById(R.id.textView94);
        textViewTradename.setText(stringsTradeName[position]);

        TextView textViewAdherence = (TextView) view.findViewById(R.id.textView96);
        textViewAdherence.setText(stringsPercentAdherence[position]);

        ImageView imageViewAdherence = (ImageView) view.findViewById(R.id.imageView4);
        imageViewAdherence.setImageResource(intsAdherence[position]);

        ImageView imageViewDisplay = (ImageView) view.findViewById(R.id.imageView5);
        imageViewDisplay.setImageResource(intsDisplay[position]);

        return view;
    }
}
