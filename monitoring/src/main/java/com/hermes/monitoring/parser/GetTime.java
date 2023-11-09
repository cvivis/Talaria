package com.hermes.monitoring.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
@Slf4j
public class GetTime {
    public Long getTimeZone(String time) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        Date date = dateFormat.parse(time);
        return date.getTime();
    }

    public Long getTime(String time) throws  ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd:HH:mm:ss", Locale.US);

//        Date date = dateFormat.parse("31/Oct/2023:11.13.00");
        Date date = dateFormat.parse(time);
        return date.getTime();
    }

    public Date getDateZone(String time) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        Date date = dateFormat.parse(time);
//        log.info("In Date : {}",date);
//        log.info("In Time: {}",time);
        return date;
    }

    public Long getErrorTime(String time) throws  ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);

//        Date date = dateFormat.parse("31/Oct/2023:11.13.00");
        Date date = dateFormat.parse(time);
        return date.getTime();
    }

    public String getHour(Date date) {
//        log.info("Date: {}",date);
        SimpleDateFormat hourOutputFormat = new SimpleDateFormat("HH");
        String hourStr = hourOutputFormat.format(date);
//        log.info("Hour: {}",hourStr);
        return hourStr;
    }

    public String getYear(Date date) {
        SimpleDateFormat dateOutputFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateStr = dateOutputFormat.format(date);
//        log.info("Year: {}",dateStr);
        return dateStr;
    }
}
