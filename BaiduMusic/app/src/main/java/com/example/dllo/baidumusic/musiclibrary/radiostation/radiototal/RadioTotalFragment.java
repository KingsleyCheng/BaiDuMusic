package com.example.dllo.baidumusic.musiclibrary.radiostation.radiototal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.musiclibrary.radiostation.radiototal.radioplay.RadioPlayActivity;
import com.example.dllo.baidumusic.urlvalues.URLValues;
import com.example.dllo.baidumusic.volley.GsonRequest;
import com.example.dllo.baidumusic.volley.MySingleton;


/**
 * Created by dllo on 16/6/23.
 */

public class RadioTotalFragment extends BaseFragment {
    private RadioTotalRecyclerAdapter radioTotalRecyclerAdapter;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private RadioTotalBean radioTotalBean;

    //写一个生成指定Fragment的方法
    public static RadioTotalFragment getInstance(int pos) {
        RadioTotalFragment radioTotalFragment = new RadioTotalFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        radioTotalFragment.setArguments(bundle);
        return radioTotalFragment;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_radio_total;
    }

    @Override
    protected void intiView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.radio_total_recycler_view);

    }

    @Override
    protected void initData() {
        int pos = getArguments().getInt("pos");
        radioTotalRecyclerAdapter = new RadioTotalRecyclerAdapter(context);
        requestQueue = MySingleton.getInstance(context).getRequestQueue();
        GsonRequest<RadioTotalBean> gsonReyeust = new GsonRequest<RadioTotalBean>(Request.Method.GET,
                URLValues.RADIO_TOTAL1+pos+URLValues.RADIO_TOTAL2, RadioTotalBean.class, new Response.Listener<RadioTotalBean>() {
            @Override
            public void onResponse(RadioTotalBean response) {
                radioTotalBean = response;
                radioTotalRecyclerAdapter.setRadioTotalBean(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(gsonReyeust);
        recyclerView.setAdapter(radioTotalRecyclerAdapter);
        GridLayoutManager manager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(manager);

        radioTotalRecyclerAdapter.setRadioTotalOnClickListener(new RadioTotalOnClickListener() {
            @Override
            public void radioTotalOnClick(int position) {
                String sceneId = radioTotalBean.getResult().get(position).getScene_id();
                Intent intent = new Intent(context, RadioPlayActivity.class);
                intent.putExtra("sceneId", sceneId);
                intent.putExtra("sceneName", radioTotalBean.getResult().get(position).getScene_name());
                startActivity(intent);
                getActivity().finish();


            }
        });

    }

}
