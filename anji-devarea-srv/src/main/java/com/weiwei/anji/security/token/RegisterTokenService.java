package com.weiwei.anji.security.token;

import java.util.UUID;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;

public class RegisterTokenService {
	private static final Logger logger = LoggerFactory.getLogger(RegisterTokenService.class);
    private static final Cache registerTokenCache = CacheManager.getInstance().getCache("registerTokenCache");
    public static final int LIFE_IN_MILLISECONDS = 2 * 60 * 1000;

    @Scheduled(fixedRate = LIFE_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        //logger.info("ANJI APP Evicting expired register tokens");
        registerTokenCache.evictExpiredElements();
    }

    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    public void store(String token, Authentication authentication) {
    	registerTokenCache.put(new Element(token, authentication));
    }

    public boolean contains(String token) {
        return registerTokenCache.get(token) != null;
    }

    public Authentication retrieve(String token) {
        return (Authentication) registerTokenCache.get(token).getObjectValue();
    }
   
}
