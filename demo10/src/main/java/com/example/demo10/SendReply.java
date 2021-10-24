package com.example.demo10;

public class SendReply {
    private int replyid;
    private String username;
    private String reply;
    private int replyNum;
    private int likes;
    private boolean sticky;
    private String time;
    private String replyName;

    public SendReply(int replyid, String username, String reply, int replyNum, int likes, boolean sticky, String time, String replyName) {
        this.replyid = replyid;
        this.username = username;
        this.reply = reply;
        this.likes = likes;
        this.sticky = sticky;
        this.time = time;
        this.replyName = replyName;
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
