package io.github.guilhermebuenomartins.smarttaskscheduler.config;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Configuration
@NoArgsConstructor
public class VariableConfig {
    private final Integer startTimeLowerLimit = 1;
    private final Integer startTimeUpperLimit = Integer.MAX_VALUE;
    private final Integer durationLowerLimit = 1;
    private final Integer durationUpperLimit = Integer.MAX_VALUE;
    private final Integer priorityLowerLimit = 0;
    private final Integer priorityUpperLimit = Integer.MAX_VALUE;
}
