package com.hermes.monitoring.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiFailCountDto {
    private String date;
    private Integer count;
}
