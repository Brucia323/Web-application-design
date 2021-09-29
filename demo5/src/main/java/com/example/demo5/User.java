package com.example.demo5;

import java.sql.*;

public class User {
    private int id;
    private String name;
    private String password;
    private int isManager;

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

    public boolean login() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HuaTi", "root", "20010323");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE name='" + name + "' AND password='" + password + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString(1));
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

    public boolean enroll() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HuaTi", "root", "20010323");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (name, password, manager) VALUES ('" + name + "','" + password + "','" + isManager + "')");
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
