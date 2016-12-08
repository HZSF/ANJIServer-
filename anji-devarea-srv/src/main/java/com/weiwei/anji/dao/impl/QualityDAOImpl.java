package com.weiwei.anji.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IQualityDAO;
import com.weiwei.anji.dbmodel.TableUserInspection;


public class QualityDAOImpl implements IQualityDAO {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
    protected JdbcTemplate jdbcTemplate;
	@Override
	public String insertNewChairInspectionBooking(String username) {
		String sql_query = "SELECT * FROM user_inspection_map WHERE userName=?";
		List<?> result = jdbcTemplate.query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
		if(result == null || result.size() == 0){
			String sql_insert = "INSERT INTO user_inspection_map (userName, isApplied_chair) VALUES (?, ?)";
			jdbcTemplate.update(sql_insert, new Object[]{username, "Y"});
			return Constants.EVENT_SUCCESS;
		}else{
			String sql_query1 = "SELECT * FROM user_inspection_map WHERE userName=? AND isApplied_chair='Y'";
			List<?> result1 = jdbcTemplate.query(sql_query1, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
			if(result1 == null || result1.size() == 0){
				String sql_update = "UPDATE user_inspection_map SET isApplied_chair = ? WHERE userName = ?";
				jdbcTemplate.update(sql_update, new Object[]{"Y", username});
				return Constants.EVENT_SUCCESS;
			}else{
				return Constants.EVENT_EXISTED;
			}
		}
	}
	
	@Override
	public String insertNewFurnitureInspectionBooking(String username) {
		String sql_query = "SELECT * FROM user_inspection_map WHERE userName=?";
		List<?> result = jdbcTemplate.query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
		if(result == null || result.size() == 0){
			String sql_insert = "INSERT INTO user_inspection_map (userName, isApplied_furniture) VALUES (?, ?)";
			jdbcTemplate.update(sql_insert, new Object[]{username, "Y"});
			return Constants.EVENT_SUCCESS;
		}else{
			String sql_query1 = "SELECT * FROM user_inspection_map WHERE userName=? AND isApplied_furniture='Y'";
			List<?> result1 = jdbcTemplate.query(sql_query1, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
			if(result1 == null || result1.size() == 0){
				String sql_update = "UPDATE user_inspection_map SET isApplied_furniture = ? WHERE userName = ?";
				jdbcTemplate.update(sql_update, new Object[]{"Y", username});
				return Constants.EVENT_SUCCESS;
			}else{
				return Constants.EVENT_EXISTED;
			}
		}
	}
	
	@Override
	public String insertNewBambooInspectionBooking(String username) {
		String sql_query = "SELECT * FROM user_inspection_map WHERE userName=?";
		List<?> result = jdbcTemplate.query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
		if(result == null || result.size() == 0){
			String sql_insert = "INSERT INTO user_inspection_map (userName, isApplied_bamboo) VALUES (?, ?)";
			jdbcTemplate.update(sql_insert, new Object[]{username, "Y"});
			return Constants.EVENT_SUCCESS;
		}else{
			String sql_query1 = "SELECT * FROM user_inspection_map WHERE userName=? AND isApplied_bamboo='Y'";
			List<?> result1 = jdbcTemplate.query(sql_query1, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
			if(result1 == null || result1.size() == 0){
				String sql_update = "UPDATE user_inspection_map SET isApplied_bamboo = ? WHERE userName = ?";
				jdbcTemplate.update(sql_update, new Object[]{"Y", username});
				return Constants.EVENT_SUCCESS;
			}else{
				return Constants.EVENT_EXISTED;
			}
		}
	}
	
	@Override
	public String insertNewToyInspectionBooking(String username) {
		String sql_query = "SELECT * FROM user_inspection_map WHERE userName=?";
		List<?> result = jdbcTemplate.query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
		if(result == null || result.size() == 0){
			String sql_insert = "INSERT INTO user_inspection_map (userName, isApplied_toy) VALUES (?, ?)";
			jdbcTemplate.update(sql_insert, new Object[]{username, "Y"});
			return Constants.EVENT_SUCCESS;
		}else{
			String sql_query1 = "SELECT * FROM user_inspection_map WHERE userName=? AND isApplied_toy='Y'";
			List<?> result1 = jdbcTemplate.query(sql_query1, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
			if(result1 == null || result1.size() == 0){
				String sql_update = "UPDATE user_inspection_map SET isApplied_toy = ? WHERE userName = ?";
				jdbcTemplate.update(sql_update, new Object[]{"Y", username});
				return Constants.EVENT_SUCCESS;
			}else{
				return Constants.EVENT_EXISTED;
			}
		}
	}
	
	@Override
	public String insertNewWrapperInspectionBooking(String username) {
		String sql_query = "SELECT * FROM user_inspection_map WHERE userName=?";
		List<?> result = jdbcTemplate.query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
		if(result == null || result.size() == 0){
			String sql_insert = "INSERT INTO user_inspection_map (userName, isApplied_packageMaterial) VALUES (?, ?)";
			jdbcTemplate.update(sql_insert, new Object[]{username, "Y"});
			return Constants.EVENT_SUCCESS;
		}else{
			String sql_query1 = "SELECT * FROM user_inspection_map WHERE userName=? AND isApplied_packageMaterial='Y'";
			List<?> result1 = jdbcTemplate.query(sql_query1, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
			if(result1 == null || result1.size() == 0){
				String sql_update = "UPDATE user_inspection_map SET isApplied_packageMaterial = ? WHERE userName = ?";
				jdbcTemplate.update(sql_update, new Object[]{"Y", username});
				return Constants.EVENT_SUCCESS;
			}else{
				return Constants.EVENT_EXISTED;
			}
		}
	}
	
	@Override
	public boolean[] getInspectionIsApplied(String username) {
		boolean[] isApplied = new boolean[5];
		List<TableUserInspection> resultList = getBookedInspectionByUsername(username);
		if(resultList == null || resultList.size() < 1){
			return isApplied;
		}
		TableUserInspection table = resultList.get(0);
		if(table.getIsApplied_chair() != null && Constants.YES.equalsIgnoreCase(table.getIsApplied_chair())){
			isApplied[0] = true;
		}
		if(table.getIsApplied_furniture() != null && Constants.YES.equalsIgnoreCase(table.getIsApplied_furniture())){
			isApplied[1] = true;
		}
		if(table.getIsApplied_bamboo() != null && Constants.YES.equalsIgnoreCase(table.getIsApplied_bamboo())){
			isApplied[2] = true;
		}
		if(table.getIsApplied_toy() != null && Constants.YES.equalsIgnoreCase(table.getIsApplied_toy())){
			isApplied[3] = true;
		}
		if(table.getIsApplied_packageMaterial() != null && Constants.YES.equalsIgnoreCase(table.getIsApplied_packageMaterial())){
			isApplied[4] = true;
		}
		return isApplied;
	}
	
	public List<TableUserInspection> getBookedInspectionByUsername(String username){
		String sql = "SELECT * FROM user_inspection_map WHERE userName=?";
		return jdbcTemplate.query(sql, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
	}
	
	public void cancelBookedChairInspection(String username){
		String sql_query = "SELECT * FROM user_inspection_map WHERE userName=?";
		List<?> result = jdbcTemplate.query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
		if(result != null && result.size() > 0){
			String sql_update = "UPDATE user_inspection_map SET isApplied_chair = ? WHERE userName = ?";
			jdbcTemplate.update(sql_update, new Object[]{"N", username});
		}
	}
	
	public void cancelBookedFurnitureInspection(String username){
		String sql_query = "SELECT * FROM user_inspection_map WHERE userName=?";
		List<?> result = jdbcTemplate.query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
		if(result != null && result.size() > 0){
			String sql_update = "UPDATE user_inspection_map SET isApplied_furniture = ? WHERE userName = ?";
			jdbcTemplate.update(sql_update, new Object[]{"N", username});
		}
	}
	
	public void cancelBookedBambooInspection(String username){
		String sql_query = "SELECT * FROM user_inspection_map WHERE userName=?";
		List<?> result = jdbcTemplate.query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
		if(result != null && result.size() > 0){
			String sql_update = "UPDATE user_inspection_map SET isApplied_bamboo = ? WHERE userName = ?";
			jdbcTemplate.update(sql_update, new Object[]{"N", username});
		}
	}
	
	public void cancelBookedToyInspection(String username){
		String sql_query = "SELECT * FROM user_inspection_map WHERE userName=?";
		List<?> result = jdbcTemplate.query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
		if(result != null && result.size() > 0){
			String sql_update = "UPDATE user_inspection_map SET isApplied_toy = ? WHERE userName = ?";
			jdbcTemplate.update(sql_update, new Object[]{"N", username});
		}
	}
	
	public void cancelBookedWrapperInspection(String username){
		String sql_query = "SELECT * FROM user_inspection_map WHERE userName=?";
		List<?> result = jdbcTemplate.query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
		if(result != null && result.size() > 0){
			String sql_update = "UPDATE user_inspection_map SET isApplied_packageMaterial = ? WHERE userName = ?";
			jdbcTemplate.update(sql_update, new Object[]{"N", username});
		}
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
