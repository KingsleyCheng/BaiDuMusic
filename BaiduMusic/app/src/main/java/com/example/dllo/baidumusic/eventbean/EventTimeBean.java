package com.example.dllo.baidumusic.eventbean;

/**
 * Created by dllo on 16/7/3.
 */
public class EventTimeBean {
    private int currentTime, maxTime;

    public EventTimeBean(int currentTime, int maxTime) {
        this.currentTime = currentTime;
        this.maxTime = maxTime;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }
}
