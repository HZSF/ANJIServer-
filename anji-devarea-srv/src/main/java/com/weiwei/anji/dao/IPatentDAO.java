package com.weiwei.anji.dao;

import java.util.List;

public interface IPatentDAO {
	public void deleteAnnFeeMonitor(String username, String patentId);
	public List<?> findAnnualFeeMonitorByUsername(String username);
	public boolean[] containAnnualFeeDetailRecordByMonitorIDArray(Integer[] ids);
	public void addDelegateMonitor(String username, String patentId);
	public String addAchieveTransForm(String username, String patent_id, String title, String price);
}
