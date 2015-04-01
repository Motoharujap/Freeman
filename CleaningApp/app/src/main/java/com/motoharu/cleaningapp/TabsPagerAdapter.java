package com.motoharu.cleaningapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int index) {
        switch(index) {
            case 0:
                return new OrderFragment();
            case 1:
                return new HomeFragment();
            case 2:
                return new StatsFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 3;
    }
}
