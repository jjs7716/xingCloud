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
        FilePrams filePrams=new FilePrams(userId,folderId);
        Map<String,Object> data=new HashMap<>();
        List<FolderInfo> folderList=folderInfoDao.queryFolder(filePrams);
        //判断是否为移动或复制文件遍历目录树
        if(selectFolder!=null){
            queryFolder(filePrams,folderList);
        }else{
            List<FileInfo> fileList = fileInfoDao.queryFile(filePrams);
            List<String> nameList=new ArrayList<>();
            List<String> idList=new ArrayList<>();
            getParent(filePrams,nameList,idList);
            data.put("nameList",nameList);
            data.put("idList",idList);
            data.put("fileList",fileList);
        }
        data.put("folderList",folderList);
        dataResult.setData(data);
        return dataResult;
    }

    /**
     * 遍历子文件夹是否为空
     * @param filePrams
     * @param folderList
     */
    private void queryFolder(FilePrams filePrams,List<FolderInfo> folderList){
        for (FolderInfo folderInfo : folderList) {
            filePrams.setFolderId(folderInfo.getFolderId());
            if(folderInfoDao.queryFolder(filePrams).size()!=0){
                folderInfo.setFolderEmpty(0);
            }
        }
    }

    /**
     * 获取当前文件夹的全路径
     * @param filePrams
     * @param nameList
     * @param idList
     */
    private void getParent(FilePrams filePrams,List<String> nameList,List<String> idList){
        FolderInfo folderInfo = folderInfoDao.queryParent(filePrams);
        if(folderInfo!=null){
            filePrams.setFolderId(folderInfo.getParentId());
            nameList.add(folderInfo.getFolderName());
            idList.add(folderInfo.getFolderId());
            getParent(filePrams,nameList,idList);
        }else {
            Collections.reverse(nameList);
            Collections.reverse(idList);
        }
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
        filePrams.setUserId(((User)request.getSession().getAttribute("user")).getUserId());
        getFolder(filePrams,data);
        dataResult.setData(data);
        return dataResult;
    }

    /**
     * 递归获取全部目录树
     * @param filePrams
     * @param frontMap
     */
    private void getFolder(FilePrams filePrams,Map<String,Object> frontMap){
        List<FolderInfo> FolderInfoList=folderInfoDao.queryFolder(filePrams);
        if(FolderInfoList.size()>0){
            for (int i = 0; i < FolderInfoList.size(); i++) {
                Map<String,Object> map=new HashMap<>();
                filePrams.setFolderId(FolderInfoList.get(i).getFolderId());
                frontMap.put(FolderInfoList.get(i).getFolderName(),map);
                getFolder(filePrams,map);
            }
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
