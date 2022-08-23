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
public class ScheduleDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("schedule")
    private List<String> schedule;

    @JsonProperty("error")
    private ErrorDTO error;
}
