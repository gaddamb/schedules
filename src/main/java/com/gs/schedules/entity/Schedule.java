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
@Table(
        name="schedule",
        uniqueConstraints = @UniqueConstraint(
                name= "name_unique",
                columnNames = "name"
        )
)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scheduleId;

    @Column(
            name="name",
            nullable = false
    )
    private String name;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name="zone_id",
            referencedColumnName = "zoneId"
    )
    private Zone zone;

    /*@OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "schedule_id",
            referencedColumnName = "scheduleId"
    )
    private List<ScheduleItem> scheduleItemList = new ArrayList<>(); */
}
