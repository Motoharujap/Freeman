package ObjectModel;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import Database.DBHelper;
import Database.DBOperationCreator;
import core.Freeman;

/**
 * Created by sergey on 26.03.15.
 */
public class User {
    private ArrayList<String> userInfo = new ArrayList<String>();
    private long _userId;
    private String _email;
    private String _password;
    private String _phoneNumber;
    private String _name;
    private String _surname;
    private String _lastname;
    private String _accStr;
    private String _accBd;
    private String _accAp;
    private String _retStr;
    private String _retBd;
    private String _retAp;
    private DBHelper _dbHelper;
    private Context _c;
    private static User instance;
    private Cursor _cursor;
    private Freeman app;

    private User(){
    }
    public static User getInstance(){
        if (instance == null){
            instance = new User();
        }
        return instance;
    }

    public void createUser(String email, String password, String phoneNumber, String name,
                           String surname, String lastname, String accStr, String accBd, String accAp,
                           String retStr, String retBd, String retAp, Context c){
        _email = email;
        _password = password;
        _phoneNumber = phoneNumber;
        _name = name;
        _surname = surname;
        _lastname = lastname;
        _accStr = accStr;
        _accBd = accBd;
        _accAp = accAp;
        _retStr = retStr;
        _retBd = retBd;
        _retAp = retAp;
        _c = c;
  }

    public void saveUserToDB(){
        _dbHelper = new DBHelper(_c);
        _userId = _dbHelper.createUser(_email, _password, _phoneNumber, _name, _surname, _lastname, _accStr, _accBd, _accAp, _retStr, _retBd, _retAp);
        _dbHelper.saveCurrentUserID(_userId);
    }

    public void instantiateUser(int userId){
        //acquiring apps context for DBHelper
        app = Freeman.getInstance();
        _c = app.getApplicationContext();

        DBOperationCreator dc = new DBOperationCreator();
        userInfo = dc.getUserInfo(userId, _c);
        _email = userInfo.get(0);
        _password = userInfo.get(1);
        _phoneNumber = userInfo.get(2);
        _name = userInfo.get(3);
        _surname = userInfo.get(4);
        _lastname = userInfo.get(5);
        _accStr = userInfo.get(6);
        _accBd = userInfo.get(7);
        _accAp = userInfo.get(8);
        _retStr = userInfo.get(9);
        _retBd = userInfo.get(10);
        _retAp = userInfo.get(11);
    }

    public long getuserId() {
        return _userId;
    }

    public String getemail() {
        return _email;
    }

    public void setemail(String _email) {

        this._email = _email;
        _dbHelper.updateEmail(_userId, _email);
    }

    public String getpassword() {
        return _password;
    }

    public void setpassword(String _password) {
        this._password = _password;
        _dbHelper.updatePassword(_userId, _password);
    }

    public String getphoneNumber() {
        return _phoneNumber;
    }

    public void setphoneNumber(String _phoneNumber) {
        _dbHelper = new DBHelper(_c);
        this._phoneNumber = _phoneNumber;
        _dbHelper.updatePhoneNumber(_userId, _phoneNumber);
    }

    public String getname() {
        return _name;
    }

    public void setname(String _name) {
        this._name = _name;
        _dbHelper.updateUserName(_userId, _name);
    }

    public String getsurname() {
        return _surname;
    }

    public void setsurname(String _surname) {
        this._surname = _surname;
        _dbHelper.updateUserSecondName(_userId, _surname);
    }

    public String getlastname() {
        return _lastname;
    }

    public void setlastname(String _lastname) {
        this._lastname = _lastname;
        _dbHelper.updateUserSecondName(_userId, _lastname);
    }

    public String getaccStr() {
        return _accStr;
    }

    public void setaccStr(String _accStr) {
        _dbHelper = new DBHelper(_c);
        this._accStr = _accStr;
        _dbHelper.updateAccStrAddress(_userId, _accStr);
    }

    public String getaccBd() {
        return _accBd;
    }

    public void setaccBd(String _accBd) {
        _dbHelper = new DBHelper(_c);
        this._accBd = _accBd;
        _dbHelper.updateAccBdAddress(_userId, _accBd);
    }

    public String getaccAp() {
        return _accAp;
    }

    public void setaccAp(String _accAp) {
        _dbHelper = new DBHelper(_c);
        this._accAp = _accAp;
        _dbHelper.updateAccApAddress(_userId, _accAp);
    }

    public String getretStr() {
        return _retStr;
    }

    public void setretStr(String _retStr) {
        _dbHelper = new DBHelper(_c);
        this._retStr = _retStr;
        _dbHelper.updateRetStreetAddress(_userId, _retStr);
    }

    public String getretBd() {
        return _retBd;
    }

    public void setretBd(String _retBd) {
        _dbHelper = new DBHelper(_c);
        this._retBd = _retBd;
        _dbHelper.updateRetBdAddress(_userId, _retBd);
    }

    public String getretAp() {
        return _retAp;
    }

    public void setretAp(String _retAp) {
        _dbHelper = new DBHelper(_c);
        this._retAp = _retAp;
        _dbHelper.updateRetApAddress(_userId, _retAp);
    }
}
