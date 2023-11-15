package com.hermes.monitoring.cvivis.job.api;


import com.hermes.monitoring.entity.ClientFail;
import com.hermes.monitoring.cvivis.parser.ApiFailCountParser;
import com.hermes.monitoring.repository.ClientFailRepository;
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

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApiFailCountConfig {
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final ApiFailCountParser apiFailCountParser;
//    private final ClientFailRepository clientFailRepository;
//
//    @Value("${fail.log.url}")
//    String url;
//
//    Map<String, Integer> map = new HashMap<>();
//
//    @Bean
//    public Job apiFailCountJob(){
//
//        Job fileJob = jobBuilderFactory.get("apiFailCount")
//                .incrementer(new RunIdIncrementer())
//                .start(apiFailParseStep()) // 처리 후 전송
//                .next(apiFailCountInsertStep())
//                .build();
//
//        return fileJob;
//    }
//
//    @Bean
//    public Step apiFailParseStep(){
//        // log.info("-----apiFailCountParser 시작-----");
//        return stepBuilderFactory.get("apiFailCountParser")
//                .tasklet((contribution, chunkContext) ->{
//                    map = apiFailCountParser.parseLog(url);
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
//
//    // String key = item.getPath() + "_" + year + "_" + hour + "_" + item.getHttpMethod() + "_" + item.getStatusCode();
//    @Bean
//    @Transactional
//    public Step apiFailCountInsertStep(){
//        return stepBuilderFactory.get("apiFailCountParser")
//                .tasklet((contribution, chunkContext) ->{
//                    List<String> keySet = new ArrayList<>(map.keySet());
//                    for(String info:keySet){
//                        // log.info("info: {}",info);
//                        int count = map.get(info);
//                        String[] key = info.split("_");
//                        String url = key[0];
//                        String year = key[1];
//                        Integer hour = Integer.parseInt(key[2]);
//                        String method = key[3];
//                        Integer statusCode = Integer.parseInt(key[4]);
////                        log.info("{} {} {} {} {} {}",url,year,hour,method,statusCode, count);
//                        ClientFail clientFail = ClientFail.builder()
//                                .url(url)
//                                .date(year)
//                                .hourlyCount(count)
//                                .statusCode(statusCode)
//                                .method(method)
//                                .hour(hour)
//                                .groupName()
//                                .build();
//                        clientFailRepository.save(clientFail);
//                    }
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }

//    @Bean
//    public ItemReader<LogDto> itemReader() {
//        FlatFileItemReader<LogDto> itemReader = new FlatFileItemReader<>();
//        itemReader.setResource(new FileSystemResource(url));
//        DefaultLineMapper<LogDto> lineMapper = new DefaultLineMapper<>();
//        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
//        tokenizer.setDelimiter(" ");
//        lineMapper.setLineTokenizer(tokenizer);
//        lineMapper.setFieldSetMapper(new ApiFailCountMapper(getTime)); /* filedSetMapper */
//        itemReader.setLineMapper(lineMapper);
//        return itemReader;
//    }
//
//    @Bean
//    public ItemWriter<LogDto> itemWriter(){
//        return new ItemWriter<LogDto>() {
//            @Override
//            public void write(List<? extends LogDto> items) throws Exception {
//
//                for(LogDto item : items){
//                    if(item != null){
//                        Date date = item.getDateTime();
//                        String year = getTime.getYear(date);
//                        String hour = getTime.getHour(date);
//                        String key = item.getPath() + "_"+year+ "_"+hour+"_"+item.getHttpMethod()+"_"+item.getStatusCode();
//                        dateCountMap.put(key,dateCountMap.getOrDefault(key,0)+1);
//                    }
//                }
//                log.info("map: {}",dateCountMap);
//
//                List<String> keySet = new ArrayList<>(dateCountMap.keySet());
//                for()
//            }
//        };
//    }

}
