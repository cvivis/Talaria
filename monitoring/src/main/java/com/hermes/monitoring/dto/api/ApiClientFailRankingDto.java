package com.hermes.monitoring.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiClientFailRankingDto {
    private Integer statusCode;
    private Long count;
    private Double avgCount;
}
