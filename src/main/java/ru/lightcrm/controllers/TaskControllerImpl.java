package ru.lightcrm.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lightcrm.controllers.interfaces.TaskController;
import ru.lightcrm.entities.dtos.TaskDto;
import ru.lightcrm.services.interfaces.TaskService;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;

    @Override
    public List<TaskDto> getAllTasks(List<Long> taskStatesId, Map<String, String> params) {
        return taskService.findAll(params, taskStatesId);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        return taskService.findById(id);
    }

    @Override
    public TaskDto saveTask(TaskDto taskDTO) {
        taskDTO.setId(null);
        return taskService.saveOrUpdate(taskDTO);
    }

    @Override
    public TaskDto updateTask(TaskDto taskDTO) {
        return taskService.saveOrUpdate(taskDTO);
    }

    @Override
    public void deleteTaskById(Long id) {
        taskService.deleteById(id);
    }
}
