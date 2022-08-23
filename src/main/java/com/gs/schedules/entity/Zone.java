package com.gs.schedules.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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
