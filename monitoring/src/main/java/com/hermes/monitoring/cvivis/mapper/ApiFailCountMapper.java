package com.hermes.monitoring.cvivis.mapper;

import com.hermes.monitoring.lsm.dto.LogDto;
import com.hermes.monitoring.cvivis.parser.GetTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.text.ParseException;

@Slf4j
@AllArgsConstructor
/*
    Date dateTime;
    String httpMethod;
    String path;
    String statusCode;
* */
public class ApiFailCountMapper implements FieldSetMapper<LogDto>  {

    private GetTime getTime;
    @Override
    public LogDto mapFieldSet(FieldSet fieldSet) {
        String date = fieldSet.readString(3).replace("[","") +" "+fieldSet.readString(4).replace("]","");
        LogDto logDto = new LogDto();

        for(int i = 0 ; i < fieldSet.getFieldCount();i++){
            log.info("fieldSet {} {}" ,i ,fieldSet.readString(i));
        }
        logDto.setIp(fieldSet.readString(0));
        try {
            logDto.setDate(getTime.getDateZone(date));
            log.info("getTimeDate : {}",logDto.getDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        logDto.setHttpMethod(fieldSet.readString(5).split(" ")[0]);
        logDto.setUrl(fieldSet.readString(5).split(" ")[1]);
        logDto.setHttpStatusCode(fieldSet.readString(6));
        return logDto;
    }
}
