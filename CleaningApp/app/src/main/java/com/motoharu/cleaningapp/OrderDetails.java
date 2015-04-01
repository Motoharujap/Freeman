package com.motoharu.cleaningapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.SQLException;

import ObjectModel.User;


public class OrderDetails extends Activity {
    private TextView accBdTV, accStreetTV, accApTV, retBdTV, retStrTV, retApTV;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        user = User.getInstance();
        accBdTV = (TextView) findViewById(R.id.accBdStatsD);
        accStreetTV = (TextView) findViewById(R.id.accStrStatsD);
        accApTV = (TextView) findViewById(R.id.accApStatsD);
        retBdTV = (TextView) findViewById(R.id.retBdStatsD);
        retStrTV = (TextView) findViewById(R.id.retStreetStatsD);
        retApTV = (TextView) findViewById(R.id.retApStatsD);
        fillData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void fillData(){
        //TODO currently, all order data is being updated, need to make another table with orders, that keeps info about current order
        accStreetTV.setText(user.getaccStr());
        accBdTV.setText(user.getaccBd());
        accApTV.setText(user.getaccAp());
        retBdTV.setText(user.getretBd());
        retStrTV.setText(user.getretStr());
        retApTV.setText(user.getretAp());
    }
}
