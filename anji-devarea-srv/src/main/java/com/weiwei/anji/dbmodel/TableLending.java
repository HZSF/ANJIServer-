package com.weiwei.anji.dbmodel;

import java.sql.Timestamp;

public class TableLending {
	public int id;
	public double loadSum;
	public Timestamp deadLine;
	public int bankId;
	public String userName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLoadSum() {
		return loadSum;
	}
	public void setLoadSum(double loadSum) {
		this.loadSum = loadSum;
	}
	public Timestamp getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(Timestamp deadLine) {
		this.deadLine = deadLine;
	}
	public int getBankId() {
		return bankId;
	}
	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
