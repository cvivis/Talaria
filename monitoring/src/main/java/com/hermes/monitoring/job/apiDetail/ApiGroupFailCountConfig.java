package com.hermes.monitoring.job.apiDetail;

import com.hermes.monitoring.entity.ClientGroupFail;
import com.hermes.monitoring.entity.RequestGroupCount;
import com.hermes.monitoring.parser.ApiGroupLogParser;
import com.hermes.monitoring.repository.ClientFailGroupCountRepository;
import com.hermes.monitoring.repository.RequestGroupCountRepository;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApiGroupFailCountConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ApiGroupLogParser apiGroupLogParser;
    private final ClientFailGroupCountRepository clientFailGroupCountRepository;

    @Value("${fail.log.url}")
    String url;

    Map<String, Integer> map = new HashMap<>();

    @Bean
    public Job apiGroupFailCountJob(){

        Job fileJob = jobBuilderFactory.get("apiGroupFailCount")
                .incrementer(new RunIdIncrementer())
                .start(apiGroupFailParseStep()) // 처리 후 전송
                .next(apiGroupFailCountInsertStep())
                .build();

        return fileJob;
    }

    @Bean
    public Step apiGroupFailParseStep(){
        log.info("-----apiGroupFailCountParser 시작-----");
        return stepBuilderFactory.get("apiGroupFailCountParser")
                .tasklet((contribution, chunkContext) ->{
                    map = apiGroupLogParser.apiGroupLogParse(url);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // String key = item.getPath() + "_" + year + "_" + hour + "_" + item.getHttpMethod() + "_" + item.getStatusCode();
    @Bean
    @Transactional
    public Step apiGroupFailCountInsertStep(){
        return stepBuilderFactory.get("apiGroupFailCountParser")
                .tasklet((contribution, chunkContext) ->{
                    List<String> keySet = new ArrayList<>(map.keySet());
                    for(String info:keySet){
                        log.info("info: {}",info);
                        int count = map.get(info);
                        String[] key = info.split("_");
                        String routingUrl = key[0];
                        String url = key[1];
                        String year = key[2];
                        Integer hour = Integer.parseInt(key[3]);
                        String method = key[4];
                        Integer statusCode = Integer.parseInt(key[5]);
//                        log.info("{} {} {} {} {} {}",url,year,hour,method,statusCode, count);
                        ClientGroupFail clientGroupFail = ClientGroupFail.builder()
                                .routingGroup(routingUrl)
                                .hourlyCount(count)
                                .method(method)
                                .date(year)
                                .hour(hour)
                                .url(url)
                                .statusCode(statusCode)
                                .build();
                        clientFailGroupCountRepository.save(clientGroupFail);
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

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
