package com.example.dllo.baidumusic.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.dllo.baidumusic.R;

import java.util.Stack;

/**
 * Created by dllo on 16/6/21.
 */
public abstract class BaseActivity extends AppCompatActivity {


    public abstract int setLayout();

    public abstract void activityInitData();

//    //Fragment 的管理栈
//    protected Stack<BaseFragment> fragmentControlStack;
//
//    public void addFragment(BaseFragment baseFragment) {
//        //将Fragment加入返回栈
//        fragmentControlStack.push(baseFragment);
//    }
//
//    //获得栈顶Fragment,并不出栈
//    public BaseFragment getTopFragment() {
//        //弹栈一次,获得栈顶Fragment
//        BaseFragment baseFragment = fragmentControlStack.pop();
//        fragmentControlStack.push(baseFragment);//再将该Fragment放回到Fragment的返回栈里
//        return baseFragment;
//    }
//
//    //出栈
//    public BaseFragment popFragment() {
//
//        return fragmentControlStack.pop();
//    }
//
//    //Fragment 点击返回键
//    public void fragmentBack() {
//        BaseFragment currentFragment = popFragment();
//        BaseFragment beforeFragment = getTopFragment();
//        getSupportFragmentManager().beginTransaction()
//                .remove(currentFragment).show(beforeFragment).commit();
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_letf_out);
        setContentView(setLayout());
        activityInitData();
        //注册栈
//        fragmentControlStack = new Stack<>();
        //注册广播

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_letf_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
