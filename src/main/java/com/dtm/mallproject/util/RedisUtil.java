package com.dtm.mallproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/3 17:02
 * @description : Redis 工具类，仅根据业务需要，封装了部分方法
 */
@Component
public class RedisUtil {
    private RedisTemplate redisTemplate;

    /**
     * 解决Redis的转义问题
     */
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取 RedisTemplate 对象
     *
     * @return RedisTemplate 对象
     */
    public RedisTemplate getRedisTemplate(){
        return this.redisTemplate;
    }

    /**
     * 删除一个键
     *
     * @param key 键
     * @return 删除结果
     */
    public Integer del(String key){
        Boolean delete = redisTemplate.delete(key);
        if (delete) {return 1;}
        return 0;
    }

    /**
     * 向一个 hash 中放入一条数据，如果不存在则创建
     *
     * @param key 键
     * @param field 项
     * @param value 值
     * @return 操作结果
     */
    public Boolean hSet(String key, String field, Object value){
        try {
            redisTemplate.opsForHash().put(key,field,value);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 向一个 hash 中放入多条数据，如果不存在则创建
     *
     * @param key 键
     * @param map 对应多个键值
     * @return 操作结果
     */
    public Boolean hMSet(String key, Map<String, Object> map){
        try {
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 根据 hash 的 field 获取 value
     * @param key 键
     * @param field 项
     * @return 值
     */
    public String hGet(String key, String field){
        return (String) redisTemplate.opsForHash().get(key,field);
    }

    /**
     * 获取 hash 中的所有的键值对集合
     *
     * @param key 键
     * @return 键值对集合
     */
    public Map<Object, Object> hEntries(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除 hash 中的值
     *
     * @param key 键
     * @param field 项，可以为多个
     * @return 删除结果，每成功删除一项则结果加 1，全部失败则为 0
     */
    public Integer hDel(String key, Object... field){
        Long delete = redisTemplate.opsForHash().delete(key, field);
        return delete.intValue();
    }

    /**
     * 判断 hash 中是否存在该字段
     *
     * @param key 键
     * @param field 项
     * @return 判断结果
     */
    public Boolean hHas(String key, String field){
        return redisTemplate.opsForHash().hasKey(key,field);
    }

    /**
     * hash 的子性递增
     *
     * @param key 键
     * @param field 项
     * @param increment 递增值
     * @return 递增后的结果值
     */
    public Long hIncrBy(String key, String field, Long increment){
        return redisTemplate.opsForHash().increment(key,field,increment);
    }

    /**
     * hash 的原子性递减
     *
     * @param key 键
     * @param field 项
     * @param decrement 递减值
     * @return 递减后的结果值
     */
    public Long hDecrBy(String key, String field, Long decrement){
        return redisTemplate.opsForHash().increment(key,field,-decrement);
    }

    /**
     * 向 Set 中添加成员 (数据不能重复)
     *
     * @param key 键
     * @param member 成员 (可以为多个)
     * @return 操作结果，每成功添加一项则结果加 1，全部失败则为 0
     */
    public Integer sAdd(String key, String... member){
        return redisTemplate.opsForSet().add(key, member).intValue();
    }

    /**
     * 从 Set 中删除成员
     *
     * @param key 键
     * @param member 成员 (可以为多个)
     * @return 操作结果，每成功删除一项则结果加 1，全部失败则为 0
     */
    public Integer sRemove(String key, String... member){
        return redisTemplate.opsForSet().remove(key, member).intValue();
    }

    /**
     * 获取 Set 中的所有成员
     *
     * @param key 键
     * @return 所有成员
     */
    public Set<String> sMembers(String key){
        return redisTemplate.opsForSet().members(key);
    }

}
