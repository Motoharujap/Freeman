package login_passwords_and_blackjack;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Motoharu on 31.10.2014.
 */
public class SaveSharedPreferences {

    static final String USER_NAME = "userName";
    static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static void setUserName(Context context, String name){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(USER_NAME, name);
        editor.commit();
    }

    public static String getUserName(Context context){
        return getSharedPreferences(context).getString(USER_NAME, "");
    }
}
