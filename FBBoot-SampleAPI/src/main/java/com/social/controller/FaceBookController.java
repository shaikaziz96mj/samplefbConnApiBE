package com.social.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.social.service.FaceBookService;

@RestController
@RequestMapping("/")
public class FaceBookController {
	
	@Autowired
	private FaceBookService facebookService;

	@GetMapping
	public List<String> welcome() {
		List<String> urls=new ArrayList<String>();
		urls.add("http://localhost:4041/generateFaceBookAuthorizeURL");
		urls.add("http://localhost:4041/getUserData");
		urls.add("http://localhost:4041/getUserAccount");
		urls.add("http://localhost:4041/getLongLivedToken");
		urls.add("http://localhost:4041/saveData");
		return urls;
	}
	
	@GetMapping("/generateFaceBookAuthorizeURL")
	public String generateFaceBookAuthorizeURL() {
		return facebookService.generateFaceBookAuthorizeURL();
	}
	
	@GetMapping("/facebook")
	public void generateFaceBookAccessToken(@RequestParam("code") String code) {
		facebookService.generateFaceBookAccessToken(code);
	}
	
	/*@GetMapping("/getUserData")
	public String getUserData(){
		return facebookService.getUserData();
	}*/
	
	@GetMapping("/getUserAccount/{accountId}")
	public Account getUserAccount(@PathVariable String accountId){
		return facebookService.getUserAccount(accountId);
	}
	
	@GetMapping("/getLongLivedToken")
	public String getLongLivedToken() {
		return facebookService.getLongLivedToken();
	}
	
	@GetMapping("/saveData")
	public String saveUserData() {
		return facebookService.saveUserData();
	}
	
}