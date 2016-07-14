package com.example.dllo.baidumusic.musiclibrary.recommend;

import java.util.List;

/**
 * Created by dllo on 16/6/23.
 */
public class RecommendViewBean {
    /**
     * error_code : 22000
     * content : {"title":"热门歌单","list":[{"listid":"5868","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_5cb8e6df12f2aea7965a6883d0208ac5.jpg","listenum":"87601","collectnum":"894","title":"邂逅一首小酒馆里的爵士乐","tag":"爵士,蓝调,浪漫,美好","type":"gedan"},{"listid":"5535","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_e3d700e5bd6a05f58d3023834303878c.jpg","listenum":"86613","collectnum":"717","title":"你的心是一座孤独的岛","tag":"华语,好听,伤感","type":"gedan"},{"listid":"6727","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_732a7dc8bfbf4d15fd654378f053fa85.jpg","listenum":"21610","collectnum":"359","title":"耳边歌声，似是故人来","tag":"百度音乐人,原创,古风,温柔,抒情","type":"gedan"},{"listid":"3305","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_df413003f9318d284a3bcda0ad946c5b.jpg","listenum":"102725","collectnum":"704","title":"粤语抒情,回忆时光","tag":"华语,粤语,经典老歌","type":"gedan"},{"listid":"4861","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_ca8b67bc9c48490c8eca3defa876b7ec.jpg","listenum":"310899","collectnum":"1990","title":"停不下来的欧美流行","tag":"欧美,金曲,乡村,经典","type":"gedan"},{"listid":"6721","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_8d2ba6ff02d3d985fedb20d0308f0689.jpg","listenum":"13754","collectnum":"468","title":"逗比青年欢乐多","tag":"开心,好听,舒服,搞笑","type":"gedan"}]}
     */

    private int error_code;
    /**
     * title : 热门歌单
     * list : [{"listid":"5868","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_5cb8e6df12f2aea7965a6883d0208ac5.jpg","listenum":"87601","collectnum":"894","title":"邂逅一首小酒馆里的爵士乐","tag":"爵士,蓝调,浪漫,美好","type":"gedan"},{"listid":"5535","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_e3d700e5bd6a05f58d3023834303878c.jpg","listenum":"86613","collectnum":"717","title":"你的心是一座孤独的岛","tag":"华语,好听,伤感","type":"gedan"},{"listid":"6727","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_732a7dc8bfbf4d15fd654378f053fa85.jpg","listenum":"21610","collectnum":"359","title":"耳边歌声，似是故人来","tag":"百度音乐人,原创,古风,温柔,抒情","type":"gedan"},{"listid":"3305","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_df413003f9318d284a3bcda0ad946c5b.jpg","listenum":"102725","collectnum":"704","title":"粤语抒情,回忆时光","tag":"华语,粤语,经典老歌","type":"gedan"},{"listid":"4861","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_ca8b67bc9c48490c8eca3defa876b7ec.jpg","listenum":"310899","collectnum":"1990","title":"停不下来的欧美流行","tag":"欧美,金曲,乡村,经典","type":"gedan"},{"listid":"6721","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_8d2ba6ff02d3d985fedb20d0308f0689.jpg","listenum":"13754","collectnum":"468","title":"逗比青年欢乐多","tag":"开心,好听,舒服,搞笑","type":"gedan"}]
     */

    private ContentBean content;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        private String title;
        /**
         * listid : 5868
         * pic : http://business.cdn.qianqian.com/qianqian/pic/bos_client_5cb8e6df12f2aea7965a6883d0208ac5.jpg
         * listenum : 87601
         * collectnum : 894
         * title : 邂逅一首小酒馆里的爵士乐
         * tag : 爵士,蓝调,浪漫,美好
         * type : gedan
         */

        private List<ListBean> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String listid;
            private String pic;
            private String listenum;
            private String collectnum;
            private String title;
            private String tag;
            private String type;

            public String getListid() {
                return listid;
            }

            public void setListid(String listid) {
                this.listid = listid;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getListenum() {
                return listenum;
            }

            public void setListenum(String listenum) {
                this.listenum = listenum;
            }

            public String getCollectnum() {
                return collectnum;
            }

            public void setCollectnum(String collectnum) {
                this.collectnum = collectnum;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
