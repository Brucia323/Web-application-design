package com.zcy.dao;

import com.zcy.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 连接数据库所需的数据
 *
 * @author ZZZCNY
 * @version 1.0
 * @since 2021/11/4
 */
public class MySQL {
    private static final String url = "jdbc:mysql://localhost:3306/demo15";
    private static final String user = "root";
    private static final String password = "20010323";
    
    /**
     * 从数据库读取需要的书的信息
     *
     * @param currentPage 当前页码
     * @param pageSize    每页的数量大小
     * @return 书的列表
     * @throws ClassNotFoundException 驱动程序查找异常
     * @throws SQLException           数据库异常
     * @since 1.0
     */
    public List<Book> findBook(int currentPage, int pageSize) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book LIMIT ?, ?");
        preparedStatement.setInt(1, (currentPage - 1) * pageSize);
        preparedStatement.setInt(2, pageSize);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Book> list = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getInt("id"));
            book.setName(resultSet.getString("name"));
            book.setAuthor(resultSet.getString("author"));
            book.setYear(resultSet.getString("year"));
            book.setPublisher(resultSet.getString("publisher"));
            book.setPrice(resultSet.getDouble("price"));
            book.setAvailable(resultSet.getInt("available"));
            list.add(book);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return list;
    }
    
    /**
     * 计算图书数量
     *
     * @return 图书数量
     * @throws ClassNotFoundException 驱动程序查找异常
     * @throws SQLException           数据库异常
     * @since 1.0
     */
    public int count() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM book");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return count;
    }
    
}
