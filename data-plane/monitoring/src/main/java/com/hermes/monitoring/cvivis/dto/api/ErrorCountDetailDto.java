package com.hermes.monitoring.cvivis.dto.api;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorCountDetailDto {
    private String errorLevel ;
    private String errorCode ;
    private String errorMessage;
    private String clientIp ;
    private String serverName ;
}
