package org.bumishi.admin.application;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.bumishi.admin.domain.modle.Menu;
import org.bumishi.admin.domain.modle.User;
import org.bumishi.admin.domain.repository.MenuRepository;
import org.bumishi.admin.domain.repository.RoleRepository;
import org.bumishi.admin.domain.service.CacheService;
import org.bumishi.admin.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by xieqiang on 2016/9/17.
 */
@Service
//@CacheConfig(cacheNames = "menulist")
public class MenuService {

    @Autowired
    protected MenuRepository menuRepository;

    @Autowired
    protected RoleRepository roleRepository;
    
    @Autowired
    protected EntityManager em;
    
    @Autowired
	CacheService cacheService;
    
    public static final Logger LOGGER = LoggerFactory.getLogger(MenuService.class);


    /*cache操作相关的注解中key都是spel表达式，字符串需要用''*/
    /*@Caching(
            put = @CachePut(key = "#menu.id"),
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(value = "user-nav-menu", allEntries = true)}
    )*/
    public Menu create(Menu menu) {
        validate(menu);
        menuRepository.save(menu);
        try{
        	cacheService.put("menu:"+menu.getId(), menu);
        	cacheService.remove("user-nav-menu");
        	cacheService.remove("menu-list");
        }catch(Exception e){
        	LOGGER.error("缓存移除失败,菜单id:{},错误消息:{}",menu.getId(),e.getMessage());	
        }
        return menu;
    }

    /*@Caching(
            put = @CachePut(key = "#menu.id"),
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(value = "user-nav-menu", allEntries = true)}
    )*/
    public Menu modify(Menu menu) {
        validate(menu);
        menuRepository.save(menu);
        try{
        	cacheService.put("menu:"+menu.getId(), menu);
        	cacheService.remove("user-nav-menu");
        	cacheService.remove("menu-list");
        }catch(Exception e){
        	LOGGER.error("缓存移除失败,菜单id:{},错误消息:{}",menu.getId(),e.getMessage());	
        }
        return menu;
    }


    //@Cacheable
    public Menu get(String code) {
    	Menu menu = null;
    	try{
	    	if(cacheService.containsKey("menu:"+code)){
	    		menu = cacheService.get("menu:"+code,Menu.class);
	    	}
    	}catch(Exception e){
	        LOGGER.error("缓存查询失败,菜单id:{},错误消息:{}",code,e.getMessage());	
	    }
        if (null==menu){
        	menuRepository.findOne(code);
        	try{
        		cacheService.put("menu:"+code, menu);
        	}catch(Exception e){
    	        LOGGER.error("缓存保存失败,菜单id:{},错误消息:{}",code,e.getMessage());	
    	    }
    	}
    	
        return menu;
    }

    /*@Caching(
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(key = "#code"), @CacheEvict(value = "user-nav-menu", allEntries = true)}
    )*/
    @Transactional
    public void delete(String code) {
    	Query query = em.createNativeQuery("DELETE FROM role_menu WHERE menu_id=?");
    	query.setParameter(1, code);
    	query.executeUpdate();
        menuRepository.delete(code);
        try{
        	cacheService.remove("menu:"+code);
        	cacheService.remove("user-nav-menu");
        	cacheService.remove("menu-list");
        }catch(Exception e){
        	LOGGER.error("缓存移除失败,菜单id:{},错误消息:{}",code,e.getMessage());	
        }
    }

    //@Cacheable(key = "'list'")
    public List<Menu> list() {
    	List<Menu> list = null;
    	try{
	    	if(cacheService.containsKey("menu-list")){
	    		list = cacheService.getList("menu-list",Menu.class);
	    	}
    	}catch(Exception e){
    		LOGGER.error("获取菜单列表缓存失败,错误消息:{}",e.getMessage());	
    	}
        if (null==list) {
        	list = (List<Menu>) menuRepository.findAll();
        	for (Menu menu : list) {
				menu.setChildNodes(null);
			}
    		try{
    			cacheService.put("menu-list", list);
    		}catch(Exception e){
    			LOGGER.error(" 保存菜单列表缓存失败,错误消息:{}",e.getMessage());	
    		}
    	}
        Menu.sortByTree(list);
        return list;
    }

    /*@Caching(
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(key = "#menu"), @CacheEvict(value = "user-nav-menu", allEntries = true)}
    )*/
    @Transactional
    public void switchStatus(String menu, boolean disable) {
    	Query query = em.createNativeQuery("update Menu SET disabled=? WHERE id=?");
    	query.setParameter(1, disable);
    	query.setParameter(2, menu);
    	query.executeUpdate();
    	try{
         	cacheService.remove("menu:"+menu);
         	cacheService.remove("menu-list");
         	cacheService.remove("user-nav-menu");
         }catch(Exception e){
         	LOGGER.error("菜单缓存移除失败,menuid:{},错误消息:{}",menu,e.getMessage());	
         }
    }

    //@Cacheable(value = "user-nav-menu")
    public List<Menu> getNavMenus(String uid) {
        List<Menu> list = null;
        try{
	    	if(cacheService.containsKey("user-nav-menu")){
	    		list = cacheService.getList("user-nav-menu",Menu.class);
	    	}
    	}catch(Exception e){
    		LOGGER.error("获取菜单缓存失败,错误消息:{}",e.getMessage());	
    	}
        if (null==list) {
	        if (SecurityUtil.isRoot()) {
	            list = (List<Menu>) menuRepository.findAll();
	        } else {
	            list = menuRepository.getNavMenus(uid);
	        }
	        list = (List<Menu>) Menu.buildTree(list);
	        try{
    			cacheService.put("user-nav-menu", list);
    		}catch(Exception e){
    			LOGGER.error(" 保存菜单缓存失败,错误消息:{}",e.getMessage());	
    		}
        }
        return list;
    }

    private void validate(Menu menu) {
        Assert.hasText(menu.getId());
        Assert.hasText(menu.getLabel());
    }

}
