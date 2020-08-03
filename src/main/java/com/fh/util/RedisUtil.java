package com.fh.util;

import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisUtil {

    //给缓存赋值
    public static void setKeyValue(String key, String value){
        Jedis jedis = null;

        try {
            //调用redis连接池方法
            jedis = RedisPool.useRedisPool();
            //
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    //获取缓存
    public static String getKeyValue(String key){
        Jedis jedis = null;
        String keyValue = null;

        try {
            //调用redis连接池方法
             jedis = RedisPool.useRedisPool();
             //
             keyValue = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return keyValue;
    }

    //删除缓存
    public static void deleteKeyValue(String key){
        Jedis jedis = null;

        try {
            //调用redis连接池方法
            jedis = RedisPool.useRedisPool();
            //
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    //根据id删除
    public static void hDel(String key,String field){
        Jedis jedis = null;

        try {
            //调用redis连接池方法
            jedis = RedisPool.useRedisPool();
            //
            jedis.hdel(key,field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    //批量删除
    public static void hDelBatch(String key,String[] field){
        Jedis jedis = null;

        try {
            //调用redis连接池方法
            jedis = RedisPool.useRedisPool();
            for (int i = 0; i < field.length; i++) {
                //
                jedis.hdel(key,field[i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    //设置缓存过期时间
    public static void expireTime(String key, String value, int second){
        Jedis jedis = null;

        try {
            //调用redis连接池方法
            jedis = RedisPool.useRedisPool();
            //
            jedis.setex(key,second,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }


    //设置 hash
    public static void hset(String key,String value,String field){
        Jedis jedis =null;
        try {
            jedis = RedisPool.useRedisPool();
            jedis.hset(key,value,field);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    //获取 hash
    public static String hget(String key,String field){
        Jedis jedis =null;
        String s =null;
        try {
            jedis = RedisPool.useRedisPool();
            s = jedis.hget(key,field);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return s;
    }


    //获取多个
    public static List<String>  hGet(String key){
        Jedis jedis =null;
        List<String> hvals = null;
        try {
            jedis = RedisPool.useRedisPool();
            hvals = jedis.hvals(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return hvals;
    }

    //判断key是否存在
    public static Boolean exists(String key,String id){
        Jedis jedis =null;
        Boolean s =null;
        try {
            jedis = RedisPool.useRedisPool();
            s = jedis.hexists(key,id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return s;
    }

    //是否存在
    public static Boolean exists1(String key){
        Jedis jedis =null;
        Boolean exists =null;
        try {
            jedis = RedisPool.useRedisPool();
            exists = jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return exists;
    }

    //删除
    public static Long deleteKey(String keys){
        Jedis jedis = null;
        Long del = null;
        try {
            jedis = RedisPool.useRedisPool();
            del = jedis.del(keys);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return del;
    }

}
