package com.hermes.monitoring.dto;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ApiDailySuccessRequestCountDto {
    private Date date;
    private Integer sum;

    public ApiDailySuccessRequestCountDto(String date, Integer hour, Long sum) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = date + " " + hour + ":00:00";
        this.date = sdf.parse(dateString);
        this.sum = sum.intValue();
    }
}
