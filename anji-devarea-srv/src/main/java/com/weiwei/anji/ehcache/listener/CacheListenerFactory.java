package com.weiwei.anji.ehcache.listener;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

public class CacheListenerFactory extends CacheEventListenerFactory{
	
	@Override
	public CacheEventListener createCacheEventListener(Properties properties) {
		TokenCacheEventListener listener = new TokenCacheEventListener();
		return listener;
	}
}
