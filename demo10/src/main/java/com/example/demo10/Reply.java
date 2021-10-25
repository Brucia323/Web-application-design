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
 * 回复管理
 *
 * @author ZZZCNY
 * @version 1.1
 * @since 2021/10/24
 */
public class Reply {
    private int userid;
    private int topicid;
    private String reply;
    private int replyid;

    public Reply(int userid, int topicid, String reply, int replyid) {
        this.userid = userid;
        this.topicid = topicid;
        this.reply = reply;
        this.replyid = replyid;
    }

    public Reply() {
    }

    /**
     * 评论
     *
     * @return 新增的评论
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     * @throws IOException            读写异常
     * @author ZZZCNY
     * @since 1.0
     */
    public String reply() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reply (topicid, userid, replyid) VALUES ('" + topicid + "', '" + userid + "', '" + replyid + "')");
        int result = preparedStatement.executeUpdate();
        ResultSet resultSet;
        if (result > 0) {
            preparedStatement = connection.prepareStatement("SELECT MAX(id) FROM reply");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                replyid = resultSet.getInt(1);
                RandomAccessFile randomAccessFile = new RandomAccessFile("reply" + replyid + ".json", "rw");
                Gson gson = new Gson();
                SavaReply reply1 = new SavaReply(reply);
                String json = gson.toJson(reply1);
                randomAccessFile.write(json.getBytes());
                randomAccessFile.close();
                preparedStatement.executeUpdate("UPDATE topics SET reply = reply + 1 WHERE id = '" + topicid + "'");
            }
        }
        User user = new User();
        String username = user.getUsername(userid);
        resultSet = preparedStatement.executeQuery("SELECT time FROM reply WHERE id = '" + replyid + "'");
        resultSet.next();
        String time = resultSet.getString(1);
        resultSet = preparedStatement.executeQuery("SELECT replyid FROM reply WHERE id = '" + replyid + "'");
        resultSet.next();
        int replyid1 = resultSet.getInt("replyid");
        String replyName;
        SendReply reply2;
        if (replyid1 == 0) {
            replyName = "";
            reply2 = new SendReply(replyid, topicid, username, reply, 0, 0, false, time, replyName, getTopicReplyNum(topicid), 0);
        } else {
            setReplyNum(replyid1);
            replyName = getReplyName(replyid1);
            reply2 = new SendReply(replyid, topicid, username, reply, 0, 0, false, time, replyName, getTopicReplyNum(topicid), getReplyNum(replyid1));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        Gson gson = new Gson();
        return gson.toJson(reply2);
    }

    /**
     * 加载评论
     *
     * @param topicid 话题ID
     * @return 评论内容
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     * @throws IOException            读写异常
     * @author ZZZCNY
     * @since 1.0
     */
    public String viewReply(int topicid) throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reply WHERE topicid = '" + topicid + "' ORDER BY sticky DESC, id");
        ResultSet resultSet = preparedStatement.executeQuery();
        List list = new ArrayList();
        while (resultSet.next()) {
            replyid = resultSet.getInt("id");
            userid = resultSet.getInt("userid");
            User user = new User();
            String username = user.getUsername(userid);
            int replyNum = resultSet.getInt("reply");
            String time = resultSet.getString("time");
            int likes = resultSet.getInt("likes");
            int replyid1 = resultSet.getInt("replyid");
            boolean sticky = resultSet.getBoolean("sticky");
            String replyName;
            if (replyid1 == 0) {
                replyName = "";
            } else {
                replyName = getReplyName(replyid1);
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile("reply" + replyid + ".json", "r");
            String json = new String(randomAccessFile.readLine().getBytes("ISO_8859_1"), "GBK");
            randomAccessFile.close();
            JsonReader jsonReader = new JsonReader(new StringReader(json));
            while (jsonReader.hasNext()) {
                JsonToken nextToken = jsonReader.peek();
                if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {
                    jsonReader.beginObject();
                } else if (JsonToken.NAME.equals(nextToken)) {
                    jsonReader.nextName();
                } else if (JsonToken.STRING.equals(nextToken)) {
                    reply = jsonReader.nextString();
                } else if (JsonToken.END_OBJECT.equals(nextToken)) {
                    jsonReader.endObject();
                }
            }
            SendReply reply1 = new SendReply(replyid, topicid, username, reply, replyNum, likes, sticky, time, replyName);
            list.add(reply1);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    /**
     * 获取被回复的评论的用户名
     *
     * @param replyid 被回复的评论的ID
     * @return 用户名
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     * @author ZZZCNY
     * @since 1.1
     */
    public String getReplyName(int replyid) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT userid FROM reply WHERE id = '" + replyid + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int userid = resultSet.getInt("userid");
        resultSet.close();
        preparedStatement.close();
        connection.close();
        User user = new User();
        return user.getUsername(userid);
    }

    /**
     * 获取话题评论数
     *
     * @param topicid 话题ID
     * @return 评论数
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     * @author ZZZCNY
     * @since 1.1
     */
    public int getTopicReplyNum(int topicid) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT reply FROM topics WHERE id = '" + topicid + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("reply");
    }

    /**
     * 获取评论的评论数
     *
     * @param replyid 评论ID
     * @return 评论数
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     * @author ZZZCNY
     * @since 1.1
     */
    public int getReplyNum(int replyid) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT reply FROM reply WHERE id = '" + replyid + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("reply");
    }

    /**
     * 修改评论数
     *
     * @param replyid 评论ID
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     * @author ZZZCNY
     * @since 1.1
     */
    public void setReplyNum(int replyid) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE reply SET reply = reply + 1 WHERE id = '" + replyid + "'");
        preparedStatement.executeUpdate();
    }

    /**
     * 点赞
     *
     * @param userid  用户ID
     * @param replyid 评论ID
     * @param topicid 话题ID
     * @return 点赞数
     * @throws ClassNotFoundException 驱动程序加载失败
     * @throws SQLException           数据库异常
     */
    public String likes(int userid, int replyid, int topicid) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(MySQL.url, MySQL.user, MySQL.password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM likes WHERE userid = '" + userid + "' AND topicid = '" + topicid + "' AND replyid = '" + replyid + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            preparedStatement.executeUpdate("DELETE FROM likes WHERE topicid = '" + topicid + "' AND userid = '" + userid + "' AND replyid = '" + replyid + "'");
            preparedStatement.executeUpdate("UPDATE reply SET likes = likes - 1 WHERE id = '" + replyid + "'");
        } else {
            preparedStatement.executeUpdate("INSERT INTO likes (userid, topicid, replyid) VALUES ('" + userid + "', '" + topicid + "', '" + replyid + "')");
            preparedStatement.executeUpdate("UPDATE reply SET likes = likes + 1 WHERE id = '" + replyid + "'");
        }
        resultSet = preparedStatement.executeQuery("SELECT likes FROM reply WHERE id = '" + replyid + "'");
        resultSet.next();
        String likes = resultSet.getString(1);
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return likes;
    }

}
