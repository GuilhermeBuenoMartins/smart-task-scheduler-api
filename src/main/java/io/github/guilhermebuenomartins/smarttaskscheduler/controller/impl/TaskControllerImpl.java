package io.github.guilhermebuenomartins.smarttaskscheduler.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.guilhermebuenomartins.smarttaskscheduler.controller.TaskController;
import io.github.guilhermebuenomartins.smarttaskscheduler.dto.TaskInsertionDto;
import io.github.guilhermebuenomartins.smarttaskscheduler.dto.TaskResponseDto;
import io.github.guilhermebuenomartins.smarttaskscheduler.service.TaskService;

@RestController
@RequestMapping(value = "/api/task")
public class TaskControllerImpl implements TaskController {
    @Autowired
    private TaskService service;
    
    @PostMapping("/list")
    public ResponseEntity<List<TaskResponseDto>> insert(@RequestBody List<TaskInsertionDto> requestDtos) {
        List<TaskResponseDto> responseDtos = service.insert(requestDtos);
        return new ResponseEntity<>(responseDtos, HttpStatusCode.valueOf(201));
    }
    
}
