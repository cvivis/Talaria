package com.hermes.talaria.domain.subscription.dto;

import com.hermes.talaria.domain.subscription.constant.Status;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubscriptionDto {
    private Long subscriptionId;
    private Long memberId;
    private Long apisId;
    private String content;
    private LocalDateTime subscriptionTime;
    private Status status;
    private String address;

    @Builder
    public SubscriptionDto(Long subscriptionId, Long memberId, Long apisId, String content, LocalDateTime subscriptionTime, Status status, String address) {
        this.subscriptionId = subscriptionId;
        this.memberId = memberId;
        this.apisId = apisId;
        this.content = content;
        this.subscriptionTime = subscriptionTime;
        this.status = status;
        this.address = address;
    }
}
