package com.example.dllo.baidumusic.mymusic.localmusic;

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
public class LocalMusicAdapter extends BaseAdapter {
    private List<LocalMusicBean> localMusicBeen;
    private Context context;
    private LocalMusicOnClickListener localMusicOnClickListener;

    public LocalMusicAdapter(Context context) {
        this.context = context;
    }

    public void setLocalMusicBeen(List<LocalMusicBean> localMusicBeen) {
        this.localMusicBeen = localMusicBeen;
        notifyDataSetChanged();
    }

    public void setLocalMusicOnClickListener(LocalMusicOnClickListener localMusicOnClickListener) {
        this.localMusicOnClickListener = localMusicOnClickListener;
    }

    @Override
    public int getCount() {
        return localMusicBeen == null ? 0 : localMusicBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return localMusicBeen == null ? null : localMusicBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoelder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_local_music, parent, false);
            holder = new ViewHoelder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHoelder) convertView.getTag();
        }
        holder.title.setText(localMusicBeen.get(position).getTitle());
        holder.author.setText(localMusicBeen.get(position).getAuthor());
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localMusicOnClickListener.LocalMusicOnClick(position);
            }
        });
        return convertView;
    }

    class ViewHoelder {
        private TextView title, author;
        private ImageView menu;

        public ViewHoelder(View view) {
            title = (TextView) view.findViewById(R.id.local_music_title);
            author = (TextView) view.findViewById(R.id.local_music_author);
            menu = (ImageView) view.findViewById(R.id.local_music_menu);

        }
    }
}
