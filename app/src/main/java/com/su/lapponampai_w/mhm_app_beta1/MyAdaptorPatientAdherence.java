package com.su.lapponampai_w.mhm_app_beta1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by apple on 10/29/16.
 */

public class MyAdaptorPatientAdherence extends BaseAdapter {

    Context context;
    String[] stringsTradeName;
    int[] intsAdherence, intsMedicine, intsStar1, intsStar2,
            intsStar3, intsStar4, intsStar5, intsStar6, intsStar7, intsStar8;


    public MyAdaptorPatientAdherence(Context context, String[] stringsTradeName,
                                     int[] intsAdherence, int[] intsMedicine,
                                     int[] intsStar1, int[] intsStar2, int[] intsStar3,
                                     int[] intsStar4, int[] intsStar5, int[] intsStar6,
                                     int[] intsStar7, int[] intsStar8) {
        this.context = context;
        this.stringsTradeName = stringsTradeName;
        this.intsAdherence = intsAdherence;
        this.intsMedicine = intsMedicine;
        this.intsStar1 = intsStar1;
        this.intsStar2 = intsStar2;
        this.intsStar3 = intsStar3;
        this.intsStar4 = intsStar4;
        this.intsStar5 = intsStar5;
        this.intsStar6 = intsStar6;
        this.intsStar7 = intsStar7;
        this.intsStar8 = intsStar8;
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
        View view = layoutInflater.inflate(R.layout.my_adherence_listview, parent, false);

        TextView textViewTradename = (TextView) view.findViewById(R.id.textView203);
        textViewTradename.setText(stringsTradeName[position]);

        ImageView imageViewAdherence = (ImageView) view.findViewById(R.id.imageView7);
        imageViewAdherence.setImageResource(intsAdherence[position]);
        ImageView imageViewMedicine = (ImageView) view.findViewById(R.id.imageView8);
        imageViewMedicine.setImageResource(intsMedicine[position]);
        ImageView imageViewStar1 = (ImageView) view.findViewById(R.id.imageView9);
        imageViewStar1.setImageResource(intsStar1[position]);
        ImageView imageViewStar2 = (ImageView) view.findViewById(R.id.imageView10);
        imageViewStar2.setImageResource(intsStar2[position]);
        ImageView imageViewStar3 = (ImageView) view.findViewById(R.id.imageView11);
        imageViewStar3.setImageResource(intsStar3[position]);
        ImageView imageViewStar4 = (ImageView) view.findViewById(R.id.imageView12);
        imageViewStar4.setImageResource(intsStar4[position]);
        ImageView imageViewStar5 = (ImageView) view.findViewById(R.id.imageView13);
        imageViewStar5.setImageResource(intsStar5[position]);
        ImageView imageViewStar6 = (ImageView) view.findViewById(R.id.imageView14);
        imageViewStar6.setImageResource(intsStar6[position]);
        ImageView imageViewStar7 = (ImageView) view.findViewById(R.id.imageView15);
        imageViewStar7.setImageResource(intsStar7[position]);
        ImageView imageViewStar8 = (ImageView) view.findViewById(R.id.imageView16);
        imageViewStar8.setImageResource(intsStar8[position]);

        return view;
    }
}
