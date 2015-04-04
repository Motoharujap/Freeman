package com.motoharu.cleaningapp;

import android.annotation.TargetApi;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.app.ActionBar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.motoharu.cleaningapp.UserInterface.SettingsActivity;

import Database.DBHelper;
import Logic.Dialogs;
import Logic.InterfaceDialogCallback;
import login_passwords_and_blackjack.SaveSharedPreferences;


public class MainScreen extends ActionBarActivity {
    ViewPager mViewPager;
    private TabsPagerAdapter tpa;
    private InterfaceDialogCallback _yesCallback, _noCallback;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        tpa = new TabsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener(){
                    @Override
                public void onPageSelected(int position)
                    {
                        getActionBar().setSelectedNavigationItem(position);
                    }
                }
        );
        mViewPager.setAdapter(tpa);
        final android.app.ActionBar ab = getActionBar();
        ab.setNavigationMode(ab.NAVIGATION_MODE_TABS);
        ab.setDisplayShowHomeEnabled(true);
        ActionBar.TabListener tl = new ActionBar.TabListener() {

            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };
        ab.addTab(ab.newTab().setText("Заказать!").setTabListener(tl));
        ab.addTab(ab.newTab().setText("Мои заказы").setTabListener(tl));
        ab.addTab(ab.newTab().setText("Профиль").setTabListener(tl));
    }

    public void onBackPressed()
    {
        _yesCallback = new InterfaceDialogCallback() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };
        _noCallback = new InterfaceDialogCallback() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        };
        Dialogs.makeYesNoDialog(this,
                getResources().getString(R.string.exit),
                getResources().getString(R.string.exitConfirmation),
                _yesCallback,
                _noCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.profile:
                Intent intent = new Intent(this, EditProfile.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                OrderFragment of = new OrderFragment();
                of.count = 0;
                SaveSharedPreferences.setUserName(MainScreen.this, "");
                intent = new Intent(this, LoginStart.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
