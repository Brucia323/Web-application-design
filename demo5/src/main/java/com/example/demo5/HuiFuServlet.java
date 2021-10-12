package com.example.demo5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "huifu", value = "/huifu")
public class HuiFuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userid")); // 用户ID
        int huaTiId = Integer.parseInt(request.getParameter("id")); // 话题ID
        String content = "";
        String huiFuId = "0";
        huiFuId = request.getParameter("huifuid"); // 回复ID，不一定有
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HuaTi", "root",
                    "20010323"); // 连接数据库
            if (huiFuId == null && !request.getParameter("huifuhuati").isEmpty()) {
                // 回复话题
                content = request.getParameter("huifuhuati"); // 内容
                PreparedStatement preparedStatement = connection
                        .prepareStatement("INSERT INTO huifu (huatiid, userid, time) VALUES ('" + huaTiId + "', '"
                                + userId + "', '" + new Timestamp(System.currentTimeMillis()) + "')"); // 插入回复记录
                int result = preparedStatement.executeUpdate(); // 执行插入语句
                if (result > 0) {
                    // 插入成功
                    preparedStatement = connection.prepareStatement("SELECT MAX(id) FROM huifu"); // 找到刚刚插入的回复的ID
                    ResultSet resultSet = preparedStatement.executeQuery(); // 执行查询语句并获取结果集
                    if (resultSet.next()) {
                        int id = Integer.parseInt(resultSet.getString(1)); // 回复ID
                        try {
                            RandomAccessFile randomAccessFile = new RandomAccessFile("huifu" + id + ".txt", "rw"); // 打开文件
                            try {
                                randomAccessFile.write(content.getBytes()); // 将内容写入文件
                                randomAccessFile.close();
                            } catch (IOException e) {
                                // 写入失败
                                e.printStackTrace();
                                preparedStatement = connection
                                        .prepareStatement("DELETE FROM huifu WHERE id = '" + id + "'");
                                preparedStatement.executeUpdate();
                                try {
                                    randomAccessFile.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        } catch (FileNotFoundException e) {
                            // 打开失败
                            e.printStackTrace();
                            preparedStatement = connection
                                    .prepareStatement("DELETE FROM huifu WHERE id = '" + id + "'");
                            preparedStatement.executeUpdate();
                        }
                        response.setHeader("refresh", "0;url=xiangxi.jsp?id=" + huaTiId + "&userid=" + userId); // 跳转回详情页
                    }
                    resultSet.close();
                }
                preparedStatement.close();
                PreparedStatement preparedStatement1 = connection
                        .prepareStatement("UPDATE huati SET huifu = huifu + 1 WHERE id = '" + huaTiId + "'");
                preparedStatement1.executeUpdate();
                preparedStatement1.close();
            } else if (huiFuId != null && !request.getParameter("huifupinglun").isEmpty()) {
                // 回复话题下的回复
                content = request.getParameter("huifupinglun");
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO huifu (huatiid, userid, time, huifuid) VALUES ('" + huaTiId + "', '" + userId
                                + "', '" + new Timestamp(System.currentTimeMillis()) + "', '" + huiFuId + "')"); // 插入回复记录
                int result = preparedStatement.executeUpdate(); // 执行插入语句
                if (result > 0) {
                    // 插入成功
                    preparedStatement = connection.prepareStatement("SELECT MAX(id) FROM huifu"); // 找到刚刚插入的回复的ID
                    ResultSet resultSet = preparedStatement.executeQuery(); // 执行查询语句并获取结果集
                    if (resultSet.next()) {
                        int id = Integer.parseInt(resultSet.getString(1)); // 回复ID
                        try {
                            RandomAccessFile randomAccessFile = new RandomAccessFile("huifu" + id + ".txt", "rw"); // 打开文件
                            try {
                                randomAccessFile.write(content.getBytes()); // 将内容写入文件
                                randomAccessFile.close();
                            } catch (IOException e) {
                                // 写入失败
                                e.printStackTrace();
                                preparedStatement = connection
                                        .prepareStatement("DELETE FROM huifu WHERE id = '" + id + "'");
                                preparedStatement.executeUpdate();
                                try {
                                    randomAccessFile.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        } catch (FileNotFoundException e) {
                            // 打开失败
                            e.printStackTrace();
                            preparedStatement = connection
                                    .prepareStatement("DELETE FROM huifu WHERE id = '" + id + "'");
                            preparedStatement.executeUpdate();
                        }
                        response.setHeader("refresh",
                                "0;url=xiangxi.jsp?id=" + huaTiId + "&userid=" + userId + "#" + huiFuId); // 跳转回详情页对应回复处
                    }
                    resultSet.close();
                }
                preparedStatement.close();
                PreparedStatement preparedStatement1 = connection
                        .prepareStatement("UPDATE huati SET huifu = huifu + 1 WHERE id = '" + huaTiId + "'");
                preparedStatement1.executeUpdate();
                preparedStatement1.close();
            } else {
                response.setHeader("refresh", "0;url=xiangxi.jsp?id=" + huaTiId + "&userid=" + userId); // 跳转回详情页
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
