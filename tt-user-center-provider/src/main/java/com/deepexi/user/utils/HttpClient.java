package com.deepexi.user.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @Description
 * @Author lizhongbao
 * @Date  2019/9/21
 */
public class HttpClient {

    private static RequestConfig requestConfig = null;

    @PostConstruct
    public void config() {
        requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
    }

    public static HttpResponse delete(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        org.apache.http.client.methods.HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(requestConfig);
        httpDelete.setHeader("Content-type", "application/json");
        httpDelete.setHeader("DataEncoding", "UTF-8");
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpDelete);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeClient(httpClient, httpResponse);
        }
        return httpResponse;
    }

    private static void closeClient(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse) {
        if (httpResponse != null) {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != httpClient) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
