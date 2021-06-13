package com.example.customannotationjava;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonParser {

    private void checkIfSerializable(Object object) throws Exception {
        if (object == null) {
            throw new Exception("The object to serialize is null");
        }
        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(SerializableJSON.class)) {
            throw new Exception(clazz.getSimpleName() + " is not annotated with SerializableJSON");
        }
    }

    private void executeInitialMethod(Object object) throws Exception {
        if (object == null) {
            throw new Exception("The object to serialize is null");
        }
        Class<?> clazz = object.getClass();
        for (Method method : object.getClass().getMethods()) {
            if (method.isAnnotationPresent(BeforeSerializable.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getJsonString(Object object) throws Exception {
        if (object == null) {
            throw new Exception("The object to serialize is null");
        }
        Class<?> clazz = object.getClass();
        Map<String, Object> map = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(JsonField.class) && field.get(object) != null) {
                String key = getKey(field);
                if(!TextUtils.isEmpty(key)) {
                    map.put(key, field.get(object));
                }
            }
        }
        String json = map.entrySet().stream().map(stringStringEntry ->{

            String a = "\"" + stringStringEntry.getKey() + "\":";
            Object b = null;
            if(stringStringEntry.getValue() != null){
                if(stringStringEntry.getValue() instanceof String){
                    b = "\""+stringStringEntry.getValue()+"\"";
                }else{
                    b = stringStringEntry.getValue();
                }
            }
            return a + b;
        })
                .collect(Collectors.joining(","));
        return "{" + json + "}";
    }

    private String getKey(Field field) {
        JsonField jsonField = field.getAnnotation(JsonField.class);
        if (jsonField != null) {
            String key = jsonField.key();
            if (key.length() == 0) {
                key = field.getName();
            }
            return key;
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String parseToJson(Object object) throws Exception {
        try {
            checkIfSerializable(object);
            executeInitialMethod(object);
            return getJsonString(object);
        } catch (Exception e) {
            throw e;
        }
    }
}
