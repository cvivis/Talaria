package com.hermes.monitoring.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorCountTypeDto {
    private String errorType;
    private Integer count;
    private List<ErrorCountDetailDto> detailInfo;
}
