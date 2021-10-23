package com.example.demo10;

import java.sql.*;

/**
 * 处理用户的登录和注册
 *
 * @author ZZZCNY
 * @version 1.2
 * @since 2021/10/20
 */
public class User {
    private String username;
    private String password;
    private boolean administrator;
    private int id;

    public User() {
    }

    public User(String username, boolean administrator, int id) {
        this.username = username;
        this.administrator = administrator;
        this.id = id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    /**
     * 获取用户名
     *
     * @param userId 用户id
     * @return 用户名
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     * @author ZZZCNY
     * @since 1.2
     */
    public String getUsername(int userId) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM user WHERE id = '" + userId + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("username");
        }
        return null;
    }

    public int getId() {
        return id;
    }

    /**
     * 登录
     *
     * @return 登录成功返回true, 登录失败返回false
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
            administrator = resultSet.getString("administrator").equals("1");
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
     * @return 注册成功返回ture, 注册失败返回false
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
     * @return 有重复返回true, 没有重复返回false
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
