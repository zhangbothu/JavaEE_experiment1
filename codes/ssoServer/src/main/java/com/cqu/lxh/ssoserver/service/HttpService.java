package com.cqu.lxh.ssoserver.service;


import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpService {
    /**
     * 模拟浏览器的请求
     * @param httpURL 发送请求的地址
     * @param jesssionId 会话Id
     * @return
     * @throws Exception
     */
    public static void sendHttpRequest(String httpURL,String jesssionId) throws Exception{
        //建立URL连接对象
        URL url = new URL(httpURL);
        //创建连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求的方式(需要是大写的)
        conn.setRequestMethod("POST");
        //设置需要输出
        conn.setDoOutput(true);
        conn.addRequestProperty("Cookie","JSESSIONID="+jesssionId);
        //发送请求到服务器
        conn.connect();
        conn.getInputStream();
        conn.disconnect();
    }
}
