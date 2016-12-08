package com.weiwei.anji.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.weiwei.anji.beans.TrademarkBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.dao.ITrademarkDAO;
import com.weiwei.anji.dao.impl.TrademarkDAOImpl;
import com.weiwei.anji.dbmodel.TableTrademarkMonitor;
import com.weiwei.service.processors.base.BaseProcessor;

public class SecRetrieveTrdmkMonProcessor extends BaseProcessor{

	private String username;
	private TrademarkDAOImpl trademarkDao;
	private List<TrademarkBean> result_list;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		trademarkDao = new TrademarkDAOImpl();
		trademarkDao.setJdbcTemplate((JdbcTemplate)scopes.get(Constants.DAOOBJECT));
	}
	@Override
	protected String executeProcess(Map scopes) {
		try{
			List<TableTrademarkMonitor> tablelist = (List<TableTrademarkMonitor>) trademarkDao.findTrademarkMonitorByUserName(username);
			if(tablelist != null && tablelist.size() > 0){
				result_list = new ArrayList<TrademarkBean>();
				for(TableTrademarkMonitor table : tablelist){
					TrademarkBean bean = new TrademarkBean();
					bean.setCategoryNum(String.valueOf(table.getCategory_number()));
					bean.setRegNum(String.valueOf(table.getRegister_id()));
					bean.setName(table.getName());
					result_list.add(bean);
				}
			}
			scopes.put(Constants.SERVICE_RESPONSE, result_list);
			return Constants.EVENT_SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
