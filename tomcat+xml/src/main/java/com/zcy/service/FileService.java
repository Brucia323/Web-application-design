package com.zcy.service;

import com.google.gson.Gson;
import com.zcy.bean.File;
import com.zcy.bean.FileList;
import com.zcy.dao.GetSqlSession;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileService {
    /**
     * 读取文件列表
     *
     * @param currentPage 当前页
     * @return 文件列表
     */
    public String readFileList(int currentPage) throws IOException {
        SqlSession session = GetSqlSession.getSqlSession();
        int totalCount = session.selectOne("FileMapper.selectPublicFileCount");
        int totalPage = totalCount % 10 == 0 ? totalCount / 10 : totalCount / 10 + 1;
        List<File> files = session.selectList("FileMapper.selectPublicFile", (currentPage - 1) * 10);
        session.close();
        FileList fileList = new FileList();
        fileList.setCurrentPage(currentPage);
        fileList.setTotalPage(totalPage);
        fileList.setFiles(files);
        Gson gson = new Gson();
        return gson.toJson(fileList);
    }
    
    /**
     * 上传文件
     *
     * @param fileName  文件名
     * @param userId    用户id
     * @param authority 权限
     * @return 文件id
     */
    public int uploadFile(String fileName, int userId, String authority) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("fileName", fileName);
        map.put("userId", String.valueOf(userId));
        map.put("authority", authority);
        SqlSession session = GetSqlSession.getSqlSession();
        int fileId = session.insert("FileMapper.insertFile", map);
        session.commit();
        session.close();
        return fileId;
    }
    
    /**
     * 下载文件
     *
     * @param fileId 文件id
     * @return 文件名
     */
    public String downloadFile(int fileId) throws IOException {
        SqlSession session = GetSqlSession.getSqlSession();
        String fileName = session.selectOne("FileMapper.selectFileNameById", fileId);
        session.close();
        return fileName;
    }
}
