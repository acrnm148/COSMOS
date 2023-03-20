package com.cosmos.back.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.lang.reflect.Modifier;

public class RedisDB {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    private Jedis jedis;

    private static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .disableHtmlEscaping()
            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT)
            .create();

    @PostConstruct
    public void init() {
        jedis = new Jedis(host, port);
    }

    public <T> T get(String name, Class<T> tClass) {
        try {
            jedis.connect();
            String s = jedis.get(name);
            return gson.fromJson(s, tClass);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            jedis.disconnect();
        }
        return null;
    }


    public <T> void set(String key, Object value, Class<T> tClass) {
        try {
            jedis.connect();
            jedis.set(key, gson.toJson(value, tClass));
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            jedis.disconnect();
        }
    }

    public void expire(String key, Integer expire) {
        try {
            jedis.connect();
            jedis.expire(key, expire);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            jedis.disconnect();
        }
    }

    public Long ttl(String key) {
        try {
            jedis.connect();
            return jedis.ttl(key);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            jedis.disconnect();
        }
        return null;
    }

    public void del(String key) {
        try {
            jedis.connect();
            jedis.del(key);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            jedis.disconnect();
        }
    }
}
