package com.hermes.talaria.domain.subscription.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // 무슨 용도인지 아직 모르겠다
public class ApplySubscriptionRequest {
    Long subscriptionId;
    Long memberId;
    Long apisId;
    String content;
    String address;

    @Builder
    public ApplySubscriptionRequest(Long subscriptionId, Long memberId, Long apisId, String content, String address) {
        this.subscriptionId = subscriptionId;
        this.memberId = memberId;
        this.apisId = apisId;
        this.content = content;
        this.address = address;
    }

}
