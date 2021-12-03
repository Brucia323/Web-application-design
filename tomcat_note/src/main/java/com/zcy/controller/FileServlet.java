package com.zcy.controller;

import com.zcy.service.FileService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "FileServlet", value = "/FileServlet")
public class FileServlet extends HttpServlet {
    private List<String> fileNameList = new ArrayList<>();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        downloadFile(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String flag = request.getParameter("flag") == null ? "upload" : request.getParameter("flag");
        String result = "";
        if (flag.equals("look")) {
            result = getFileList(request);
        } else if (flag.equals("upload")) {
            try {
                uploadFile(request);
                response.setHeader("refresh", "0;url=myfile.html");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag.equals("upload1")) {
            uploadFile1(request);
        } else if (flag.equals("lookme")) {
            result = getMyFileList(request);
        } else if (flag.equals("delete")) {
            deleteFile(request);
        }
        out.print(result);
    }
    
    /**
     * 读取文件列表
     *
     * @param request 请求
     * @return 文件列表
     */
    private String getFileList(HttpServletRequest request) throws IOException {
        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }
        FileService fileService = new FileService();
        return fileService.readFileList(page);
    }
    
    /**
     * 上传文件
     *
     * @param request 请求
     */
    private void uploadFile(HttpServletRequest request) throws Exception {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        servletFileUpload.setHeaderEncoding("utf-8");
        List<FileItem> fileItems = servletFileUpload.parseRequest(request);
        String fileName = "";
        for (FileItem fileItem : fileItems) {
            if (!fileItem.isFormField()) {
                String uploadPath = this.getServletContext().getRealPath("/upload");
                File file = new File(uploadPath);
                if (!file.exists()) {
                    file.mkdir();
                }
                fileName = fileItem.getName();
                if (fileName != null) {
                    fileName = FilenameUtils.getName(fileName);
                }
                fileName = UUID.randomUUID() + "_" + fileName;
                fileNameList.add(fileName);
                fileItem.write(new File(file, fileName));
            }
        }
    }
    
    /**
     * 上传文件信息到数据库
     *
     * @param request 请求
     */
    private void uploadFile1(HttpServletRequest request) throws IOException {
        for (int i = 0; fileNameList.isEmpty(); i++) {
            System.out.println(i);
        }
        int userId = Integer.parseInt(request.getParameter("userId"));
        String authority = request.getParameter("authority");
        FileService fileService = new FileService();
        for (String fileName : fileNameList) {
            fileService.uploadFile(fileName, userId, authority);
        }
        fileNameList = new ArrayList<>();
    }
    
    /**
     * 下载文件
     *
     * @param request  请求
     * @param response 响应
     */
    private void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int fileId = Integer.parseInt(request.getParameter("fileId"));
        FileService fileService = new FileService();
        String fileName = fileService.downloadFile(fileId);
        String filePath = this.getServletContext().getRealPath("/upload/" + fileName);
        File file = new File(filePath);
        if (file.exists()) {
            int index = fileName.indexOf("_");
            fileName = fileName.substring(index + 1);
            fileName = URLEncoder.encode(fileName, "utf-8");
            response.setHeader("content-disposition", "attachment; filename=" + fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(buffer)) > 0) {
                servletOutputStream.write(buffer, 0, len);
            }
            fileInputStream.close();
            servletOutputStream.close();
        }
    }
    
    /**
     * 读取我的文件列表
     *
     * @param request 请求
     * @return 文件列表
     */
    private String getMyFileList(HttpServletRequest request) throws IOException {
        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }
        FileService fileService = new FileService();
        return fileService.readMyFileList(page, Integer.parseInt(request.getParameter("userId")));
    }
    
    /**
     * 修改文件权限
     *
     * @param request 请求
     * @deprecated
     */
    private void updateFileAuthority(HttpServletRequest request) throws IOException {
        int fileId = Integer.parseInt(request.getParameter("fileId"));
        System.out.println(fileId);
        String authority = request.getParameter("authority");
        FileService fileService = new FileService();
        fileService.updateAuthority(fileId, authority);
    }
    
    /**
     * 删除文件
     *
     * @param request 请求
     */
    private void deleteFile(HttpServletRequest request) throws IOException {
        int fileId = Integer.parseInt(request.getParameter("fileId"));
        FileService fileService = new FileService();
        fileService.deleteFile(fileId);
    }
}
