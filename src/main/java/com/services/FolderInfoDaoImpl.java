package com.services;

import com.dao.FileInfoDao;
import com.dao.FolderInfoDao;
import com.domain.FilePrams;
import com.domain.FolderInfo;
import com.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public void moveToFolder(List<String> fileIdList, String folderId) {
        folderInfoDao.moveToFolder(fileIdList,folderId);
    }

    @Override
    public void copyToFolder(List<String> fileIdList, String folderId) {
        folderInfoDao.copyToFolder(fileIdList,folderId);
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

}
