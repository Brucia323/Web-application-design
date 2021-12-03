package com.zcy.service;

import com.zcy.dao.GetSqlSession;
import com.zcy.dao.UserMapper;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户id
     */
    public int login(String username, String password) throws IOException {
        SqlSession session = GetSqlSession.getSqlSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        int userId = mapper.selectUserToLogin(username, password);
        session.close();
        return userId;
    }
    
    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户id
     */
    public int logup(String username, String password) throws IOException {
        if (checkUsername(username) == 1) {
            SqlSession session = GetSqlSession.getSqlSession();
            UserMapper mapper = session.getMapper(UserMapper.class);
            int userId = mapper.insertUserToLogup(username, password);
            session.commit();
            session.close();
            return userId;
        } else {
            return 0;
        }
    }
    
    /**
     * 检查账号是否被注册
     *
     * @param username 账号
     * @return true=1/false=0
     */
    public int checkUsername(String username) throws IOException {
        SqlSession session = GetSqlSession.getSqlSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        int flag = mapper.selectUserToCheckUsername(username);
        session.close();
        return flag == 0 ? 1 : 0;
    }
}
