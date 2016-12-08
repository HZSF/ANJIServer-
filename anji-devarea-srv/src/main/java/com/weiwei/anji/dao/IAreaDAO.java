package com.weiwei.anji.dao;

import java.util.List;

public interface IAreaDAO {
	public List<?> findProvinceList();
	public List<?> findCityListByProvinceId(int province_id);
}
