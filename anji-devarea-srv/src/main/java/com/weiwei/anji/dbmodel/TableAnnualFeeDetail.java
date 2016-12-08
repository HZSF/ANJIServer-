package com.weiwei.anji.dbmodel;

import java.sql.Timestamp;

public class TableAnnualFeeDetail {
	public int id;
	public int annual_fee_monitor_id;
	public Timestamp annual_fee_paid_date;
	public Integer annual_fee_paid_year_month;
	public Integer annual_fee_paid_year;
	public Double annual_fee;
	public Double service_fee;
	public Short is_transfered_to_us;
	public Timestamp transfered_to_us_date;
	public Timestamp customer_apply_date;
	public Integer customer_apply_year;
	public Integer customer_apply_year_month;
	public String comment;
	public String applicant;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAnnual_fee_monitor_id() {
		return annual_fee_monitor_id;
	}
	public void setAnnual_fee_monitor_id(int annual_fee_monitor_id) {
		this.annual_fee_monitor_id = annual_fee_monitor_id;
	}
	public Timestamp getAnnual_fee_paid_date() {
		return annual_fee_paid_date;
	}
	public void setAnnual_fee_paid_date(Timestamp annual_fee_paid_date) {
		this.annual_fee_paid_date = annual_fee_paid_date;
	}
	public Integer getAnnual_fee_paid_year_month() {
		return annual_fee_paid_year_month;
	}
	public void setAnnual_fee_paid_year_month(Integer annual_fee_paid_year_month) {
		this.annual_fee_paid_year_month = annual_fee_paid_year_month;
	}
	public Integer getAnnual_fee_paid_year() {
		return annual_fee_paid_year;
	}
	public void setAnnual_fee_paid_year(Integer annual_fee_paid_year) {
		this.annual_fee_paid_year = annual_fee_paid_year;
	}
	public Double getAnnual_fee() {
		return annual_fee;
	}
	public void setAnnual_fee(Double annual_fee) {
		this.annual_fee = annual_fee;
	}
	public Double getService_fee() {
		return service_fee;
	}
	public void setService_fee(Double service_fee) {
		this.service_fee = service_fee;
	}
	public Short getIs_transfered_to_us() {
		return is_transfered_to_us;
	}
	public void setIs_transfered_to_us(Short is_transfered_to_us) {
		this.is_transfered_to_us = is_transfered_to_us;
	}
	public Timestamp getTransfered_to_us_date() {
		return transfered_to_us_date;
	}
	public void setTransfered_to_us_date(Timestamp transfered_to_us_date) {
		this.transfered_to_us_date = transfered_to_us_date;
	}
	public Timestamp getCustomer_apply_date() {
		return customer_apply_date;
	}
	public void setCustomer_apply_date(Timestamp customer_apply_date) {
		this.customer_apply_date = customer_apply_date;
	}
	public Integer getCustomer_apply_year() {
		return customer_apply_year;
	}
	public void setCustomer_apply_year(Integer customer_apply_year) {
		this.customer_apply_year = customer_apply_year;
	}
	public Integer getCustomer_apply_year_month() {
		return customer_apply_year_month;
	}
	public void setCustomer_apply_year_month(Integer customer_apply_year_month) {
		this.customer_apply_year_month = customer_apply_year_month;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	
	
}
