package com.gs.schedules.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;
}
