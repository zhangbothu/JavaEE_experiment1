package com.cqu.lxh.ssoserver.service;

import com.cqu.lxh.ssoserver.model.ClientInfo;

import java.util.*;

public class MockDatabaseService {
    public static Set<String> T_TOKEN = new HashSet<String>();
    public static Map<String, List<ClientInfo>> T_CLIENT_INFO = new HashMap<String, List<ClientInfo>>();
}

