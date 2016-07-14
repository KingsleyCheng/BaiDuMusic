package com.example.dllo.baidumusic.songplaypage;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dllo on 16/7/2.
 */
public class PlayPageBean implements Parcelable {
    private String title;
    private String author;
    private String imageUrl;
    private String lrclink;

    public PlayPageBean() {
    }

    public PlayPageBean(String title, String author, String imageUrl, String lrclink) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.lrclink = lrclink;
    }

    protected PlayPageBean(Parcel in) {
        title = in.readString();
        author = in.readString();
        imageUrl = in.readString();
        lrclink = in.readString();
    }

    public static final Creator<PlayPageBean> CREATOR = new Creator<PlayPageBean>() {
        @Override
        public PlayPageBean createFromParcel(Parcel in) {
            return new PlayPageBean(in);
        }

        @Override
        public PlayPageBean[] newArray(int size) {
            return new PlayPageBean[size];
        }
    };

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLrclink() {
        return lrclink;
    }

    public void setLrclink(String lrclink) {
        this.lrclink = lrclink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(imageUrl);
        dest.writeString(lrclink);
    }
}
