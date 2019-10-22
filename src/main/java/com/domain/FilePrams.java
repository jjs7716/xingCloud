package com.domain;



public class FilePrams {
    private String userId;
    private String fileId;
    private String folderId;


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
