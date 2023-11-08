package com.hermes.monitoring.parser;

import com.hermes.monitoring.dto.LogDto;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class LogParser {
    private static final String LOGFILE_REGEX = "^(\\S+) - - \\[([^\\]]+)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d+) (\\d+) \"([^\"]*)\" \"([^\"]*)\" (\\d+\\.\\d+) ([\\d.\\-]+)$";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
    public List<LogDto> parseLog(String filePath) throws IOException, ParseException {
        List<LogDto> logList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogDto logDTO = parseLogEntry(line);
                if (logDTO != null) {
                    logList.add(logDTO);
                }
            }
        }
        return logList;
    }


    private LogDto parseLogEntry(String logEntry) throws ParseException {
        // 이 메서드에서 로그 항목을 파싱하여 LogDTO 객체로 변환합니다.
        Pattern pattern = Pattern.compile(LOGFILE_REGEX);
        Matcher matcher = pattern.matcher(logEntry);
        String ipAddress = "ip";
        Date dateTime = new Date();
        String httpMethod = "httpMethod";
        String path = "경로";
        String httpVersion = "httpVersion";
        String statusCode = "statusCode";
        double requestTime = 0;
        double responseTime = -1;
        if (matcher.find()) {
            ipAddress = matcher.group(1);
            dateTime = sdf.parse(matcher.group(2));
            httpMethod = matcher.group(3);
            path = matcher.group(4);
            httpVersion = matcher.group(5);
            statusCode = matcher.group(6);
            requestTime = Double.parseDouble(matcher.group(10));
            responseTime = Double.parseDouble(matcher.group(11));
        }
        return new LogDto(ipAddress, dateTime, httpMethod, path, httpVersion, statusCode, requestTime, responseTime);
    }

    public List<LogDto> parseLog(String filePath, long extractTime) throws IOException, ParseException {
        List<LogDto> logList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogDto logDTO = parseLogEntry(line, extractTime);
                if (logDTO != null) {
                    logList.add(logDTO);
                }
            }
        }
        return logList;
    }

    private LogDto parseLogEntry(String logEntry, long extractTime) throws ParseException {
        // 이 메서드에서 로그 항목을 파싱하여 LogDTO 객체로 변환합니다.
        Pattern pattern = Pattern.compile(LOGFILE_REGEX);
        Matcher matcher = pattern.matcher(logEntry);
        String ipAddress = "ip";
        Date dateTime = new Date();
        String httpMethod = "httpMethod";
        String path = "경로";
        String httpVersion = "httpVersion";
        String statusCode = "statusCode";
        double requestTime = 0;
        double responseTime = 0;
        if (matcher.find()) {
            ipAddress = matcher.group(1);
            dateTime = sdf.parse(matcher.group(2));
            httpMethod = matcher.group(3);
            path = matcher.group(4);
            httpVersion = matcher.group(5);
            statusCode = matcher.group(6);
            requestTime = Double.parseDouble(matcher.group(10));
            if(!matcher.group(11).equals("-")) responseTime = Double.parseDouble(matcher.group(11));
            Date currentTime = new Date();
            Date externalTime = new Date(currentTime.getTime() - extractTime);
            if(dateTime.equals(currentTime) ||
                    dateTime.before(currentTime) &&
                            dateTime.after(externalTime)){
                if(responseTime != -1){
                    return new LogDto(ipAddress, dateTime, httpMethod, path, httpVersion, statusCode, requestTime, responseTime);
                }else{
                    return new LogDto(ipAddress, dateTime, httpMethod, path, httpVersion, statusCode, requestTime);
                }
            }
        }
        return null;
    }

}
