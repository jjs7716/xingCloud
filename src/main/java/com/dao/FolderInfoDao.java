package com.dao;

import com.domain.FilePrams;
import com.domain.FolderInfo;
import com.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FolderInfoDao {

    /**
     * 新建文件夹
     * @param folderInfo
     */
    void creatFolder(FolderInfo folderInfo);

    /**
     * 查找用户所有文件夹
     * @param filePrams
     * @return
     */
    List<FolderInfo> queryFolder(FilePrams filePrams);

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
     * 移动到指定文件夹
     * @param fileIdList    移动的文件id列表
     * @param folderId      指定的文件夹id
     */
    void moveToFolder(@Param("fileIdList") List<String> fileIdList,@Param("folderId") String folderId);

    /**
     * 复制到指定文件夹
     * @param fileIdList    复制的文件id列表
     * @param folderId      指定的文件夹id
     */
    void copyToFolder(@Param("fileIdList") List<String> fileIdList,@Param("folderId") String folderId);

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
}
