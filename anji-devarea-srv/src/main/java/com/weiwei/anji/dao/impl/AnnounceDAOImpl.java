package com.weiwei.anji.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.dao.IAnnounceDAO;
import com.weiwei.anji.dbmodel.Announce;
import com.weiwei.anji.dbmodel.AnnounceNew;

public class AnnounceDAOImpl implements IAnnounceDAO{
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
    protected JdbcTemplate jdbcTemplate;
	
	public List<Announce> findBySequenceId(int startId, int endId) {
		String numbers = String.valueOf(endId-startId+1);
		String sql = "SELECT aid, title, url FROM dede_co_htmls where nid=1 ORDER BY url desc LIMIT "+numbers;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Announce.class));
	}

	public List<?> findByUrl(String url) {
		String sql = "SELECT aid, result FROM dede_co_htmls WHERE url=?";
		return  jdbcTemplate.query(sql, new String[]{url}, new BeanPropertyRowMapper(Announce.class));
	}
	
	public List<Announce> findGovBySequenceId(int startId, int endId) {
		String numbers = String.valueOf(endId-startId+1);
		String sql = "SELECT aid, title, url FROM dede_co_htmls where nid=2 ORDER BY url desc LIMIT "+numbers;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Announce.class));
		
	}

	public List<?> findGovByUrl(String url) {
		String sql = "SELECT aid, result FROM dede_co_htmls WHERE nid=4 AND url=?";
		return  jdbcTemplate.query(sql, new String[]{url}, new BeanPropertyRowMapper(Announce.class));
	}
	
	public List<AnnounceNew> findBySequenceIdNew(int startId, int endId) {
		String numbers = String.valueOf(endId-startId+1);
		String sql = "(select null as aid, t2.title as title, null as url, t1.body as body, FROM_UNIXTIME(t2.pubdate) as pubdate "
				+ "from anjicms_db.dede_addonarticle t1 "
				+ "LEFT JOIN  anjicms_db.dede_archives t2 on t1.aid = t2.id where t2.typeid = 1 and t2.arcrank=0 order by t2.pubdate DESC LIMIT "+numbers+") "
				+ "union all "
				+ "(SELECT aid, title, url, null as body, null as pubdate FROM anjicms_db.dede_co_htmls "
				+ "where nid=1 ORDER BY url desc LIMIT "+numbers+")";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(AnnounceNew.class));
		
	}
	
	public List<AnnounceNew> findGovBySequenceIdNew(int startId, int endId) {
		String numbers = String.valueOf(endId-startId+1);
		String sql = "(select null as aid, t2.title as title, null as url, t1.body as body, FROM_UNIXTIME(t2.pubdate) as pubdate "
				+ "from anjicms_db.dede_addonarticle t1 "
				+ "LEFT JOIN  anjicms_db.dede_archives t2 on t1.aid = t2.id where t2.typeid = 2 and t2.arcrank=0 order by t2.pubdate DESC LIMIT "+numbers+") "
				+ "union all "
				+ "(SELECT aid, title, url, null as body, null as pubdate FROM anjicms_db.dede_co_htmls "
				+ "where nid=2 ORDER BY url desc LIMIT "+numbers+")";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(AnnounceNew.class));
		
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
