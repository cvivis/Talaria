package com.hermes.monitoring.parser;


import com.hermes.monitoring.dto.ApiFailDetailDto;
import com.hermes.monitoring.dto.ApiRequestDetailDto;
import com.hermes.monitoring.dto.api.LogGroupDto;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class ApiGroupLogParser {
    GetTime getTime = new GetTime();
    public Map<String,Integer> apiGroupLogParse(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Map<String,Integer> map = new HashMap<>();
        List<LogGroupDto> list = new ArrayList<>();
        try{
            String line;
            while ((line = br.readLine()) != null) {
                list.add(apiGroupLogEntry(line));
            }
//            log.info("map 확인 : {}",map);
        }catch (IOException | ParseException e){
            e.printStackTrace();
            br.close();
        }

        for(LogGroupDto item : list) {
            if(item != null) {
                Date date = item.getDateTime();
                String year = getTime.getYear(date);
                String hour = getTime.getHour(date);
                String key = item.getPath() + "_" + year + "_" + hour + "_" + item.getHttpMethod() + "_";
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
        }
        br.close();
        return map;
    }

    public LogGroupDto apiGroupLogEntry(String line) throws ParseException {
//        String regex = "^(\\S+) - - \\[([^\\]]+)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d+) (\\d+) \"([^\"]*)\" \"([^\"]*)\" (\\d+\\.\\d+) ([\\d.\\-]+)$";
        String regex = "^(\\S+) - - (/\\\\w+/\\\\w+)(/\\\\w+.*)? \"(\\S+) (\\S+) (\\S+)\" (\\d+) (\\d+) \"([^\"]*)\" \"([^\"]*)\" (\\d+\\.\\d+) ([\\d.\\-]+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        String ip = "ip";
        Date dateTime = new Date();
        String httpMethod = "method";
        String path = "path";
        String httpProtocol = "protocol";
        String statusCode = "statusCode";
        String requestTime = "requestTime";
        String responseTime = "responseTime";


        if (matcher.find()) {
//                log.info("statusCode : {}",matcher.group(6));
            ip = matcher.group(1);
            dateTime = getTime.getDateZone(matcher.group(2));
            httpMethod = matcher.group(3);
            path = matcher.group(4);
            httpProtocol = matcher.group(5);
            statusCode = matcher.group(6);
            requestTime = matcher.group(10);
            responseTime = matcher.group(11);
            return LogGroupDto.builder()
                    .ip(ip)
                    .httpProtocol(httpProtocol)
                    .statusCode(statusCode)
                    .dateTime(dateTime)
                    .httpMethod(httpMethod)
                    .path(path)
                    .requestTime(requestTime)
                    .responseTime(responseTime)
                    .build();
        }
        else{
            log.info("no line: {}",line);
            return null;
        }
    }
}
