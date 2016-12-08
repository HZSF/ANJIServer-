package com.weiwei.anji.request;

public class LendFormSubmitRequest {
	public double loadSum;
	public String deadLine;
	public String bankAbbr;
	public double getLoadSum() {
		return loadSum;
	}
	public void setLoadSum(double loadSum) {
		this.loadSum = loadSum;
	}
	public String getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
	}
	public String getBankAbbr() {
		return bankAbbr;
	}
	public void setBankAbbr(String bankAbbr) {
		this.bankAbbr = bankAbbr;
	}
}
