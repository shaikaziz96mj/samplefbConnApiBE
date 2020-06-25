package com.social.service;

import org.springframework.social.facebook.api.Account;

public interface FaceBookService {

	public String generateFaceBookAuthorizeURL();

	public void generateFaceBookAccessToken(String code);

	//public String getUserData();

	public Account getUserAccount(String accountId);

	public String getLongLivedToken();

	public String saveUserData();

}