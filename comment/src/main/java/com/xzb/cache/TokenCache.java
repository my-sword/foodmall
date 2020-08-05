package com.xzb.cache;

import java.util.HashMap;
import java.util.Map;
//Token的定义：Token是服务端生成的一串字符串，以作客户端进行请求的一个令牌，当第一次登录后，服务器生成一个Token便将此Token返回给客户端，以后客户端只需带上这个Token前来请求数据即可，无需再次带上用户名和密码。
//使用Token的目的：Token的目的是为了减轻服务器的压力，减少频繁的查询数据库，使服务器更加健壮。
//链接：https://www.jianshu.com/p/24825a2683e6

/**
 * token缓存，存放token与对应的用户信息（这里存放的是用户手机号）
 */
public class TokenCache {
    private static TokenCache instance;

    private Map<String, Long> tokenMap;

    private TokenCache() {
	tokenMap = new HashMap<>();
    }

    public static TokenCache getInstance() {
	if (instance == null) {
	    synchronized (TokenCache.class) {
		if (instance == null) {
		    instance = new TokenCache();
		}
	    }
	}
	return instance;
    }

    /**
     * 保存token与对应的手机号
     * @param token
     * @param phone 手机号
     */
    public void save(String token, Long phone) {
	tokenMap.put(token, phone);
    }

    /**
     * 根据token获取用户信息(手机号)
     * @param token
     * @return 手机号
     */
    public Long getPhone(String token) {
	return tokenMap.get(token);
    }
}