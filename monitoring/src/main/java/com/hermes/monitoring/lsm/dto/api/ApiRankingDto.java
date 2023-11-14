package com.hermes.monitoring.lsm.dto.api;

import lombok.Data;

@Data
public class ApiRankingDto {
    private Integer statusCode;
    private Long count;
    private Double avgCount;
}
