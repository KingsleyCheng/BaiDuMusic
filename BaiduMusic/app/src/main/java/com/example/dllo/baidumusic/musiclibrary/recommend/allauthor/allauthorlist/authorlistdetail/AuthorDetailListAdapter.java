package com.example.dllo.baidumusic.musiclibrary.recommend.allauthor.allauthorlist.authorlistdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.baidumusic.R;

/**
 * Created by dllo on 16/6/30.
 */
public class AuthorDetailListAdapter extends BaseAdapter {
    private AuthorDetailListBean authorDetailListBean;
    private Context context;
    private AuthorDetailListOnClickListener authorDetailListOnClickListener;

    public void setAuthorDetailListOnClickListener(AuthorDetailListOnClickListener authorDetailListOnClickListener) {
        this.authorDetailListOnClickListener = authorDetailListOnClickListener;
    }

    public AuthorDetailListAdapter(Context context) {
        this.context = context;
    }

    public void setAuthorDetailListBean(AuthorDetailListBean authorDetailListBean) {
        this.authorDetailListBean = authorDetailListBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return authorDetailListBean == null ? 0 : authorDetailListBean.getSonglist().size();
    }

    @Override
    public Object getItem(int position) {
        return authorDetailListBean == null ? null : authorDetailListBean.getSonglist().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_author_detai_listl, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.authorDetailListName.setText(authorDetailListBean.getSonglist().get(position).getTitle());
        holder.authorDetailListTitle.setText("《" + authorDetailListBean.getSonglist().get(position).getAlbum_title() + "》");
        holder.authorDetailListMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authorDetailListOnClickListener.authorDetailListOnClick(position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView authorDetailListName, authorDetailListTitle;
        private ImageView authorDetailListMore;

        public ViewHolder(View view) {
            authorDetailListName = (TextView) view.findViewById(R.id.author_detail_list_name);
            authorDetailListTitle = (TextView) view.findViewById(R.id.author_detail_list_title);
            authorDetailListMore = (ImageView) view.findViewById(R.id.author_detail_list_more);
        }


    }
}
