package com.services;

import com.dao.FileInfoDao;
import com.dao.FolderInfoDao;
import com.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.XingUtils;

import java.util.*;

@Service
public class FolderInfoDaoImpl implements FolderInfoDao {

    @Autowired
    private FolderInfoDao folderInfoDao;

    @Autowired
    private FileInfoDao fileInfoDao;

    @Override
    public void creatFolder(FolderInfo folderInfo) {
        folderInfoDao.creatFolder(folderInfo);
    }

    @Override
    public void insertFolder(List<FolderInfo> folderInfoList) {
        folderInfoDao.insertFolder(folderInfoList);
    }

    @Override
    public List<FolderInfo> queryFolder(FilePrams filePrams) {
       return folderInfoDao.queryFolder(filePrams);
    }

    @Override
    public List<FolderInfo> queryFolderById(FilePrams filePrams,List<String> folderIdList) {
        return folderInfoDao.queryFolderById(filePrams,folderIdList);
    }

    @Override
    public List<FolderInfo> queryFolderByRecycle(FilePrams filePrams) {
        return folderInfoDao.queryFolderByRecycle(filePrams);
    }

    @Override
    public void reFolderName(String folderId, String newFolderName) {
        folderInfoDao.reFolderName(folderId,newFolderName);
    }

    @Override
    public void moveFolder(MoveFilePrams moveFilePrams) {
        folderInfoDao.moveFolder(moveFilePrams);
    }

    /**
     * 复制文件夹
     * @param moveFilePrams
     */
    public void copyFolder(MoveFilePrams moveFilePrams) {
        FilePrams filePrams=new FilePrams();
        List<String> folderIdList = moveFilePrams.getFolderIdList();
        filePrams.setFolderIdList(folderIdList);
        List<FileInfo> fileInfos = fileInfoDao.queryById(filePrams);
        for (FileInfo fileInfo : fileInfos) {
            fileInfo.setFileId(XingUtils.getUUID());
            fileInfoDao.insertInfo(fileInfo);
        }
    }

    @Override
    public void moveFile(MoveFilePrams moveFilePrams) {
        folderInfoDao.moveFile(moveFilePrams);
    }


    /**
     * 复制文件
     * @param moveFilePrams
     */
    public void copyFile(MoveFilePrams moveFilePrams) {
        FilePrams filePrams=new FilePrams();
        List<String> fileIdList = moveFilePrams.getFileIdList();
        filePrams.setFileIdList(fileIdList);
        List<FileInfo> fileInfos = fileInfoDao.queryById(filePrams);
        for (FileInfo fileInfo : fileInfos) {
            fileInfo.setFileId(XingUtils.getUUID());
            fileInfo.setParentId(moveFilePrams.getTargetId());
            fileInfo.setParentName(moveFilePrams.getTargetName());
            fileInfoDao.insertInfo(fileInfo);
        }
    }


    @Override
    public void recoverFolder(List<String> folderIdList, String folderUid) {
        folderInfoDao.recoverFolder(folderIdList,folderUid);
        fileInfoDao.recoverFile(null,folderUid,folderIdList);
    }

    @Override
    public void recycleFolder(List<String> folderIdList, String folderUid) {
        folderInfoDao.recycleFolder(folderIdList,folderUid);
        fileInfoDao.recycleFile(null,folderUid,folderIdList);
    }


    @Override
    public void deleteFolder(List<String> folderIdList, String folderUid) {
        folderInfoDao.deleteFolder(folderIdList,folderUid);
    }

    @Override
    public FolderInfo queryParent(FilePrams filePrams) {
        return folderInfoDao.queryParent(filePrams);
    }


    /**
     * 遍历子文件夹是否为空
     * @param filePrams
     * @param folderList
     */
    public void queryFolder(FilePrams filePrams,List<FolderInfo> folderList){
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
    public void getParent(FilePrams filePrams,List<String> nameList,List<String> idList){
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
     * 递归获取一个文件夹下所有文件夹的所有信息
     * @param filePrams
     * @param frontMap
     * @return
     */
    public List<FolderInfo> getFolderInfoList(FilePrams filePrams,Map<String,Object> frontMap){
        List<FolderInfo> folderInfoList=new ArrayList<>();
        getFolder(filePrams,frontMap,folderInfoList);
        return folderInfoList;
    }

    /**
     * 递归获取一个文件夹下所有文件夹的ID集合
     * @param filePrams
     * @return
     */
    public List<String> getFolderIdList(FilePrams filePrams){
        List<String> folderIdList=new ArrayList<>();
        List<FolderInfo> folderInfoList = getFolderInfoList(filePrams, new HashMap<>());
        for (FolderInfo folderInfo : folderInfoList) {
            folderIdList.add(folderInfo.getFolderId());
        }
        return folderIdList;
    }
    /**
     * 递归获取全部目录树
     * @param filePrams
     * @param frontMap     一个空的Map<String,Object>,写入文件夹名树
     */
    public void getFolder(FilePrams filePrams,Map<String,Object> frontMap,List<FolderInfo> folderInfos){
        List<FolderInfo> folderInfoList=folderInfoDao.queryFolder(filePrams);
        folderInfos.addAll(folderInfoList);
        if(folderInfoList.size()>0){
            for (int i = 0; i < folderInfoList.size(); i++) {
                Map<String,Object> map=new HashMap<>();
                String folderId = folderInfoList.get(i).getFolderId();
                filePrams.setFolderId(folderId);
                frontMap.put(folderInfoList.get(i).getFolderName(),map);
                getFolder(filePrams,map,folderInfos);
            }
        }
    }
}
