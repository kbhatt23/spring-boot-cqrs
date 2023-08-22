package com.starwarsuniverse.oauthserver.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starwarsuniverse.oauthserver.models.AppConstants;
import com.starwarsuniverse.oauthserver.models.Health;

@RequestMapping("/health")
@RestController
public class HealthController {

	@GetMapping
	public Health healthMetrics() {
		return Health.of().status(AppConstants.HEALTH_STATUS_RUNNING)
				  .time(System.currentTimeMillis())
				  .build();
	}
}
