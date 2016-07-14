package com.example.dllo.baidumusic.mymusic.download;

/**
 * Created by dllo on 16/7/11.
 */
public class DownloadBean {
    private String title;
    private String author;
    private String songId;

    public DownloadBean() {
    }

    public DownloadBean(String title, String author, String songId) {
        this.title = title;
        this.author = author;
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

}
