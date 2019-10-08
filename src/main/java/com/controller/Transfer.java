package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class Transfer {

    /**
     * 中转页面
     * @param request
     * @return
     */
    @RequestMapping(path="/transfer")
    public String transfer(HttpServletRequest request){
        String transfer = request.getParameter("transfer");
        //跳转到添加页面
        if("add".equals(transfer)){
            return "handle/upload";
        }
        if("setting".equals(transfer)){
            return "setting";
        }
        if("recycle".equals(transfer)){
            return "handle/recycle";
        }
        return "main";
    }

}
