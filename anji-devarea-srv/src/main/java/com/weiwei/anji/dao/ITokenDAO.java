package com.weiwei.anji.dao;

public interface ITokenDAO {
	public void storeToken(String token, String username);
	public boolean validateToken(String token, String username);
	public void expireToken(String username);
}
