package Database;

import android.content.Context;
import android.database.Cursor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import core.Freeman;
import data.FeedItem;
import data.OrderStatusItem;

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



    public final int K_KEY_ID = 101;
    public final int K_EMAIL = 102;
    public final int K_PASSWORD = 103;
    public final int K_PHONE_NUMBER = 104;
    public final int K_ACCEPT_ADDRESS_STREET = 108;
    public final int K_ACCEPT_ADDRESS_BD = 109;
    public final int K_ACCEPT_ADDRESS_AP = 110;
    public final int K_RETURN_ADDRESS_STREET = 111;
    public final int K_RETURN_ADDRESS_BD = 112;
    public final int K_RETURN_ADDRESS_AP = 113;
    public final int K_SHIRTS_Q = 114;
    public final int K_SUMM = 115;
    public final int K_STATUS = 116;
    public final int K_DATE_STAMP = 117;

    public ArrayList<String> getUserInfo(int userID, Context context){
        ArrayList<String> userInfo = new ArrayList<String>();
        DBHelper mDbhelper = new DBHelper(context);
        Cursor c = null;
        try {
            c = mDbhelper.getUser(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (c.moveToFirst()){
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.EMAIL)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.PASSWORD)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.PHONE_NUMBER)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.USER_NAME)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.USER_SECOND_NAME)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.USER_SURNAME)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.ACCEPT_ADDRESS_STREET)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.ACCEPT_ADDRESS_BD)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.ACCEPT_ADDRESS_AP)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.RETURN_ADDRESS_STREET)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.RETURN_ADDRESS_BD)));
            userInfo.add(c.getString(c.getColumnIndex(DBHelper.RETURN_ADDRESS_AP)));

        }
        return userInfo;
    }

    public HashMap<String, String> getCurrentOrderInfo(int id, Context context){
        HashMap<String, String> map = new HashMap<String, String>();
        DBHelper mDbHelper = new DBHelper(context);
        Cursor c = mDbHelper.getCurrentOrderInfo(id);

        if (c.moveToFirst()){
            //map.put(KEY_ID, String.valueOf(c.getInt(c.getColumnIndex(DBHelper.KEY_CORDER_ID))));
            map.put(PHONE_NUMBER, c.getString(c.getColumnIndex(DBHelper.CO_PHONE_NUMBER)));
            map.put(ACCEPT_ADDRESS_STREET, c.getString(c.getColumnIndex(DBHelper.CO_ACCEPT_ADDRESS_STREET)));
            map.put(ACCEPT_ADDRESS_BD, c.getString(c.getColumnIndex(DBHelper.CO_ACCEPT_ADDRESS_BD)));
            map.put(ACCEPT_ADDRESS_AP, c.getString(c.getColumnIndex(DBHelper.CO_ACCEPT_ADDRESS_AP)));
            map.put(RETURN_ADDRESS_STREET, c.getString(c.getColumnIndex(DBHelper.CO_RETURN_ADDRESS_STREET)));
            map.put(RETURN_ADDRESS_BD, c.getString(c.getColumnIndex(DBHelper.CO_RETURN_ADDRESS_BD)));
            map.put(RETURN_ADDRESS_AP, c.getString(c.getColumnIndex(DBHelper.CO_RETURN_ADDRESS_AP)));
        }

        return map;
    }

    public ArrayList<OrderStatusItem> getItems(long userId, Context context){
        DBHelper mDbhelper = new DBHelper(context);
        Cursor c = mDbhelper.getAllOrders(userId);
        ArrayList<OrderStatusItem> orderStatusItems = new ArrayList<OrderStatusItem>();
        do {
            OrderStatusItem si = new OrderStatusItem();
            si.setShirtsQ(c.getString(c.getColumnIndexOrThrow(DBHelper.SHIRTS_Q)));
            si.setSumm(c.getString(c.getColumnIndexOrThrow(DBHelper.SUMM)));
            si.setStatus(c.getString(c.getColumnIndexOrThrow(DBHelper.STATUS)));
            si.setTimeStamp(c.getString(c.getColumnIndexOrThrow(DBHelper.DATE_STAMP)));
            si.setId((int) c.getLong(c.getColumnIndex(DBHelper.KEY_CORDER_ID)));
            orderStatusItems.add(si);
        } while (c.moveToNext());
        return orderStatusItems;
    }

    public String getCurrentOrderInfo(int infoID, int orderid, Context context){
        DBHelper dbhelper = new DBHelper(context);
        Cursor cursor = dbhelper.getCurrentOrderInfo(orderid);
        String result = "";
        if (cursor.moveToFirst()) {
            switch (infoID) {
                case K_PHONE_NUMBER: {
                    result = cursor.getString(cursor.getColumnIndex(DBHelper.CO_PHONE_NUMBER));
                }
                case K_ACCEPT_ADDRESS_STREET: {
                    result = cursor.getString(cursor.getColumnIndex(DBHelper.CO_ACCEPT_ADDRESS_STREET));
                }
                case K_ACCEPT_ADDRESS_BD: {
                    result = cursor.getString(cursor.getColumnIndex(DBHelper.CO_ACCEPT_ADDRESS_BD));
                }
                case K_ACCEPT_ADDRESS_AP:{
                    result = cursor.getString(cursor.getColumnIndex(DBHelper.CO_ACCEPT_ADDRESS_AP));
                }
                case K_RETURN_ADDRESS_STREET:{
                    result = cursor.getString(cursor.getColumnIndex(DBHelper.CO_RETURN_ADDRESS_STREET));
                }
                case K_RETURN_ADDRESS_BD:{
                    result = cursor.getString(cursor.getColumnIndex(DBHelper.CO_RETURN_ADDRESS_BD));
                }
                case K_RETURN_ADDRESS_AP:{
                    result = cursor.getString(cursor.getColumnIndex(DBHelper.CO_RETURN_ADDRESS_AP));
                }
                case K_STATUS:{
                    result = cursor.getString(cursor.getColumnIndex(DBHelper.STATUS));
                }
                case K_SHIRTS_Q:{
                    result = cursor.getString(cursor.getColumnIndex(DBHelper.SHIRTS_Q));
                }
                case K_DATE_STAMP:{
                    result = cursor.getString(cursor.getColumnIndex(DBHelper.DATE_STAMP));
                }
                case K_SUMM:{
                    result = cursor.getString(cursor.getColumnIndex(DBHelper.SUMM));
                }
            }
        }
        return result;
    }


}
