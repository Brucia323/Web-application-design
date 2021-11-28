package com.zcy.controller;

import com.google.gson.Gson;
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
import java.util.List;
import java.util.UUID;

@WebServlet(name = "FileServlet", value = "/FileServlet")
public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String flag = request.getParameter("flag");
        String result = "";
        if (flag.equals("look")) {
            result = getFileList(request);
        } else if (flag.equals("upload")) {
            try {
                result = uploadFile(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (flag.equals("download")){
            downloadFile(request,response);
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
     * @return 上传的文件编号
     */
    private String uploadFile(HttpServletRequest request) throws Exception {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        servletFileUpload.setHeaderEncoding("utf-8");
        List<FileItem> fileItems = servletFileUpload.parseRequest(request);
        String fileName = "";
        int fileId = 0;
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
                fileItem.write(new File(file, fileName));
                FileService fileService = new FileService();
                fileId = fileService.uploadFile(fileName, Integer.parseInt(request.getParameter("userId")),
                        request.getParameter("authority"));
            }
        }
        com.zcy.bean.File file = new com.zcy.bean.File();
        file.setFileId(fileId);
        file.setFileName(fileName);
        Gson gson = new Gson();
        return gson.toJson(file);
    }
    
    /**
     * 下载文件
     *
     * @param request 请求
     * @param response 响应
     */
    private void downloadFile(HttpServletRequest request,HttpServletResponse response) throws IOException {
        int fileId = Integer.parseInt(request.getParameter("fileId"));
        FileService fileService = new FileService();
        String fileName = fileService.downloadFile(fileId);
        String filePath = this.getServletContext().getRealPath("/upload/" + fileName);
        File file = new File(filePath);
        if (file.exists()) {
            fileName= URLEncoder.encode(fileName,"utf-8");
            response.setHeader("content-disposition","attachment; filename="+fileName);
            FileInputStream fileInputStream=new FileInputStream(file);
            ServletOutputStream servletOutputStream= response.getOutputStream();
            byte[] buffer=new byte[1024];
            int len=0;
            while ((len=fileInputStream.read(buffer))>0){
                servletOutputStream.write(buffer,0,len);
            }
            fileInputStream.close();
            servletOutputStream.close();
        }
    }
}
