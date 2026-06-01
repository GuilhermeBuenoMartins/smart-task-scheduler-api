package io.github.guilhermebuenomartins.smarttaskscheduler.controller.impl;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.guilhermebuenomartins.smarttaskscheduler.config.VariableConfig;
import io.github.guilhermebuenomartins.smarttaskscheduler.dto.TaskInsertionDto;
import io.github.guilhermebuenomartins.smarttaskscheduler.dto.TaskResponseDto;
import io.github.guilhermebuenomartins.smarttaskscheduler.model.Status;
import io.github.guilhermebuenomartins.smarttaskscheduler.model.Task;
import io.github.guilhermebuenomartins.smarttaskscheduler.repository.TaskRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerImplTest {
    private static final String TASK_LIST_ENDPOINT = "/api/task/list";
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private VariableConfig config;
    @Autowired
    private MockMvc mockMvc;
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
     * @throws Exception
     */
    @Test
    @SuppressWarnings("null")
    void testInsert() throws Exception {
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
        List<TaskInsertionDto> insertionRequestDtos = List.of(
            new TaskInsertionDto(
                config.getStartTimeLowerLimit(), config.getDurationLowerLimit(), config.getPriorityLowerLimit()),
            new TaskInsertionDto(
                config.getStartTimeLowerLimit() - 1, config.getDurationLowerLimit(), config.getPriorityLowerLimit()),
            new TaskInsertionDto(
                config.getStartTimeLowerLimit(), config.getDurationLowerLimit() - 1, config.getPriorityLowerLimit()),
            new TaskInsertionDto(
                config.getStartTimeLowerLimit(), config.getDurationLowerLimit(), config.getPriorityLowerLimit() - 1),
            new TaskInsertionDto(
                config.getStartTimeUpperLimit(), config.getDurationUpperLimit(), config.getPriorityUpperLimit())
        );
        List<TaskResponseDto> expectedResponse = List.of(
            new TaskResponseDto(
                1, config.getStartTimeLowerLimit(), Status.NEW, config.getDurationLowerLimit(), config.getPriorityLowerLimit()),
            new TaskResponseDto(
                2, config.getStartTimeUpperLimit(), Status.NEW, config.getDurationUpperLimit(), config.getPriorityUpperLimit())
            );
        String responseBody = mapper.writeValueAsString(expectedResponse);
        String requestBody = mapper.writeValueAsString(insertionRequestDtos);
        mockMvc.perform(MockMvcRequestBuilders
            .post(TASK_LIST_ENDPOINT)
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                MockMvcResultMatchers.status().isCreated(), MockMvcResultMatchers.content().json(responseBody)
            ).andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test Insert Empty List
     * 
     * Test condiction when the filter return a empty list.
     * @throws Exception
     */
    @Test
    @SuppressWarnings("null")
    void testInsertEmptyList() throws Exception {
        List<TaskInsertionDto> insertionRequestDtos = List.of(
            new TaskInsertionDto(
                config.getStartTimeLowerLimit() - 1, config.getDurationLowerLimit(), config.getPriorityLowerLimit()),
            new TaskInsertionDto(
                config.getStartTimeLowerLimit(), config.getDurationLowerLimit() - 1, config.getPriorityLowerLimit()),
            new TaskInsertionDto(
                config.getStartTimeLowerLimit(), config.getDurationLowerLimit(), config.getPriorityLowerLimit() - 1)
        );
        String requestBody = mapper.writeValueAsString(insertionRequestDtos);
        mockMvc.perform(MockMvcRequestBuilders
            .post(TASK_LIST_ENDPOINT)
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                MockMvcResultMatchers.status().isCreated(), MockMvcResultMatchers.content().json("[]")
            ).andDo(MockMvcResultHandlers.print());
    }

    /**
    * Test List
    * 
    * Return all tasks in the database with default filters: page = 0, size = 5 and statuses = [NEW, INITIATED].
    * @throws Exception
    */
    @Test
    @SuppressWarnings("null")
    public void testList() throws Exception{
        // Create mocks
        List<Task> mockedTasks = List.of(
            new Task(
                1, config.getStartTimeLowerLimit(), Status.NEW, config.getDurationLowerLimit(), config.getPriorityLowerLimit()),
            new Task(
                2, config.getStartTimeUpperLimit(), Status.NEW, config.getDurationUpperLimit(), config.getPriorityUpperLimit()),
            new Task(
                3, config.getStartTimeLowerLimit(), Status.INITIATED, config.getDurationLowerLimit(), config.getPriorityLowerLimit()),
            new Task(
                4, config.getStartTimeUpperLimit(), Status.INITIATED, config.getDurationUpperLimit(), config.getPriorityUpperLimit()),
            new Task(
                5, config.getStartTimeLowerLimit(), Status.NEW, config.getDurationLowerLimit(), config.getPriorityLowerLimit())
            );
        Mockito.when(repository.findByStatusIn(Mockito.eq(Set.of(Status.NEW, Status.INITIATED)), Mockito.any()))
            .thenReturn(new PageImpl<>(mockedTasks, PageRequest.of(1, 5, Sort.by("id").ascending()), 5));
        // Executing Test
        mockMvc.perform(MockMvcRequestBuilders
            .get(TASK_LIST_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.content").isArray(),
                MockMvcResultMatchers.jsonPath("$.size").value(5),
                MockMvcResultMatchers.jsonPath("$.number").value(1),
                MockMvcResultMatchers.jsonPath("$.sort.sorted").value(true))
            .andDo(MockMvcResultHandlers.print());
    }

    /***
    * Test List with a status COMPLETED
    * 
    * Return all tasks in the database with default filters: page = 0, size = 5 and statuses = [COMPLETED].
    * @throws Exception
    */
    @Test
    @SuppressWarnings("null")
    public void testListWithAStatus() throws Exception {
        // Create mocks
        List<Task> mockedTasks = List.of(
            new Task(
                5, config.getStartTimeLowerLimit(), Status.COMPLETED, 0, config.getPriorityLowerLimit()),
            new Task(
                6, config.getStartTimeUpperLimit(), Status.COMPLETED, 0, config.getPriorityUpperLimit())
            );
        Mockito.when(repository.findByStatusIn(Mockito.eq(Set.of(Status.COMPLETED)), Mockito.any()))
            .thenReturn(new PageImpl<>(mockedTasks, PageRequest.of(2, 2, Sort.by("id").ascending()), 2));
        // Executing Test
        mockMvc.perform(MockMvcRequestBuilders
            .get(TASK_LIST_ENDPOINT)
            .contentType((MediaType.APPLICATION_JSON))
            .param("page", "2")
            .param("size", "2")
            .param("statuses", "COMPLETED"))
            .andExpectAll(
                MockMvcResultMatchers.status().isOk(), 
                MockMvcResultMatchers.jsonPath("$.content").isArray(),
                MockMvcResultMatchers.jsonPath("$.size").value(2),
                MockMvcResultMatchers.jsonPath("$.number").value(2),
                MockMvcResultMatchers.jsonPath("$.sort.sorted").value(true))
            .andDo(MockMvcResultHandlers.print());
    }
}
