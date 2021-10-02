package com.example.demo5;

import java.sql.*;

public class User {
    private int id; // 用户ID
    private String name; // 用户名
    private final String password; // 密码
    private int isManager; // 管理员标识

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void setManager(int manager) {
        isManager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 登录
     */
    public boolean login() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HuaTi", "root", "20010323"); // 连接数据库
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE name = '" + name + "' AND password = '" + password + "'"); // 查询对应用户名和密码
            ResultSet resultSet = preparedStatement.executeQuery(); // 执行查询语句并返回结果集
            if (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString(1)); // 记录用户ID
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 注册
     */
    public boolean enroll() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HuaTi", "root", "20010323"); // 连接数据库
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (name, password, manager) VALUES ('" + name + "', '" + password + "', '" + isManager + "')"); // 插入用户记录
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getId() {
        return id;
    }
}
