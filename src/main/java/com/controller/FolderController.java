package com.controller;

import com.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.FileInfoDaoImpl;
import com.services.FolderInfoDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.html.HTMLParagraphElement;
import utils.DateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/folder")
public class FolderController {

    @Autowired
    private FolderInfoDaoImpl folderInfoDao;

    @Autowired
    private FileInfoDaoImpl fileInfoDao;

    @Autowired
    private ObjectMapper mapper;
    /**
     * 新建文件夹
     * @param request
     * @return
     */
    @RequestMapping("/creatFolder")
    @ResponseBody
    public String creatFolder(HttpServletRequest request){
        FolderInfo folderInfo=new FolderInfo();
        String folderId = request.getParameter("folderId");
        String folderName = request.getParameter("folderName");
        User user = (User) request.getSession().getAttribute("user");
        String userId=user.getUserId();
        folderInfo.setFolderId(folderId);
        folderInfo.setFolderName(folderName);
        folderInfo.setFolderUid(userId);
        folderInfo.setUpdateTime(DateUtil.nowDate());
        folderInfoDao.creatFolder(folderInfo);
        return "success";
    }

    /**
     * 重命名文件夹
     */
    @RequestMapping(path = "/reFolderName")
    @ResponseBody
    public String reFolderName(HttpServletRequest request){
        String folderId = request.getParameter("folderId");
        String newFolderName = request.getParameter("newFolderName");
        folderInfoDao.reFolderName(folderId,newFolderName);
        return "success";
    }

    /**
     * 进入文件夹
     * @param request
     * @return
     */
    @RequestMapping(path = "/folder")
    public DataResult folder(HttpServletRequest request){
        DataResult dataResult=new DataResult();
        Map<String,Object> data=new HashMap<>();
        String userId= ((User) request.getSession().getAttribute("user")).getUserId();
        String folderId = request.getParameter("folderId");
        FilePrams filePrams=new FilePrams(userId,folderId);
        List<FileInfo> fileList = fileInfoDao.queryFile(filePrams);
        List<FolderInfo> folderList=folderInfoDao.queryFolder(filePrams);
        data.put("fileList",fileList);
        data.put("folderList",folderList);
        return dataResult;
    }

//    /**
//     * 恢复文件夹及其包含的所有文件
//     * @param request
//     */
//    @RequestMapping(path = "recoverFolder")
//    public void recoverFolder(HttpServletRequest request,@RequestBody String JSON) throws IOException {
//        Map<String,Object> map = mapper.readValue(JSON, Map.class);
//    }
//
//    /**
//     * 删除文件夹及其包含的所有文件
//     * @param request
//     */
//    @RequestMapping(path = "recycleFolder")
//    public void recycleFolder(HttpServletRequest request,@RequestBody String JSON){
//
//    }
//
//    /**
//     * 彻底删除文件夹及其包含的所有文件
//     * @param request
//     */
//    @RequestMapping(path = "deleteFolder")
//    public void deleteFolder(HttpServletRequest request){
//
//    }
}
