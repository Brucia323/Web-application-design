package com.example.demo10;

import java.sql.Time;

/**
 * 用于序列化和反序列化话题对象
 *
 * @author ZZZCNY
 * @version 1.1
 * @since 2021/10/20
 */
public class Topic {
    private int topicid;
    private int userid;
    private String title;
    private String content;
    private Time time;
    private int likes;
    private int reply;
    private boolean sticky;
    private boolean essence;

    public Topic(int topicid, int userid, String title, String content, Time time, int likes, int reply, boolean sticky, boolean essence) {
        this.topicid = topicid;
        this.userid = userid;
        this.title = title;
        this.content = content;
        this.time = time;
        this.likes = likes;
        this.reply = reply;
        this.sticky = sticky;
        this.essence = essence;
    }

    public Topic(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public boolean isSticky() {
        return sticky;
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    public boolean isEssence() {
        return essence;
    }

    public void setEssence(boolean essence) {
        this.essence = essence;
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
