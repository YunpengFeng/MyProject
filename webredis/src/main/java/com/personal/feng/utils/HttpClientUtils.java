package com.personal.feng.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreConnectionPNames;

import java.io.IOException;
import java.nio.charset.Charset;


/*
 *网络请求的工具类
 *使用post ，get ,delete ,update四种请求
 */

public class HttpClientUtils {

    private static HttpClient httpClient = null;
    private static JSONObject resultjson = null;

    /*使用静态游离快创建HttpClient对象*/
    static {
        httpClient = HttpClientBuilder.create().build();
        /*  HttpClient client = new DefaultHttpClient();*/
        /*设置超时时间*/
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
        resultjson = new JSONObject();
    }

    /*
     * Post请求
     *params{
     *      json：消息体（参数）
     *      url 请求的地址
     * }
     * return {
     *          1、流
     *          2、json格式的数据
     * }
     *
     */
    public static JSONObject postMethodToJson(JSONObject json, String url) {
        HttpPost httppost = new HttpPost(url);
        // 构造消息头
        httppost.setHeader("Content-type", "application/json; charset=utf-8");
        httppost.setHeader("Connection", "Close");

        // 构建消息实体
        StringEntity entity = new StringEntity(json.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        // 发送Json格式的数据请求
        entity.setContentType("application/json");
        httppost.setEntity(entity);
        HttpResponse response = null;
        try {
            response = httpClient.execute(httppost);

            int statusCode = response.getStatusLine().getStatusCode();
            /* to do处理数据封装到Json中*/
        } catch (IOException e) {
            e.printStackTrace();
            resultjson.put("message", "exception");
        }
        return resultjson;

    }

    /*
     * Get请求
     *
     * */
    public static void getMethodToJson() {


    }
}
