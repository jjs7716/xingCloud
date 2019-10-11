package com.domain;

import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class FolderInfo {
    private String folderId;
    private String folderName;
    private String folderUid;
    private Date updateTime;
    private String parentId="";
    private String parentName="";
    private int fileNum;
    private int folderNum;
    private Date deleteTime;
    private int deleteDay=-1;
    private int folderState=0;
    private int isFolder=1;

    public int getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(int isFolder) {
        this.isFolder = isFolder;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderUid() {
        return folderUid;
    }

    public void setFolderUid(String folderUid) {
        this.folderUid = folderUid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public int getDeleteDay() {
        return deleteDay;
    }

    public void setDeleteDay(int deleteDay) {
        this.deleteDay = deleteDay;
    }

    public int getFolderState() {
        return folderState;
    }

    public void setFolderState(int folderState) {
        this.folderState = folderState;
    }

    public int getFileNum() {
        return fileNum;
    }

    public void setFileNum(int fileNum) {
        this.fileNum = fileNum;
    }

    public int getFolderNum() {
        return folderNum;
    }

    public void setFolderNum(int folderNum) {
        this.folderNum = folderNum;
    }
}
