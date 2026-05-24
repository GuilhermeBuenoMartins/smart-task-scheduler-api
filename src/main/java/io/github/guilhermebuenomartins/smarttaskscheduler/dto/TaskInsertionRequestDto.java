package io.github.guilhermebuenomartins.smarttaskscheduler.dto;

public class TaskInsertionRequestDto extends TaskDto {

    public TaskInsertionRequestDto() {
        super();
    }

    public TaskInsertionRequestDto(Integer startTime, Integer duration, Integer priority) {
        super(startTime, duration, priority);
    }
}
