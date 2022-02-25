package com.cqu.lxh.ssoserver.service;

import java.io.IOException;
import java.util.Properties;

public class SSOServerService {
    private static Properties ssoProperties = new Properties();
    public static String SERVER_URL_PREFIX;// 统一认证中心地址:http://www.sso.com:8443,在sso.properties配置
    public static String CLIENTA_HOST_URL;// 当前客户端地址:http://www.A.com:8088,在sso.properties配置
    public static String CLIENTB_HOST_URL;// 另一客户端地址:http://www.B.com:8080,在sso.properties配置

    static {
        try {
            ssoProperties.load(SSOServerService.class.getClassLoader().getResourceAsStream("sso.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SERVER_URL_PREFIX = ssoProperties.getProperty("server-url-prefix");
        CLIENTA_HOST_URL = ssoProperties.getProperty("clientA-host-url");
        CLIENTB_HOST_URL = ssoProperties.getProperty("clientB-host-url");
    }

}
