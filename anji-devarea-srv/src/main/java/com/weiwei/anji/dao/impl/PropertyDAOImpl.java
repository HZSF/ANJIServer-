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

import com.weiwei.anji.dao.IPropertyDAO;
import com.weiwei.anji.dbmodel.PropertyBuyJoinTable;
import com.weiwei.anji.dbmodel.PropertyFavoriteJoinTable;
import com.weiwei.anji.dbmodel.PropertyLendJoinTable;
import com.weiwei.anji.dbmodel.PropertyRentJoinTable;
import com.weiwei.anji.dbmodel.PropertySellJoinTable;

public class PropertyDAOImpl implements IPropertyDAO {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected JdbcTemplate jdbcTemplate;
	@Override
	public void insertSellProperty(String username, String region, String category, int area, int levels, int ask_price,
			String description) {
		Calendar calendar = Calendar.getInstance();
		Timestamp date = new Timestamp(calendar.getTimeInMillis());
		String sql_insert = "INSERT INTO anjicms_db.property_sell (region_id, category_id, area, levels, ask_price, description, submit_time, customer_id) "
				+ "VALUES((SELECT id FROM anjicms_db.regions WHERE name = ? ), (SELECT id FROM anjicms_db.property_category WHERE name = ? ), ?, ?, ?, ?, ?,"
				+ "(SELECT id FROM customers WHERE userName= ? ))";
		jdbcTemplate.update(sql_insert, new Object[]{region, category, area, levels, ask_price, description, date, username});
	}
	@Override
	public void insertBuyProperty(String username, String region, String category, int area, int levels, int ask_price,
			String description) {
		Calendar calendar = Calendar.getInstance();
		Timestamp date = new Timestamp(calendar.getTimeInMillis());
		String sql_insert = "INSERT INTO anjicms_db.property_buy (region_id, category_id, area, levels, ask_price, description, submit_time, customer_id) "
				+ "VALUES((SELECT id FROM anjicms_db.regions WHERE name = ? ), (SELECT id FROM anjicms_db.property_category WHERE name = ? ), ?, ?, ?, ?, ?,"
				+ "(SELECT id FROM customers WHERE userName= ? ))";
		jdbcTemplate.update(sql_insert, new Object[]{region, category, area, levels, ask_price, description, date, username});
	}
	@Override
	public void insertLendProperty(String username, String region, String category, int area, int levels, int ask_price,
			String description) {
		Calendar calendar = Calendar.getInstance();
		Timestamp date = new Timestamp(calendar.getTimeInMillis());
		String sql_insert = "INSERT INTO anjicms_db.property_lend (region_id, category_id, area, levels, ask_price, description, submit_time, customer_id) "
				+ "VALUES((SELECT id FROM anjicms_db.regions WHERE name = ? ), (SELECT id FROM anjicms_db.property_category WHERE name = ? ), ?, ?, ?, ?, ?,"
				+ "(SELECT id FROM customers WHERE userName= ? ))";
		jdbcTemplate.update(sql_insert, new Object[]{region, category, area, levels, ask_price, description, date, username});
	}
	@Override
	public void insertRentProperty(String username, String region, String category, int area, int levels, int ask_price,
			String description) {
		Calendar calendar = Calendar.getInstance();
		Timestamp date = new Timestamp(calendar.getTimeInMillis());
		String sql_insert = "INSERT INTO anjicms_db.property_rent (region_id, category_id, area, levels, ask_price, description, submit_time, customer_id) "
				+ "VALUES((SELECT id FROM anjicms_db.regions WHERE name = ? ), (SELECT id FROM anjicms_db.property_category WHERE name = ? ), ?, ?, ?, ?, ?,"
				+ "(SELECT id FROM customers WHERE userName= ? ))";
		jdbcTemplate.update(sql_insert, new Object[]{region, category, area, levels, ask_price, description, date, username});
	}
	@Override
	public int addToFavoritesSell(String username, int property_sell_id) {
		String sql_insert = "INSERT INTO anjicms_db.property_favorite_sell (customer_id, property_sell_id) "
				+ "SELECT * FROM (SELECT (SELECT id FROM anjicms_db.customers WHERE userName = ?), ?) AS tmp "
				+ "WHERE NOT EXISTS (SELECT pf.id FROM anjicms_db.property_favorite_sell pf, anjicms_db.customers cust "
				+ "WHERE pf.customer_id = cust.id AND cust.`userName` = ? AND pf.is_cancelled IS NULL AND pf.property_sell_id=?) LIMIT 1";
		return jdbcTemplate.update(sql_insert, new Object[]{username, property_sell_id, username, property_sell_id});
	}
	@Override
	public int addToFavoritesLend(String username, int property_lend_id) {
		String sql_insert = "INSERT INTO anjicms_db.property_favorite_lend (customer_id, property_lend_id) "
				+ "SELECT * FROM (SELECT (SELECT id FROM anjicms_db.customers WHERE userName = ?), ?) AS tmp "
				+ "WHERE NOT EXISTS (SELECT pf.id FROM anjicms_db.property_favorite_lend pf, anjicms_db.customers cust "
				+ "WHERE pf.customer_id = cust.id AND cust.`userName` = ? AND pf.is_cancelled IS NULL AND pf.property_lend_id=?) LIMIT 1";
		return jdbcTemplate.update(sql_insert, new Object[]{username, property_lend_id, username, property_lend_id});
	}
	@Override
	public int cancelFavoritesSell(String username, int property_sell_id) {
		String sql = "UPDATE property_favorite_sell SET is_cancelled='1' WHERE property_sell_id=? AND is_cancelled IS NULL "
				+ "AND customer_id=(SELECT id FROM anjicms_db.customers WHERE userName=?)";
		return jdbcTemplate.update(sql, property_sell_id, username);
	}
	@Override
	public int cancelFavoritesLend(String username, int property_lend_id) {
		String sql = "UPDATE property_favorite_lend SET is_cancelled='1' WHERE property_lend_id=? AND is_cancelled IS NULL "
				+ "AND customer_id=(SELECT id FROM anjicms_db.customers WHERE userName=?)";
		return jdbcTemplate.update(sql, property_lend_id, username);
	}
	@Override
	public List<?> getFavoriteSellListLimitedNumberStartFromId(String username, int startId, int n) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_sell ps, customers cust, property_favorite_sell pfs "
				+ "WHERE cust.userName = ? AND cust.id=pfs.customer_id AND pfs.is_cancelled IS NULL AND pfs.property_sell_id=ps.id "
				+ "AND r.id=ps.region_id AND p.id=ps.category_id AND p.id > ? LIMIT "+ n;
		return jdbcTemplate.query(sql, new Object[]{username, startId}, new BeanPropertyRowMapper(PropertySellJoinTable.class));
	}
	@Override
	public List<?> getFavoriteSellListLimitedNumber(String username, int n) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_sell ps, customers cust, property_favorite_sell pfs "
				+ "WHERE cust.userName = ? AND cust.id=pfs.customer_id AND pfs.is_cancelled IS NULL AND pfs.property_sell_id=ps.id "
				+ "AND r.id=ps.region_id AND p.id=ps.category_id LIMIT "+ n;
		return jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper(PropertySellJoinTable.class));
	}
	@Override
	public List<?> getFavoriteLendListLimitedNumberStartFromId(String username, int startId, int n) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_lend ps, customers cust, property_favorite_lend pfs "
				+ "WHERE cust.userName = ? AND cust.id=pfs.customer_id AND pfs.is_cancelled IS NULL AND pfs.property_lend_id=ps.id "
				+ "AND r.id=ps.region_id AND p.id=ps.category_id AND p.id > ? LIMIT "+ n;
		return jdbcTemplate.query(sql, new Object[]{username, startId}, new BeanPropertyRowMapper(PropertyLendJoinTable.class));
	}
	@Override
	public List<?> getFavoriteLendListLimitedNumber(String username, int n) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_lend ps, customers cust, property_favorite_lend pfs "
				+ "WHERE cust.userName = ? AND cust.id=pfs.customer_id AND pfs.is_cancelled IS NULL AND pfs.property_lend_id=ps.id "
				+ "AND r.id=ps.region_id AND p.id=ps.category_id LIMIT "+ n;
		return jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper(PropertyLendJoinTable.class));
	}
	
	public List<?> getFavoriteList(String username) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, "
				+ "ps.ask_price, ps.description, ps.submit_time, '1' as type "
				+ "FROM anjicms_db.regions r, anjicms_db.property_category p, anjicms_db.property_sell ps, "
				+ "anjicms_db.customers cust, anjicms_db.property_favorite_sell pfs "
				+ "WHERE cust.userName = ? AND cust.id=pfs.customer_id AND pfs.is_cancelled IS NULL "
				+ "AND pfs.property_sell_id=ps.id AND r.id=ps.region_id AND p.id=ps.category_id "
				+ "Union "
				+ "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, "
				+ "ps.ask_price, ps.description, ps.submit_time, '2' as type FROM anjicms_db.regions r, "
				+ "anjicms_db.property_category p, anjicms_db.property_lend ps, anjicms_db.customers cust, anjicms_db.property_favorite_lend pfs "
				+ "WHERE cust.userName = ? AND cust.id=pfs.customer_id AND pfs.is_cancelled IS NULL "
				+ "AND pfs.property_lend_id=ps.id AND r.id=ps.region_id AND p.id=ps.category_id";
		return jdbcTemplate.query(sql, new Object[]{username, username}, new BeanPropertyRowMapper(PropertyFavoriteJoinTable.class));
	}
	
	public List<?> getMySubmitList(String username) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, "
				+ "ps.ask_price, ps.description, ps.submit_time, '1' as type "
				+ "FROM anjicms_db.regions r, anjicms_db.property_category p, anjicms_db.property_sell ps, "
				+ "anjicms_db.customers cust "
				+ "WHERE cust.userName = ? AND cust.id=ps.customer_id AND r.id=ps.region_id "
				+ "AND p.id=ps.category_id "
				+ "Union "
				+ "SELECT ps1.id, r.name AS address_area, p.name AS category_name, ps1.area, ps1.levels, "
				+ "ps1.ask_price, ps1.description, ps1.submit_time, '2' as type "
				+ "FROM anjicms_db.regions r, anjicms_db.property_category p, anjicms_db.property_lend ps1, "
				+ "anjicms_db.customers cust "
				+ "WHERE cust.userName = ? AND cust.id=ps1.customer_id "
				+ "AND r.id=ps1.region_id AND p.id=ps1.category_id";
		return jdbcTemplate.query(sql, new Object[]{username, username}, new BeanPropertyRowMapper(PropertyFavoriteJoinTable.class));
	}
	
	@Override
	public List<?> getSellingPropertyListLimitedNumberStartFromId(int startId, int n) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_sell ps WHERE r.id=ps.region_id AND p.id=ps.category_id AND ps.id > ? LIMIT "+ n;
		return jdbcTemplate.query(sql, new Object[]{startId}, new BeanPropertyRowMapper(PropertySellJoinTable.class));
	}
	@Override
	public List<?> getSellingPropertyListLimitedNumber(int n) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_sell ps WHERE r.id=ps.region_id AND p.id=ps.category_id LIMIT "+ n;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(PropertySellJoinTable.class));
	}
	
	@Override
	public List<?> getBuyingPropertyListLimitedNumberStartFromId(int startId, int n) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_buy ps WHERE r.id=ps.region_id AND p.id=ps.category_id AND ps.id > ? LIMIT "+ n;
		return jdbcTemplate.query(sql, new Object[]{startId}, new BeanPropertyRowMapper(PropertyBuyJoinTable.class));
	}
	@Override
	public List<?> getBuyingPropertyListLimitedNumber(int n) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_buy ps WHERE r.id=ps.region_id AND p.id=ps.category_id LIMIT "+ n;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(PropertyBuyJoinTable.class));
	}
	
	@Override
	public List<?> getLendingPropertyListLimitedNumberStartFromId(int startId, int n) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_lend ps WHERE r.id=ps.region_id AND p.id=ps.category_id AND ps.id > ? LIMIT "+ n;
		return jdbcTemplate.query(sql, new Object[]{startId}, new BeanPropertyRowMapper(PropertyLendJoinTable.class));
	}
	@Override
	public List<?> getLendingPropertyListLimitedNumber(int n) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_lend ps WHERE r.id=ps.region_id AND p.id=ps.category_id LIMIT "+ n;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(PropertyLendJoinTable.class));
	}
	
	@Override
	public List<?> getRentingPropertyListLimitedNumberStartFromId(int startId, int n) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_rent ps WHERE r.id=ps.region_id AND p.id=ps.category_id AND ps.id > ? LIMIT "+ n;
		return jdbcTemplate.query(sql, new Object[]{startId}, new BeanPropertyRowMapper(PropertyRentJoinTable.class));
	}
	@Override
	public List<?> getRentingPropertyListLimitedNumber(int n) {
		String sql = "SELECT ps.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_rent ps WHERE r.id=ps.region_id AND p.id=ps.category_id LIMIT "+ n;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(PropertyRentJoinTable.class));
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
