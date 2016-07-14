package com.example.dllo.baidumusic.mymusic.recentplay;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by dllo on 16/7/6.
 */
public class RecentPlayBean {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    private String title;
    private String author;
    @Column("songId")
    private String songId;

    public RecentPlayBean() {
    }

    public RecentPlayBean(String title, String author, String songId) {
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
