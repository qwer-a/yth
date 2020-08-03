package com.fh.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {

   private static JedisPool jedisPool;
    //创建redis连接池
    private static void createRedisPool(){
        //创建JedisPoolConfig对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        //连接池最大连接数
        jedisPoolConfig.setMaxWaitMillis(1000);

        //连接池空闲连接
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMinIdle(100);

        //在获取连接的时候检查有效性，默认是false
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);

        //创建jedisPool对象
       jedisPool = new JedisPool(jedisPoolConfig,"127.0.0.1",6379);

    }

    //静态块
    static{
        createRedisPool();
    }

    //创建公共方法，供别的类调用
    public static Jedis useRedisPool(){
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }
}
