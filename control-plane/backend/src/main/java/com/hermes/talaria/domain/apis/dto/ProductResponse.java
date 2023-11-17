package com.hermes.talaria.domain.apis.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class ProductResponse {
    private Long apisId;
    private String name;
    private Map<String, Object> swaggerContent;
    private String routingUrl;

    @Builder
    public ProductResponse(Long apisId, String name, Map<String, Object> swaggerContent, String routingUrl) {
        this.apisId = apisId;
        this.name = name;
        this.swaggerContent = swaggerContent;
        this.routingUrl = routingUrl;
    }
}
