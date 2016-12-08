package com.weiwei.anji.dao;

import java.sql.Timestamp;
import java.util.List;

import com.weiwei.anji.dbmodel.TableLoan;

public interface IFinanceDAO {
	public String insertNewCreditLoanAndUserMap(String username, TableLoan tableLoanData);
	public String insertNewLending(String username, double amount, Timestamp deadline, String bankAbbr);
	public List<TableLoan> findCreditLoanByUsername(String username);
	public List<?> findLendingByUsername(String username);
	public List<?> findCreditLoanById(String id);
	public List<?> findLendingById(String lendId);
	public void cancelCreditLoan(String username, String loanId);
	public void cancelLending(String username, String lendId);
}
