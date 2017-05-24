package com.weiwei.anji;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ruanwei.interfacej.SmsClientSend;
import com.weiwei.anji.common.Constants;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AnjiDevareaSrvApplication.class)
@WebAppConfiguration
public class AnjiDevareaSrvApplicationTests {

	@Test
	public void contextLoads() {
		String resultFromSmsServer = SmsClientSend.sendSms(Constants.SMS_SERVICE_URL, Constants.SMS_SERVICE_USERID, Constants.SMS_SERVICE_ACCOUNT, 
    			Constants.SMS_SERVICE_PASSWORD, "13751102941", Constants.SMS_SERVICE_FPWD_CONTENT+"019235"+Constants.SMS_SERVICE_SIGNATURE);
    	System.out.println(resultFromSmsServer);
	}
	
}
