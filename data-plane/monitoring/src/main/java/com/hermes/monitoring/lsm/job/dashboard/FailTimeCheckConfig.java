package com.hermes.monitoring.lsm.job.dashboard;

import com.hermes.monitoring.lsm.dto.dashboard.FailTimeDto;
import com.hermes.monitoring.lsm.dto.LogDto;
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
public class FailTimeCheckConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final WebSocketService webSocketService;
    private final LogParser logParser = new LogParser();
    private static final long EXTRACT_TIME = 5000;

    private List<LogDto> logDtoList;
    private int failTime;

    @Value("${log.access.shared.fail}")
    String logUrl;

    // 4xx 응답 평균 시간을 확인
    @Bean
    public Job failTimeCheckJob(){
        System.out.println("4xx 응답 평균 시간 측정 Job");
        // Step 1 : 로그 파일을 LogDtoList로 변환하여 읽는다.
        // Step 2 : LogDtoList의 개수를 한다.
        // Step 3 : 웹 소켓으로 SuccessTime 전송한다.
        return jobBuilderFactory.get("failTimeCheck")
                .incrementer(new RunIdIncrementer())
                .start(failLogFileReaderStep())
                .next(countFailTimeStep())
                .next(sendFailTimeStep())
                .build();
    }

    // Step 1 : 로그 파일을 LogDtoList로 변환하여 읽는다.
    @Bean
    public Step failLogFileReaderStep(){
        return stepBuilderFactory.get("failLogFileReader")
                .tasklet((contribution, chunkContext) -> {
                    // 파일을 읽어 logDtoList로 변환
                    logDtoList = logParser.parseLog(logUrl, EXTRACT_TIME);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // Step 2 : LogDtoList의 개수를 한다.
    @Bean
    public Step countFailTimeStep(){
        return stepBuilderFactory.get("countFailTime")
                .tasklet((contribution, chunkContext) -> {
                    failTime = logDtoList.size();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // Step 3 : 웹 소켓으로 FailTime 전송한다.
    @Bean
    public Step sendFailTimeStep(){
        return stepBuilderFactory.get("sendFailTime")
                .tasklet((contribution, chunkContext) -> {
                    webSocketService.sendMessageToClient("/sub/fail-time-check", new FailTimeDto(new Date(),failTime));
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
