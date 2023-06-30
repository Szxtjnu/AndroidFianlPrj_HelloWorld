package com.andorid.finalprj.domain;

import java.util.List;

public class CourseBean {
    /**
     * Title : 新品  Java 应用安全性必知必会
     * URL : https://juejin.cn/book/7226982735611428924?utm_source=course_list
     * pic_url : https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/686623b9c01e493d9476c2c58b69d1f3~tplv-k3u1fbpfcp-no-mark:420:420:300:420.awebp?
     * discribe : 从 0 到 1 手把手教你构建一套强大的安全体系
     * user_pic : https://p3-passport.byteimg.com/img/user-avatar/b2d44e9baed8fb6dd2718325ad3a40a0~100x100.awebp
     * user_name : 天涯兰
     * price : ¥19.95
     */

    private String Title;
    private String URL;
    private String pic_url;
    private String discribe;
    private String user_pic;
    private String user_name;
    private String price;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getDiscribe() {
        return discribe;
    }

    public void setDiscribe(String discribe) {
        this.discribe = discribe;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
