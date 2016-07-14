package com.example.dllo.baidumusic.mymusic.likesong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.baidumusic.R;

import java.util.List;

/**
 * Created by dllo on 16/7/7.
 */
public class LikeSongAdapter extends BaseAdapter {
    private List<LikeSongBean> likeSongBeen;
    private Context context;
    private LikeSongOnClickListener likeSongOnClickListener;

    public void setLikeSongOnClickListener(LikeSongOnClickListener likeSongOnClickListener) {
        this.likeSongOnClickListener = likeSongOnClickListener;
    }

    public LikeSongAdapter(Context context) {
        this.context = context;
    }

    public void setLikeSongBeen(List<LikeSongBean> likeSongBeen) {
        this.likeSongBeen = likeSongBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return likeSongBeen == null ? 0 : likeSongBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return likeSongBeen == null ? null : likeSongBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_like_song, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(likeSongBeen.get(position).getTitle());
        holder.author.setText(likeSongBeen.get(position).getAuthor());
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeSongOnClickListener.likeSongClick(position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView title;
        private TextView author;
        private ImageView menu;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.like_song_title);
            author = (TextView) view.findViewById(R.id.like_song_author);
            menu = (ImageView) view.findViewById(R.id.like_song_menu);
        }
    }
}
