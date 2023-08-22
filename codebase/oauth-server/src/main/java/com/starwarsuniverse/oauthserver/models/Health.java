package com.starwarsuniverse.oauthserver.models;

import lombok.Builder;
import lombok.Data;

@Builder(builderMethodName = "of")
@Data
public class Health {

	private String status;
	
	private long time;
}
