package com.hermes.monitoring.dto.api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class ApiDailyRequestCountDto {
    private Date date;
    private Long count;

    public ApiDailyRequestCountDto(String date, Long count) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date newDate = dateFormat.parse(date);
        this.date = newDate;
        this.count = count;
    }
}
