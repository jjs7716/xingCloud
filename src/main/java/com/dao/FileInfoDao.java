package com.dao;

import com.domain.FileInfo;
import com.domain.FilePrams;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface FileInfoDao {
    /**
     * 根据用户名和父文件夹查找他的文件
     * @param filePrams
     * userId,parentId
     * @return
     */
    List<FileInfo> queryFile(FilePrams filePrams);

    /**
     * 显示回收站文件
     * @param filePrams
     * @return
     */
    List<FileInfo> queryFileByRecycle(FilePrams filePrams);


    /**
     * 插入文件信息
     * @param fileInfo
     */
    void insertInfo(FileInfo fileInfo);

    /**
     * 批量插入文件信息
     * @param fileInfoList
     */
    void insertFiles(List<FileInfo> fileInfoList);

    /**
     * 根据文件Id查找文件
     * @param
     * @return
     */
    List<FileInfo> queryById(FilePrams filePrams);

    /**
     * 根据文件ID列表查找文件
     * @param filePrams
     * @param fileIdList
     * @return
     */
    List<FileInfo> queryByIdList(@Param("filePrams")FilePrams filePrams,@Param("fileIdList")List<String> fileIdList);

    /**
     * 根据文件夹ID列表查找文件信息
     * @param filePrams
     * @param folderIdList
     * @return
     */
    List<FileInfo> queryFileByFolderIdList(@Param("filePrams")FilePrams filePrams,@Param("folderIdList")List<String> folderIdList);

    /**
     * 根据文件唯一标识码查找是否重复
     * @param fileKey
     * @return
     */
    List<String> queryByFileKey(String fileKey);

    /**
     * 删除到回收站
     * 注:多个参数,要加@Param注解,指定和哪个参数对应,注解里的值要和SQL语句里的参数相同
     * 例:@Param("deleteTime") 和SQL里的#{deleteTime}要相同
     * 注:如果是list集合,要和for each里的collection属性对应
     * 例:@Param("list")和collection="list"
     * @param fileIdList
     * @param fileUid
     */
    void recycleFile(@Param("fileIdList") List<String> fileIdList,@Param("fileUid") String fileUid,@Param("folderIdList") List<String> folderIdList);


    /**
     * 还原文件
     * @param fileIdList
     */
    void recoverFile(@Param("fileIdList") List<String> fileIdList,@Param("fileUid") String fileUid,@Param("folderIdList") List<String> folderIdList);


    /**
     * 彻底删除
     * @param fileIdList
     */
    void deleteFile(@Param("fileIdList") List<String> fileIdList,@Param("fileUid") String fileUid,@Param("folderIdList") List<String> folderIdList);

    /**
     * 条件搜索
     * @param userId
     * @param searchName
     * @return
     */
    List<FileInfo> search(@Param("userId") String userId,@Param("searchName") String searchName);


    /**
     * 定时更新剩余清空时间
     */
    void upDateDeleteDay();


    /**
     * 定时删除回收站文件
     */
    void taskDelete();

    /**
     * 重命名文件
     * @param fileId
     */
    void reFileName(@Param("fileId")String fileId,@Param("newFileName") String newFileName);

    List<FileInfo> searchByVideo(String userId);

    List<FileInfo> searchByMusic(String userId);

    List<FileInfo> searchByDoc(String userId);

    List<FileInfo> searchByImg(String userId);

    List<FileInfo> searchByOther(String userId);

//    Integer getDeleteDay(FileInfo fileInfo);

}
