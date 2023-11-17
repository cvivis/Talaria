package com.hermes.monitoring.lsm.dto.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

@Data
public class ApiHourlyCountDto {
    private Date date;
    private Long count;

    public ApiHourlyCountDto(String date, Integer hour, Long count) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = dateFormat.parse(date + " " + hour +":00:00");
        this.date = newDate;
        this.count = count;
    }
}
