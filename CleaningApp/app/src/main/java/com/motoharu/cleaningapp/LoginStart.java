package com.motoharu.cleaningapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.motoharu.cleaningapp.UserInterface.CoreActivity;
import com.motoharu.cleaningapp.UserInterface.RegistrationActivity;

import java.util.HashMap;
import java.util.Map;

import Database.DBHelper;
import login_passwords_and_blackjack.SaveSharedPreferences;


public class LoginStart extends CoreActivity implements View.OnClickListener {
    EditText login, pass;
    DBHelper mDBhelper;
    int loginButtonCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_start);
        //connecting to the DB
        mDBhelper = new DBHelper(this);
        if (SaveSharedPreferences.getUserName(LoginStart.this).length()==0){

        }
        else{
            Cursor cursor = mDBhelper.getAllUsers();
            while(cursor.moveToNext()){
                String login = new String(cursor.getString(cursor.getColumnIndex(DBHelper.EMAIL)));
                String prefLogin = SaveSharedPreferences.getUserName(LoginStart.this);
                if (login.equals(prefLogin)){
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.MAIN_ID));
                    Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                    intent.putExtra(DBHelper.MAIN_ID, id);
                    startActivity(intent);
                }
                else{

                }
            }
        }
        //creating layouts, setting them invisible
        login = (EditText) findViewById(R.id.emailLogin);
        login.setVisibility(View.GONE);
        pass = (EditText) findViewById(R.id.passLogin);
        pass.setVisibility(View.GONE);
        Button btnReg = (Button) findViewById(R.id.regButton);
        Button btnLgn = (Button) findViewById(R.id.login_button);
        btnReg.setOnClickListener(this);
        btnLgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginButtonCount == 0) {
                    Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_fade_in);
                    animFadeIn.setDuration(800);
                    login.setVisibility(View.VISIBLE);
                    login.startAnimation(animFadeIn);
                    pass.setVisibility(View.VISIBLE);
                    pass.startAnimation(animFadeIn);
                    loginButtonCount = 1;
                } else if (loginButtonCount == 1) {
                    Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_fade_out);
                    animFadeOut.setDuration(700);
                    login.startAnimation(animFadeOut);
                    pass.startAnimation(animFadeOut);
                    login.setVisibility(View.GONE);
                    pass.setVisibility(View.GONE);
                    if (login.getText().toString().equals("") || pass.getText().toString().equals("")) {
                        loginButtonCount = 0;
                    } else if (login.getText().toString() != null && pass.getText().toString() != null) {
                        loginButtonCount = 0;
                        SaveSharedPreferences.setUserName(getApplicationContext(), login.getText().toString());
                        checkUser();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_start, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            login.setText("motoharujap@gmail.com");
            pass.setText("terorist");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void checkUser()
    {
        Cursor mCursor = mDBhelper.getAllUsers();
        String enteredEmail = new String(login.getText().toString());
        String enteredPass = new String(pass.getText().toString());

        while (mCursor.moveToNext()) {
            String testEmail = new String(mCursor.getString(mCursor.getColumnIndex(DBHelper.EMAIL)));
            String testPass = new String(mCursor.getString(mCursor.getColumnIndex(DBHelper.PASSWORD)));
            if (enteredEmail.equals(testEmail)) {
                if (enteredEmail.equals(testEmail) && enteredPass.equals(testPass)) {
                    mDBhelper.saveCurrentUserID(mCursor.getLong(mCursor.getColumnIndex(mDBhelper.MAIN_ID)));
                    long id = mDBhelper.getCurrentUserID();
                    Intent intent = new Intent(getBaseContext(), MainScreen.class);
                    intent.putExtra(mDBhelper.MAIN_ID, id);
                    startActivity(intent);
                } else if (enteredEmail.equals(testEmail) && !enteredPass.equals(testPass))
                    Toast.makeText(this, "Неверный email или пароль", Toast.LENGTH_LONG).show();
                else if (!enteredEmail.equals(testEmail) && enteredPass.equals(testPass))
                    Toast.makeText(this, "Неверный email или пароль", Toast.LENGTH_LONG).show();
            }
        }
        mCursor.close();
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void requestWithHeadersTest(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.somewebsite.com";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                //TODO add host
                params.put("Host", "");
                params.put("User-Agent", "Mobile Freeman");
                params.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                params.put("Authorization", getUserNameAndPass(login.getText().toString(), pass.getText().toString()));
                params.put("Accept-Language", "ru,en-us;q=0.7,en;q=0.3");
                params.put("Accept-charset", "windows-1251,utf-8;q=0.7,*;q=0.7");
                params.put("Keep-Alive", "300");
                params.put("Connection", "close");
                return params;
            }
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.getDeviceId();
                params.put("Phone ID", telephonyManager.getDeviceId());
                return params;
            }

        };
        queue.add(postRequest);

    }
    public String getUserNameAndPass(String login, String password){
        String source = login + ":" + password;
        String ret="Basic "+ Base64.encodeToString(source.getBytes(), Base64.DEFAULT);
        return ret;
    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder adb = new AlertDialog.Builder(LoginStart.this);
        adb.setTitle("Выход");
        adb.setMessage("Вы действительно хотите выйти?");
        adb.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        adb.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adb.show();
        return;
    }
}
