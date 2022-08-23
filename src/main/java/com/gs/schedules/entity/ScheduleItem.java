package com.gs.schedules.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scheduleItemId;

    private String scheduleItem;

    private String type; // either 'range (Date Range)' or 'series'

    @ManyToOne()
    @JoinColumn(
            name = "schedule_id",
            referencedColumnName = "scheduleId"
    )
    private Schedule schedule;
}
