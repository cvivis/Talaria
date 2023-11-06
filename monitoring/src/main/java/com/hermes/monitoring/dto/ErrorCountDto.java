package com.hermes.monitoring.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorCountDto {
    private List<ErrorCountTypeDto> data;
}
