package com.weiwei.anji.dao;

import java.util.List;

public interface ITrademarkDAO {
	public String addTrademarkMonitor(String username, String regNum, int categoryNum, String name);
	public List<?> findTrademarkMonitorByUserName(String username);
	public void cancelTrademarkMonitor(String username, String regNum);
	public String addTrademarkTrade(String username, String regNum, int categoryNum, String name, int priceAsk);
}
