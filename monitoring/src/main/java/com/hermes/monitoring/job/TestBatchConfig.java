package com.hermes.monitoring.job;

import com.hermes.monitoring.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Configuration
//@EnableBatchProcessing
@RequiredArgsConstructor
public class TestBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final WebSocketService webSocketService;
    @Bean
    public Job ExampleJob(){

        Job exampleJob = jobBuilderFactory.get("exampleJob1")
                .incrementer(new RunIdIncrementer())
                .start(Step())
                .build();

        return exampleJob;
    }

    @Bean
    public Step Step() {
        return stepBuilderFactory.get("step")
                .tasklet((contribution, chunkContext) -> {
                    // log.info("Step!");
                    try (FileInputStream fis = new FileInputStream("C:/Users/SSAFY/Desktop/Project/BatchMonitor/springBatchTest/log.txt");
                         BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            // 파일에서 한 줄씩 읽음
                            // System.out.println(line);
                            webSocketService.sendMessageToClient("/sub/log",line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


}