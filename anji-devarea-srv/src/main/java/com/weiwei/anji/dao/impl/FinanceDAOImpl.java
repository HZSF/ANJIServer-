package com.weiwei.anji.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IFinanceDAO;
import com.weiwei.anji.dbmodel.TableLendWithBank;
import com.weiwei.anji.dbmodel.TableLending;
import com.weiwei.anji.dbmodel.TableLoan;
import com.weiwei.anji.dbmodel.TableUserLoan;

public class FinanceDAOImpl implements IFinanceDAO {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected JdbcTemplate jdbcTemplate;
	
	@Override
	public String insertNewCreditLoanAndUserMap(String username, TableLoan tableLoanData) {
		logger.debug("calling insertNewCreditLoanAndUserMap(String username, TableLoan tableLoanData).");
		if(isExistedCreditLoan(username))
			return Constants.EVENT_EXISTED;
		String loanId = insertNewCreditLoan(username, tableLoanData);
		if(loanId != null && !"".equalsIgnoreCase(loanId)){
			insertUsernameLoanData(username, loanId);
		}
		return Constants.EVENT_SUCCESS;
	}
	public boolean isExistedCreditLoan(String username){
		String sql = "SELECT * FROM loan l JOIN (SELECT loanId FROM loan_usermap lu WHERE lu.`userName`=?) AS loanid ON l.id=loanid AND (l.audit_flag IS NULL OR l.audit_flag<>9)";
		List<?> result = jdbcTemplate.query(sql, new String[]{username}, new BeanPropertyRowMapper(TableLoan.class));
		if(result == null || result.size() == 0){
			return false;
		}else{
			return true;
		}
	}
	public String insertNewCreditLoan(final String username, TableLoan tableLoanData) {
		Object[] params = (Object[])tableLoanData.getFieldValues();
		String sql_insert = "INSERT INTO loan (" + tableLoanData.toJdbcNameString() + ") VALUES (" + tableLoanData.toJdbcInsertString() + ")";
		jdbcTemplate.update(sql_insert, params);
		String sql_query = "SELECT id FROM loan WHERE " + tableLoanData.toJdbcQueryString();
		List<TableLoan> result = jdbcTemplate.query(sql_query, params, new BeanPropertyRowMapper(TableLoan.class));
		if(result.size() >= 1){
			int size = result.size();
			TableLoan tableLoan = result.get(size-1);
			return tableLoan.getId();
		}
		return null;
	}
	public void insertUsernameLoanData(String username, String loanId){
		String sql_query = "SELECT * FROM loan_usermap WHERE userName=? AND loanId=?";
		List<?> result = jdbcTemplate.query(sql_query, new String[]{username, loanId}, new BeanPropertyRowMapper(TableUserLoan.class));
		if(result == null || result.size() == 0){
			String sql_insert = "INSERT INTO loan_usermap (userName, loanId) VALUES (?, ?)";
			jdbcTemplate.update(sql_insert, new Object[]{username, loanId});
		}
	}
	@Override
	public String insertNewLending(String username, double amount, Timestamp deadline, String bankAbbr) {
		if(isExistedLending(username))
			return Constants.EVENT_EXISTED;
		String sql_insert = "INSERT INTO money_lending (loadSum, deadLine, bankId, userName) VALUES (?, ?, (SELECT id FROM anjicms_db.bank WHERE abbreviation=?), ?)";
		jdbcTemplate.update(sql_insert, new Object[]{amount, deadline, bankAbbr, username});
		return Constants.EVENT_SUCCESS;
	}
	
	public boolean isExistedLending(String username){
		String sql = "SELECT * FROM money_lending WHERE userName=? AND (audit_flag IS NULL OR audit_flag<>9)";
		List<?> result = jdbcTemplate.query(sql, new String[]{username}, new BeanPropertyRowMapper(TableLending.class));
		if(result == null || result.size() == 0){
			return false;
		}else{
			return true;
		}
	}
	@Override
	public List<TableLoan> findCreditLoanByUsername(String username) {
		String sql = "SELECT * FROM loan WHERE id=(SELECT loanId FROM loan_usermap WHERE userName=? AND is_cancelled IS NULL) AND (audit_flag IS NULL OR audit_flag<>9)";
		return jdbcTemplate.query(sql, new String[]{username}, new BeanPropertyRowMapper(TableLoan.class));
	}
	@Override
	public List<?> findLendingByUsername(String username) {
		String sql = "SELECT * FROM money_lending WHERE userName=? AND (audit_flag IS NULL OR audit_flag<>9)";
		return jdbcTemplate.query(sql, new String[]{username}, new BeanPropertyRowMapper(TableLending.class));
	}
	@Override
	public List<?> findCreditLoanById(String id) {
		String sql = "SELECT * FROM loan WHERE id=? AND (audit_flag IS NULL OR audit_flag<>9)";
		return jdbcTemplate.query(sql, new String[]{id}, new BeanPropertyRowMapper(TableLoan.class));
	}
	@Override
	public List<?> findLendingById(String lendId) {
		String sql = "SELECT ml.*, b.abbreviation FROM anjicms_db.money_lending ml LEFT JOIN anjicms_db.bank b ON ml.id=? AND ml.`bankId`=b.id";
		return jdbcTemplate.query(sql, new String[]{lendId}, new BeanPropertyRowMapper(TableLendWithBank.class));
	}
	@Override
	public void cancelCreditLoan(String username, String loanId) {
		String sql_update1 = "UPDATE loan_usermap SET is_cancelled='1' WHERE userName='"+username+"' AND loanId='"+loanId+"' AND is_cancelled IS NULL";
		String sql_update2 = "UPDATE loan SET audit_flag='9', audit_comment='取消申请' WHERE id='"+loanId+"'";
		jdbcTemplate.batchUpdate(new String[]{sql_update1, sql_update2});
	}
	@Override
	public void cancelLending(String username, String lendId) {
		String sql = "UPDATE money_lending SET audit_flag='9', audit_comment='取消申请' WHERE id=?";
		jdbcTemplate.update(sql, lendId);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
