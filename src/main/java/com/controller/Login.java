package com.controller;


import com.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.FileInfoDaoImpl;
import com.services.FolderInfoDaoImpl;
import com.services.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.CaptchaUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.*;


@Controller
@RequestMapping(path="")
public class Login {



    @Autowired
    private FileInfoDaoImpl fileInfoDao;

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private FolderInfoDaoImpl folderInfoDao;

    @Autowired
    private ObjectMapper mapper;

    static private boolean SUCCESS=true;
    static private boolean FAILURE=false;

    /**
     * 显示回收站已标记的用户文件
     * @param session
     * @return
     * @throws ParseException
     */
    @RequestMapping(path = "/recycle")
    @ResponseBody
    public DataResult recycle(HttpSession session){
        DataResult dataResult=new DataResult();
        Map<String,Object> data=new HashMap<>();
//        //声明回收站保存时间
//        Integer saveDay=30;
        User user = (User) session.getAttribute("user");
        FilePrams filePrams=new FilePrams(user.getUserId(),"");
        //获取用户在回收站的所有文件
        List<FileInfo> fileList = fileInfoDao.queryFileByRecycle(filePrams);
        List<FolderInfo> folderList=folderInfoDao.queryFolderByRecycle(filePrams);
        data.put("fileList",fileList);
        data.put("folderList",folderList);
        dataResult.setStatus(true);
        dataResult.setData(data);
        return dataResult;
    }

    /**
     * 登录验证
     * @param request
     * @param json          前端提交的Json字符串
     * @return
     * @throws IOException
     */
    @RequestMapping(path = "/login")
    @ResponseBody
    public DataResult loginCheck(HttpServletRequest request,@RequestBody String json) throws IOException {
        //结果集
        DataResult dataResult=new DataResult();
        Map<String,Object> data=new HashMap<>();
        //调用jackson解析json并封装用户信息
        User user = mapper.readValue(json, User.class);
        String error1="验证码错误!";
        String error2="用户名或密码错误!";
        //获得存在session中的验证码和用户输入的验证码比对
        String code = (String) request.getSession().getAttribute("loginCode");
        if(!code.equalsIgnoreCase(user.getLoginCode())){
            //验证码错误，返回错误信息
            dataResult.setStatus(FAILURE);
            dataResult.setMsg(error1);
            return dataResult;
        }
        List<User> users = userDao.checkUser(user);
        if(users.size()==0){
            //用户名或密码错误，返回错误信息
            dataResult.setStatus(FAILURE);
            dataResult.setMsg(error2);
            return dataResult;
        }
        else{
            user=users.get(0);
            user.setPassword("");
            user.setStrPassword("");
            data.put("user",user);
            //将用户信息和返回状态封装到result中
            dataResult.setStatus(SUCCESS);
            dataResult.setData(data);
            //更新登录时间
            userDao.updateLastLoginTime(user);
            request.getSession().setAttribute("user",user);
            return dataResult;
        }
    }

    /**
     * 检查用户名是否存在
     * @param json
     * @throws IOException
     */
    @RequestMapping(path = "/checkUser",produces ={"application/json;charset=utf-8"} )
    @ResponseBody
    public DataResult checkUser(@RequestBody String json) throws IOException {
        DataResult dataResult=new DataResult();
        User user = mapper.readValue(json, User.class);
        String username = user.getUsername();
        //检查username是否存在
        List<User> cUser = userDao.checkUsername(username);
        if (cUser.size() <=0) {
            dataResult.setStatus(SUCCESS);
            dataResult.setMsg("用户名可用！");
            return dataResult;
        } else {
            dataResult.setStatus(FAILURE);
            dataResult.setMsg("用户名已存在！");
            return dataResult;
        }
    }

    /**
     * 注册用户
     * @param request
     * @return
     */
    @RequestMapping(path = "/regist")
    public String regist(HttpServletRequest request,User user){
        userDao.insert(user);
        request.getSession().setAttribute("user",user);
        return "redirect:/main";
    }

    /**
     * 退出清空session,返回主页
     * @param request
     * @return
     */
    @RequestMapping(path = "/exit")
    public String exit(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/index.html";
    }

    /**
     * 设置页面,修改密码
     * @return
     */
    @RequestMapping(path = "/setting")
    public void setting(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("text/text;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        User user = (User) request.getSession().getAttribute("user");
        String password = user.getPassword();
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        if(password.equals(oldPassword)){
            user.setPassword(newPassword);
            userDao.updatePassword(user);
            response.getWriter().write("true");
        }
        else{
            response.getWriter().write("false");
        }
    }


    /**
     * 验证码模块
     * @param request
     * @param response
     * @throws Exception
     */

    @ResponseBody
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = CaptchaUtil.createImage();
        //将验证码存入Session
        request.getSession(true).setAttribute("loginCode",objs[0]);
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "jpeg", os);
        os.flush();
    }

}
