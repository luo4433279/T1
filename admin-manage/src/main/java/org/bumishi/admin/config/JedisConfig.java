package org.bumishi.admin.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.bumishi.admin.domain.modle.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @Description jedis配置
 * @author kelvin
 * @date 2016年10月2日 下午5:21:54
 */
@Component
@ConfigurationProperties(prefix = "redis.cache")
@EnableCaching
public class JedisConfig {

	@Value("${redis.pool.config.maxTotal}")
	private int maxTotal;
	@Value("${redis.pool.config.maxIdle}")
	private int maxIdle;
	@Value("${redis.pool.config.minIdle}")
	private int minIdle;
	@Value("${redis.pool.config.maxWaitMillis}")
	private int maxWaitMillis;
	@Value("${redis.pool.config.lifo}")
	private boolean lifo;
	@Value("${redis.pool.config.blockWhenExhausted}")
	private boolean blockWhenExhausted;
	@Value("${redis.pool.config.testOnBorrow}")
	private boolean testOnBorrow;
	@Value("${redis.pool.config.testOnReturn}")
	private boolean testOnReturn;
	@Value("${redis.pool.config.testWhileIdle}")
	private boolean testWhileIdle;
	@Value("${redis.pool.config.timeBetweenEvictionRunsMillis}")
	private long  timeBetweenEvictionRunsMillis;

	@Autowired
	private RedisProperties redisProperties;

	/**
	 * 注意： 这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
	 * 
	 * @return
	 */
	@Bean
	public JedisCluster getJedisCluster(GenericObjectPoolConfig genericObjectPoolConfig) {
		String[] serverArray = redisProperties.getClusterNodes().split(",");// 获取服务器数组(这里要相信自己的输入，所以没有考虑空指针问题)
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();

		for (String ipPort : serverArray) {
			String[] ipPortPair = ipPort.split(":");
			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
		}
		return new JedisCluster(nodes, redisProperties.getCommandTimeout(), nodes.size(),
				genericObjectPoolConfig);
	}
	
	@Bean 
	GenericObjectPoolConfig genericObjectPoolConfig(){
		GenericObjectPoolConfig  poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxTotal(maxTotal);
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxWaitMillis(maxWaitMillis);
		poolConfig.setLifo(lifo);
		poolConfig.setBlockWhenExhausted(blockWhenExhausted);
		poolConfig.setTestOnBorrow(testOnBorrow);
		poolConfig.setTestOnReturn(testOnReturn);
		poolConfig.setTestWhileIdle(testWhileIdle);
		poolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		return poolConfig;
		
	}

}
