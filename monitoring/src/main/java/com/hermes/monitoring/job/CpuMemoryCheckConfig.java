package com.hermes.monitoring.job;

import com.hermes.monitoring.dto.CpuMemoryUsageDto;
import com.hermes.monitoring.service.CpuMemoryService;
import com.hermes.monitoring.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CpuMemoryCheckConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final WebSocketService webSocketService;
    private final CpuMemoryService cpuMemoryService;

    private CpuMemoryUsageDto cpuMemoryUsageDto;

    @Bean
    public Job cpuMemoryCheckJob(){
        System.out.println("Cpu Memory 확인 잡 시작");
        // STEP 1 : NGINX 서버로 부터 CPU MEMORY 사용량 확인
        // STEP 2 : 프론트 전송
        return jobBuilderFactory.get("cpuMemoryCheck")
                .incrementer(new RunIdIncrementer())
                .start(getCpuMemoryUsageStep())
                //.next(sendCpuMemoryUsageStep())
                .build();
    }


    // STEP 1 : NGINX 서버로 부터 CPU MEMORY 사용량 확인
    @Bean
    public Step getCpuMemoryUsageStep(){
        return stepBuilderFactory.get("getCpuMemoryUsage")
                .tasklet((contribution, chunkContext) -> {
                    cpuMemoryUsageDto = cpuMemoryService.getCpuMemoryUsage();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


    // STEP 2 : 프론트 전송
    @Bean
    public Step sendCpuMemoryUsageStep() {
        return stepBuilderFactory.get("sendCpuMemoryUsage")
                .tasklet((contribution, chunkContext) -> {
                    webSocketService.sendCpuMemoryUsageToClient("/sub/log",cpuMemoryUsageDto);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


}
