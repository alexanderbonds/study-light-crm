package ru.lightcrm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lightcrm.entities.TaskState;
import ru.lightcrm.entities.dtos.TaskStateDto;
import ru.lightcrm.exceptions.ResourceNotFoundException;
import ru.lightcrm.repositories.TaskStateRepository;
import ru.lightcrm.services.interfaces.TaskStateService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskStateServiceImpl implements TaskStateService {

    private final TaskStateRepository taskStateRepository;

    @Override
    public List<TaskStateDto> findAll() {
        return taskStateRepository.findAll().stream().map(TaskStateDto::new).collect(Collectors.toList());
    }

    @Override
    public TaskState findEntityById(Long id) {
        return taskStateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Статус задачи с id = %s не найден", id)));
    }

    @Override
    public TaskStateDto findById(Long id) {
        return new TaskStateDto(taskStateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Статус задачи с id = %s не найден", id))));
    }

    @Override
    public TaskStateDto findOneByName(String name) {
        return new TaskStateDto(taskStateRepository.findOneByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Статус задачи с наименованием = \"%s\" не найден", name))));
    }
}
