package com.example.dllo.baidumusic.musicplayservice;

/**
 * Created by dllo on 16/6/27.
 */
public class SongIdEvent {
    private String songId;

    public SongIdEvent(String songId) {
        this.songId = songId;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }
}
