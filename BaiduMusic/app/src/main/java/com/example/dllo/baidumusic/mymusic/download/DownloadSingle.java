package com.example.dllo.baidumusic.mymusic.download;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dllo.baidumusic.MyApp;
import com.example.dllo.baidumusic.db.LiteOrmSingleton;
import com.example.dllo.baidumusic.musicplayservice.SongPlayBean;
import com.example.dllo.baidumusic.urlvalues.URLValues;
import com.example.dllo.baidumusic.volley.MySingleton;
import com.google.gson.Gson;
import com.litesuits.orm.db.assit.QueryBuilder;

/**
 * Created by dllo on 16/7/11.
 */
public class DownloadSingle {
    private static DownloadSingle singleDownload;

    private DownloadManager downloadManager;


    private DownloadSingle() {
        downloadManager = (DownloadManager) MyApp.context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public static DownloadSingle getSingleDownload() {
        if (singleDownload == null) {
            synchronized (DownloadSingle.class) {
                if (singleDownload == null) {
                    singleDownload = new DownloadSingle();
                }
            }
        }
        return singleDownload;
    }

    public void DownLoad(String songId) {
        String url = URLValues.SONG_PLAY1 + songId + URLValues.SONG_PLAY2;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String string = s.substring(1, s.length() - 2);
                Gson gson = new Gson();
                SongPlayBean songPlayBean = gson.fromJson(string, SongPlayBean.class);
                String downUrl = songPlayBean.getBitrate().getFile_link();
                String songId = songPlayBean.getSonginfo().getSong_id();
                String title = songPlayBean.getSonginfo().getTitle();
                String author = songPlayBean.getSonginfo().getAuthor();
                QueryBuilder<DownloadBean> queryBuilder = new QueryBuilder<>(DownloadBean.class);
                queryBuilder.whereEquals("title", title);
                if (LiteOrmSingleton.getInstance().getLiteOrm().query(queryBuilder).size() != 0) {
                    Toast.makeText(MyApp.context, "已经下载过该歌曲,请勿重复下载", Toast.LENGTH_SHORT).show();
                } else {
                    // 开始下载
                    Uri resource = Uri.parse(downUrl);
                    DownloadManager.Request request = new DownloadManager.Request(resource);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                    request.setAllowedOverRoaming(false);
                    // 设置文件类型
                    MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                    String mimiString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(downUrl));
                    request.setMimeType(mimiString);
                    //在通知栏中显示
                    request.setShowRunningNotification(true);
                    request.setVisibleInDownloadsUi(true);
                    //sdcard的目录下的download文件夹
                    request.setDestinationInExternalPublicDir("/music/mp3", title + ".mp3");
                    long id = downloadManager.enqueue(request);
                    LiteOrmSingleton.getInstance().getLiteOrm().insert(new DownloadBean(title, author, songId));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        MySingleton.getInstance(MyApp.context).getRequestQueue().add(stringRequest);
    }

}
