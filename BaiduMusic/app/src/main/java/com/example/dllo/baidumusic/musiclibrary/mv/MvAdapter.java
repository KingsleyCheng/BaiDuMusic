package com.example.dllo.baidumusic.musiclibrary.mv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.volley.MySingleton;

/**
 * Created by dllo on 16/6/22.
 */
public class MvAdapter extends RecyclerView.Adapter<MvAdapter.MyViewHolder> {
    private MvBean mvBean;
    private Context context;
    private MvOnClickListener mvOnClickListener;

    public void setMvOnClickListener(MvOnClickListener mvOnClickListener) {
        this.mvOnClickListener = mvOnClickListener;
    }

    public MvAdapter(Context context) {
        this.context = context;
    }

    public void setMvBean(MvBean mvBean) {
        this.mvBean = mvBean;
        notifyDataSetChanged();
    }

    public void addMvBean(MvBean bean) {
        mvBean.getResult().getMv_list().addAll(bean.getResult().getMv_list());
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = null;
        View view = LayoutInflater.from(context).inflate(R.layout.item_mv, parent, false);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MySingleton.getInstance(context).getImageLoader()
                .get(mvBean.getResult().getMv_list().get(position).getThumbnail(),
                        ImageLoader.getImageListener(holder.mvIv, R.mipmap.default_mv, R.mipmap.default_mv));
        holder.titleTv.setText(mvBean.getResult().getMv_list().get(position).getTitle());
        holder.arlistTv.setText(mvBean.getResult().getMv_list().get(position).getArtist());

        if (mvOnClickListener != null) {
            holder.mvLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mvOnClickListener.mvOnClick(position);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mvBean != null && mvBean.getResult().getMv_list().size() > 0 ? mvBean.getResult().getMv_list().size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mvIv;
        private TextView titleTv, arlistTv;
        private LinearLayout mvLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            mvIv = (ImageView) itemView.findViewById(R.id.mv_iv);
            titleTv = (TextView) itemView.findViewById(R.id.mv_title);
            arlistTv = (TextView) itemView.findViewById(R.id.mv_arlist);
            mvLayout = (LinearLayout) itemView.findViewById(R.id.mv_layout);
        }
    }
}
