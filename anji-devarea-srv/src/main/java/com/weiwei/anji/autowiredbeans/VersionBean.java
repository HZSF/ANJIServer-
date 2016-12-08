package com.weiwei.anji.autowiredbeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VersionBean {
	private String version;
	private String androidVersion;
	@Autowired
    public VersionBean(@Value("${spring.anji.service.version}") String version, @Value("${spring.anji.android.version}") String androidversion){
		this.version = version;
		androidVersion = androidversion;
	}
	public String getVersion() {
		return version;
	}
	public String getAndroidVersion() {
		return androidVersion;
	}
}
