package com.domain;

import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class DiskFile {
    private String fileKey;
    private String updateUid;
    private int cateCount=1;
    private Date updateTime;
    private int zeroDay=0;

    public int getZeroDay() {
        return zeroDay;
    }

    public void setZeroDay(int zeroDay) {
        this.zeroDay = zeroDay;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(String updateUid) {
        this.updateUid = updateUid;
    }

    public int getCateCount() {
        return cateCount;
    }

    public void setCateCount(int cateCount) {
        this.cateCount = cateCount;
    }
}
