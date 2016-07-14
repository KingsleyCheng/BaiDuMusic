package com.example.dllo.baidumusic.musiclibrary.mv;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dllo on 16/7/5.
 */
public class MvPlayBean {
    /**
     * error_code : 22000
     * result : {"video_info":{"video_id":"267254679","mv_id":"248773773","provider":"12","sourcepath":"http://www.yinyuetai.com/video/2609976","thumbnail":"http://qukufile2.qianqian.com/data2/pic/11766be292f486530fc63d7248fbae09/267272432/267272432.jpg","thumbnail2":"http://qukufile2.qianqian.com/data2/pic/55dc4d2f680c5f08a34bc2da0f5f9682/267272428/267272428.jpg","del_status":"0","distribution":"0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000"},"files":{"31":{"video_file_id":"267254683","video_id":"267254679","definition":"31","file_link":"http://www.yinyuetai.com/mv/video-url/2609976","file_format":"","file_extension":"mp4","file_duration":"225","file_size":"0","source_path":"http://www.yinyuetai.com/mv/video-url/2609976"}},"min_definition":"31","max_definition":"31","mv_info":{"mv_id":"248773773","all_artist_id":"15432661","title":"Superstar","aliastitle":"","subtitle":"","play_nums":"0","publishtime":"2015-09-28","del_status":"0","artist_id":"15432661","thumbnail":"http://qukufile2.qianqian.com/data2/pic/11766be292f486530fc63d7248fbae09/267272432/267272432.jpg","thumbnail2":"http://qukufile2.qianqian.com/data2/pic/55dc4d2f680c5f08a34bc2da0f5f9682/267272428/267272428.jpg","artist":"D'banj","provider":"12"}}
     */

    private int error_code;
    /**
     * video_info : {"video_id":"267254679","mv_id":"248773773","provider":"12","sourcepath":"http://www.yinyuetai.com/video/2609976","thumbnail":"http://qukufile2.qianqian.com/data2/pic/11766be292f486530fc63d7248fbae09/267272432/267272432.jpg","thumbnail2":"http://qukufile2.qianqian.com/data2/pic/55dc4d2f680c5f08a34bc2da0f5f9682/267272428/267272428.jpg","del_status":"0","distribution":"0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000"}
     * files : {"31":{"video_file_id":"267254683","video_id":"267254679","definition":"31","file_link":"http://www.yinyuetai.com/mv/video-url/2609976","file_format":"","file_extension":"mp4","file_duration":"225","file_size":"0","source_path":"http://www.yinyuetai.com/mv/video-url/2609976"}}
     * min_definition : 31
     * max_definition : 31
     * mv_info : {"mv_id":"248773773","all_artist_id":"15432661","title":"Superstar","aliastitle":"","subtitle":"","play_nums":"0","publishtime":"2015-09-28","del_status":"0","artist_id":"15432661","thumbnail":"http://qukufile2.qianqian.com/data2/pic/11766be292f486530fc63d7248fbae09/267272432/267272432.jpg","thumbnail2":"http://qukufile2.qianqian.com/data2/pic/55dc4d2f680c5f08a34bc2da0f5f9682/267272428/267272428.jpg","artist":"D'banj","provider":"12"}
     */

    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * video_id : 267254679
         * mv_id : 248773773
         * provider : 12
         * sourcepath : http://www.yinyuetai.com/video/2609976
         * thumbnail : http://qukufile2.qianqian.com/data2/pic/11766be292f486530fc63d7248fbae09/267272432/267272432.jpg
         * thumbnail2 : http://qukufile2.qianqian.com/data2/pic/55dc4d2f680c5f08a34bc2da0f5f9682/267272428/267272428.jpg
         * del_status : 0
         * distribution : 0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000
         */

        private VideoInfoBean video_info;
        /**
         * 31 : {"video_file_id":"267254683","video_id":"267254679","definition":"31","file_link":"http://www.yinyuetai.com/mv/video-url/2609976","file_format":"","file_extension":"mp4","file_duration":"225","file_size":"0","source_path":"http://www.yinyuetai.com/mv/video-url/2609976"}
         */

        private FilesBean files;
        private String min_definition;
        private String max_definition;
        /**
         * mv_id : 248773773
         * all_artist_id : 15432661
         * title : Superstar
         * aliastitle :
         * subtitle :
         * play_nums : 0
         * publishtime : 2015-09-28
         * del_status : 0
         * artist_id : 15432661
         * thumbnail : http://qukufile2.qianqian.com/data2/pic/11766be292f486530fc63d7248fbae09/267272432/267272432.jpg
         * thumbnail2 : http://qukufile2.qianqian.com/data2/pic/55dc4d2f680c5f08a34bc2da0f5f9682/267272428/267272428.jpg
         * artist : D'banj
         * provider : 12
         */

        private MvInfoBean mv_info;

        public VideoInfoBean getVideo_info() {
            return video_info;
        }

        public void setVideo_info(VideoInfoBean video_info) {
            this.video_info = video_info;
        }

        public FilesBean getFiles() {
            return files;
        }

        public void setFiles(FilesBean files) {
            this.files = files;
        }

        public String getMin_definition() {
            return min_definition;
        }

        public void setMin_definition(String min_definition) {
            this.min_definition = min_definition;
        }

        public String getMax_definition() {
            return max_definition;
        }

        public void setMax_definition(String max_definition) {
            this.max_definition = max_definition;
        }

        public MvInfoBean getMv_info() {
            return mv_info;
        }

        public void setMv_info(MvInfoBean mv_info) {
            this.mv_info = mv_info;
        }

        public static class VideoInfoBean {
            private String video_id;
            private String mv_id;
            private String provider;
            private String sourcepath;
            private String thumbnail;
            private String thumbnail2;
            private String del_status;
            private String distribution;

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public String getMv_id() {
                return mv_id;
            }

            public void setMv_id(String mv_id) {
                this.mv_id = mv_id;
            }

            public String getProvider() {
                return provider;
            }

            public void setProvider(String provider) {
                this.provider = provider;
            }

            public String getSourcepath() {
                return sourcepath;
            }

            public void setSourcepath(String sourcepath) {
                this.sourcepath = sourcepath;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getThumbnail2() {
                return thumbnail2;
            }

            public void setThumbnail2(String thumbnail2) {
                this.thumbnail2 = thumbnail2;
            }

            public String getDel_status() {
                return del_status;
            }

            public void setDel_status(String del_status) {
                this.del_status = del_status;
            }

            public String getDistribution() {
                return distribution;
            }

            public void setDistribution(String distribution) {
                this.distribution = distribution;
            }
        }

        public static class FilesBean {
            /**
             * video_file_id : 267254683
             * video_id : 267254679
             * definition : 31
             * file_link : http://www.yinyuetai.com/mv/video-url/2609976
             * file_format :
             * file_extension : mp4
             * file_duration : 225
             * file_size : 0
             * source_path : http://www.yinyuetai.com/mv/video-url/2609976
             */

            @SerializedName("31")
            private Bean31 value31;

            public Bean31 getValue31() {
                return value31;
            }

            public void setValue31(Bean31 value31) {
                this.value31 = value31;
            }

            public static class Bean31 {
                private String video_file_id;
                private String video_id;
                private String definition;
                private String file_link;
                private String file_format;
                private String file_extension;
                private String file_duration;
                private String file_size;
                private String source_path;

                public String getVideo_file_id() {
                    return video_file_id;
                }

                public void setVideo_file_id(String video_file_id) {
                    this.video_file_id = video_file_id;
                }

                public String getVideo_id() {
                    return video_id;
                }

                public void setVideo_id(String video_id) {
                    this.video_id = video_id;
                }

                public String getDefinition() {
                    return definition;
                }

                public void setDefinition(String definition) {
                    this.definition = definition;
                }

                public String getFile_link() {
                    return file_link;
                }

                public void setFile_link(String file_link) {
                    this.file_link = file_link;
                }

                public String getFile_format() {
                    return file_format;
                }

                public void setFile_format(String file_format) {
                    this.file_format = file_format;
                }

                public String getFile_extension() {
                    return file_extension;
                }

                public void setFile_extension(String file_extension) {
                    this.file_extension = file_extension;
                }

                public String getFile_duration() {
                    return file_duration;
                }

                public void setFile_duration(String file_duration) {
                    this.file_duration = file_duration;
                }

                public String getFile_size() {
                    return file_size;
                }

                public void setFile_size(String file_size) {
                    this.file_size = file_size;
                }

                public String getSource_path() {
                    return source_path;
                }

                public void setSource_path(String source_path) {
                    this.source_path = source_path;
                }
            }
        }

        public static class MvInfoBean {
            private String mv_id;
            private String all_artist_id;
            private String title;
            private String aliastitle;
            private String subtitle;
            private String play_nums;
            private String publishtime;
            private String del_status;
            private String artist_id;
            private String thumbnail;
            private String thumbnail2;
            private String artist;
            private String provider;

            public String getMv_id() {
                return mv_id;
            }

            public void setMv_id(String mv_id) {
                this.mv_id = mv_id;
            }

            public String getAll_artist_id() {
                return all_artist_id;
            }

            public void setAll_artist_id(String all_artist_id) {
                this.all_artist_id = all_artist_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAliastitle() {
                return aliastitle;
            }

            public void setAliastitle(String aliastitle) {
                this.aliastitle = aliastitle;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getPlay_nums() {
                return play_nums;
            }

            public void setPlay_nums(String play_nums) {
                this.play_nums = play_nums;
            }

            public String getPublishtime() {
                return publishtime;
            }

            public void setPublishtime(String publishtime) {
                this.publishtime = publishtime;
            }

            public String getDel_status() {
                return del_status;
            }

            public void setDel_status(String del_status) {
                this.del_status = del_status;
            }

            public String getArtist_id() {
                return artist_id;
            }

            public void setArtist_id(String artist_id) {
                this.artist_id = artist_id;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getThumbnail2() {
                return thumbnail2;
            }

            public void setThumbnail2(String thumbnail2) {
                this.thumbnail2 = thumbnail2;
            }

            public String getArtist() {
                return artist;
            }

            public void setArtist(String artist) {
                this.artist = artist;
            }

            public String getProvider() {
                return provider;
            }

            public void setProvider(String provider) {
                this.provider = provider;
            }
        }
    }
}
