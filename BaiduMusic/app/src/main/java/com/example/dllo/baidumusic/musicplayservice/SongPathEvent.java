package com.example.dllo.baidumusic.musicplayservice;

/**
 * Created by dllo on 16/7/8.
 */
public class SongPathEvent {
    private String path;

    public SongPathEvent(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
