package com.weiwei.anji.common;

public class Constants {
	final public static String SERVICE_REQUEST = "service_request";
	final public static String SERVICE_RESPONSE = "service_response";
	final public static String EVENT_SUCCESS = "success"; 
	final public static String EVENT_FAIL = "fail";
	final public static String EVENT_EXISTED = "existed";
	
	final public static String DAOOBJECT = "dao";
	
	final public static String YES = "Y";
	final public static String NO = "N";
	
	//sms
	final public static String SMS_SERVICE_URL = "http://121.52.209.124:8888/sms.aspx?";
	final public static String SMS_SERVICE_USERID = "5272";
	final public static String SMS_SERVICE_ACCOUNT = "a10103";
	final public static String SMS_SERVICE_PASSWORD = "1235451";
	final public static String SMS_SERVICE_CONTENT = "欢迎注册安吉开发区服务平台，请在2分钟内完成验证，验证码：";
	final public static String SMS_SERVICE_FPWD_CONTENT = "安吉开发区服务平台忘记密码服务，请再2分钟内完成验证，验证码：";
	final public static String SMS_SERVICE_SIGNATURE = "【微未信息】";
	
	//security
	final public static String SECURE_API_PATH = "/sajsrv";
    final public static String AUTHENTICATE_URL = SECURE_API_PATH + "/authenticate";
    final public static String REGISTER_URL = SECURE_API_PATH + "/register";
    final public static String REGISTER_CODE_URL = SECURE_API_PATH + "/registerCode";
    final public static String FORGET_PASSWORD_URL = SECURE_API_PATH + "/fpwd";
    final public static String USERNAME = "username"; 
	final public static String PASSWORD = "password";
	final public static String PHONENUMBER = "phoneNumber";
	final public static String COMPANYNAME = "companyName";
    
    final public static String ERROR_002 = "002";
	final public static String ERROR_003 = "003";
	final public static String ERROR_004 = "004";
	
	//announcement
	final public static String EVENT_ANNOUNCE_BODY = "announce_body";
	final public static String EVENT_ANNOUNCE_LIST = "announce_list";
	final public static String DB_COLUMN_ANNOUNCE_AID = "aid";
	final public static String DB_COLUMN_ANNOUNCE_TITLE = "title";
	final public static String DB_COLUMN_ANNOUNCE_BODY = "body";
	final public static String DB_COLUMN_ANNOUNCE_PUBLISH_TIME = "publishTime";
	final public static String DB_COLUMN_ANNOUNCE_URL = "url";
	final public static String DB_COLUMN_ANNOUNCE_SOURCE = "source";
	final public static String DB_COLUMN_ANNOUNCE_RESULT = "result";
}
