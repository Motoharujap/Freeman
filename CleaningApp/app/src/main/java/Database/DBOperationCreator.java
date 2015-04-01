package Database;

import android.content.Context;
import android.database.Cursor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import core.Freeman;

/**
 * Created by sergey on 31.03.15.
 */
public class DBOperationCreator {
    public final String KEY_ID = "id";
    public final String EMAIL = "email";
    public final String PASSWORD = "password";
    public final String PHONE_NUMBER = "phone_number";
    public final String USER_NAME = "uName";
    public final String USER_SECOND_NAME = "uSecName";
    public final String USER_SURNAME = "uSurname";
    public final String ACCEPT_ADDRESS_STREET = "acceptStreet";
    public final String ACCEPT_ADDRESS_BD = "acceptBld";
    public final String ACCEPT_ADDRESS_AP = "acceptAp";
    public final String RETURN_ADDRESS_STREET = "returnStreet";
    public final String RETURN_ADDRESS_BD = "returnBld";
    public final String RETURN_ADDRESS_AP = "returnAp";

    public ArrayList<String> getUserInfo(int userID, Context context){
        ArrayList<String> userInfo = new ArrayList<String>();
        DBHelper mDbhelper = new DBHelper(context);
        Cursor c = null;
        try {
            if (c.moveToFirst()) {
                c = mDbhelper.getUser(userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (c != null){
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.EMAIL)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.PASSWORD)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.PHONE_NUMBER)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.USER_NAME)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.USER_SECOND_NAME)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.USER_SURNAME)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.ACCEPT_ADDRESS_STREET)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.ACCEPT_ADDRESS_BD)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.RETURN_ADDRESS_STREET)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.RETURN_ADDRESS_BD)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.RETURN_ADDRESS_AP)));

        }
        return userInfo;
    }

    public HashMap<String, String> getCurrentOrderInfo(int id, Context context){
        HashMap<String, String> map = new HashMap<String, String>();
        DBHelper mDbHelper = new DBHelper(context);
        Cursor c = null;
        c = mDbHelper.getCurrentOrderInfo(id);
        if (c.moveToFirst()){
            map.put(KEY_ID, String.valueOf(c.getInt(c.getColumnIndex(DBHelper.KEY_CORDER_ID))));
            map.put(PHONE_NUMBER, c.getString(c.getColumnIndex(DBHelper.CO_PHONE_NUMBER)));
            map.put(ACCEPT_ADDRESS_STREET, c.getString(c.getColumnIndex(DBHelper.CO_ACCEPT_ADDRESS_STREET)));
        }

        return map;
    }
}
