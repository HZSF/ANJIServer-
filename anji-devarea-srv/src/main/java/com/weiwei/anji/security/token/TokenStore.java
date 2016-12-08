package com.weiwei.anji.security.token;

import com.weiwei.anji.dao.ITokenDAO;

public class TokenStore {
	private ITokenDAO tokenDao;
	
	public void storeToken(String token, String username){
		tokenDao.storeToken(token, username);
	}
	public boolean validateToken(String token, String username){
		return tokenDao.validateToken(token, username);
	}
	public ITokenDAO getTokenDao() {
		return tokenDao;
	}
	public void setTokenDao(ITokenDAO tokenDao) {
		this.tokenDao = tokenDao;
	}
}
