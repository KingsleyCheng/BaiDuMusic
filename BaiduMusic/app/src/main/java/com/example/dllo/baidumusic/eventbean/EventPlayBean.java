package com.example.dllo.baidumusic.eventbean;

/**
 * Created by dllo on 16/7/3.
 */
public class EventPlayBean {
    private boolean isPlay;

    public EventPlayBean(boolean isPlay) {
        this.isPlay = isPlay;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }
}
