package com.weiwei.anji.dao.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.dao.IQuestionDAO;
import com.weiwei.anji.dbmodel.TableQuestion;
import com.weiwei.anji.dbmodel.TableQuestionComment;

public class QuestionDAOImpl implements IQuestionDAO {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected JdbcTemplate jdbcTemplate;
	@Override
	public List<TableQuestion> getQuestionList() {
		String sql = "select c.*, c1.userName, (select count(*) from anjicms_db.comment c_sub WHERE c_sub.session_id=c.session_id) as count "
				+ "from anjicms_db.comment c left join anjicms_db.customers c1 on c.customer_id=c1.id ORDER BY comment_time ASC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(TableQuestion.class));
	}
	@Override
	public void submitQuestion(String username, String question) {
		if(question.contains("'")){
			question = question.replaceAll("'", "\\\\'");
		}
		Calendar calendar = Calendar.getInstance();
		Timestamp date = new Timestamp(calendar.getTimeInMillis());
		String sql_insert1 = "INSERT INTO comment_session (announce_id) VALUES (0)";
		String sql_insert2 = "INSERT INTO comment (comment_content, comment_time, session_id, customer_id) VALUES ('" + question + "', '" + date + "', (SELECT LAST_INSERT_ID()), "
				+ "(SELECT id FROM customers WHERE userName='" +username+"'))";
		jdbcTemplate.batchUpdate(new String[]{sql_insert1, sql_insert2});
	}
	@Override
	public List<TableQuestionComment> getQuestionCommentsList(String sessionId) {
		String sql = "select c.*, c1.userName from (select * FROM anjicms_db.comment where session_id=?) c left join anjicms_db.customers c1 "
				+ "on c.customer_id=c1.id ORDER BY c.comment_time DESC";
		return jdbcTemplate.query(sql, new Object[]{sessionId}, new BeanPropertyRowMapper(TableQuestionComment.class));
	}
	@Override
	public void submitComment(String username, String comment, String sessionId) {
		if(comment.contains("'")){
			comment = comment.replaceAll("'", "\\\\'");
		}
		Calendar calendar = Calendar.getInstance();
		Timestamp date = new Timestamp(calendar.getTimeInMillis());
		String sql_insert = "INSERT INTO comment (comment_content, comment_time, session_id, customer_id) VALUES (?, ?, ?, (SELECT id FROM customers WHERE userName=?))";
		jdbcTemplate.update(sql_insert, new Object[]{comment, date, sessionId, username});
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
