package com.dao;

import com.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FolderInfoDao {

    /**
     * 新建文件夹
     * @param folderInfo
     */
    void creatFolder(FolderInfo folderInfo);

    /**
     * 批量插入文件夹信息
     * @param folderInfoList
     */
    void insertFolders(@Param("folderInfoList") List<FolderInfo> folderInfoList);


    /**
     * 根据文件夹ID查其子文件夹信息
     * @param filePrams
     * @return
     */
    List<FolderInfo> queryChildrenFolder(FilePrams filePrams);

    /**
     * 根据ID查询文件夹信息
     * @param filePrams
     * @return
     */
    FolderInfo queryFolderById(FilePrams filePrams);

    /**
     * 根据ID列表查询文件夹信息
     * @param filePrams
     * @param folderIdList
     * @return
     */
    List<FolderInfo> queryFolderByIdList(@Param("filePrams")FilePrams filePrams,@Param("folderIdList")List<String> folderIdList);

    /**
     * 查找用户在回收站的所有文件夹
     * @param filePrams
     * @return
     */
    List<FolderInfo> queryFolderByRecycle(FilePrams filePrams);

    /**
     * 重命名文件夹
     * @param folderId                  指定要重命名的文件夹ID
     * @param newFolderName     文件夹新的名字
     */
    void reFolderName(@Param("folderId") String folderId,@Param("newFolderName") String newFolderName);

    /**
     * 移动文件夹
     * @param moveFilePrams
     */
    void moveFolder(MoveFilePrams moveFilePrams);

    /**
     * 移动文件
     * @param moveFilePrams
     */
    void moveFile(MoveFilePrams moveFilePrams);

    /**
     * 恢复文件夹
     * @param folderIdList          要恢复的文件夹的ID列表
     * @param folderUid             文件夹所属的用户ID
     */
    void recoverFolder(@Param("folderIdList") List<String> folderIdList,@Param("folderUid") String folderUid);

    /**
     * 删除文件夹到回收站
     * @param folderIdList      要删除的文件夹ID列表
     * @param folderUid         文件夹所属的用户ID
     */
    void recycleFolder(@Param("folderIdList") List<String> folderIdList,@Param("folderUid") String folderUid);

    /**
     * 彻底删除文件夹
     * @param folderIdList      要删除的文件夹ID列表
     * @param folderUid         文件夹所属的用户ID
     */
    void deleteFolder(@Param("folderIdList") List<String> folderIdList,@Param("folderUid") String folderUid);

    /**
     * 查找父文件夹
     * @param filePrams
     * @return
     */
    FolderInfo queryParent(FilePrams filePrams);
}
