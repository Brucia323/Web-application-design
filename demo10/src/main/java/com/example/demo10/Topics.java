package com.example.demo10;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 话题管理
 *
 * @author ZZZCNY
 * @version 1.1
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

    public Topics() {
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
                RandomAccessFile randomAccessFile = new RandomAccessFile("topics" + topicid + ".json", "rw");
                Gson gson = new Gson();
                Topic topic = new Topic(title, content);
                String json = gson.toJson(topic);
                randomAccessFile.write(json.getBytes(StandardCharsets.UTF_8));
                randomAccessFile.close();
            }
            resultSet.close();
        }
        preparedStatement.close();
        connection.close();
    }

    /**
     * 加载话题
     *
     * @return 所有留言
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     * @throws IOException            读写异常
     * @author ZZZCNY
     * @since 1.1
     */
    public String viewTopic() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM topics");
        ResultSet resultSet = preparedStatement.executeQuery();
        List list = new ArrayList();
        while (resultSet.next()) {
            topicid = resultSet.getInt("id");
            userid = resultSet.getInt("userid");
            Time time = resultSet.getTime("time");
            int likes = resultSet.getInt("likes");
            int reply = resultSet.getInt("reply");
            boolean sticky = resultSet.getBoolean("sticky");
            boolean essence = resultSet.getBoolean("essence");
            RandomAccessFile randomAccessFile = new RandomAccessFile("topics" + topicid + ".json", "r");
            String json = new String(randomAccessFile.readLine().getBytes(StandardCharsets.UTF_8));
            randomAccessFile.close();
            JsonReader jsonReader = new JsonReader(new StringReader(json));
            String name = "";
            while (jsonReader.hasNext()) {
                JsonToken nextToken = jsonReader.peek();
                if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {
                    jsonReader.beginObject();
                } else if (JsonToken.NAME.equals(nextToken)) {
                    name = jsonReader.nextName();
                } else if (JsonToken.STRING.equals(nextToken)) {
                    if (name == "title") {
                        title = jsonReader.nextString();
                        name = "";
                    } else if (name == "content") {
                        content = jsonReader.nextString();
                        name = "";
                    }
                } else if (JsonToken.END_OBJECT.equals(nextToken)) {
                    jsonReader.endObject();
                }
            }
            Topic topic = new Topic(topicid, userid, title, content, time, likes, reply, sticky, essence);
            list.add(topic);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        Gson gson = new Gson();
        String json1 = gson.toJson(list);
        return json1;
    }
}