package com.gs.schedules.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offender {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long offenderId;

    private String firstName;
    private String lastName;

}
