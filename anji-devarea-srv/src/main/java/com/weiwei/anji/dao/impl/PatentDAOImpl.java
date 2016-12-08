package com.weiwei.anji.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IPatentDAO;
import com.weiwei.anji.dbmodel.TableAnnualFeeDetail;
import com.weiwei.anji.dbmodel.TableAnnualFeeMonitor;


public class PatentDAOImpl implements IPatentDAO {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
    protected JdbcTemplate jdbcTemplate;
	
	@Override
	public void deleteAnnFeeMonitor(String username, String patentId) {
		logger.info("deleteAnnFeeMonitor(int custId, String patentId)");
		String sql = "UPDATE annual_fee_monitor SET is_deleted='1' WHERE patent_id=? AND customer_id=(SELECT id FROM customers WHERE userName=?) AND is_deleted IS NULL";
		jdbcTemplate.update(sql, patentId, username);
	}
	
	public List<?> findAnnualFeeMonitorByUsername(String username) {
		String sql = "SELECT * FROM annual_fee_monitor WHERE customer_id=(SELECT id FROM customers WHERE userName=?) AND is_deleted IS NULL";
		return jdbcTemplate.query(sql, new String[]{username}, new BeanPropertyRowMapper(TableAnnualFeeMonitor.class));
	}
	
	public boolean[] containAnnualFeeDetailRecordByMonitorIDArray(Integer[] ids){
		List<TableAnnualFeeDetail> result = (List<TableAnnualFeeDetail>)findAnnualFeeDetailByMonitorIDArray(ids);
		if(result != null && result.size() > 0){
			ArrayList<Integer> availableIDs = new ArrayList<Integer>();
			for(TableAnnualFeeDetail tableAFD : result){
				availableIDs.add(tableAFD.getAnnual_fee_monitor_id());
			}
			boolean[] contains = new boolean[ids.length];
			for(int i=0; i<ids.length; i++){
				if(availableIDs.contains(ids[i])){
					contains[i] = true;
				}else{
					contains[i] = false;
				}
			}
			return contains;
		}else{
			return null;
		}
	}
	
	public List<?> findAnnualFeeDetailByMonitorIDArray(Integer[] ids){
		if(ids.length <= 0){
			return null;
		}
		StringBuilder querys = new StringBuilder();
		for(int i=0; i<ids.length-1; i++){
			querys.append("?, ");
		}
		querys.append("?");
		String sql = "SELECT * FROM annual_fee_detail WHERE annual_fee_monitor_id IN (" + querys.toString() + ")";
		return jdbcTemplate.query(sql, ids, new BeanPropertyRowMapper(TableAnnualFeeDetail.class));
	}

	@Override
	public void addDelegateMonitor(String username, String patentId) {
		List<TableAnnualFeeMonitor> tableAnnualFeeMonitorList = (List<TableAnnualFeeMonitor>)findAnnualFeeMonitorByUsername(username);
		if(tableAnnualFeeMonitorList != null && tableAnnualFeeMonitorList.size() > 0){
			TableAnnualFeeMonitor afmTable = tableAnnualFeeMonitorList.get(0);
			int monitorId = afmTable.getId();
			TableAnnualFeeDetail tableDetail = new TableAnnualFeeDetail();
			tableDetail.setAnnual_fee_monitor_id(monitorId);
			Calendar calendar = Calendar.getInstance();
			Timestamp apply_date = new Timestamp(calendar.getTimeInMillis());
			tableDetail.setCustomer_apply_date(apply_date);
			tableDetail.setCustomer_apply_year(calendar.get(Calendar.YEAR));
			String yearMonth = String.valueOf(calendar.get(Calendar.YEAR)) + String.valueOf(calendar.get(Calendar.MONTH));
			tableDetail.setCustomer_apply_year_month(Integer.valueOf(yearMonth));
			addFeeDetail(tableDetail);
		}
	}
	
	public void addFeeDetail(TableAnnualFeeDetail data) {
		// TODO Auto-generated method stub
		String sql_insert = "INSERT INTO annual_fee_detail (annual_fee_monitor_id, customer_apply_date, customer_apply_year, customer_apply_year_month) VALUES (?, ?, ?, ?)";
		int annFeeMonId = data.getAnnual_fee_monitor_id();
		Timestamp applyDate = data.getCustomer_apply_date();
		int applyYear = data.getCustomer_apply_year();
		int applyMonth = data.getCustomer_apply_year_month();
		jdbcTemplate.update(sql_insert, new Object[]{annFeeMonId, applyDate, applyYear, applyMonth});
	}

	@Override
	public String addAchieveTransForm(String username, String patent_id, String title, String price) {
		String sql = "SELECT * FROM annual_fee_monitor WHERE customer_id=(SELECT id FROM customers WHERE userName=?) AND patent_id = ? AND is_deleted IS NULL LIMIT 1";
		List<?> existingResult = jdbcTemplate.query(sql, new String[]{username, patent_id}, new BeanPropertyRowMapper(TableAnnualFeeMonitor.class));
		if(existingResult != null && existingResult.size() > 0){
			return Constants.EVENT_EXISTED;
		}
		Calendar calendar = Calendar.getInstance();
		Timestamp applyDate = new Timestamp(calendar.getTimeInMillis());
		String sql_insert = "INSERT INTO achievement_transform (customer_id, patent_id, title, apply_date, price) VALUES ((SELECT id FROM customers WHERE userName=?), ?, ?, ?, ?)";
		jdbcTemplate.update(sql_insert, new Object[]{username, patent_id, title, applyDate, Double.valueOf(price)});
		return Constants.EVENT_SUCCESS;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
