package com.domain;


import java.util.List;

public class FilePrams {
    private String userId;
    private String fileId;
    private String folderId;
    private List<String> fileIdList;
    private List<String> folderIdList;

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

    public FilePrams(){ }


    public FilePrams(String userId) {
        this.userId = userId;
    }


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }
}
