package com.starwarsuniverse.oauthserver.models;

import javax.persistence.Embeddable;

import org.springframework.security.core.GrantedAuthority;

@Embeddable
public enum Role implements GrantedAuthority{
	READ_PRIVILEDGE,WRITE_PRIVILEDGE;

	@Override
	public String getAuthority() {
		return name();
	}

}
