package com.cqu.lxh.ssoserver.model;

import lombok.Data;


@Data
public class ClientInfo {
    private String clientUrl;
    private String jsessionid;
}