package ru.lightcrm.services.interfaces;

import ru.lightcrm.entities.TaskState;
import ru.lightcrm.entities.dtos.TaskStateDto;

import java.util.List;

public interface TaskStateService {
    List<TaskStateDto> findAll();

    TaskStateDto findById(Long id);

    TaskStateDto findOneByName(String name);

    TaskState findEntityById(Long id);
}
