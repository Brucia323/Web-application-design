package com.example.demo10;

import java.sql.Time;

/**
 * 发送话题
 *
 * @author ZZZCNY
 * @version 1.0
 * @since 2021/10/23
 */
public class SendTopic {
    private int topicid;
    private String username;
    private String title;
    private String content;
    private Time time;
    private int likes;
    private int reply;
    private boolean sticky;
    private boolean essence;

    public SendTopic(int topicid, String username, String title, String content, Time time, int likes, int reply, boolean sticky, boolean essence) {
        this.topicid = topicid;
        this.username = username;
        this.title = title;
        this.content = content;
        this.time = time;
        this.likes = likes;
        this.reply = reply;
        this.sticky = sticky;
        this.essence = essence;
    }

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
