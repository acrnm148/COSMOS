package com.cosmos.back.config;

import com.cosmos.back.dto.response.review.ReviewUserResponseDto;
import com.cosmos.back.model.ReviewCategory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;


public class RedisDB {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    private Jedis jedis;

    private ObjectMapper objectMapper;

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


//    public class AutoAdapter implements JsonSerializer<ReviewUserResponseDto> {
//        @Override
//        public JsonElement serialize(ReviewUserResponseDto dto, Type type, JsonSerializationContext jsc) {
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("reviewId", dto.getReviewId());
////            jsonObject.addProperty("categories", dto.getCategories().);
////            jsonObject.addProperty("indiReviewCategories", dto.getIndiReviewCategories().toString());
//            jsonObject.addProperty("score", dto.getScore());
//            jsonObject.addProperty("contents", dto.getContents());
//            jsonObject.addProperty("placeId", dto.getPlaceId());
//            jsonObject.addProperty("nickName", dto.getNickName());
//            jsonObject.addProperty("createdTime", dto.getCreatedTime());
////            jsonObject.addProperty("images", dto.getImages().toString());
//            jsonObject.addProperty("contentsOpen", dto.getContentsOpen());
//            jsonObject.addProperty("imageOpen", dto.getImageOpen());
//            return jsonObject;
//        }
//    }

    public <T> void set(String key, Object value, Class<T> tClass) {
        try {
            jedis.connect();
//            System.out.println("value = " + value);
//            String text = objectMapper.writeValueAsString(value);
//            System.out.println("text = " + text);

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setFieldNamingStrategy(new CustomNamingConfig());
//            gsonBuilder.registerTypeAdapter(ReviewUserResponseDto.class, new AutoAdapter());
            Gson gson = gsonBuilder.serializeNulls().create();
            System.out.println("value = " + value);
            System.out.println("tClass = " + tClass);
            System.out.println("gson = " + gson);
            System.out.println("gson.toString() = " + gson.toString());
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
