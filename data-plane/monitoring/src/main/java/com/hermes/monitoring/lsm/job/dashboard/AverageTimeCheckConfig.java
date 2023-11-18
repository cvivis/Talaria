package com.hermes.monitoring.lsm.job.dashboard;


import com.hermes.monitoring.lsm.dto.dashboard.AverageResponseTimeDto;
import com.hermes.monitoring.lsm.dto.LogDto;
import com.hermes.monitoring.lsm.parser.LogParser;
import com.hermes.monitoring.global.WebSocketService;
import java.util.ArrayList;
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

import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AverageTimeCheckConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final WebSocketService webSocketService;
    private final LogParser logParser = new LogParser();
    private List<LogDto> logDtoList;
    private double averageResponseTime;

    // 추출하고 싶은 시간 범위
    private static final long EXTRACT_TIME = 5000;

    // 읽어드릴 로그 파일
    @Value("${log.url}")
    String logUrl;

    // 응답 평균 시간을 확인
    @Bean
    public Job checkAverageTime(){
        System.out.println("응답 평균 시간 측정 시작");
        // Step 1 : 로그파일을 LogDtoList로 변환하여 읽어온다.
        // Step 2 : LogDtoList의 평균 응답 시간을 계산하여 AverageResponseTimeDto 반환한다.
        // Step 3 : 웹 소켓으로 AverageResponseTime 전송한다.
        return jobBuilderFactory.get("averageTimeCheck")
                .incrementer(new RunIdIncrementer())
                .start(logFileReaderStep())
                .next(calculateAverageResponseStep())
                .next(sendAverageResponseTimeStep())
                .build();
    }

    // Step 1 : 로그파일을 LogDtoList로 변환하여 읽어온다.
    @Bean
    public Step logFileReaderStep(){
        System.out.println("로그 파일 읽는 스탭");
        return stepBuilderFactory.get("logFileReader")
                .tasklet((contribution, chunkContext) -> {
                    // 파일을 읽어 logDtoList로 변환
                    logDtoList = logParser.parseLog(logUrl, EXTRACT_TIME);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // Step 2 : LogDtoList의 평균 응답 시간을 계산하여 AverageResponseTimeDto 반환한다.
    @Bean
    public Step calculateAverageResponseStep(){
        System.out.println("평균 응답 시간을 계산하는 스탭");
        return stepBuilderFactory.get("calculateAverageResponse")
                .tasklet((contribution, chunkContext) -> {
                    List<LogDto> responseLogDto = new ArrayList<>();
                    averageResponseTime = 0;
                    double sumResponseTime = 0;
                    // 각 logDto를 보면서 평균 시간 계산
                    for(LogDto logDto: logDtoList){
                        if(logDto.getResponseTime() != -1){
                            responseLogDto.add(logDto);
                            sumResponseTime += logDto.getResponseTime();
                        }
                    }
                    if(!responseLogDto.isEmpty()){
                        System.out.println("okay!");
                        averageResponseTime = sumResponseTime/ (double) responseLogDto.size();
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // Step 3 : 웹 소켓으로 AverageResponseTime 전송한다.
    @Bean
    public Step sendAverageResponseTimeStep(){
        System.out.println("평균 응답 시간을 전송하는 스탭");
        return stepBuilderFactory.get("sendAverageResponseTime")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("평균 응답 시간: " + averageResponseTime);
                    webSocketService.sendMessageToClient("/sub/average-time-check",new AverageResponseTimeDto(new Date(),averageResponseTime));
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
