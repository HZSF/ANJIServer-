package com.weiwei.anji.processors;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IQuestionDAO;
import com.weiwei.anji.dao.impl.QuestionDAOImpl;
import com.weiwei.anji.request.QuestionSubmitRequest;
import com.weiwei.service.processors.base.BaseProcessor;

public class SubmitQuestionProcessor extends BaseProcessor {
	private String username;
	private QuestionSubmitRequest request;
	private QuestionDAOImpl questionDao;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (QuestionSubmitRequest)scopes.get(Constants.SERVICE_REQUEST);
		questionDao = new QuestionDAOImpl();
		questionDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		try{
			String question = request.getQuestion();
			questionDao.submitQuestion(username, question);
			return Constants.EVENT_SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
	}
}
