//package com.hermes.monitoring.parser;
//
//import com.hermes.monitoring.dto.LogDto;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
//@Slf4j
//public class LogParser {
//
//    private static final long EXTRACTTIME = 5000; // 현재 시간 5초전
//    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH.mm.ss Z", Locale.US);
//    public List<LogDto> parseLog(String filePath) throws IOException, ParseException {
//        List<LogDto> logList = new ArrayList<>();
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(filePath));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                LogDto logDTO = parseLogEntry(line);
//                if (logDTO != null) {
//                    logList.add(logDTO);
//                }
//            }
//            reader.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//        return logList;
//    }
//
//    private LogDto parseLogEntry(String logEntry) throws ParseException {
//        // 이 메서드에서 로그 항목을 파싱하여 LogDTO 객체로 변환합니다.
//        String regex = "^(\\S+) - - \\[([^\\]]+)\\] \"(\\S+) (\\S+)\\s+HTTP/\\d\\.\\d\" (\\d+) (\\d+) \"([^\"]+)\" \"([^\"]+)\"$";
//        log.info(logEntry);
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(logEntry);
//        String ip = "초기 ip";
//        Date date = new Date();
//        String method = "초기 메소드";
//        String url = "초기 url";
//        String status = "초기 상태";
//        if (matcher.find()) {
//            ip = matcher.group(1);
//            date = sdf.parse(matcher.group(2));
//            method = matcher.group(3);
//            url = matcher.group(4);
//            status = matcher.group(5);
//        }
//        Date currentTime = new Date();
//        Date externalTime = new Date(currentTime.getTime() - EXTRACTTIME);
//        if(date.equals(currentTime) ||
//                date.before(currentTime) &&
//                        date.after(externalTime)){
//            return new LogDto(ip, date, method, url, status);
//        }
//        return null;
//    }
//}
