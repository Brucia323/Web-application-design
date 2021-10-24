package com.example.demo10;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 话题管理
 *
 * @author ZZZCNY
 * @version 1.2
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
                topicid = resultSet.getInt(1);
                RandomAccessFile randomAccessFile = new RandomAccessFile("topics" + topicid + ".json", "rw");
                Gson gson = new Gson();
                SaveTopic topic = new SaveTopic(title, content);
                String json = gson.toJson(topic);
                randomAccessFile.write(json.getBytes());
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
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM topics ORDER BY sticky DESC, id DESC");
        ResultSet resultSet = preparedStatement.executeQuery();
        List list = new ArrayList();
        while (resultSet.next()) {
            topicid = resultSet.getInt("id");
            userid = resultSet.getInt("userid");
            User user = new User();
            String username = user.getUsername(userid);
            String time = resultSet.getString("time");
            int likes = resultSet.getInt("likes");
            int reply = resultSet.getInt("reply");
            boolean sticky = resultSet.getBoolean("sticky");
            boolean essence = resultSet.getBoolean("essence");
            RandomAccessFile randomAccessFile = new RandomAccessFile("topics" + topicid + ".json", "r");
            String json = new String(randomAccessFile.readLine().getBytes("ISO_8859_1"), "GBK");
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
                    if (name.equals("title")) {
                        title = jsonReader.nextString();
                        name = "";
                    } else if (name.equals("content")) {
                        content = jsonReader.nextString();
                        name = "";
                    }
                } else if (JsonToken.END_OBJECT.equals(nextToken)) {
                    jsonReader.endObject();
                }
            }
            SendTopic topic = new SendTopic(topicid, username, title, content, time, likes, reply, sticky, essence);
            list.add(topic);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    /**
     * 点赞
     *
     * @param userid  用户id
     * @param topicid 话题id
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     * @author ZZZcNY
     * @since 1.2
     */
    public String likes(int userid, int topicid) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM likes WHERE userid = '" + userid + "' AND topicid = '" + topicid + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            preparedStatement.executeUpdate("DELETE FROM likes WHERE topicid = '" + topicid + "' AND userid = '" + userid + "'");
            preparedStatement.executeUpdate("UPDATE topics SET likes = likes - 1 WHERE id = '" + topicid + "'");
        } else {
            preparedStatement.executeUpdate("INSERT INTO likes (userid, topicid) VALUES ('" + userid + "', '" + topicid + "')");
            preparedStatement.executeUpdate("UPDATE topics SET likes = likes + 1 WHERE id = '" + topicid + "'");
        }
        resultSet = preparedStatement.executeQuery("SELECT likes FROM topics WHERE id = '" + topicid + "'");
        resultSet.next();
        String likes = resultSet.getString(1);
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return likes;
    }

    public String loadingCommentPageTopics(int topicid) throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM topics WHERE id = '" + topicid + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            topicid = resultSet.getInt("id");
            userid = resultSet.getInt("userid");
            User user = new User();
            String username = user.getUsername(userid);
            String time = resultSet.getString("time");
            int likes = resultSet.getInt("likes");
            int reply = resultSet.getInt("reply");
            boolean sticky = resultSet.getBoolean("sticky");
            boolean essence = resultSet.getBoolean("essence");
            RandomAccessFile randomAccessFile = new RandomAccessFile("topics" + topicid + ".json", "r");
            String json = new String(randomAccessFile.readLine().getBytes("ISO_8859_1"), "GBK");
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
                    if (name.equals("title")) {
                        title = jsonReader.nextString();
                        name = "";
                    } else if (name.equals("content")) {
                        content = jsonReader.nextString();
                        name = "";
                    }
                } else if (JsonToken.END_OBJECT.equals(nextToken)) {
                    jsonReader.endObject();
                }
            }
            SendTopic topic = new SendTopic(topicid, username, title, content, time, likes, reply, sticky, essence);
            resultSet.close();
            preparedStatement.close();
            connection.close();
            Gson gson = new Gson();
            return gson.toJson(topic);
        }
        return null;
    }
}
