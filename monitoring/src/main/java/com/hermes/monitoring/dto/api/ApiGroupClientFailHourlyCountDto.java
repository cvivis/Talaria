package com.hermes.monitoring.dto.api;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class ApiGroupClientFailHourlyCountDto {
    private Date date;
    private Integer count;
}
