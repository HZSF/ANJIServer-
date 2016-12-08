package com.weiwei.anji.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.FinancialAppliedLoanBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IFinanceDAO;
import com.weiwei.anji.dao.impl.FinanceDAOImpl;
import com.weiwei.anji.dbmodel.TableLending;
import com.weiwei.anji.dbmodel.TableLoan;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecAppliedLoanProcessor extends BaseProcessor{
	private String username;
	public List<FinancialAppliedLoanBean> financialAppliedList;
	private FinanceDAOImpl financeDao;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		financialAppliedList = new ArrayList<FinancialAppliedLoanBean>();
		financeDao = new FinanceDAOImpl();
		financeDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		List<TableLoan> appliedLoanList = financeDao.findCreditLoanByUsername(username);
		if(appliedLoanList != null && appliedLoanList.size() > 0){
			for(TableLoan tableLoan : appliedLoanList){
				FinancialAppliedLoanBean bean = new FinancialAppliedLoanBean();
				bean.setLoanType("Loan");
				bean.setLoanAmount(String.valueOf(tableLoan.getLoan_limit()));
				bean.setLoanId(tableLoan.getId());
				financialAppliedList.add(bean);
			}
		}
		List<TableLending> appliedLendingList = (List<TableLending>)financeDao.findLendingByUsername(username);
		if(appliedLendingList != null && appliedLendingList.size() > 0){
			for(TableLending tableLending : appliedLendingList){
				FinancialAppliedLoanBean bean = new FinancialAppliedLoanBean();
				bean.setLoanType("Lend");
				bean.setLoanAmount(String.valueOf(tableLending.getLoadSum()));
				bean.setLoanId(String.valueOf(tableLending.getId()));
				financialAppliedList.add(bean);
			}
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, financialAppliedList);
		return event;
	}

}
