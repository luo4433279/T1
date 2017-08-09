package org.bumishi.admin.interfaces.web;

import org.bumishi.admin.security.SecurityUser;
import org.bumishi.admin.security.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qiang.xie
 * @date 2016/11/17
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model) {
    	SecurityUser user =SecurityUtil.getUser();
        return "index";
    }
    @RequestMapping("/index_v1")
    public String index1() {
        return "index_v1";
    }
}
