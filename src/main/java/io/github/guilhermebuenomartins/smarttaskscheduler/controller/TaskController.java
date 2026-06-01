package io.github.guilhermebuenomartins.smarttaskscheduler.controller;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.guilhermebuenomartins.smarttaskscheduler.dto.TaskInsertionDto;
import io.github.guilhermebuenomartins.smarttaskscheduler.dto.TaskResponseDto;
import io.github.guilhermebuenomartins.smarttaskscheduler.model.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Task Controller", description = "Task endpoints.")
public interface TaskController {

    @Operation(summary = "Endpoint Insert", description = "Insert tasks into the database.",
        requestBody = @RequestBody(
            required = true, content = @Content(schema = @Schema(implementation = TaskInsertionDto[].class))
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Successful insertion.",
                content = @Content(schema = @Schema(implementation = TaskResponseDto[].class))
            )
        }
    )
    public ResponseEntity<List<TaskResponseDto>> insert(List<TaskInsertionDto> requestDtos);
    
    @Operation(summary = "Endpoint List", description = "List all tasks in the database.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful listing.",
                content = @Content(schema = @Schema(implementation = TaskResponseDto[].class))
            )
        }
    )
    public ResponseEntity<Page<TaskResponseDto>> list(
        @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size, 
        @RequestParam(name = "statuses", defaultValue = "NEW,INITIATED") Set<Status> statuses);
}
