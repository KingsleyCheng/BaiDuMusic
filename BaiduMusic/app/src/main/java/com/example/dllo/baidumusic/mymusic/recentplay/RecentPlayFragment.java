package com.example.dllo.baidumusic.mymusic.recentplay;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
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

import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.db.LiteOrmSingleton;
import com.example.dllo.baidumusic.musicplayservice.IndexEvent;
import com.example.dllo.baidumusic.musicplayservice.PlayingListManager;
import com.example.dllo.baidumusic.musicplayservice.SongIdEvent;
import com.example.dllo.baidumusic.mymusic.download.DownloadSingle;
import com.example.dllo.baidumusic.mymusic.likesong.LikeSongBean;
import com.example.dllo.baidumusic.songplaypage.playinglist.PlayingListBean;
import com.example.dllo.baidumusic.totalfragment.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by dllo on 16/7/6.
 */
public class RecentPlayFragment extends BaseFragment implements View.OnClickListener {
    private RecentPlayAdapter recnetPlayAdapter;
    private ListView listView;
    private ImageView recentPlayReturn;
    private List<RecentPlayBean> recentPlayBeen;
    private PopupWindow popupWindow, pupCollection;
    private int pos;
    private TextView nothing, recentPlayRemoveAll;
    private BmobUser bmobUser;

    @Override
    public int setLayout() {
        return R.layout.fragment_recent_play;
    }

    @Override
    protected void intiView(View view) {
        listView = (ListView) view.findViewById(R.id.recent_play_list_view);
        recentPlayReturn = (ImageView) view.findViewById(R.id.recent_play_return);
        nothing = (TextView) view.findViewById(R.id.nothing);
        recentPlayRemoveAll = (TextView) view.findViewById(R.id.recent_play_remove_all);
        recentPlayReturn.setOnClickListener(this);
        recentPlayRemoveAll.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        recnetPlayAdapter = new RecentPlayAdapter(context);
        recentPlayBeen = new ArrayList<>();
        if (LiteOrmSingleton.getInstance().getLiteOrm().query(RecentPlayBean.class).size() != 0) {
            for (RecentPlayBean recentBean : LiteOrmSingleton.getInstance().getLiteOrm().query(RecentPlayBean.class)) {
                recentPlayBeen.add(recentBean);
            }
        } else {
            nothing.setVisibility(View.VISIBLE);
        }

        recnetPlayAdapter.setRecentPlayBeen(recentPlayBeen);
        listView.setAdapter(recnetPlayAdapter);

        recnetPlayAdapter.setRecentPlayOnClickListener(new RecentPlayOnClickListener() {
            @Override
            public void recentPlayOnClick(int position) {
                pos = position;
                if (popupWindow == null || !popupWindow.isShowing()) {
                    showPopupwindow();
                }
            }
        });
        //点击播放歌曲,并添加到播放列表
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new SongIdEvent(recentPlayBeen.get(position).getSongId()));
                EventBus.getDefault().post(new IndexEvent(position));
                PlayingListManager.getInstance().getPlayingListBeen().clear();
                for (RecentPlayBean recentBean : recentPlayBeen) {
                    PlayingListManager.getInstance().getPlayingListBeen()
                            .add(new PlayingListBean(recentBean.getTitle(), recentBean.getAuthor(),
                                    recentBean.getSongId()));
                }
            }
        });
    }

    private void showPopupwindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_play_popupwindow, null, false);
        view.findViewById(R.id.recent_play_framelayout).setOnClickListener(this);
        view.findViewById(R.id.recent_play_remove).setOnClickListener(this);
        view.findViewById(R.id.recent_play_add).setOnClickListener(this);
        view.findViewById(R.id.recent_play_share).setOnClickListener(this);
        TextView recentPlayTitle = (TextView) view.findViewById(R.id.recent_play_title);
        recentPlayTitle.setText(recentPlayBeen.get(pos).getTitle());
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setBackgroundDrawable(new PaintDrawable());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recent_play_return:
                getFragmentManager().popBackStack();
                break;
            case R.id.recent_play_framelayout:
                popupWindow.dismiss();
                break;
            case R.id.recent_play_remove:
                LiteOrmSingleton.getInstance().getLiteOrm().delete(recentPlayBeen.get(pos));
                recentPlayBeen = LiteOrmSingleton.getInstance().getLiteOrm().query(RecentPlayBean.class);
                recnetPlayAdapter.setRecentPlayBeen(recentPlayBeen);
                Intent intent = new Intent(context.getPackageName() + ".recentPlayRemove");
                context.sendBroadcast(intent);
                popupWindow.dismiss();
                Toast.makeText(context, "移除列表", Toast.LENGTH_SHORT).show();
                if (LiteOrmSingleton.getInstance().getLiteOrm().query(RecentPlayBean.class).size() == 0) {
                    nothing.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.recent_play_remove_all:
                if (LiteOrmSingleton.getInstance().getLiteOrm().query(RecentPlayBean.class).size() > 0) {
                    LiteOrmSingleton.getInstance().getLiteOrm().deleteAll(RecentPlayBean.class);
                    recentPlayBeen = LiteOrmSingleton.getInstance().getLiteOrm().query(RecentPlayBean.class);
                    recnetPlayAdapter.setRecentPlayBeen(recentPlayBeen);
                    Intent intent1 = new Intent(context.getPackageName() + ".recentPlayRemove");
                    context.sendBroadcast(intent1);
                    Toast.makeText(context, "全部清除", Toast.LENGTH_SHORT).show();
                    nothing.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.recent_play_add:
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
            case R.id.like_song_download:
                DownloadSingle.getSingleDownload().DownLoad(recentPlayBeen.get(pos).getSongId());
                //发送广播刷新数据
                Intent intent1 = new Intent(context.getPackageName() + ".localMusicRemove");
                context.sendBroadcast(intent1);
                popupWindow.dismiss();
                break;
            case R.id.like_song_menu_framelayout:
                pupCollection.dismiss();
                break;
            case R.id.like_song_menu:
                //收藏数据保存到云端
                saveDataToCloud();
                pupCollection.dismiss();
                break;
            case R.id.recent_play_share:
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
        }
    }

    //保存数据到云端的POPUWINDOW
    private void showPupcollection() {
        View view = LayoutInflater.from(context).inflate(R.layout.like_song_menu_popupwindow, null, false);
        TextView likeSongMenuTitle = (TextView) view.findViewById(R.id.like_song_menu_title);
        view.findViewById(R.id.like_song_menu).setOnClickListener(this);
        view.findViewById(R.id.like_song_menu_framelayout).setOnClickListener(this);
        likeSongMenuTitle.setText(recentPlayBeen.get(pos).getTitle());
        pupCollection = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pupCollection.setFocusable(true);
        pupCollection.setBackgroundDrawable(new BitmapDrawable());
        pupCollection.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    //保存数据到云端
    public void saveDataToCloud() {
        BmobQuery<LikeSongBean> query = new BmobQuery<>();
        query.addWhereEqualTo("songId", recentPlayBeen.get(pos).getSongId());
        query.setLimit(50);
        query.findObjects(context, new FindListener<LikeSongBean>() {
            @Override
            public void onSuccess(List<LikeSongBean> list) {
                if (list.size() == 0) {
                    LikeSongBean likeSongBean = new LikeSongBean();
                    likeSongBean.setUserName(BmobUser.getCurrentUser(context).getUsername());
                    likeSongBean.setTitle(recentPlayBeen.get(pos).getTitle());
                    likeSongBean.setAuthor(recentPlayBeen.get(pos).getAuthor());
                    likeSongBean.setSongId(recentPlayBeen.get(pos).getSongId());
                    likeSongBean.save(context, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(context, "收藏失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(context, "歌曲已添加", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(int i, String s) {
                Toast.makeText(context, "收藏错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
