package com.cosmos.back.config;

import com.google.gson.FieldNamingStrategy;

import java.lang.reflect.Field;

public class CustomNamingConfig implements FieldNamingStrategy {
    @Override
    public String translateName(Field f) {
        String keyVal = f.getName(); // Json 데이터 키값 가져옴
        return keyVal;
    }
}
