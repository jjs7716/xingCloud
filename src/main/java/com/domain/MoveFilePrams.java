package com.domain;

import java.util.List;

public class MoveFilePrams {
    private String userId;
    private List<String> fileIdList;
    private List<String> folderIdList;
    private String selectType;
    private String targetId;
    private String targetName;

    @Override
    public String toString() {
        return "MoveFilePrams{" +
                "fileIdList=" + fileIdList +
                ", folderIdList=" + folderIdList +
                ", selectType='" + selectType + '\'' +
                ", targetId='" + targetId + '\'' +
                ", targetName='" + targetName + '\'' +
                '}';
    }

    public MoveFilePrams(){

    }
    public MoveFilePrams(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public List<String> getFileIdList() {
        return fileIdList;
    }

    public void setFileIdList(List<String> fileIdList) {
        this.fileIdList = fileIdList;
    }

    public List<String> getFolderIdList() {
        return folderIdList;
    }

    public void setFolderIdList(List<String> folderIdList) {
        this.folderIdList = folderIdList;
    }

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
}
