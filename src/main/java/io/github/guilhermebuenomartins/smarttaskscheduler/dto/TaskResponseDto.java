package io.github.guilhermebuenomartins.smarttaskscheduler.dto;

import io.github.guilhermebuenomartins.smarttaskscheduler.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Task Response DTO")
public class TaskResponseDto extends TaskDto {
    @Schema(example = "0")
    private Integer id;
    @Schema(examples = {"NEW", "STARTED", "COMPLETE"})
    private Status status;

    public TaskResponseDto() {
        super();
    }

    public TaskResponseDto(Integer id, Integer startTime, Status status, Integer duration, Integer priority) {
        super(startTime, duration, priority);
        this.id = id;
        this.status = status;
    }
}
