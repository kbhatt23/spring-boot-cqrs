package com.learning.cqrs.controllers;

import java.util.Optional;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/replay")
public class ReplayProductEventsController {
	
	@Autowired
	private EventProcessingConfiguration eventProcessingConfiguration;

	@GetMapping
	public ResponseEntity<String> replayProductEvents(){
		String processorName = "product-service-group";
		
		Optional<TrackingEventProcessor> eventProcessor = eventProcessingConfiguration.eventProcessorByProcessingGroup(processorName, TrackingEventProcessor.class);
		
		if(eventProcessor.isPresent()) {
			TrackingEventProcessor trackingEventProcessor = eventProcessor.get();
			trackingEventProcessor.shutDown();
			trackingEventProcessor.resetTokens();
			trackingEventProcessor.start();
			
			return new ResponseEntity<String>("products replay sucesful", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("products replay failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
