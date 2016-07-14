package com.example.dllo.baidumusic.musiclibrary.recommend.allauthor;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.main.MainActivity;
import com.example.dllo.baidumusic.musiclibrary.recommend.allauthor.allauthorlist.AllAuthorListFragment;
import com.example.dllo.baidumusic.totalfragment.TotalFragment;
import com.example.dllo.baidumusic.urlvalues.URLValues;
import com.example.dllo.baidumusic.volley.GsonRequest;
import com.example.dllo.baidumusic.volley.MySingleton;

/**
 * Created by dllo on 16/6/30.
 */
public class AllAuthorFragment extends BaseFragment implements View.OnClickListener {
    private ImageView allAuthorReturn;
    private LinearLayout allAuthorMore;
    private ViewPager viewPager;
    private AllAuthorAdapter allAuthorAdapter;
    private RequestQueue requestQueue;
    private AllAuthorBean allAuthorBean;
    private LinearLayout dots;
    private ImageView[] dotIv;
    private int[] singerId = {R.id.chn_male_singer, R.id.chn_female_singer, R.id.chn_combination,
            R.id.western_male_singer, R.id.western_female_singer, R.id.western_combination,
            R.id.kr_male_singer, R.id.kr_female_singer, R.id.kr_combination,
            R.id.jp_male_singer, R.id.jp_female_singer, R.id.jp_combination,
            R.id.other_singer};
    private String[] allAuthorListUrl = {
            URLValues.CHN_MALE_SINGER, URLValues.CHE_FEMALE_SINGER, URLValues.CHE_COMBINATION,
            URLValues.WESTERN_MALE_SINGER, URLValues.WESTERN_FEMALE_SINGER, URLValues.WESTERN_COMBINATION,
            URLValues.KR_MALE_SINGER, URLValues.KR_FEMALE_SINGER, URLValues.KR_COMBINATION,
            URLValues.JP_MALE_SINGER, URLValues.JP_FEMALE_SINGER, URLValues.JP_COMBINATION,
            URLValues.OTHER_SINGER};
    private String[] allAuthorType = {"华语男歌手", "话语女歌手", "话语组合", "欧美男歌手", "欧美女歌手", "欧美组合",
            "韩国男歌手", "韩国女歌手", "韩国组合", "日本男歌手", "日本女歌手", "日本组合", "其他"};

    @Override
    public int setLayout() {
        return R.layout.fragment_all_author;
    }

    @Override
    protected void intiView(View view) {
        allAuthorReturn = (ImageView) view.findViewById(R.id.all_author_return);
        allAuthorMore = (LinearLayout) view.findViewById(R.id.all_author_more);
        viewPager = (ViewPager) view.findViewById(R.id.all_author_view_pager);
        dots = (LinearLayout) view.findViewById(R.id.all_author_viewgroup);
        for (int i = 0; i < singerId.length; i++) {
            view.findViewById(singerId[i]).setOnClickListener(this);
        }

    }

    @Override
    protected void initData() {
        allAuthorAdapter = new AllAuthorAdapter(context);
        requestQueue = MySingleton.getInstance(context).getRequestQueue();
        GsonRequest<AllAuthorBean> gsonRequest = new GsonRequest<AllAuthorBean>(Request.Method.GET, URLValues.ALL_AUTHOR_BANNER,
                AllAuthorBean.class, new Response.Listener<AllAuthorBean>() {
            @Override
            public void onResponse(AllAuthorBean response) {
                allAuthorBean = response;
                allAuthorAdapter.setAllAuthorBean(response);
                //轮播图加点
                dotIv = new ImageView[response.getArtist().size() / 3];
                for (int i = 0; i < dotIv.length; i++) {
                    ImageView imageView = new ImageView(context);
                    if (i == 0) {
                        imageView.setImageResource(R.mipmap.ic_dot_default_selected);
                    } else {
                        imageView.setImageResource(R.mipmap.ic_dot_default_unselected);
                    }
                    dotIv[i] = imageView;
                    LinearLayout.LayoutParams layoutParams =
                            new LinearLayout.LayoutParams(new
                                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                    //设置点的view的左右边距
                    layoutParams.leftMargin = 10;
                    layoutParams.rightMargin = 10;
                    dots.addView(imageView, layoutParams);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(gsonRequest);
        viewPager.setAdapter(allAuthorAdapter);

        //轮播图点的位置监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotIv.length; i++) {
                    if (i == position) {
                        dotIv[i].setImageResource(R.mipmap.ic_dot_default_selected);
                    } else {
                        dotIv[i].setImageResource(R.mipmap.ic_dot_default_unselected);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        //退出页面监听
        allAuthorReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        //跳转到歌手详情列表
        allAuthorAdapter.setAllAuthorOnClickListener(new AllAuthorOnClickListener() {
            @Override
            public void allAuthorOnClick(int position) {
                Log.d("AllAuthorFragment", "position:" + position);


                String tingUid = allAuthorBean.getArtist().get(position).getTing_uid();
                String picUrl = allAuthorBean.getArtist().get(position).getAvatar_big();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.nextToAuthorDetailList(tingUid, picUrl);

            }
        });
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (!getActivity().isDestroyed()) {
//            MainActivity mainActivity = (MainActivity) getActivity();
//            mainActivity.showtotalFragment();
//        }
//    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < singerId.length; i++) {
            if (v.getId() == singerId[i]) {
                String url = allAuthorListUrl[i];
                String type = allAuthorType[i];
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.toAllAuthorList(url, type);
            }
        }


    }

}
