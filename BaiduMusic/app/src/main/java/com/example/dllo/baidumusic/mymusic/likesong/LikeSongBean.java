package com.example.dllo.baidumusic.mymusic.likesong;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import cn.bmob.v3.BmobObject;

/**
 * Created by dllo on 16/7/7.
 */
public class LikeSongBean extends BmobObject{
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    private String title;
    private String author;
    @Column("songId")
    private String songId;
    private String userName;

    public LikeSongBean() {
    }

    public LikeSongBean(String title, String author, String songId) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
