package ObjectModel;

import android.content.Context;

import Database.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sergey on 23.03.15.
 */
public class Order {
    private long _userId;
    private int _shirtsq;
    private String _summ;
    private String _dateStamp;
    private String _status;
    private String _phoneNumber;
    private String _accStr;
    private String _accBd;
    private String _accAp;
    private String _retStr;
    private String _retBd;
    private String _retAp;
    private DBHelper _dbHelper;
    private Context _c;

    public Order(long userId, int shirtsq, String summ, String dateStamp,
                 String status, String phoneNumber, String accStr, String accBd,
                 String accAp, String retStr, String retBd, String retAp, Context c) {
        _userId = userId;
        _shirtsq = shirtsq;
        _summ = summ;
        _dateStamp = dateStamp;
        _status = status;
        _phoneNumber = phoneNumber;
        _accStr = accStr;
        _accBd = accBd;
        _accAp = accAp;
        _retStr = retStr;
        _retBd = retBd;
        _retAp = retAp;
        _c = c;
    }

    public void saveToDB(){
        _dbHelper = new DBHelper(_c);
        Date date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd");
        _dbHelper.createOrder(_userId, _shirtsq, _summ, dt.format(date), "Заказ не создан");
    }
}
