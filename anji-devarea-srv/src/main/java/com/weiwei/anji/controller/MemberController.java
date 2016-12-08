package com.weiwei.anji.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weiwei.anji.beans.CourseBean;
import com.weiwei.anji.beans.CustomerInfoBean;
import com.weiwei.anji.beans.FinancialAppliedCreditLoanBean;
import com.weiwei.anji.beans.FinancialAppliedLendingBean;
import com.weiwei.anji.beans.FinancialAppliedLoanBean;
import com.weiwei.anji.beans.InspectionIsAppliedBean;
import com.weiwei.anji.beans.PatentAnnualFeeMonitorBean;
import com.weiwei.anji.beans.PropertyFavoriteBean;
import com.weiwei.anji.beans.PropertyOnLendBean;
import com.weiwei.anji.beans.PropertyOnSellBean;
import com.weiwei.anji.beans.TrademarkBean;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.processors.BookBambooInspectionProcessor;
import com.weiwei.anji.processors.BookChairInspectionProcessor;
import com.weiwei.anji.processors.BookFurnitureInspectionProcessor;
import com.weiwei.anji.processors.BookToyInspectionProcessor;
import com.weiwei.anji.processors.BookWrapperInspectionProcessor;
import com.weiwei.anji.processors.CancelAppointedBambooInspectionProcessor;
import com.weiwei.anji.processors.CancelAppointedChairInspectionProcessor;
import com.weiwei.anji.processors.CancelAppointedFurnitureInspectionProcessor;
import com.weiwei.anji.processors.CancelAppointedToyInspectionProcessor;
import com.weiwei.anji.processors.CancelAppointedWrapperInspectionProcessor;
import com.weiwei.anji.processors.ChangePasswordProcessor;
import com.weiwei.anji.processors.CustInfoGetImgProcessor;
import com.weiwei.anji.processors.CustInfoUpdAreaProcessor;
import com.weiwei.anji.processors.CustInfoUpdImgProcessor;
import com.weiwei.anji.processors.CustomerInfoRetrieveProcessor;
import com.weiwei.anji.processors.CustomerInfoUpdateProcessor;
import com.weiwei.anji.processors.LogoutProcessor;
import com.weiwei.anji.processors.RegisterPostProcessor;
import com.weiwei.anji.processors.SecAddPtntAchiTransProcessor;
import com.weiwei.anji.processors.SecAddPtntAnnFeeMonDetailProcessor;
import com.weiwei.anji.processors.SecAddPtntAnnFeeMonitorProcessor;
import com.weiwei.anji.processors.SecAddTrademarkMonitorProcessor;
import com.weiwei.anji.processors.SecAddTrdmkSellProcessor;
import com.weiwei.anji.processors.SecAppliedCreditLoanProcessor;
import com.weiwei.anji.processors.SecAppliedLendingProcessor;
import com.weiwei.anji.processors.SecAppliedLoanProcessor;
import com.weiwei.anji.processors.SecBookedInspectionProcessor;
import com.weiwei.anji.processors.SecCancelAppliedCreditLoanProcessor;
import com.weiwei.anji.processors.SecCancelAppliedLendingProcessor;
import com.weiwei.anji.processors.SecDeletePtntAnnFeeMonitorProcesor;
import com.weiwei.anji.processors.SecDeleteTrdmkMonProcessor;
import com.weiwei.anji.processors.SecDeregisterCourseProcessor;
import com.weiwei.anji.processors.SecLoanApplicationProcessor;
import com.weiwei.anji.processors.SecOnlendApplicationProcessor;
import com.weiwei.anji.processors.SecPropertyAddBuyProcessor;
import com.weiwei.anji.processors.SecPropertyAddFavorites;
import com.weiwei.anji.processors.SecPropertyAddLendProcessor;
import com.weiwei.anji.processors.SecPropertyAddRentProcessor;
import com.weiwei.anji.processors.SecPropertyAddSellProcessor;
import com.weiwei.anji.processors.SecPropertyCancelFavorites;
import com.weiwei.anji.processors.SecPropertyFavoriteLendListProcessor;
import com.weiwei.anji.processors.SecPropertyFavoriteListProcessor;
import com.weiwei.anji.processors.SecPropertyFavoriteSellListProcessor;
import com.weiwei.anji.processors.SecPropertyMySubmitListProcessor;
import com.weiwei.anji.processors.SecRegisterCourseProcessor;
import com.weiwei.anji.processors.SecRetrievePatentAnnualFeeMonitorProcessor;
import com.weiwei.anji.processors.SecRetrieveRegistedCourseProcessor;
import com.weiwei.anji.processors.SecRetrieveTrdmkMonProcessor;
import com.weiwei.anji.processors.SubmitCommentProcessor;
import com.weiwei.anji.processors.SubmitQuestionProcessor;
import com.weiwei.anji.processors.VerifyFpwdProcessor;
import com.weiwei.anji.request.CommentSubmitRequest;
import com.weiwei.anji.request.CourseRequest;
import com.weiwei.anji.request.CreditLoanRequest;
import com.weiwei.anji.request.CustomerInfoUpdateAreaRequest;
import com.weiwei.anji.request.CustomerInfoUpdateImageRequest;
import com.weiwei.anji.request.CustomerInfoUpdateRequest;
import com.weiwei.anji.request.FpwdResetPasswordRequest;
import com.weiwei.anji.request.LendFormSubmitRequest;
import com.weiwei.anji.request.PropertyAddBuyRequest;
import com.weiwei.anji.request.PropertyAddFavoriteRequest;
import com.weiwei.anji.request.PropertyAddLendRequest;
import com.weiwei.anji.request.PropertyAddRentRequest;
import com.weiwei.anji.request.PropertyAddSellRequest;
import com.weiwei.anji.request.PropertyCancelFavoriteRequest;
import com.weiwei.anji.request.PropertyLendListRequest;
import com.weiwei.anji.request.PropertySellListRequest;
import com.weiwei.anji.request.PtntAddAchiTransRequest;
import com.weiwei.anji.request.PtntAddAnnFeeMoRequest;
import com.weiwei.anji.request.QuestionSubmitRequest;
import com.weiwei.anji.request.TrademarkAddMonitorRequest;
import com.weiwei.anji.request.TrademarkAddSellRequest;
import com.weiwei.anji.security.domain.DomainCredential;
import com.weiwei.anji.security.domain.DomainUser;
import com.weiwei.service.common.response.GeneralServiceResponse;
import com.weiwei.service.processors.base.BaseProcessor;

@RestController
@RequestMapping(value = "/sajsrv")
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private Map<String, Object> scopes = new HashMap<String, Object>();
	private BaseProcessor processor;
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	private String getUsername(HttpServletRequest httpRequest) throws IOException{
		String username_saw = httpRequest.getHeader("X-Auth-Username");
		String username_str = null;
        if(username_saw != null){
        	username_str = URLDecoder.decode(URLDecoder.decode(username_saw, "UTF-8"), "UTF-8");
        }
        return username_str;
	}
	
	@RequestMapping(value="/ping")
	public String ping(HttpServletRequest httpRequest) throws IOException{
        logger.info("ping: " + getUsername(httpRequest));
		return "weiwei anji";
	}
	
	@RequestMapping(value="/verifyRegister")
	public String verifyRegistration(){
		logger.info("verifyRegistration");
		scopes.put(Constants.USERNAME, ((DomainUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		DomainCredential dc = (DomainCredential)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		scopes.put(Constants.PASSWORD, dc.getPassword());
		scopes.put(Constants.PHONENUMBER, dc.getPhoneNumber());
		scopes.put(Constants.COMPANYNAME, dc.getCompanyName());
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new RegisterPostProcessor();
		String result = processor.doProcess(scopes);
		SecurityContextHolder.clearContext();
		return result;
	}
	
	@RequestMapping(value="/verifyFpwd")
	public String verifyFpwd(@RequestBody FpwdResetPasswordRequest request){
		logger.info("verifyFpwd");
		scopes.put(Constants.SERVICE_REQUEST, request);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new VerifyFpwdProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new LogoutProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/cpwd")
	public String changePassword(@RequestBody String password, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, password);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new ChangePasswordProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/getUserInfo")
	public CustomerInfoBean getUserInfo(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new CustomerInfoRetrieveProcessor();
		processor.doProcess(scopes);
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			return (CustomerInfoBean)scopes.get(Constants.SERVICE_RESPONSE);
		}else{
			return null;
		}
	}
	
	@RequestMapping(value="/setUserInfo")
	public String setUserInfo(@RequestBody CustomerInfoUpdateRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new CustomerInfoUpdateProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/setUserInfoArea")
	public String setUserInfoArea(@RequestBody CustomerInfoUpdateAreaRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new CustInfoUpdAreaProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/setUserInfoImg")
	public String setUserInfoImg(final HttpServletRequest httpRequest, final HttpServletResponse response) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		InputStream fileContent = null;
		CustomerInfoUpdateImageRequest imgRequest = new CustomerInfoUpdateImageRequest();
		try {
			Part filePart = httpRequest.getPart("file");
			long size = filePart.getSize();
			fileContent = filePart.getInputStream();
			imgRequest.setImg_data(fileContent);
			imgRequest.setSize(size);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} finally{
		}
		scopes.put(Constants.SERVICE_REQUEST, imgRequest);
		processor = new CustInfoUpdImgProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="getUserInfoImg")
	public byte[] getUserInfoImg(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new CustInfoGetImgProcessor();
		processor.doProcess(scopes);
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			byte[] responseByte = (byte[])scopes.get(Constants.SERVICE_RESPONSE);
			return responseByte;
		}
		return null;
	}
	
	@RequestMapping(value="/finance/loan/submitform")
	public String loanService(@RequestBody CreditLoanRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecLoanApplicationProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/finance/onlend/submitform")
	public String onlendService(@RequestBody LendFormSubmitRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecOnlendApplicationProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/finance/loan/applied")
	public GeneralServiceResponse<FinancialAppliedLoanBean> appliedLoanService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecAppliedLoanProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<FinancialAppliedLoanBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/finance/loan/creditLoanApplied")
	public FinancialAppliedCreditLoanBean appliedCreditLoanService(@RequestBody String loanId){
		scopes.put(Constants.SERVICE_REQUEST, loanId);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecAppliedCreditLoanProcessor();
		processor.doProcess(scopes);
		FinancialAppliedCreditLoanBean serviceResponse = new FinancialAppliedCreditLoanBean();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse = (FinancialAppliedCreditLoanBean)scopes.get(Constants.SERVICE_RESPONSE);
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/finance/loan/lendingApplied")
	public FinancialAppliedLendingBean appliedLendingService(@RequestBody String lendId){
		scopes.put(Constants.SERVICE_REQUEST, lendId);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecAppliedLendingProcessor();
		processor.doProcess(scopes);
		FinancialAppliedLendingBean serviceResponse = new FinancialAppliedLendingBean();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse = (FinancialAppliedLendingBean)scopes.get(Constants.SERVICE_RESPONSE);
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/finance/cancel/creditLoan")
	public String cancelAppliedCreditLoanService(@RequestBody String loanId, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		scopes.put(Constants.SERVICE_REQUEST, loanId);
		processor = new SecCancelAppliedCreditLoanProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/finance/cancel/lend")
	public String cancelAppliedLendingService(@RequestBody String lendId, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		scopes.put(Constants.SERVICE_REQUEST, lendId);
		processor = new SecCancelAppliedLendingProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/registerCourse")
	public String registerCourseService(@RequestBody CourseRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecRegisterCourseProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/retrieveRegisteredCourse")
	public GeneralServiceResponse<CourseBean> retrieveRegistedCourseService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecRetrieveRegistedCourseProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<CourseBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/removeRegisteredCourse")
	public String removeRegisteredCourseService(@RequestBody String courseID, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, courseID);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecDeregisterCourseProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/inspection/book/chair")
	public String chairInspectionBookService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new BookChairInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/inspection/book/furniture")
	public String furnitureInspectionBookService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new BookFurnitureInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/inspection/book/bamboo")
	public String bambooInspectionBookService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new BookBambooInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/inspection/book/toy")
	public String toyInspectionBookService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new BookToyInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/inspection/book/wrapper")
	public String wrapperInspectionBookService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new BookWrapperInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/inspection/booked")
	public InspectionIsAppliedBean inspectionBookedQueryService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecBookedInspectionProcessor();
		processor.doProcess(scopes);
		InspectionIsAppliedBean serviceResponse = new InspectionIsAppliedBean();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse = (InspectionIsAppliedBean)scopes.get(Constants.SERVICE_RESPONSE);
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/inspection/cancel/chair")
	public String cancelAppointedChairInspectionService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new CancelAppointedChairInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/inspection/cancel/furniture")
	public String cancelAppointedFurnitureInspectionService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new CancelAppointedFurnitureInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/inspection/cancel/bamboo")
	public String cancelAppointedBambooInspectionService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new CancelAppointedBambooInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/inspection/cancel/toy")
	public String cancelAppointedToyInspectionService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new CancelAppointedToyInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/inspection/cancel/wrapper")
	public String cancelAppointedWrapperInspectionService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new CancelAppointedWrapperInspectionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/patent/AnnualFeeMonitor")
	public GeneralServiceResponse<PatentAnnualFeeMonitorBean> retrieveMonitoringPatentService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecRetrievePatentAnnualFeeMonitorProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PatentAnnualFeeMonitorBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/patent/add/AnnFeeMon")
	public String addPatentAnnFeeMonitorService(@RequestBody PtntAddAnnFeeMoRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecAddPtntAnnFeeMonitorProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/patent/delete/AnnFeeMon")
	public String deletePatentAnnFeeMonitorService(@RequestBody String patentId, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, patentId);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecDeletePtntAnnFeeMonitorProcesor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/patent/delegate/AnnFeeMon")
	public String SecAddPtntAnnFeeMonDetailProcessor(@RequestBody String patentId, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, patentId);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecAddPtntAnnFeeMonDetailProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/patent/add/AchieveTrans")
	public String SecAddPtntAchiTransProcessor(@RequestBody PtntAddAchiTransRequest request, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, request);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecAddPtntAchiTransProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/trademark/add/monitor")
	public String addTrademarkMonitorService(@RequestBody TrademarkAddMonitorRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecAddTrademarkMonitorProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/trademark/get/monitor")
	public GeneralServiceResponse<TrademarkBean> retrieveMonitoringTrademarkService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecRetrieveTrdmkMonProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<TrademarkBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/trademark/delete/monitor")
	public String deleteTrademarkMonitorService(@RequestBody String regNum,HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, regNum);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecDeleteTrdmkMonProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/trademark/add/sell")
	public String addTrademarkSellService(@RequestBody TrademarkAddSellRequest serviceRequest,HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecAddTrdmkSellProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/question/submit")
	public String submitQuestion(@RequestBody QuestionSubmitRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SubmitQuestionProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/comment/submit")
	public String submitComment(@RequestBody CommentSubmitRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SubmitCommentProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/property/add/sell")
	public String addPropertySellService(@RequestBody PropertyAddSellRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecPropertyAddSellProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/property/add/buy")
	public String addPropertyBuyService(@RequestBody PropertyAddBuyRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecPropertyAddBuyProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/property/add/lend")
	public String addPropertyLendService(@RequestBody PropertyAddLendRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecPropertyAddLendProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/property/add/rent")
	public String addPropertyRentService(@RequestBody PropertyAddRentRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecPropertyAddRentProcessor();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/property/add/favorites")
	public String addPropertyFavorites(@RequestBody PropertyAddFavoriteRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecPropertyAddFavorites();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/property/cancel/favorites")
	public String cancelPropertyFavorites(@RequestBody PropertyCancelFavoriteRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecPropertyCancelFavorites();
		return processor.doProcess(scopes);
	}
	
	@RequestMapping(value="/property/fetch/favorites/sell")
	public GeneralServiceResponse<PropertyOnSellBean> propertyFavoriteSellListService(@RequestBody PropertySellListRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecPropertyFavoriteSellListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PropertyOnSellBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/property/fetch/favorites/lend")
	public GeneralServiceResponse<PropertyOnLendBean> propertyFavoriteSellListService(@RequestBody PropertyLendListRequest serviceRequest, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecPropertyFavoriteLendListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PropertyOnLendBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/property/fetch/favorites")
	public GeneralServiceResponse<PropertyFavoriteBean> propertyFavoriteListService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecPropertyFavoriteListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PropertyFavoriteBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/property/fetch/mysubmit")
	public GeneralServiceResponse<PropertyFavoriteBean> propertyMysubmitListService(HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, getUsername(httpRequest));
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new SecPropertyMySubmitListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PropertyFavoriteBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
}
