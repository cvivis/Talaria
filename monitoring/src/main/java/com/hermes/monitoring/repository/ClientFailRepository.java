package com.hermes.monitoring.repository;

import com.hermes.monitoring.dto.api.ApiClientFailHourlyCountDto;
import com.hermes.monitoring.entity.ClientFail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientFailRepository extends JpaRepository<ClientFail,Long> {
    @Query(value = "select new com.hermes.monitoring.dto.api.ApiClientFailHourlyCountDto(c.date,c.hour,c.hourlyCount) " +
            "from ClientFail as c  where c.url = :url and c.method = :method " +
            "group by c.date,c.url,c.hour,c.method,c.hourlyCount order by c.date desc, c.hour desc")
    List<ApiClientFailHourlyCountDto> findHourAndCountByUrlAndMethod(@Param("url")String url, @Param("method")String method);

    @Query(value = "SELECT today.status_code as status_code, today.count as count, round(cast(sum(hourly_count) as numeric) / (to_date(max(today),'YYYY-MM-DD') - cast(to_date(min(date),'YYYY-MM-DD') as date)),1) as avg" +
            " from client_fail as a JOIN " +
            "(select date as today, status_code, sum(hourly_count) as count from client_fail as b where b.url = ?1 and b.method = ?2 and to_date(date,'YYYY-MM-DD') = current_date group by b.status_code, date order by count desc limit 3) as today " +
            "ON a.status_code = today.status_code and a.date != today.today " +
            "where a.url = ?1 and a.method = ?2 " +
            "group by (today.status_code, today.count)" , nativeQuery = true)
    List<ClientFailRepository.ApiClientFailRankingVo> findTodayAndAvgCount( String url, String method);

    interface ApiClientFailRankingVo{
        Integer getStatusCode();
        Long getCount();
        Double getAvg();

    }
}
