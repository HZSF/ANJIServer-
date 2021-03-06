package com.weiwei.anji.processors;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.common.StringUtility;
import com.weiwei.anji.dao.IFinanceDAO;
import com.weiwei.anji.dao.impl.FinanceDAOImpl;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecCancelAppliedCreditLoanProcessor extends BaseProcessor{
	private String username;
	private String loanId;
	private FinanceDAOImpl financeDao;
	
	@Override
	protected void preProcess(Map scopes){
		loanId = (String)scopes.get(Constants.SERVICE_REQUEST);
		username = (String)scopes.get(Constants.USERNAME);
		financeDao = new FinanceDAOImpl();
		financeDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		if(!StringUtility.isEmptyString(loanId)){
			financeDao.cancelCreditLoan(username, loanId);
			return Constants.EVENT_SUCCESS;
		}
		return Constants.EVENT_FAIL;
	}
}
