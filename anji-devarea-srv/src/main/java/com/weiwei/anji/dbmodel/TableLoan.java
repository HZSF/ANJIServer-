package com.weiwei.anji.dbmodel;

public class TableLoan {
	public String id;
	public double reg_capital = 0;
    public double total_asset = 0;
    public double total_debt = 0;
    public double bank_loan = 0;
    public double other_debt = 0;
    public double owner_equality = 0;
    public double asset_liability_ratio = 0.0;
    public String register_address = "";
    public String company_contact = "";
    public String legal_representative = "";
    public Short tech_company = 0; 
    public String company_name = "";
    public String main_product = "";
    public double productivity = 0;
    public double production_capacity = 0;
    public double industrial_output = 0;
    public double sales_income = 0;
    public double total_profit = 0;
    public double total_tax = 0;
    public double added_value_tax = 0;
    public String loan_purpose = "";
    public double loan_limit = 0;
    //public String loan_start = "";
    //public String loan_end = "";
    public int loan_period = 0;
    public int companyid = 0;
    
    public Object[] fieldNameString = new Object[]{
    	reg_capital, total_asset, total_debt, bank_loan, other_debt, owner_equality, asset_liability_ratio, register_address,
    	company_contact, legal_representative, tech_company, company_name, main_product, productivity, production_capacity, 
    	industrial_output, sales_income, total_profit, total_tax, added_value_tax, loan_purpose, loan_limit, loan_period, companyid
	};
    
    public Object[] getFieldValues(){
    	Object[] fieldValues = new Object[]{
    			getReg_capital(),
    			getTotal_asset(),
    			getTotal_debt(),
    			getBank_loan(),
    			getOther_debt(),
    			getOwner_equality(), 
    			getAsset_liability_ratio(), 
    			getRegister_address(),
    			getCompany_contact(),
    			getLegal_representative(),
    			getTech_company(),
    			getCompany_name(),
    	    	getMain_product(), 
    	    	getProductivity(), 
    	    	getProduction_capacity(), 
    	    	getIndustrial_output(),
    	    	getSales_income(), 
    	    	getTotal_profit(),
    			getTotal_tax(), 
    			getAdded_value_tax(), 
    			getLoan_purpose(), 
    			getLoan_limit(), 
    			getLoan_period(), 
    			getCompanyid()
    	};
    	
    	return fieldValues;
    }
    
	public String toJdbcNameString() {
		return "reg_capital, total_asset, total_debt, bank_loan, other_debt, owner_equality, asset_liability_ratio"
				+ ", register_address, company_contact, legal_representative, tech_company, company_name"
				+ ", main_product, productivity, production_capacity, industrial_output, sales_income, total_profit"
				+ ", total_tax, added_value_tax, loan_purpose, loan_limit, loan_period, companyid";
	}
	
	public String toJdbcInsertString(){
		String values = "";
		for(int i=0; i<fieldNameString.length; i++){
			values += "?";
			if(i != (fieldNameString.length - 1)){
				values += ", ";
			}
		}
		return values;
	}
	
	public String toJdbcQueryString(){
		return "reg_capital=? AND total_asset=? AND total_debt=? AND bank_loan=? AND other_debt=? AND owner_equality=? AND asset_liability_ratio=? "
				+ "AND register_address=? AND company_contact=? AND legal_representative=? AND tech_company=? AND company_name=? "
		+ "AND main_product=? AND productivity=? AND production_capacity=? AND industrial_output=? AND sales_income=? AND total_profit=?"
		+ "AND total_tax=? AND added_value_tax=? AND loan_purpose=? AND loan_limit=? AND loan_period=? AND companyid=?";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public double getReg_capital() {
		return reg_capital;
	}

	public void setReg_capital(double reg_capital) {
		this.reg_capital = reg_capital;
	}

	public double getTotal_asset() {
		return total_asset;
	}

	public void setTotal_asset(double total_asset) {
		this.total_asset = total_asset;
	}

	public double getTotal_debt() {
		return total_debt;
	}

	public void setTotal_debt(double total_debt) {
		this.total_debt = total_debt;
	}

	public double getBank_loan() {
		return bank_loan;
	}

	public void setBank_loan(double bank_loan) {
		this.bank_loan = bank_loan;
	}

	public double getOther_debt() {
		return other_debt;
	}

	public void setOther_debt(double other_debt) {
		this.other_debt = other_debt;
	}

	public double getOwner_equality() {
		return owner_equality;
	}

	public void setOwner_equality(double owner_equality) {
		this.owner_equality = owner_equality;
	}

	public double getAsset_liability_ratio() {
		return asset_liability_ratio;
	}

	public void setAsset_liability_ratio(double asset_liability_ratio) {
		this.asset_liability_ratio = asset_liability_ratio;
	}

	public String getMain_product() {
		return main_product;
	}

	public void setMain_product(String main_product) {
		this.main_product = main_product;
	}

	public double getProductivity() {
		return productivity;
	}

	public void setProductivity(double productivity) {
		this.productivity = productivity;
	}

	public double getProduction_capacity() {
		return production_capacity;
	}

	public void setProduction_capacity(double production_capacity) {
		this.production_capacity = production_capacity;
	}

	public double getIndustrial_output() {
		return industrial_output;
	}

	public void setIndustrial_output(double industrial_output) {
		this.industrial_output = industrial_output;
	}

	public double getSales_income() {
		return sales_income;
	}

	public void setSales_income(double sales_income) {
		this.sales_income = sales_income;
	}

	public double getTotal_profit() {
		return total_profit;
	}

	public void setTotal_profit(double total_profit) {
		this.total_profit = total_profit;
	}

	public double getTotal_tax() {
		return total_tax;
	}

	public void setTotal_tax(double total_tax) {
		this.total_tax = total_tax;
	}

	public double getAdded_value_tax() {
		return added_value_tax;
	}

	public void setAdded_value_tax(double added_value_tax) {
		this.added_value_tax = added_value_tax;
	}

	public String getLoan_purpose() {
		return loan_purpose;
	}

	public void setLoan_purpose(String loan_purpose) {
		this.loan_purpose = loan_purpose;
	}

	public double getLoan_limit() {
		return loan_limit;
	}

	public void setLoan_limit(double loan_limit) {
		this.loan_limit = loan_limit;
	}

	public int getLoan_period() {
		return loan_period;
	}

	public void setLoan_period(int loan_period) {
		this.loan_period = loan_period;
	}

	public int getCompanyid() {
		return companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}

	public Object[] getFieldNameString() {
		return fieldNameString;
	}

	public void setFieldNameString(Object[] fieldNameString) {
		this.fieldNameString = fieldNameString;
	}

	public String getRegister_address() {
		return register_address;
	}

	public void setRegister_address(String register_address) {
		this.register_address = register_address;
	}

	public String getCompany_contact() {
		return company_contact;
	}

	public void setCompany_contact(String company_contact) {
		this.company_contact = company_contact;
	}

	public String getLegal_representative() {
		return legal_representative;
	}

	public void setLegal_representative(String legal_representative) {
		this.legal_representative = legal_representative;
	}

	public Short getTech_company() {
		return tech_company;
	}

	public void setTech_company(Short tech_company) {
		this.tech_company = tech_company;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
}
