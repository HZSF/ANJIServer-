package com.weiwei.anji.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.QuestionCommentsBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IQuestionDAO;
import com.weiwei.anji.dao.impl.QuestionDAOImpl;
import com.weiwei.anji.dbmodel.TableQuestionComment;
import com.weiwei.service.processors.base.BaseProcessor;

public class GetQuestionCommentProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private List<QuestionCommentsBean> beanList = null;
	private QuestionDAOImpl questionDao;
	private String sessionId;
	@Override
	protected void preProcess(Map scopes){
		questionDao = new QuestionDAOImpl();
		questionDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
		sessionId = (String)scopes.get(Constants.SERVICE_REQUEST);
	}
	@Override
	protected String executeProcess(Map scopes) {
		logger.info("executeProcess..");
		List<TableQuestionComment> tablelist = (List<TableQuestionComment>)questionDao.getQuestionCommentsList(sessionId);
		beanList = new ArrayList<>();
		for(int i=0; i<tablelist.size()-1; i++){
			TableQuestionComment table = tablelist.get(i);
			QuestionCommentsBean bean = new QuestionCommentsBean();
			bean.setComment(table.getComment_content());
			bean.setTime(table.getComment_time().toString());
			bean.setCustomerName(table.getUserName());
			beanList.add(bean);
		}
		return Constants.EVENT_SUCCESS;
	}
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, beanList);
		return event;
	}
}
