package com.hermes.monitoring.cvivis.parser;

import com.hermes.monitoring.cvivis.dto.api.ApiRequestDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class ApiRequestCountParser {
    GetTime getTime = new GetTime();
    public Map<String,Integer> parseLog(String path) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Map<String,Integer> map = new HashMap<>();
        List<ApiRequestDetailDto> list = new ArrayList<>();
        try{
            String line;
            while ((line = br.readLine()) != null) {
                list.add(parseLogEntry(line));
            }
//            log.info("map 확인 : {}",map);
        }catch (IOException e){
            e.printStackTrace();
            br.close();
        }
        br.close();
        for(ApiRequestDetailDto item : list) {
                    if(item != null) {
                        Date date = item.getDateTime();
                        String year = getTime.getYear(date);
                        String hour = getTime.getHour(date);
                        String key = item.getPath() + "_" + year + "_" + hour + "_" + item.getHttpMethod() + "_";
                        map.put(key, map.getOrDefault(key, 0) + 1);
                    }
        }
        return map;
    }

        public ApiRequestDetailDto parseLogEntry(String line) throws ParseException {
//            String regex = "^(\\S+) - - \\[([^\\]]+)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d+) (\\d+) \"([^\"]*)\" \"([^\"]*)\" (\\d+\\.\\d+) ([\\d.\\-]+)$";
            String regex = "^(\\S+) - - \\[([^\\]]+)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d+) (\\d+) \"([^\"]*)\" \"([^\"]*)\" (\\d+\\.\\d+) ([\\d.\\-]+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);
            String ip = "ip";
            Date dateTime = new Date();
            String httpMethod = "메서드";
            String path = "path";
            String httpProtocol = "protocol";
            String statusCode = "statusCode";
            String requestTime = "responseTime";

            if (matcher.find()) {
//                log.info("statusCode : {}",matcher.group(6));
                ip = matcher.group(1);
                dateTime = getTime.getDateZone(matcher.group(2));
                httpMethod = matcher.group(3);
                path = matcher.group(4);
                httpProtocol = matcher.group(5);
                statusCode = matcher.group(6);
                return ApiRequestDetailDto.builder()
                        .path(path)
                        .httpMethod(httpMethod)
                        .dateTime(dateTime)
                        .build();
            }
            else{
                log.info("no line: {}",line);
                return null;
            }

        }
}
