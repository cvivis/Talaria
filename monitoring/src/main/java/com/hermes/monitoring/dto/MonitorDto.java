package com.hermes.monitoring.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MonitorDto {

    Date date; // 모니터링 시간
    Integer time; // 요청 횟수

    public MonitorDto(Date date, int time){
        this.date = date;
        this.time = time;
    }

}
