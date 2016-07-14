package com.example.dllo.baidumusic.volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by dllo on 16/6/21.
 */
public class MemoryCache implements ImageLoader.ImageCache {
    //LruCache这个类可以用来缓存,内部帮我们写好了的算法
    //可以在缓存存满的时候,把最近最少使用的东西干掉
    private LruCache<String, Bitmap> lruCache;

    public MemoryCache() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024 / 8);
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            //来告诉LruCache每一个Bitmap占有多大内存
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return lruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }
}
