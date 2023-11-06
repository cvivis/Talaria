package com.hermes.monitoring.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GetTime {
    public Long getTime(String time) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        Date date = dateFormat.parse(time);
        return date.getTime();
    }
}
