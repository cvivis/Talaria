package com.hermes.monitoring.lsm.dto.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiRankingDto {
    private Integer statusCode;
    private Long count;
    private Double avgCount;

    public ApiRankingDto(Integer statusCode, Long count, Double avgCount) {
        this.statusCode = statusCode;
        this.count = count;
        this.avgCount = avgCount;
    }
}
