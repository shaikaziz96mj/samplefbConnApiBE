package com.social.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;

@Data
@Entity
@Table(name="fbconnections")
public class FbConnections implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "user_id",length = 50)
	@Type(type = "string")
	@GenericGenerator(name = "gen1",strategy = "assigned")
	@GeneratedValue(generator = "gen1")
	private String userId;
	@Column(name = "user_name",length = 100)
	@Type(type = "string")
	private String userName;
	@Column(name = "short_token",length = 250)
	@Type(type = "string")
	private String shortLivedToken;
	@Column(name = "long_token",length = 250)
	@Type(type = "string")
	private String longLivedToken;
	@Column(name = "expires_in",length = 50)
	@Type(type = "long")
	private long expiryTime;
	
}