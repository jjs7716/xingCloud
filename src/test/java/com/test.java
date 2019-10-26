package com;

import com.controller.FolderController;
import com.dao.FileInfoDao;
import com.dao.FolderInfoDao;
import com.dao.UserDao;
import com.domain.*;
import com.services.DiskFileDaoImpl;
import com.services.FolderInfoDaoImpl;
import com.services.UserDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;
import utils.BaseUtil;
import utils.DateUtil;
import utils.XingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:ApplicationContext.xml")
public class test {

    @Autowired
    private UserDao userDao;

    @Autowired
    private User user;

    @Autowired
    private FileInfo fileInfo;

    @Autowired
    private FileInfoDao fileInfoDao;

    @Autowired
    private FolderInfoDaoImpl folderInfoDao;

    @Autowired
    private UserDaoImpl userDaoImpl;

    @Autowired
    private DiskFileDaoImpl diskFileDao;

    @Autowired
    private FolderController folderController;
    @Test
    public void test1() throws InterruptedException {
        MoveFilePrams moveFilePrams = new MoveFilePrams();
        List<String> fileIdList=new ArrayList<>();
        List<String> folderIdList=new ArrayList<>();
        fileIdList.add("f42c7aed1d771bc30d4f73ea0017bdc8");
        fileIdList.add("ec60ff97ddaec74b8771cebe67e36135");
        fileIdList.add("e7f6081c9e002d64da658f27404a88f9");
        String targetId="ffeabd223de0d4eacb9a3e6e53e5448d";
        String targetName="新建文件夹1-1-2-1";
        String type="copy";
        moveFilePrams.setFileIdList(fileIdList);
        moveFilePrams.setFolderIdList(folderIdList);
        moveFilePrams.setTargetId(targetId);
        moveFilePrams.setSelectType(type);
        moveFilePrams.setTargetName(targetName);
        folderInfoDao.moveFile(moveFilePrams);
    }
    @Test
    public void test2()  {
        String username;
        String password;
        for (int i = 0; i <50; i++) {
            User user=new User();
            username=XingUtils.getRandomString(8);
            List<User> users = userDaoImpl.checkUsername(username);
            if(users.size()>=1){
                continue;
            }
            password=XingUtils.getRandomString(12);
            user.setUsername(username);
            user.setPassword(password);
            userDaoImpl.insert(user);
        }
    }


    @Test
    public void test3() {
        String userId="c3a052c72a20976ef216f2f86e100e2f";
        String searchName="city";
        List<FileInfo> search = fileInfoDao.search(userId, searchName);
        FileInfo fileInfo = search.get(0);
        long i=DateUtil.nowDate().getTime() - fileInfo.getDeleteTime().getTime();
        long l = i / (1000 * 60 * 60 * 24);
        System.out.println(l);
        System.out.println(search);
    }

    @Test
    public void test4(){
//        MoveFilePrams moveFilePrams = new MoveFilePrams();
//        moveFilePrams.setUserId("d4e2e762de5eb3232441447cadee4d4e");
//        List<String> fileIdList=new ArrayList<>();
//        fileIdList.add("e269a7fbfcc4d3d192d42287fd176894");
//        fileIdList.add("a413a31114b3d2a79b3485d74ee75b2b");
//        moveFilePrams.setFileIdList(fileIdList);
//        moveFilePrams.setTargetName("新建文件夹1-2-1");
//        moveFilePrams.setTargetId("eeb69a3cb92300456b6a5f4162093851");
//        folderInfoDao.copyFile(moveFilePrams);

//        DataResult dataResult=new DataResult();
//        Map<String,Object> data1=new HashMap<>();
//        Map<String,Object> data2=new HashMap<>();
        FilePrams filePrams=new FilePrams();
        filePrams.setUserId("d4e2e762de5eb3232441447cadee4d4e");
//        filePrams.setFolderId("f4b9ec30ad9f68f89b29639786cb62ef");
//        Set<String> strings=new HashSet<>();
//        strings.add(filePrams.getFolderId());
        List<String> folderIdList=new ArrayList<>();
        folderIdList.add("428fca9bc1921c25c5121f9da7815cde");
        folderIdList.add("210f760a89db30aa72ca258a3483cc7f");
//        folderController.getFolderName(filePrams,data1);
//        folderController.getFolderName(filePrams,data2,folderIdList);
        List<FolderInfo> folderInfoList = folderInfoDao.getFolderInfoList(filePrams);
//        for (FolderInfo folderInfo : folderInfoList) {
//            System.out.println(folderInfo);
//        }
//        test6();
//        test5(data2,strings);
//        System.out.println(folderIdList);

    }

    private void test5(Map<String,Object> data,Set<String> strings){
        if(!data.isEmpty()){
            Set<String> set = data.keySet();
            strings.addAll(set);
            for (String string : set) {
                Map<String,Object> map=(Map)data.get(string);
                test5(map,strings);
            }
        }
    }
    @Test
    public void test6(){
        FilePrams filePrams=new FilePrams();
        MoveFilePrams moveFilePrams=new MoveFilePrams();
//        filePrams.setUserId("d4e2e762de5eb3232441447cadee4d4e");
//        filePrams.setFolderId("210f760a89db30aa72ca258a3483cc7f");
        List<String> folderIdList=new ArrayList<>();
        folderIdList.add("6a10bbd480e4c5573d8f3af73ae0454b");
        folderIdList.add("210f760a89db30aa72ca258a3483cc7f");
//        filePrams.setFolderIdList(folderIdList);
//        moveFilePrams.setFolderIdList(folderIdList);
//        moveFilePrams.setUserId("d4e2e762de5eb3232441447cadee4d4e");
//        folderInfoDao.copyFolder(moveFilePrams);
//        filePrams.setFolderId("210f760a89db30aa72ca258a3483cc7f");

        Map<String,Object> data=new HashMap<>();
        List<FolderInfo> folderInfoList=new ArrayList<>();
        List<FolderInfo> folderInfoList1 = folderInfoDao.queryFolderByIdList(filePrams, folderIdList);
//        folderInfoDao.getFolder(filePrams,data,folderInfoList);
//        System.out.println(data);
//        System.out.println(folderInfoList);
    }


}
