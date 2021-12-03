package com.zcy.bean;

/**
 * 文件类
 *
 * @author ZZZCNY
 */
public class File {
    /**
     * 文件id
     */
    private int fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 用户id
     */
    private String username;
    /**
     * 权限
     */
    private String authority;
    
    public int getFileId() {
        return fileId;
    }
    
    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getAuthority() {
        return authority;
    }
    
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
