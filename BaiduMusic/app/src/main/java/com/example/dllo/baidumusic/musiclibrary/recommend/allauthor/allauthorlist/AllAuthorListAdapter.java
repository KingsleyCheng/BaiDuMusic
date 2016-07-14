package com.example.dllo.baidumusic.musiclibrary.recommend.allauthor.allauthorlist;

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
 * Created by dllo on 16/6/30.
 */
public class AllAuthorListAdapter extends BaseAdapter {
    private AllAuthorListBean allAuthorListBean;
    private Context context;

    public AllAuthorListAdapter(Context context) {
        this.context = context;
    }

    public void setAllAuthorListBean(AllAuthorListBean allAuthorListBean) {
        this.allAuthorListBean = allAuthorListBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return allAuthorListBean == null ? 0 : allAuthorListBean.getArtist().size();
    }

    @Override
    public Object getItem(int position) {
        return allAuthorListBean == null ? null : allAuthorListBean.getArtist().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_all_author_list, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MySingleton.getInstance(context).getImageLoader().get(allAuthorListBean.getArtist().get(position).getAvatar_small(),
                ImageLoader.getImageListener(holder.allAuthorListIv, R.mipmap.share_default, R.mipmap.share_default));
        holder.allAuthorListName.setText(allAuthorListBean.getArtist().get(position).getName());
        return convertView;
    }

    class ViewHolder {
        private ImageView allAuthorListIv;
        private TextView allAuthorListName;

        public ViewHolder(View view) {
            allAuthorListIv = (ImageView) view.findViewById(R.id.all_author_list_iv);
            allAuthorListName = (TextView) view.findViewById(R.id.all_author_list_name);
        }
    }
}
