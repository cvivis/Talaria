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
@Table(name = "client_fail_group", uniqueConstraints = {
        @UniqueConstraint(name = "uk_fail_group_count", columnNames = {"date","routing_group" ,"hour","hourly_count","url","method","status_code"}),
})
public class ClientGroupFail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ClientFailGroupId;

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
    @Column(name = "status_code")
    private Integer statusCode;

    @NotNull
    private String method;

    @NotNull
    @Column(name = "routing_group")
    private String routingGroup;

}
