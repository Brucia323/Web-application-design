package com.zcy.service;

import com.zcy.dao.MySQL;
import com.zcy.entity.Book;
import com.zcy.entity.PageBean;

import java.sql.SQLException;
import java.util.List;

public class SearchService {
    public PageBean search(String keyword, int currentPage, int pageSize) throws SQLException, ClassNotFoundException {
        MySQL mySQL = new MySQL();
        int count = mySQL.searchCount(keyword);
        int totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        List<Book> list = mySQL.search(keyword, currentPage, pageSize);
        return new PageBean(currentPage, pageSize, count, totalPage, list);
    }
}
