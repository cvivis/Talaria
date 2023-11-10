package com.hermes.talaria.domain.apis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.talaria.domain.apis.constant.ApisStatus;
import com.hermes.talaria.domain.apis.dto.ApisDto;
import com.hermes.talaria.domain.apis.dto.ApisSubDto;
import com.hermes.talaria.domain.apis.entity.Apis;
import com.hermes.talaria.domain.apis.repository.ApisRepository;
import com.hermes.talaria.domain.subscription.constant.Status;
import com.hermes.talaria.domain.subscription.entity.Subscription;
import com.hermes.talaria.domain.subscription.repository.SubscriptionRepository;
import com.hermes.talaria.global.error.ErrorCode;
import com.hermes.talaria.global.error.exception.BusinessException;
import com.hermes.talaria.global.util.ModelMapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ApisService {

	private final ApisRepository apisRepository;
	private final SubscriptionRepository subscriptionRepository;

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

		apis.update(apisDto);

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

	public List<ApisDto> findApisByStatus(ApisStatus status) {
		List<Apis> apisList = apisRepository.findApisByStatus(status);

		return apisList.stream()
			.map(apis -> ModelMapperUtil.getModelMapper().map(apis, ApisDto.class))
			.collect(Collectors.toList());
	}

	public List<ApisSubDto> findApisSubsByStatus(Long memberId, String statusStr) {
		List<Subscription> subscriptions = new ArrayList<>();

		if (statusStr.equals("all")) {
			for (Status status : Status.values()) {
				subscriptions.addAll(subscriptionRepository.findByMemberIdAndStatus(memberId, status));
			}
		} else {
			Status status;
			try {
				status = Status.valueOf(statusStr);
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
}
