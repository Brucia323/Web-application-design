package com.example.demo5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.*;

public class New {
    private int userId;
    private String title;
    private String content;

    public void setUserId(int id) {
        this.userId = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean write() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HuaTi", "root", "20010323");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO huati(title, userid, time) VALUES ('" + title + "','" + userId + "','" + new Timestamp(System.currentTimeMillis()) + "')");
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                preparedStatement = connection.prepareStatement("SELECT max(id) FROM huati");
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int id = Integer.parseInt(resultSet.getString(1));
                    try {
                        RandomAccessFile randomAccessFile = new RandomAccessFile("huati" + id + ".txt", "rw");
                        try {
                            randomAccessFile.write(content.getBytes());
                            randomAccessFile.close();
                            resultSet.close();
                            preparedStatement.close();
                            connection.close();
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                            preparedStatement = connection.prepareStatement("DELETE FROM huati WHERE id='" + id + "'");
                            preparedStatement.executeUpdate();
                            try {
                                randomAccessFile.close();
                                resultSet.close();
                                preparedStatement.close();
                                connection.close();
                                return false;
                            } catch (IOException ex) {
                                e.printStackTrace();
                                resultSet.close();
                                preparedStatement.close();
                                connection.close();
                                return false;
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        preparedStatement = connection.prepareStatement("DELETE FROM huati WHERE id='" + id + "'");
                        preparedStatement.executeUpdate();
                        resultSet.close();
                        preparedStatement.close();
                        connection.close();
                        return false;
                    }
                } else {
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                    return false;
                }
            } else {
                preparedStatement.close();
                connection.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
