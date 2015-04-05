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
import java.util.HashMap;

import Database.DBHelper;
import Database.DBOperationCreator;
import ObjectModel.Order;
import ObjectModel.User;


public class OrderDetails extends Activity {
    private TextView accBdTV, accStreetTV, accApTV, retBdTV, retStrTV, retApTV;
    private User user;
    private Order order;
    private int orderid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        user = User.getInstance();
        order = new Order();
        Intent intent = getIntent();
        orderid = intent.getIntExtra(DBHelper.KEY_CORDER_ID, 0);
        order.initializeCurrentOrder(orderid, OrderDetails.this);
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
        //TODO pass context to the DBO constructor instead of passing it to every method
        DBOperationCreator dbo = new DBOperationCreator(OrderDetails.this);
        HashMap<String, String> map = dbo.getCurrentOrderInfo(orderid);
        accStreetTV.setText(map.get(Order.ACCEPT_ADDRESS_STREET));
        accBdTV.setText(map.get(Order.ACCEPT_ADDRESS_BD));
        accApTV.setText(map.get(Order.ACCEPT_ADDRESS_AP));
        retBdTV.setText(map.get(Order.RETURN_ADDRESS_BD));
        retStrTV.setText(map.get(Order.RETURN_ADDRESS_STREET));
        retApTV.setText(map.get(Order.RETURN_ADDRESS_AP));
    }
}
