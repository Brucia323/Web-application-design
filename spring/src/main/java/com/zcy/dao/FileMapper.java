package com.zcy.dao;

import com.zcy.bean.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FileMapper {
    @Select("select * from file where user_id = #{userId} limit #{currentPage}, 10")
    List<File> selectMyFile(@Param("userId") int userId, @Param("currentPage") int currentPage);
    
    @Select("select file_id as fileId, filename as fileName, username from file, user where user.user_id = file" +
            ".user_id and authority = '公开' limit #{currentPage}, 10")
    List<File> selectPublicFile(int currentPage);
    
    @Select("select count(*) from file where authority = '公开'")
    int selectPublicFileCount();
    
    @Select("select count(*) from file where user_id = #{userId}")
    int selectMyFileCount(int userId);
    
    @Select("select filename from file where file_id = #{fileId}")
    String selectFileNameById(int fileId);
    
    @Insert("insert into file(filename, user_id, authority) VALUES (#{fileName}, #{userId}, #{authority})")
    void insertFile(@Param("fileName") String fileName, @Param("userId") int userId,
                    @Param("authority") String authority);
    
    @Update("update file set authority = #{authority} where file_id = #{fileId}")
    void updateFileAuthority(@Param("authority") String authority, @Param("fileId") int fileId);
    
    @Delete("delete from file where file_id = #{fileId}")
    void deleteMyFile(int fileId);
}
