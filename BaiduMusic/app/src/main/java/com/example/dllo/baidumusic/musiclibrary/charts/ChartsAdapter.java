package com.example.dllo.baidumusic.musiclibrary.charts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.volley.MySingleton;

import java.util.List;

/**
 * Created by dllo on 16/6/21.
 */
public class ChartsAdapter extends BaseAdapter {
    private Context context;
    private ChartsBean chartsBean;

    public ChartsAdapter(Context context) {
        this.context = context;
    }

    public void setChartsBean(ChartsBean chartsBean) {
        this.chartsBean = chartsBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return chartsBean != null && chartsBean.getContent().size() > 0 ? chartsBean.getContent().size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return chartsBean != null && chartsBean.getContent().size() > 0 ? chartsBean.getContent().get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_charts, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MySingleton.getInstance(context).getImageLoader().get(chartsBean.getContent().get(position).getPic_s260(),
                ImageLoader.getImageListener(holder.chartsIv, R.mipmap.default_album_list, R.mipmap.default_album_list));
        holder.nameTv.setText(chartsBean.getContent().get(position).getName());
        holder.titleOne.setText(chartsBean.getContent().get(position).getContentsong().get(0).getTitle());
        holder.titleTwo.setText(chartsBean.getContent().get(position).getContentsong().get(1).getTitle());
        holder.titleThree.setText(chartsBean.getContent().get(position).getContentsong().get(2).getTitle());
        holder.authorOne.setText(chartsBean.getContent().get(position).getContentsong().get(0).getAuthor());
        holder.authorTwo.setText(chartsBean.getContent().get(position).getContentsong().get(1).getAuthor());
        holder.authorThree.setText(chartsBean.getContent().get(position).getContentsong().get(2).getAuthor());
        return convertView;

    }

    class ViewHolder {
        private TextView nameTv, titleOne, titleTwo, titleThree, authorOne, authorTwo, authorThree;
        private ImageView chartsIv;

        public ViewHolder(View view) {
            nameTv = (TextView) view.findViewById(R.id.charts_name_tv);
            titleOne = (TextView) view.findViewById(R.id.charts_title_one);
            titleTwo = (TextView) view.findViewById(R.id.charts_title_two);
            titleThree = (TextView) view.findViewById(R.id.charts_title_three);
            authorOne = (TextView) view.findViewById(R.id.charts_author_one);
            authorTwo = (TextView) view.findViewById(R.id.charts_author_two);
            authorThree = (TextView) view.findViewById(R.id.charts_author_three);
            chartsIv = (ImageView) view.findViewById(R.id.charts_iv);

        }
    }
}
