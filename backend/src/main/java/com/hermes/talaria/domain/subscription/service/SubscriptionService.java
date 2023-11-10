package com.hermes.talaria.domain.subscription.service;

import com.hermes.talaria.domain.subscription.constant.Status;
import com.hermes.talaria.domain.subscription.dto.SubscriptionDto;
import com.hermes.talaria.domain.subscription.entity.Subscription;
import com.hermes.talaria.domain.subscription.repository.SubscriptionRepository;
import com.hermes.talaria.global.error.ErrorCode;
import com.hermes.talaria.global.error.exception.AuthenticationException;
import com.hermes.talaria.global.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public void applySubscription(SubscriptionDto subscriptionDto) {

        Subscription subscription;
        // 기존에 요청한 적이 있다면(subscriptionId가 null이 아닐때)
        if(subscriptionDto.getSubscriptionId() != null) {
            subscription = subscriptionRepository.findById(subscriptionDto.getSubscriptionId())
                    .orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_SUBSCRIPTION));
            subscription.update(subscriptionDto);
        } else { // 첫 요청
            subscription = ModelMapperUtil.getModelMapper().map(subscriptionDto, Subscription.class);
            subscription.setStatus(Status.PENDING);
        }
        subscriptionRepository.save(subscription);

    }


}
