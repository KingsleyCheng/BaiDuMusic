package com.example.dllo.baidumusic;

import android.app.Application;
import android.content.Context;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;

/**
 * Created by dllo on 16/6/22.
 */
public class MyApp extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Bmob.initialize(context, "4e56a54b07b5eb7933bd9b311a5a0a56");

//        // 使用推送服务时的初始化操作
//        BmobInstallation.getCurrentInstallation(this).save();
//        // 启动推送服务
//        BmobPush.startWork(this);
    }
}
