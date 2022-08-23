package com.gs.schedules.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Builder
@Getter
@JsonPropertyOrder({"errorCode", "errorDescription"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorDTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String errorDescription;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String errorMessage;
}
