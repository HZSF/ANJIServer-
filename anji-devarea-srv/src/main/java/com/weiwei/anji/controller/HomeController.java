package com.weiwei.anji.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ruanwei.interfacej.SmsClientOverage;
import com.weiwei.anji.autowiredbeans.VersionBean;
import com.weiwei.anji.beans.AnnouncementBean;
import com.weiwei.anji.beans.CityBean;
import com.weiwei.anji.beans.PropertyOnBuyBean;
import com.weiwei.anji.beans.PropertyOnLendBean;
import com.weiwei.anji.beans.PropertyOnRentBean;
import com.weiwei.anji.beans.PropertyOnSellBean;
import com.weiwei.anji.beans.QuestionCommentsBean;
import com.weiwei.anji.beans.QuestionListBean;
import com.weiwei.anji.beans.TrademarkGoodsServiceBean;
import com.weiwei.anji.beans.VersionInfo;
import com.weiwei.anji.common.Constants;
import com.weiwei.anji.processors.AnnounceNewGovProcessor;
import com.weiwei.anji.processors.AnnouncementNewProcessor;
import com.weiwei.anji.processors.GetCityListProcessor;
import com.weiwei.anji.processors.GetProvinceListProcessor;
import com.weiwei.anji.processors.GetQuestionCommentProcessor;
import com.weiwei.anji.processors.GetQuestionListProcessor;
import com.weiwei.anji.processors.PropertyBuyListProcessor;
import com.weiwei.anji.processors.PropertyLendListProcessor;
import com.weiwei.anji.processors.PropertyRentListProcessor;
import com.weiwei.anji.processors.PropertySellListProcessor;
import com.weiwei.anji.processors.TrademarkGoodsServiceProcessor;
import com.weiwei.anji.request.AnnouncementRequest;
import com.weiwei.anji.request.PropertyBuyListRequest;
import com.weiwei.anji.request.PropertyLendListRequest;
import com.weiwei.anji.request.PropertyRentListRequest;
import com.weiwei.anji.request.PropertySellListRequest;
import com.weiwei.anji.request.QuestionSubmitRequest;
import com.weiwei.anji.request.TrademarkGoodsServiceRequest;
import com.weiwei.service.common.response.GeneralServiceResponse;
import com.weiwei.service.processors.base.BaseProcessor;

@RestController
@RequestMapping(value = "/ajsrv")
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private Map<String, Object> scopes = new HashMap<String, Object>();
	private BaseProcessor processor;
	
	@Autowired
	private VersionBean versionBean;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model){
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		return "home";
	}
	
	@RequestMapping(value="/version")
    public String versionService(Locale locale, Model model){
		logger.info("Version api! The client locale is {}.", locale);
    	return versionBean.getVersion();
    }
	
	@RequestMapping(value="/android/version")
    public VersionInfo androidVersionService(Locale locale, Model model){
		logger.info("Version api! The client locale is {}.", locale);
		VersionInfo versionInfo = new VersionInfo();
		versionInfo.setVersion(versionBean.getAndroidVersion());
    	return versionInfo;
    }
	
	@RequestMapping(value="/test")
    public String testService(@RequestBody QuestionSubmitRequest serviceRequest){
    	return serviceRequest.question;
    }
	
	@RequestMapping(value = "/sms/credit")
	public String smsCredit() {
		logger.info("query sms credit!");
		return SmsClientOverage.queryOverage(Constants.SMS_SERVICE_URL, Constants.SMS_SERVICE_USERID, Constants.SMS_SERVICE_ACCOUNT, 
    			Constants.SMS_SERVICE_PASSWORD);
	}
	
	@RequestMapping(value="/announcement",  method = RequestMethod.POST)
	public GeneralServiceResponse<AnnouncementBean> announcementService(@RequestBody AnnouncementRequest serviceRequest) {
		logger.info("/announcement!");
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new AnnouncementNewProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<AnnouncementBean> serviceResponse = new GeneralServiceResponse<AnnouncementBean>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get("service_response"));
		}	
		return serviceResponse;
	}
	
	@RequestMapping(value="/govdoc",  method = RequestMethod.POST)
	public GeneralServiceResponse<AnnouncementBean> govdocService(@RequestBody AnnouncementRequest serviceRequest) {
		logger.info("/govdoc!");
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new AnnounceNewGovProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<AnnouncementBean> serviceResponse = new GeneralServiceResponse<AnnouncementBean>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get("service_response"));
		}	
		return serviceResponse;
	}
	
	@RequestMapping(value="/province")
	public GeneralServiceResponse<String> getProvinceList(){
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new GetProvinceListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<String> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/city")
	public GeneralServiceResponse<CityBean> getCityList(@RequestBody String provinceId){
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		scopes.put(Constants.SERVICE_REQUEST, provinceId);
		processor = new GetCityListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<CityBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/questions")
	public GeneralServiceResponse<QuestionListBean> questionList(){
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new GetQuestionListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<QuestionListBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/question/comments")
	public GeneralServiceResponse<QuestionCommentsBean> commentsList(@RequestBody String sessionId){
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		scopes.put(Constants.SERVICE_REQUEST, sessionId);
		processor = new GetQuestionCommentProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<QuestionCommentsBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/property/fetch/sell")
	public GeneralServiceResponse<PropertyOnSellBean> propertyOnSellListService(@RequestBody PropertySellListRequest serviceRequest){
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new PropertySellListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PropertyOnSellBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/property/fetch/buy")
	public GeneralServiceResponse<PropertyOnBuyBean> propertyOnBuyListService(@RequestBody PropertyBuyListRequest serviceRequest){
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new PropertyBuyListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PropertyOnBuyBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/property/fetch/lend")
	public GeneralServiceResponse<PropertyOnLendBean> propertyOnLendListService(@RequestBody PropertyLendListRequest serviceRequest){
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new PropertyLendListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PropertyOnLendBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/property/fetch/rent")
	public GeneralServiceResponse<PropertyOnRentBean> propertyOnRentListService(@RequestBody PropertyRentListRequest serviceRequest){
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new PropertyRentListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PropertyOnRentBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/trademark/goodsservice")
	public GeneralServiceResponse<TrademarkGoodsServiceBean> trademarkGoodsService(@RequestBody TrademarkGoodsServiceRequest serviceRequest){
		scopes.put(Constants.SERVICE_REQUEST, serviceRequest);
		processor = new TrademarkGoodsServiceProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<TrademarkGoodsServiceBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	/*
	@RequestMapping(value="/changepwd")
	public String changePassword(@RequestBody FpwdCpwdRequest request, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.SERVICE_REQUEST, request);
		scopes.put(Constants.DAOOBJECT, daosBean.getCustomerDao());
		processor = new FpwdCpwdProcessor();
		return processor.doProcess(scopes);
	}
	*/
	/*
	@RequestMapping(value="/changepwd")
	public String changePassword(@RequestBody String password, HttpServletRequest httpRequest) throws IOException{
		scopes.put(Constants.USERNAME, "魏征");
		scopes.put(Constants.SERVICE_REQUEST, password);
		scopes.put(Constants.DAOOBJECT, jdbcTemplate);
		processor = new ChangePasswordProcessor();
		return processor.doProcess(scopes);
	}*/
}
