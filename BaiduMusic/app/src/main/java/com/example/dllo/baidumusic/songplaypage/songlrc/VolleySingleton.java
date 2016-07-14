package com.example.dllo.baidumusic.songplaypage.songlrc;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.dllo.baidumusic.MyApp;
import com.example.dllo.baidumusic.volley.MemoryCache;

/**
 * Created by dllo on 16/7/5.
 */
public class VolleySingleton {
    private static VolleySingleton ourInstance = new VolleySingleton();
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public static VolleySingleton getInstance() {

        return ourInstance;
    }

    private VolleySingleton() {

        requestQueue= Volley.newRequestQueue(MyApp.context);
        imageLoader=new ImageLoader(requestQueue,new MemoryCache());
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
