package com.hermes.monitoring.lsm.job.dashboard;


import com.hermes.monitoring.lsm.dto.LogDto;
import com.hermes.monitoring.lsm.dto.dashboard.MonitorDto;
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
public class HttpStatusConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private LogParser logParser = new LogParser();
    private final WebSocketService webSocketService;
    private List<LogDto> logDataList;
    private MonitorDto monitorDto;

    @Value("${log.url}")
    String logUrl;


    @Bean
    public Job HttpStatusJob(){
        System.out.println("시작");
        return jobBuilderFactory.get("httpStatus")
                .incrementer(new RunIdIncrementer())
                .start(flatFileItemReaderStep())
                .next(checkConnectionCountStep())//
                .next(sendLogFileToFrontStep()) // 프론트 단에 보내주기
                .build();
    }

    // 로그 파일을 불러오고 5초동안 요청한 로그 DTO들을 가져오는 스탭
    @Bean
    public Step flatFileItemReaderStep() {
        return stepBuilderFactory.get("flatFileItemReader")
                .tasklet((contribution, chunkContext) ->{
                    logDataList = logParser.parseLog(logUrl);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // 모니터링에 보여줄 모니터링DTO를 변환해주는 스탭
    @Bean
    public Step checkConnectionCountStep() {
        return stepBuilderFactory.get("checkConnection")
                .tasklet((contribution, chunkContext) -> {
                    monitorDto = new MonitorDto(new Date(), logDataList.size());
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // 웹 소켓으로 모니터링 정보를 보내주는 스탭
    @Bean
    public Step sendLogFileToFrontStep() {
        return stepBuilderFactory.get("sendLogFileFront")
                .tasklet((contribution, chunkContext) -> {
                    webSocketService.sendMonitorDtoToClient("/sub/log",monitorDto);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }




}
