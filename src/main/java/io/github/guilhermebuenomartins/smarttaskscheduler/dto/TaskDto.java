package io.github.guilhermebuenomartins.smarttaskscheduler.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Task DTO")
public class TaskDto {
    @Schema(requiredMode = RequiredMode.REQUIRED, example = "1")
    protected Integer startTime;
    @Schema(requiredMode = RequiredMode.REQUIRED, example = "1")
    protected Integer duration;
    @Schema(requiredMode = RequiredMode.REQUIRED, example = "0")
    protected Integer priority;
}
