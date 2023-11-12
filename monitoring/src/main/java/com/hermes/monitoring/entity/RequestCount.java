package com.hermes.monitoring.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "request_count", uniqueConstraints = {
        @UniqueConstraint(name = "uk_request_count", columnNames = {"date", "hour","hourly_count","url","method","group_name"}),
})
public class RequestCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestCountId;

    @NotNull
    private String date;

    @NotNull
    private Integer hour;

    @NotNull
    @Column(name = "hourly_count")
    private Integer hourlyCount;

    @NotNull
    private String url;

    @NotNull
    private String method;

    @NotNull
    @Column(name = "group_name")
    private String groupName;
}
