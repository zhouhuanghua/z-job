package cn.zhh.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhh
 */
@Slf4j
public class HttpClientUtil {

    private HttpClientUtil() {}

    public static byte[] postRequest(String reqURL, byte[] dataBytes, Map<String, String> headerMap) throws Exception {
        byte[] responseBytes = null;
        HttpPost httpPost = new HttpPost(reqURL);
        CloseableHttpClient httpClient = HttpClients.custom().disableAutomaticRetries().build();

        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000)
                    .setConnectTimeout(10000)
                    .build();
            httpPost.setConfig(requestConfig);
            if (Objects.nonNull(dataBytes)) {
                httpPost.setEntity(new ByteArrayEntity(dataBytes, ContentType.DEFAULT_BINARY));
            }
            headerMap.forEach((k, v) -> {
                httpPost.setHeader(k, v);
            });

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseBytes = EntityUtils.toByteArray(entity);
                EntityUtils.consume(entity);
            }
        } catch (Exception var15) {
            log.warn(var15.getMessage(), var15);
            throw var15;
        } finally {
            httpPost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException var14) {
                log.error(var14.getMessage(), var14);
            }

        }

        return responseBytes;
    }

}
