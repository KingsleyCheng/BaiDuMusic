package com.example.dllo.baidumusic.songplaypage.playinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dllo.baidumusic.R;

import java.util.List;

/**
 * Created by dllo on 16/6/29.
 */
public class PlayingListAdapter extends BaseAdapter {
    private List<PlayingListBean> playPageListBean;
    private Context context;

    public PlayingListAdapter(Context context) {
        this.context = context;
    }

    public void setPlayPageListBean(List<PlayingListBean> playPageListBean) {
        this.playPageListBean = playPageListBean;
    }

    @Override
    public int getCount() {
        return playPageListBean == null ? 0 : playPageListBean.size();
    }

    @Override
    public Object getItem(int position) {
        return playPageListBean == null ? null : playPageListBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_play_page_list, parent, false);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.playPageListTitle.setText(playPageListBean.get(position).getTitle());
        holder.playPageListAuthor.setText(playPageListBean.get(position).getAuthor());
        return convertView;
    }

    class ViewHolder {
        private TextView playPageListTitle, playPageListAuthor;

        public ViewHolder(View view) {
            playPageListTitle = (TextView) view.findViewById(R.id.play_page_list_title);
            playPageListAuthor = (TextView) view.findViewById(R.id.play_page_list_author);

        }
    }
}
