package com.luckychacha.elasticsearch.common.util;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
//import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.gson.*;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
//import com.luckychacha.elasticsearch.common.constant.Constant;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JsonUtil {
    private static Gson gson = null;

    private JsonUtil() {
    }

    public static String toJson(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }

        return gsonString;
    }

    public static <T> T fromJson(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }

        return t;
    }
//    public static String toJson(Object object) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = null;
//
//        try {
//            json = objectMapper.writeValueAsString(object);
//        } catch (Exception var4) {
//            var4.printStackTrace();
//        }
//
//        return json;
//    }
//
//    public static <T> T fromJson(String json, Class<T> t) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Constant.DEFAULT_DATE_TIME_FORMAT)));
//        javaTimeModule.addDeserializer(
//                LocalDateTime.class,
//                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(Constant.DEFAULT_DATE_TIME_FORMAT))
//        );
//
//        javaTimeModule.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ofPattern(Constant.DEFAULT_DATE_FORMAT)));
//        javaTimeModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ofPattern(Constant.DEFAULT_DATE_FORMAT)));
//
//        javaTimeModule.addSerializer(LocalTime.class,new LocalTimeSerializer(DateTimeFormatter.ofPattern(Constant.DEFAULT_TIME_FORMAT)));
//        javaTimeModule.addDeserializer(LocalTime.class,new LocalTimeDeserializer(DateTimeFormatter.ofPattern(Constant.DEFAULT_TIME_FORMAT)));
//
//        objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
//
//        objectMapper.registerModule(new Jdk8Module());
//        objectMapper.registerModule(new JavaTimeModule());
//        Object resultT = null;
//
//        try {
//            resultT = objectMapper.readValue(json, t);
//        } catch (Exception var5) {
//            var5.printStackTrace();
//        }
//
//        return (T) resultT;
//    }
    public static <T> List<T> toList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = (List)gson.fromJson(gsonString, (new TypeToken<List<T>>() {
            }).getType());
        }

        return list;
    }

    static {
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//                            Instant instant = Instant.ofEpochMilli(jsonElement.getAsJsonPrimitive().getAsLong());
//                            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                        String datetime = jsonElement.getAsJsonPrimitive().getAsString();
                        return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    }
                })
                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        String datetime = json.getAsJsonPrimitive().getAsString();
                        return LocalDate.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                })
                .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                    @Override
                    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }
                })
                .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                    @Override
                    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    }
                })
                .serializeNulls()
                .create();

    }

}
