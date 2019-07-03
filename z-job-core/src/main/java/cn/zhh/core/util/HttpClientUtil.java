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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author zhh
 */
@Slf4j
public class HttpClientUtil {

    private HttpClientUtil() {}

    public static byte[] postRequest(String reqURL, byte[] date) throws Exception {
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
            if (date != null) {
                httpPost.setEntity(new ByteArrayEntity(date, ContentType.DEFAULT_BINARY));
            }

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
