package com.zcy.service;

import com.google.gson.Gson;
import com.zcy.bean.File;
import com.zcy.bean.FileList;
import com.zcy.dao.FileMapper;
import com.zcy.dao.GetSqlSession;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    /**
     * 读取文件列表
     *
     * @param currentPage 当前页
     * @return 文件列表
     */
    public String readFileList(int currentPage) throws IOException {
        SqlSession session = GetSqlSession.getSqlSession();
        FileMapper mapper = session.getMapper(FileMapper.class);
        int totalCount = mapper.selectPublicFileCount();
        int totalPage = totalCount % 10 == 0 ? totalCount / 10 : totalCount / 10 + 1;
        List<File> files = mapper.selectPublicFile((currentPage - 1) * 10);
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
        SqlSession session = GetSqlSession.getSqlSession();
        FileMapper mapper = session.getMapper(FileMapper.class);
        mapper.insertFile(fileName, userId, authority);
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
        FileMapper mapper = session.getMapper(FileMapper.class);
        String fileName = mapper.selectFileNameById(fileId);
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
        FileMapper mapper = session.getMapper(FileMapper.class);
        int totalCount = mapper.selectMyFileCount(userId);
        int totalPage = totalCount % 10 == 0 ? totalCount / 10 : totalCount / 10 + 1;
        List<File> files = mapper.selectMyFile(userId, (currentPage - 1) * 10);
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
     * @param fileId    文件id
     * @param authority 权限
     * @deprecated
     */
    public void updateAuthority(int fileId, String authority) throws IOException {
        SqlSession session = GetSqlSession.getSqlSession();
        FileMapper mapper = session.getMapper(FileMapper.class);
        mapper.updateFileAuthority(authority, fileId);
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
        FileMapper mapper = session.getMapper(FileMapper.class);
        mapper.deleteMyFile(fileId);
        session.commit();
        session.close();
    }
}
