package com.example.demo5;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet(name = "DianZanServlet", value = "/DianZanServlet")
public class DianZanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id")); // 话题ID
        int userid = Integer.parseInt(request.getParameter("userid")); // 用户ID
        String huiFuId = request.getParameter("huifuid"); // 回复ID，不一定有
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo5", "root", "20010323"); // 连接数据库
            if (huiFuId == null) {
                // 主页点赞话题
                dianZanHuaTi(id, userid, connection);
                response.setHeader("refresh", "0;url=main.jsp?id=" + request.getParameter("userid") + "#" + request.getParameter("id")); // 跳转回主页对应话题处
            } else if (!huiFuId.equals("0")) {
                // 在详情页点赞回复
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM dianzan WHERE userid = '" + userid + "' AND huatiid = '" + id + "' AND huifuid = '" + huiFuId + "'"); // 查询该用户对该回复的点赞记录
                ResultSet resultSet = preparedStatement.executeQuery(); // 执行查询语句并获取结果集
                if (resultSet.next()) {
                    // 如果有点赞过
                    preparedStatement = connection.prepareStatement("DELETE FROM dianzan WHERE huifuid = '" + huiFuId + "' AND userid = '" + userid + "' AND huatiid = '" + id + "'"); // 删除点赞记录
                    preparedStatement.executeUpdate(); // 执行删除语句
                    preparedStatement = connection.prepareStatement("UPDATE huifu SET zan = zan - 1 WHERE id = '" + huiFuId + "'"); // 将该回复点赞数-1
                    preparedStatement.executeUpdate(); // 执行更新语句
                } else {
                    //没有点赞过
                    preparedStatement = connection.prepareStatement("INSERT INTO dianzan (userid, huatiid, huifuid) VALUES ('" + userid + "', '" + id + "', '" + huiFuId + "')"); // 增加点赞记录
                    preparedStatement.executeUpdate(); // 执行插入语句
                    preparedStatement = connection.prepareStatement("UPDATE huifu SET zan = zan + 1 WHERE id = '" + huiFuId + "'"); // 将该回复点赞数+1
                    preparedStatement.executeUpdate(); // 执行更新语句
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
                response.setHeader("refresh", "0;url=XiangXi.jsp?id=" + id + "&userid=" + userid + "#" + huiFuId); // 跳转回详情页对应回复处
            } else {
                // 在详情页点赞话题
                dianZanHuaTi(id, userid, connection);
                response.setHeader("refresh", "0;url=XiangXi.jsp?id=" + id + "&userid=" + userid); // 跳转回详情页
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dianZanHuaTi(int id, int userid, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM dianzan WHERE huatiid = '" + id + "' AND userid = '" + userid + "' AND huifuid = '0'"); // 查询该用户对该话题的点赞记录
        ResultSet resultSet = preparedStatement.executeQuery(); // 执行查询语句并获取结果集
        if (resultSet.next()) {
            // 如果有点赞过
            preparedStatement = connection.prepareStatement("DELETE FROM dianzan WHERE userid = '" + userid + "' AND huatiid = '" + id + "'"); // 删除点赞记录
            preparedStatement.executeUpdate(); // 执行删除语句
            preparedStatement = connection.prepareStatement("UPDATE huati SET zan = zan - 1 WHERE id = '" + id + "'"); // 将该话题点赞数-1
            preparedStatement.executeUpdate(); // 执行更新语句
        } else {
            // 没有点赞过
            preparedStatement = connection.prepareStatement("INSERT INTO dianzan (userid, huatiid) VALUES ('" + userid + "','" + id + "')"); // 增加点赞记录
            preparedStatement.executeUpdate(); // 执行插入语句
            preparedStatement = connection.prepareStatement("UPDATE huati SET zan = zan + 1 WHERE id = '" + id + "'"); // 将该话题点赞数+1
            preparedStatement.executeUpdate(); // 执行更新语句
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
