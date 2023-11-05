package com.hermes.monitoring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CreateLogFile {

    @Value("${log.url}")
    String url;
    public void createLogFile() throws IOException {
        String result = "";
        try {
            log.info("----- log 날짜 수정 중 -----");
            BufferedReader br = new BufferedReader(new FileReader(url));
            File temp = new File(url+"_temp"); // File객체 생성
            log.info("new Log file : " + url+"_temp");
            if(!temp.exists()){ // 파일이 존재하지 않으면
                temp.createNewFile(); // 신규생성
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(url+"_temp"));


            while((result = br.readLine()) != null) {
                String regex = "^(\\S+) - - \\[([^\\]]+)\\] \"(\\S+) (\\S+)\\s+HTTP/\\d\\.\\d\" (\\d+) (\\d+) \"([^\"]+)\" \"([^\"]+)\" (\\d+\\.\\d+) (\\d+\\.\\d+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(result);
                if (matcher.find()) {
                    String date = matcher.group(2);
                    Calendar calendar = Calendar.getInstance();
                    Date currentDate = calendar.getTime();

                    // 3초 전 시간 계산
                    calendar.setTime(currentDate);
                    calendar.add(Calendar.SECOND, -3);
                    Date threeSecondsAgo = calendar.getTime();

                    // 원하는 형식으로 날짜 포맷팅
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH.mm.ss Z", Locale.US);
                    String formattedDate = sdf.format(threeSecondsAgo);


                    log.info("date : "+date);
                    log.info("resultbefore: " + result);
                    result = result.replaceAll(Pattern.quote(date),formattedDate);
                    log.info("resultafter: " + result);
                }

                bw.write(result + "\r\n");
                bw.flush();
            }
            bw.close();
            br.close();
            Path file = Paths.get(url);
            Path newFile = Paths.get(url+"_temp");
            Files.move(newFile,file);
            log.info("----- log 날짜 수정 완료 -----");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
