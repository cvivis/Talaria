package com.hermes.monitoring.lsm.job.dashboard;

import com.hermes.monitoring.lsm.dto.LogDto;
import com.hermes.monitoring.lsm.dto.dashboard.ServerFailTimeDto;
import com.hermes.monitoring.lsm.parser.LogParser;
import com.hermes.monitoring.global.WebSocketService;
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
public class ServerFailTimeCheckConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final WebSocketService webSocketService;
    private final LogParser logParser = new LogParser();
    private static final long EXTRACT_TIME = 5000;

    private List<LogDto> logDtoList;

    @Value("${log.access.shared.serverfail}")
    String logUrl;

    private int serverFailTime;

    @Bean
    public Job checkServerFailTime(){
        System.out.println("500 응답 평균 시간 측정 Job");
        // Step 1 : 로그 파일을 LogDtoList로 변환하여 읽는다.
        // Step 2 : LogDtoList의 개수를 한다.
        // Step 3 : 웹 소켓으로 ServerFailTime 전송한다.
        return jobBuilderFactory.get("failTimeCheck")
                .incrementer(new RunIdIncrementer())
                .start(serverFailLogFileReaderStep())
                .next(countServerFailTimeStep())
                .next(sendServerFailTimeStep())
                .build();
    }


    // Step 1 : 로그 파일을 LogDtoList로 변환하여 읽는다.
    @Bean
    public Step serverFailLogFileReaderStep(){
        return stepBuilderFactory.get("serverFailLogFileReader")
                .tasklet((contribution, chunkContext) -> {
                    // 파일을 읽어 logDtoList로 변환
                    logDtoList = logParser.parseLog(logUrl, EXTRACT_TIME);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // Step 2 : LogDtoList의 개수를 한다.
    @Bean
    public Step countServerFailTimeStep(){
        return stepBuilderFactory.get("countServerFailTime")
                .tasklet((contribution, chunkContext) -> {
                    serverFailTime = logDtoList.size();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // Step 3 : 웹 소켓으로 ServerFailTime 전송한다.
    @Bean
    public Step sendServerFailTimeStep(){
        return stepBuilderFactory.get("sendServerFailTime")
                .tasklet((contribution, chunkContext) -> {
                    webSocketService.sendMessageToClient("/sub/server-fail-time-check", new ServerFailTimeDto(new Date(),serverFailTime));
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
