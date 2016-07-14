package com.example.dllo.baidumusic.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.songplaypage.playinglist.PlayingListBean;

import java.util.ArrayList;

/**
 * Created by dllo on 16/7/3.
 */
public class PopupWindowAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PlayingListBean> playingListBeen;
    private OnRemoveOnClickListener onRemoveOnClickListener;

    public void setOnRemoveOnClickListener(OnRemoveOnClickListener onRemoveOnClickListener) {
        this.onRemoveOnClickListener = onRemoveOnClickListener;
    }

    public PopupWindowAdapter(Context context) {
        this.context = context;
    }

    public void setPlayingListBeen(ArrayList<PlayingListBean> playingListBeen) {
        this.playingListBeen = playingListBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return playingListBeen == null ? 0 : playingListBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return playingListBeen == null ? null : playingListBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_popupwindow, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(playingListBeen.get(position).getTitle());
        holder.author.setText(playingListBeen.get(position).getAuthor());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemoveOnClickListener.onRemoveOnClick(position);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView title, author;
        ImageView remove;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.pop_list_title);
            author = (TextView) view.findViewById(R.id.pop_list_author);
            remove = (ImageView) view.findViewById(R.id.pop_list_del);
        }
    }
}
