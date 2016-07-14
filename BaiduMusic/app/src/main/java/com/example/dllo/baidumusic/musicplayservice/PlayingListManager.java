package com.example.dllo.baidumusic.musicplayservice;

import com.example.dllo.baidumusic.songplaypage.playinglist.PlayingListBean;

import java.util.ArrayList;

/**
 * Created by dllo on 16/7/3.
 */
public class PlayingListManager {
    private ArrayList<PlayingListBean> playingListBeen;
    private static PlayingListManager playingListManager;

    public ArrayList<PlayingListBean> getPlayingListBeen() {
        return playingListBeen;
    }

    private PlayingListManager() {
        playingListBeen = new ArrayList<>();
    }

    public static PlayingListManager getInstance() {
        if (playingListManager == null) {
            synchronized (PlayingListManager.class) {
                if (playingListManager == null) {
                    playingListManager = new PlayingListManager();
                }
            }
        }
        return playingListManager;
    }
}
