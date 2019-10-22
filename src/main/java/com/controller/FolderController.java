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
import utils.BaseUtil;
import utils.DateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.*;

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
    public String creatFolder(HttpServletRequest request){
        FolderInfo folderInfo=new FolderInfo();
        String folderId = request.getParameter("folderId");
        String folderName = request.getParameter("folderName");
        String currentFolderId = request.getParameter("currentFolderId");
        String currentFolderName = request.getParameter("currentFolderName");
        if(currentFolderId==null){
            currentFolderId="";
            currentFolderName="";
        }
        String userId = BaseUtil.getUserId(request);
        folderInfo.setFolderId(folderId);
        folderInfo.setFolderName(folderName);
        folderInfo.setFolderUid(userId);
        folderInfo.setUpdateTime(DateUtil.nowDate());
        folderInfo.setParentId(currentFolderId);
        folderInfo.setParentName(currentFolderName);
        folderInfoDao.creatFolder(folderInfo);
        return "success";
    }

    /**
     * 重命名文件夹
     */
    @RequestMapping(path = "/reFolderName")
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
        String userId= ((User) request.getSession().getAttribute("user")).getUserId();
        String folderId = request.getParameter("folderId");
        String selectFolder=request.getParameter("selectFolder");
        FilePrams filePrams=new FilePrams(userId);
        filePrams.setFolderId(folderId);
        Map<String,Object> data=new HashMap<>();
        List<FolderInfo> folderList=folderInfoDao.queryChildrenFolder(filePrams);
        FolderInfo folderInfo = folderInfoDao.queryFolderById(filePrams);
        String currentFolderName;
        if(folderInfo==null){
            currentFolderName="";
        }else{
            currentFolderName=folderInfo.getFolderName();
        }
        //判断是否为移动或复制文件遍历目录树
        if(selectFolder!=null){
            folderInfoDao.queryIsEmptyFolder(filePrams,folderList);
        }else{
            List<FileInfo> fileList = fileInfoDao.queryFile(filePrams);
            List<String> nameList=new ArrayList<>();
            List<String> idList=new ArrayList<>();
            folderInfoDao.getParent(filePrams,nameList,idList);
            data.put("nameList",nameList);
            data.put("idList",idList);
            data.put("fileList",fileList);
            data.put("currentFolderName",currentFolderName);
        }
        data.put("folderList",folderList);
        dataResult.setData(data);
        return dataResult;
    }


    /**
     * 获取指定文件夹目录树
     * @param request
     * @return
     */
    @RequestMapping("selectFolder")
    public DataResult selectFolder(HttpServletRequest request){
        DataResult dataResult=new DataResult();
        Map<String,Object> data=new HashMap<>();
        FilePrams filePrams=new FilePrams();
        filePrams.setUserId(BaseUtil.getUserId(request));
        folderInfoDao.getFolderInfoList(filePrams,data);
        dataResult.setData(data);
        return dataResult;
    }

    /**
     * 复制或者移动
     * @param request
     * @param json
     * @return
     * @throws IOException
     */
    @RequestMapping(path="moveOrCopy",produces = {"application/json; charset=UTF-8"})
    public String moveAndCopy(HttpServletRequest request,@RequestBody String json) throws IOException {
        MoveFilePrams moveFilePrams = mapper.readValue(json, MoveFilePrams.class);
        moveFilePrams.setUserId(BaseUtil.getUserId(request));
        if("copy".equals(moveFilePrams.getSelectType())){
            copy(moveFilePrams);
        }
        if("move".equals(moveFilePrams.getSelectType())){
            move(moveFilePrams);
        }
        return "success";
    }

    private void copy(MoveFilePrams moveFilePrams){
        if(!moveFilePrams.getFolderIdList().isEmpty()){
            folderInfoDao.copyFolder(moveFilePrams);
        }
        if(!moveFilePrams.getFileIdList().isEmpty()){
            folderInfoDao.copyFile(moveFilePrams);
        }
    }

    private void move(MoveFilePrams moveFilePrams){
        if(!moveFilePrams.getFolderIdList().isEmpty()){
            folderInfoDao.moveFolder(moveFilePrams);
        }
        if(!moveFilePrams.getFileIdList().isEmpty()){
            folderInfoDao.moveFile(moveFilePrams);
        }
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
