package com.zcy.bean;

/**
 * 用户类
 *
 * @author ZZZCNY
 */
public class User {
    /**
     * 用户id
     */
    private int userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
