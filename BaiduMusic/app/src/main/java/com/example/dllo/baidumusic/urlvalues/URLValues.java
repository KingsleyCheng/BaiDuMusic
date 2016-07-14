package com.example.dllo.baidumusic.urlvalues;

import com.example.dllo.baidumusic.R;

/**
 * Created by dllo on 16/7/2.
 */
public class URLValues {

    public static final String CHARST = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billCategory&format=json&from=ios&version=5.2.1&from=ios&channel=appstore";
    public static final String CHARST_LIST1 = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=";
    public static final String CHARST_LIST2 = "&format=json&offset=0&size=50&from=ios&fields=title,song_id,author,resource_type,havehigh,is_new,has_mv_mobile,album_title,ting_uid,album_id,charge,all_rate&version=5.2.1&from=ios&channel=appstore";
    public static final String MVLIST1 = "http://tingapi.ting.baidu.com/v1/restserver/ting?" +
            "from=android&version=5.7.3.0&channel=xiaomi&operator=3&provider=11%2C12&method=" +
            "baidu.ting.mv.searchMV&format=json&order=1&page_num=";
    public static final String MVLIST2 = "&page_size=20&query=全部";
    public static final String MV_PLAY1 = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.7.3.0&channel=xiaomi&operator=3&provider=11%2C12&method=baidu.ting.mv.playMV&format=json&mv_id=";
    public static final String MV_PLAY2 = "&song_id=&definition=0";
    public static final String RECOMMEND_BANNER = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.plaza.getFocusPic&format=json&from=ios&version=5.2.3&from=ios&channel=appstore";
    public static final String RECOMMEND_SONG_MENU = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=" +
            "baidu.ting.diy.getHotGeDanAndOfficial&num=6&version=5.2.3&from=ios&channel=appstore";
    public static final String ALL_AUTHOR_BANNER = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=12&offset=0&area=0&sex=0&abc=&from=ios&version=5.2.1&from=ios&channel=appstore";
    public static final String CHN_MALE_SINGER = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=50&offset=0&area=6&sex=1&abc=%E7%83%AD%E9%97%A8&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String CHE_FEMALE_SINGER = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=50&offset=0&area=6&sex=2&abc=%E7%83%AD%E9%97%A8&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String CHE_COMBINATION = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=50&offset=0&area=6&sex=3&abc=%E7%83%AD%E9%97%A8&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String WESTERN_MALE_SINGER = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=50&offset=0&area=3&sex=1&abc=%E7%83%AD%E9%97%A8&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String WESTERN_FEMALE_SINGER = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=50&offset=0&area=3&sex=2&abc=%E7%83%AD%E9%97%A8&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String WESTERN_COMBINATION = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=50&offset=0&area=3&sex=3&abc=%E7%83%AD%E9%97%A8&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String KR_MALE_SINGER = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=50&offset=0&area=7&sex=1&abc=%E7%83%AD%E9%97%A8&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String KR_FEMALE_SINGER = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=50&offset=0&area=7&sex=2&abc=%E7%83%AD%E9%97%A8&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String KR_COMBINATION = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=50&offset=0&area=7&sex=3&abc=%E7%83%AD%E9%97%A8&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String JP_MALE_SINGER = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=50&offset=0&area=60&sex=1&abc=%E7%83%AD%E9%97%A8&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String JP_FEMALE_SINGER = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=50&offset=0&area=60&sex=2&abc=%E7%83%AD%E9%97%A8&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String JP_COMBINATION = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=50&offset=0&area=60&sex=3&abc=%E7%83%AD%E9%97%A8&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String OTHER_SINGER = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getList&format=json&order=1&limit=50&offset=0&area=5&sex=4&abc=%E7%83%AD%E9%97%A8&from=ios&version=5.2.5&from=ios&channel=appstore";
    public static final String AUTHOR_DETAIL_LIST1 = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getSongList&format=json&tinguid=";
    public static final String AUTHOR_DETAIL_LIST2 = "&artistid(null)&limits=30&order=2&offset=0&version=5.2.5&from=ios&channel=appstore";
    public static final String SONG_MENU = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.gedan&page_no=1&page_size=30&from=ios&version=5.2.3&from=ios&channel=appstore";
    public static final String SONG_MENU1="http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.gedan&page_no=";
    public static final String SONG_MENU2="&page_size=30&from=ios&version=5.2.3&from=ios&channel=appstore";
    public static final String SONG_MENU_LIST1 = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.diy.gedanInfo&from=ios&listid=";
    public static final String SONG_MENU_LIST2 = "&version=5.2.3&from=ios&channel=appstore";
    public static final String SONG_PLAY1 = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.song.play&format=json&callback=&songid=";
    public static final String SONG_PLAY2 = "&_=1413017198449";
    public static final String RADIO_PLAY1="http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.getSmartSongList&page_no=1&page_size=50&scene_id=";
    public static final String RADIO_PLAY2="&item_id=0&version=5.2.5&from=ios&channel=appstore";
    public static final String RADIO_TOTAL1="http://tingapi.ting.baidu.com/v1/restserver/ting/?method=baidu.ting.scene.getCategoryScene&category_id=";
    public static final String RADIO_TOTAL2="&version=5.2.5&from=ios&channel=appstore";




}
