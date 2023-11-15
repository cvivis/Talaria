package com.hermes.monitoring.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CreateErrorFile {

    @Value("${error.url}")
    String url;

    @Value("${error.baseUrl}")
    String baseUrl;
    public void createErrorFile() throws IOException {
        String result = "";
        BufferedReader br = new BufferedReader(new FileReader(url));
        BufferedWriter bw = null;
        try {
            // log.info("----- log 날짜 수정 중 -----");
//            BufferedReader br = new BufferedReader(new FileReader(url));
            File temp = new File(baseUrl+"_temp.txt"); // File객체 생성
            // log.info("new Log file : " + baseUrl+"_temp.txt");
            if(!temp.exists()){ // 파일이 존재하지 않으면
                temp.createNewFile(); // 신규생성
            }
            bw = new BufferedWriter(new FileWriter(baseUrl+"_temp.txt"));


            while((result = br.readLine()) != null) {
                String regex = "^(\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}) \\[(\\w+)\\] (\\d+#\\d+): \\*\\d+ (.*), client: (.+), server: (.+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(result);
                if (matcher.find()) {
                    String date = matcher.group(1);
                    String errorLevel = matcher.group(2);
                    String replaceLevel = errorLevel;
                    if(errorLevel.equals("crit")) {
                        replaceLevel = "warn";
                    }
                    else if(errorLevel.equals("warn")){
                        replaceLevel = "crit";
                    }
                    Calendar calendar = Calendar.getInstance();
                    Date currentDate = calendar.getTime();
                    // 3초 전 시간 계산
                    calendar.setTime(currentDate);
                    calendar.add(Calendar.SECOND, -3);
                    Date threeSecondsAgo = calendar.getTime();

                    // 원하는 형식으로 날짜 포맷팅
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                    String formattedDate = sdf.format(threeSecondsAgo);


                    result = result.replaceAll(Pattern.quote(date),formattedDate);
                    result = result.replaceAll(errorLevel,replaceLevel);
                }

                bw.write(result + "\r\n");
                bw.flush();
            }
            bw.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            bw.close();
            br.close();
        }
        File file = new File(url);

        if(file.exists()){
            // log.info("{} 삭제",url);
            Files.deleteIfExists(file.toPath());
        }

        Path newFile = Paths.get(baseUrl+"_temp.txt");
        Files.move(newFile,newFile.resolveSibling(url));
        // log.info("----- log 수정 완료 -----");
    }
}
