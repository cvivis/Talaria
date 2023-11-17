package com.hermes.talaria.domain.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hermes.talaria.domain.gateway.dto.GatewayServicesResponse;
import com.hermes.talaria.domain.gateway.service.GatewayService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/gateway")
@AllArgsConstructor
public class GatewayController {

	private final GatewayService gatewayService;

	@GetMapping()
	public ResponseEntity<GatewayServicesResponse> getAllServices() throws JsonProcessingException {
		GatewayServicesResponse response = new GatewayServicesResponse();
		response.setServices(gatewayService.gatewayMethod());
		return ResponseEntity.ok().body(response);
	}
}
