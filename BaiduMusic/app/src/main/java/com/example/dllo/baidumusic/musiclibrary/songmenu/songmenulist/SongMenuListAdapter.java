package com.example.dllo.baidumusic.musiclibrary.songmenu.songmenulist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.baidumusic.R;

/**
 * Created by dllo on 16/6/24.
 */
public class SongMenuListAdapter extends BaseAdapter {
    private SongMenuListBean songMenuListBean;
    private Context context;
    private SongMenuListOnClickListener songMenuListOnClickListener;

    public void setSongMenuListOnClickListener(SongMenuListOnClickListener songMenuListOnClickListener) {
        this.songMenuListOnClickListener = songMenuListOnClickListener;
    }

    public SongMenuListAdapter(Context context) {
        this.context = context;
    }

    public void setSongMenuListBean(SongMenuListBean songMenuListBean) {
        this.songMenuListBean = songMenuListBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return songMenuListBean == null ? 0 : songMenuListBean.getContent().size();
    }

    @Override
    public Object getItem(int position) {
        return songMenuListBean == null ? null : songMenuListBean.getContent().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_song_menu_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTv.setText(songMenuListBean.getContent().get(position).getTitle());
        holder.authorTv.setText(songMenuListBean.getContent().get(position).getAuthor());
        holder.songMenuListMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songMenuListOnClickListener.songMenuListOnClick(position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView nameTv, authorTv;
        private ImageView songMenuListMore;

        public ViewHolder(View view) {
            nameTv = (TextView) view.findViewById(R.id.song_menu_list_name);
            authorTv = (TextView) view.findViewById(R.id.song_menu_list_author);
            songMenuListMore = (ImageView) view.findViewById(R.id.song_menu_list_more);
        }
    }
}
