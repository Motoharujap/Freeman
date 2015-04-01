package com.motoharu.cleaningapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import Database.DBHelper;


public class EditProfile extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    Button save;
    DBHelper mDBhelper;
    EditText phone, accHouse, accStreet, accAp, retBld, retStreet, retAp, name, secName, surName, email, pass, repPass;
    TextView lvVal;
    CheckBox chk;
    LinearLayout str, hs, apL;
    String phoneInp = "";
    String nameInp = "";
    String secnameInp = "";
    String surnameInp = "";
    String accHouseInp = "";
    String accStreetInp = "";
    String accApInp = "";
    String retHouseIp = "";
    String retStreetIp = "";
    String retApIp = "";
    String emailInp = "";
    String passInp = "";
    String repPassInp = "";
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        mDBhelper = new DBHelper(this);
        this.getSupportLoaderManager().initLoader(0, null, this);
        save = (Button) findViewById(R.id.saveEditButton);

        //id
        Intent intent = getIntent();
        id = 0;
        id = intent.getLongExtra(DBHelper.MAIN_ID, 1);

        fillData(id);
        //password equality confirmation
        passInp = pass.getText().toString();
        repPassInp = repPass.getText().toString();
        if (passInp.equals(repPassInp)) {
            lvVal.setText("Пароли совпадают");
            lvVal.setTextColor(Color.parseColor("#FF41FF11"));
        }
        else {
            lvVal.setText("Пароли не совпадают");
            lvVal.setTextColor(Color.parseColor("#ffff2f30"));
        }

        //checkbox
        chk = (CheckBox) findViewById(R.id.checkBoxEdit);
        chk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked){
                    retBld.setEnabled(false);
                    retStreet.setEnabled(false);
                    retAp.setEnabled(false);

                    str.setBackgroundColor(-7829368);
                    hs.setBackgroundColor(-7829368);
                    apL.setBackgroundColor(-7829368);
                }
                else
                {
                    retBld.setEnabled(true);
                    retStreet.setEnabled(true);
                    retAp.setEnabled(true);

                    str.setBackgroundColor(-1);
                    hs.setBackgroundColor(-1);
                    apL.setBackgroundColor(-1);
                }
            }
        });

        //after text change password equality confirmation
        repPass = (EditText) findViewById(R.id.repeatPassEdit);
        repPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                passInp = pass.getText().toString();
                repPassInp = repPass.getText().toString();
                if (passInp.equals(repPassInp)) {
                    lvVal.setText("Пароли совпадают");
                    lvVal.setTextColor(Color.parseColor("#FF41FF11"));
                }
                else {
                    lvVal.setText("Пароли не совпадают");
                    lvVal.setTextColor(Color.parseColor("#ffff2f30"));
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passInp = pass.getText().toString();
                repPassInp = repPass.getText().toString();
                if (passInp.equals(repPassInp)) {
                    lvVal.setText("Пароли совпадают");
                    lvVal.setTextColor(Color.parseColor("#FF41FF11"));
                }
                else {
                    lvVal.setText("Пароли не совпадают");
                    lvVal.setTextColor(Color.parseColor("#ffff2f30"));
                }
            }
        });



        save = (Button) findViewById(R.id.saveEditButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chk.isChecked())
                {
                    passInp = pass.getText().toString();
                    repPassInp = repPass.getText().toString();

                    if (passInp.equals(repPassInp)&&passInp!=null) {
                        phoneInp = phone.getText().toString();
                        mDBhelper.updatePhoneNumber(id, phoneInp);
                        nameInp = name.getText().toString();
                        mDBhelper.updateUserName(id, nameInp);
                        secnameInp = secName.getText().toString();
                        mDBhelper.updateUserSecondName(id, secnameInp);
                        surnameInp = surName.getText().toString();
                        mDBhelper.updateUserSurname(id, surnameInp);
                        accHouseInp = accHouse.getText().toString();
                        mDBhelper.updateAccBdAddress(id, accHouseInp);
                        accStreetInp = accStreet.getText().toString();
                        mDBhelper.updateAccStrAddress(id, accStreetInp);
                        accApInp = accAp.getText().toString();
                        mDBhelper.updateAccApAddress(id, accApInp);
                        retHouseIp = accHouseInp;
                        mDBhelper.updateRetBdAddress(id, retHouseIp);
                        retStreetIp = accStreetInp;
                        mDBhelper.updateRetStreetAddress(id, retStreetIp);
                        retApIp = accApInp;
                        mDBhelper.updateRetApAddress(id, retApIp);
                        emailInp = email.getText().toString();
                        mDBhelper.updateEmail(id, emailInp);
                       // mDBhelper.updatePassword(id, passInp);
                        getSupportLoaderManager().getLoader(0).forceLoad();

                        Intent intent = new Intent(getBaseContext(), MainScreen.class);
                        startActivity(intent);

                    }
                    else
                        Toast.makeText(getBaseContext(), "Пароль и его подтверждение не совпадают", Toast.LENGTH_LONG).show();
                }
                else {
                    passInp = pass.getText().toString();
                    repPassInp = repPass.getText().toString();

                    if (passInp.equals(repPassInp)&&passInp!=null) {
                        phoneInp = phone.getText().toString();
                        mDBhelper.updatePhoneNumber(id, phoneInp);
                        nameInp = name.getText().toString();
                        mDBhelper.updateUserName(id, nameInp);
                        secnameInp = secName.getText().toString();
                        mDBhelper.updateUserSecondName(id, secnameInp);
                        surnameInp = surName.getText().toString();
                        mDBhelper.updateUserSurname(id, surnameInp);
                        accHouseInp = accHouse.getText().toString();
                        mDBhelper.updateAccBdAddress(id, accHouseInp);
                        accStreetInp = accStreet.getText().toString();
                        mDBhelper.updateAccStrAddress(id, accStreetInp);
                        accApInp = accAp.getText().toString();
                        mDBhelper.updateAccApAddress(id, accApInp);
                        retHouseIp = retBld.getText().toString();
                        mDBhelper.updateRetBdAddress(id, retHouseIp);
                        retStreetIp = retStreet.getText().toString();
                        mDBhelper.updateRetStreetAddress(id, retStreetIp);
                        retApIp = retAp.getText().toString();
                        mDBhelper.updateRetApAddress(id, retApIp);
                        emailInp = email.getText().toString();
                        mDBhelper.updateEmail(id, emailInp);
                        //mDBhelper.updatePassword(id, passInp);
                        getSupportLoaderManager().getLoader(0).forceLoad();

                        Intent intent = new Intent(getBaseContext(), MainScreen.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getBaseContext(), "Пароль и его подтверждение не совпадают", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed()
    {

        EditProfile.super.onBackPressed();

    }

    public void fillData(long id)
    {
        Cursor userInfo = null;
        try {
                userInfo = mDBhelper.getUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //user login info
        email = (EditText) findViewById(R.id.emailEdit);
        pass = (EditText) findViewById(R.id.passwordEdit);
        repPass = (EditText) findViewById(R.id.repeatPassEdit);
        lvVal = (TextView) findViewById(R.id.liveValidationEdit);
        accHouse = (EditText) findViewById(R.id.accBldEdit);
        accStreet = (EditText) findViewById(R.id.accStreetEdit);
        accAp = (EditText) findViewById(R.id.accApEdit);
        retBld = (EditText) findViewById(R.id.retBldEdit);
        retStreet = (EditText) findViewById(R.id.retStrEdit);
        retAp = (EditText) findViewById(R.id.retApEdit);
        str = (LinearLayout) findViewById(R.id.strLayoutReturnEdit);
        hs = (LinearLayout) findViewById(R.id.hsLayoutReturnEdit);
        apL = (LinearLayout) findViewById(R.id.apLayoutReturnEdit);
        phone = (EditText) findViewById(R.id.phoneEdit);
        name = (EditText) findViewById(R.id.nameEdit);
        secName = (EditText) findViewById(R.id.secnameEdit);
        surName = (EditText) findViewById(R.id.surnameEdit);


        email.setText(userInfo.getString(userInfo.getColumnIndex(mDBhelper.EMAIL)));
        //pass.setText(userInfo.getString(userInfo.getColumnIndex(mDBhelper.PASSWORD)));
       // repPass.setText(userInfo.getString(userInfo.getColumnIndex(mDBhelper.PASSWORD)));

        //accept address edit text views

        accHouse.setText(userInfo.getString(userInfo.getColumnIndex(mDBhelper.ACCEPT_ADDRESS_BD)));
        accStreet.setText(userInfo.getString(userInfo.getColumnIndex(mDBhelper.ACCEPT_ADDRESS_STREET)));
        accAp.setText(userInfo.getString(userInfo.getColumnIndex(mDBhelper.ACCEPT_ADDRESS_AP)));
        //return address edit text views

        retBld.setText(userInfo.getString(userInfo.getColumnIndex(mDBhelper.RETURN_ADDRESS_BD)));
        retStreet.setText(userInfo.getString(userInfo.getColumnIndex(mDBhelper.RETURN_ADDRESS_STREET)));
        retAp.setText(userInfo.getString(userInfo.getColumnIndex(mDBhelper.RETURN_ADDRESS_AP)));

        phone.setText(userInfo.getString(userInfo.getColumnIndex(mDBhelper.PHONE_NUMBER)));
        name.setText(userInfo.getString(userInfo.getColumnIndex(mDBhelper.USER_NAME)));
        secName.setText(userInfo.getString(userInfo.getColumnIndex(mDBhelper.USER_SECOND_NAME)));
        surName.setText(userInfo.getString(userInfo.getColumnIndex(mDBhelper.USER_SURNAME)));
        userInfo.close();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_profile, menu);
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
}
