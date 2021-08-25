package ru.lightcrm.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.lightcrm.entities.Task;

import javax.persistence.criteria.JoinType;
import java.util.List;

public class TaskSpecification {

    public static Specification<Task> executorEquals(long executorId){
        return (Specification<Task>)(root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.or(criteriaBuilder.in(root.join("coExecutors", JoinType.LEFT).get("id")).value(executorId),
                        criteriaBuilder.equal(root.get("responsible").get("id"), executorId),
                        criteriaBuilder.equal(root.get("producer").get("id"), executorId));
    }

    public static Specification<Task> responsibleEquals(long responsible_id){
        return (Specification<Task>)(root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("responsible").get("id"), responsible_id);
    }

    public static Specification<Task> producerEquals(long producer_id){
        return (Specification<Task>)(root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("producer").get("id"), producer_id);
    }

    public static Specification<Task> taskStateEquals(long taskState_id){
        return (Specification<Task>)(root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("taskState").get("id"), taskState_id);
    }

    public static Specification<Task> taskStatesEquals(List<Long> taskStates_id){
        return (Specification<Task>)(root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.in(root.get("taskState").get("id")).value(taskStates_id);
    }

    public static Specification<Task> coExecutorsEquals(long coExecutor_id){
        return (Specification<Task>)(root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.in(root.join("coExecutors",JoinType.LEFT).get("id")).value(coExecutor_id);
    }

    public static Specification<Task> titleLike(String title){
        return (Specification<Task>)(root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), title);
    }

}
