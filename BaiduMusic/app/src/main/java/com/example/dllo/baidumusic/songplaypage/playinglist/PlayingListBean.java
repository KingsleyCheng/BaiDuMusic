package com.example.dllo.baidumusic.songplaypage.playinglist;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by dllo on 16/6/29.
 */
public class PlayingListBean {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    private String title;
    private String author;
    private String songId;

    public PlayingListBean() {
    }

    public PlayingListBean(String title, String author, String songId) {
        this.title = title;
        this.author = author;
        this.songId = songId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
