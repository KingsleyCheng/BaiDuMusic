package com.example.dllo.baidumusic.mymusic.localmusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseFragment;
import com.example.dllo.baidumusic.db.LiteOrmSingleton;
import com.example.dllo.baidumusic.mymusic.likesong.LikeSongBean;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by dllo on 16/7/7.
 */
public class LocalMusicFragment extends BaseFragment implements View.OnClickListener {
    private LocalMusicAdapter localMusicAdapter;
    private ListView listView;
    private ImageView localMusicReturn;
    private List<LocalMusicBean> localMusicBeen;
    private PopupWindow popupWindow;
    private int pos;
    private ImageView likeSongLoveIv, likeSongDownloadIv;
    private TextView likeSongLoveTv, likeSongDownloadTv;
    private TextView nothing;
    private AddDownloadBroadcast addDownloadBroadcast;

    @Override
    public int setLayout() {
        return R.layout.fragment_loca_music;
    }

    @Override
    protected void intiView(View view) {
        listView = (ListView) view.findViewById(R.id.local_music_list_view);
        localMusicReturn = (ImageView) view.findViewById(R.id.local_music_return);
        nothing = (TextView) view.findViewById(R.id.nothing);
        localMusicReturn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        addDownloadBroadcast =new AddDownloadBroadcast();
        IntentFilter filter=new IntentFilter();
        filter.addAction(context.getPackageName() + ".localMusicChange");
        context.registerReceiver(addDownloadBroadcast,filter);

        localMusicAdapter = new LocalMusicAdapter(context);
        localMusicBeen = new ArrayList<>();
        if (LiteOrmSingleton.getInstance().getLiteOrm().query(LocalMusicBean.class).size() != 0) {
            for (LocalMusicBean musicBean : LiteOrmSingleton.getInstance().getLiteOrm().query(LocalMusicBean.class)) {
                localMusicBeen.add(musicBean);
            }
        } else {
            nothing.setVisibility(View.VISIBLE);
        }
        localMusicAdapter.setLocalMusicBeen(localMusicBeen);
        listView.setAdapter(localMusicAdapter);

        localMusicAdapter.setLocalMusicOnClickListener(new LocalMusicOnClickListener() {
            @Override
            public void LocalMusicOnClick(int position) {
                pos = position;
                popupWindow = new PopupWindow();
                if (popupWindow == null || !popupWindow.isShowing()) {
                    showPopupwindow();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                EventBus.getDefault().post(new SongPathEvent(localMusicBeen.get(position).getPath()));
//                EventBus.getDefault().post(new IndexEvent(position));
//                PlayingListManager.getInstance().getPlayingListBeen().clear();
//                for (LocalMusicBean localBean : localMusicBeen) {
//                    PlayingListManager.getInstance().getPlayingListBeen()
//                            .add(new PlayingListBean(localBean.getTitle(), localBean.getAuthor(), localBean.getPath()));
//                }
            }
        });
    }


    private void showPopupwindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.like_song_popupwindow, null, false);
        likeSongLoveIv = (ImageView) view.findViewById(R.id.like_song_love_iv);
        likeSongLoveTv = (TextView) view.findViewById(R.id.like_song_love_tv);
        likeSongDownloadIv = (ImageView) view.findViewById(R.id.like_song_download_iv);
        likeSongDownloadTv = (TextView) view.findViewById(R.id.like_song_download_tv);
        likeSongDownloadIv.setImageResource(R.mipmap.ic_listmore_remove_normal);
        likeSongDownloadTv.setText("删除");
        view.findViewById(R.id.like_song_framelayout).setOnClickListener(this);
        view.findViewById(R.id.like_song_love).setOnClickListener(this);
        view.findViewById(R.id.like_song_download).setOnClickListener(this);
        view.findViewById(R.id.like_song_share).setOnClickListener(this);
        TextView likeSongTitle = (TextView) view.findViewById(R.id.like_song_title);
        likeSongTitle.setText(localMusicBeen.get(pos).getTitle());
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        QueryBuilder<LikeSongBean> builder = new QueryBuilder<>(LikeSongBean.class);
        builder.whereEquals("songId", localMusicBeen.get(pos).getPath());
        if (LiteOrmSingleton.getInstance().getLiteOrm().query(builder).size() == 0) {
            likeSongLoveIv.setImageResource(R.mipmap.ic_listmore_love_normal);
            likeSongLoveTv.setText("喜欢");
        } else {
            likeSongLoveIv.setImageResource(R.mipmap.ic_listmore_love_hl);
            likeSongLoveTv.setText("取消喜欢");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.like_song_framelayout:
                popupWindow.dismiss();
                break;
            case R.id.like_song_love:
                QueryBuilder<LikeSongBean> builder = new QueryBuilder<>(LikeSongBean.class);
                builder.whereEquals("songId", localMusicBeen.get(pos).getPath());
                if (LiteOrmSingleton.getInstance().getLiteOrm().query(builder).size() == 0) {
                    LiteOrmSingleton.getInstance().getLiteOrm().insert(
                            new LikeSongBean(localMusicBeen.get(pos).getTitle(),
                                    localMusicBeen.get(pos).getAuthor(),
                                    localMusicBeen.get(pos).getPath()));
                    Toast.makeText(context, "已添加到我喜欢的单曲", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                } else {
                    LiteOrmSingleton.getInstance().getLiteOrm()
                            .delete(LiteOrmSingleton.getInstance().getLiteOrm().query(builder));
                    Toast.makeText(context, "已取消喜欢", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                }
                //发送广播刷新数据
                Intent intent = new Intent(context.getPackageName() + ".likeSongAdd");
                context.sendBroadcast(intent);
                break;
            case R.id.local_music_return:
                getFragmentManager().popBackStack();
                break;
            case R.id.like_song_download:
                LiteOrmSingleton.getInstance().getLiteOrm().delete(localMusicBeen.get(pos));
                localMusicBeen = LiteOrmSingleton.getInstance().getLiteOrm().query(LocalMusicBean.class);
                localMusicAdapter.setLocalMusicBeen(localMusicBeen);
                Intent intent1 = new Intent(context.getPackageName() + ".localMusicRemove");
                context.sendBroadcast(intent1);
                popupWindow.dismiss();
                Toast.makeText(context, "移除列表", Toast.LENGTH_SHORT).show();
                if (LiteOrmSingleton.getInstance().getLiteOrm().query(LocalMusicBean.class).size() == 0) {
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
                break;

        }
    }
    class AddDownloadBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ScanLocalMusic.localMusic(context);
        }
    }

}
