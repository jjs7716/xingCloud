package com.domain;

public class FilePrams {
    private String userId;
    private String folderId;

    public FilePrams(){ }


    public FilePrams(String userId) {
        this.userId = userId;
    }

    public FilePrams(String userId, String folderId) {
        this.userId = userId;
        this.folderId = folderId;
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
