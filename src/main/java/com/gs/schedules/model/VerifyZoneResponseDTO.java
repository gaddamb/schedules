package com.gs.schedules.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VerifyZoneResponseDTO {

    @JsonProperty("zoneId")
    private String zoneId;

    @JsonProperty("allowedSchedules")
    private List<String> scheduleNames;

    @JsonProperty("date")
    private String date;

    @JsonProperty("isOffenderAllowedAtZoneOnDate")
    private boolean isOffenderAllowedAtZoneOnDate;

    @JsonProperty("error")
    private ErrorDTO error;
}
