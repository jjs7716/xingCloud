package task;

import com.services.FileInfoDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RecycleCtrl {

    @Autowired
    private FileInfoDaoImpl fileInfoDao;
    private int t=0;

    /**
     * 定时更新剩余清空时间
     */
    public void updateDeleteDay(){
        fileInfoDao.upDateDeleteDay();
        System.out.println("执行了"+(t++)+"次");
    }

    /**
     * 定时删除文件
     */
    public void taskDelete(){
        System.out.println("执行了"+(t++)+"次");
//        fileInfoDao.taskDelete();
    }
}
