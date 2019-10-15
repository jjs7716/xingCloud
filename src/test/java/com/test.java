package com;

import com.dao.FileInfoDao;
import com.dao.FolderInfoDao;
import com.dao.UserDao;
import com.domain.FileInfo;
import com.domain.MoveFilePrams;
import com.domain.User;
import com.services.DiskFileDaoImpl;
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
    private FolderInfoDao folderInfoDao;

    @Autowired
    private UserDaoImpl userDaoImpl;

    @Autowired
    private DiskFileDaoImpl diskFileDao;
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
        for (int i = 0; i < 10; i++) {

            System.out.println(XingUtils.getUUID());
        }
    }

}
