package com.dtm.mallproject;

import com.dtm.mallproject.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/3 16:22
 * @description : 测试Redis
 */
@SpringBootTest
class RedisTest {
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    @Test
    void testHash(){
        BoundHashOperations<String, Object, Object> hash = redisTemplate.boundHashOps("testHash");
        // 存key
        hash.put("key1","10");
        hash.put("key2","5");

        // 存一个Map
        HashMap<String, String> map = new HashMap<>();
        map.put("key3","12");
        map.put("key4","100");
        hash.putAll(map);

        // 获取hash中的所有的key
        Set<Object> keys = hash.keys();
        System.out.println("获取hash中的所有的key");
        keys.forEach(System.out::println);

        // 获取hash中的所有的value
        List<Object> values = hash.values();
        System.out.println("获取hash中的所有的value");
        values.forEach(System.out::println);

        // 获取所有的键值对集合
        Map<Object, Object> entries = hash.entries();
        System.out.println("获取所有的键值对集合");
        entries.forEach((key, value) -> System.out.println(key+"-"+value));

        // 根据key获取value
        String value1 = (String)hash.get("key1");
        System.out.println("根据key获取value");
        System.out.println(value1);

        // 判断hash中是否含有该值
        Boolean result = hash.hasKey("key07");
        System.out.println(result);
    }

    @Resource
    private RedisUtil redisUtil;
    @Test
    void testRedisUtil(){
        Boolean hSet = redisUtil.hSet("testUtil", "k1", "100");
        System.out.println("hSet-"+hSet);

        HashMap<String, Object> map = new HashMap<>();
        map.put("k2","10");
        Boolean hMSet = redisUtil.hMSet("testUtil", map);
        System.out.println("hMSet-"+hMSet);

        Object hGet = redisUtil.hGet("testUtil", "k1");
        System.out.println("hGet-"+hGet);

        Map<Object, Object> hEntries = redisUtil.hEntries("testUtil");
        hEntries.forEach((key,value) -> System.out.println("hEntries-"+key+"-"+value));

        Boolean hHas = redisUtil.hHas("testUtil", "k3");
        System.out.println(hHas);

        Long hIncrBy = redisUtil.hIncrBy("testUtil", "k1", 10L);
        System.out.println("hIncrBy-"+hIncrBy);

        Long hDecrBy = redisUtil.hDecrBy("testUtil", "k2", 5L);
        System.out.println("hDecrBy-"+hDecrBy);
    }

    @Test
    void testTransaction(){
        SessionCallback<Object> sessionCallback = new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForHash().put("test2", "k2", "10");
                return operations.exec();
            }
        };
        Object result = redisUtil.getRedisTemplate().execute(sessionCallback);
        System.out.println(result);
    }

    @Test
    void testSet(){
        Integer sAdd = redisUtil.sAdd("test", "v1", "v2","v3");
        System.out.println(sAdd);

        Integer sRemove = redisUtil.sRemove("test", "v2");
        System.out.println(sRemove);

        Set<String> sMembers = redisUtil.sMembers("test");
        sMembers.forEach(System.out::println);
    }
}
