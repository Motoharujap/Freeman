package com.motoharu.cleaningapp.UserInterface;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.app.LoaderManager;
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

import com.motoharu.cleaningapp.Confirm_order;
import com.motoharu.cleaningapp.EditProfile;
import com.motoharu.cleaningapp.MainScreen;
import com.motoharu.cleaningapp.PasswordValidator;
import com.motoharu.cleaningapp.R;

import ObjectModel.User;
import interaction_with_webapp.UserFunctions;


@SuppressWarnings("ALL")
public class RegistrationActivity extends Activity implements LoaderManager.LoaderCallbacks {
    Button btnSave;
    CheckBox chk;
    EditText phone, accHouse, accStreet, accAp, retBld, retStreet, retAp, name, secName, surName, email, pass, repPass;
    TextView lvVal;
    LinearLayout str, hs, apL;
    SQLiteDatabase db;
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
    PasswordValidator passwordValidator;
    private static String register_tag = "register";

    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_UID = "uid";
    private static String KEY_PHONE = "phone";
    private static String KEY_EMAIL = "email";
    private static String KEY_NAME = "name";
    private static String KEY_SECNAME = "secname";
    private static String KEY_SURNAME = "surname";
    private static String KEY_ACCSTR = "accstr";
    private static String KEY_ACCBD = "accbd";
    private static String KEY_ACCAP = "accap";
    private static String KEY_RETSTR = "retstr";
    private static String KEY_RETBD = "retbd";
    private static String KEY_RETAP = "retap";

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        passwordValidator = new PasswordValidator();
        user = User.getInstance();
        //user login info
        email = (EditText) findViewById(R.id.emailEt);
        pass = (EditText) findViewById(R.id.passwordEt);
        repPass = (EditText) findViewById(R.id.repeatPassEt);
        lvVal = (TextView) findViewById(R.id.liveValidation);

        //accept address edit text views
        accHouse = (EditText) findViewById(R.id.accBldEt);
        accStreet = (EditText) findViewById(R.id.accStreetEt);
        accAp = (EditText) findViewById(R.id.accApEt);

        //return address edit text views
        retBld = (EditText) findViewById(R.id.retBldEt);
        retStreet = (EditText) findViewById(R.id.retStrEt);
        retAp = (EditText) findViewById(R.id.retApEt);


        str = (LinearLayout) findViewById(R.id.strLayoutReturn);
        hs = (LinearLayout) findViewById(R.id.hsLayoutReturn);
        apL = (LinearLayout) findViewById(R.id.apLayoutReturn);

        phone = (EditText) findViewById(R.id.phoneEt);
        name = (EditText) findViewById(R.id.nameEt);
        secName = (EditText) findViewById(R.id.secnameEt);
        surName = (EditText) findViewById(R.id.surnameEt);

        chk = (CheckBox) findViewById(R.id.checkBox);
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    retBld.setEnabled(false);
                    retStreet.setEnabled(false);
                    retAp.setEnabled(false);

                    str.setBackgroundColor(-7829368);
                    hs.setBackgroundColor(-7829368);
                    apL.setBackgroundColor(-7829368);
                } else {
                    retBld.setEnabled(true);
                    retStreet.setEnabled(true);
                    retAp.setEnabled(true);

                    str.setBackgroundColor(-1);
                    hs.setBackgroundColor(-1);
                    apL.setBackgroundColor(-1);
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                emailInp = email.getText().toString();
                Boolean val = passwordValidator.validateEmail(emailInp);
                if (val) {
                    email.setBackgroundColor(Color.parseColor("#5117FF1D"));
                }
            }
        });
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passInp = pass.getText().toString();
                Boolean val = passwordValidator.validatePassword(passInp);
                if (val) {
                    pass.setBackgroundColor(Color.parseColor("#5117FF1D"));
                } else {
                    pass.setBackgroundColor(Color.parseColor("#33FF1D20"));
                }
            }
        });

        repPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passInp = pass.getText().toString();
                passInp = pass.getText().toString();
                Boolean val = passwordValidator.validatePassword(passInp);
                if (val) {
                    pass.setBackgroundColor(Color.parseColor("#5117FF1D"));
                } else {
                    pass.requestFocus();
                    Toast.makeText(getBaseContext(), "Пароль должен быть не короче 6 символов", Toast.LENGTH_LONG).show();
                }
                repPassInp = repPass.getText().toString();
                if (passInp.equals(repPassInp) && !repPassInp.equals("")) {
                    repPass.setBackgroundColor(Color.parseColor("#5117FF1D"));
                    lvVal.setText("Пароли совпадают");
                    lvVal.setTextColor(Color.parseColor("#FF41FF11"));
                } else {
                    pass.setBackgroundColor(Color.parseColor("#33FF1D20"));
                    repPass.setBackgroundColor(Color.parseColor("#33FF1D20"));
                    lvVal.setText("Пароли не совпадают");
                    lvVal.setTextColor(Color.parseColor("#ffff2f30"));
                }
            }
        });
        btnSave = (Button) findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
                //registerViaWebApp();
            }
        });
    }

    public void register() {
        emailInp = email.getText().toString();
        Boolean val = passwordValidator.validateEmail(emailInp);
        phoneInp = phone.getText().toString();
        nameInp = name.getText().toString();
        secnameInp = secName.getText().toString();
        surnameInp = surName.getText().toString();
        accHouseInp = accHouse.getText().toString();
        accStreetInp = accStreet.getText().toString();
        accApInp = accAp.getText().toString();
        emailInp = email.getText().toString();
        passInp = pass.getText().toString();
        repPassInp = repPass.getText().toString();
        if (chk.isChecked()) {
            retHouseIp = accHouseInp;
            retStreetIp = accStreetInp;
            retApIp = accApInp;
        } else {
            retHouseIp = retBld.getText().toString();
            retStreetIp = retStreet.getText().toString();
            retApIp = retAp.getText().toString();
        }
        if (passInp.equals(repPassInp) && !passInp.equals("") && val) {
            user.createUser(emailInp, passInp, phoneInp, nameInp, secnameInp, surnameInp, accStreetInp, accHouseInp,
                    accApInp, retStreetIp, retHouseIp, retApIp, RegistrationActivity.this);
            //TODO remove shity code
            user.saveUserToDB();
            user.saveCurrentUserID();

            Intent intent = new Intent(RegistrationActivity.this, MainScreen.class);
            startActivity(intent);
            Intent newIntent = new Intent(RegistrationActivity.this, Confirm_order.class);
            Intent editProfileIntent = new Intent(RegistrationActivity.this, EditProfile.class);
        } else if (!passInp.equals(repPassInp)) {
            Toast.makeText(getBaseContext(), "Пароль и его подтверждение не совпадают", Toast.LENGTH_LONG).show();
        } else if (!val) {
            email.requestFocus();
            email.setBackgroundColor(Color.parseColor("#33FF1D20"));
            Toast.makeText(getBaseContext(), "Проверьте правильность введеного email-адреса", Toast.LENGTH_LONG).show();
        } else if (!passInp.equals(repPassInp)) {
            Toast.makeText(getBaseContext(), "Пароль и его подтверждение не совпадают", Toast.LENGTH_LONG).show();
        } else if (!val) {
            email.requestFocus();
            email.setBackgroundColor(Color.parseColor("#33FF1D20"));
            Toast.makeText(getBaseContext(), "Проверьте правильность введеного email-адреса", Toast.LENGTH_LONG).show();
        }
    }

    public void registerViaWebApp() {
        emailInp = email.getText().toString();
        passInp = pass.getText().toString();
        phoneInp = phone.getText().toString();
        nameInp = name.getText().toString();
        secnameInp = secName.getText().toString();
        surnameInp = surName.getText().toString();
        accHouseInp = accHouse.getText().toString();
        accStreetInp = accStreet.getText().toString();
        accApInp = accAp.getText().toString();
        retHouseIp = retBld.getText().toString();
        retStreetIp = retStreet.getText().toString();
        retApIp = retAp.getText().toString();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserFunctions uf = new UserFunctions();
                uf.registerUser(nameInp, emailInp, passInp, phoneInp, secnameInp, surnameInp, accHouseInp, accStreetInp, accApInp,
                        retHouseIp, retStreetIp, retApIp);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException i) {
            System.out.println(i);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            email.setText("motoharujap@gmail.com");
            pass.setText("terorist");
            repPass.setText("terorist");
            name.setText("sergey");
            chk.setChecked(true);
            accHouse.setText("2");
            accStreet.setText("Wall");
            accAp.setText("22");
            phone.setText("+793849384");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public android.content.Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this);
    }

    @Override
    public void onLoadFinished(android.content.Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(android.content.Loader loader) {

    }


}




