package com.example.dllo.baidumusic.mymusic.likesong;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.util.Log;
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
import com.example.dllo.baidumusic.songplaypage.playinglist.PlayingListBean;
import com.litesuits.orm.db.assit.QueryBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by dllo on 16/7/7.
 */
public class LikeSongFragment extends BaseFragment implements View.OnClickListener {
    private LikeSongAdapter likeSongAdapter;
    private ListView listView;
    private List<LikeSongBean> likeSongBeen;
    private PopupWindow popupWindow;
    private ImageView likeSongReturn;
    private int pos;
    private ImageView likeSongLoveIv;
    private TextView likeSongLoveTv, nothing;
    private List<LikeSongBean> likeSongBeanList;
    private BmobUser bmobUser;


    @Override
    public int setLayout() {
        return R.layout.fragment_like_song;
    }

    @Override
    protected void intiView(View view) {
        listView = (ListView) view.findViewById(R.id.like_song_list_view);
        likeSongReturn = (ImageView) view.findViewById(R.id.like_song_return);
        nothing = (TextView) view.findViewById(R.id.nothing);
        likeSongReturn.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        likeSongAdapter = new LikeSongAdapter(context);
        likeSongBeen = new ArrayList<>();
        //获取本地数据库数据
        if (LiteOrmSingleton.getInstance().getLiteOrm().query(LikeSongBean.class).size() != 0) {
            for (LikeSongBean likeBean : LiteOrmSingleton.getInstance().getLiteOrm().query(LikeSongBean.class)) {
                likeSongBeen.add(likeBean);
            }

        }
        //获取云端数据
        bmobUser = BmobUser.getCurrentUser(context);
        if (bmobUser != null) {
            BmobQuery<LikeSongBean> query = new BmobQuery<>();
            query.addWhereEqualTo("userName", BmobUser.getCurrentUser(context).getUsername());
            query.setLimit(50);
            query.findObjects(context, new FindListener<LikeSongBean>() {
                @Override
                public void onSuccess(List<LikeSongBean> list) {
                    for (LikeSongBean songBean : list) {
                        likeSongBeen.add(songBean);
                        likeSongAdapter.setLikeSongBeen(likeSongBeen);
                    }

                }

                @Override
                public void onError(int i, String s) {
                }
            });

        }

        likeSongAdapter.setLikeSongBeen(likeSongBeen);
        listView.setAdapter(likeSongAdapter);


        bmobUser = BmobUser.getCurrentUser(context);
        if (bmobUser != null) {
            BmobQuery<LikeSongBean> query = new BmobQuery<>();
            query.addWhereEqualTo("userName", BmobUser.getCurrentUser(context).getUsername());
            query.count(context, LikeSongBean.class, new CountListener() {
                @Override
                public void onSuccess(int i) {
                    if (LiteOrmSingleton.getInstance().getLiteOrm().query(LikeSongBean.class).size() == 0 && i == 0) {
                        nothing.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        } else {
            if (LiteOrmSingleton.getInstance().getLiteOrm().query(LikeSongBean.class).size() == 0) {
                nothing.setVisibility(View.VISIBLE);
            }
        }


        likeSongAdapter.setLikeSongOnClickListener(new LikeSongOnClickListener() {
            @Override
            public void likeSongClick(int position) {
                pos = position;
                popupWindow = new PopupWindow();
                if (popupWindow == null || !popupWindow.isShowing()) {
                    showPopupwindow();
                }
            }
        });

        //点击播放歌曲,并添加到播放列表
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new SongIdEvent(likeSongBeen.get(position).getSongId()));
                EventBus.getDefault().post(new IndexEvent(position));
                PlayingListManager.getInstance().getPlayingListBeen().clear();
                for (LikeSongBean likeBean : likeSongBeen) {
                    PlayingListManager.getInstance().getPlayingListBeen()
                            .add(new PlayingListBean(likeBean.getTitle(), likeBean.getAuthor(),
                                    likeBean.getSongId()));
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.like_song_return:
                getFragmentManager().popBackStack();
                break;
            case R.id.like_song_framelayout:
                popupWindow.dismiss();
                break;
            case R.id.like_song_love:
                QueryBuilder<LikeSongBean> builder = new QueryBuilder<>(LikeSongBean.class);
                builder.whereEquals("songId", likeSongBeen.get(pos).getSongId());
                if (LiteOrmSingleton.getInstance().getLiteOrm().query(builder).size() > 0) {
                    LiteOrmSingleton.getInstance().getLiteOrm()
                            .delete(LiteOrmSingleton.getInstance().getLiteOrm().query(builder));
                }
                bmobUser = BmobUser.getCurrentUser(context);
                if (bmobUser != null) {
                    BmobQuery<LikeSongBean> query = new BmobQuery<>();
                    query.addWhereEqualTo("songId", likeSongBeen.get(pos).getSongId());
                    query.addWhereEqualTo("userName", BmobUser.getCurrentUser(context).getUsername());
                    query.setLimit(50);
                    query.findObjects(context, new FindListener<LikeSongBean>() {
                        @Override
                        public void onSuccess(List<LikeSongBean> list) {
                            if (list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    LikeSongBean likeSongBean = list.get(i);
                                    likeSongBean.delete(context);
                                }
                            }
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                }
                likeSongBeen.remove(pos);
                likeSongAdapter.setLikeSongBeen(likeSongBeen);
                Toast.makeText(context, "已取消喜欢", Toast.LENGTH_SHORT).show();


                //发送广播刷新数据
                Intent intent = new Intent(context.getPackageName() + ".likeSongCancle");
                context.sendBroadcast(intent);
                popupWindow.dismiss();

                if (likeSongBeen.size() == 0) {
                    nothing.setVisibility(View.VISIBLE);
                }
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


        }
    }

    private void showPopupwindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.like_song_popupwindow, null, false);
        likeSongLoveIv = (ImageView) view.findViewById(R.id.like_song_love_iv);
        likeSongLoveTv = (TextView) view.findViewById(R.id.like_song_love_tv);
        view.findViewById(R.id.like_song_framelayout).setOnClickListener(this);
        view.findViewById(R.id.like_song_love).setOnClickListener(this);
        view.findViewById(R.id.like_song_share).setOnClickListener(this);
        TextView likeSongTitle = (TextView) view.findViewById(R.id.like_song_title);
        likeSongTitle.setText(likeSongBeen.get(pos).getTitle());
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        likeSongLoveIv.setImageResource(R.mipmap.ic_listmore_love_hl);
        likeSongLoveTv.setText("取消喜欢");
    }
}
