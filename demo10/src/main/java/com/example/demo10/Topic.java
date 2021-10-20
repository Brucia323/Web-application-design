package com.example.demo10;

/**
 * 用于序列化和反序列化话题对象
 *
 * @author ZZZCNY
 * @version 1.0
 * @since 2021/10/20
 */
public class Topic {
    private int topicid;
    private String title;
    private String content;

    public Topic(int topicid, String title, String content) {
        this.topicid = topicid;
        this.title = title;
        this.content = content;
    }

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
