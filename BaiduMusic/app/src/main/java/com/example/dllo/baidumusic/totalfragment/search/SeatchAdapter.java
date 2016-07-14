package com.example.dllo.baidumusic.totalfragment.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dllo.baidumusic.R;

/**
 * Created by dllo on 16/7/4.
 */
public class SeatchAdapter extends BaseAdapter {
    private SearchBean searchBean;
    private Context context;

    public SeatchAdapter(Context context) {
        this.context = context;
    }

    public void setSearchBean(SearchBean searchBean) {
        this.searchBean = searchBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return searchBean == null ? 0 : searchBean.getResult().getSong_info().getSong_list().size();
    }

    @Override
    public Object getItem(int position) {
        return searchBean == null ? null : searchBean.getResult().getSong_info().getSong_list().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.searchTitle.setText(searchBean.getResult().getSong_info().getSong_list().get(position).getTitle());
        holder.searchAuthor.setText(searchBean.getResult().getSong_info().getSong_list().get(position).getAuthor());
        return convertView;
    }

    class ViewHolder {
        private TextView searchTitle, searchAuthor;

        public ViewHolder(View view) {
            searchTitle = (TextView) view.findViewById(R.id.search_title);
            searchAuthor = (TextView) view.findViewById(R.id.search_author);
        }
    }
}
