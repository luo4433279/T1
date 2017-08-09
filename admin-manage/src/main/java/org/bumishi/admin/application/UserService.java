package org.bumishi.admin.application;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.bumishi.admin.domain.modle.Role;
import org.bumishi.admin.domain.modle.SelectRole;
import org.bumishi.admin.domain.modle.User;
import org.bumishi.admin.domain.modle.UserRole;
import org.bumishi.admin.domain.repository.RoleRepository;
import org.bumishi.admin.domain.repository.UserRepository;
import org.bumishi.admin.domain.service.CacheService;
import org.bumishi.admin.domain.service.RoleSelectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by xieqiang on 2016/9/17.
 */
@Service
//@CacheConfig(cacheNames = "user")
public class UserService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected Md5PasswordEncoder md5PasswordEncoder;

    @Autowired
    protected RoleSelectService roleSelectService;

    @Autowired
    protected RoleRepository roleRepository;
    
    @Autowired
    protected EntityManager  em;
    
	@Autowired
	CacheService cacheService;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

   /* @Caching(
            put = @CachePut(key = "#user.id"),
            evict = @CacheEvict(value = "user-list", key = "'list'")
    )*/
    public User create(User user) {
        validate(user);
        //Assert.hasText(user.getPassword());
        user.setDisabled(false);
        user.setCreatetime(new Date());
        user.setSalt(RandomStringUtils.randomAscii(10));
        user.setPassword(md5PasswordEncoder.encodePassword(user.getPassword(), user.getSalt()));
        userRepository.save(user);
        try{
        	cacheService.put("user:"+user.getId(), user);
        	cacheService.remove("user-list");
        }catch(Exception e){
        	LOGGER.error("缓存移除失败,用户id:{},错误消息:{}",user.getId(),e.getMessage());	
        }
        return user;
    }


    /*@Caching(
            put = @CachePut(key = "#user.id"),
            evict = @CacheEvict(value = "user-list", key = "'list'")
    )*/
    public User modify(User user) {
        Assert.hasText(user.getId());
        User old = get(user.getId());
        if (StringUtils.isNotBlank(user.getUsername())) {
            old.setUsername(user.getUsername());
        }
        if (StringUtils.isNotBlank(user.getPassword())) {
            old.setPassword(md5PasswordEncoder.encodePassword(user.getPassword(), old.getSalt()));
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            old.setEmail(user.getEmail());
        }
        userRepository.save(old);
        try{
        	cacheService.put("user:"+user.getId(), old);
        	cacheService.remove("user-list");
        }catch(Exception e){
        	LOGGER.error("缓存移除失败,用户id:{},错误消息:{}",user.getId(),e.getMessage());	
        }
        return user;
    }

    /*@Caching(
            evict = {@CacheEvict(value = "user-list", key = "'list'"), @CacheEvict(key = "#id")}
    )*/
    public void delete(String id) {
        userRepository.delete(id);
        try{
        	cacheService.remove("user:"+id);
        	cacheService.remove("user-list");
        }catch(Exception e){
        	LOGGER.error("缓存移除失败,用户id:{},错误消息:{}",id,e.getMessage());	
        }
    }

    //@Cacheable
    public User get(String id) {
    	User user = null;
    	try{
	    	if(cacheService.containsKey("user:"+id)){
	    		user = cacheService.get("user:"+id,User.class);
	    	}
    	}catch(Exception e){
	        LOGGER.error("缓存查询失败,用户id:{},错误消息:{}",id,e.getMessage());	
	    }
        if (null==user){
        	user =  userRepository.findOne(id);
        	try{
        		cacheService.put("user:"+id, user);
        	}catch(Exception e){
    	        LOGGER.error("缓存保存失败,用户id:{},错误消息:{}",id,e.getMessage());	
    	    }
    	}
    	
        return user;
    }

    //@Cacheable(value = "user-list", key = "'list'")
    public List<User> list() {
    	List<User> list = null;
    	try{
	    	if(cacheService.containsKey("user-list")){
	    		list = cacheService.getList("user-list",User.class);
	    	}
    	}catch(Exception e){
    		LOGGER.error("获取用户列表缓存失败,错误消息:{}",e.getMessage());	
    	}
        if (null==list) {
    		list =  (List<User>)userRepository.findAll();
    		try{
    			cacheService.put("user-list", list);
    		}catch(Exception e){
    			LOGGER.error(" 保存用户列表缓存失败,错误消息:{}",e.getMessage());	
    		}
    	}
        return list;
    }

    /*@Caching(
       
    		evict = {@CacheEvict(value = "user-list", key = "'list'"), @CacheEvict(key = "#id")}
    )*/
    @Transactional
    public void switchStatus(String id, boolean disable) {
    	Query query = em.createNativeQuery("update user SET disabled=? WHERE id=?");
    	query.setParameter(1, disable);
    	query.setParameter(2, id);
    	query.executeUpdate();
    	 try{
         	cacheService.remove("user:"+id);
         	cacheService.remove("user-list");
         }catch(Exception e){
         	LOGGER.error("缓存移除失败,用户id:{},错误消息:{}",id,e.getMessage());	
         }
    }



    /*@Caching(
            evict = {@CacheEvict(value = "user-nav-menu", key = "#uid")}
    )*/
    @Transactional
    public void grantRole(String uid, List<String> roleIds) {
    	
    	
    	Query query = em.createNativeQuery("DELETE FROM user_role WHERE uid=?");
    	query.setParameter(1, uid);
    	query.executeUpdate();
    	
         if (!CollectionUtils.isEmpty(roleIds)) {
        	 for (String roleId:roleIds){
        	   UserRole userRole = new UserRole();
        	   userRole.setUid(uid);
        	   userRole.setRoleId(roleId);
          	   em.persist(userRole);
             } 
             em.flush();
             em.clear();
         }
         try{
         	cacheService.remove("user-nav-menu:"+uid);
         }catch(Exception e){
         	LOGGER.error("移除用户授权菜单失败,用户id:{},错误消息:{}",uid,e.getMessage());	
         }
    }

    private void validate(User user) {
        Assert.hasText(user.getUsername());
        if (user.isRoot()) {
            throw new IllegalArgumentException("user loginName cannot is root");
        }
    }

    public List<SelectRole> selectRoles(String uid) {
        return roleSelectService.mergeRole((List<Role>) roleRepository.findAll(), roleRepository.getRoles(uid));
    }

}
