package com.controller;

import com.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.DiskFileDaoImpl;
import com.services.FileInfoDaoImpl;
import com.services.FolderInfoDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="recycle")
public class Recycle {

    @Autowired
    private FileInfoDaoImpl fileInfoDao;

    @Autowired
    private FolderInfoDaoImpl folderInfoDao;

    @Autowired
    private DiskFileDaoImpl diskFileDao;

    @Autowired
    private ObjectMapper mapper;

    /**
     * 恢复
     */
    private String RECOVER="recover";

    /**
     * 删除
     */
    private String RECYCLE="recycle";

    /**
     * 彻底删除
     */
    private String DELETE="delete";

    @RequestMapping("controller")
    @ResponseBody
    public String controller(HttpServletRequest request, @RequestBody String JSON) throws IOException {
        String userId = getUserId(request);
        Map<String,Object> data = mapper.readValue(JSON, Map.class);
        String control= (String) data.get("control");
        List<String> fileIdList = (List<String>) data.get("fileIdList");
        List<String> fileKeyList=(List<String>) data.get("fileKeyList");
        List<String> folderIdList = (List<String>) data.get("folderIdList");
        if(RECOVER.equals(control)){
            recover(folderIdList,fileIdList,fileKeyList,userId);
        }else if(RECYCLE.equals(control)){
            recycle(folderIdList,fileIdList,fileKeyList,userId);
        }else if(DELETE.equals(control)){
            delete(folderIdList,fileIdList,userId);
        }
        return "success";
    }

    /**
     * 恢复
     * @param folderIdList
     * @param fileIdList
     * @param userId
     */
    private void recover(List<String> folderIdList,List<String> fileIdList,List<String> fileKeyList,String userId){
        if(folderIdList.size()>0){
            folderInfoDao.recoverFolder(folderIdList,userId);
        }
        if(fileIdList.size()>0){
            fileInfoDao.recoverFile(fileIdList,userId);
            diskFileDao.batchAddCateCount(fileKeyList);
        }
    }

    /**
     * 删除
     * @param folderIdList
     * @param fileIdList
     * @param userId
     */
    private void recycle(List<String> folderIdList,List<String> fileIdList,List<String> fileKeyList,String userId){
        if(folderIdList.size()>0){
            folderInfoDao.recycleFolder(folderIdList,userId);
        }
        if(fileIdList.size()>0){
            fileInfoDao.recycleFile(fileIdList,userId);
            diskFileDao.batchSubtractCateCount(fileKeyList);
        }
    }

    /**
     * 彻底删除
     * @param folderIdList
     * @param fileIdList
     * @param userId
     */
    private void delete(List<String> folderIdList,List<String> fileIdList,String userId) {
        if(folderIdList.size()>0){
            folderInfoDao.deleteFolder(folderIdList,userId);
        }
        if(fileIdList.size()>0){
            fileInfoDao.deleteFile(fileIdList,userId);
        }
    }

    private String getUserId(HttpServletRequest request){
        return ((User)request.getSession().getAttribute("user")).getUserId();
    }
}
