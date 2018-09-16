package com.personal.feng.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.mime.*;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;


/*
 *网络请求的工具类
 *使用post ，get ,delete ,update四种请求
 */

public class HttpClientUtils {

    // private static HttpClient httpClient = null;
    private static CloseableHttpClient httpClient = null;
    private static JSONObject resultjson = null;
    private static HttpResponse response = null;

    /*使用静态游离快创建HttpClient对象*/
    static {

        httpClient = HttpClients.createDefault();
        /*httpClient = HttpClientBuilder.create().build();*/
        /* 4.3 开始不使用 client = new DefaultHttpClient();*/
        /*设置超时时间*/
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
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

        try {
            response = httpClient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            /* to do处理数据封装到Json中*/

            /*关闭流*/
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
            resultjson.put("message", "exception");
        }
        return resultjson;

    }

    /*
     * Get请求
     *
     *
     */
    public static JSONObject getMethodToJson(JSONObject json, String url) {
        HttpGet httpGet = new HttpGet(url + "?" + URLEncoder.encode(json.toJSONString()));
        try {
            response = httpClient.execute(httpGet);
            //得到响应体
            HttpEntity entity = response.getEntity();

            /*使用流接收信息*/
            if (entity != null) {
                InputStream is = entity.getContent();
                //转换为字节输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));
                StringBuffer stringBuffer = new StringBuffer();
                String body = null;
                while ((body = br.readLine()) != null) {
                    stringBuffer.append(body);
                }
                resultjson = JSONObject.parseObject(stringBuffer.toString());

                /*关闭流*/
                is.close();
                br.close();
                httpClient.close();
            }

            /*使用string字符串接收数据*/
           /* if (entity != null) {
                String content = EntityUtils.toString(entity);
                resultjson = JSONObject.parseObject(content);
            }*/

        } catch (IOException e) {
            e.printStackTrace();
            resultjson.put("message", "exception");
        }

        return resultjson;
    }


    /*
     * Delete请求
     *
     */
    public static JSONObject deleteMethodToJson(JSONObject json, String url) {
        HttpDelete httpdelete = new HttpDelete(url);
        httpdelete.setHeader("Content-type", "application/json");
        /*仿httppost*/
        if (json != null && json.size() > 0) {
            Map<String, String> map = JSONObject.parseObject(json.toJSONString(), new TypeReference<Map<String, String>>() {
            });
            for (Map.Entry<String, String> entry : map.entrySet()) {
                httpdelete.setHeader(entry.getKey(), entry.getValue());
            }
        }
        /*仿httpget
        url = url + "?" + URLEncoder.encode(json.toJSONString());
        HttpDelete httpdelete = new HttpDelete(url);
        */
        try {
            HttpResponse response = httpClient.execute(httpdelete);
            // to do  get JSONObject


        } catch (IOException e) {
            e.printStackTrace();
            resultjson.put("message", "exception");
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return resultjson;
    }


    /*
     *
     * Put请求
     */
    public static JSONObject PutMethodToJson(JSONObject json, String url) {
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder stringBuilder = null;
        try {
            HttpPut httpPut = new HttpPut("url");
            if (json != null && json.size() > 0) {
                Map<String, String> map = JSONObject.parseObject(json.toJSONString(), new TypeReference<Map<String, String>>() {
                });
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    httpPut.addHeader(entry.getKey(), entry.getValue());
                }
            }
            HttpResponse httpResponse = httpClient.execute(httpPut);
            //连接成功
            if (200 == httpResponse.getStatusLine().getStatusCode()) {
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                br = new BufferedReader(new InputStreamReader(is));
                String tempStr;
                stringBuilder = new StringBuilder();
                while ((tempStr = br.readLine()) != null) {
                    stringBuilder.append(tempStr);
                }
                resultjson = JSONObject.parseObject(stringBuilder.toString());
                br.close();
                is.close();
                httpClient.close();
            }
        } catch (Exception e) {
            resultjson.put("message", "exception");
            e.printStackTrace();
        }
        return resultjson;
    }

    /*
     *使用post支持多文件上传
     *
     */
    public static HttpResponse httpPostFormMultipart(String url, Map<String, String> params, List<File> files, Map<String, String> headers, String encode) {
        if (encode == null) {
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);

        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        mEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mEntityBuilder.setCharset(Charset.forName(encode));

        // 普通参数
        ContentType contentType = ContentType.create("text/plain", Charset.forName(encode));//解决中文乱码
        if (params != null && params.size() > 0) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                mEntityBuilder.addTextBody(key, params.get(key), contentType);
            }
        }
        //二进制参数
        if (files != null && files.size() > 0) {
            for (File file : files) {
                mEntityBuilder.addBinaryBody("file", file, contentType, file.getName());
            }
        }
        httpost.setEntity(mEntityBuilder.build());
        String content = null;
        try {
            response = httpClient.execute(httpost);
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity, encode);
            /*response.se(content);*/
            response.setHeaders(response.getAllHeaders());
            response.setReasonPhrase(response.getStatusLine().getReasonPhrase());
            response.setStatusCode(response.getStatusLine().getStatusCode());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
