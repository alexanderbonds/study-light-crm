package ru.lightcrm.services.filters;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import ru.lightcrm.entities.Task;
import ru.lightcrm.repositories.specifications.TaskSpecification;

import java.util.List;
import java.util.Map;

@Getter
public class TaskFilter {

    private Specification<Task> spec;

    public TaskFilter(Map<String, String> params, @Nullable List<Long> taskStatesId) {

        spec = Specification.where(null);

        if (params != null && params.containsKey("executorId") && !params.get("executorId").isBlank()) {
            Long executorId = Long.parseLong(params.get("executorId"));
            spec = spec.and(TaskSpecification.executorEquals(executorId));

            if (params != null && params.containsKey("coExecutorId") && !params.get("coExecutorId").isBlank()){
                Long coExecutorId = Long.parseLong(params.get("coExecutorId"));
                spec = spec.or(TaskSpecification.coExecutorsEquals(coExecutorId));
            }

        }else{

            if (params != null && params.containsKey("coExecutorId") && !params.get("coExecutorId").isBlank()){
                Long coExecutorId = Long.parseLong(params.get("coExecutorId"));
                spec = spec.and(TaskSpecification.coExecutorsEquals(coExecutorId));
            }
        }

        if (params != null && params.containsKey("title") && !params.get("title").isBlank()) {
            spec = spec.and(TaskSpecification.titleLike(params.get("title")));
        }

        if (params != null && params.containsKey("responsibleId") && !params.get("responsibleId").isBlank()) {
            Long responsibleId = Long.parseLong(params.get("responsibleId"));
            spec = spec.and(TaskSpecification.responsibleEquals(responsibleId));
        }

        if (params != null && params.containsKey("producerId") && !params.get("producerId").isBlank()) {
            Long producerId = Long.parseLong(params.get("producerId"));
            spec = spec.and(TaskSpecification.producerEquals(producerId));
        }

        if (params != null && params.containsKey("taskStateId") && !params.get("taskStateId").isBlank()){
            Long taskStateId = Long.parseLong(params.get("taskStateId"));
            spec = spec.and(TaskSpecification.taskStateEquals(taskStateId));
        }

        if (taskStatesId != null && !taskStatesId.isEmpty()){
            spec = spec.and(TaskSpecification.taskStatesEquals(taskStatesId));
        }

    }
}
