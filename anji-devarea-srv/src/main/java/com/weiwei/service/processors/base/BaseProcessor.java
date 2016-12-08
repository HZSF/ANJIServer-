package com.weiwei.service.processors.base;

import java.util.Map;

import org.springframework.stereotype.Controller;

public abstract class BaseProcessor {
	public String doProcess(Map scopes) {
		preProcess(scopes);
		String event = executeProcess(scopes);
		event = postProcess(scopes, event);
		return event;
	}
	
	protected void preProcess(Map scopes){}
	protected abstract String executeProcess(Map scopes);
	protected String postProcess(Map scopes, String event){
		return event;
	}
}
