package com.cqu.lxh.client.interceptor;

import com.cqu.lxh.client.service.HttpService;
import com.cqu.lxh.client.service.SSOClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Component
public class SSOClientInterceptor implements HandlerInterceptor {

    final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
            throws Exception {
        logger.info("Check log state {}...", req.getRequestURI());
        HttpSession session = req.getSession();
        // 1. 判断是否有局部会话
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        if(isLogin != null && isLogin)
            return true;
        else{
            // 2. 判断地址栏中是否有token参数
            String token = req.getParameter("token");
            logger.info("check token {}...", token);
            if(StringUtils.hasLength(token)){
                //token信息不为null,说明地址中包含了token,拥有令牌.
                //判断token信息是否由认证中心产生的.
                String httpURL = SSOClientService.SERVER_URL_PREFIX+"/verify";
                Map<String,String> params = new HashMap<String,String>();
                params.put("token", token);
                params.put("clientUrl", SSOClientService.CLIENTA_HOST_URL+"/logout");
                params.put("jsessionid", session.getId());
                try {
                    String isVerify = HttpService.sendHttpRequest(httpURL, params);
                    if("true".equals(isVerify)){
                        //如果返回的字符串是true,说明这个token是由统一认证中心产生的.
                        //创建局部的会话.
                        session.setAttribute("isLogin", true);
                        //放行该次的请求
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("No local session, redirect to sso server");
        //没有局部会话,重定向到统一认证中心,检查是否有其他的系统已经登录过.
        // http://www.sso.com:8443/checkLogin?redirectUrl=http://www.crm.com:8088
        SSOClientService.redirectToSSOURL(req, resp);
        return false;
    }
}
