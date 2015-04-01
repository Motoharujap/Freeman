package interaction_with_webapp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import Database.DBHelper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Motoharu on 18.10.2014.
 */
public class UserFunctions {
    private JSONParser jsonParser;
    private static String loginURL = "http://localhost/android_login_api";
    private static String registerURL = "http://10.0.2.2/android_login_api/index.php";

    private static String login_tag = "login";
    private static String register_tag = "register";

    DBHelper mDBhelper;

    public UserFunctions(){
        jsonParser = new JSONParser();
    }
    public JSONObject loginUser(String email, String password)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        Log.e("JSON", json.toString());
        return json;
    }


    public JSONObject registerUser2(String email, String password, String phoneNumber, String userName, String userSecName, String userSurName, String accStr, String accBd, String accAp,
                                   String retStr, String retBd, String retAp){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        Log.e("JSON", json.toString());
        return json;
    }

    public JSONObject registerUser(String name, String email, String password, String phoneNumber, String uSecName, String uSurName,
                                   String accBd, String accStr, String accAp, String retBd, String retStr, String retAp){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("phoneNumber", phoneNumber));
        params.add(new BasicNameValuePair("uSecName", uSecName));
        params.add(new BasicNameValuePair("uSurName", uSurName));
        params.add(new BasicNameValuePair("accBd", accBd));
        params.add(new BasicNameValuePair("accStr", accStr));
        params.add(new BasicNameValuePair("accAp", accAp));
        params.add(new BasicNameValuePair("retBd", retBd));
        params.add(new BasicNameValuePair("retStr", retStr));
        params.add(new BasicNameValuePair("retAp", retAp));
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        System.out.print(json);
        Log.e("JSON", json.toString());
        return json;
    }

    //test registerUser method using AsyncHttpClient
    public void regUser(Context context, String tag, String name, String email, String password){
        mDBhelper = new DBHelper(context);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("password", password);
        map.put("email", email);
        map.put("tag", tag);
        map.put("name", name);
        Gson gson = new GsonBuilder().create();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tag", gson.toJson("register"));
        params.put("name", gson.toJson(name));
        params.put("email", gson.toJson(email));
        params.put("password", gson.toJson(password));
        System.out.print(params);
        client.post("http://10.0.2.2/android_login_api/index.php", params, new AsyncHttpResponseHandler(){
           @Override
           public void onSuccess(String response)
           {
                    System.out.print("Success");
           }
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                    System.out.println("Failure");
            }
        });
    }


    public boolean isUserLoggedIn(Context context)
    {
        DBHelper mDBhelper = new DBHelper(context);
        int count = mDBhelper.getRowCount();
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean logoutUser(Context context){
        DBHelper mDBhelper = new DBHelper(context);
        mDBhelper.resetTables();

        return true;
    }


}
