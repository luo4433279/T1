package org.bumishi.admin.application;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.bumishi.admin.domain.modle.Resource;
import org.bumishi.admin.domain.modle.User;
import org.bumishi.admin.domain.repository.ResourceRepository;
import org.bumishi.admin.domain.repository.RoleRepository;
import org.bumishi.admin.domain.service.CacheService;
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
//@CacheConfig(cacheNames = "resource")
public class ResourceService {

    @Autowired
    protected ResourceRepository resourceRepository;

    @Autowired
    protected RoleRepository roleRepository;
    
    @Autowired
    protected EntityManager em;
    
    @Autowired
	CacheService cacheService;
    
    public static final Logger LOGGER = LoggerFactory.getLogger(ResourceService.class);

    /*@Caching(
            put = @CachePut(key = "#resource.id"),
            evict = @CacheEvict(key = "'list'")
    )*/
    public Resource create(Resource resource) {
        validate(resource);
        resourceRepository.save(resource);
        try{
        	cacheService.put("resource:"+resource.getId(), resource);
        	cacheService.remove("resource-list");
        }catch(Exception e){
        	LOGGER.error("缓存移除失败,资源id:{},错误消息:{}",resource.getId(),e.getMessage());	
        }
        return resource;
    }

    /*@Caching(
            put = @CachePut(key = "#resource.id"),
            evict = @CacheEvict(key = "'list'")
    )*/
    public Resource modify(Resource resource) {
        validate(resource);
        resourceRepository.save(resource);
        try{
        	cacheService.put("resource:"+resource.getId(), resource);
        	cacheService.remove("resource-list");
        }catch(Exception e){
        	LOGGER.error("缓存移除失败,资源id:{},错误消息:{}",resource.getId(),e.getMessage());	
        }
        return resource;
    }

    //@Cacheable
    public Resource get(String code){
    	Resource resource = null;
    	try{
	    	if(cacheService.containsKey("resource:"+code)){
	    		resource = cacheService.get("resource:"+code,Resource.class);
	    	}
    	}catch(Exception e){
	        LOGGER.error("缓存查询失败,资源id:{},错误消息:{}",code,e.getMessage());	
	    }
        if (null==resource){
        	resource =  resourceRepository.findOne(code);
        	try{
        		cacheService.put("resource:"+code, resource);
        	}catch(Exception e){
    	        LOGGER.error("缓存保存失败,资源id:{},错误消息:{}",code,e.getMessage());	
    	    }
    	}
    	
        return resource;
    }

    //@Cacheable(key = "'list'")
    public List<Resource> list(){
    	List<Resource> list = null;
    	try{
	    	if(cacheService.containsKey("resource-list")){
	    		list = cacheService.getList("resource-list",Resource.class);
	    	}
    	}catch(Exception e){
    		LOGGER.error("获取资源列表缓存失败,错误消息:{}",e.getMessage());	
    	}
        if (null==list) {
    		list =  (List<Resource>)resourceRepository.findAll();
    		try{
    			cacheService.put("resource-list", list);
    		}catch(Exception e){
    			LOGGER.error(" 保存资源列表缓存失败,错误消息:{}",e.getMessage());	
    		}
    	}
        return list;
    }

    /*@Caching(
            evict = {@CacheEvict(key = "#code"), @CacheEvict(key = "'list'")}
    )*/
    
    @Transactional
    public void delete(String code){
    	Query query = em.createNativeQuery("DELETE FROM role_resource WHERE resource_id=?");
        query.setParameter(1, code);
        query.executeUpdate();
       
        resourceRepository.delete(code);
        try{
        	cacheService.remove("resource:"+code);
        	cacheService.remove("resource-list");
        }catch(Exception e){
        	LOGGER.error("缓存移除失败,资源id:{},错误消息:{}",code,e.getMessage());	
        }
    }

   /* @Caching(
            evict = {@CacheEvict(key = "#code"), @CacheEvict(key = "'list'")}
    )*/
    @Transactional
    public void switchStatus(String code,boolean disable){
        Query query = em.createNativeQuery("update resource SET disabled=? WHERE id=?");
        query.setParameter(1, disable);
        query.setParameter(2, code);
        query.executeUpdate();
        try{
        	cacheService.remove("resource:"+code);
        	cacheService.remove("resource-list");
        }catch(Exception e){
        	LOGGER.error("缓存移除失败,资源id:{},错误消息:{}",code,e.getMessage());	
        }
    }

    private void validate(Resource resource) {
        Assert.hasText(resource.getTitle());
        Assert.hasText(resource.getUrl());

    }
}
