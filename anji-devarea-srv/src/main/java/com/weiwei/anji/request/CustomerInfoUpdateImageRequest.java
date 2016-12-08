package com.weiwei.anji.request;

import java.io.InputStream;

public class CustomerInfoUpdateImageRequest {
	public InputStream img_data;
	public long size;
	public InputStream getImg_data() {
		return img_data;
	}
	public void setImg_data(InputStream img_data) {
		this.img_data = img_data;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
}
