package com.example.dllo.baidumusic.musiclibrary.charts.chartslist;

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

/**
 * Created by dllo on 16/6/25.
 */
public class ChartsListAdapter extends BaseAdapter {
    private ChartsListBean chartsListBean;
    private Context context;
    private ChartsListOnClickListener chartsListOnClickListener;

    public void setChartsListOnClickListener(ChartsListOnClickListener chartsListOnClickListener) {
        this.chartsListOnClickListener = chartsListOnClickListener;
    }

    public ChartsListAdapter(Context context) {
        this.context = context;
    }

    public void setChartsListBean(ChartsListBean chartsListBean) {
        this.chartsListBean = chartsListBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return chartsListBean == null ? 0 : chartsListBean.getSong_list().size();
    }

    @Override
    public Object getItem(int position) {
        return chartsListBean == null ? null : chartsListBean.getSong_list().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_charts_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MySingleton.getInstance(context).getImageLoader().get(
                chartsListBean.getSong_list().get(position).getPic_small(),
                ImageLoader.getImageListener(holder.smallIv,
                        R.mipmap.share_default, R.mipmap.share_default));
        holder.authorTv.setText(chartsListBean.getSong_list().get(position).getAuthor());
        holder.songNameTv.setText(chartsListBean.getSong_list().get(position).getTitle());
        holder.chartsListMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chartsListOnClickListener.chartsListOnClick(position);
            }
        });
        return convertView;

    }


    class ViewHolder {
        private TextView songNameTv, authorTv;
        private ImageView smallIv;
        private ImageView chartsListMore;

        public ViewHolder(View view) {
            smallIv = (ImageView) view.findViewById(R.id.charts_list_small_iv);
            songNameTv = (TextView) view.findViewById(R.id.charts_list_name);
            authorTv = (TextView) view.findViewById(R.id.charts_list_author);
            chartsListMore = (ImageView) view.findViewById(R.id.charts_list_more);

        }
    }
}
