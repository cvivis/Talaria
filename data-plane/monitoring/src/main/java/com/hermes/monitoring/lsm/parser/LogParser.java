package com.hermes.monitoring.lsm.parser;

import com.hermes.monitoring.lsm.dto.LogDto;
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
    private static final String LOGFILE_REGEX_1 = "^(\\S+) - - \\[([^\\]]+)\\] \"(\\S+) (\\/[^\\/]+\\/[^\\/]+)(\\/.+) (\\S+)\" (\\d+) (\\d+) \"([^\"]*)\" \"([^\"]*)\" (\\d+\\.\\d+) ([\\d.\\-]+)$";
    // ip, date, http_method, /developer/groupName, /url, http_status_code, body_bytes_sent, http_referer, http_user_agent request_time, upstream_response_time
    private static final String LOGFILE_REGEX_2 = "^(\\S+) - - \\[([^\\]]+)\\] \"(\\S+) (\\/[^\\/]+\\/[^\\/]+) (\\S+)\" (\\d+) (\\d+) \"([^\"]*)\" \"([^\"]*)\" (\\d+\\.\\d+) ([\\d.\\-]+)$";
    // ip, date, http_method, /developer/groupName, http_status_code, body_bytes_sent, http_referer, http_user_agent request_time, upstream_response_time
    private static final String LOGFILE_REGEX_3 = "^(\\S+) - - \\[([^\\]]+)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d+) (\\d+) \"([^\"]*)\" \"([^\"]*)\" (\\d+\\.\\d+) ([\\d.\\-]+)$";
    // ip, date, http_method, url, http_status_code, body_bytes_sent, http_referer, http_user_agent request_time, upstream_response_time
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

    private LogDto parseLogEntry(String logEntry) throws ParseException {
        // 이 메서드에서 로그 항목을 파싱하여 LogDTO 객체로 변환합니다.
        Pattern pattern1 = Pattern.compile(LOGFILE_REGEX_1);
        Pattern pattern2 = Pattern.compile(LOGFILE_REGEX_2);
        Pattern pattern3 = Pattern.compile(LOGFILE_REGEX_3);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        Matcher matcher1 = pattern1.matcher(logEntry);
        Matcher matcher2 = pattern2.matcher(logEntry);
        Matcher matcher3 = pattern3.matcher(logEntry);
        String ip = "";
        Date date = new Date();
        String httpMethod = "";
        String group = "";
        String url = "";
        String httpVersion = "";
        String httpStatusCode = "";
        double requestTime = -1;
        double responseTime = -1;
        if (matcher1.find()) {
            ip = matcher1.group(1);
            date = sdf.parse(matcher1.group(2));
            httpMethod = matcher1.group(3);
            group = matcher1.group(4);
            url = matcher1.group(5);
            httpVersion = matcher1.group(6);
            httpStatusCode = matcher1.group(7);
            requestTime = Double.parseDouble(matcher1.group(11));
            if(!matcher1.group(12).equals("-")) responseTime = Double.parseDouble(matcher1.group(12));
            LogDto logDto = new LogDto(ip, date, httpMethod, group, url, httpVersion, httpStatusCode, requestTime, responseTime);
            return logDto;
        }
        if (matcher2.find()) {
            ip = matcher2.group(1);
            date = sdf.parse(matcher2.group(2));
            httpMethod = matcher2.group(3);
            group = matcher2.group(4);
            httpVersion = matcher2.group(5);
            httpStatusCode = matcher2.group(6);
            requestTime = Double.parseDouble(matcher2.group(10));
            if(!matcher2.group(11).equals("-"))responseTime = Double.parseDouble(matcher2.group(11));
            LogDto logDto = new LogDto(ip, date, httpMethod, group, url, httpVersion, httpStatusCode, requestTime, responseTime);
            return logDto;
        }
        if (matcher3.find()) {
            ip = matcher3.group(1);
            date = sdf.parse(matcher3.group(2));
            httpMethod = matcher3.group(3);
            url = matcher3.group(4);
            httpVersion = matcher3.group(5);
            httpStatusCode = matcher3.group(6);
            requestTime = Double.parseDouble(matcher3.group(10));
            if(!matcher3.group(11).equals("-"))responseTime = Double.parseDouble(matcher3.group(11));
            LogDto logDto = new LogDto(ip, date, httpMethod, group, url, httpVersion, httpStatusCode, requestTime, responseTime);
            return logDto;
        }
        return null;
    }

    private LogDto parseLogEntry(String logEntry, long extractTime) throws ParseException {
        Pattern pattern1 = Pattern.compile(LOGFILE_REGEX_1);
        Pattern pattern2 = Pattern.compile(LOGFILE_REGEX_2);
        Pattern pattern3 = Pattern.compile(LOGFILE_REGEX_3);
        Matcher matcher3 = pattern3.matcher(logEntry);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        Matcher matcher1 = pattern1.matcher(logEntry);
        Matcher matcher2 = pattern2.matcher(logEntry);
        String ip = "";
        Date date = new Date();
        String httpMethod = "";
        String group = "";
        String url = "";
        String httpVersion = "";
        String httpStatusCode = "";
        double requestTime = -1;
        double responseTime = -1;
        if (matcher1.find()) {
            ip = matcher1.group(1);
            date = sdf.parse(matcher1.group(2));
            httpMethod = matcher1.group(3);
            group = matcher1.group(4);
            url = matcher1.group(5);
            httpVersion = matcher1.group(6);
            httpStatusCode = matcher1.group(7);
            requestTime = Double.parseDouble(matcher1.group(11));
            if(!matcher1.group(12).equals("-")) responseTime = Double.parseDouble(matcher1.group(12));
            Date currentTime = new Date();
            Date externalTime = new Date(currentTime.getTime() - extractTime);
            if(date.equals(currentTime) ||
                    date.before(currentTime) &&
                            date.after(externalTime)){
                return new LogDto(ip, date, httpMethod, group, url, httpVersion, httpStatusCode, requestTime, responseTime);
            }
        }
        if (matcher2.find()) {
            ip = matcher2.group(1);
            date = sdf.parse(matcher2.group(2));
            httpMethod = matcher2.group(3);
            group = matcher2.group(4);
            httpVersion = matcher2.group(5);
            httpStatusCode = matcher2.group(6);
            requestTime = Double.parseDouble(matcher2.group(10));
            if(!matcher2.group(11).equals("-"))responseTime = Double.parseDouble(matcher2.group(11));
            Date currentTime = new Date();
            Date externalTime = new Date(currentTime.getTime() - extractTime);
            if(date.equals(currentTime) ||
                    date.before(currentTime) &&
                            date.after(externalTime)){
                return new LogDto(ip, date, httpMethod, group, url, httpVersion, httpStatusCode, requestTime, responseTime);
            }
        }
        if (matcher3.find()) {
            ip = matcher3.group(1);
            date = sdf.parse(matcher3.group(2));
            httpMethod = matcher3.group(3);
            url = matcher3.group(4);
            httpVersion = matcher3.group(5);
            httpStatusCode = matcher3.group(6);
            requestTime = Double.parseDouble(matcher3.group(10));
            if(!matcher3.group(11).equals("-"))responseTime = Double.parseDouble(matcher3.group(11));
            Date currentTime = new Date();
            Date externalTime = new Date(currentTime.getTime() - extractTime);
            if(date.equals(currentTime) ||
                    date.before(currentTime) &&
                            date.after(externalTime)){
                return new LogDto(ip, date, httpMethod, group, url, httpVersion, httpStatusCode, requestTime, responseTime);
            }
        }
        return null;
    }

}
