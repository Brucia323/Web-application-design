package com.zcy.service;

import com.zcy.dao.MySQL;
import com.zcy.entity.Book;
import com.zcy.entity.PageBean;

import java.sql.SQLException;
import java.util.List;

public class BookService {
    public PageBean findByPage(int currentPage, int pageSize) throws SQLException, ClassNotFoundException {
//        MySQL.getInstance();
        MySQL mySQL = new MySQL();
//        int count = MySQL.getBookCount();
        int count = mySQL.getBookCount();
        int totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
//        List<Book> list = MySQL.findBookPage(currentPage, pageSize);
        List<Book> list = mySQL.findBookPage(currentPage, pageSize);
        PageBean pageBean = new PageBean(currentPage, pageSize, count, totalPage, list);
        return pageBean;
    }
}
