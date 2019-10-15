package com.services;

import com.dao.FileInfoDao;
import com.dao.FolderInfoDao;
import com.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.XingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public List<FolderInfo> queryFolder(FilePrams filePrams) {
       return folderInfoDao.queryFolder(filePrams);
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

    }

    @Override
    public void copyFolder(MoveFilePrams moveFilePrams) {

    }

    @Override
    public void moveFile(MoveFilePrams moveFilePrams) {
        folderInfoDao.moveFile(moveFilePrams);
    }

    @Override
    public void copyFile(MoveFilePrams moveFilePrams) {
        FilePrams filePrams = new FilePrams();
        List<String> newFileIdList=new ArrayList<>();
        List<FileInfo> fileInfos = fileInfoDao.queryById(moveFilePrams.getFileIdList());
        for (int i = 0; i < fileInfos.size(); i++) {
            fileInfos.get(i).setFileId(XingUtils.getUUID());
        }
        folderInfoDao.copyFile(moveFilePrams);
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


}
