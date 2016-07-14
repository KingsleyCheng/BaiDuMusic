package com.example.dllo.baidumusic.musiclibrary.charts.chartslist;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.db.LiteOrmSingleton;
import com.example.dllo.baidumusic.musicplayservice.IndexEvent;
import com.example.dllo.baidumusic.musicplayservice.MusicPlayService;
import com.example.dllo.baidumusic.musicplayservice.PlayingListManager;
import com.example.dllo.baidumusic.musicplayservice.SongIdEvent;
import com.example.dllo.baidumusic.mymusic.download.DownloadSingle;
import com.example.dllo.baidumusic.mymusic.likesong.LikeSongBean;
import com.example.dllo.baidumusic.mymusic.localmusic.ScanLocalMusic;
import com.example.dllo.baidumusic.songplaypage.playinglist.PlayingListBean;
import com.example.dllo.baidumusic.totalfragment.login.LoginActivity;
import com.example.dllo.baidumusic.urlvalues.URLValues;
import com.example.dllo.baidumusic.volley.GsonRequest;
import com.example.dllo.baidumusic.volley.MySingleton;
import com.litesuits.orm.db.assit.QueryBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by dllo on 16/6/25.
 */
public class ChartsListFragment extends BaseFragment implements View.OnClickListener {
    private ImageView chartsListIv, chartsListBack, chartsListShare;
    private TextView chartsPublishTime, chartsListNumber, chartsListTitle;
    private ListView listView;
    private ChartsListAdapter chartsListAdapter;
    private RequestQueue requestQueue;
    private ChartsListBean chartsListBean;
    private ServiceConnection serviceConnection;
    private MusicPlayService.MyBinder myBinder;
    private PopupWindow popupWindow, pupCollection;
    private int pos, id;
    private ImageView likeSongLoveIv;
    private TextView likeSongLoveTv;
    private BmobUser bmobUser;
    private View viewTitle;

    @Override
    public int setLayout() {
        return R.layout.fragment_charts_list;
    }

    @Override
    protected void intiView(View view) {
        viewTitle = LayoutInflater.from(context).inflate(R.layout.item_charts_list_title, null);
        chartsListIv = (ImageView) viewTitle.findViewById(R.id.charts_list_iv);
        chartsPublishTime = (TextView) viewTitle.findViewById(R.id.charts_publish_time);
        chartsListNumber = (TextView) viewTitle.findViewById(R.id.charts_list_number);
        chartsListShare = (ImageView) viewTitle.findViewById(R.id.charts_list_share);
        chartsListBack = (ImageView) view.findViewById(R.id.charts_list_back);
        listView = (ListView) view.findViewById(R.id.charts_list_lv);
        chartsListTitle = (TextView) view.findViewById(R.id.charts_list_title);
        chartsListShare.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (MusicPlayService.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }

        };
        Intent intent = new Intent(context, SongIdEvent.class);
        context.startService(intent);
        chartsListAdapter = new ChartsListAdapter(context);
        int type = getArguments().getInt("chartstype");
        String chartsListUrl = URLValues.CHARST_LIST1 + type + URLValues.CHARST_LIST2;
        requestQueue = MySingleton.getInstance(context).getRequestQueue();
        GsonRequest<ChartsListBean> gsonRequest = new GsonRequest<ChartsListBean>(Request.Method.GET,
                chartsListUrl, ChartsListBean.class, new Response.Listener<ChartsListBean>() {
            @Override
            public void onResponse(ChartsListBean response) {
                chartsListBean = response;
                chartsListAdapter.setChartsListBean(response);
                MySingleton.getInstance(context).getImageLoader().get(response.getBillboard().getPic_s640(),
                        ImageLoader.getImageListener(chartsListIv, R.mipmap.default_album_topic_detail,
                                R.mipmap.default_album_topic_detail));
                chartsPublishTime.setText(response.getBillboard().getUpdate_date());
                chartsListTitle.setText(response.getBillboard().getName());
                chartsListNumber.setText("共" + response.getSong_list().size() + "首歌");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(gsonRequest);
        listView.setAdapter(chartsListAdapter);
        listView.addHeaderView(viewTitle);
        //item监听EventBus传值到服务
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    EventBus.getDefault().post(new SongIdEvent(chartsListBean.getSong_list().get(position - 1).getSong_id()));
                    EventBus.getDefault().post(new IndexEvent(position - 1));
                    PlayingListManager.getInstance().getPlayingListBeen().clear();
                    for (ChartsListBean.SongListBean songListBean : chartsListBean.getSong_list()) {
                        PlayingListManager.getInstance().getPlayingListBeen().add(
                                new PlayingListBean(songListBean.getTitle(), songListBean.getAuthor(), songListBean.getSong_id()));
                    }
                }
            }
        });

        chartsListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        //跳转到喜欢与下载页面
        chartsListAdapter.setChartsListOnClickListener(new ChartsListOnClickListener() {
            @Override
            public void chartsListOnClick(int position) {
                pos = position;
                popupWindow = new PopupWindow();
                if (popupWindow == null || !popupWindow.isShowing()) {
                    showPopupwindow();
                }

            }
        });
    }

    private void showPopupwindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.like_song_popupwindow, null, false);
        likeSongLoveIv = (ImageView) view.findViewById(R.id.like_song_love_iv);
        likeSongLoveTv = (TextView) view.findViewById(R.id.like_song_love_tv);
        view.findViewById(R.id.like_song_framelayout).setOnClickListener(this);
        view.findViewById(R.id.like_song_love).setOnClickListener(this);
        view.findViewById(R.id.like_song_add).setOnClickListener(this);
        view.findViewById(R.id.like_song_share).setOnClickListener(this);
        view.findViewById(R.id.like_song_download).setOnClickListener(this);
        TextView likeSongTitle = (TextView) view.findViewById(R.id.like_song_title);
        likeSongTitle.setText(chartsListBean.getSong_list().get(pos).getTitle());
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setBackgroundDrawable(new PaintDrawable());

        bmobUser = BmobUser.getCurrentUser(context);
        if (bmobUser == null) {
            //查询本地数据库设置红心图标
            QueryBuilder<LikeSongBean> builder = new QueryBuilder<>(LikeSongBean.class);
            builder.whereEquals("songId", chartsListBean.getSong_list().get(pos).getSong_id());
            if (LiteOrmSingleton.getInstance().getLiteOrm().query(builder).size() == 0) {
                likeSongLoveIv.setImageResource(R.mipmap.ic_listmore_love_normal);
                likeSongLoveTv.setText("喜欢");
            } else {
                likeSongLoveIv.setImageResource(R.mipmap.ic_listmore_love_hl);
                likeSongLoveTv.setText("取消喜欢");
            }
        } else {
            //查询云端数据设置红心图标
            BmobQuery<LikeSongBean> query = new BmobQuery<>();
            query.addWhereEqualTo("userName", BmobUser.getCurrentUser(context).getUsername());
            query.addWhereEqualTo("songId", chartsListBean.getSong_list().get(pos).getSong_id());
            query.setLimit(50);
            query.findObjects(context, new FindListener<LikeSongBean>() {
                @Override
                public void onSuccess(List<LikeSongBean> list) {
                    if (list.size() == 0) {
                        likeSongLoveIv.setImageResource(R.mipmap.ic_listmore_love_normal);
                        likeSongLoveTv.setText("喜欢");
                    } else {
                        likeSongLoveIv.setImageResource(R.mipmap.ic_listmore_love_hl);
                        likeSongLoveTv.setText("取消喜欢");
                    }
                }

                @Override
                public void onError(int i, String s) {
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        id = v.getId();
        switch (v.getId()) {
            case R.id.like_song_framelayout:
                popupWindow.dismiss();
                break;
            case R.id.like_song_love:
                //判断登录状态如果登录保存到云端,如果没有登录保存到本地数据库
                bmobUser = BmobUser.getCurrentUser(context);
                if (bmobUser == null) {//未登录状态
                    QueryBuilder<LikeSongBean> builder = new QueryBuilder<>(LikeSongBean.class);
                    builder.whereEquals("songId", chartsListBean.getSong_list().get(pos).getSong_id());
                    if (LiteOrmSingleton.getInstance().getLiteOrm().query(builder).size() == 0) {
                        LiteOrmSingleton.getInstance().getLiteOrm().insert(
                                new LikeSongBean(chartsListBean.getSong_list().get(pos).getTitle(),
                                        chartsListBean.getSong_list().get(pos).getAuthor(),
                                        chartsListBean.getSong_list().get(pos).getSong_id()));

                        Toast.makeText(context, "已添加到我喜欢的单曲", Toast.LENGTH_SHORT).show();
                    } else {
                        LiteOrmSingleton.getInstance().getLiteOrm()
                                .delete(LiteOrmSingleton.getInstance().getLiteOrm().query(builder));
                        Toast.makeText(context, "已取消喜欢", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    saveDataToCloud();
                }
                //发送广播刷新数据
                Intent intent = new Intent(context.getPackageName() + ".likeSongAdd");
                context.sendBroadcast(intent);
                popupWindow.dismiss();
                break;
            case R.id.like_song_add:
                pupCollection = new PopupWindow();
                if (pupCollection == null || !pupCollection.isShowing()) {
                    bmobUser = BmobUser.getCurrentUser(context);
                    if (bmobUser == null) {
                        Intent intent1 = new Intent(context, LoginActivity.class);
                        startActivity(intent1);
                        showPupcollection();
                        popupWindow.dismiss();
                    } else {
                        showPupcollection();
                        popupWindow.dismiss();
                    }
                }
                break;
            case R.id.like_song_menu_framelayout:
                pupCollection.dismiss();
                break;
            case R.id.like_song_menu:
                //收藏数据保存到云端
                saveDataToCloud();
                pupCollection.dismiss();
                break;
            case R.id.like_song_share:
                ShareSDK.initSDK(context);
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("我是分享文本");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl("http://sharesdk.cn");
// 启动分享GUI
                oks.show(context);
                popupWindow.dismiss();
                break;
            case R.id.charts_list_share:
                ShareSDK.initSDK(context);
                OnekeyShare oks1 = new OnekeyShare();
                //关闭sso授权
                oks1.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                oks1.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks1.setText("我是分享文本");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                oks1.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks1.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks1.setSite(getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks1.setSiteUrl("http://sharesdk.cn");
// 启动分享GUI
                oks1.show(context);
                break;
            case R.id.like_song_download:
                DownloadSingle.getSingleDownload().DownLoad(chartsListBean.getSong_list().get(pos).getSong_id());
                ScanLocalMusic.localMusic(context);
                //发送广播刷新数据
                Intent intent1 = new Intent(context.getPackageName() + ".localMusicChange");
                context.sendBroadcast(intent1);
                popupWindow.dismiss();
                break;

        }
    }

    //保存数据到云端的POPUWINDOW
    private void showPupcollection() {
        View view = LayoutInflater.from(context).inflate(R.layout.like_song_menu_popupwindow, null, false);
        TextView likeSongMenuTitle = (TextView) view.findViewById(R.id.like_song_menu_title);
        view.findViewById(R.id.like_song_menu).setOnClickListener(this);
        view.findViewById(R.id.like_song_menu_framelayout).setOnClickListener(this);
        likeSongMenuTitle.setText(chartsListBean.getSong_list().get(pos).getTitle());
        pupCollection = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pupCollection.setFocusable(true);
        pupCollection.setBackgroundDrawable(new BitmapDrawable());
        pupCollection.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    //保存数据到云端
    public void saveDataToCloud() {
        BmobQuery<LikeSongBean> query = new BmobQuery<>();
        query.addWhereEqualTo("userName", BmobUser.getCurrentUser(context).getUsername());
        query.addWhereEqualTo("songId", chartsListBean.getSong_list().get(pos).getSong_id());
        query.setLimit(50);
        query.findObjects(context, new FindListener<LikeSongBean>() {
            @Override
            public void onSuccess(List<LikeSongBean> list) {
                if (list.size() == 0) {
                    LikeSongBean likeSongBean = new LikeSongBean();
                    likeSongBean.setUserName(BmobUser.getCurrentUser(context).getUsername());
                    likeSongBean.setTitle(chartsListBean.getSong_list().get(pos).getTitle());
                    likeSongBean.setAuthor(chartsListBean.getSong_list().get(pos).getAuthor());
                    likeSongBean.setSongId(chartsListBean.getSong_list().get(pos).getSong_id());
                    likeSongBean.save(context, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                            //发送广播刷新数据
                            Intent intent = new Intent(context.getPackageName() + ".likeSongAdd");
                            context.sendBroadcast(intent);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(context, "收藏失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    if (id == R.id.like_song_love) {//判断是第一个pup点击的
                        for (int i = 0; i < list.size(); i++) {
                            LikeSongBean likeSongBean = list.get(i);
                            likeSongBean.delete(context);
                        }
                        Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show();
                    } else {//第二个pup点击的
                        Toast.makeText(context, "歌曲已添加", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context, "收藏错误", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
