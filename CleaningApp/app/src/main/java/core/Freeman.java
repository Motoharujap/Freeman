package core;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import Database.DBHelper;

import ObjectModel.User;
import volley.LruBitmapCache;

/**
 * Created by Motoharu on 13.10.2014.
 */
public class Freeman extends Application {
    public static final String TAG = Freeman.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    LruBitmapCache mLruBitmapCache;
    private User user;
    public DBHelper mDbHelper;

    private static Freeman mInstance;

    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        mDbHelper = new DBHelper(this);
        user = User.getInstance();
        if (mDbHelper.areUsersExist()){
            //TODO fill the User object with data
            int userID = (int) mDbHelper.getCurrentUserID();
            user.instantiateUser(userID);
        }
    }

    public static synchronized Freeman getInstance()
    {
        return mInstance;
    }

    public RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader()
    {
        getRequestQueue();
        if (mImageLoader == null){

            getLruBitmapCache();
            mImageLoader = new ImageLoader(mRequestQueue, mLruBitmapCache);
        }
        return this.mImageLoader;
    }

    public LruBitmapCache getLruBitmapCache()
    {
        if (mLruBitmapCache==null){
            mLruBitmapCache = new LruBitmapCache();
        }
        return this.mLruBitmapCache;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag)
    {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag)
    {
        if (mRequestQueue!=null){
            mRequestQueue.cancelAll(tag);
        }
    }

}
