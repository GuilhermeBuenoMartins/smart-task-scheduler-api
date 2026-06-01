package io.github.guilhermebuenomartins.smarttaskscheduler.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.guilhermebuenomartins.smarttaskscheduler.config.VariableConfig;
import io.github.guilhermebuenomartins.smarttaskscheduler.dto.TaskDto;
import io.github.guilhermebuenomartins.smarttaskscheduler.dto.TaskInsertionDto;
import io.github.guilhermebuenomartins.smarttaskscheduler.dto.TaskResponseDto;
import io.github.guilhermebuenomartins.smarttaskscheduler.model.Status;
import io.github.guilhermebuenomartins.smarttaskscheduler.model.Task;
import io.github.guilhermebuenomartins.smarttaskscheduler.repository.TaskRepository;

@Service
public class TaskService {
    @Autowired
    private VariableConfig config;
    @Autowired
    private TaskRepository repository;

    public List<TaskResponseDto> insert(List<TaskInsertionDto> dtos){
        List<TaskDto> filteredDtos = filter(dtos.stream().map(dto -> (TaskDto) dto).toList());
        if (filteredDtos.isEmpty()) { return List.of(); }
        ObjectMapper mapper = new ObjectMapper();
        List<Task> tasks = mapper.convertValue(filteredDtos, new TypeReference<List<Task>>(){});
        tasks.stream().forEach(task -> task.setStatus(Status.NEW));
        tasks = repository.saveAll(tasks);
        return mapper.convertValue(tasks, new TypeReference<List<TaskResponseDto>>(){});
    }

    private List<TaskDto> filter(List<TaskDto> dtos) {
        final Integer startTimeLowerLimit = config.getStartTimeLowerLimit();
        final Integer startTimeUpperLimit = config.getStartTimeUpperLimit();
        final Integer durationLowerLimit = config.getDurationLowerLimit();
        final Integer durationUpperLimit = config.getDurationUpperLimit();
        final Integer priorityLowerLimit = config.getPriorityLowerLimit();
        final Integer priorityUpperLimit = config.getPriorityUpperLimit();
        return dtos.stream().filter(dto -> 
            (dto.getStartTime() >= startTimeLowerLimit && dto.getStartTime() <= startTimeUpperLimit)
            && (dto.getDuration() >= durationLowerLimit && dto.getDuration() <= durationUpperLimit)
            && (dto.getPriority() >= priorityLowerLimit && dto.getPriority() <= priorityUpperLimit)
        ).toList();
    }

    public Page<TaskResponseDto> findAll(Integer page, Integer size, Set<Status> statuses) {
        ObjectMapper mapper = new ObjectMapper();
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Task> taskPage = repository.findByStatusIn(statuses, pageable);
        return taskPage.map(task -> mapper.convertValue(task, TaskResponseDto.class));
    }
}
