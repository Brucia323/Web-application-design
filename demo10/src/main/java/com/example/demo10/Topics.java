package com.example.demo10;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/**
 * 话题管理
 *
 * @author ZZZCNY
 * @version 1.0
 * @since 2021/10/20
 */
public class Topics {
    private int userid;
    private String title;
    private String content;
    private int topicid;

    public Topics(int userid, String title, String content) {
        this.userid = userid;
        this.title = title;
        this.content = content;
    }

    /**
     * 新建话题
     *
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     * @throws IOException            读写异常
     * @author ZZZCNY
     * @since 1.0
     */
    public void newTopic() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO topics (userid) VALUES ('" + userid + "')");
        int result = preparedStatement.executeUpdate();
        if (result > 0) {
            preparedStatement = connection.prepareStatement("SELECT MAX(id) FROM topics");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                topicid = resultSet.getInt("id");
                RandomAccessFile randomAccessFile = new RandomAccessFile("topics.json", "rw");
                Gson gson = new Gson();
                Topic topic = new Topic(topicid, title, content);
                String json = gson.toJson(topic);
                randomAccessFile.seek(randomAccessFile.length());
                randomAccessFile.write(json.getBytes(StandardCharsets.UTF_8));
                randomAccessFile.close();
            }
        }
    }
}
