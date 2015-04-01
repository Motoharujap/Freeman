package volley;


import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.*;
public class LruBitmapCache extends LruCache<String, Bitmap> implements
        ImageLoader.ImageCache {


    public static int getDefaultLruCachesize()
    {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }
    public LruBitmapCache()
    {
        this(getDefaultLruCachesize());
    }

    public LruBitmapCache(int sizeInKiloBytes)
    {
        super(sizeInKiloBytes);
    }

    public int sizeOf(String key, Bitmap value)
    {
        return value.getRowBytes() * value.getHeight() / 1024;
    }
    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
    }
}
