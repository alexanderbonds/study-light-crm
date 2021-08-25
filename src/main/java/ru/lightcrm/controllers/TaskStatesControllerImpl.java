package ru.lightcrm.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lightcrm.controllers.interfaces.TaskStateController;
import ru.lightcrm.entities.dtos.TaskStateDto;
import ru.lightcrm.services.interfaces.TaskStateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskStatesControllerImpl implements TaskStateController {

    private final TaskStateService taskStateService;

    @Override
    public List<TaskStateDto> getAllTaskStates() {
        return taskStateService.findAll();
    }
}
