package com.weiwei.anji.processors;

import java.net.URLDecoder;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ICustomerDAO;
import com.weiwei.anji.dao.impl.CustomerDAOImpl;
import com.weiwei.anji.request.PtntAddAnnFeeMoRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecAddPtntAnnFeeMonitorProcessor extends BaseProcessor{
	private String username;
	private CustomerDAOImpl customerDao;
	private PtntAddAnnFeeMoRequest request;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (PtntAddAnnFeeMoRequest)scopes.get(Constants.SERVICE_REQUEST);
		customerDao = new CustomerDAOImpl();
		customerDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		try{
			String customerID = customerDao.findCustomerIdByUsername(username);
			String link = "http://120.26.38.77:81/ajaxCall/annualFeeMonitor.ashx?";
			link += "id=";
			link += request.getPatentId();
			link += "&applyDate=";
			link += request.getApplyDate();
			link += "&userId=";
			link += customerID;
			link += "&applicant=";
			link += URLDecoder.decode(request.getApplicant(), "UTF-8");
			link += "&title=";
			link += URLDecoder.decode(request.getPatentTitle(), "UTF-8");
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(link);
			HttpResponse response = client.execute(request);
			String result = EntityUtils.toString(response.getEntity());
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
	}
}
