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
 * @version 1.0
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
            }
        }
        User user = new User();
        String username = user.getUsername(userid);
        resultSet = preparedStatement.executeQuery("SELECT time FROM reply WHERE id = '" + replyid + "'");
        resultSet.next();
        String time = resultSet.getString(1);
        resultSet = preparedStatement.executeQuery("SELECT replyid FROM reply WHERE id = '" + replyid + "'");
        resultSet.next();
        replyid = resultSet.getInt("replyid");
        String replyName;
        if (replyid == 0) {
            replyName = "";
        } else {
            resultSet = preparedStatement.executeQuery("SELECT userid FROM reply WHERE id = '" + replyid + "'");
            resultSet.next();
            replyName = user.getUsername(resultSet.getInt("userid"));
        }
        SendReply reply2 = new SendReply(replyid, username, reply, 0, 0, false, time, replyName);
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
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reply WHERE topicid = '" + topicid + "' ORDER BY sticky DESC, id DESC");
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
            int replyid = resultSet.getInt("replyid");
            String replyName;
            if (replyid == 0) {
                replyName = "";
            } else {
                ResultSet resultSet1 = preparedStatement.executeQuery("SELECT userid FROM reply WHERE id = '" + replyid + "'");
                resultSet.next();
                replyName = user.getUsername(resultSet1.getInt("userid"));
            }
            boolean sticky = resultSet.getBoolean("sticky");
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
            SendReply reply1 = new SendReply(replyid, username, reply, replyNum, likes, sticky, time, replyName);
            list.add(reply1);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
