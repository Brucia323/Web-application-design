package com.example.demo10;

/**
 * 发送评论
 *
 * @author ZZZCNY
 * @version 1.0
 * @since 2021/10/24
 */
public class SendReply {
    private int replyid;
    private int topicid;
    private String username;
    private String reply;
    private int replyNum;
    private int likes;
    private boolean sticky;
    private String time;
    private String replyName;
    private int topicReplyNum;
    private int lastReplyNum;

    public SendReply(int replyid, int topicid, String username, String reply, int replyNum, int likes, boolean sticky, String time, String replyName, int topicReplyNum, int lastReplyNum) {
        this.replyid = replyid;
        this.topicid = topicid;
        this.username = username;
        this.reply = reply;
        this.replyNum = replyNum;
        this.likes = likes;
        this.sticky = sticky;
        this.time = time;
        this.replyName = replyName;
        this.topicReplyNum = topicReplyNum;
        this.lastReplyNum = lastReplyNum;
    }

    public SendReply(int replyid, int topicid, String username, String reply, int replyNum, int likes, boolean sticky, String time, String replyName, int topicReplyNum) {
        this.replyid = replyid;
        this.topicid = topicid;
        this.username = username;
        this.reply = reply;
        this.replyNum = replyNum;
        this.likes = likes;
        this.sticky = sticky;
        this.time = time;
        this.replyName = replyName;
        this.topicReplyNum = topicReplyNum;
    }

    public SendReply(int replyid, int topicid, String username, String reply, int replyNum, int likes, boolean sticky, String time, String replyName) {
        this.replyid = replyid;
        this.topicid = topicid;
        this.username = username;
        this.reply = reply;
        this.replyNum = replyNum;
        this.likes = likes;
        this.sticky = sticky;
        this.time = time;
        this.replyName = replyName;
    }

    public int getLastReplyNum() {
        return lastReplyNum;
    }

    public void setLastReplyNum(int lastReplyNum) {
        this.lastReplyNum = lastReplyNum;
    }

    public int getTopicReplyNum() {
        return topicReplyNum;
    }

    public void setTopicReplyNum(int topicReplyNum) {
        this.topicReplyNum = topicReplyNum;
    }

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public boolean isSticky() {
        return sticky;
    }

    public int getReplyid() {
        return replyid;
    }

    public void setReplyid(int replyid) {
        this.replyid = replyid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean getSticky() {
        return sticky;
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }
}
