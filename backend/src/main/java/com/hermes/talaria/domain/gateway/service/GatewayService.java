package com.hermes.talaria.domain.gateway.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hermes.talaria.domain.apis.constant.ApisStatus;
import com.hermes.talaria.domain.apis.entity.Apis;
import com.hermes.talaria.domain.apis.repository.ApisRepository;
import com.hermes.talaria.domain.gateway.dto.GatewayIPResponse;
import com.hermes.talaria.domain.gateway.dto.GatewayInfoResponse;
import com.hermes.talaria.domain.gateway.dto.GatewayKeyResponse;
import com.hermes.talaria.domain.gateway.dto.GatewayPoliciesResponse;
import com.hermes.talaria.domain.gateway.dto.GatewayResponse;
import com.hermes.talaria.domain.key.entity.Key;
import com.hermes.talaria.domain.key.repository.KeyRepository;
import com.hermes.talaria.domain.member.entity.Member;
import com.hermes.talaria.domain.member.repository.MemberRepository;
import com.hermes.talaria.domain.subscription.constant.SubscriptionStatus;
import com.hermes.talaria.domain.subscription.entity.Subscription;
import com.hermes.talaria.domain.subscription.repository.SubscriptionRepository;
import com.hermes.talaria.global.error.ErrorCode;
import com.hermes.talaria.global.error.exception.AuthenticationException;
import com.hermes.talaria.global.error.exception.KeyException;
import com.hermes.talaria.global.util.JsonParserUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatewayService {

	private final ApisRepository apisRepository;
	private final MemberRepository memberRepository;
	private final KeyRepository keyRepository;
	private final SubscriptionRepository subscriptionRepository;

	public List<GatewayResponse> gatewayMethod() throws JsonProcessingException {
		List<Apis> apisList = apisRepository.findApisByStatus(ApisStatus.APPROVED_ON);

		List<GatewayResponse> gatewayResponses = new ArrayList<>();

		for (Apis apis : apisList) {
			if (apis.getSwaggerContent() == null) continue;
			Member developer = memberRepository.findByMemberId(apis.getDeveloperId())
				.orElseThrow(() -> new AuthenticationException(
					ErrorCode.NOT_EXIST_MEMBER));

			GatewayInfoResponse info = new GatewayInfoResponse();
			info.setDeptName(developer.getEmail());
			info.setGroupName(apis.getName());

			GatewayPoliciesResponse policies = new GatewayPoliciesResponse();
			policies.setQuota(apis.getQuota());

			List<GatewayIPResponse> whitelist = new ArrayList<>();

			for (String ip : apis.getWhiteList()) {
				whitelist.add(new GatewayIPResponse(ip));
			}

			policies.setWhitelist(whitelist);

			List<Subscription> subscriptions = subscriptionRepository.findByApisIdAndStatus(apis.getApisId(),
				SubscriptionStatus.SUBSCRIBING);
			List<GatewayKeyResponse> keys = new ArrayList<>();

			for (Subscription subscription : subscriptions) {
				Key key = keyRepository.findByKeyId(subscription.getKeyId())
					.orElseThrow(() -> new KeyException(ErrorCode.NOT_EXIST_KEY));
				Member user = memberRepository.findByMemberId(subscription.getMemberId())
					.orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER));

				keys.add(new GatewayKeyResponse(key.getKeyValue(), user.getEmail()));
			}

			Map<String, Object> oas = JsonParserUtil.parser(apis.getSwaggerContent());

			gatewayResponses.add(new GatewayResponse(info, policies, keys, oas));

		}

		return gatewayResponses;
	}
}
