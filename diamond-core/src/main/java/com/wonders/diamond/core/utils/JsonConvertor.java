package com.wonders.diamond.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ningyang on 2017/1/14.
 */
public class JsonConvertor {

    private static final ObjectMapper mapper = new ObjectMapper();

    public JsonConvertor() {
    }

    public static <T> T toObject(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static <T> T toObject(InputStream stream, Class<T> type) {
        try {
            return mapper.readValue(stream, type);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static JsonNode toJsonNode(String json) {
        try {
            return mapper.readTree(json);
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static JsonNode toJsonNode(InputStream stream) {
        try {
            return mapper.readTree(stream);
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException var2) {
            throw new RuntimeException(var2);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
