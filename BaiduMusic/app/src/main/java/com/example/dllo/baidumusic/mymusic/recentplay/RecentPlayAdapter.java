package com.example.dllo.baidumusic.mymusic.recentplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.dllo.baidumusic.R;

import java.util.List;

/**
 * Created by dllo on 16/7/6.
 */
public class RecentPlayAdapter extends BaseAdapter {
    private List<RecentPlayBean> recentPlayBeen;
    private Context context;
    private RecentPlayOnClickListener recentPlayOnClickListener;

    public void setRecentPlayOnClickListener(RecentPlayOnClickListener recentPlayOnClickListener) {
        this.recentPlayOnClickListener = recentPlayOnClickListener;
    }

    public RecentPlayAdapter(Context context) {
        this.context = context;
    }

    public void setRecentPlayBeen(List<RecentPlayBean> recentPlayBeen) {
        this.recentPlayBeen = recentPlayBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return recentPlayBeen == null ? 0 : recentPlayBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return recentPlayBeen == null ? null : recentPlayBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recent_play, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(recentPlayBeen.get(position).getTitle());
        holder.author.setText(recentPlayBeen.get(position).getAuthor());
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recentPlayOnClickListener.recentPlayOnClick(position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView title;
        private TextView author;
        private ImageView menu;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.recent_play_title);
            author = (TextView) view.findViewById(R.id.recent_play_author);
            menu = (ImageView) view.findViewById(R.id.recent_play_menu);
        }
    }
}
