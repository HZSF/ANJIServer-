package com.weiwei.anji.dao;

import java.util.List;

public interface IPropertyDAO {
	public void insertSellProperty(String username, String region, String category, int area, int levels, int ask_price, String description);
	public void insertBuyProperty(String username, String region, String category, int area, int levels, int ask_price, String description);
	public void insertLendProperty(String username, String region, String category, int area, int levels, int ask_price, String description);
	public void insertRentProperty(String username, String region, String category, int area, int levels, int ask_price, String description);
	public int addToFavoritesSell(String username, int property_sell_id);
	public int addToFavoritesLend(String username, int property_lend_id);
	public int cancelFavoritesSell(String username, int property_sell_id);
	public int cancelFavoritesLend(String username, int property_lend_id);
	public List<?> getFavoriteSellListLimitedNumberStartFromId(String username, int startId, int n);
	public List<?> getFavoriteSellListLimitedNumber(String username, int n);
	public List<?> getFavoriteLendListLimitedNumberStartFromId(String username, int startId, int n);
	public List<?> getFavoriteLendListLimitedNumber(String username, int n);
	public List<?> getSellingPropertyListLimitedNumberStartFromId(int startId, int n);
	public List<?> getSellingPropertyListLimitedNumber(int n);
	public List<?> getBuyingPropertyListLimitedNumberStartFromId(int startId, int n);
	public List<?> getBuyingPropertyListLimitedNumber(int n);
	public List<?> getLendingPropertyListLimitedNumberStartFromId(int startId, int n);
	public List<?> getLendingPropertyListLimitedNumber(int n);
	public List<?> getRentingPropertyListLimitedNumberStartFromId(int startId, int n);
	public List<?> getRentingPropertyListLimitedNumber(int n);
}
