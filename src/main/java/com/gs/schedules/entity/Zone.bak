package com.gs.schedules.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long zoneId;

    @OneToOne(
            mappedBy = "zone"
    )
    private Schedule schedule ;

    @ManyToOne
    @JoinColumn(
            name = "offender_id",
            referencedColumnName = "offenderId"
    )
    private Offender offender;

}
