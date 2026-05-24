package io.github.guilhermebuenomartins.smarttaskscheduler.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import io.github.guilhermebuenomartins.smarttaskscheduler.config.VariableConfig;
import io.github.guilhermebuenomartins.smarttaskscheduler.dto.TaskInsertionRequestDto;
import io.github.guilhermebuenomartins.smarttaskscheduler.dto.TaskResponseDto;
import io.github.guilhermebuenomartins.smarttaskscheduler.model.Status;
import io.github.guilhermebuenomartins.smarttaskscheduler.model.Task;
import io.github.guilhermebuenomartins.smarttaskscheduler.repository.TaskRepository;

@SpringBootTest
@DisplayName("")
public class TaskServiceTest {
    @Autowired
    private VariableConfig config;
    @Autowired
    private TaskService service;
    @MockitoBean
    private TaskRepository repository;

    /**
     * Test Insert
     * 
     * Test filter of task before saving the data in the database.
     * The request simulates the following test scenarios:
     * <ul>
     *  <li><code>starttime</code>, <code>duration</code> and <code>priority</code> near to lower limit</li>
     *  <li><code>startime</code> lesser than lower limit</li>
     *  <li><code>duration</code> lesser than lower limit</li>
     *  <li><code>priority</code> lesser than lower limit</li>
     *  <li><code>starttime</code>, <code>duration</code> and <code>priority</code> near to upper limit</li>
     * </ul>
     * Upper limits was removed and, therefore, not tested to satisfied the business rules.
     */
    @Test
    @DisplayName("")
    @SuppressWarnings("null")
    public void testInsert() {
        // Create mocks
        List<Task> mockedInputTasks = List.of(
            new Task(
                null, config.getStartTimeLowerLimit(), Status.NEW, config.getDurationLowerLimit(), config.getPriorityLowerLimit()),
            new Task(
                null, config.getStartTimeUpperLimit(), Status.NEW, config.getDurationUpperLimit(), config.getPriorityUpperLimit())
            );
        List<Task> mockedOuputTasks = List.of(
            new Task(
                1, config.getStartTimeLowerLimit(), Status.NEW, config.getDurationLowerLimit(), config.getPriorityLowerLimit()),
            new Task(
                2, config.getStartTimeUpperLimit(), Status.NEW, config.getDurationUpperLimit(), config.getPriorityUpperLimit()));
        Mockito.when(repository.saveAll(mockedInputTasks)).thenReturn(mockedOuputTasks);
        // Executing Test
        List<TaskInsertionRequestDto> insertionRequestDtos = List.of(
            new TaskInsertionRequestDto(
                config.getStartTimeLowerLimit(), config.getDurationLowerLimit(), config.getPriorityLowerLimit()),
            new TaskInsertionRequestDto(
                config.getStartTimeLowerLimit() - 1, config.getDurationLowerLimit(), config.getPriorityLowerLimit()),
            new TaskInsertionRequestDto(
                config.getStartTimeLowerLimit(), config.getDurationLowerLimit() - 1, config.getPriorityLowerLimit()),
            new TaskInsertionRequestDto(
                config.getStartTimeLowerLimit(), config.getDurationLowerLimit(), config.getPriorityLowerLimit() - 1),
            new TaskInsertionRequestDto(
                config.getStartTimeUpperLimit(), config.getDurationUpperLimit(), config.getPriorityUpperLimit())
        );
        List<TaskResponseDto> expectedResponseDtos = List.of(
            new TaskResponseDto(
                1, config.getStartTimeLowerLimit(), Status.NEW, config.getDurationLowerLimit(), config.getPriorityLowerLimit()),
            new TaskResponseDto(
                2, config.getStartTimeUpperLimit(), Status.NEW, config.getDurationUpperLimit(), config.getPriorityUpperLimit())
            );
        List<TaskResponseDto> responseDtos = service.insert(insertionRequestDtos);
        Assertions.assertEquals(expectedResponseDtos, responseDtos);
    }
}
