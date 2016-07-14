package com.example.dllo.baidumusic.musiclibrary.recommend;

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

/**
 * Created by dllo on 16/6/23.
 */
public class RecommendViewAdapter extends RecyclerView.Adapter<RecommendViewAdapter.MyViewHoler> {
    private RecommendViewBean recommendViewBean;
    private Context context;
    private RecommendOnClickListener recommendOnClickListener;

    public void setRecommendOnClickListener(RecommendOnClickListener recommendOnClickListener) {
        this.recommendOnClickListener = recommendOnClickListener;
    }

    public RecommendViewAdapter(Context context) {
        this.context = context;
    }

    public void setRecommendViewBean(RecommendViewBean recommendViewBean) {
        this.recommendViewBean = recommendViewBean;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHoler myViewHoler = null;
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend, parent, false);
        myViewHoler = new MyViewHoler(view);
        return myViewHoler;
    }

    @Override
    public void onBindViewHolder(final MyViewHoler holder, int position) {
        MySingleton.getInstance(context).getImageLoader().get(recommendViewBean.getContent().getList()
                .get(position).getPic(), ImageLoader.getImageListener(holder.recommendIv,
                R.mipmap.default_radio_list_item_image, R.mipmap.default_radio_list_item_image));
        holder.listennumTv.setText(recommendViewBean.getContent().getList().get(position).getListenum());
        holder.titleTv.setText(recommendViewBean.getContent().getList().get(position).getTitle());
        holder.tagTv.setText(recommendViewBean.getContent().getList().get(position).getTag());

        if (recommendOnClickListener != null) {
            holder.recommendLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    recommendOnClickListener.recommendOnClick(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return recommendViewBean != null && recommendViewBean.getContent().getList().size() > 0 ?
                recommendViewBean.getContent().getList().size() : 0;
    }

    class MyViewHoler extends RecyclerView.ViewHolder {
        private ImageView recommendIv;
        private TextView titleTv, tagTv, listennumTv;
        private LinearLayout recommendLayout;

        public MyViewHoler(View itemView) {
            super(itemView);
            recommendIv = (ImageView) itemView.findViewById(R.id.recommend_iv);
            titleTv = (TextView) itemView.findViewById(R.id.recommend_title);
            tagTv = (TextView) itemView.findViewById(R.id.recommend_tag);
            listennumTv = (TextView) itemView.findViewById(R.id.recommend_listennum);
            recommendLayout = (LinearLayout) itemView.findViewById(R.id.recommend_layout);
        }
    }
}
