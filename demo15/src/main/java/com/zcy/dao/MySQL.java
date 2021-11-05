package com.zcy.dao;

import com.zcy.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 连接数据库所需的数据
 *
 * @author ZZZCNY
 * @version 1.1
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
    public List<Book> findBookPage(int currentPage, int pageSize) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book LIMIT ?, ?");
        preparedStatement.setInt(1, (currentPage - 1) * pageSize);
        preparedStatement.setInt(2, pageSize);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Book> list = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setBookId(resultSet.getInt("id"));
            book.setBookName(resultSet.getString("name"));
            book.setBookAuthor(resultSet.getString("author"));
            book.setBookPublishTime(resultSet.getString("year"));
            book.setBookPublish(resultSet.getString("publisher"));
            book.setBookPrice(resultSet.getDouble("price"));
            book.setBookNum(resultSet.getInt("available"));
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
    public int getBookCount() throws ClassNotFoundException, SQLException {
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
    
    /**
     * 预定
     *
     * @param bookId 图书ID
     * @throws ClassNotFoundException 驱动程序查找异常
     * @throws SQLException           数据库异常
     * @since 1.1
     */
    public void reserve(int bookId) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE book SET available=available-1 WHERE id=?");
        preparedStatement.setInt(1, bookId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
    
    /**
     * 搜索图书
     *
     * @param bookName    书名
     * @param currentPage 当前页码
     * @param pageSize    每页的数量大小
     * @return 书的列表
     * @throws ClassNotFoundException 驱动程序查找异常
     * @throws SQLException           数据库异常
     * @since 1.1
     */
    public List<Book> search(String bookName, int currentPage, int pageSize) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book WHERE name LIKE ? LIMIT" +
                " ?, ?");
        preparedStatement.setString(1, "%" + bookName + "%");
        preparedStatement.setInt(2, (currentPage - 1) * pageSize);
        preparedStatement.setInt(3, pageSize);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Book> list = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setBookId(resultSet.getInt("id"));
            book.setBookName(resultSet.getString("name"));
            book.setBookAuthor(resultSet.getString("author"));
            book.setBookPublishTime(resultSet.getString("year"));
            book.setBookPublish(resultSet.getString("publisher"));
            book.setBookPrice(resultSet.getDouble("price"));
            book.setBookNum(resultSet.getInt("available"));
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
     * @param bookName 书名
     * @return 数量
     * @throws ClassNotFoundException 驱动程序查找异常
     * @throws SQLException           数据库异常
     * @since 1.1
     */
    public int searchCount(String bookName) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM book WHERE name LIKE ?");
        preparedStatement.setString(1, "%" + bookName + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return count;
    }
}
