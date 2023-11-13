package com.hermes.monitoring.repository;

import com.hermes.monitoring.cvivis.dto.api.ApiDailyRequestCountDto;
import com.hermes.monitoring.entity.RequestGroupCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestGroupCountRepository extends JpaRepository<RequestGroupCount,Long> {
//select date,hour,url,method,sum(hourly_count) from client_fail where url = '/robots.txt' and method = 'GET' group by (url,method,date,hour) order by date desc,hour desc;


    @Query(value = "select new com.hermes.monitoring.cvivis.dto.api.ApiDailyRequestCountDto(r.date, sum(r.hourlyCount)) " +
            "from RequestGroupCount as r  where r.groupName = :routing " +
            "group by r.date,r.groupName order by r.date asc")
    List<ApiDailyRequestCountDto> findDateAndCountByUrlAndMethod(@Param("routing")String routing);
}


