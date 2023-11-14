package com.hermes.talaria.domain.apis.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.hermes.talaria.domain.apis.dto.ProductResponse;
import com.hermes.talaria.global.error.exception.ApisException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.talaria.domain.apis.constant.ApisStatus;
import com.hermes.talaria.domain.apis.dto.ApisDto;
import com.hermes.talaria.domain.apis.dto.ApisSubDto;
import com.hermes.talaria.domain.apis.entity.Apis;
import com.hermes.talaria.domain.apis.repository.ApisRepository;
import com.hermes.talaria.domain.member.entity.Member;
import com.hermes.talaria.domain.member.repository.MemberRepository;
import com.hermes.talaria.domain.subscription.constant.SubscriptionStatus;
import com.hermes.talaria.domain.subscription.entity.Subscription;
import com.hermes.talaria.domain.subscription.repository.SubscriptionRepository;
import com.hermes.talaria.global.error.ErrorCode;
import com.hermes.talaria.global.error.exception.AuthenticationException;
import com.hermes.talaria.global.error.exception.BusinessException;
import com.hermes.talaria.global.util.ModelMapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ApisService {

	private final ApisRepository apisRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final MemberRepository memberRepository;

	public Long create(ApisDto apisDto) {
		Apis apis = apisRepository.save(ModelMapperUtil.getModelMapper().map(apisDto, Apis.class));

		return apis.getApisId();
	}

	public List<ApisDto> findApisByDeveloperId(Long memberId) {
		List<Apis> apisList = apisRepository.findApisByDeveloperId(memberId);

		return apisList.stream()
			.map(apis -> ModelMapperUtil.getModelMapper().map(apis, ApisDto.class))
			.collect(Collectors.toList());
	}

	public Long update(ApisDto apisDto) {
		Apis apis = apisRepository.findApisByApisId(apisDto.getApisId()).orElseThrow(() -> new BusinessException(
			ErrorCode.NOT_EXIST_APIS));

		if (!apis.getDeveloperId().equals(apisDto.getDeveloperId())) {
			throw new BusinessException(ErrorCode.WRONG_AUTHORITY);
		}

		apis.updateName(apisDto.getName());
		apis.updateWebServerUrl(apis.getWebServerUrl());
		apis.updateStatus(apis.getStatus());

		return apis.getApisId();
	}

	public Long delete(Long memberId, Long apisId) {
		Apis apis = apisRepository.findApisByApisId(apisId).orElseThrow(() -> new BusinessException(
			ErrorCode.NOT_EXIST_APIS));

		if (!apis.getDeveloperId().equals(memberId)) {
			throw new BusinessException(ErrorCode.WRONG_AUTHORITY);
		}

		apisRepository.deleteApisByApisId(apisId);

		return apisId;
	}

	public Long deleteByAdmin(Long apisId) {
		Apis apis = apisRepository.findApisByApisId(apisId).orElseThrow(() -> new BusinessException(
			ErrorCode.NOT_EXIST_APIS));

		apisRepository.deleteApisByApisId(apis.getApisId());

		return apisId;
	}

	public Long registerOas(ApisDto apisDto) {
		Apis apis = apisRepository.findApisByApisId(apisDto.getApisId()).orElseThrow(() -> new BusinessException(
			ErrorCode.NOT_EXIST_APIS));

		if (!apis.getDeveloperId().equals(apisDto.getDeveloperId())) {
			throw new BusinessException(ErrorCode.WRONG_AUTHORITY);
		}

		apis.registerOas(apisDto);

		return apis.getApisId();
	}

	public ApisDto findApisByApisId(Long memberId, Long apisId) {
		Apis apis = apisRepository.findApisByApisId(apisId).orElseThrow(() -> new BusinessException(
			ErrorCode.NOT_EXIST_APIS));

		if (!apis.getDeveloperId().equals(memberId)) {
			throw new BusinessException(ErrorCode.WRONG_AUTHORITY);
		}

		return ModelMapperUtil.getModelMapper().map(apis, ApisDto.class);
	}

	public List<ApisDto> getApisByStatus(List<ApisStatus> status) {
		List<Apis> apis = new ArrayList<>();

		for (ApisStatus stat : status) {
			apis.addAll(apisRepository.findApisByStatus(stat));
		}

		apis.sort(Comparator.comparingLong(Apis::getApisId));

		List<ApisDto> apisDtoList = apis.stream().map((a) -> {
			Member member = memberRepository.findByMemberId(a.getDeveloperId())
				.orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_MEMBER));
			ApisDto apisDto = ModelMapperUtil.getModelMapper().map(a, ApisDto.class);
			apisDto.setDeveloperEmail(member.getEmail());
			return apisDto;
		}).collect(Collectors.toList());

		return apisDtoList;
	}

	public void updateApisManagement(ApisDto apisDto) {
		Apis apis = apisRepository.findApisByApisId(apisDto.getApisId()).orElseThrow(() -> new BusinessException(
			ErrorCode.NOT_EXIST_APIS));

		if (apisDto.getQuota() != null) {
			apis.updateQuota(apisDto.getQuota());
		}

		if (apisDto.getStatus() != null) {
			apis.updateStatus(apisDto.getStatus());
		}

		if (apisDto.getWhiteList() != null) {
			apis.updateWhiteList(apisDto.getWhiteList());
		}
	}

	public List<ApisSubDto> findApisSubsByStatus(Long memberId, String statusStr) {
		List<Subscription> subscriptions = new ArrayList<>();

		if (statusStr.equals("all")) {
			for (SubscriptionStatus status : SubscriptionStatus.values()) {
				subscriptions.addAll(subscriptionRepository.findByMemberIdAndStatus(memberId, status));
			}
		} else {
			SubscriptionStatus status;
			try {
				status = SubscriptionStatus.valueOf(statusStr);
			} catch (IllegalArgumentException e) {
				throw new BusinessException(ErrorCode.NOT_EXIST_SUBSCRIPTION_STATUS);
			}
			subscriptions = subscriptionRepository.findByMemberIdAndStatus(memberId, status);
		}

		List<Apis> apisList = new ArrayList<>();
		for (Subscription subscription : subscriptions) {
			apisList.add(apisRepository.findApisByApisId(subscription.getApisId())
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_APIS)));
		}

		List<ApisSubDto> apisSubDtos = new ArrayList<>();
		for (int i = 0; i < subscriptions.size(); i++) {
			apisSubDtos.add(ApisSubDto.fromSubscriptionAndApis(subscriptions.get(i), apisList.get(i)));
		}

		return apisSubDtos;
	}

	public ProductResponse findApisByApisName(String apisName) {
		 Apis apis = apisRepository.findApisByName(apisName)
				 .orElseThrow(() -> new ApisException(ErrorCode.NOT_EXIST_APIS));
		 return ModelMapperUtil.getModelMapper().map(apis, ProductResponse.class);
	}
}
