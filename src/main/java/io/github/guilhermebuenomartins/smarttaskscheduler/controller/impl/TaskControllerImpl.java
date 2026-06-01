package io.github.guilhermebuenomartins.smarttaskscheduler.controller.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.guilhermebuenomartins.smarttaskscheduler.controller.TaskController;
import io.github.guilhermebuenomartins.smarttaskscheduler.dto.TaskInsertionDto;
import io.github.guilhermebuenomartins.smarttaskscheduler.dto.TaskResponseDto;
import io.github.guilhermebuenomartins.smarttaskscheduler.model.Status;
import io.github.guilhermebuenomartins.smarttaskscheduler.service.TaskService;

@RestController
@RequestMapping(value = "/api/task")
public class TaskControllerImpl implements TaskController {
    @Autowired
    private TaskService service;
    
    @Override
    @PostMapping("/list")
    public ResponseEntity<List<TaskResponseDto>> insert(@RequestBody List<TaskInsertionDto> requestDtos) {
        List<TaskResponseDto> responseDtos = service.insert(requestDtos);
        return new ResponseEntity<>(responseDtos, HttpStatusCode.valueOf(201));
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<Page<TaskResponseDto>> list(
        @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "5") int size, 
        @RequestParam(name = "statuses", defaultValue = "NEW,INITIATED") Set<Status> statuses) {
        Page<TaskResponseDto> responseDtos = service.findAll(page, size, statuses);
        return new ResponseEntity<>(responseDtos, HttpStatusCode.valueOf(200));
    }
    
}
