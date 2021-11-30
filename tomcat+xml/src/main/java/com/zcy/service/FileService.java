package com.zcy.service;

import com.google.gson.Gson;
import com.zcy.bean.File;
import com.zcy.bean.FileList;
import com.zcy.dao.GetSqlSession;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
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
        List<File> files1 = new ArrayList<>();
        for (File file1 : files) {
            String fileName = file1.getFileName();
            int index = fileName.indexOf("_");
            fileName = fileName.substring(index + 1);
            file1.setFileName(fileName);
            files1.add(file1);
        }
        FileList fileList = new FileList();
        fileList.setCurrentPage(currentPage);
        fileList.setTotalPage(totalPage);
        fileList.setFiles(files1);
        Gson gson = new Gson();
        return gson.toJson(fileList);
    }
    
    /**
     * 上传文件
     *
     * @param fileName  文件名
     * @param userId    用户id
     * @param authority 权限
     */
    public void uploadFile(String fileName, int userId, String authority) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("fileName", fileName);
        map.put("userId", String.valueOf(userId));
        map.put("authority", authority);
        SqlSession session = GetSqlSession.getSqlSession();
        session.insert("FileMapper.insertFile", map);
        session.commit();
        session.close();
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
    
    /**
     * 读取我的文件列表
     *
     * @param currentPage 页码
     * @param userId      用户id
     * @return 文件列表
     */
    public String readMyFileList(int currentPage, int userId) throws IOException {
        SqlSession session = GetSqlSession.getSqlSession();
        int totalCount = session.selectOne("FileMapper.selectMyFileCount", userId);
        int totalPage = totalCount % 10 == 0 ? totalCount / 10 : totalCount / 10 + 1;
        Map<String, Integer> map = new HashMap<>();
        map.put("userId", userId);
        map.put("currentPage", (currentPage - 1) * 10);
        List<File> files = session.selectList("FileMapper.selectMyFile", map);
        session.close();
        List<File> files1 = new ArrayList<>();
        for (File file1 : files) {
            String fileName = file1.getFileName();
            int index = fileName.indexOf("_");
            fileName = fileName.substring(index + 1);
            file1.setFileName(fileName);
            files1.add(file1);
        }
        FileList fileList = new FileList();
        fileList.setCurrentPage(currentPage);
        fileList.setTotalPage(totalPage);
        fileList.setFiles(files1);
        Gson gson = new Gson();
        return gson.toJson(fileList);
    }
    
    /**
     * 修改文件权限
     *
     * @param fileId 文件id
     * @param authority 权限
     */
    public void updateAuthority(int fileId, String authority) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("fileId", String.valueOf(fileId));
        map.put("authority", authority);
        SqlSession session = GetSqlSession.getSqlSession();
        session.update("FileMapper.updateFileAuthority", map);
        session.commit();
        session.close();
    }
    
    /**
     * 删除文件
     *
     * @param fileId 文件id
     */
    public void deleteFile(int fileId) throws IOException {
        SqlSession session = GetSqlSession.getSqlSession();
        session.delete("FileMapper.deleteMyFile", fileId);
        session.commit();
        session.close();
    }
}
