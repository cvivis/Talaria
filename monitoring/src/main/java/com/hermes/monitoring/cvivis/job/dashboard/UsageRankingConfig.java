package com.hermes.monitoring.cvivis.job.dashboard;


import com.hermes.monitoring.cvivis.dto.dashboard.UsageRankingDto;
import com.hermes.monitoring.cvivis.dto.dashboard.UsageRankingListDto;
import com.hermes.monitoring.cvivis.parser.UsageRangkingParser;
import com.hermes.monitoring.service.WebSocketService;
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
public class UsageRankingConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final WebSocketService webSocketService;
    private final UsageRangkingParser usaUsageRangkingParser;

    @Value("${log.url}")
    String logUrl;


    @Bean
    public Job UsageRankingJob(){
        log.info("-----UsageRankingJob 시작-----");
        return jobBuilderFactory.get("usageRanking")
                .incrementer(new RunIdIncrementer())
                .start(logParseStep()) // 처리 후 전송
                .build();
    }

    @Bean
    public Step logParseStep(){
        log.info("-----Usage Parsing 시작-----");
        return stepBuilderFactory.get("rankingParser")
                .tasklet((contribution, chunkContext) ->{
                    List<UsageRankingDto> ranking = new ArrayList<>();
                    ranking = usaUsageRangkingParser.parseLog(logUrl);
                    UsageRankingListDto result = UsageRankingListDto.builder()
                            .data(ranking)
                            .build();
                    webSocketService.sendMessageToClient("/sub/usage-ranking",result);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


}
