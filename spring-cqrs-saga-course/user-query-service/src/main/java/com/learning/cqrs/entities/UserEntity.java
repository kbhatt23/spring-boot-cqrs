package com.learning.cqrs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
	@Id
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "user_name")
	private String name;
	
	@Column(name = "user_balance")
	private double balance;


}
