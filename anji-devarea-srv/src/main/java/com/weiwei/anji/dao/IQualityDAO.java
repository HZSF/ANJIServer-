package com.weiwei.anji.dao;

public interface IQualityDAO {
	public String insertNewChairInspectionBooking(String username) ;
	public String insertNewFurnitureInspectionBooking(String username) ;
	public String insertNewBambooInspectionBooking(String username) ;
	public String insertNewToyInspectionBooking(String username) ;
	public String insertNewWrapperInspectionBooking(String username) ;
	public boolean[] getInspectionIsApplied(String username);
	public void cancelBookedChairInspection(String username);
	public void cancelBookedFurnitureInspection(String username);
	public void cancelBookedBambooInspection(String username);
	public void cancelBookedToyInspection(String username);
	public void cancelBookedWrapperInspection(String username);
}
