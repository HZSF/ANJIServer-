package com.weiwei.anji.ehcache.listener;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class TokenCacheEventListener implements CacheEventListener {

	@Override
	public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyElementExpired(Ehcache cache, Element element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyElementEvicted(Ehcache cache, Element element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyRemoveAll(Ehcache cache) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object clone() throws CloneNotSupportedException{
		TokenCacheEventListener listener = new TokenCacheEventListener();
		return listener;
	}

}
