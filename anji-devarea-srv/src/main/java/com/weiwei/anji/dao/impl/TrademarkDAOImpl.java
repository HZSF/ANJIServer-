package com.weiwei.anji.dao.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.weiwei.anji.dao.ITrademarkDAO;
import com.weiwei.anji.dbmodel.TableTrademarkMonitor;
import com.weiwei.anji.dbmodel.TableTrademarkTrade;


public class TrademarkDAOImpl implements ITrademarkDAO {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
    protected JdbcTemplate jdbcTemplate;
	@Override
	public String addTrademarkMonitor(String username, String regNum, int categoryNum, String name) {
		String sql = "SELECT * From trademark_monitor WHERE customer_id=(SELECT id FROM customers WHERE userName=?) AND register_id=? AND is_deleted IS NULL";
		List<?> tableList = jdbcTemplate.query(sql, new Object[]{username, regNum}, new BeanPropertyRowMapper(TableTrademarkMonitor.class));
		if(tableList != null && tableList.size() > 0){
			return "existed";
		}else{
			String sql_insert = "INSERT INTO trademark_monitor (customer_id, register_id, category_number, name) VALUES ((SELECT id FROM customers WHERE userName=?), ?, ?, ?)";
			jdbcTemplate.update(sql_insert, new Object[]{username, regNum, categoryNum, name});
			return "success";
		}
	}
	@Override
	public List<?> findTrademarkMonitorByUserName(String username) {
		String sql = "SELECT * From trademark_monitor WHERE customer_id=(SELECT id FROM customers WHERE userName=?) AND is_deleted IS NULL";
		return jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper(TableTrademarkMonitor.class));
	}
	@Override
	public void cancelTrademarkMonitor(String username, String regNum) {
		String sql = "UPDATE trademark_monitor SET is_deleted='1' WHERE register_id=? AND customer_id=(SELECT id FROM customers WHERE userName=?) AND is_deleted IS NULL";
		jdbcTemplate.update(sql, regNum, username);
	}
	@Override
	public String addTrademarkTrade(String username, String regNum, int categoryNum, String name, int priceAsk) {
		String sql = "SELECT * FROM trademark_trade WHERE customer_id=(SELECT id FROM customers WHERE userName=?) AND register_id=? AND is_deleted IS NULL";
		List<?> tableList = jdbcTemplate.query(sql, new Object[]{username, regNum}, new BeanPropertyRowMapper(TableTrademarkTrade.class));
		if(tableList != null && tableList.size() > 0){
			return "existed";
		}else{
			Calendar calendar = Calendar.getInstance();
			Timestamp date = new Timestamp(calendar.getTimeInMillis());
			String sql_insert = "INSERT INTO trademark_trade (customer_id, register_id, category_number, name, price_ask, submit_date) VALUES ((SELECT id FROM customers WHERE userName=?), ?, ?, ?, ?, ?)";
			jdbcTemplate.update(sql_insert, new Object[]{username, regNum, categoryNum, name, priceAsk, date});
			return "success";
		}
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
