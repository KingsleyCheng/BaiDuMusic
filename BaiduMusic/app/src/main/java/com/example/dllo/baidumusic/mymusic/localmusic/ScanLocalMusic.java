package com.example.dllo.baidumusic.mymusic.localmusic;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.dllo.baidumusic.db.LiteOrmSingleton;
import com.litesuits.orm.db.assit.QueryBuilder;

/**
 * Created by dllo on 16/7/8.
 */
public class ScanLocalMusic {
    public static void localMusic(Context context) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {

                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                LocalMusicBean localMusicBean = new LocalMusicBean(title, author, path);
                QueryBuilder<LocalMusicBean> builder = new QueryBuilder<>(LocalMusicBean.class);
                builder.whereEquals("path", path);
                if (LiteOrmSingleton.getInstance().getLiteOrm().query(builder).size() == 0) {
                    LiteOrmSingleton.getInstance().getLiteOrm().insert(localMusicBean);
                }
            }
        }

    }
}
