package com.example.dllo.baidumusic.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by dllo on 16/6/21.
 */
public class MySingleton {
    private static MySingleton mySingleton;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    private MySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue, new MemoryCache());
    }

    public static MySingleton getInstance(Context context) {
        if (mySingleton == null) {
            synchronized (MySingleton.class) {
                if (mySingleton == null) {
                    mySingleton = new MySingleton(context);
                }
            }
        }
        return mySingleton;
    }
}
