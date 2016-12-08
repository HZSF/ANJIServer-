package com.weiwei.anji.processors;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IQuestionDAO;
import com.weiwei.anji.dao.impl.QuestionDAOImpl;
import com.weiwei.anji.request.CommentSubmitRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class SubmitCommentProcessor extends BaseProcessor {
	private String username;
	private CommentSubmitRequest request;
	private QuestionDAOImpl questionDao;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (CommentSubmitRequest)scopes.get(Constants.SERVICE_REQUEST);
		questionDao = new QuestionDAOImpl();
		questionDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		try{
			String comment = request.getComment();
			String session_id = request.getSession_id();
			questionDao.submitComment(username, comment, session_id);
			return Constants.EVENT_SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
	}
}
