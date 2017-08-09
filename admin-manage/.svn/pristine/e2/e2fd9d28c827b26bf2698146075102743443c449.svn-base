package org.bumishi.admin.interfaces.web;

import org.apache.commons.lang3.StringUtils;
import org.bumishi.admin.application.MenuService;
import org.bumishi.admin.security.SecurityUser;
import org.bumishi.admin.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qiang.xie
 * @date 2016/9/27
 */
@ControllerAdvice
public class PublicAdvice {

    @Autowired
    protected MenuService menuService;

    @ExceptionHandler
    public void handleControllerException(HttpServletRequest request, HttpServletResponse response, Throwable ex) throws IOException {
        //ex.printStackTrace();
        String ajax = request.getHeader("X-Requested-With");//判断是不是ajax请求
        response.setCharacterEncoding("utf-8");
        if (StringUtils.isBlank(ajax)) {//页面请求
            response.sendRedirect("/error");
        } else {//ajax请求
            response.getWriter().println("出错了:" + ex.getMessage());
        }

    }
    //在每个请求都把用户和菜单传给前端
    @ModelAttribute
    public void addCommonModel(Model model, HttpServletRequest request) {
        SecurityUser user = SecurityUtil.getUser();
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("navs", menuService.getNavMenus(user.getUid()));
        }
    }


}
