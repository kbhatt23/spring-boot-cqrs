package com.starwarsuniverse.oauthserver.models;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {
	@Id
	private String userName;
	
	private String password;
	
	@Embedded
	private List<Role> roles;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
}
