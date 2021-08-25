package ru.lightcrm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.lightcrm.entities.Task;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    Optional<Task> findOneByTitle(@NotNull String title);

    @Query("SELECT t FROM Task t WHERE t.producer.id = ?1")
    List<Task> findByProducerId(@NotNull Long id);

    @Query("SELECT t FROM Task t WHERE t.producer.id = ?1 AND t.taskState.id = ?2")
    List<Task> findByProducerIdAndTaskStateId(@NotNull Long producerId, @NotNull Long taskStateId);

    @Query("SELECT t FROM Task t WHERE t.responsible.id = ?1")
    List<Task> findByResponsibleId(@NotNull Long id);

    @Query("SELECT t FROM Task t WHERE t.responsible.id = ?1 AND t.taskState.id = ?2")
    List<Task> findByResponsibleIdAndTaskStateId(@NotNull Long responsibleId, @NotNull Long taskStateId);

    @Query("SELECT t FROM Task t WHERE t.project.id = ?1")
    List<Task> findByProjectId(@NotNull Long id);

    Integer countAllByCompanyId(@NotNull Long id);
}
