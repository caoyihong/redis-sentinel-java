package com.mkfree.sentinel;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by caoyihong on 2017/5/8.
 */
public class TestSentinel {
    private static final JedisSentinelPool pool;
    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMinIdle(5);

        Set<String> sentinels = new HashSet<>(Arrays.asList(
                "111.111.111.111:26379",
                "111.111.111.112:26379",
                "111.111.111.113:26379"
        ));
//        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//        poolConfig.setMaxTotal(10);
//        poolConfig.setMaxIdle(5);
//        poolConfig.setMinIdle(5);
        pool = new JedisSentinelPool("yewu01", sentinels, jedisPoolConfig);
    }

    public static void main(String[] args) throws Exception {
        pool.getCurrentHostMaster();
        String key1 = "key1";
        Jedis jedis = pool.getResource();
        jedis.set(key1, "222");
        System.out.println(jedis.get(key1));
    }
}
