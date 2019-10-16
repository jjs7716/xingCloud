package com.services;

import com.dao.FileInfoDao;
import com.domain.FileInfo;
import com.domain.FilePrams;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Service
public class FileInfoDaoImpl implements FileInfoDao {

    @Autowired
    private FileInfoDao fileInfoDao;

    @Override
    public List<FileInfo> queryFile(FilePrams filePrams) {
        return fileInfoDao.queryFile(filePrams);
    }

    @Override
    public List<FileInfo> queryFileByRecycle(FilePrams filePrams) {
        return fileInfoDao.queryFileByRecycle(filePrams);
    }

    @Override
    public void insertInfo(FileInfo fileInfo) {
        fileInfoDao.insertInfo(fileInfo);
    }

    @Override
    public List<FileInfo> queryById(FilePrams filePrams) {
       return fileInfoDao.queryById(filePrams);
    }

    @Override
    public List<String> queryByFileKey(String fileKey) {
       return fileInfoDao.queryByFileKey(fileKey);
    }

    @Override
    public void recycleFile(List<String> fileIdList,String fileUid,List<String> folderIdList) {
        fileInfoDao.recycleFile(fileIdList,fileUid,folderIdList);
    }

    public void recycleFile(List<String> fileIdList,String fileUid) {
        fileInfoDao.recycleFile(fileIdList,fileUid,null);
    }

    @Override
    public void recoverFile(List<String> fileIdList,String fileUid,List<String> folderIdList) {
        fileInfoDao.recoverFile(fileIdList,fileUid,folderIdList);
    }

    public void recoverFile(List<String> fileIdList,String fileUid) {
        fileInfoDao.recoverFile(fileIdList,fileUid,null);
    }

    @Override
    public void deleteFile(List<String> fileIdList,String fileUid,List<String> folderIdList) {
        fileInfoDao.deleteFile(fileIdList,fileUid,folderIdList);
    }

    public void deleteFile(List<String> fileIdList,String fileUid) {
        fileInfoDao.deleteFile(fileIdList,fileUid,null);
    }

    @Override
    public List<FileInfo> search(String userId, String searchName) {
        return fileInfoDao.search(userId,searchName);
    }

    @Override
    public void upDateDeleteDay() {
        fileInfoDao.upDateDeleteDay();
    }

    @Override
    public void taskDelete() {
        fileInfoDao.taskDelete();
    }

    @Override
    public void reFileName(String fileId,String newFileName) {
        fileInfoDao.reFileName(fileId,newFileName);
    }

    @Override
    public List<FileInfo> searchByVideo(String userId) {
        return fileInfoDao.searchByVideo(userId);
    }

    @Override
    public List<FileInfo> searchByMusic(String userId) {
        return fileInfoDao.searchByMusic(userId);
    }

    @Override
    public List<FileInfo> searchByDoc(String userId) {
        return fileInfoDao.searchByDoc(userId);
    }

    @Override
    public List<FileInfo> searchByImg(String userId) {
        return fileInfoDao.searchByImg(userId);
    }

    @Override
    public List<FileInfo> searchByOther(String userId) {
        return fileInfoDao.searchByOther(userId);
    }

//    @Override
//    public Integer getDeleteDay(FileInfo userId) {
//        return fileInfoDao.getDeleteDay(userId);
//    }
}
