package io.github.guilhermebuenomartins.smarttaskscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    protected Integer startTime;
    protected Integer duration;
    protected Integer priority;
}
