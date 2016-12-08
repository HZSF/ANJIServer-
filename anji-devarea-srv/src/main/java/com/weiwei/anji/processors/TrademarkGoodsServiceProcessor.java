package com.weiwei.anji.processors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.weiwei.anji.beans.TrademarkGoodsServiceBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.common.StringUtility;
import com.weiwei.anji.request.TrademarkGoodsServiceRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class TrademarkGoodsServiceProcessor extends BaseProcessor {
	protected String url = "http://sbcx.saic.gov.cn:9080/tmois/wszhcx_getGoodsDetail.xhtml?regNum=";//17601756&intcls=42";
	private TrademarkGoodsServiceRequest request;
	private String stringResult;
	private List<TrademarkGoodsServiceBean> result_list;
	
	@Override
	protected void preProcess(Map scopes){
		request = (TrademarkGoodsServiceRequest)scopes.get(Constants.SERVICE_REQUEST);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		url += request.getRegNum();
		url += "&intcls=";
		url += String.valueOf(request.getIntcls());
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet httpRequest = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpRequest);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            stringResult = result.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		result_list = new ArrayList<TrademarkGoodsServiceBean>();
		if(!StringUtility.isEmptyString(stringResult)){
			while(stringResult.contains("list_01")){
				if(stringResult.contains("list_01")){
					TrademarkGoodsServiceBean bean = new TrademarkGoodsServiceBean();
					stringResult = stringResult.substring(stringResult.indexOf("list_01"));
					if(stringResult.contains(">") && stringResult.contains("</td>")){
						String id = stringResult.substring(stringResult.indexOf(">"), stringResult.indexOf("</td>"));
						if(id.length()>1){
							id = id.substring(1);
						}
						bean.setId(id.trim());
						stringResult = stringResult.substring(stringResult.indexOf("</td>"));
					}
					if(stringResult.contains("list_01")){
						stringResult = stringResult.substring(stringResult.indexOf("list_01"));
						if(stringResult.contains(">") && stringResult.contains("</td>")){
							String goodservice = stringResult.substring(stringResult.indexOf(">"), stringResult.indexOf("</td>"));
							if(goodservice.length()>1){
								goodservice = goodservice.substring(1);
							}
							bean.setService(goodservice.trim());
							stringResult = stringResult.substring(stringResult.indexOf("</td>"));
						}
						if(stringResult.contains("list_01")){
							stringResult = stringResult.substring(stringResult.indexOf("list_01"));
							if(stringResult.contains(">") && stringResult.contains("</td>")){
								String group = stringResult.substring(stringResult.indexOf(">"), stringResult.indexOf("</td>"));
								if(group.length()>1){
									group = group.substring(1);
								}
								bean.setGroup(group.trim());
								stringResult = stringResult.substring(stringResult.indexOf("</td>"));
							}
						}
					}
					result_list.add(bean);
				}
				
				
				if(stringResult.contains("list_02")){
					TrademarkGoodsServiceBean bean = new TrademarkGoodsServiceBean();
					stringResult = stringResult.substring(stringResult.indexOf("list_02"));
					if(stringResult.contains(">") && stringResult.contains("</td>")){
						String id = stringResult.substring(stringResult.indexOf(">"), stringResult.indexOf("</td>"));
						if(id.length()>1){
							id = id.substring(1);
						}
						bean.setId(id.trim());
						stringResult = stringResult.substring(stringResult.indexOf("</td>"));
					}
					if(stringResult.contains("list_02")){
						stringResult = stringResult.substring(stringResult.indexOf("list_02"));
						if(stringResult.contains(">") && stringResult.contains("</td>")){
							String goodservice = stringResult.substring(stringResult.indexOf(">"), stringResult.indexOf("</td>"));
							if(goodservice.length()>1){
								goodservice = goodservice.substring(1);
							}
							bean.setService(goodservice.trim());
							stringResult = stringResult.substring(stringResult.indexOf("</td>"));
						}
						if(stringResult.contains("list_02")){
							stringResult = stringResult.substring(stringResult.indexOf("list_02"));
							if(stringResult.contains(">") && stringResult.contains("</td>")){
								String group = stringResult.substring(stringResult.indexOf(">"), stringResult.indexOf("</td>"));
								if(group.length()>1){
									group = group.substring(1);
								}
								bean.setGroup(group.trim());
								stringResult = stringResult.substring(stringResult.indexOf("</td>"));
							}
						}
					}
					result_list.add(bean);
				}
			}
		}
		scopes.put(Constants.SERVICE_RESPONSE, result_list);
		return event;
	}

}
