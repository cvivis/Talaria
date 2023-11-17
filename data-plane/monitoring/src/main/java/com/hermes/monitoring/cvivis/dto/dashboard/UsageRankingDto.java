package com.hermes.monitoring.cvivis.dto.dashboard;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsageRankingDto{
    private String url;
    private String method;
    private Integer usage;
    private Integer ranking;
}
