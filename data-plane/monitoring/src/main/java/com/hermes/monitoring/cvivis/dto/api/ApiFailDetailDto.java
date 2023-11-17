package com.hermes.monitoring.cvivis.dto.api;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiFailDetailDto {
    private Date dateTime;
    private String httpMethod;
    private String path ;
    private String statusCode ;
}
