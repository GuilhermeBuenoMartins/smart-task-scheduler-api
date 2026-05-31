package io.github.guilhermebuenomartins.smarttaskscheduler.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tasks Insertion DTO")
public class TaskInsertionDto extends TaskDto {
    
    public TaskInsertionDto() {
        super();
    }

    public TaskInsertionDto(Integer startTime, Integer duration, Integer priority) {
        super(startTime, duration, priority);
    }
}
