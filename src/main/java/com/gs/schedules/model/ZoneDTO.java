package com.gs.schedules.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ZoneDTO {

    @JsonProperty("zoneId")
    private String zoneId;

    @JsonProperty("scheduleName")
    private String scheduleName;

    @JsonProperty("date")
    private String date;
}
