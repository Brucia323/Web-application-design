package com.zcy.entity;

/**
 * 书
 *
 * @author ZZZCNY
 * @version 1.0
 * @since 2021/11/4
 */
public class Book {
    private int bookId;
    private String bookName;
    private String bookAuthor;
    private String bookPublishTime;
    private String bookPublish;
    private double bookPrice;
    private int bookNum;
    
    public int getBookId() {
        return bookId;
    }
    
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    public String getBookName() {
        return bookName;
    }
    
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
    public String getBookAuthor() {
        return bookAuthor;
    }
    
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
    
    public String getBookPublishTime() {
        return bookPublishTime;
    }
    
    public void setBookPublishTime(String bookPublishTime) {
        this.bookPublishTime = bookPublishTime;
    }
    
    public String getBookPublish() {
        return bookPublish;
    }
    
    public void setBookPublish(String bookPublish) {
        this.bookPublish = bookPublish;
    }
    
    public double getBookPrice() {
        return bookPrice;
    }
    
    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }
    
    public int getBookNum() {
        return bookNum;
    }
    
    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }
}
