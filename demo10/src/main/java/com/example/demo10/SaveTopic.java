package com.example.demo10;

/**
 * 保存话题
 *
 * @author ZZZCNY
 * @version 1.2
 * @since 2021/10/20
 */
public class SaveTopic {
    private String title;
    private String content;
    
    public SaveTopic(String title, String content) {
        this.title = title;
        this.content = content;
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
