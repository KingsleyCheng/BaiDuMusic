package com.example.dllo.baidumusic.musiclibrary.radiostation.radiototal;

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
public class RadioTotalRecyclerAdapter extends RecyclerView.Adapter<RadioTotalRecyclerAdapter.MyViewHolder> {
    private RadioTotalBean radioTotalBean;
    private Context context;
    private RadioTotalOnClickListener radioTotalOnClickListener;

    public void setRadioTotalOnClickListener(RadioTotalOnClickListener radioTotalOnClickListener) {
        this.radioTotalOnClickListener = radioTotalOnClickListener;
    }

    public RadioTotalRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setRadioTotalBean(RadioTotalBean radioTotalBean) {
        this.radioTotalBean = radioTotalBean;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = null;
        View view = LayoutInflater.from(context).inflate(R.layout.item_radio_total, parent, false);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MySingleton.getInstance(context).getImageLoader().get(radioTotalBean
                .getResult().get(position).getIcon_android(), ImageLoader.getImageListener(holder.iconIv,
                R.mipmap.img_minibar_default, R.mipmap.img_minibar_default));
        holder.nameTv.setText(radioTotalBean.getResult().get(position).getScene_name());

        if (radioTotalOnClickListener != null) {
            holder.radioTotalLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positiont = holder.getLayoutPosition();
                    radioTotalOnClickListener.radioTotalOnClick(position);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return radioTotalBean != null && radioTotalBean.getResult().size() > 0 ? radioTotalBean.getResult().size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTv;
        private ImageView iconIv;
        private LinearLayout radioTotalLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            radioTotalLayout = (LinearLayout) itemView.findViewById(R.id.radio_total_layout);
            nameTv = (TextView) itemView.findViewById(R.id.radio_total_tv);
            iconIv = (ImageView) itemView.findViewById(R.id.radio_total_iv);
        }
    }
}
