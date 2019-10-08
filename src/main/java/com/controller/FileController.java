package com.controller;

import com.domain.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.DiskFileDaoImpl;
import com.services.FileInfoDaoImpl;
import com.services.FolderInfoDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import utils.BaseUtil;
import utils.DateUtil;
import utils.DownLoadUtils;
import utils.XingUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(path="/file")
public class FileController {

    private String video=".video";
    private String music=".music";
    private String doc=".doc";
    private String img=".img";
    private String add="add";
    private String setting="setting";


    @Autowired
    private FileInfoDaoImpl fileInfoDaoImpl;

    @Autowired
    private Format format;

    @Autowired
    private DiskFileDaoImpl diskFileDaoImpl;

    @Autowired
    private FolderInfoDaoImpl folderInfoDao;

    @Autowired
    private ObjectMapper mapper;
    /**
     * 主页面,显示用户所有未标记删除文件和文件夹
     * @param request
     * @return
     */
    @RequestMapping(path = "/main")
    public DataResult main(HttpServletRequest request) {
        DataResult dataResult=new DataResult();
        User user= (User) request.getSession().getAttribute("user");
        FilePrams filePrams=new FilePrams(user.getUserId(),"");
        if(user==null){
            dataResult.setStatus(false);
            return dataResult;
        }
        List<FileInfo> fileList = fileInfoDaoImpl.queryFile(filePrams);
        List<FolderInfo> folderList=folderInfoDao.queryFolder(filePrams);
        Map<String,Object> data=new HashMap<>();
        data.put("fileList",fileList);
        data.put("folderList",folderList);
        request.getSession().removeAttribute("searchName");
        dataResult.setData(data);
        dataResult.setStatus(true);
        return dataResult;
    }

    /**
     * Spring文件上传
     * @param request
     */
    @RequestMapping(path = "/upload")
    public String upload(HttpServletRequest request) throws IOException {
        User user= (User) request.getSession().getAttribute("user");
        String userId=user.getUserId();
        //强转使用AJAX来获取,传统MultipartFile获取不到
        MultipartHttpServletRequest ms= (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = ms.getFileMap();
        Collection<MultipartFile> values = fileMap.values();
        if(values.size()==0){
            return "handle/upload";
        }
        //获取文件夹的真实路径
        String path = BaseUtil.getFolder();
        //遍历上传文件列表
        for (Map.Entry<String, MultipartFile> multipartFileEntry : fileMap.entrySet()) {
            DiskFile diskFile=new DiskFile();
            FileInfo fileInfo=new FileInfo();
            Date nowDate = DateUtil.nowDate();
            String key = multipartFileEntry.getKey();
            MultipartFile value = multipartFileEntry.getValue();
            //获取文件名
            String filename = value.getOriginalFilename();
            //获取文件大小
            long size = value.getSize();
            //设置文件大小信息
            fileInfo.setFileSize(size);
            //设置文件所属用户信息
            fileInfo.setFileUid(userId);
            //设置文件上传时间信息
            fileInfo.setUpdateTime(nowDate);
            //设置格式化后的文件大小信息
            fileInfo.setStrFileSize(XingUtils.formatFileSize(size));
            //设置文件名信息
            fileInfo.setFileName(filename);
            //将文件转换为流
            InputStream is = value.getInputStream();
            String fileKey=DigestUtils.md5DigestAsHex(is);
            //设置文件的md5信息
            fileInfo.setFileKey(fileKey);
            //根据当前时间和文件名计算md5
            String currentTime = Long.toString(System.currentTimeMillis());
            byte[] fileBytes=(currentTime+filename).getBytes();
            String fileId = DigestUtils.md5DigestAsHex(fileBytes);
            fileInfo.setFileId(fileId);
            //上传文件,以后用md5来标记文件,以后用来减少重复
            try {
                //根据md5判断服务器上是否有重复的文件
                if(diskFileDaoImpl.queryCateCount(fileKey)==null){
                    value.transferTo(new File(path,fileKey));
                    //第一次上传,保存上传信息
                    diskFile.setFileKey(fileKey);
                    diskFile.setUpdateUid(userId);
                    diskFile.setUpdateTime(nowDate);
                    //插入文件引用信息
                    diskFileDaoImpl.insertFileInfo(diskFile);
                }else{
                    //有重复文件,就不上传,只加引用次数
                    diskFileDaoImpl.addCateCount(fileKey);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //将文件信息插入数据库
            fileInfoDaoImpl.insertInfo(fileType(fileInfo));
            request.getSession().setAttribute(key,"成功上传!");
        }
        return "main";
    }

    /**
     * 单个文件下载
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(path = "/download")
    public String downLoadFile(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String fileId = request.getParameter("fileId");
        List<FileInfo> byId = fileInfoDaoImpl.queryById(fileId);
        String fileName= byId.get(0).getFileName();
        String fileKey=byId.get(0).getFileKey();
        //调用下载方法
        down(fileKey,fileName,request,response);
        return "main";
    }

    /**
     * 批量下载
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(path = "/downloads")
    public void downLoadFiles(HttpServletRequest request, HttpServletResponse response, @RequestBody String json) throws IOException {
        DataResult dataResult=new DataResult();
        //获取选中的ID封装为Map
        Map<String, String[]> map = request.getParameterMap();
        //将Map转换为List集合
        List<String> idList = XingUtils.ValueToList(map);
//        if(idList.size()<=0){
//            dataResult.setStatus(false);
//            return dataResult;
//        }
        //根据fileId查找文件名,并返回集合 注:不是真实路径
        List<FileInfo> fileList=fileInfoDaoImpl.queryById(idList);
        //判断如果是一个文件,就不压缩,直接下载
        if(fileList.size()==1){
            down(fileList.get(0).getFileKey(),request,response);
            return;
//            return "main";
        }
        //压缩文件名
        String zipName=fileList.get(0).getFileName().substring(0,fileList.get(0).getFileName().length()-4)+"等多个文件.zip";
        //压缩后文件的真实路径
        String pathName=BaseUtil.getFolder();
        //压缩文件
        XingUtils.createZipFile(fileList,pathName,zipName);
        //下载压缩后的文件
        down(zipName,request,response);
        //删除压缩后的文件
        new File(pathName+zipName).delete();
    }


    /**
     * 压缩文件时调用
     * 当压缩文件时没有文件Id 则将压缩后的文件名赋给文件Id
     * @param filename
     * @param request
     * @param response
     * @throws IOException
     */
    private void down(String filename,HttpServletRequest request,HttpServletResponse response) throws IOException {
        down(filename,filename,request,response);
    }

    /**
     * 下载文件的方法
     * @param filename  文件名
     * @param request
     * @param response
     * @throws IOException
     */
    private void down(String fileKey,String filename,HttpServletRequest request,HttpServletResponse response) throws IOException {
        //设置编码
        response.setContentType("application/x-rar-compressed;charset=UTF-8");
        //输入流对象
        FileInputStream file;
        //输出流对象
        ServletOutputStream sos;
        //创建域对象
        ServletContext servletContext = request.getServletContext();
        //建立输入流,把文件加载进内存
        file=new FileInputStream(BaseUtil.getFolder()+fileKey);
        //根据文件后缀名来判断文件类型
        String mimeType = servletContext.getMimeType(filename);
        filename = DownLoadUtils.getContentDisposition(filename, request);
        //设置响应头
        response.setHeader("context-type",mimeType);
        response.setHeader("content-disposition",filename);
        //建立输出流
        sos=response.getOutputStream();
        byte[] bytes=new byte[1024*8];
        int len=0;
        while((len=file.read(bytes))!=-1){
            sos.write(bytes,0,len);
        }
        if(file!=null){
            file.close();
        }
        if(sos!=null){
            sos.close();
        }
    }

//    /**
//     * 单个删除到回收站
//     * @param request
//     * @return
//     */
//    @RequestMapping(path = "/recycle")
//    public String recycle(HttpServletRequest request){
//        //获得文件ID
//        String fileId = request.getParameter("fileId");
//        //标记删除到回收站
//        fileInfoDaoImpl.recycle(DateUtil.nowStrTime(),fileId);
//        return "success";
//    }

//    /**
//     * 批量删除到回收站
//     * @param request
//     * @throws IOException
//     */
//    @RequestMapping(path = "/batchRecycle")
//    public String batchRecycle(HttpServletRequest request){
//        Map<String, String[]> map = request.getParameterMap();
//        List<String> idList = Arrays.asList(map.get("fileId"));
//        if(idList.size()>0){
//            //标记删除到回收站
//            fileInfoDaoImpl.batchRecycle(DateUtil.nowStrTime(),idList);
//        }
//        return "success";
//    }

//    /**
//     * 单个恢复
//     * @param request
//     * @return
//     */
//    @RequestMapping(path = "/recover")
//    public String recover(HttpServletRequest request) {
//        String userId = ((User) request.getSession().getAttribute("user")).getUserId();
//        //获得文件ID
//        String fileId = request.getParameter("fileId");
//        String fileKey=request.getParameter("fileKey");
//        //标记删除到回收站
//        fileInfoDaoImpl.recoverFile(fileId,userId);
//        diskFileDaoImpl.addCateCount(fileKey);
//        return "success";
//    }

//    /**
//     * 批量恢复
//     * @param request
//     * @throws IOException
//     */
//    @RequestMapping(path = "/batchRecover")
//    public String batchRecover(HttpServletRequest request) {
//        String userId = ((User) request.getSession().getAttribute("user")).getUserId();
//        Map<String, String[]> map = request.getParameterMap();
//        List<String> fileIdLIst = Arrays.asList(map.get("fileId"));
//        List<String> keyList = Arrays.asList(map.get("fileKey"));
//        if(fileIdLIst.size()>0){
//            fileInfoDaoImpl.recoverFile(fileIdLIst,userId);
//            diskFileDaoImpl.batchAddCateCount(keyList);
//        }
//        return "success";
//    }
//
//    /**
//     * 单个删除
//     * @param request
//     * @return
//     */
//    @RequestMapping(path = "/delete")
//    public String delete(HttpServletRequest request) {
//        String userId = ((User) request.getSession().getAttribute("user")).getUserId();
//        //获得文件ID
//        String fileId = request.getParameter("fileId");
//        String fileKey = request.getParameter("fileKey");
//        //从数据库删除文件信息
//        fileInfoDaoImpl.deleteFile(fileId,userId);
//        //减少文件的引用次数
//        diskFileDaoImpl.subtractCateCount(fileKey);
//        return "success";
//    }
//
//    /**
//     * 批量删除
//     * @param request
//     * @throws IOException
//     */
//    @RequestMapping(path = "/batchDelete")
//    public String batchDelete(HttpServletRequest request ,@RequestBody String json){
//        Map<String, String[]> map = request.getParameterMap();
//        List<String> idList = Arrays.asList(map.get("fileId"));
//        List<String> keyList = Arrays.asList(map.get("fileKey"));
//        if(keyList.size()>0){
//            fileInfoDaoImpl.batchDelete(idList);
//            diskFileDaoImpl.batchSubtractCateCount(keyList);
//        }
//        return "success";
//    }

    /**
     * 根据条件模糊查找
     * @param request
     * @return
     */
    @RequestMapping(path = "/search")
    public DataResult search(HttpServletRequest request, @RequestBody String json) throws IOException {
        Map<String,Object> data = mapper.readValue(json, Map.class);
        String userId = ((User) request.getSession().getAttribute("user")).getUserId();
        String type = (String) data.get("type");
        String search = (String) data.get("search");
        search=(search==null)?"":search;
        List<FileInfo> fileList;
        if(type==null){
            fileList=fileInfoDaoImpl.search(userId,search);
            data.put("fileList",fileList);
            return main(request);
        }else{
            type="."+type;
            if(video.equals(type)){
                fileList=fileInfoDaoImpl.searchByVideo(userId);
            }else if(music.equals(type)) {
                fileList=fileInfoDaoImpl.searchByMusic(userId);
            }else if(doc.equals(type)){
                fileList=fileInfoDaoImpl.searchByDoc(userId);
            }else if(img.equals(type)){
                fileList=fileInfoDaoImpl.searchByImg(userId);
            }else {
                fileList=fileInfoDaoImpl.searchByOther(userId);
            }
            data.put("fileList",fileList);
            DataResult dataResult=main(request);
            dataResult.setData(data);
            return dataResult;
        }
    }

    /**
     * 删除硬盘上的文件
     * @param path
     */
    private void systemDelete(String path){
        File file=new File(path);
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 重命名文件
     */
    @RequestMapping(path = "/reFileName")
    public String reFileName(HttpServletRequest request){
        String isFolder = request.getParameter("isFolder");
        String fileId = request.getParameter("fileId");
        String newFileName = request.getParameter("newFileName");
        if("0".equals(isFolder)){
            fileInfoDaoImpl.reFileName(fileId,newFileName);
        }else {
            folderInfoDao.reFolderName(fileId,newFileName);
        }
        return "success";
    }

    /**
     * 根据后缀判断文件格式并存入类中
     * @param fileInfo
     * @return
     */
    private FileInfo fileType(FileInfo fileInfo){
        String filename=fileInfo.getFileName();
        int t=0;
        //判断文件类型
        for (String s : format.getVideoList()) {
            if(filename.endsWith(s)){
                fileInfo.setFileType(".video");
                t++;
                break;
            }
        }
        if (t==0) {
            for (String s : format.getMusicList()) {
                if (filename.endsWith(s)) {
                    fileInfo.setFileType(".music");
                    t++;
                    break;
                }
            }
        }
        if(t==0){
            for (String s : format.getImgList()) {
                if(filename.endsWith(s)){
                    fileInfo.setFileType(".img");
                    t++;
                    break;
                }
            }
        }
        if(t==0){
            for (String s : format.getDocList()) {
                if(filename.endsWith(s)){
                    fileInfo.setFileType(".doc");
                    t++;
                    break;
                }
            }
        }
        if(t==0){
            fileInfo.setFileType(".other");
        }
        return fileInfo;
    }

}
