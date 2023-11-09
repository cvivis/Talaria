package com.hermes.monitoring.repository;

import com.hermes.monitoring.dto.ApiDailySuccessRequestCountDto;
import com.hermes.monitoring.entity.Success;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiSuccessRepository extends JpaRepository<Success,Integer> {

    @Query(
            "select new com.hermes.monitoring.dto.ApiDailySuccessRequestCountDto(s.date, s.hour, sum(s.hourlyCount)) " +
                    "from Success s where s.url = :apiUrl and s.method = :method " +
                    "group by s.date, s.hour, s.url, s.method " +
                    "order by s.date desc"
    )
    List<ApiDailySuccessRequestCountDto> findSuccess(@Param("apiUrl") String apiUrl, @Param("method") String method);
}
