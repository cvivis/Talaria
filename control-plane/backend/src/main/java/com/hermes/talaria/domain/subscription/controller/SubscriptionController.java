package com.hermes.talaria.domain.subscription.controller;

import java.util.ArrayList;
import java.util.List;

import com.hermes.talaria.domain.subscription.constant.SubscriptionStatus;
import com.hermes.talaria.global.memberinfo.MemberInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hermes.talaria.domain.subscription.dto.ApplySubscriptionRequest;
import com.hermes.talaria.domain.subscription.dto.ManageSubscriptionRequest;
import com.hermes.talaria.domain.subscription.dto.ManageSubscriptionResponse;
import com.hermes.talaria.domain.subscription.dto.SubscriptionDto;
import com.hermes.talaria.domain.subscription.service.SubscriptionService;
import com.hermes.talaria.global.util.ModelMapperUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

	private final SubscriptionService subscriptionService;

	@PostMapping("/user/apply")
	public ResponseEntity<Void> applySubscription(@MemberInfo Long memberId, @MemberInfo Long keyId, @RequestBody ApplySubscriptionRequest request) {
		request.setMemberId(memberId);
		request.setKeyId(keyId);
		SubscriptionDto subscriptionDto = ModelMapperUtil.getModelMapper().map(request, SubscriptionDto.class);
		subscriptionService.applySubscription(subscriptionDto);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/admin")
	public ResponseEntity<List<ManageSubscriptionResponse>> getSubscriptionByStatus(
		@RequestParam String status) {
		// SubscriptionDto subscriptionDto = ModelMapperUtil.getModelMapper().map(request, SubscriptionDto.class);
		// subscriptionService.updateSubscriptionStatus(subscriptionDto);
		List<ManageSubscriptionResponse> response = new ArrayList<>();
		if ("pending".equals(status))
			response.addAll(subscriptionService.getSubscriptionsByStatusIsPending());

		return ResponseEntity.ok().body(response);
	}

	@PatchMapping("/admin")
	public ResponseEntity<Void> manageSubscription(@RequestBody ManageSubscriptionRequest request) {
		SubscriptionDto subscriptionDto = ModelMapperUtil.getModelMapper().map(request, SubscriptionDto.class);
		subscriptionService.updateSubscriptionStatus(subscriptionDto);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/user")
	public ResponseEntity<SubscriptionDto> getSubscription(@MemberInfo Long memberId, @RequestParam Long apisId) {
		SubscriptionDto response = subscriptionService.getSubscription(memberId, apisId);

		return ResponseEntity.ok().body(response);
	}
}
