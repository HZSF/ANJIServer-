package com.ruanwei.interfacej;

import java.net.URLEncoder;

import com.ruanwei.tool.SmsClientAccessTool;

public class SmsClientOverage {
	public static String queryOverage(String url, String userid,
			String account, String password) {

		try {
			StringBuffer sendParam = new StringBuffer();
			sendParam.append("action=overage");
			sendParam.append("&userid=").append(userid);
			sendParam.append("&account=").append(
					URLEncoder.encode(account, "UTF-8"));
			sendParam.append("&password=").append(
					URLEncoder.encode(password, "UTF-8"));

			return SmsClientAccessTool.getInstance().doAccessHTTPPost(url,
					sendParam.toString(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "exception->" + e.getMessage();
		}
	}
}
