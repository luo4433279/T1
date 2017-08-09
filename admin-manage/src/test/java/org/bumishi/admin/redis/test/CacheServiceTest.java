package org.bumishi.admin.redis.test;

import java.util.Calendar;
import java.util.List;

import org.bumishi.admin.Application;
import org.bumishi.admin.domain.service.CacheService;
import org.bumishi.admin.interfaces.facade.dto.Node;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;




/**   
* @Description TODO
* @author kelvin
* @date 2016年10月3日 上午10:45:07 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@WebAppConfiguration
public class CacheServiceTest {
  @Autowired
  CacheService cacheService;
	@Test
	public void testGet() {
		try {
//			cacheService.remove("key1");
//			cacheService.put("key1", 123);
			cacheService.put("key2adfs", "你好123");
			cacheService.put("key1", 2233);
			cacheService.put("key3", new Long(5000));

			Assert.assertEquals(cacheService.get("key2adfs", String.class), "你好123");
/*			Assert.assertEquals(cacheService.get("key1", Integer.class), 2233);
			Assert.assertEquals(cacheService.get("key3", Long.class), 5000L);*/
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testGetObject() {
		try {
			String nodeName = "fdasfd";
			Node node = new Node();
			node.setName(nodeName);
			cacheService.put(node.getName(), node);
			Node nodeFromCache = (Node) cacheService.get(node.getName(), Node.class);
			Assert.assertEquals(nodeFromCache.getName(), nodeName);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testRemove() {
		try {
			cacheService.remove("key1");
			cacheService.put("key1", "value1");
			Assert.assertEquals(cacheService.get("key1", String.class), "value1");
			cacheService.remove("key1");
			Assert.assertNull(cacheService.get("key1", String.class));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testcontainsKey() {
		try {
			cacheService.remove("key1");
			cacheService.put("key1", "value1");
			Assert.assertTrue(cacheService.containsKey("key1"));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testPutKVSecond() {
		try {

			int seconds = 30;
			cacheService.remove("key1");
			cacheService.put("key1", "value1", seconds);
			Assert.assertEquals(cacheService.get("key1", String.class), "value1");

			Thread.sleep(30 * 1000 + 10);
			Assert.assertNull(cacheService.get("key1", String.class));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	@Test
	public void testPutKVDate() {
		try {
			String key = "testPutKVDate";
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.SECOND, 30);
			cacheService.remove(key);
			cacheService.put(key, "value1", calendar.getTime());

			Assert.assertEquals(cacheService.get(key, String.class), "value1");

			Thread.sleep(30 * 1000 + 10);
			Assert.assertNull(cacheService.get(key, String.class));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testAppend() {
		try {
			String key = "testAppend";
			cacheService.remove(key);
			cacheService.put(key, "value1");
			cacheService.append(key, "22");
			String s = cacheService.get(key, String.class);
			// String ss =cacheService.lpop(key, String.class);
			Assert.assertEquals(cacheService.get(key, String.class), "value122");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

/*	@Test
	public void testLPushAndPop() {
		try {
			String key = "testLpush2";
			cacheService.lpop(key, String.class);
			long i = cacheService.lpush(key, "value1");
			Assert.assertTrue(i == 1);
			Assert.assertEquals(cacheService.lpop(key, String.class), "value1");

			String key3 = "testLpush3";
			cacheService.lpop(key3, Integer.class);
			Assert.assertTrue(cacheService.lpush(key3, 122) == 1);
			Assert.assertTrue(cacheService.lpop(key3, Integer.class) == 122);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}*/

	@Test
	public void testRPushAndPop() {
		try {
			String key = "testRPushAndPop1";
			cacheService.lpop(key, String.class);
			long i = cacheService.lpush(key, "value1");
			Assert.assertTrue(i == 1);
			Assert.assertEquals(cacheService.lpop(key, String.class), "value1");

			String key2 = "testRPushAndPop2";
			cacheService.lpop(key2, Integer.class);
			Assert.assertTrue(cacheService.lpush(key2, 122) == 1);
			Assert.assertTrue(cacheService.lpop(key2, Integer.class) == 122);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testLlen() {
		try {
			String key = "testLlen";
			while (cacheService.llen(key) > 0) {
				cacheService.lpop(key, String.class);
			}
			cacheService.lpush(key, "value1");
			Assert.assertTrue(cacheService.llen(key) == 1);
			cacheService.rpush(key, "fgff");
			Assert.assertTrue(cacheService.llen(key) == 2);
			cacheService.lpop(key, String.class);
			Assert.assertTrue(cacheService.llen(key) == 1);
			cacheService.lpop(key, String.class);
			Assert.assertTrue(cacheService.llen(key) == 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testLindex() {
		try {
			String key = "testLindex";
			while (cacheService.llen(key) > 0) {
				cacheService.lpop(key, String.class);
			}
			cacheService.lpush(key, "value1");
			cacheService.lpush(key, "fgff");
			cacheService.lpush(key, "fgfffff");
			String s = cacheService.lindex(key, 1, String.class);
			Assert.assertEquals("fgff", s);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testLrange() {
		try {
			String key = "testLrange";
			while (cacheService.llen(key) > 0) {
				cacheService.lpop(key, String.class);
			}
			int len = 10;
			for(int i=0;i< len ;i++){
				cacheService.lpush(key, i);
			}
			//cache :  9 8 7 6 5 4 3 2 1 0
			int start = 2;
			int pos =10;
			List<Integer> list = cacheService.lrange(key, start, start+ pos,Integer.class);
			//list: 7 6 5 4 3
			for(int i=0;i<list.size();i++){
				int value = list.get(i);
				Assert.assertEquals(len-1-start-i, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
}
