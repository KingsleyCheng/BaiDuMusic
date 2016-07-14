package com.example.dllo.baidumusic.musiclibrary.recommend.allauthor;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.volley.MySingleton;

/**
 * Created by dllo on 16/6/29.
 */
public class AllAuthorAdapter extends PagerAdapter {
    private Context context;
    private AllAuthorBean allAuthorBean;
    private AllAuthorOnClickListener allAuthorOnClickListener;

    public void setAllAuthorOnClickListener(AllAuthorOnClickListener allAuthorOnClickListener) {
        this.allAuthorOnClickListener = allAuthorOnClickListener;
    }

    public AllAuthorAdapter(Context context) {
        this.context = context;
    }

    public void setAllAuthorBean(AllAuthorBean allAuthorBean) {
        this.allAuthorBean = allAuthorBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return allAuthorBean == null ? 0 : allAuthorBean.getArtist().size() / 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_author_view, container, false);
        ImageView imageOne = (ImageView) view.findViewById(R.id.author_image_one);
        ImageView imageTwo = (ImageView) view.findViewById(R.id.author_image_two);
        ImageView imageThree = (ImageView) view.findViewById(R.id.author_image_three);
        TextView nameOne = (TextView) view.findViewById(R.id.author_name_one);
        TextView nameTwo = (TextView) view.findViewById(R.id.author_name_two);
        TextView nameThree = (TextView) view.findViewById(R.id.author_name_three);
        final int pos = position * 3;
        nameOne.setText(allAuthorBean.getArtist().get(pos).getName());
        nameTwo.setText(allAuthorBean.getArtist().get(pos + 1).getName());
        nameThree.setText(allAuthorBean.getArtist().get(pos + 2).getName());
        MySingleton.getInstance(context).getImageLoader().get(allAuthorBean.getArtist().get(pos).getAvatar_big(),
                ImageLoader.getImageListener(imageOne, R.mipmap.default_radio_list_item_image,
                        R.mipmap.default_radio_list_item_image));
        MySingleton.getInstance(context).getImageLoader().get(allAuthorBean.getArtist().get(pos + 1).getAvatar_big(),
                ImageLoader.getImageListener(imageTwo, R.mipmap.default_radio_list_item_image,
                        R.mipmap.default_radio_list_item_image));
        MySingleton.getInstance(context).getImageLoader().get(allAuthorBean.getArtist().get(pos + 2).getAvatar_big(),
                ImageLoader.getImageListener(imageThree, R.mipmap.default_radio_list_item_image,
                        R.mipmap.default_radio_list_item_image));
        imageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allAuthorOnClickListener.allAuthorOnClick(pos);
            }
        });
        imageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allAuthorOnClickListener.allAuthorOnClick(pos + 1);
            }
        });
        imageThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allAuthorOnClickListener.allAuthorOnClick(pos + 2);
            }
        });

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
