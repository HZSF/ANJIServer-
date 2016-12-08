package com.weiwei.anji.security.token;

import java.util.UUID;

public class TokenService {
    private TokenStore restApiAuthTokenStore = new TokenStore();

    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    public void store(String token, String username) {
        restApiAuthTokenStore.storeToken(token, username);
    }

    public boolean validateToken(String username, String token) {
        return restApiAuthTokenStore.validateToken(token, username);
    }

	public TokenStore getRestApiAuthTokenStore() {
		return restApiAuthTokenStore;
	}

	public void setRestApiAuthTokenStore(TokenStore restApiAuthTokenStore) {
		this.restApiAuthTokenStore = restApiAuthTokenStore;
	}
    
}
