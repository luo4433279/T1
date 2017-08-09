package org.bumishi.admin.domain.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.JedisCluster;

/**
 * @Description 缓存服务
 * @author kelvin
 * @date 2016年10月2日 下午3:07:32 
 */
@Component
public class CacheService {
	public static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);
	@Autowired
	JedisCluster jedisCluster;
	
	public Object put(String key, Object value) {
		if(value instanceof String){
			return jedisCluster.set(key, (String)value);
		}else{
			return jedisCluster.set(key, JSON.toJSONString(value));	
		}
	}
	
	public Object put(String key, Object value, int TTL) {
		if(value instanceof String){
			return jedisCluster.setex(key,TTL, (String)value);
		}else{
			return jedisCluster.setex(key, TTL, JSON.toJSONString(value));
		}
	}
	
	public Object put(String key, Object value, Date expiry) {
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		int seconds= (int) ((expiry.getTime()-now.getTime())/1000);
		if (expiry.after(now)&& seconds>=0) {
			return put(key, value,seconds);
		} else {
			return put(key, value);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<T> clazz) {
		if(String.class.equals(clazz)){
			return (T) jedisCluster.get(key);
		}else{
			return JSON.parseObject(jedisCluster.get(key), clazz);		
		}
	}
	
	public <T> List<T> getList(String key,Class<T> clazz){
		 List<T> list =null;
		 try {
			 String str = jedisCluster.get(key);
	         list =JSON.parseArray(str, clazz);
		 } catch (Exception e){
			 LOGGER.error("redis 获取错误,key:{},错误消息：{}",key,e.getMessage());
		 }
         return list;
	}
	public Object remove(String key) {
		return jedisCluster.del(key);
	}

	public boolean containsKey(String key) {
		return jedisCluster.exists(key);
	}

	public Long append(String key, String value) {
		return jedisCluster.append(key, value);
	}

	public Long lpush(String key, Object value) {
		return jedisCluster.lpush(key, JSON.toJSONString(value));
	}

	public Long rpush(String key, Object value) {
		return jedisCluster.rpush(key, JSON.toJSONString(value));
	}

	public <T> T lpop(String key, Class<T> clazz) {
		return JSON.parseObject(jedisCluster.lpop(key), clazz);
	}

	public <T> T rpop(String key, Class<T> clazz) {
		return JSON.parseObject(jedisCluster.rpop(key), clazz);
	}

	public <T> T lindex(String key, long index, Class<T> clazz) {
		return JSON.parseObject(jedisCluster.lindex(key,index), clazz);
	}

	public Long llen(String key) {
		return jedisCluster.llen(key);
	}

	public <T> List<T> lrange(String key, long start, long end,Class<T> clazz) {
		List<String> list =jedisCluster.lrange(key,start,end);
		List<T> resultList = new ArrayList<T>();
		for(String json:list){
			resultList.add(JSON.parseObject(json,clazz));
		}
		return resultList;
	}

	public boolean exists(String key){
		return jedisCluster.exists(key);
	}
}
