package com.weiwei.anji.dao;

import java.util.List;

import com.weiwei.anji.dbmodel.TableQuestion;
import com.weiwei.anji.dbmodel.TableQuestionComment;

public interface IQuestionDAO {
	public List<TableQuestion> getQuestionList();
	public void submitQuestion(String username, String question);
	public List<TableQuestionComment> getQuestionCommentsList(String sessionId);
	public void submitComment(String username, String comment, String sessionId);
}
