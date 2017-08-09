package org.bumishi.admin.interfaces.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bumishi.admin.annotation.SystemServiceLog;
import org.bumishi.admin.application.MenuService;
import org.bumishi.admin.domain.modle.Menu;
import org.bumishi.admin.interfaces.facade.assembler.MenuAssembler;
import org.bumishi.admin.interfaces.facade.commondobject.MenuCreateCommand;
import org.bumishi.admin.interfaces.facade.commondobject.MenuUpdateCommond;
import org.bumishi.admin.security.SecurityUser;
import org.bumishi.admin.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author qiang.xie
 * @date 2016/9/18
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
	
	private static Log logger=LogFactory.getLog(MenuController.class);

    @Autowired
    protected MenuService menuService;
    
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    @SystemServiceLog(module = "菜单管理", methods = "新增菜单")
    public String create(MenuCreateCommand menu) {
        menuService.create(MenuAssembler.createCommendToDomain(menu));
        if (logger.isDebugEnabled()){
        	SecurityUser user =SecurityUtil.getUser();
        	logger.debug(user.getUsername()+"新增菜单成功!");
        }
        return "redirect:/menu";
    }


    @RequestMapping(value = "/{id}/modify", method = RequestMethod.POST)
    @SystemServiceLog(module = "菜单管理", methods = "修改菜单")
    public String modify(@PathVariable("id") String id, MenuUpdateCommond menu) {
        menuService.modify(MenuAssembler.updateCommendToDomain(id, menu));
        return "redirect:/menu";
    }


    @RequestMapping(value = "/{id}/status", method = RequestMethod.PUT)
    @ResponseBody
    @SystemServiceLog(module = "菜单管理", methods = "切换状态")
    public void switchStatus(@PathVariable("id") String id, @RequestParam("disable") boolean disable) {
        menuService.switchStatus(id,disable);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemServiceLog(module = "菜单管理", methods = "删除菜单")
    public void delete(@PathVariable("id") String id) {
         menuService.delete(id);
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    @SystemServiceLog(module = "菜单管理", methods = "查看菜单")
    public String toform(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "parent", required = false) boolean parent, Model model) {
        String url = null;
        if (StringUtils.isNotBlank(id) && !parent) {
        	Menu menu = menuService.get(id);
        	String[] items = menu.getPath().split(",");
			Menu parentMenu = menuService.get(items[items.length-1]);
            model.addAttribute("parentTitle", parentMenu == null?null:parentMenu.getLabel());
            model.addAttribute("menu", menu);
            url = "/menu/" + id + "/modify";
        } else {
            url = "/menu/add";
            if (StringUtils.isNotBlank(id)) {
                model.addAttribute("parentPath", id);
                String[] items = id.split(",");
    			Menu parentMenu = menuService.get(items[items.length-1]);
                model.addAttribute("parentTitle", parentMenu == null?null:parentMenu.getLabel());
            }
        }
        model.addAttribute("api", url);
        return "menu/form";
    }

    @RequestMapping(method = RequestMethod.GET)
    @SystemServiceLog(module = "菜单管理", methods = "查询菜单")
    public String list(Model model) {
        model.addAttribute("list", menuService.list());
        return "menu/list";
    }
    
    @RequestMapping(value ="/{menuId}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String ,String> checkMenu(@PathVariable("menuId") String menuId){
    	Menu menu = menuService.get(menuId);
    	Boolean result = (menu == null?false:true);
    	Map<String ,String> map = new HashMap<String ,String>();
    	map.put("result", result.toString());
    	return map;
    }


}
