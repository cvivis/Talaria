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
@Table(name = "request_group_count", uniqueConstraints = {
        @UniqueConstraint(name = "uk_request_group_count", columnNames = {"date","group_name", "hour","hourly_count","url","method"}),
})
public class RequestGroupCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestGroupCountId;

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
