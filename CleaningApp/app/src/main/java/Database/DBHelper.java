package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Motoharu on 02.10.2014.
 */
public class DBHelper extends SQLiteOpenHelper {
    //Database information
    public static final String DB_NAME = "CLEANING.DB";
    public static final int DB_VERSION = 1;

    //main id
    public static final String MAIN_ID = "_id";

    //user info table
    public static final String TABLE_USER = "user";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String USER_NAME = "uName";
    public static final String USER_SECOND_NAME = "uSecName";
    public static final String USER_SURNAME = "uSurname";
    public static final String ACCEPT_ADDRESS_STREET = "acceptStreet";
    public static final String ACCEPT_ADDRESS_BD = "acceptBld";
    public static final String ACCEPT_ADDRESS_AP = "acceptAp";
    public static final String RETURN_ADDRESS_STREET = "returnStreet";
    public static final String RETURN_ADDRESS_BD = "returnBld";
    public static final String RETURN_ADDRESS_AP = "returnAp";

    //current user info table
    public static final String TABLE_CURRENT_USER = "current_user";
    public static final String CURRENT_USER_ID = "userID";

    //current oreder info table
    public static final String TABLE_CURRENT_ORDER = "current_order";
    public static final String KEY_CORDER_ID = "order_id";
    public static final String SHIRTS_Q = "shirtsq";
    public static final String SUMM = "summ";
    public static final String STATUS = "status";
    public static final String DATE_STAMP = "date";
    public static final String USER_UNIQUE_ID = "userIdUnique";
    public static final String CO_PHONE_NUMBER = "co_phonenumber";
    public static final String CO_ACCEPT_ADDRESS_STREET = "co_acceptStreet";
    public static final String CO_ACCEPT_ADDRESS_BD = "co_acceptBld";
    public static final String CO_ACCEPT_ADDRESS_AP = "co_acceptAp";
    public static final String CO_RETURN_ADDRESS_STREET = "co_returnStreet";
    public static final String CO_RETURN_ADDRESS_BD = "co_returnBld";
    public static final String CO_RETURN_ADDRESS_AP = "co_returnAp";

    //table creation statement
    public static final String CREATE_TABLE_USER = "create table "
            + TABLE_USER + "("
            + MAIN_ID + " integer primary key autoincrement, "
            + EMAIL + " text, "
            + PASSWORD + " text, "
            + PHONE_NUMBER + " text, "
            + USER_NAME + " text, "
            + USER_SECOND_NAME + " text, "
            + USER_SURNAME + " text, "
            + ACCEPT_ADDRESS_STREET + " text, "
            + ACCEPT_ADDRESS_BD + " text, "
            + ACCEPT_ADDRESS_AP + " text, "
            + RETURN_ADDRESS_STREET + " text, "
            + RETURN_ADDRESS_BD + " text, "
            + RETURN_ADDRESS_AP + " text);";

    public static final String CREATE_TABLE_CURRENT_USER = "create table "
            + TABLE_CURRENT_USER + "("
            + CURRENT_USER_ID + " integer);";

    public static final String CREATE_TABLE_CURRENT_ORDER = "create table "
            + TABLE_CURRENT_ORDER + "("
            + KEY_CORDER_ID + " integer primary key autoincrement, "
            + SHIRTS_Q + " integer, "
            + SUMM + " text, "
            + STATUS + " text, "
            + DATE_STAMP + " text, "
            + USER_UNIQUE_ID + " integer, "
            + CO_PHONE_NUMBER + " text, "
            + CO_ACCEPT_ADDRESS_STREET + " text, "
            + CO_ACCEPT_ADDRESS_BD + " text, "
            + CO_ACCEPT_ADDRESS_AP + " text, "
            + CO_RETURN_ADDRESS_STREET + " text, "
            + CO_RETURN_ADDRESS_BD + " text, "
            + CO_RETURN_ADDRESS_AP + " text);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_CURRENT_USER);
            db.execSQL(CREATE_TABLE_CURRENT_ORDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT_ORDER);
            onCreate(db);

    }


    //CRUD operations
    //table order crud
    public boolean areUsersExist(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    //current order CRUD
    public void writeCurrentOrder(long userId, int shirtsq, String summ, String dateStamp, String status, String phoneNumber, String accStr, String accBd, String accAp,
                                  String retStr, String retBd, String retAp){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_UNIQUE_ID, userId);
        cv.put(SHIRTS_Q, shirtsq);
        cv.put(SUMM, summ);
        cv.put(DATE_STAMP, dateStamp);
        cv.put(STATUS, status);
        cv.put(CO_PHONE_NUMBER, phoneNumber);
        cv.put(CO_ACCEPT_ADDRESS_STREET, accStr);
        cv.put(CO_ACCEPT_ADDRESS_BD, accBd);
        cv.put(CO_ACCEPT_ADDRESS_AP, accAp);
        cv.put(CO_RETURN_ADDRESS_STREET, retStr);
        cv.put(CO_RETURN_ADDRESS_BD, retBd);
        cv.put(CO_RETURN_ADDRESS_AP, retAp);
        db.insert(TABLE_CURRENT_ORDER, null, cv);
    }

    public Cursor getCurrentOrderInfo(int id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(true, TABLE_CURRENT_ORDER, new String[]{KEY_CORDER_ID, USER_UNIQUE_ID, SHIRTS_Q, SUMM, DATE_STAMP, STATUS, CO_PHONE_NUMBER, CO_ACCEPT_ADDRESS_STREET,
                CO_ACCEPT_ADDRESS_BD, CO_ACCEPT_ADDRESS_AP, CO_RETURN_ADDRESS_STREET, CO_RETURN_ADDRESS_BD, CO_RETURN_ADDRESS_AP},
                KEY_CORDER_ID + " = " + id, null, null, null, null, null);
        if (c != null){
            c.moveToFirst();
        }
         return c;
    }

    public Cursor getAllOrders(long id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_CURRENT_ORDER + " where " + USER_UNIQUE_ID + " = " + id, null);
        if (c != null){
            c.moveToFirst();
        }
         return c;
    }

    //Delete order
    public void deleteOrdersById(ArrayList<Integer> idList){
        SQLiteDatabase database = getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_CURRENT_ORDER + " WHERE " + KEY_CORDER_ID + " = ? ";
        for (int i = 0; i < idList.size(); i++){
            database.execSQL(deleteQuery, new Object[]{idList.get(i)});
        }
    }

    public void insertUser(HashMap<String, String> queryValues){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EMAIL, queryValues.get("email"));
        cv.put(USER_NAME, queryValues.get("user_name"));
        cv.put(PASSWORD, queryValues.get("password"));
        db.insert(TABLE_USER, null, cv);
        db.close();
    }

    public long getCurrentUserID(){
        SQLiteDatabase db = this.getWritableDatabase();
        long userID = 0;
        String query = "SELECT " + CURRENT_USER_ID + " FROM " + TABLE_CURRENT_USER;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()){
            userID = c.getLong(c.getColumnIndex(MAIN_ID));
        }
        return userID;
    }

    public void saveCurrentUserID(long userID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MAIN_ID, userID);
        db.insert(TABLE_CURRENT_USER, null, cv);
    }

    public ArrayList<HashMap<String, String>> getAllUsers1() {
        ArrayList<HashMap<String, String>> usersList;
        usersList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", cursor.getString(0));
                map.put("userName", cursor.getString(1));
                usersList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        cursor.close();
        return usersList;
    }
    public long createUser(String email, String password, String phoneNumber, String userName, String userSecName, String userSurName, String accStr, String accBd, String accAp,
                           String retStr, String retBd, String retAp)
    {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PHONE_NUMBER, phoneNumber);
        cv.put(EMAIL, email);
        cv.put(PASSWORD, password);
        cv.put(USER_NAME, userName);
        cv.put(USER_SECOND_NAME, userSecName);
        cv.put(USER_SURNAME, userSurName);
        cv.put(ACCEPT_ADDRESS_STREET, accStr);
        cv.put(ACCEPT_ADDRESS_BD, accBd);
        cv.put(ACCEPT_ADDRESS_AP, accAp);
        cv.put(RETURN_ADDRESS_STREET, retStr);
        cv.put(RETURN_ADDRESS_BD, retBd);
        cv.put(RETURN_ADDRESS_AP, retAp);
        long rowID = db.insert(TABLE_USER, null, cv);
        return rowID;

    }

    public Cursor getUser(long rowId) throws SQLException {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.query(true, TABLE_USER, new String[]{MAIN_ID, PHONE_NUMBER, EMAIL, PASSWORD, USER_NAME, USER_SECOND_NAME, USER_SURNAME, ACCEPT_ADDRESS_STREET,
                        ACCEPT_ADDRESS_BD, ACCEPT_ADDRESS_AP, RETURN_ADDRESS_STREET, RETURN_ADDRESS_BD, RETURN_ADDRESS_AP}, MAIN_ID + " = " + rowId,
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void deleteEverything(long rowId)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USER, DBHelper.MAIN_ID + "=" + rowId, null);
    }

    public boolean updatePhoneNumber(long userId, String phone)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put(PHONE_NUMBER, phone);
        return db.update(TABLE_USER, updateValues, MAIN_ID + "=" + userId, null) > 0;
    }

    public boolean updateUserName(long rowId, String userName)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues uv = new ContentValues();
        uv.put(USER_NAME, userName);
        return db.update(TABLE_USER, uv, MAIN_ID + " = " + rowId, null) > 0;
    }

    public boolean updateUserSecondName(long rowId, String usSecName)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues uv = new ContentValues();
        uv.put(USER_SECOND_NAME, usSecName);
        return db.update(TABLE_USER, uv, MAIN_ID + " = " + rowId, null) > 0;
    }

    public boolean updateUserSurname(long rowid, String usSurName)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues uv = new ContentValues();
        uv.put(USER_SURNAME, usSurName);
        return db.update(TABLE_USER, uv, MAIN_ID + " = " + rowid, null) > 0;
    }

    public boolean updateAccBdAddress(long rowId, String accBd)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues uv = new ContentValues();
        uv.put(ACCEPT_ADDRESS_BD, accBd);
        return db.update(TABLE_USER, uv, MAIN_ID + " = " + rowId, null) > 0;
    }

    public boolean updateAccStrAddress(long rowId, String accStr)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues uv = new ContentValues();
        uv.put(ACCEPT_ADDRESS_STREET, accStr);
        return db.update(TABLE_USER, uv, MAIN_ID + " = " + rowId, null) > 0;
    }

    public boolean updateAccApAddress(long rowId, String accAp)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues uv = new ContentValues();
        uv.put(ACCEPT_ADDRESS_AP, accAp);
        return db.update(TABLE_USER, uv, MAIN_ID + " = " + rowId, null) > 0;
    }

    public boolean updateRetBdAddress(long rowId, String reBd)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues uv = new ContentValues();
        uv.put(RETURN_ADDRESS_BD, reBd);
        return db.update(TABLE_USER, uv, MAIN_ID + " = " + rowId, null) > 0;
    }

    public boolean updateRetStreetAddress(long rowId, String retStr)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues uv = new ContentValues();
        uv.put(RETURN_ADDRESS_STREET, retStr);
        return db.update(TABLE_USER, uv, MAIN_ID + " = " + rowId, null) > 0;
    }

    public boolean updateRetApAddress(long rowId, String retAp)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues uv = new ContentValues();
        uv.put(RETURN_ADDRESS_AP, retAp);
        return db.update(TABLE_USER, uv, MAIN_ID + " = " + rowId, null) > 0;
    }


    public boolean updateEmail(long id, String email)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put(EMAIL, email);
        return db.update(TABLE_USER, updateValues, MAIN_ID + "=" + id, null) > 0;
    }

    public boolean updatePassword(long id, String password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues uv = new ContentValues();
        uv.put(PASSWORD, password);

        return db.update(TABLE_USER, uv, MAIN_ID + "=" + id, null) > 0;
    }
    public String getUserInfo() {
        String text = "";
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT uName FROM user";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor!=null && cursor.getCount()>0)
        {
            cursor.moveToFirst();
            do {
              text = cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME));
            } while (cursor.moveToNext());

        }

    return text;
}
    public Cursor getAllUsers()
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_USER, null, null, null, null, null, null);
    }
    //TODO delete
    public int getRowCount()
    {
        String countQuery = "SELECT *FROM " + TABLE_USER;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        cursor.close();
        return rowCount;
    }
    //TODO delete
    public void resetTables(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }
    //TODO delete
    public String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  *FROM " + TABLE_USER;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", cursor.getString(0));
                map.put("userName", cursor.getString(1));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }

    @Override
    public void close()
    {
        this.close();
    }
}
