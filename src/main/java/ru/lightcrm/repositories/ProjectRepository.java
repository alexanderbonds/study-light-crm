package ru.lightcrm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.lightcrm.entities.Project;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    Optional<Project> findOneByName(@NotNull String name);

    @Query("SELECT p FROM Project p WHERE p.manager.id = ?1")
    List<Project> findByManagerId(@NotNull Long id);
}
