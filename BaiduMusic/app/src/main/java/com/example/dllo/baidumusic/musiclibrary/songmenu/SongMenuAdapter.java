package com.example.dllo.baidumusic.musiclibrary.songmenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.volley.MySingleton;

import java.util.List;

/**
 * Created by dllo on 16/6/22.
 */
public class SongMenuAdapter extends RecyclerView.Adapter<SongMenuAdapter.MyViewHolder> {
    private SongMenuBean songMenuBean;
    private Context context;
    private SongMenuOnClickListener songMenuOnClickListener;

    public void setSongMenuOnClickListener(SongMenuOnClickListener songMenuOnClickListener) {
        this.songMenuOnClickListener = songMenuOnClickListener;
    }

    public SongMenuAdapter(Context context) {
        this.context = context;
    }

    public void setSongMenuBean(SongMenuBean songMenuBean) {
        this.songMenuBean = songMenuBean;
        notifyDataSetChanged();
    }

    public SongMenuBean getSongMenuBean() {
        return songMenuBean;
    }

    public void addSongMenuBean(SongMenuBean bean) {
        songMenuBean.getContent().addAll(bean.getContent());
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = null;
        View view = LayoutInflater.from(context).inflate(R.layout.item_song_menu, parent, false);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MySingleton.getInstance(context).getImageLoader().get(songMenuBean.getContent().get(position).getPic_300(),
                ImageLoader.getImageListener(holder.songMenuIv, R.mipmap.default_playlist_list, R.mipmap.default_playlist_list));
        holder.listennumTv.setText(songMenuBean.getContent().get(position).getListenum());
        holder.titleTv.setText(songMenuBean.getContent().get(position).getTitle());
        holder.tagTv.setText(songMenuBean.getContent().get(position).getTag());
        //当接口对象不为空时,对itemview进行设置
        if (songMenuOnClickListener != null) {
            holder.songMenuLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //itemview用getLayoutPosition的方法获取当前点击position
                    int position = holder.getLayoutPosition();
                    //调用接口对象的方法
                    songMenuOnClickListener.onSongMenuOnClick(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return songMenuBean != null && songMenuBean.getContent().size() > 0 ? songMenuBean.getContent().size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView listennumTv, titleTv, tagTv;
        private ImageView songMenuIv;
        private LinearLayout songMenuLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            listennumTv = (TextView) itemView.findViewById(R.id.song_menu_listennum);
            titleTv = (TextView) itemView.findViewById(R.id.song_menu_title);
            tagTv = (TextView) itemView.findViewById(R.id.song_menu_tag);
            songMenuIv = (ImageView) itemView.findViewById(R.id.song_menu_iv);
            songMenuLayout = (LinearLayout) itemView.findViewById(R.id.song_menu_layout);
        }
    }
}
