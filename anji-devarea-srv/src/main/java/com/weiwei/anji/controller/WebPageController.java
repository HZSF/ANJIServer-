package com.weiwei.anji.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/ajweb")
public class WebPageController {
	
	private static final Logger logger = LoggerFactory.getLogger(WebPageController.class);
	
	@RequestMapping("/gotoaj")
	public String gotoaj(){
		logger.info("gotoaj");
		return "AnjiIntro";
	}
	
	@RequestMapping("/ajintro")
	public String ajintro(){
		logger.info("ajintro");
		return "AjIntroPage";
	}
	
	@RequestMapping("/ajinvplat")
	public String ajinvplat(){
		logger.info("ajinvplat");
		return "AjInvPlatPage";
	}
	
	@RequestMapping("/znbcywl")
	public String znbcywl(){
		logger.info("znbcywl");
		return "ZnbcyWL";
	}
	
	@RequestMapping("znbcych")
	public String znbcych(){
		logger.info("znbcych");
		return "ZnbcyCH";
	}
	
	@RequestMapping("znbcyxc")
	public String znbcyxc(){
		logger.info("znbcyxc");
		return "ZnbcyXC";
	}
	
	@RequestMapping("znbcyzs")
	public String znbcyzs(){
		logger.info("znbcyzs");
		return "ZnbcyZS";
	}
	
	@RequestMapping("hlfbzd")
	public String hlfbzd(){
		logger.info("hlfbzd");
		return "HlfbZD";
	}
}
