package ObjectModel;

import android.content.Context;

import Database.DBHelper;
import Database.DBOperationCreator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by sergey on 23.03.15.
 */
public class Order {
    private long userId;
    private int shirtsq;
    private String summ;
    private String dateStamp;
    private String status;
    private String phoneNumber;
    private String accStr;
    private String accBd;
    private String accAp;
    private String retStr;
    private String retBd;
    private String retAp;
    private DBHelper dbHelper;

    public final String PHONE_NUMBER = "phone_number";
    public final String ACCEPT_ADDRESS_STREET = "acceptStreet";
    public final String ACCEPT_ADDRESS_BD = "acceptBld";
    public final String ACCEPT_ADDRESS_AP = "acceptAp";
    public final String RETURN_ADDRESS_STREET = "returnStreet";
    public final String RETURN_ADDRESS_BD = "returnBld";
    public final String RETURN_ADDRESS_AP = "returnAp";

    public Order() {
    }

    public void saveToDB(Context c){
        dbHelper = new DBHelper(c);
        Date date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd");
        dbHelper.createOrder(userId, shirtsq, summ, dt.format(date), "Заказ не создан");
    }

    public void initializeCurrentOrder(int id, Context c){
        DBOperationCreator dbc = new DBOperationCreator();
        HashMap<String, String> orderInfo = dbc.getCurrentOrderInfo(id, c);
        this.phoneNumber = orderInfo.get(PHONE_NUMBER);
        this.accStr = orderInfo.get(ACCEPT_ADDRESS_STREET);
        this.accBd = orderInfo.get(ACCEPT_ADDRESS_BD);
        this.accAp = orderInfo.get(ACCEPT_ADDRESS_AP);
        this.retStr = orderInfo.get(RETURN_ADDRESS_STREET);
        this.retBd = orderInfo.get(RETURN_ADDRESS_BD);
        this.retAp = orderInfo.get(RETURN_ADDRESS_AP);
    }

    public long getUserId() {
        return userId;
    }

    public int getShirtsq() {
        return shirtsq;
    }

    public void setShirtsq(int shirtsq) {
        this.shirtsq = shirtsq;
    }

    public String getSumm() {
        return summ;
    }

    public void setSumm(String summ) {
        this.summ = summ;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccStr() {
        return accStr;
    }

    public void setAccStr(String accStr) {
        this.accStr = accStr;
    }

    public String getAccBd() {
        return accBd;
    }

    public void setAccBd(String accBd) {
        this.accBd = accBd;
    }

    public String getAccAp() {
        return accAp;
    }

    public void setAccAp(String accAp) {
        this.accAp = accAp;
    }

    public String getRetStr() {
        return retStr;
    }

    public void setRetStr(String retStr) {
        this.retStr = retStr;
    }

    public String getRetBd() {
        return retBd;
    }

    public void setRetBd(String retBd) {
        this.retBd = retBd;
    }

    public String getRetAp() {
        return retAp;
    }

    public void setRetAp(String retAp) {
        this.retAp = retAp;
    }

}
