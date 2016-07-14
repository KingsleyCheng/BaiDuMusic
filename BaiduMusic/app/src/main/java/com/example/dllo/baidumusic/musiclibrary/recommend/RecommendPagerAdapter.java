package com.example.dllo.baidumusic.musiclibrary.recommend;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.baidumusic.MyApp;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.volley.MySingleton;

/**
 * Created by dllo on 16/6/22.
 */
public class RecommendPagerAdapter extends PagerAdapter {
    private RecommendPagerBean recommendPagerBean;

    public void setRecommendPagerBean(RecommendPagerBean recommendPagerBean) {
        this.recommendPagerBean = recommendPagerBean;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {//设置无上限的数量,可以无限的滑动
        return recommendPagerBean == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //取出指定位置的ImageView
        ImageView imageView = new ImageView(MyApp.context);
        MySingleton.getInstance(MyApp.context).getImageLoader().get(recommendPagerBean.getPic()
                        .get(position % recommendPagerBean.getPic().size()).getRandpic(),
                ImageLoader.getImageListener(imageView,
                        R.mipmap.default_album_topic_detail, R.mipmap.default_album_topic_detail));
        //当图片少的时候,不会触发destroyItem
        //这个时候去向container中addView会报错
        //手动捕获异常
        try {
            container.addView(imageView);

        } catch (IllegalStateException e) {
            //从container中移除ImageView
            container.removeView(imageView);
            //再次添加
            container.addView(imageView);
        }
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (container.getChildAt(position % recommendPagerBean.getPic().size()) == object) {
            //销毁指定位置的ImageView回收内存
            container.removeViewAt(position % recommendPagerBean.getPic().size());

        }
    }
}
