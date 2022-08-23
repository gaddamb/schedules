package com.gs.schedules.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OffenderDTO {

    @JsonProperty("zoneId")
    private String zoneId;

    @JsonProperty("offenderId")
    private String offenderId;
}

