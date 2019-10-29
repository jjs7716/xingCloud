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
    public void insertFolders(List<FolderInfo> folderInfoList) {
        folderInfoDao.insertFolders(folderInfoList);
    }

    @Override
    public List<FolderInfo> queryChildrenFolder(FilePrams filePrams) {
       return folderInfoDao.queryChildrenFolder(filePrams);
    }

    @Override
    public FolderInfo queryFolderById(FilePrams filePrams) {
        return folderInfoDao.queryFolderById(filePrams);
    }

    @Override
    public List<FolderInfo> queryFolderByIdList(FilePrams filePrams, List<String> folderIdList) {
        return folderInfoDao.queryFolderByIdList(filePrams,folderIdList);
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
        filePrams.setUserId(moveFilePrams.getUserId());
        //目标文件夹ID
        String targetId = moveFilePrams.getTargetId();
        //目标文件夹名
        String targetName = moveFilePrams.getTargetName();
        //获取第一级文件夹的ID
        List<String> folderIdList = moveFilePrams.getFolderIdList();
        //获得第一级文件夹的信息
        List<FolderInfo> folderInfos = queryFolderByIdList(filePrams, folderIdList);
        //folderInfoList是包含第一级文件夹的所有文件夹信息列表,folderInfos仅仅是第一级文件夹的信息
        List<FolderInfo> folderInfoList = new ArrayList<>(folderInfos);
        List<FileInfo> fileInfoList=new ArrayList<>();
        //设置第一级新的parentId和folderId,不能在循环里
        for (FolderInfo folderInfo : folderInfos) {
            folderInfo.setParentId(targetId);
            folderInfo.setParentName(targetName);
        }
        //获取文件夹内所有的文件夹信息,包含子文件夹
        for (String folderId : folderIdList) {
            filePrams.setFolderId(folderId);
            folderInfoList.addAll(getFolderInfoList(filePrams));
        }
        //新建一个新旧ID对应的map集合
        Map<String,String> folderIdMap=new HashMap<>(folderInfoList.size());
        //获得所有文件的信息
        for (FolderInfo folderInfo : folderInfoList) {
            String folderId = folderInfo.getFolderId();
            String newFolderId=XingUtils.getUUID();
            //将文件夹旧ID和新生成的ID一一对应放到map里
            folderIdMap.put(folderId,newFolderId);
            filePrams.setFolderId(folderId);
            folderInfo.setFolderId(newFolderId);
            //根据每个文件夹的ID查询每个文件夹的文件信息
            List<FileInfo> fileInfos1 = fileInfoDao.queryFile(filePrams);
            fileInfoList.addAll(fileInfos1);
        }
        //更换folder的parentId
        List<String> oldFolderIdList=new ArrayList<>(folderIdMap.keySet());
        for (FolderInfo folderInfo : folderInfoList) {
            for (String oldId : oldFolderIdList) {
                if(folderInfo.getParentId().equals(oldId)){
                    folderInfo.setParentId(folderIdMap.get(oldId));
                }
            }
        }
        //遍历所有文件信息列表,生成新的文件ID,并设置新的parentId
        for (FileInfo fileInfo : fileInfoList) {
            for (String oldId : oldFolderIdList) {
                if(fileInfo.getParentId().equals(oldId)){
                    fileInfo.setParentId(folderIdMap.get(oldId));
                }
            }
            fileInfo.setFileId(XingUtils.getUUID());
        }

        insertFolders(folderInfoList);
        if(!fileInfoList.isEmpty()){
            fileInfoDao.insertFiles(fileInfoList);
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
        List<FileInfo> fileInfos = fileInfoDao.queryByIdList(filePrams,fileIdList);
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
    public void queryIsEmptyFolder(FilePrams filePrams,List<FolderInfo> folderList){
        for (FolderInfo folderInfo : folderList) {
            filePrams.setFolderId(folderInfo.getFolderId());
            if(folderInfoDao.queryChildrenFolder(filePrams).size()!=0){
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
     * @return
     */
    public List<FolderInfo> getFolderInfoList(FilePrams filePrams){
        List<FolderInfo> folderInfoList=new ArrayList<>();
        getFolder(filePrams,new HashMap<>(),folderInfoList);
        return folderInfoList;
    }

    public List<FolderInfo> getFolderInfoList(FilePrams filePrams,Map<String,Object> frontMap){
        List<FolderInfo> folderInfoList=new ArrayList<>();
        getFolder(filePrams,frontMap,folderInfoList);
        return folderInfoList;
    }

    /**
     * 递归获取一个文件夹下所有文件夹的ID集合
     * @param folderInfoList
     * @return
     */
    public List<String> getFolderIdList(List<FolderInfo> folderInfoList){
        List<String> folderIdList=new ArrayList<>();
        for (FolderInfo folderInfo : folderInfoList) {
            folderIdList.add(folderInfo.getFolderId());
        }
        return folderIdList;
    }

    /**
     * 递归获取全部目录树
     * @param filePrams    包含父文件夹ID和userId
     * @param frontMap     一个空的Map<String,Object>,写入文件夹名树
     */
    public void getFolder(FilePrams filePrams,Map<String,Object> frontMap,List<FolderInfo> folderInfos){
        List<FolderInfo> folderInfoList=folderInfoDao.queryChildrenFolder(filePrams);
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
    public void setFolderParent(FilePrams filePrams,List<FolderInfo> folderInfos){
        List<FolderInfo> folderInfoList=folderInfoDao.queryChildrenFolder(filePrams);
        folderInfos.addAll(folderInfoList);
        if(folderInfoList.size()>0){
            for (int i = 0; i < folderInfoList.size(); i++) {
                Map<String,Object> map=new HashMap<>();
                String folderId = folderInfoList.get(i).getFolderId();
                filePrams.setFolderId(folderId);
                getFolder(filePrams,map,folderInfos);
            }
        }
    }
}
