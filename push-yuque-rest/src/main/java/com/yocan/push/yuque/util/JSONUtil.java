package com.yocan.push.yuque.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class JSONUtil {
    /**
     * jackson处理工具，可作为单例使用
     */
    private static final ObjectMapper objectMapper;

    private static final TypeFactory typeFactory;

    private static final ConcurrentHashMap<String, JavaType> javaTypeCache = new ConcurrentHashMap<>();

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        typeFactory = objectMapper.getTypeFactory();
    }

    public static ObjectMapper getObjectMapper(){
        return objectMapper;
    }

    public static TypeFactory getTypeFactory(){
        return typeFactory;
    }

    public static JavaType getJavaType(Class<?> parametrized, Class<?>... parameterClasses){
        String typeIdentifier = parametrized + Arrays.toString(parameterClasses);
        JavaType fromCache = javaTypeCache.get(typeIdentifier);
        if (fromCache != null){
            return fromCache;
        }
        JavaType newJavaType = typeFactory.constructParametricType(parametrized, parameterClasses);
        javaTypeCache.put(typeIdentifier, newJavaType);
        return newJavaType;
    }



    public static String objectToJsonString(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static <T> T jsonToObject(String json, Class<T> cls){
        try {
            return objectMapper.readValue(json, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T jsonToObject(String json, JavaType type) throws Exception {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Json转换异常");
        }
    }
}