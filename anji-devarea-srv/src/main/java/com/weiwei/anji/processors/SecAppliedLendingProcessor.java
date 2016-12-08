package com.weiwei.anji.processors;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.FinancialAppliedLendingBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.common.StringUtility;
import com.weiwei.anji.dao.IFinanceDAO;
import com.weiwei.anji.dao.impl.FinanceDAOImpl;
import com.weiwei.anji.dbmodel.TableLendWithBank;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecAppliedLendingProcessor extends BaseProcessor{
	private String lendId;
	private FinancialAppliedLendingBean lendBean;
	private FinanceDAOImpl financeDao;
	
	@Override
	protected void preProcess(Map scopes){
		lendId = (String)scopes.get(Constants.SERVICE_REQUEST);
		lendBean = new FinancialAppliedLendingBean();
		financeDao = new FinanceDAOImpl();
		financeDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
		
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		try{
			if(!StringUtility.isEmptyString(lendId)){
				List<TableLendWithBank> appliedLendList = (List<TableLendWithBank>)financeDao.findLendingById(lendId);
				if(appliedLendList != null && appliedLendList.size() > 0){
					TableLendWithBank tableLend = appliedLendList.get(0);
					lendBean.setBankAbbr(tableLend.getAbbreviation());
					lendBean.setDeadLine(tableLend.getDeadLine().toString().substring(0, 10));
					lendBean.setLoadSum(tableLend.getLoadSum());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, lendBean);
		return event;
	}
}
