package com.hermes.monitoring.dto;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiRequestDetailDto {
    private Date dateTime;
    private String httpMethod;
    private String path ;
}
