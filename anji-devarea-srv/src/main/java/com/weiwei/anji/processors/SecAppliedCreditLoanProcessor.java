package com.weiwei.anji.processors;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.FinancialAppliedCreditLoanBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.common.StringUtility;
import com.weiwei.anji.dao.IFinanceDAO;
import com.weiwei.anji.dao.impl.FinanceDAOImpl;
import com.weiwei.anji.dbmodel.TableLoan;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecAppliedCreditLoanProcessor extends BaseProcessor{

	private String loanId;
	private FinancialAppliedCreditLoanBean loanBean;
	private FinanceDAOImpl financeDao;
	
	@Override
	protected void preProcess(Map scopes){
		loanId = (String)scopes.get(Constants.SERVICE_REQUEST);
		loanBean = new FinancialAppliedCreditLoanBean();
		financeDao = new FinanceDAOImpl();
		financeDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		try{
			if(!StringUtility.isEmptyString(loanId)){
				List<TableLoan> appliedLoanList = (List<TableLoan>)financeDao.findCreditLoanById(loanId);
				if(appliedLoanList != null && appliedLoanList.size() > 0){
					TableLoan tableLoan = appliedLoanList.get(0);
					Class<?> loanBeanClass = loanBean.getClass();
					Class<?> tableLoanClass = tableLoan.getClass();
					Field[] fields = loanBeanClass.getFields();
					for(Field field : fields){
						String name = field.getName();
						Field tableField = tableLoanClass.getField(name);
						if(tableField != null){
							Object value = tableField.get(tableLoan);
							field.set(loanBean, value);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, loanBean);
		return event;
	}

}
