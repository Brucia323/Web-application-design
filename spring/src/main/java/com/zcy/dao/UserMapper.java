package com.zcy.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select user_id from user where username = #{username} and password = #{password}")
    int selectUserToLogin(@Param("username") String username, @Param("password") String password);
    
    @Select("select username from user where user_id = #{userId}")
    String selectUserNameByUserId(int userId);
    
    @Select("select count(*) from user where username = #{username}")
    int selectUserToCheckUsername(String username);
    
    @Insert("insert into user(username, password) VALUES (#{username}, #{password})")
    int insertUserToLogup(@Param("username") String username, @Param("password") String password);
}
