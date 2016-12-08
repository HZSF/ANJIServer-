package com.weiwei.anji.processors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.QuestionListBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.IQuestionDAO;
import com.weiwei.anji.dao.impl.QuestionDAOImpl;
import com.weiwei.anji.dbmodel.TableQuestion;
import com.weiwei.service.processors.base.BaseProcessor;

public class GetQuestionListProcessor extends BaseProcessor{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private List<QuestionListBean> beanList = null;
	private QuestionDAOImpl questionDao;
	@Override
	protected void preProcess(Map scopes){
		//questionDao = (IQuestionDAO)scopes.get(Constants.DAOOBJECT);
		questionDao = new QuestionDAOImpl();
		questionDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		logger.info("executeProcess..");
		List<TableQuestion> tablelist = (List<TableQuestion>)questionDao.getQuestionList();
		beanList = new ArrayList<>();
		boolean[] status = new boolean[tablelist.size()];
		for(boolean s : status){
			s = false;
		}
		for(int i=0; i<tablelist.size(); i++){
			if(status[i]){
				continue;
			}
			TableQuestion table = tablelist.get(i);
			QuestionListBean bean = new QuestionListBean();
			bean.setNumOfComments(String.valueOf(table.getCount()-1));
			bean.setSession_id(String.valueOf(table.getSession_id()));
			if(table.getCount()-1 > 0){
				ArrayList<String> commentArrayList = new ArrayList();
				ArrayList<String> customernameArrayList = new ArrayList();
				TableQuestion subTable = null;
				boolean veryFirstDone = false;
				for(int j=i; j<tablelist.size(); j++){
					subTable = tablelist.get(j);
					if(subTable.getSession_id() == table.getSession_id()){
						status[j] = true;
						if(!veryFirstDone){
							bean.setUserName(subTable.getUserName());
							bean.setQuestion(subTable.getComment_content());
							bean.setTime(subTable.getComment_time().toString());
							veryFirstDone = true;
						} else {
							commentArrayList.add(subTable.getComment_content());
							customernameArrayList.add(subTable.getUserName());
						}
					}
				}
				bean.setCommentList(commentArrayList.toArray(new String[0]));
				bean.setCustomernameList(customernameArrayList.toArray(new String[0]));
			} else {
				bean.setUserName(table.getUserName());
				bean.setQuestion(table.getComment_content());
				bean.setTime(table.getComment_time().toString());
			}
			beanList.add(bean);
		}
		Collections.reverse(beanList);
		return Constants.EVENT_SUCCESS;
	}
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, beanList);
		return event;
	}
}
