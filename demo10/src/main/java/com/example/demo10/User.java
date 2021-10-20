package com.example.demo10;

import java.sql.*;

/**
 * 处理用户的登录和注册
 *
 * @author ZZZCNY
 * @version 1.0
 * @since 2021/10/20
 */
public class User {
    private final String username;
    private String password;
    private boolean administrator;
    private int id;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    /**
     * 登录
     *
     * @return boolean 登录成功返回true, 登录失败返回false
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     * @author ZZZCNY
     * @since 1.0
     */
    public boolean login() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            administrator = Boolean.parseBoolean(resultSet.getString("administrator"));
            id = Integer.parseInt(resultSet.getString("id"));
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return true;
        } else {
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return false;
        }
    }

    /**
     * 注册
     *
     * @return boolean 注册成功返回ture, 注册失败返回false
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     * @author ZZZCNY
     * @since 1.0
     */
    public boolean register() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (username, password) VALUES ('" + username + "', '" + password + "')");
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return result > 0;
    }

    public boolean getAdministrator() {
        return administrator;
    }

    /**
     * 检查用户名是否重复
     *
     * @return boolean 有重复返回true, 没有重复返回false
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     * @author ZZZCNY
     * @since 1.0
     */
    public boolean checkUsername() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username = '" + username + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return true;
        } else {
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return false;
        }
    }
}
