package com.yida.solr.book.examples.ch12.customsort;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by Lanxiaowei
 */
public class Weibo {
    @Field
    private Integer id;

    @Field
    private String title;

    private Integer thumbsUp;

    private Integer thumbsDown;

    private Integer totalvotes;

    public Weibo() {}

    public Weibo(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(Integer thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public Integer getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(Integer thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public Integer getTotalvotes() {
        return totalvotes;
    }

    public void setTotalvotes(Integer totalvotes) {
        this.totalvotes = totalvotes;
    }
}
