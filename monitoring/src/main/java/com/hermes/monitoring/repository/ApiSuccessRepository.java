package com.hermes.monitoring.repository;

import com.hermes.monitoring.entity.Success;
import com.hermes.monitoring.lsm.dto.api.ApiHourlyCountDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiSuccessRepository extends JpaRepository<Success,Integer> {

    @Query(value = "select new com.hermes.monitoring.lsm.dto.api.ApiHourlyCountDto(s.date,s.hour,sum(s.hourlyCount)) " +
            "from Success as s  where s.groupName = :groupName " +
            "group by s.date,s.hour,s.groupName order by s.date asc, s.hour desc")
    List<ApiHourlyCountDto> findDateAndCountByGroupName(@Param("groupName") String groupName);
}
