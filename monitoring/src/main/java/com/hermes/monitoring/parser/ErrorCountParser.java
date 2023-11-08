package com.hermes.monitoring.parser;

import com.hermes.monitoring.dto.Enum.Error;
import com.hermes.monitoring.dto.ErrorCountDetailDto;
import com.hermes.monitoring.dto.ErrorCountTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ErrorCountParser {

    public GetTime getTime = new GetTime();
    public List<ErrorCountTypeDto> parseLog(String path) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        List<ErrorCountTypeDto> result = new ArrayList<>();
        Map<String,List<ErrorCountDetailDto>> map = new HashMap<>();
        map.put(Error.ERROR.toString(),new ArrayList<>());
        map.put(Error.CRIT.toString(),new ArrayList<>());
        map.put(Error.EMERG.toString(),new ArrayList<>());
        map.put(Error.WARN.toString(),new ArrayList<>());
        try{
            String line;
            while ((line = br.readLine()) != null) {
                ErrorCountDetailDto errorCountDetail = parseLogEntry(line);
                if(errorCountDetail != null){
                   map.get(errorCountDetail.getErrorLevel()).add(errorCountDetail);
                }
            }
//            log.info("map 확인 : {}",map);
        }catch (IOException e){
            e.printStackTrace();
            br.close();
        }
        br.close();
        List<String> keySet = new ArrayList<>(map.keySet());
        keySet.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (s1.equals(s2)) {
                    return 0;
                } else if (s1.equals("WARN")) {
                    return 1;
                } else if (s1.equals("CRIT")) {
                    return s2.equals("WARN") ? -1 : 1;
                } else if (s1.equals("EMERG")) {
                    return s2.equals("ERROR") ? 1 : -1;
                } else {
                    return -1;
                }
            }
        });
        for(String key : keySet){
            ErrorCountTypeDto errorCountTypeDto = ErrorCountTypeDto.builder()
                    .errorType(key)
                    .count(map.get(key).size())
                    .detailInfo(map.get(key))
                    .build();
            result.add(errorCountTypeDto);
        }
        log.info("errorKeySet : {}",keySet);


        return result;
    }

    public ErrorCountDetailDto parseLogEntry(String line) throws ParseException {
        String regex = "^(\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}) \\[(\\w+)\\] (\\d+#\\d+): \\*\\d+ (.*), client: (.+), server: (.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        String errorLevel = "errorLevel";
        String errorCode = "errorCode";
        String errorMessage = "errorMessage";
        String clientIp = "clientIp";
        String serverName = "serverName";
        Long date = new Date().getTime();

//        log.info("line: {}",line);
        if (matcher.find()) {
            date = getTime.getTime(matcher.group(1));
            errorLevel = matcher.group(2);
            errorLevel = errorLevel.toUpperCase(Locale.ROOT);
//            log.info("errorLevel : {}",errorLevel);
            for(Error e : Error.values()){
                if(errorLevel.equals(e.toString())){
                    errorLevel = e.toString();
//                    log.info("들어옴");
                }
            }
            errorCode = matcher.group(3);
            errorMessage = matcher.group(4);
            clientIp = matcher.group(5);
            serverName = matcher.group(6);
        }

        Date currentTime = new Date();
//        log.info("date {} , now : {}",date , currentTime.getTime());
        if(date <= currentTime.getTime() && date >= currentTime.getTime() - 5000){
            ErrorCountDetailDto errorCountDetailDto = ErrorCountDetailDto.builder()
                    .errorLevel(errorLevel)
                    .errorCode(errorCode)
                    .errorMessage(errorMessage)
                    .clientIp(clientIp)
                    .serverName(serverName)
                    .build();
//            log.info("확인 : "+errorCountDetailDto.getErrorLevel());
            return errorCountDetailDto;
        }
        else{
//            log.info("group: ",matcher);
//            log.info("틀림 : "+line);
            return null;
        }
//        return url+ "_"+method;
    }
}
