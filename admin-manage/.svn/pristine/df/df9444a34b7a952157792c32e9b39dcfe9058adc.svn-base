package org.bumishi.admin.application;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.bumishi.admin.domain.modle.Menu;
import org.bumishi.admin.domain.modle.Resource;
import org.bumishi.admin.domain.modle.Role;
import org.bumishi.admin.domain.modle.RoleMenu;
import org.bumishi.admin.domain.modle.RoleResource;
import org.bumishi.admin.domain.modle.SelectMenu;
import org.bumishi.admin.domain.modle.SelectResource;
import org.bumishi.admin.domain.repository.MenuRepository;
import org.bumishi.admin.domain.repository.ResourceRepository;
import org.bumishi.admin.domain.repository.RoleRepository;
import org.bumishi.admin.domain.service.CacheService;
import org.bumishi.admin.domain.service.ResourceSelectService;
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
//@CacheConfig(cacheNames = "role")
public class RoleService {

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected ResourceRepository resourceRepository;

    @Autowired
    protected ResourceSelectService resourceSelectService;

    @Autowired
    protected MenuRepository menuRepository;
    
    @Autowired
    protected EntityManager  em;

    @Autowired
	CacheService cacheService;
    
    public static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

   /* @Caching(
            evict = @CacheEvict(key = "'list'"),
            put = @CachePut(key = "#role.id")
    )*/
    public Role create(Role role) {
        Assert.hasText(role.getName(),"Role name is empty");
        //role.setId(UUID.randomUUID().toString());
         roleRepository.save(role);
         try{
         	cacheService.put("role:"+role.getId(), role);
         	cacheService.remove("role-list");
         }catch(Exception e){
         	LOGGER.error("缓存移除失败,角色id:{},错误消息:{}",role.getId(),e.getMessage());	
         }
        return role;
    }

   /* @Caching(
            evict = @CacheEvict(key = "'list'"),
            put = @CachePut(key = "#newRole.id")
    )*/
    public Role modify(Role newRole) {
        Assert.hasText(newRole.getId(),"Role id is empty");
        Assert.hasText(newRole.getName(),"Role name is empty");
        roleRepository.save(newRole);
        try{
        	cacheService.put("role:"+newRole.getId(), newRole);
        	cacheService.remove("role-list");
        }catch(Exception e){
        	LOGGER.error("缓存移除失败,角色id:{},错误消息:{}",newRole.getId(),e.getMessage());	
        }
        return newRole;
    }

    //@Cacheable
    public Role get(String id){
    	Role role = null;
    	try{
	    	if(cacheService.containsKey("role:"+id)){
	    		role = cacheService.get("role:"+id,Role.class);
	    	}
	    }catch(Exception e){
	        LOGGER.error("缓存查询失败,角色id:{},错误消息:{}",id,e.getMessage());	
	    }
        if (null==role){
        	role =  roleRepository.findOne(id);
        	try{
        		cacheService.put("role:"+id, role);
        	}catch(Exception e){
    	        LOGGER.error("缓存保存失败,角色id:{},错误消息:{}",id,e.getMessage());	
    	    }
    	}
    	
        return role;
    }

    //@Cacheable(key = "'list'")
    public List<Role> list(){
    	List<Role> list = null;
    	try{
	    	if(cacheService.containsKey("role-list")){
	    		list = cacheService.getList("role-list",Role.class);
	    	}
    	}catch(Exception e){
    		LOGGER.error("获取角色列表缓存失败,错误消息:{}",e.getMessage());	
    	}
        if (null==list) {
    		list =  (List<Role>)roleRepository.findAll();
    		try{
    			cacheService.put("role-list", list);
    		}catch(Exception e){
    			LOGGER.error(" 保存角色列表缓存失败,错误消息:{}",e.getMessage());	
    		}
    	}
        return list;
    }

    /*@Caching(
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(key = "#id")}
    )*/
    public void delete(String id){
        roleRepository.delete(id);
        try{
        	cacheService.remove("role:"+id);
        	cacheService.remove("role-list");
        }catch(Exception e){
        	LOGGER.error("缓存移除失败,角色id:{},错误消息:{}",id,e.getMessage());	
        }
    }

    /*@Caching(
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(key = "#id")}
    )*/
    @Transactional
    public void switchStatus(String id,boolean disable){
    	Query query = em.createNativeQuery("update role SET disabled=? WHERE id=?");
    	query.setParameter(1, disable);
    	query.setParameter(2, id);
    	query.executeUpdate();
    	try{
        	cacheService.remove("role:"+id);
        	cacheService.remove("role-list");
        }catch(Exception e){
        	LOGGER.error("缓存移除失败,角色id:{},错误消息:{}",id,e.getMessage());	
        }
    }
    
    @Transactional
    public void grantResource(String roleId, List<String> resources){
    	Query query = em.createNativeQuery("DELETE FROM role_resource WHERE role_id=?");
    	query.setParameter(1, roleId);
    	query.executeUpdate();
         if (!CollectionUtils.isEmpty(resources)) {
        	 for (String resourceId:resources){
          	   RoleResource roleResource = new RoleResource();
          	   roleResource.setResourceId(resourceId);
          	   roleResource.setRoleId(roleId);
          	   em.persist(roleResource);
             } 
             em.flush();
             em.clear();
         }
    }


    //@CacheEvict(value = "user-nav-menu", allEntries = true)
    @Transactional
    public void grantMenu(String roleId, List<String> menus){
        Query query = em.createNativeQuery("DELETE FROM role_menu WHERE role_id=?");
        query.setParameter(1, roleId);
        query.executeUpdate();
        if (!CollectionUtils.isEmpty(menus)) {
           for (String menuId:menus){
        	   RoleMenu roleMenu = new RoleMenu();
        	   roleMenu.setMenuId(menuId);
        	   roleMenu.setRoleId(roleId);
        	   em.persist(roleMenu);
           } 
           em.flush();
           em.clear();
        }
        try{
         	cacheService.remove("user-nav-menu");
         }catch(Exception e){
         	LOGGER.error("移除角色授权菜单失败,用户角色:{},错误消息:{}",roleId,e.getMessage());	
         }
    }

    public List<SelectResource> selectResources(String roleId) {
        return resourceSelectService.mergeResource((List<Resource>) resourceRepository.findAll(), resourceRepository.listByRole(roleId));
    }

    public List<SelectMenu> selectMenus(String roleId) {
        return resourceSelectService.mergeMenus((List<Menu>) menuRepository.findAll(), menuRepository.roleMenus(roleId));
    }
}
