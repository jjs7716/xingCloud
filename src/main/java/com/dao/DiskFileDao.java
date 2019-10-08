package com.dao;

import com.domain.DiskFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiskFileDao {

    /**
     * 插入信息
     */
    void insertFileInfo(DiskFile diskFile);

    /**
     * 添加引用次数
     */
    void addCateCount(String fileKey);

    /**
     * 批量添加引用次数
     * @param keyList
     */
    void batchAddCateCount(@Param("keyList") List<String> keyList);

    /**
     * 减少引用次数
     */
    void subtractCateCount(String fileKey);

    /**
     * 批量减少引用次数
     * @param keyList
     */
    void batchSubtractCateCount(@Param("keyList") List<String> keyList);
    /**
     * 查询文件的引用次数
     * @return
     */
    DiskFile queryCateCount(String fileKey);


    /**
     * 查询引用次数小于0的文件
     * @return
     */
    List<DiskFile> queryFileInfoByCateCount();
}
