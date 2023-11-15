package com.hermes.monitoring.cvivis.parser;

import com.hermes.monitoring.cvivis.dto.dashboard.UsageRankingDto;
import com.hermes.monitoring.cvivis.parser.GetTime;
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
public class UsageRangkingParser {

    public GetTime getTime = new GetTime();
    public List<UsageRankingDto> parseLog(String path) throws IOException, ParseException {
        Map<String, Integer> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        try{
            String line;
            while ((line = br.readLine()) != null) {
//                log.info("line: "+line);
                String urlMethod = parseLogEntry(line);
                if(urlMethod != null){
                    map.put(urlMethod,map.getOrDefault(urlMethod,0)+1);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            br.close();
        }
        List<String> keySet = new ArrayList<>(map.keySet());
        keySet.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return map.get(o1).compareTo(map.get(o2)) * (-1);
            }
        });
        //log.info("keySetLIst : {}",keySet);

        List<UsageRankingDto> result = new ArrayList<>();
        if(keySet.size() < 5){
            while(keySet.size()<5){
                keySet.add("UseAPI_Nothing");
            }
        }
        int rangking = 0;
        int beforeUsage = -1;
        int sameCount = 1;
        for(int i = 0; i < 5;i++){
            String[] urlMethodArr = keySet.get(i).split("_");
            if(urlMethodArr[1].equals("Nothing")){
                UsageRankingDto usageRankingDto = UsageRankingDto.builder()
                        .url((urlMethodArr[0]))
                        .method(urlMethodArr[1])
                        .usage(0)
                        .ranking(0)
                        .build();
                result.add(usageRankingDto);
                beforeUsage = 0;
            }else{
                int nowUsage = map.get(keySet.get(i));
                if(nowUsage == beforeUsage){
                    sameCount++;
                }
                else{
                    if(sameCount != 0){
                        rangking += sameCount;
                        sameCount = 1;
                    }
                    else{
                        rangking++;
                    }
                }
                UsageRankingDto usageRankingDto = UsageRankingDto.builder()
                        .url((urlMethodArr[0]))
                        .method(urlMethodArr[1])
                        .usage(nowUsage)
                        .ranking(rangking)
                        .build();
                result.add(usageRankingDto);
                beforeUsage = nowUsage;
            }
        }
        br.close();
        return result;
    }

    public String parseLogEntry(String line) throws ParseException {
//        String regex = "^(\\S+) - - \\[([^\\]]+)\\] \"(\\S+) (\\S+)\\s+HTTP/\\d\\.\\d\" (\\d+) (\\d+) \"([^\"]+)\" \"([^\"]+)\" (\\d+\\.\\d+) (\\d+\\.\\d+)$";
        String regex = "^(\\S+) - - \\[([^\\]]+)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d+) (\\d+) \"([^\"]*)\" \"([^\"]*)\" (\\d+\\.\\d+) ([\\d.\\-]+)$";

        /*  \[([^\]]+)\] : []안에 들어간 그룹 뽑기
*   \"([^\"]+) : \이나 " 을 제외한 그룹  "로 시작
*  \"([^\"]+) (\S+) (\S+)" :  method url protocol
*  (\d+) (\d+) : 에러코드 , 뭔 숫자
*  "([^"]*)" : "-" 이걸로 자주 찍힘
*  "([^"]*)"  : client info "아닌 문자가 여러개 올 수 있음
*   (\d+\.\d+) : 숫자 , 문자 그대로 . , 숫자 형태
*   ([\d.\-]+) : 숫자 혹은 점, 혹은 - 중 1개라도 있어야함
* */


        //log.info(line);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        String method = "초기 메소드";
        String url = "초기 url";
        Long date = new Date().getTime();
//        System.out.println("그룹을 찾아ㅏ라라라ㅏㅏㄹ "+matcher.group(0));
        if (matcher.find()) {
            method = matcher.group(3);
            url = matcher.group(4);
//            log.info("url: {}",url);
            date = getTime.getTimeZone(matcher.group(2));
        }
        Date currentTime = new Date();
//        log.info("date {} , now : {}",date , currentTime.getTimeZone());
        if(date <= currentTime.getTime() && date >= currentTime.getTime() - 5000){
            return url+ "_"+method;
        }
        else{
            // log.info("group: ",matcher.group(0));
//            log.info("틀림 : "+line);
            return null;
        }
//        return url+ "_"+method;
    }
}
