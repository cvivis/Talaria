package com.hermes.monitoring.job;

import com.hermes.monitoring.dto.ApiSuccessCountDto;
import com.hermes.monitoring.dto.LogDto;
import com.hermes.monitoring.parser.LogParser;
import com.hermes.monitoring.service.ApiSuccessDbInsertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApiSuccessCountConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ApiSuccessDbInsertService apiSuccessDbInsertService;
    private final LogParser logParser = new LogParser();

    private List<LogDto> logDtoList;

    private Map<String, ApiSuccessCountDto> statusUrlCount;

    // 읽어드릴 로그 파일
    @Value("${success.log.url}")
    String logUrl;
    @Bean
    public Job countSuccessCountJob(){
        // STEP 1 : 200 log file을 DtoList로 불러온다.
        // STEP 2 : 200 파일의 http status code 별로 DTO를 분리한다.
        // STEP 3 : DB에 INSERT한다.
        return jobBuilderFactory.get("countSuccessCount")
                .incrementer(new RunIdIncrementer())
                .start(apiSuccessLogFileReaderStep())
                .next(mappingSuccessStatusCodeStep())
                .next(successDbInsertStep())
                .build();
    }


    // STEP 1 : 500 log file을 DtoList로 불러온다.
    @Bean
    public Step apiSuccessLogFileReaderStep(){
        return stepBuilderFactory.get("apiSuccessLogFileReader")
                .tasklet((contribution, chunkContext) -> {
                    // 파일을 읽어 logDtoList로 변환
                    logDtoList = logParser.parseLog(logUrl);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // STEP 2 : 500 파일의 http status code 별로 DTO를 분리한다.
    @Bean
    public Step mappingSuccessStatusCodeStep(){
        return stepBuilderFactory.get("mappingSuccessStatusCode")
                .tasklet((contribution, chunkContext) -> {
                    // http status로 분류
                    statusUrlCount = new LinkedHashMap<>();
                    for(LogDto logDto : logDtoList){
                        String statusCode = logDto.getHttpStatusCode(); // LogDto에서 StatusCode를 가져옵니다.
                        String url = logDto.getUrl();
                        String method = logDto.getHttpMethod();
                        String key = statusCode + " " + url + " " + method;
                        ApiSuccessCountDto dto = statusUrlCount.get(key);

                        // statusCode가 statusCount Map의 키로 존재하는지 확인하고, 존재하면 값을 1 증가시킵니다.
                        if (dto != null) {
                            dto.up();
                        } else {
                            // 객체가 없는 경우, 새로운 객체를 생성하고 count를 1로 초기화합니다.
                            dto = new ApiSuccessCountDto(url, method, statusCode, 1);
                            statusUrlCount.put(key, dto);
                        }
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // STEP 3 : DB에 INSERT한다.
    public Step successDbInsertStep(){
        return stepBuilderFactory.get("successDbInsert")
                .tasklet((contribution, chunkContext) -> {
                    for(String key : statusUrlCount.keySet()){
                        ApiSuccessCountDto apiSuccessCountDto = statusUrlCount.get(key);
                        System.out.println(apiSuccessCountDto.toString());
                        apiSuccessDbInsertService.insert(apiSuccessCountDto);
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
