package com.ggxueche.utils;

/**
 *
 * 描述：社区话题
 * </br>
 */
public class Topic {
    private int id;

    private String title;

    public Topic(){

    }

    public Topic(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
