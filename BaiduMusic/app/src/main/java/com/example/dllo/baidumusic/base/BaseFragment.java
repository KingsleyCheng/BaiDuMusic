package com.example.dllo.baidumusic.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dllo on 16/6/21.
 */
public abstract class BaseFragment extends Fragment{
    protected Context context;
    protected BaseActivity baseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
//        baseActivity = (BaseActivity) context;
//        baseActivity.addFragment(this);
    }

    public abstract int setLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiView(view);
    }

    protected abstract void intiView(View view);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract void initData();

}
