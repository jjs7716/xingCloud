package utils;

import com.domain.FilePrams;
import com.domain.User;

import javax.servlet.http.HttpServletRequest;

public class BaseUtil {

    public static String IMG_FOLDER_NAME="img";
    public static String DEFAULT_DELETE_TIME="2099-01-01 00:00:00";
    public static int DEFAULT_DELETE_DAY=99999;
    /**
     * 根据系统属性获得用户主目录
     */
    private static String userHome = System.getProperties().getProperty("user.home");
    /**
     * 根据系统属性获得路径间隔符
     */
    private static String property = System.getProperty("file.separator");
    /**
     * 头像文件夹
     * @param user
     * @return
     */
    public static String userImgPath(User user){
        return userHome+property+IMG_FOLDER_NAME+property+user.getUserId();
    }

    /**
     * 获取文件夹路径
     * @return
     */
    public static String getFolder(){
        return userHome+property+"UpLoad"+property;
    }


    public static User getUser(HttpServletRequest request){
        return (User) request.getSession().getAttribute("user");
    }

    public static String getUserId(HttpServletRequest request){
        if(getUser(request)==null){
            return "";
        }
        return getUser(request).getUserId();
    }
}
