package com.hermes.talaria.domain.subscription.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.talaria.domain.apis.entity.Apis;
import com.hermes.talaria.domain.apis.repository.ApisRepository;
import com.hermes.talaria.domain.member.entity.Member;
import com.hermes.talaria.domain.member.repository.MemberRepository;
import com.hermes.talaria.domain.subscription.constant.SubscriptionStatus;
import com.hermes.talaria.domain.subscription.dto.ManageSubscriptionResponse;
import com.hermes.talaria.domain.subscription.dto.SubscriptionDto;
import com.hermes.talaria.domain.subscription.entity.Subscription;
import com.hermes.talaria.domain.subscription.repository.SubscriptionRepository;
import com.hermes.talaria.global.error.ErrorCode;
import com.hermes.talaria.global.error.exception.ApisException;
import com.hermes.talaria.global.error.exception.AuthenticationException;
import com.hermes.talaria.global.util.ModelMapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionService {

	private final SubscriptionRepository subscriptionRepository;
	private final ApisRepository apisRepository;
	private final MemberRepository memberRepository;

	public void applySubscription(SubscriptionDto subscriptionDto) {

		Subscription subscription;
		// 기존에 요청한 적이 있다면(subscriptionId가 null이 아닐때)
		if (subscriptionDto.getSubscriptionId() != null) {
			subscription = subscriptionRepository.findById(subscriptionDto.getSubscriptionId())
				.orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_SUBSCRIPTION));
			subscription.update(subscriptionDto);
		} else { // 첫 요청
			subscription = ModelMapperUtil.getModelMapper().map(subscriptionDto, Subscription.class);
			subscriptionRepository.save(subscription);
		}
	}

	public List<ManageSubscriptionResponse> getSubscriptionsByStatusIsPending() {
		List<Subscription> subscriptionList = subscriptionRepository.findByStatus(SubscriptionStatus.PENDING);
		List<SubscriptionDto> subscriptionDtos = subscriptionList.stream()
			.map((s) -> ModelMapperUtil.getModelMapper().map(s, SubscriptionDto.class))
			.collect(
				Collectors.toList());

		List<ManageSubscriptionResponse> responses = subscriptionDtos.stream().map((s) -> {
			// api 정보
			Apis apis = apisRepository.findApisByApisId(s.getApisId())
				.orElseThrow(() -> new ApisException(ErrorCode.NOT_EXIST_APIS));
			Member developer = memberRepository.findByMemberId(apis.getDeveloperId())
				.orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER));
			Member user = memberRepository.findByMemberId(s.getMemberId())
				.orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER));

			return ManageSubscriptionResponse.of(s, apis, developer, user);
			// 신청한 user 정보
		}).collect(Collectors.toList());

		return responses;
	}

	public void updateSubscriptionStatus(SubscriptionDto subscriptionDto) {
		Subscription subscription = subscriptionRepository.findById(subscriptionDto.getSubscriptionId())
			.orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_SUBSCRIPTION));
		subscription.updateStatus(subscriptionDto.getStatus());
	}

}
