package com.hermes.talaria.domain.subscription.entity;

import com.hermes.talaria.domain.subscription.constant.Status;
import com.hermes.talaria.domain.subscription.dto.SubscriptionDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Subscription implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    @Column
    @NotNull
    private Long memberId;

    @Column
    @NotNull
    private Long apisId;

    @Column
    private String content;

    @Column
    @CreatedDate
    private LocalDateTime subscriptionTime;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private String address;

    @Builder
    public Subscription(Long subscriptionId, Long memberId, Long apisId, String content, LocalDateTime subscriptionTime, Status status, String address) {
        this.subscriptionId = subscriptionId;
        this.memberId = memberId;
        this.apisId = apisId;
        this.content = content;
        this.subscriptionTime = subscriptionTime;
        this.status = status;
        this.address = address;
    }

    public void update(SubscriptionDto subscriptionDto) {
        this.address = subscriptionDto.getAddress();
        this.content = subscriptionDto.getContent();
        this.status = Status.PENDING;
        this.subscriptionTime = LocalDateTime.now();
    }
}
