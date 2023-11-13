package com.hermes.monitoring.cvivis.job.dashboard;


import com.hermes.monitoring.cvivis.dto.api.ErrorCountTypeDto;
import com.hermes.monitoring.cvivis.parser.ErrorCountParser;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ErrorCountConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final WebSocketService webSocketService;
    private final ErrorCountParser errorCountParser;

    @Value("${error.url}")
    String errorUrl;


    @Bean
    public Job ErrorCountJob(){
        log.info("-----ErrorCountJob 시작-----");
        return jobBuilderFactory.get("errorCount")
                .incrementer(new RunIdIncrementer())
                .start(errorParseStep()) // 처리 후 전송
                .build();
    }

    @Bean
    public Step errorParseStep(){
        log.info("-----ErrorCount Parsing 시작-----");
        return stepBuilderFactory.get("errorCountParser")
                .tasklet((contribution, chunkContext) ->{
                    List<ErrorCountTypeDto> result = new ArrayList<>();
                    result = errorCountParser.parseLog(errorUrl);
                    webSocketService.sendMessageToClient("/sub/error-count",result);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


}
