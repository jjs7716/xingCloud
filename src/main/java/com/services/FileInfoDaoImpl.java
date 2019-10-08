package com.services;

import com.dao.FileInfoDao;
import com.domain.FileInfo;
import com.domain.FilePrams;
import com.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<FileInfo> queryById(String id) {
        List<String> list=new ArrayList<>();
        list.add(id);
        return fileInfoDao.queryById(list);
    }

    @Override
    public List<FileInfo> queryById(List<String> list) {
       return fileInfoDao.queryById(list);
    }

    @Override
    public List<String> queryByFileKey(String fileKey) {
       return fileInfoDao.queryByFileKey(fileKey);
    }

    @Override
    public void recycleFile(List<String> fileIdList,String fileUid) {
        fileInfoDao.recycleFile(fileIdList,fileUid);
    }

    public void recycleFile(String fileId,String fileUid) {
        List<String> fileIdList=new ArrayList<>();
        fileIdList.add(fileId);
        fileInfoDao.recycleFile(fileIdList,fileUid);
    }

    @Override
    public void recoverFile(List<String> fileIdList,String fileUid) {
        fileInfoDao.recoverFile(fileIdList,fileUid);
    }

    public void recoverFile(String fileId,String fileUid){
        List<String> fileIdList=new ArrayList<>();
        fileIdList.add(fileId);
        fileInfoDao.recoverFile(fileIdList,fileUid);
    }
    @Override
    public void deleteFile(List<String> fileIdList,String fileUid) {
        fileInfoDao.deleteFile(fileIdList,fileUid);
    }

    public void deleteFile(String fileId,String fileUid){
        List<String> fileIdList=new ArrayList<>();
        fileIdList.add(fileId);
        fileInfoDao.deleteFile(fileIdList,fileUid);
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
