package com.su.lapponampai_w.mhm_app_beta1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by apple on 7/3/16.
 */
public class MyViewPagerAdaptor extends FragmentPagerAdapter{

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();

    public void addFragments(Fragment fragment,String titles) {
        this.fragments.add(fragment);
        this.tabTitles.add(titles);
    }

    public MyViewPagerAdaptor(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

}
