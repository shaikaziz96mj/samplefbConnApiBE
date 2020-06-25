package com.social.serviceimpl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import com.social.domain.FbConnections;
import com.social.repository.FbConnectRepo;
import com.social.service.FaceBookService;

@Service
public class FaceBookServiceImpl implements FaceBookService {

	@Autowired
	FbConnectRepo fbRepo;

	private String accessToken;

	@Value("${spring.social.facebook.app-id}")
	private String facebookAppID;
	@Value("${spring.social.facebook.app-secret}")
	private String facebookSecret;

	private FacebookConnectionFactory createConnection() {
		return new FacebookConnectionFactory(facebookAppID, facebookSecret);
	}

	@Override
	public String generateFaceBookAuthorizeURL() {
		OAuth2Parameters params=new OAuth2Parameters();
		params.setRedirectUri("http://localhost:4041/facebook");
		params.setScope("email");
		return createConnection().getOAuthOperations().buildAuthenticateUrl(params);
	}

	@Override
	public void generateFaceBookAccessToken(String code) {
		accessToken=createConnection().getOAuthOperations().exchangeForAccess(code,"http://localhost:4041/facebook",null).getAccessToken();
		Long accessTokenLife=createConnection().getOAuthOperations().exchangeForAccess(code,"http://localhost:4041/facebook",null).getExpireTime();
		System.out.println("Access token life::"+accessTokenLife);
		System.out.println("Access Token::"+accessToken);

	}

	@Override
	public String getLongLivedToken(){
		Scanner sc=null;
		String inline="";
		String baseUrl="https://graph.facebook.com/oauth/access_token?client_id="+facebookAppID+"&client_secret="+facebookSecret+"&grant_type=fb_exchange_token&fb_exchange_token="+accessToken;
		URL url=null;
		JSONParser parser=null;
		JSONObject obj=null;
		String accessToken=null;
		String tokenType=null;
		long expiryTime=0;
		try {
			url = new URL(baseUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
			conn.connect(); 
			int responsecode = conn.getResponseCode(); 
			if(responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " +responsecode);
			sc = new Scanner(url.openStream());
			while(sc.hasNext())
			{
				inline+=sc.nextLine();
			}
			parser=new JSONParser();
			obj=(JSONObject)parser.parse(inline);
			accessToken=(String)obj.get("access_token");
			tokenType=(String)obj.get("token_type");
			expiryTime=(long)obj.get("expires_in");
			System.out.println(accessToken+"  "+tokenType+"  "+expiryTime);

		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(sc!=null)
					sc.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return inline;
	}

	/*@Override
	public String getUserData() {
		Facebook facebook=new FacebookTemplate(accessToken);
		String[] fields= {"id","first_name","name","email","birthday","gender","age_range","hometown","inspirational_people"};
		return facebook.fetchObject("me", String.class, fields);
	}*/

	@Override
	public Account getUserAccount(String accountId) {
		return new FacebookTemplate(accessToken).pageOperations().getAccount(accountId);
	}

	@Override
	public String saveUserData() {
		String id=null;
		String name=null,slToken=null,llToken=null;
		long expiryTime=0;
		String longToken=null;
		Facebook facebook=null;
		JSONParser parser=null;
		JSONObject obj=null;
		FbConnections fbconn=null;
		try {
			facebook=new FacebookTemplate(accessToken);
			String[] fields= {"id","first_name","name"};
			User user=facebook.fetchObject("me", User.class, fields);
			id=user.getId();
			name=user.getName();
			slToken=accessToken;
			
			//getting long lived token
			longToken=getLongLivedToken();
			
			parser=new JSONParser();
			obj=(JSONObject)parser.parse(longToken);
			llToken=(String)obj.get("access_token");
			expiryTime=(long)obj.get("expires_in");
			
			//System.out.println(id+"\n"+name+"\n"+slToken+"\n"+llToken+"\n"+expiryTime);
			//save data
			fbconn=new FbConnections();
			fbconn.setUserId(id);
			fbconn.setUserName(name);
			fbconn.setShortLivedToken(slToken);
			fbconn.setLongLivedToken(llToken);
			fbconn.setExpiryTime(expiryTime);
			
			//call save method
			fbconn=fbRepo.saveAndFlush(fbconn);
			
		}catch (Exception e) {
			e.printStackTrace();
		}

		return (fbconn==null)?"User not registered":"User registered with ID::"+fbconn.getUserId();
		
	}

}