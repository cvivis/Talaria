package com.hermes.monitoring.repository;

import com.hermes.monitoring.cvivis.dto.api.ApiDailyRequestCountDto;
import com.hermes.monitoring.entity.RequestCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestCountRepository extends JpaRepository<RequestCount,Long> {
//select date,hour,url,method,sum(hourly_count) from client_fail where url = '/robots.txt' and method = 'GET' group by (url,method,date,hour) order by date desc,hour desc;


    @Query(value = "select new com.hermes.monitoring.dto.api.ApiDailyRequestCountDto(r.date, sum(r.hourlyCount)) " +
            "from RequestCount as r  where r.url = :url and r.method = :method " +
            "group by r.date,r.url order by r.date")
    List<ApiDailyRequestCountDto> findDateAndCountByUrlAndMethod(@Param("url")String url, @Param("method")String method);
}


