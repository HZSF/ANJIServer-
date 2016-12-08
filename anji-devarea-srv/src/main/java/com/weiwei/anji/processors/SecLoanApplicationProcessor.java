package com.weiwei.anji.processors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IFinanceDAO;
import com.weiwei.anji.dao.impl.FinanceDAOImpl;
import com.weiwei.anji.dbmodel.TableLoan;
import com.weiwei.anji.request.CreditLoanRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecLoanApplicationProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private FinanceDAOImpl financeDao;
	private CreditLoanRequest request;
	private String username;
	
	@Override
	protected void preProcess(Map scopes){
		request = (CreditLoanRequest)scopes.get(Constants.SERVICE_REQUEST);
		username = (String)scopes.get(Constants.USERNAME);
		financeDao = new FinanceDAOImpl();
		financeDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		logger.info("executeProcess..");
		TableLoan tableLoanData = request;
		if(tableLoanData != null){
			return financeDao.insertNewCreditLoanAndUserMap(username, tableLoanData);
		}
		return Constants.EVENT_FAIL;
	}

}
