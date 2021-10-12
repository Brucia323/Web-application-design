package com.example.demo5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.*;

public class New {
    private int userId; // 用户ID
    private String title;  // 标题
    private String content; // 内容

    public void setUserId(int id) {
        this.userId = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean write() throws IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HuaTi", "root", "20010323"); // 连接数据库
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO huati(title, userid, time) VALUES ('" + title + "', '" + userId + "', '" + new Timestamp(System.currentTimeMillis()) + "')"); // 新建话题
            int result = preparedStatement.executeUpdate(); // 执行插入语句
            if (result > 0) {
                preparedStatement = connection.prepareStatement("SELECT max(id) FROM huati"); // 查询话题ID
                ResultSet resultSet = preparedStatement.executeQuery(); // 执行查询语句并返回结果集
                if (resultSet.next()) {
                    int id = Integer.parseInt(resultSet.getString(1)); // 话题ID
                    try {
                        RandomAccessFile randomAccessFile = new RandomAccessFile("huati" + id + ".txt", "rw"); // 打开文件
                        try {
                            randomAccessFile.write(content.getBytes()); // 写入文件
                            randomAccessFile.close();
                            resultSet.close();
                            preparedStatement.close();
                            connection.close();
                            return true;
                        } catch (IOException e) {
                            // 写入文件出错
                            e.printStackTrace();
                            preparedStatement = connection.prepareStatement("DELETE FROM huati WHERE id = '" + id + "'"); // 删除记录
                            preparedStatement.executeUpdate();
                            try {
                                randomAccessFile.close();
                                resultSet.close();
                                preparedStatement.close();
                                connection.close();
                                return false;
                            } catch (IOException ex) {
                                e.printStackTrace();
                                randomAccessFile.close();
                                resultSet.close();
                                preparedStatement.close();
                                connection.close();
                                return false;
                            }
                        }
                    } catch (FileNotFoundException e) {
                        // 打开文件出错
                        e.printStackTrace();
                        preparedStatement = connection.prepareStatement("DELETE FROM huati WHERE id = '" + id + "'"); // 删除记录
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
