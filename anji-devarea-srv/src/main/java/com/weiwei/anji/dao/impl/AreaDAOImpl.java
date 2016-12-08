package com.weiwei.anji.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.dao.IAreaDAO;
import com.weiwei.anji.dbmodel.ProvinceTable;
import com.weiwei.anji.dbmodel.TableCities;


public class AreaDAOImpl implements IAreaDAO {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected JdbcTemplate jdbcTemplate;
	
	@Override
	public List<?> findProvinceList() {
		logger.debug("calling findProvinceList().");
    	String sql = "SELECT * FROM province";
    	return jdbcTemplate.query(sql, new BeanPropertyRowMapper(ProvinceTable.class));
	}
	@Override
	public List<?> findCityListByProvinceId(int province_id){
		logger.debug("calling findCityListByProvinceId(int province_id).");
    	String sql = "SELECT * FROM cities WHERE province_id=?";
    	return jdbcTemplate.query(sql, new Object[]{province_id}, new BeanPropertyRowMapper(TableCities.class));
    }
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
