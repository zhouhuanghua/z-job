package cn.zhh.core.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpClientUtilTest {

    @Test
    public void testPostRequest() throws Exception {
        String url = "http://localhost:8080/job/group/auto_register";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appName", "demoApp");
        paramMap.put("address", "127.0.0.1:8080");

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");

        HttpClientUtil.postRequest(url, "{\"a\":\"2\"}".getBytes(), headerMap);
    }

}