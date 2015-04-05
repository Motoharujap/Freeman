package com.motoharu.cleaningapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Database.DBHelper;
import ObjectModel.Order;
import ObjectModel.User;


public class Confirm_order extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    //order details
    String phoneInp, accBdInp, accStrInp, accApInp, retBdInp, retStrInp, retApInp;

    //constants
    private static final String FILL_DATA = "dont fill data";
    private boolean _fillData = true;

    private DBHelper mDBhelper;
    private Order _order;
    private EditText phoneTv, accStrTv, accBdTv, accApTv, retStrTv, retBdTv, retApTv;
    private Button save, confirm;
    private TextView summTotal;
    private int summ;
    private User user;
    private boolean _isEmptyViewExists = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        mDBhelper = new DBHelper(this);
        user = User.getInstance();
        this.getSupportLoaderManager().initLoader(0, null, this);
        Intent intent = getIntent();
        //TODO get summ info from order, not by intent
        summ = intent.getIntExtra("SUMM", 1);
        _fillData = intent.getBooleanExtra(FILL_DATA, true);
        //widgets
        phoneTv = (EditText) findViewById(R.id.phoneTvOrder);
        accBdTv = (EditText) findViewById(R.id.accBldTvOrder);
        accStrTv = (EditText) findViewById(R.id.accStreetTvOrder);
        accApTv = (EditText) findViewById(R.id.accApTvOrder);
        retBdTv = (EditText) findViewById(R.id.retBdTvOrder);
        retStrTv = (EditText) findViewById(R.id.retStrTvOrder);
        retApTv = (EditText) findViewById(R.id.retApTvOrder);

        //filling widgets with data
        if (_fillData) {
            fillData();
        }

        summTotal = (TextView) findViewById(R.id.summTotal);
        summTotal.setText(summ + " руб.");

        confirm = (Button) findViewById(R.id.buttonGoOn);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                if (!_isEmptyViewExists) {
                    Intent intent = new Intent(getBaseContext(), MainScreen.class);
                    startActivity(intent);
                    writeToOrderTable();
                    Toast.makeText(getBaseContext(), "Заказ был успешно создан", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    public void fillData()
    {
        phoneTv.setText(user.getphoneNumber());
        accBdTv.setText(user.getaccBd());
        accStrTv.setText(user.getaccStr());
        accApTv.setText(user.getaccAp());
        retBdTv.setText(user.getretBd());
        retStrTv.setText(user.getretStr());
        retApTv.setText(user.getretAp());
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }

    public void saveData()
    {
        ArrayList<EditText> list = new ArrayList<EditText>();
        list.add(phoneTv);
        list.add(accBdTv);
        list.add(accStrTv);
        list.add(accApTv);
        list.add(retBdTv);
        list.add(retStrTv);
        list.add(retApTv);
        _isEmptyViewExists = false;
        //checking for an empty edit text, if there is one - show keyboard and request focus
        for (EditText et : list){
            if (et.getText().toString().trim().length() == 0){
                _isEmptyViewExists = true;
                et.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            }
        }
        if (_isEmptyViewExists){
            Toast.makeText(getBaseContext(), getResources().getString(R.string.cannotBeEmpty), Toast.LENGTH_SHORT).show();
        }
        if (!_isEmptyViewExists) {
            phoneInp = phoneTv.getText().toString();
            user.setphoneNumber(phoneInp);

            accBdInp = accBdTv.getText().toString();
            user.setaccBd(accBdInp);

            accStrInp = accStrTv.getText().toString();
            user.setaccStr(accStrInp);

            accApInp = accApTv.getText().toString();
            user.setaccAp(accApInp);

            retBdInp = retBdTv.getText().toString();
            user.setretBd(retBdInp);

            retStrInp = retStrTv.getText().toString();
            user.setretStr(retStrInp);

            retApInp = retApTv.getText().toString();
            user.setretAp(retApInp);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.confirm_order, menu);
        return true;
    }

    @Override
    public void onRestart()
    {
        super.onRestart();

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
    //TODO find out wtf is cursor loader and if I need it here or not
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new MyCursorLoader(this, mDBhelper);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
    static class MyCursorLoader extends CursorLoader {

        DBHelper db;

        public MyCursorLoader(Context context, DBHelper db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllUsers();
            return cursor;
        }

    }

    public void writeToOrderTable(){
        Intent intent = getIntent();
        int shirtsQ = intent.getIntExtra("SHIRTSQ", 1);
        Date date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd");
        _order = new Order(user.getuserId(), shirtsQ, String.valueOf(summ), dt.format(date), "Заказ не обработан", phoneInp, accStrInp, accBdInp, accApInp, retStrInp,
                retBdInp, retApInp);
        _order.saveToDB(Confirm_order.this);
    }
}
