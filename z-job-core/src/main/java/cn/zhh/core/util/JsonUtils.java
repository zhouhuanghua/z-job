package cn.zhh.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Json工具类
 *
 * @author z_hh
 */
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String writeValueAsString(Object obj) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    public static <T> T read(String jsonStr, Class<T> targetClass) throws IOException {
        return OBJECT_MAPPER.readValue(jsonStr, targetClass);
    }
}
