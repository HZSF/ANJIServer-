package com.weiwei.service.common.response;

import java.util.ArrayList;
import java.util.List;

public class GeneralServiceResponse<T> {
	protected List<T> responseObjectList;
	
	public GeneralServiceResponse(){
		super();
		responseObjectList = new ArrayList<T>();
	}
	
	public void setResponseObjectList(ArrayList<T> list){
		responseObjectList = list;
	}
	public List<T> getResponseObjectList(){
		return responseObjectList;
	}
}
