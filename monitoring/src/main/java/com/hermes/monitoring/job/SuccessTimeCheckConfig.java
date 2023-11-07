package com.hermes.monitoring.job;

import com.hermes.monitoring.dto.LogDto;
import com.hermes.monitoring.dto.SuccessTimeDto;
import com.hermes.monitoring.parser.LogParser;
import com.hermes.monitoring.service.WebSocketService;

import java.util.Date;
import java.util.List;
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

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SuccessTimeCheckConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final WebSocketService webSocketService;
    private final LogParser logParser = new LogParser();
    private static final long EXTRACT_TIME = 5000;

    private List<LogDto> logDtoList;
    private int successTime;

    @Value("${success.log.url}")
    String logUrl;

    // 200 응답 평균 시간을 확인
    @Bean
    public Job successTimeCheckJob(){
        System.out.println("200 응답 평균 시간 측정 Job");
        // Step 1 : 로그 파일을 LogDtoList로 변환하여 읽는다.
        // Step 2 : LogDtoList의 개수를 한다.
        // Step 3 : 웹 소켓으로 SuccessTime 전송한다.
        return jobBuilderFactory.get("successTimeCheck")
                .incrementer(new RunIdIncrementer())
                .start(successLogFileReaderStep())
                .next(countSuccessTimeStep())
                .next(sendSuccessTimeStep())
                .build();
    }

    // Step 1 : 로그 파일을 LogDtoList로 변환하여 읽는다.
    @Bean
    public Step successLogFileReaderStep(){
        return stepBuilderFactory.get("successLogFileReader")
                .tasklet((contribution, chunkContext) -> {
                    // 파일을 읽어 logDtoList로 변환
                    logDtoList = logParser.parseLog(logUrl, EXTRACT_TIME);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // Step 2 : LogDtoList의 개수를 한다.
    @Bean
    public Step countSuccessTimeStep(){
        return stepBuilderFactory.get("countSuccessTime")
                .tasklet((contribution, chunkContext) -> {
                    successTime = logDtoList.size();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // Step 3 : 웹 소켓으로 SuccessTime 전송한다.
    @Bean
    public Step sendSuccessTimeStep(){
        return stepBuilderFactory.get("sendSuccessTime")
                .tasklet((contribution, chunkContext) -> {
                    webSocketService.sendMessageToClient("/sub/log", new SuccessTimeDto(new Date(),successTime));
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
