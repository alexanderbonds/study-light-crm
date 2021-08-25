package ru.lightcrm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lightcrm.entities.TaskState;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface TaskStateRepository extends JpaRepository<TaskState, Long> {
    Optional<TaskState> findOneByName(@NotNull String name);
}
