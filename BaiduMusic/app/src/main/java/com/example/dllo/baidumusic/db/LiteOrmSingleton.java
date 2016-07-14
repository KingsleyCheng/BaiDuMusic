package com.example.dllo.baidumusic.db;

import com.example.dllo.baidumusic.MyApp;
import com.litesuits.orm.LiteOrm;

/**
 * Created by dllo on 16/7/6.
 */
public class LiteOrmSingleton {
    private static LiteOrmSingleton liteOrmSingleton;
    private LiteOrm liteOrm;

    public LiteOrm getLiteOrm() {
        return liteOrm;
    }

    private LiteOrmSingleton() {
        liteOrm = LiteOrm.newCascadeInstance(MyApp.context, "BAIDUMUSIC.db");
    }

    public static LiteOrmSingleton getInstance() {
        if (liteOrmSingleton == null) {
            synchronized (LiteOrmSingleton.class) {
                if (liteOrmSingleton == null) {
                    liteOrmSingleton = new LiteOrmSingleton();
                }
            }

        }
        return liteOrmSingleton;
    }
}
