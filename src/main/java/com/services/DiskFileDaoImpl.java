package com.services;

import com.dao.DiskFileDao;
import com.domain.DiskFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiskFileDaoImpl implements DiskFileDao {

    @Autowired
    private DiskFileDao diskFileDao;

    @Override
    public void insertFileInfo(DiskFile diskFile) {
        diskFileDao.insertFileInfo(diskFile);
    }

    @Override
    public void addCateCount(String fileKey) {
        diskFileDao.addCateCount(fileKey);
    }

    @Override
    public void batchAddCateCount(List<String> keyList) {
        diskFileDao.batchAddCateCount(keyList);
    }

    @Override
    public void subtractCateCount(String fileKey) {
        diskFileDao.subtractCateCount(fileKey);
    }

    @Override
    public void batchSubtractCateCount(List<String> keyList) {
        diskFileDao.batchSubtractCateCount(keyList);
    }

    @Override
    public DiskFile queryCateCount(String fileKey) {
        return diskFileDao.queryCateCount(fileKey);
    }

    @Override
    public List<DiskFile> queryFileInfoByCateCount() {
        return diskFileDao.queryFileInfoByCateCount();
    }


}
