package com.domain;


import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class FileInfo {
    private String fileId;
    private String fileName;
    private Long fileSize;
    private String fileUid;
    private Date updateTime;
    private String fileKey="";
    private String parentId="";
    private String parentName="";
    private String strFileSize;
    private Date deleteTime;
    private String strDeleteTime;
    private Integer deleteDay=-1;
    private String fileType;
    private int fileState=0;
    private int isFolder=0;

    public int getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(int isFolder) {
        this.isFolder = isFolder;
    }

    public String getStrDeleteTime() {
        return strDeleteTime;
    }

    public void setStrDeleteTime(String strDeleteTime) {
        this.strDeleteTime = strDeleteTime;
    }

    public int getFileState() {
        return fileState;
    }

    public void setFileState(int fileState) {
        this.fileState = fileState;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileUid() {
        return fileUid;
    }

    public void setFileUid(String fileUid) {
        this.fileUid = fileUid;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
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

    public String getStrFileSize() {
        return strFileSize;
    }

    public void setStrFileSize(String strFileSize) {
        this.strFileSize = strFileSize;
    }


    public Integer getDeleteDay() {
        return deleteDay;
    }

    public void setDeleteDay(Integer deleteDay) {
        this.deleteDay = deleteDay;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
